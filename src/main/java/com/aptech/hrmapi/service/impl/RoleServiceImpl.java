package com.aptech.hrmapi.service.impl;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Constant;
import com.aptech.hrmapi.common.ConvertToPage;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.RoleDTO;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.mapper.RoleMapper;
import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.model.role.*;
import com.aptech.hrmapi.repository.RoleAssignRepository;
import com.aptech.hrmapi.repository.RoleRepository;
import com.aptech.hrmapi.repository.UserRepository;
import com.aptech.hrmapi.request.RoleRequest;
import com.aptech.hrmapi.response.PageResponse;
import com.aptech.hrmapi.security.AuthenticationWebFacade;
import com.aptech.hrmapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleAssignRepository roleAssignRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final AuthenticationWebFacade authenticationFacade;
    private final EntityManager entityManager;

    @Override
    public PageResponse<RoleDTO> getAllRole(RoleRequest request) {
        log.info("getAllRole start --------------");
        Sort sort = Sort.by(request.getSortType(), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageIndex() - 1, request.getPageSize(), sort);
        ConvertToPage<RoleDTO> convertToPage = new ConvertToPage<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = builder.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.notEqual(root.get(Role_.STATUS), Constant.STATUS.REMOVE));

        if (StringUtils.isNotBlank(request.getRoleCode())) {
            predicates.add(builder.like(root.get(Role_.ROLE_CODE), "%" + request.getRoleCode() + "%"));
        }
        if (StringUtils.isNotBlank(request.getRoleName())) {
            predicates.add(builder.like(root.get(Role_.ROLE_NAME), "%" + request.getRoleName() + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        List<RoleDTO> list = roleMapper.toDtos(entityManager.createQuery(query.select(root)).getResultList());

        return new PageResponse<>(convertToPage.convertListToPage(list, pageable));
    }

    @Override
    public RoleDTO getRole(Long id) {
        log.info(String.format("getRole with id: %s", id));
        if (id == null) {
            log.error("getRole id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        Role role = roleRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));

        return roleMapper.toDto(role);
    }

    @Override
    public void assignRoleToUser(String username, String roleCode) {
        log.info(String.format("assignRoleToUser start with username : %s and roleCode : %s", username, roleCode));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        Role role = roleRepository.findRoleByRoleCode(roleCode).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        if (!role.getStatus().equals(Constant.STATUS.ACTIVE)) throw new CommonException(Response.ROLE_NOT_FOUND);

        String name = authenticationFacade.getAuthentication().getName();
        Date now = new Date();
        RoleAssign roleAssign = new RoleAssign(user, role, now, name);
        roleAssignRepository.save(roleAssign);
        log.info("assignRoleToUser assign role successfully");
    }

    @Override
    public void unAssignUserFromRole(String username, String roleCode) {
        log.info(String.format("unAssignUserFromRole start with username : %s and roleCode : %s", username, roleCode));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        Role role = roleRepository.findRoleByRoleCode(roleCode).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));

        roleAssignRepository.deleteUserFromRole(user.getId(), role.getId());
        log.info("assignRoleToUser assign role successfully");
    }

    @Override
    @Transactional
    public RoleDTO saveOrUpdate(RoleDTO dto, AddOrUpdateType type) {
        log.info(String.format("saveOrUpdate start with type : %s", type.getType()));
        if (Boolean.TRUE.equals(type.getType())) {
            return createRole(dto);
        } else {
            return updateRole(dto);
        }
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        log.info(String.format("changeStatus with id: %s and status : %s", id, status));
        if (id == null) {
            log.error("changeStatus id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }

        Role role = roleRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        String username = authenticationFacade.getAuthentication().getName();
        Date now = new Date();
        role.setStatus(status);
        role.setModifiedBy(username);
        role.setModifiedDate(now);
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        log.info(String.format("deleteRole with id: %s", id));
        if (id == null) {
            log.error("deleteRole id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }

        Role role = roleRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        String username = authenticationFacade.getAuthentication().getName();
        Date now = new Date();
        role.setStatus(Constant.STATUS.REMOVE);
        role.setModifiedBy(username);
        role.setModifiedDate(now);
        roleRepository.save(role);
    }

    private RoleDTO createRole(RoleDTO dto) {
        log.info("createRole start -------------");
        Optional<Role> optional = roleRepository.findRoleByRoleCode(dto.getRoleCode());
        if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);

        String username = authenticationFacade.getAuthentication().getName();
        Date now = new Date();
        Role role = roleMapper.toEntity(dto);
        role.setId(null);
        role.setRoleCode(role.getRoleCode().toUpperCase());
        role.setCreatedBy(username);
        role.setCreatedDate(now);
        role.setModifiedBy(username);
        role.setModifiedDate(now);
        role.setStatus(Constant.STATUS.ACTIVE);

        return roleMapper.toDto(roleRepository.save(role));
    }

    private RoleDTO updateRole(RoleDTO dto) {
        log.info("updateRole start -------------");
        if (dto.getId() == null) {
            log.error("updateRole id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        Role role = roleRepository.findById(dto.getId()).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        if (!role.getRoleCode().equals(dto.getRoleCode())) {
            Optional<Role> optional = roleRepository.findRoleByRoleCode(dto.getRoleCode());
            if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);
        }

        String username = authenticationFacade.getAuthentication().getName();
        Date now = new Date();
        BeanUtils.copyProperties(dto, role);
        role.setRoleCode(role.getRoleCode().toUpperCase());
        role.setModifiedBy(username);
        role.setModifiedDate(now);

        return roleMapper.toDto(roleRepository.save(role));
    }
}

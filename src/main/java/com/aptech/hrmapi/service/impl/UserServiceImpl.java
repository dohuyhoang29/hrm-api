package com.aptech.hrmapi.service.impl;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Constant;
import com.aptech.hrmapi.common.ConvertToPage;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.UserDTO;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.mapper.UserMapper;
import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.model.User_;
import com.aptech.hrmapi.repository.UserRepository;
import com.aptech.hrmapi.request.UserRequest;
import com.aptech.hrmapi.response.PageResponse;
import com.aptech.hrmapi.security.AuthenticationWebFacade;
import com.aptech.hrmapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationWebFacade authenticationWebFacade;
    private final EntityManager entityManager;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public PageResponse<UserDTO> getAllUser(UserRequest request) {
        log.info("UserServiceImpl getAllUser start --------");
        ConvertToPage<UserDTO> convertToPage = new ConvertToPage<>();
        Sort sort = Sort.by(request.getSortType(), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageIndex() - 1, request.getPageSize(), sort);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get(User_.STATUS), Constant.STATUS.ACTIVE));
        if (StringUtils.isNotEmpty(request.getUsername())) {
            predicates.add(builder.like(root.get(User_.USERNAME), "%" + request.getUsername() + "%"));
        }
        if (StringUtils.isNotEmpty(request.getEmail())) {
            predicates.add(builder.like(root.get(User_.EMAIL), "%" + request.getEmail() + "%"));
        }
        if (StringUtils.isNotEmpty(request.getFullName())) {
            predicates.add(builder.like(root.get(User_.FULL_NAME), "%" + request.getFullName() + "%"));
        }
        if (StringUtils.isNotEmpty(request.getPhoneNumber())) {
            predicates.add(builder.like(root.get(User_.PHONE_NUMBER), "%" + request.getPhoneNumber() + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        List<UserDTO> list = userMapper.toDtos(entityManager.createQuery(query.select(root)).getResultList());

        return new PageResponse<>(convertToPage.convertListToPage(list, pageable));
    }

    @Override
    public UserDTO getUser(Long id) {
        log.info("getUser start -----------");

        if (null == id) {
            log.error("getUser id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        User user = userRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO saveOrUpdate(UserDTO dto, AddOrUpdateType type) {
        log.info("saveOrUpdate with type : " + type.getType());
        if (Boolean.TRUE.equals(type.getType())) {
            return createUser(dto);
        } else {
            return updateUser(dto);
        }
    }

    @Override
    public void deleteUser(Long id) {
        log.info("deleteUser start -----------");
        String username = authenticationWebFacade.getAuthentication().getName();
        Date now = new Date();

        if (null == id) {
            log.error("updateUser id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }

        User user = userRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        user.setStatus(Constant.STATUS.REMOVE);
        user.setModifiedBy(username);
        user.setModifiedDate(now);

        userRepository.save(user);
    }

    private UserDTO createUser(UserDTO dto) {
        log.info("createUser start ---------");
        User user = userMapper.toEntity(dto);
        String username = authenticationWebFacade.getAuthentication().getName();
        Date now = new Date();

        Optional<User> optional = userRepository.findByUsername(user.getUsername());
        if (optional.isPresent()) {
            throw new CommonException(Response.USER_NAME_EXIST);
        }

        user.setId(null);
        user.setStatus(Constant.STATUS.ACTIVE);
        user.setCreatedBy(username);
        user.setCreatedDate(now);
        user.setModifiedBy(username);
        user.setModifiedDate(now);

        return userMapper.toDto(userRepository.save(user));
    }

    private UserDTO updateUser(UserDTO dto) {
        log.info("updateUser start -----------");
        String username = authenticationWebFacade.getAuthentication().getName();
        Date now = new Date();

        if (null == dto.getId()) {
            log.error("updateUser id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }

        User user = userRepository.findById(dto.getId()).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        if (!user.getUsername().equals(dto.getUsername())) {
            Optional<User> optional = userRepository.findByUsername(dto.getUsername());
            if (optional.isPresent()) throw new CommonException(Response.USER_NAME_EXIST);
        }

        BeanUtils.copyProperties(dto, user);
        user.setUsername(user.getUsername().toUpperCase());
        user.setModifiedBy(username);
        user.setModifiedDate(now);

        return userMapper.toDto(userRepository.save(user));
    }
}

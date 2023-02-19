package com.aptech.hrmapi.service.impl;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Constant;
import com.aptech.hrmapi.common.ConvertToPage;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.ActionDto;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.mapper.ActionMapper;
import com.aptech.hrmapi.model.Action;
import com.aptech.hrmapi.model.Action_;
import com.aptech.hrmapi.repository.ActionRepository;
import com.aptech.hrmapi.request.ActionRequest;
import com.aptech.hrmapi.request.PageOption;
import com.aptech.hrmapi.response.PageResponse;
import com.aptech.hrmapi.security.AuthenticationWebFacade;
import com.aptech.hrmapi.service.ActionService;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final AuthenticationWebFacade authenticationFacade;
    private final EntityManager entityManager;

    @Override
    public PageResponse<ActionDto> getAllAction(ActionRequest request) {
        Sort sort = Sort.by(request.getSortType(), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageIndex() - 1, request.getPageSize(), sort);
        ConvertToPage<ActionDto> convertToPage = new ConvertToPage<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Action> query = builder.createQuery(Action.class);
        Root<Action> root = query.from(Action.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get(Action_.STATUS), Constant.STATUS.ACTIVE));

        if (StringUtils.isEmpty(request.getActionCode())) {
            predicates.add(builder.like(root.get(Action_.ACTION_CODE), "%" + request.getActionCode() + "%"));
        }

        if (StringUtils.isEmpty(request.getActionName())) {
            predicates.add(builder.like(root.get(Action_.ACTION_NAME), "%" + request.getActionName() + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        List<ActionDto> list = actionMapper.toDtos(entityManager.createQuery(query.select(root)).getResultList());

        return new PageResponse<>(convertToPage.convertListToPage(list, pageable));
    }

    @Override
    public ActionDto saveOrUpdate(ActionDto dto, AddOrUpdateType type) {
        if (Boolean.TRUE.equals(type.getType())) {
            dto = createAction(dto);
        } else {
            dto = updateAction(dto);
        }

        return dto;
    }

    @Override
    public void deleteAction(Long id) {
        if (id == null) throw new CommonException(Response.SYSTEM_ERROR);
        Action action = actionRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        action.setStatus(Constant.STATUS.INACTIVE);
        action.setModifiedBy(authenticationFacade.getAuthentication().getName());
        action.setModifiedDate(new Date());

        actionRepository.save(action);
    }

    private ActionDto createAction(ActionDto dto) {
        if (dto.getId() != null) return updateAction(dto);

        Optional<Action> optional = actionRepository.findActionByActionCode(dto.getActionCode());
        if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);

        String username = authenticationFacade.getAuthentication().getName();
        Date now = new Date();
        Action action = actionMapper.toEntity(dto);
        action.setId(null);
        action.setActionCode(action.getActionCode().toUpperCase());
        action.setStatus(Constant.STATUS.ACTIVE);
        action.setCreatedBy(username);
        action.setCreatedDate(now);
        action.setModifiedBy(username);
        action.setModifiedDate(now);

        return actionMapper.toDto(actionRepository.save(action));
    }

    private ActionDto updateAction(ActionDto dto) {
        if (dto.getId() == null) throw new CommonException(Response.SYSTEM_ERROR);
        Action action = actionRepository.findById(dto.getId()).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        if (!action.getActionCode().equals(dto.getActionCode())) {
            Optional<Action> optional = actionRepository.findActionByActionCode(dto.getActionCode());
            if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);
        }

        BeanUtils.copyProperties(dto, action);
        action.setActionCode(action.getActionCode().toUpperCase());
        action.setModifiedBy(authenticationFacade.getAuthentication().getName());
        action.setModifiedDate(new Date());

        return actionMapper.toDto(actionRepository.save(action));
    }
}

package com.aptech.hrmapi.service.impl;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Constant;
import com.aptech.hrmapi.common.ConvertToPage;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.ActionDTO;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.mapper.ActionMapper;
import com.aptech.hrmapi.model.Action;
import com.aptech.hrmapi.model.Action_;
import com.aptech.hrmapi.repository.ActionRepository;
import com.aptech.hrmapi.request.ActionRequest;
import com.aptech.hrmapi.response.PageResponse;
import com.aptech.hrmapi.security.AuthenticationWebFacade;
import com.aptech.hrmapi.service.ActionService;
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
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final AuthenticationWebFacade authenticationFacade;
    private final EntityManager entityManager;

    @Override
    public PageResponse<ActionDTO> getAllAction(ActionRequest request) {
        log.info("ActionServiceImpl getAllAction start ------------");
        Sort sort = Sort.by(request.getSortType(), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageIndex() - 1, request.getPageSize(), sort);
        ConvertToPage<ActionDTO> convertToPage = new ConvertToPage<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Action> query = builder.createQuery(Action.class);
        Root<Action> root = query.from(Action.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.notEqual(root.get(Action_.STATUS), Constant.STATUS.REMOVE));

        if (StringUtils.isNotBlank(request.getActionCode())) {
            predicates.add(builder.like(root.get(Action_.ACTION_CODE), "%" + request.getActionCode() + "%"));
        }

        if (StringUtils.isNotBlank(request.getActionName())) {
            predicates.add(builder.like(root.get(Action_.ACTION_NAME), "%" + request.getActionName() + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        List<ActionDTO> list = actionMapper.toDtos(entityManager.createQuery(query.select(root)).getResultList());

        return new PageResponse<>(convertToPage.convertListToPage(list, pageable));
    }

    @Override
    public ActionDTO getAction(Long id) {
        log.info(String.format("ActionServiceImpl getAction with id : %s", id));
        if (id == null) {
            log.error("ActionServiceImpl getAction id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        Action action = actionRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));

        return actionMapper.toDto(action);
    }

    @Override
    @Transactional
    public ActionDTO saveOrUpdate(ActionDTO dto, AddOrUpdateType type) {
        log.info(String.format("ActionServiceImpl saveOrUpdate with type : %s", type.getType()));
        if (Boolean.TRUE.equals(type.getType())) {
            dto = createAction(dto);
        } else {
            dto = updateAction(dto);
        }

        return dto;
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        log.info(String.format("ActionServiceImpl changeStatus with id : %s and status : %s", id, status));
        if (id == null){
            log.error("ActionServiceImpl changeStatus id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        Action action = actionRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        action.setStatus(status);
        action.setModifiedBy(authenticationFacade.getAuthentication().getName());
        action.setModifiedDate(new Date());

        actionRepository.save(action);
    }

    @Override
    public void deleteAction(Long id) {
        log.info(String.format("ActionServiceImpl deleteAction with id : %s", id));
        if (id == null) {
            log.error("ActionServiceImpl deleteAction id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        Action action = actionRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        action.setStatus(Constant.STATUS.REMOVE);
        action.setModifiedBy(authenticationFacade.getAuthentication().getName());
        action.setModifiedDate(new Date());

        actionRepository.save(action);
    }

    private ActionDTO createAction(ActionDTO dto) {
        log.error("ActionServiceImpl createAction start ------");
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

    private ActionDTO updateAction(ActionDTO dto) {
        log.error("ActionServiceImpl updateAction start ------");
        if (dto.getId() == null) {
            log.error("ActionServiceImpl updateAction id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
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

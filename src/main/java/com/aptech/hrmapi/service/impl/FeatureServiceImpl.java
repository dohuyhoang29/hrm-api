package com.aptech.hrmapi.service.impl;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Constant;
import com.aptech.hrmapi.common.ConvertToPage;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.FeatureDTO;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.mapper.FeatureMapper;
import com.aptech.hrmapi.model.Feature;
import com.aptech.hrmapi.model.Feature_;
import com.aptech.hrmapi.repository.FeatureRepository;
import com.aptech.hrmapi.request.FeatureRequest;
import com.aptech.hrmapi.response.PageResponse;
import com.aptech.hrmapi.security.AuthenticationWebFacade;
import com.aptech.hrmapi.service.FeatureService;
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
public class FeatureServiceImpl implements FeatureService {
    private final EntityManager entityManager;
    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;
    private final AuthenticationWebFacade authenticationWebFacade;

    @Override
    public PageResponse<FeatureDTO> getAllFeature(FeatureRequest request) {
        log.info("getAllFeature start ----------------");
        Sort sort = Sort.by(request.getSortType(), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageIndex() - 1, request.getPageSize(), sort);
        ConvertToPage<FeatureDTO> convertToPage = new ConvertToPage<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Feature> query = builder.createQuery(Feature.class);
        Root<Feature> root = query.from(Feature.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.notEqual(root.get(Feature_.STATUS), Constant.STATUS.REMOVE));
        if (StringUtils.isNotEmpty(request.getFeatureCode())) {
            predicates.add(builder.like(root.get(Feature_.FEATURE_CODE), "%" + request.getFeatureCode() + "%"));
        }
        if (StringUtils.isNotEmpty(request.getFeatureName())) {
            predicates.add(builder.like(root.get(Feature_.FEATURE_NAME), "%" + request.getFeatureName() + "%"));
        }
        if (StringUtils.isNotEmpty(request.getFeatureURL())) {
            predicates.add(builder.like(root.get(Feature_.FEATURE_UR_L), "%" + request.getFeatureURL() + "%"));
        }
        if (StringUtils.isNotEmpty(request.getDescription())) {
            predicates.add(builder.like(root.get(Feature_.DESCRIPTION), "%" + request.getDescription() + "%"));
        }
        query.where(predicates.toArray(new Predicate[0]));
        List<FeatureDTO> list = featureMapper.toDtos(entityManager.createQuery(query.select(root)).getResultList());

        return new PageResponse<>(convertToPage.convertListToPage(list, pageable));
    }

    @Override
    public FeatureDTO getFeature(Long id) {
        log.info(String.format("getFeature id: %s", id));
        if (id == null) {
            log.error("getFeature id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        Feature feature = featureRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));

        return featureMapper.toDto(feature);
    }

    @Override
    @Transactional
    public FeatureDTO saveOrUpdate(FeatureDTO dto, AddOrUpdateType type) {
        log.info("saveOrUpdate type : " + type.getType());
        if (Boolean.TRUE.equals(type.getType())) {
            return createFeature(dto);
        } else {
            return updateFeature(dto);
        }
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        log.info(String.format("changeStatus id: %s --- status : %s", id, status));
        if (id == null) {
            log.error("changeStatus id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }

        Feature feature = featureRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        feature.setStatus(status);
        feature.setModifiedBy(authenticationWebFacade.getAuthentication().getName());
        feature.setModifiedDate(new Date());

        featureRepository.save(feature);
    }

    @Override
    public void deleteFeature(Long id) {
        log.info(String.format("deleteFeature id: %s", id));
        if (id == null) {
            log.error("deleteFeature id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }
        Feature feature = featureRepository.findById(id).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        feature.setStatus(Constant.STATUS.REMOVE);
        feature.setModifiedBy(authenticationWebFacade.getAuthentication().getName());
        feature.setModifiedDate(new Date());

        featureRepository.save(feature);
    }

    private FeatureDTO createFeature(FeatureDTO dto) {
        log.info("createFeature start -------");
        Optional<Feature> optional = featureRepository.findFeatureByFeatureCode(dto.getFeatureCode());
        if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);
        optional = featureRepository.findFeatureByFeatureURL(dto.getFeatureURL());
        if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);

        Feature feature = featureMapper.toEntity(dto);
        String username = authenticationWebFacade.getAuthentication().getName();
        Date now = new Date();
        feature.setId(null);
        feature.setFeatureCode(feature.getFeatureCode().toUpperCase());
        feature.setStatus(Constant.STATUS.ACTIVE);
        feature.setCreatedBy(username);
        feature.setCreatedDate(now);
        feature.setModifiedBy(username);
        feature.setModifiedDate(now);

        return featureMapper.toDto(featureRepository.save(feature));
    }

    private FeatureDTO updateFeature(FeatureDTO dto) {
        log.info("updateFeature start -------");
        if (null == dto.getId()) {
            log.error("updateFeature id is null");
            throw new CommonException(Response.SYSTEM_ERROR);
        }

        Feature feature = featureRepository.findById(dto.getId()).orElseThrow(() -> new CommonException(Response.OBJECT_NOT_FOUND));
        if (!feature.getFeatureCode().equals(dto.getFeatureCode())) {
            Optional<Feature> optional = featureRepository.findFeatureByFeatureCode(dto.getFeatureCode());
            if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);
        }
        if (!feature.getFeatureURL().equals(dto.getFeatureURL())) {
            Optional<Feature> optional = featureRepository.findFeatureByFeatureURL(dto.getFeatureURL());
            if (optional.isPresent()) throw new CommonException(Response.OBJECT_IS_EXISTS);
        }

        BeanUtils.copyProperties(dto, feature);
        String username = authenticationWebFacade.getAuthentication().getName();
        Date now = new Date();
        feature.setFeatureCode(feature.getFeatureCode().toUpperCase());
        feature.setModifiedBy(username);
        feature.setModifiedDate(now);

        return featureMapper.toDto(featureRepository.save(feature));
    }
}

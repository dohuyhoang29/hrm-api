package com.aptech.hrmapi.service;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.dto.FeatureDTO;
import com.aptech.hrmapi.request.FeatureRequest;
import com.aptech.hrmapi.response.PageResponse;

public interface FeatureService {
    PageResponse<FeatureDTO> getAllFeature(FeatureRequest request);
    FeatureDTO getFeature(Long id);
    FeatureDTO saveOrUpdate(FeatureDTO dto, AddOrUpdateType type);
    void changeStatus(Long id, Integer status);
    void deleteFeature(Long id);
}

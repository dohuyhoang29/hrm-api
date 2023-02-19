package com.aptech.hrmapi.service;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.dto.ActionDto;
import com.aptech.hrmapi.request.ActionRequest;
import com.aptech.hrmapi.response.PageResponse;


public interface ActionService {
    PageResponse<ActionDto> getAllAction(ActionRequest request);
    ActionDto saveOrUpdate(ActionDto dto, AddOrUpdateType type);
    void deleteAction(Long id);
}

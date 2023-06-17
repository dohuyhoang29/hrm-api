package com.aptech.hrmapi.service;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.dto.ActionDTO;
import com.aptech.hrmapi.request.ActionRequest;
import com.aptech.hrmapi.response.PageResponse;


public interface ActionService {
    PageResponse<ActionDTO> getAllAction(ActionRequest request);
    ActionDTO getAction(Long id);
    ActionDTO saveOrUpdate(ActionDTO dto, AddOrUpdateType type);
    void changeStatus(Long id, Integer status);
    void deleteAction(Long id);
}

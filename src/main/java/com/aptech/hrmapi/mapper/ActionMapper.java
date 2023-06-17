package com.aptech.hrmapi.mapper;

import com.aptech.hrmapi.dto.ActionDTO;
import com.aptech.hrmapi.model.Action;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionMapper {
    Action toEntity (ActionDTO dto);
    ActionDTO toDto (Action entity);
    List<Action> toEntities(List<ActionDTO> dtos);
    List<ActionDTO> toDtos(List<Action> entities);
}

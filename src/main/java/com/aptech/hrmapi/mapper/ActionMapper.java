package com.aptech.hrmapi.mapper;

import com.aptech.hrmapi.dto.ActionDto;
import com.aptech.hrmapi.model.Action;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionMapper {
    Action toEntity (ActionDto dto);
    ActionDto toDto (Action entity);
    List<Action> toEntities(List<ActionDto> dtos);
    List<ActionDto> toDtos(List<Action> entities);
}

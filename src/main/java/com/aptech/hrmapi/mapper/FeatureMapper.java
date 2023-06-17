package com.aptech.hrmapi.mapper;

import com.aptech.hrmapi.dto.FeatureDTO;
import com.aptech.hrmapi.model.Feature;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeatureMapper {
    List<FeatureDTO> toDtos(List<Feature> entities);
    List<Feature> toEntities(List<FeatureDTO> dtos);
    FeatureDTO toDto(Feature entity);
    Feature toEntity(FeatureDTO dto);
}

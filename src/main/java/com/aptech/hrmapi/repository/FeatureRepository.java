package com.aptech.hrmapi.repository;

import com.aptech.hrmapi.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findFeatureByFeatureCode(String featureCode);
    Optional<Feature> findFeatureByFeatureURL(String featureURL);
}

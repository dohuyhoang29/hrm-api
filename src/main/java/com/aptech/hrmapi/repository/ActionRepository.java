package com.aptech.hrmapi.repository;

import com.aptech.hrmapi.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {
    Optional<Action> findActionByActionCode(String actionCode);
}

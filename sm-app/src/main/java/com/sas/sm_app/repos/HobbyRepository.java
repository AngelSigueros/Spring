package com.sas.sm_app.repos;

import com.sas.sm_app.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
}

package com.friends.demo.repository;

import com.friends.demo.model.History;
import liquibase.change.core.LoadDataChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}

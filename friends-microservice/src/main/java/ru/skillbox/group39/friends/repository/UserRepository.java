package ru.skillbox.group39.friends.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.skillbox.group39.friends.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
    Optional<Users> findByUserId(Long id);

    List<Users> findAllByOrderByRatingDesc();
}

package ru.skillbox.group39.friends.repository;

import org.springframework.data.domain.PageRequest;
import ru.skillbox.group39.friends.dto.enums.StatusCode;
import ru.skillbox.group39.friends.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {

    Optional<Status> findByUserToAndUserFrom(Long user_id1, Long user_id2);

    Boolean existsByUserToAndUserFrom(Long userTo, Long userFrom);

    @Query(value = "select DISTINCT s.user_to from Status s where s.status =:userStatus", nativeQuery = true)
    List<Long> getUserListByStatus(String userStatus);

    @Query(value = "select DISTINCT s.user_from from Status s where s.status =:status and user_to =:mainId", nativeQuery = true)
    List<Long> getUserListByStatusFriend(Long mainId, String status);

    @Query(value = "select DISTINCT s.user_from from Status s where s.status =:status and user_to =:mainId", nativeQuery = true)
    List<Long> getUserListByStatusFriend(Long mainId, String status, PageRequest of);

    @Query(value = "select DISTINCT s.user_from from Status s where s.status =:status and user_to =:mainId", nativeQuery = true)
    List<Long> getUserListByStatusFriend1(Long mainId, StatusCode status);

    Integer countByUserToAndStatus(Long id, StatusCode statusUser);

}

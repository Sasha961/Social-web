package com.friends.demo.repository;

import com.friends.demo.dto.Enum.StatusUser;
import com.friends.demo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {

    Optional<Status> findByUserId1AndUserId2(String user_id1, String user_id2);

    @Query(value = "select DISTINCT s.user_id1 from Status s where s.status =:userStatus", nativeQuery = true)
    List<String> getUserListByStatus(Integer userStatus);

    @Query(value = "select DISTINCT s.user_id2 from Status s where s.status =:status and user_id1 =:mainId", nativeQuery = true)
    List<String> getUserListByStatusFriend(String mainId, Integer status);

    Integer countByUserId1AndStatus(String id, StatusUser statusUser);

}

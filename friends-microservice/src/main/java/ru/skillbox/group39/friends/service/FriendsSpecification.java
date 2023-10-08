package ru.skillbox.group39.friends.service;

import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.group39.friends.dto.enums.StatusCode;
import ru.skillbox.group39.friends.model.Status;
import ru.skillbox.group39.friends.model.Users;

public class FriendsSpecification {

    public static Specification<Users> findByFirstName(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), String.format("%%%s%%", searchWord).toLowerCase());
    }

    public static Specification<Users> findByLastName(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), String.format("%%%s%%", searchWord).toLowerCase());
    }

    public static Specification<Users> findByCountry(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("country")), String.format("%%%s%%", searchWord).toLowerCase());
    }

    public static Specification<Users> findByCity(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), String.format("%%%s%%", searchWord).toLowerCase());
    }

    public static Specification<Users> findByAge(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("age")), String.format("%%%s%%", searchWord).toLowerCase());
    }

    public static Specification<Status> findByStatusCode(StatusCode searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), String.format("%%%s%%", searchWord).toLowerCase());
    }

}

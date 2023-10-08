package ru.skillbox.group39.friends.controller;

import ru.skillbox.group39.friends.dto.friend.FriendShortDto;
import ru.skillbox.group39.friends.dto.CountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@SecurityRequirement(name = "JWT")
@Tag(name = "Friend service", description = "Сервис друзей")
public interface FriendsController {

    @PutMapping(value = "/{id}/approve")
    @Operation(summary = "Подтверждение запроса на дружбу по идентификатору")
    FriendShortDto approvedFriend(@PathVariable Long id);

    @PutMapping(value = "/unblock/{id}")
    @Operation(summary = "Разблокировка пользователя по идентификатору")
    FriendShortDto unBlock(@PathVariable Long id);

    @PutMapping(value = "/block/{id}")
    @Operation(summary = "Блокировка пользователя по идентификатору")
    FriendShortDto block(@PathVariable(name = "id") Long id);

    @PostMapping(value = "/{id}/request")
    @Operation(summary = "Создание запроса на дружбу по идентификатору")
    FriendShortDto requestFriend(@PathVariable(name = "id") Long id);

    @PostMapping(value = "/subscribe/{id}")
    @Operation(summary = "Подписка на пользователя по иднетификатору")
    FriendShortDto subscribe(@PathVariable(name = "id") Long id);

    @GetMapping
    @Operation(summary = "Получение списка друзей по различным условиям поиска")
    List<FriendShortDto> getAll(@RequestParam(value = "size", defaultValue = "5") Integer size,
                                @RequestParam(value = "statusCode", defaultValue = "FRIENDS") String statusCode,
                                @RequestParam(value = "firstName", required = false) String firstName,
                                @RequestParam(value = "ageFrom", required = false) Integer ageFrom,
                                @RequestParam(value = "ageTo", required = false) Integer ageTo);

    @GetMapping(value = "/{id}")
    @Operation(summary = "Получение записи о дружбе по id записи")
    FriendShortDto getById(@PathVariable(name = "id") Long id);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаление существующих отношений с пользователем по идентификатору")
    void deleteById(@PathVariable(name = "id") Long id);

    @GetMapping(value = "/status")
    @Operation(summary = "Получение идентификаторов пользователей имеющих заданный статус")
    List<Long> getStatusFriend(@RequestParam(name = "statusUser", required = false) String status);

    @GetMapping(value = "/recommendations")
    @Operation(summary = "Выдача рекомендаций на држубу")
    List<FriendShortDto> getRecommendations();

    @GetMapping("/friendId")
    @Operation(summary = "Получение списка идентификаторов друзей")
    List<Long> getFriendId();

    @GetMapping("/friendId/{id}")
    @Operation(summary = "Получение списка идентификаторов друзей для пользователя с id")
    List<Long> getFriendById(@PathVariable(name = "id") Long id);

    @GetMapping(value = "/count")
    @Operation(summary = "Получение количества заявок в друзья")
    CountDto getCount();

    @GetMapping(value = "/check")
    @Operation(summary = "Получение статусов отношений для заданного списка идентификаторов пользователей")
    List<String> checkFriend(ArrayList<String> ids);

    @GetMapping(value = "/blockFriendId")
    @Operation(summary = "получение идентификаторов пользователей, заблокировавших текущего пользователя")
    List<Long> getBlockFriendId();
}

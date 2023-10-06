package com.friends.demo.controller;

import com.friends.demo.dto.CountDto;
import com.friends.demo.dto.friend.FriendSearchDto;
import com.friends.demo.dto.friend.FriendShortDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@SecurityRequirement(name = "JWT")
@Tag(name = "Friend service", description = "Сервис друзей")
public interface FriendsController {

    @PutMapping(value = "/{id}/approve")
    @Operation(summary = "Подтверждение запроса на дружбу по идентификатору")
    FriendShortDto approvedFriend(@PathVariable String id);

    @PutMapping(value = "/unblock/{id}")
    @Operation(summary = "Разблокировка пользователя по идентификатору")
    FriendShortDto unBlock(@PathVariable String id);

    @PutMapping(value = "/block/{id}")
    @Operation(summary = "Блокировка пользователя по идентификатору")
    FriendShortDto block(@PathVariable(name = "id") String id);

    @PostMapping(value = "/{id}/request")
    @Operation(summary = "Создание запроса на дружбу по идентификатору")
    FriendShortDto requestFriend(@PathVariable(name = "id") String id);

    @PostMapping(value = "/subscribe/{id}")
    @Operation(summary = "Подписка на пользователя по иднетификатору")
    FriendShortDto subscribe(@PathVariable(name = "id") String id);

    @GetMapping
    @Operation(summary = "Получение списка друзей по различным условиям поиска")
    FriendShortDto getAll(
            @RequestBody(required = true) FriendSearchDto searchDto,
            @RequestParam Integer page, @RequestParam Integer size, @RequestParam ArrayList<String> sort);

    @GetMapping(value = "/{id}")
    @Operation(summary = "Получение записи о дружбе по id записи")
    FriendShortDto getById(@PathVariable(name = "id") String id);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаление существующих отношений с пользователем по идентификатору")
    void deleteById(@PathVariable(name = "id") String id);

    @GetMapping(value = "/status")
    @Operation(summary = "Получение идентификаторов пользователей имеющих заданный статус")
    List<String> statusFriend(@RequestParam(name = "statusUser") String status);

    @GetMapping(value = "/recommendations")
    @Operation(summary = "Выдача рекомендаций на држубу")
    List<FriendShortDto> getRecommendations(@RequestBody FriendSearchDto friendShortDto);

    @GetMapping("/friendId")
    @Operation(summary = "Получение списка идентификаторов друзей")
    List<String> getFriendId();

    @GetMapping("/friendId/{id}")
    @Operation(summary = "Получение списка идентификаторов друзей для пользователя с id")
    List<String> getFriendById(@PathVariable(name = "id") String id);

    @GetMapping(value = "/count")
    @Operation(summary = "Получение количества заявок в друзья")
    CountDto getCount();

    @GetMapping(value = "/check")
    @Operation(summary = "Получение статусов отношений для заданного списка идентификаторов пользователей")
    List<String> checkFriend(ArrayList<String> ids);

    @GetMapping(value = "/blockFriendId")
    @Operation(summary = "получение идентификаторов пользователей, заблокировавших текущего пользователя")
    List<String> getBlockFriendId();
}

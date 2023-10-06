package ru.skillbox.ms.searchmicroservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.ms.searchmicroservice.dto.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/search")
@SecurityRequirement(name = "JWT")
@Tag(name = "Search service", description = "Сервис поиска")
public interface SearchController {

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск по всему(посты и пользователи)")
    SearchDto searchAll(@RequestParam(name = "searchWord") String searchWord,
                        @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                        @RequestParam(name = "limit", defaultValue = "10") Integer limit);

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск по пользователям")
    List<UserDto> searchUsers(@RequestParam(name = "searchWord") String searchWord,
                              @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                              @RequestParam(name = "limit", defaultValue = "10") Integer limit);

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск по постам")
    List<PostDto> searchPosts(@RequestParam(name = "searchWord") String searchWord,
                              @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                              @RequestParam(name = "limit", defaultValue = "10") Integer limit);

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск по комментариям(для админки)")
    List<CommentDto> searchComments(@RequestParam(name = "searchWord") String searchWord,
                                    @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                                    @RequestParam(name = "limit", defaultValue = "10") Integer limit);

    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск по сообщениям(для админки)")
    List<MessageDto> searchMessages(@RequestParam(name = "searchWord") String searchWord,
                                    @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                                    @RequestParam(name = "limit", defaultValue = "10") Integer limit);
}

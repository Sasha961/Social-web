package ru.skillbox.group39.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.group39.search.dto.SearchDto;
import ru.skillbox.group39.search.dto.account.AccountDto;
import ru.skillbox.group39.search.dto.posts.CommentDto;
import ru.skillbox.group39.search.dto.posts.PostDto;

import java.util.List;

@RequestMapping("/api/v1/search")
@RestController
@SecurityRequirement(name = "JWT")
@Tag(name = "Search service", description = "Сервис поиска")
public interface SearchController {

    @GetMapping(value = "/all")
    @Operation(description = "Поиск по всему(посты, комментарии к постам и пользователи)")
    SearchDto searchAll(@RequestParam(name = "searchWord") String searchWord,
                        @RequestParam(name = "offset", defaultValue = "0") String offset,
                        @RequestParam(name = "limit", defaultValue = "5") String limit);

    @GetMapping(value = "/users")
    @Operation(description = "Поиск по пользователям")
    List<AccountDto> searchUsers(@RequestParam(name = "searchWord") String searchWord,
                                 @RequestParam(name = "offset", defaultValue = "0") String offset,
                                 @RequestParam(name = "limit", defaultValue = "5") String limit);

    @GetMapping(value = "/posts")
    @Operation(description = "Поиск по постам")
    List<PostDto> searchPosts(@RequestParam(name = "searchWord") String searchWord,
                              @RequestParam(name = "offset", defaultValue = "0") String offset,
                              @RequestParam(name = "limit", defaultValue = "5") String limit);

    @GetMapping(value = "/comments")
    @Operation(description = "Поиск по комментариям(для админки)")
    List<CommentDto> searchComments(@RequestParam(name = "searchWord") String searchWord,
                                    @RequestParam(name = "offset", defaultValue = "0") String offset,
                                    @RequestParam(name = "limit", defaultValue = "5") String limit);
}

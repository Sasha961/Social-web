package ru.skillbox.group39.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.group39.search.dto.SearchDto;
import ru.skillbox.group39.search.controller.feignClient.PostsController;
import ru.skillbox.group39.search.controller.feignClient.UsersController;
import ru.skillbox.group39.search.dto.account.AccountDto;
import ru.skillbox.group39.search.dto.posts.CommentDto;
import ru.skillbox.group39.search.dto.posts.PostDto;
import ru.skillbox.group39.search.service.SearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchControllerImpl implements SearchController {

    private final UsersController usersController;

    private final PostsController postsController;

    private final SearchService searchService;

    @Override
    public SearchDto searchAll(String searchWord, String offset, String limit) {
        return searchService.searchAll(searchWord, offset, limit);
    }

    @Override
    public List<AccountDto> searchUsers(String searchWord, String offset, String limit) {
        return usersController.getUserByName(searchWord, offset, limit);
    }

    @Override
    public List<PostDto> searchPosts(String searchWord, String offset, String limit) {
        return postsController.searchPosts(searchWord, offset, limit);
    }

    @Override
    public List<CommentDto> searchComments(String searchWord, String offset, String limit) {
        return postsController.searchComment(searchWord, offset, limit);
    }
}

package ru.skillbox.group39.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.group39.search.dto.SearchDto;
import ru.skillbox.group39.search.controller.feignClient.PostsController;
import ru.skillbox.group39.search.controller.feignClient.UsersController;
import ru.skillbox.group39.search.dto.account.AccountDto;
import ru.skillbox.group39.search.dto.posts.CommentDto;
import ru.skillbox.group39.search.dto.posts.PostDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final UsersController usersController;
    private final PostsController postsController;

    @Override
    public SearchDto searchAll(String searchWord, String offset, String limit) {

        List<AccountDto> accountDtoList = usersController.getUserByName(searchWord, offset, limit);
        List<PostDto> postDtoList = postsController.searchPosts(searchWord, offset, limit);
        List<CommentDto> commentDtoList = postsController.searchComment(searchWord, offset, limit);

        return SearchDto.builder()
                .accountDtoList(accountDtoList)
                .postDtoList(postDtoList)
                .commentDtoList(commentDtoList)
                .build();
    }
}


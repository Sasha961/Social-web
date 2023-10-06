package ru.skillbox.ms.searchmicroservice.controller;

import lombok.RequiredArgsConstructor;
import ru.skillbox.ms.searchmicroservice.dto.*;

import java.util.List;

@RequiredArgsConstructor
public class SearchControllerImpl implements SearchController {

    @Override
    public SearchDto searchAll(String searchWord, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public List<UserDto> searchUsers(String searchWord, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public List<PostDto> searchPosts(String searchWord, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public List<CommentDto> searchComments(String searchWord, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public List<MessageDto> searchMessages(String searchWord, Integer offset, Integer limit) {
        return null;
    }
}

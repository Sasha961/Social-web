package ru.skillbox.group39.search.controller.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.group39.search.config.FeignConfig;
import ru.skillbox.group39.search.dto.posts.CommentDto;
import ru.skillbox.group39.search.dto.posts.PostDto;

import java.util.List;

@FeignClient(name = "PostController", url = "http://5.63.154.191:8089/api/v1/post", configuration = FeignConfig.class)
public interface PostsController {

    @GetMapping(value = "/search-posts")
    List<PostDto> searchPosts(@RequestParam(name = "searchWord") String searchWord,
                              @RequestParam(defaultValue = "0") String offset,
                              @RequestParam(defaultValue = "3") String limit);

    @GetMapping(value = "/search-comment")
    List<CommentDto> searchComment(@RequestParam(name = "searchWord") String searchWord,
                                   @RequestParam(defaultValue = "0") String offset,
                                   @RequestParam(defaultValue = "3") String limit);
}

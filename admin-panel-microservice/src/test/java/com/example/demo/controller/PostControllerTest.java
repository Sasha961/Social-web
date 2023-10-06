package com.example.demo.controller;

import com.example.demo.controller.feignClient.ControllerFromPosts;
import com.example.demo.dto.comment.Comment;
import com.example.demo.dto.comment.CommentTypeEnum;
import com.example.demo.dto.post.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControllerFromPosts controllerFromPosts;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void searchPostTest() throws Exception {

        String word = "search";

        Post post = new Post();
        post.setPostText("test text");
        post.setBlocked(true);

        List<Post> posts = new ArrayList<>();
        posts.add(post);

        given(controllerFromPosts.searchPosts(word)).willReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/posts/search-posts/{word}", "word"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void blockPostTest() throws Exception {

        given(controllerFromPosts.blockPost(1L)).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/posts/block-post").param("id","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void unblockCommentTest() throws Exception {

        given(controllerFromPosts.blockPost(1L)).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/posts/unblock-post").param("id","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
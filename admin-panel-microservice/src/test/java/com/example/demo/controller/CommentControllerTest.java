package com.example.demo.controller;

import com.example.demo.controller.feignClient.ControllerFromPosts;
import com.example.demo.dto.account.AccountDto;
import com.example.demo.dto.account.User;
import com.example.demo.dto.comment.Comment;
import com.example.demo.dto.comment.CommentTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControllerFromPosts controllerFromPosts;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void searchCommentTest() throws Exception {

        String word = "search";

        Comment comment = new Comment();
        comment.setCommentText("aaaaaaa");
        comment.setCommentType(CommentTypeEnum.COMMENT);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        given(controllerFromPosts.searchComment(word)).willReturn(commentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/comments/search-comments/{word}", "word"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void blockCommentTest() throws Exception {

        given(controllerFromPosts.blockComment(1L)).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/comments/block-comments").param("id","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void unblockCommentTest() throws Exception {

        given(controllerFromPosts.blockComment(1L)).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/comments/unblock-comments").param("id","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
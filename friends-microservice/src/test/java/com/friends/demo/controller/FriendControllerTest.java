package com.friends.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friends.demo.dto.CountDto;
import com.friends.demo.dto.friend.FriendShortDto;
import com.friends.demo.service.impl.FriendsServiceImpl;
import com.friends.demo.service.security.JWT.JwtFilter;
import com.friends.demo.service.security.JWT.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;

import static com.friends.demo.dto.Enum.StatusUser.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = {FriendsControllerImpl.class, FriendsController.class})
@ExtendWith(MockitoExtension.class)
public class FriendControllerTest {

    @MockBean
    private FriendsServiceImpl friendsService;

    @MockBean
    private JwtFilter filter;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private FriendShortDto friendShortDto;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        friendShortDto = FriendShortDto.builder()
                .friendID("2")
                .statusCode(BLOCKED)
                .id("1")
                .previousStatusCode(NONE)
                .build();
    }

    @Test
    public void approveFriendTest_return_FriendShortDtoNotNull() throws Exception {

        given(friendsService.approvedFriend(ArgumentMatchers.any())).willReturn(friendShortDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/friends/{id}/approve", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(friendShortDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", org.hamcrest.CoreMatchers.is(friendShortDto.getStatusCode().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void unBlock_returnFriendShortDtoNotNull() throws Exception {

        friendShortDto.setStatusCode(NONE);

        given(friendsService.unBlock(ArgumentMatchers.any())).willReturn(friendShortDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/friends/unblock/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(friendShortDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", org.hamcrest.CoreMatchers.is(friendShortDto.getStatusCode().toString())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void blockTest_returnFriendShortDto() throws Exception {

        given(friendsService.block(ArgumentMatchers.any())).willReturn(friendShortDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/friends/block/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(friendShortDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", org.hamcrest.CoreMatchers.is(friendShortDto.getStatusCode().toString())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void requestFriendTest_returnFriendShortDto() throws Exception {

        friendShortDto.setStatusCode(REQUEST_TO);

        given(friendsService.requestFriend(ArgumentMatchers.any())).willReturn(friendShortDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/friends/{id}/request", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(friendShortDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", org.hamcrest.CoreMatchers.is(friendShortDto.getStatusCode().toString())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void subscribeTest_returnFriendShortDto() throws Exception {

        friendShortDto.setStatusCode(REQUEST_FROM);

        given(friendsService.subscribe(ArgumentMatchers.any())).willReturn(friendShortDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/friends/subscribe/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(friendShortDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", org.hamcrest.CoreMatchers.is(friendShortDto.getStatusCode().toString())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    public void getAllTest_returnFriendShortDto() throws Exception {
//
//    }

    @Test
    public void getByIdTest_returnFriendShortDto() throws Exception {

        given(friendsService.getById(ArgumentMatchers.any())).willReturn(friendShortDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/friends/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(friendShortDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteByIdTest_returnFriendShortDto() throws Exception {

        doNothing().when(friendsService);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/friends/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void statusFriendsTest_returnListFriends() throws Exception {

        List<String> statusFriendsList = List.of("0", "3", "4");

        given(friendsService.statusFriend(ArgumentMatchers.any())).willReturn(statusFriendsList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/friends/status").param("statusUser", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(statusFriendsList)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", org.hamcrest.CoreMatchers.is(statusFriendsList.size())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFriendsById_returnListFriends() throws Exception {

        List<String> statusFriendsList = List.of("0", "3", "4");

        given(friendsService.getFriendById(ArgumentMatchers.any())).willReturn(statusFriendsList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/friends/friendId/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(statusFriendsList)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", org.hamcrest.CoreMatchers.is(statusFriendsList.size())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCount_returnCountDto() throws Exception {

        CountDto countDto = new CountDto();
        countDto.setCount(12);

        given(friendsService.getCount()).willReturn(countDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/friends/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(countDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", org.hamcrest.CoreMatchers.is(countDto.count)))
                .andDo(print());
    }

    @Test
    public void blockFriendTest_returnListBlockFriends() throws Exception {

        List<String> blockFriendList = List.of("0", "3", "4");

        given(friendsService.getFriendById(ArgumentMatchers.any())).willReturn(blockFriendList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/friends/friendId/blockFriendId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(blockFriendList)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", org.hamcrest.CoreMatchers.is(blockFriendList.size())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}

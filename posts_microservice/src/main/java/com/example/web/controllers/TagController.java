package com.example.web.controllers;

import com.example.web.dto.PostStatisticRequestDto;
import com.example.web.dto.TagSearchDto;
import com.example.web.service.StatisticServiceImpl;
import com.example.web.service.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagServiceImpl tagService;

    //Получение тегов
    @GetMapping(value = "/api/v1/tag", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> tagSearch(TagSearchDto tagSearch) {
        return tagService.tagSearch(tagSearch);
    }
}

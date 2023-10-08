package com.example.controllers;

import com.example.dto.TagDto;
import com.example.dto.TagSearchDto;
import com.example.service.TagServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequiredArgsConstructor
public class TagController {

    TagServiceImpl tagService;

    @Operation(description = "Получение тегов", tags = {"Tag service"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),})
    @ResponseBody
    @GetMapping(value = "/api/v1/tag")
    public TagDto tagSearch(@RequestBody TagSearchDto tagSearch) {
        return tagService.tagSearch(tagSearch);
    }


}

package com.example.web.service;

import com.example.web.dto.TagSearchDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TagServiceImpl implements TagService{

    @Override
    public ResponseEntity<Void> tagSearch(TagSearchDto tagSearch) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

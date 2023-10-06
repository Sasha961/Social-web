package com.example.web.service;

import com.example.web.dto.TagSearchDto;
import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity<Void> tagSearch (TagSearchDto tagSearch);
}

package ru.skillbox.group39.search.service;

import org.springframework.stereotype.Service;
import ru.skillbox.group39.search.dto.SearchDto;

@Service
public interface SearchService {

    SearchDto searchAll(String searchWord, String offset, String limit);
}

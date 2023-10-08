package ru.skillbox.group39.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.skillbox.group39.search.dto.account.AccountDto;
import ru.skillbox.group39.search.dto.posts.CommentDto;
import ru.skillbox.group39.search.dto.posts.PostDto;

import java.util.List;

@Builder
@Data
@ToString
@Schema
public class SearchDto {

    List<AccountDto> accountDtoList;
    List<PostDto> postDtoList;
    List<CommentDto> commentDtoList;

}

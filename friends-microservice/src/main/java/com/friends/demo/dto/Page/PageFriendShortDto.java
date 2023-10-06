package com.friends.demo.dto.Page;
import com.friends.demo.dto.friend.FriendShortDto;
import com.friends.demo.dto.Sort;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class PageFriendShortDto {
    public Long totalElements;

    public Integer totalPages;

    public Integer number;

    public Integer size;

    public FriendShortDto content;

    public Sort sort;

    public boolean first;

    public boolean last;

    public Integer numberOfElements;

    public PageablObject pageable;

    public boolean empty;
}

package com.friends.demo.dto.Page;
import com.friends.demo.dto.Sort;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class PageablObject {

    public Long offset;

    public Sort sort;

    public Integer pageSize;

    public boolean paged;

    public boolean unpaged;

    public Integer pageNumber;
}

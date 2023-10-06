package com.friends.demo.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;

@Data
@Schema
public class Pageable {

    public Integer page;

    public Integer size;

    public ArrayList<String> sort;
}

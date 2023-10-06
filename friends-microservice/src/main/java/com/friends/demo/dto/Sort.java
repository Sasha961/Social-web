package com.friends.demo.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class Sort {

    public boolean empty;

    public boolean sorted;

    public boolean unsorted;
}

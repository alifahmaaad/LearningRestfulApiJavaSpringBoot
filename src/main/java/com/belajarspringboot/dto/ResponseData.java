package com.belajarspringboot.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class ResponseData<Payload> {
    private boolean status;
    private List<String> messages=new ArrayList<>();
    private Payload payload;
}

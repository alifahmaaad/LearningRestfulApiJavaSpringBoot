package com.belajarspringboot.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<Payload> {
    private boolean status;
    private List<String> messages=new ArrayList<>();
    private Payload payload;
}

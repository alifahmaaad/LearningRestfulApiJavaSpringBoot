package com.belajarspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataUserWithoutPass {
    private Long id;
    private String nama;
    private String email;
}

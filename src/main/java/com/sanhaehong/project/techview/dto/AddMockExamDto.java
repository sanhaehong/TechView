package com.sanhaehong.project.techview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddMockExamDto {

    @NotBlank
    private String title;
    private String information;

}

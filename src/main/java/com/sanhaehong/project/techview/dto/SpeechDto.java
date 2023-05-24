package com.sanhaehong.project.techview.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SpeechDto {
    private String answer;
    private MultipartFile audio;
}

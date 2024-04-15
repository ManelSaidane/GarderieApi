package com.example.garderieapi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmploiDto {
    MultipartFile file;
    String name;
}

package com.example.garderieapi.Service;

import org.springframework.web.multipart.MultipartFile;

public interface IfileService {
    String uploadFile(MultipartFile file, String description);
}

package com.example.garderieapi.Controller;

import com.example.garderieapi.dto.FileDto;
import com.example.garderieapi.Service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/upload")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public String uploadFile(@Valid @ModelAttribute FileDto fileDto, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors if needed
            return "Validation failed";
        }

        MultipartFile file = fileDto.getFile();
        String description = fileDto.getDescription(); // Nouvelle propriété

        // Utilisez le service pour gérer le téléchargement du fichier
        String fileName = fileService.uploadFile(file, description);

        return "Fichier téléchargé avec succès. Nom du fichier : " + fileName;
    }
}

package com.example.garderieapi.Service;

import com.example.garderieapi.entity.FileUpLoad;
import com.example.garderieapi.Repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileService implements IfileService{

    private final FilesRepository filesRepository;

    @Autowired
    public FileService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public String uploadFile(MultipartFile file, String description) {
        Date createdAt = new Date();
        String fileName = createdAt.getTime() + "_" + file.getOriginalFilename();

        try {
            String uploadDir = "files/images/";
            Path uploadPath = Paths.get(uploadDir);
            try {
                Files.copy(file.getInputStream(), Paths.get(uploadDir + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du fichier", e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors du traitement du fichier", e);
        }

        FileUpLoad fileUpLoad = new FileUpLoad();
        fileUpLoad.setFileName(fileName);
        fileUpLoad.setDescription(description);

        filesRepository.save(fileUpLoad);

        return fileName;
    }
}

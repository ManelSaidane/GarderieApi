package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Emploi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IEmploiService {
    ResponseEntity<String> CreateEmploi(MultipartFile file, Long groupe_id);
    ResponseEntity<byte[]> getEmploiByGroupeForGarderie(Long groupeId) throws IOException;
    ResponseEntity<byte[]> getEmploiByGroupeForResponsable(Long groupeId) throws IOException;
    ResponseEntity<byte[]> getEmploiByEnfantForParent(Long enfantId) throws IOException;
    String updateEmploi(Long emploiId, MultipartFile newFile, String newFileName);
    String deleteEmploi(Long emploiId);


}

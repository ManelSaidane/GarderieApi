package com.example.garderieapi.Service;

import com.example.garderieapi.entity.Emploi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IEmploiService {
    ResponseEntity<String> CreateEmploi(MultipartFile file, Long groupe_id);
    Emploi getEmploiByGroupeForGarderie(Long groupeId);
    Emploi getEmploiByGroupeForResponsable(Long groupeId);
    Emploi getEmploiByEnfantForParent(Long enfantId);
    String updateEmploi(Long emploiId, MultipartFile newFile, String newFileName);
    String deleteEmploi(Long emploiId);


}

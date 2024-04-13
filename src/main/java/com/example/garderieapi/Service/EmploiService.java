package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.EmploiRepository;
import com.example.garderieapi.Repository.EnfantRepository;
import com.example.garderieapi.Repository.GroupeRepository;
import com.example.garderieapi.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class EmploiService implements IEmploiService{

    private final GarderieService garderieService;
    private final ResponsableService responsableService;
    private final GroupeRepository groupeRepository;
    private final ParentService parentService;
    private final EnfantRepository enfantRepository;


    private final EmploiRepository emploiRepository;

    public EmploiService(EmploiRepository emploiRepository, GarderieService garderieService, ResponsableService responsableService, GroupeRepository groupeRepository, ParentService parentService, EnfantRepository enfantRepository) {
        this.emploiRepository = emploiRepository;
        this.garderieService = garderieService;
        this.responsableService = responsableService;
        this.groupeRepository = groupeRepository;
        this.parentService = parentService;
        this.enfantRepository = enfantRepository;
    }
//------------------------ Create Emploi ----------------------------------
    @Override
    public ResponseEntity<String> CreateEmploi(MultipartFile file, Long groupe_id) {
        Garderie garderie= garderieService.GarderieConnectee();
        if (garderie == null) return new  ResponseEntity<>("! accès interdit", HttpStatus.BAD_REQUEST);

        if (file.isEmpty())return new  ResponseEntity<>("! Vérifier la saisie de fichier",HttpStatus.BAD_REQUEST);

        if (!(file.getOriginalFilename().toLowerCase().endsWith(".jpg") ||
                !file.getOriginalFilename().toLowerCase().endsWith(".jpeg") ||
                !file.getOriginalFilename().toLowerCase().endsWith(".png") ||
                !file.getOriginalFilename().toLowerCase().endsWith(".pdf")))
        {
            return new  ResponseEntity<>("Vérifier type de vote ficher",HttpStatus.BAD_REQUEST);
        }
        Groupe groupe =groupeRepository.findByIdAndGarderie(groupe_id,garderie)
                .orElseThrow(()
                        -> new RuntimeException("! Erreur lors groupe introuvable"));


        String fileName = System.currentTimeMillis() + "_"+groupe.getId() +"_"+ file.getOriginalFilename();

        try {
            String uploadDir = "files/Emploi/";
            //Path uploadPath = Paths.get(uploadDir);
            try {
                Files.copy(file.getInputStream(), Paths.get(uploadDir + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du fichier", e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors du traitement du fichier", e);
        }

        Emploi emploiUpLoad = new Emploi();
        emploiUpLoad.setFileName(fileName);
        emploiUpLoad.setGroupe(groupe);

        emploiRepository.save(emploiUpLoad);

        return  new  ResponseEntity<>("Emploi du temps a été ajouté",HttpStatus.CREATED);
    }



    //------------------------ Get Emploi by groupe id for garderie ----------------------------------

    @Override
    public Emploi getEmploiByGroupeForGarderie(Long groupeId) {
        Garderie garderie= garderieService.GarderieConnectee();
        if (garderie == null) throw new IllegalArgumentException("! accès interdit");
        Optional<Groupe> groupe=groupeRepository.findByIdAndGarderie(groupeId,garderie);
        if (groupe.isPresent()){
            Optional<Emploi> emploi=emploiRepository.findByGroupe(groupe.get());
            if (emploi.isPresent()){
                return emploi.get();
            }
        }else throw new IllegalArgumentException("! Groupe introuvable");
        return null;
    }



    //------------------------ Get Emploi by groupe id for responsable ----------------------------------

    @Override
    public Emploi getEmploiByGroupeForResponsable(Long groupeId) {
        User responsable = responsableService.ResponsableConnectee();
        if (responsable == null) throw new IllegalArgumentException("! accès interdit");
        Optional<Groupe> groupe=groupeRepository.findById(groupeId);
        if (groupe.isPresent()){
            if (groupe.get().getResponsables().contains(responsable)){
                Optional<Emploi> emploi=emploiRepository.findByGroupe(groupe.get());
                if (emploi.isPresent()){
                    return emploi.get();
                }
            }else throw new IllegalArgumentException("! accès interdit");
        }else throw new IllegalArgumentException("! Groupe introuvable");
        return null;
    }

    //------------------------ Get Emploi by enfant id for parent ----------------------------------

    @Override
    public Emploi getEmploiByEnfantForParent(Long enfantId) {
        User parent = parentService.ParentConnectee();
        if (parent == null) throw new IllegalArgumentException("! accès interdit");
        Optional<Enfant> enfant=enfantRepository.findById(enfantId);
        if (enfant.isPresent()){
            if (parent.getEnfants().contains(enfant.get())){
                Optional<Emploi> emploi= emploiRepository.findByGroupe(enfant.get().getGroupe());
                if (emploi.isPresent()) return emploi.get();
            }else throw new IllegalArgumentException("! Enfant introuvable");

        }else throw new IllegalArgumentException("! Parent introuvable");
        return null;
    }


    //------------------------ Update Emploi ----------------------------------
    @Override
    public String updateEmploi(Long emploiId, MultipartFile newFile, String newFileName) {
        Garderie garderie= garderieService.GarderieConnectee();
        if (garderie == null) return"! accès interdit";


        Emploi existingEmploi = emploiRepository.findById(emploiId)
                .orElseThrow(() -> new RuntimeException("Emploi introuvable"));

        List<Groupe> groupeGarderie=groupeRepository.findByGarderie(garderie);
        if (!groupeGarderie.contains(existingEmploi.getGroupe())) return "Groupe introuvable";

        if (!newFile.isEmpty()) {
            String uploadDir = "files/Emploi/";
            String fileName = System.currentTimeMillis() + "_"+existingEmploi.getGroupe().getId() +"_"+ newFile.getOriginalFilename();
            try {
                Files.copy(newFile.getInputStream(), Paths.get(uploadDir + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la copie du nouveau fichier", e);
            }

            // Met à jour le nom du fichier dans l'entité Emploi
            existingEmploi.setFileName(fileName);
            existingEmploi.setGroupe(existingEmploi.getGroupe());
        }
        emploiRepository.save(existingEmploi);
        return "Emploi mis à jour avec succès";
    }
    //------------------------ Delete Emploi ----------------------------------
    @Override
    public String deleteEmploi(Long emploiId) {
        Garderie garderie= garderieService.GarderieConnectee();
        if (garderie == null) return"! accès interdit";
        Emploi existingEmploi = emploiRepository.findById(emploiId)
                .orElseThrow(() -> new RuntimeException("Emploi introuvable"));

        List<Groupe> groupeGarderie=groupeRepository.findByGarderie(garderie);
        if (groupeGarderie.contains(existingEmploi.getGroupe())) {
            emploiRepository.delete(existingEmploi);
            return "Emploi supprimé avec succès";
        }
        return "Groupe introuvable";
    }

}
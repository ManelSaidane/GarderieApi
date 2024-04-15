package com.example.garderieapi.Service;


import com.example.garderieapi.Repository.AbsenceRepository;
import com.example.garderieapi.Repository.EnfantRepository;
import com.example.garderieapi.entity.Absence;
import com.example.garderieapi.entity.Enfant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService implements IAbsenceService {

    private final AbsenceRepository absenceRepository;
    private final ResponsableService responsableService;
    private final GarderieService garderieService;
    private final EnfantRepository enfantRepository;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, ResponsableService responsableService, GarderieService garderieService, EnfantRepository enfantRepository) {
        this.absenceRepository = absenceRepository;
        this.responsableService = responsableService;
        this.garderieService = garderieService;
        this.enfantRepository = enfantRepository;
    }

    //------------------------ Create Absence ----------------------------------
    @Override
    public ResponseEntity<String> createAbsence(int nbrHeures , Long enfantId) {

        if (nbrHeures<=0) {
            return new ResponseEntity<>("le nombre d'heure incorrect",HttpStatus.BAD_REQUEST);
        }

        Optional<Enfant> enfant=enfantRepository.findById(enfantId);
        if (enfant.isPresent()){
        Absence absence= new Absence();
        if(responsableService.ResponsableConnectee()!=null){
            absence.setResponsable(responsableService.ResponsableConnectee());
        } else if (garderieService.GarderieConnectee()!=null) {
            absence.setResponsable(garderieService.GarderieConnectee().getGerant());
        }else return new ResponseEntity<>("! accès interdit",HttpStatus.BAD_REQUEST);

        absence.setNbrHeures(nbrHeures);
        Date date=new Date();
       // SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        absence.setDate(date);
        absence.setEnfant(enfant.get());
        absenceRepository.save(absence);
        return new ResponseEntity<>("L'absence ajouté avec succès", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("L'enfant introuvable",HttpStatus.BAD_REQUEST);
    }

    //------------------------ Update Absence ----------------------------------
    @Override
    public ResponseEntity<String> updateAbsence(Long idAbsence, int nbrHeures) {
        if (nbrHeures<0) {
            return new ResponseEntity<>("le nombre d'heure incorrect",HttpStatus.BAD_REQUEST);
        }
        Optional<Absence> absenceExistante = absenceRepository.findById(idAbsence);

        if (absenceExistante.isPresent()){
            absenceExistante.get().setNbrHeures(nbrHeures);
            absenceRepository.save(absenceExistante.get());
            return new ResponseEntity<>("L'absence modifiée avec succès", HttpStatus.OK);
        }
        return new ResponseEntity<>("Absence introuvable",HttpStatus.BAD_REQUEST);

    }


    //------------------------ Delete Absence ----------------------------------
    @Override
    public ResponseEntity<String> deleteAbsence(Long idAbsence) {


        Optional<Absence> absence = absenceRepository.findById(idAbsence);

        if (absence.isPresent()){
            absenceRepository.deleteById(idAbsence);
            return new ResponseEntity<>("L'absence Supprimer avec succès", HttpStatus.OK);
        }

        return new ResponseEntity<>("Absence introuvable",HttpStatus.BAD_REQUEST);
    }


    //------------------------ get les Absence by Enfant ----------------------------------
    @Override
    public ResponseEntity<List<Absence>> getAbsencesByEnfantId(Long enfantId) {
        Optional<Enfant> enfant=enfantRepository.findById(enfantId);
        if (enfant.isPresent()){
            return new ResponseEntity<>(absenceRepository.findByEnfant(enfant.get()),HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }
    //------------------------ get le Nbr des Absence by Enfant ----------------------------------
    @Override
    public ResponseEntity<Long> getNombreAbsencesPourEnfant(Long enfantId) {

        Optional<Enfant> enfant=enfantRepository.findById(enfantId);
        if (enfant.isPresent()){
            return new ResponseEntity<>(absenceRepository.countByEnfant(enfant.get()),HttpStatus.FOUND);
        }

        return new ResponseEntity<>(0L,HttpStatus.NOT_FOUND);
    }

    //------------------------ get la Somme Heures des Absence by Enfant ----------------------------------
    @Override
    public ResponseEntity<Long> getSommeHeuresAbsencePourEnfant(Long enfantId) {
        Optional<Enfant> enfant=enfantRepository.findById(enfantId);
        if (enfant.isPresent()){
            return new ResponseEntity<>(absenceRepository.sumNbrHeuresByEnfantId(enfantId),HttpStatus.FOUND);
        }
        return new ResponseEntity<>(0L,HttpStatus.NOT_FOUND);
    }






}

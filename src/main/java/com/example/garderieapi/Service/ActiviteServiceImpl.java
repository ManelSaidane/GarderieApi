package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.ActiviteRepository;
import com.example.garderieapi.dto.ActiviteDto;
import com.example.garderieapi.entity.Activite;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ActiviteServiceImpl  {

        List<ActiviteDto> getAllActivites();

        ActiviteDto getActiviteById(Long id);

        String createActivite(ActiviteDto activiteDto);

        String updateActivite(Long id, ActiviteDto activiteDto);

        String deleteActivite(Long id);

    }


package com.example.garderieapi.Service;

import com.example.garderieapi.Repository.FournitureRepository;
import com.example.garderieapi.entity.Fourniture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournitureService {
    @Autowired
    private FournitureRepository fournitureRepository;

    public List<Fourniture> getAllFournitures() {
        return fournitureRepository.findAll();
    }

    public Fourniture addFourniture(Fourniture fourniture) {
        return fournitureRepository.save(fourniture);
    }

    public void removeFourniture(Long id) {
        fournitureRepository.deleteById(id);
    }
}


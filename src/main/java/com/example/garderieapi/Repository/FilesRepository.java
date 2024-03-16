package com.example.garderieapi.Repository;

import com.example.garderieapi.entity.FileUpLoad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<FileUpLoad,Long> {
}

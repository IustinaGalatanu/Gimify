package com.example.Gymify.repository;

import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.ExerciseCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseCatalogRepository extends JpaRepository<ExerciseCatalog,Long> {
    List<ExerciseCatalog> findByTypeIgnoreCase(String type);
}

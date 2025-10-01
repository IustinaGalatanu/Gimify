package com.example.Gymify.repository;

import com.example.Gymify.model.ExerciseCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExerciseCatalogRepository extends JpaRepository<ExerciseCatalog,Long> {

    List<ExerciseCatalog> findByTypeIgnoreCase(String type);
}

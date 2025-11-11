package com.example.bankapi.repository;

import com.example.bankapi.model.Libro;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Override @NonNull
    Optional<Libro> findById(@NonNull Long id);
    List<Libro> findByTitoloContainingIgnoreCase(String titolo);
}

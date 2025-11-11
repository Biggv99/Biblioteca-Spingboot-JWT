package com.example.bankapi.repository;

import com.example.bankapi.model.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface PrestitoRepository extends JpaRepository<Prestito, Long> {

    @Override @NonNull
    Optional<Prestito> findById(@NonNull Long id);
}

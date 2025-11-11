package com.example.bankapi.repository;

import com.example.bankapi.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    List<Utente> findByNomeContainingIgnoreCase(String nome);
}

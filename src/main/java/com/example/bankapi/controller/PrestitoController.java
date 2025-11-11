package com.example.bankapi.controller;

import com.example.bankapi.GUI.Messaggi.Messaggio;
import com.example.bankapi.dto.LibroRequest;
import com.example.bankapi.dto.PrestitoRequest;
import com.example.bankapi.model.Prestito;
import com.example.bankapi.repository.PrestitoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/prestito")
public class PrestitoController implements Ruolo{

    private final PrestitoRepository prestitoRepository;

    public PrestitoController(PrestitoRepository prestitoRepository) {
        this.prestitoRepository = prestitoRepository;
    }

    @GetMapping("/view")
    public List<Prestito> view() {
        return prestitoRepository.findAll();
    }

    @PostMapping("/aggiungi")
    public String register(@RequestBody PrestitoRequest request) {

        Prestito prestito = new Prestito();
        prestito.setLibro(request.getLibro());
        prestito.setUtente(request.getUtente());
        prestito.setDataInizio(request.getDataInizio());
        prestito.setDataFine(request.getDataFine());
        prestito.setRestituito(request.getRestituito());

        prestitoRepository.save(prestito);
        return "Prestito creato!";
    }

    @DeleteMapping("/admin/delete")
    public String delete(@RequestBody LibroRequest request) {

        if (!isAdmin()) {
            new Messaggio("Accesso negato, permessi insufficienti");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Accesso negato: permessi insufficienti");
        }

        Prestito prestito = prestitoRepository.findById(request.getId())
                .orElseThrow(() -> {
                    new Messaggio("Prestito non trovato");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestito non trovato");
                });

        prestitoRepository.delete(prestito);
        return "Prestito eliminato con successo";
    }

    @PutMapping("/modifica")
    public String modificaLibro(@RequestBody PrestitoRequest request) {

        Prestito prestito = prestitoRepository.findById(request.getId())
                .orElseThrow(() -> {
                    new Messaggio("Prestito non trovato");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestito non trovato");
                });

        prestito.setRestituito(request.getRestituito());

        prestitoRepository.save(prestito);
        return "Libro aggiornato con successo!";
    }
}

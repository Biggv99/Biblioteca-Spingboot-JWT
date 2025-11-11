package com.example.bankapi.controller;

import com.example.bankapi.GUI.Messaggi.Messaggio;
import com.example.bankapi.dto.LibroRequest;
import com.example.bankapi.model.Libro;
import com.example.bankapi.repository.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/libro")
public class LibroController implements Ruolo{

    private final LibroRepository libroRepository;

    public LibroController(LibroRepository libroRepository) {

        this.libroRepository = libroRepository;
    }

    @GetMapping("/view")
    public List<Libro> view() {

        return libroRepository.findAll();
    }

    @PostMapping("/admin/aggiungi")
    public String register(@RequestBody LibroRequest request) {

        if (!libroRepository.findByTitoloContainingIgnoreCase(request.getTitolo()).isEmpty()) {
            new Messaggio("Libro già presente");
            return "Libro già presente";
        }

        if (!isAdmin()) {
            new Messaggio("Accesso negato, permessi insufficienti");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Accesso negato: permessi insufficienti");
        }

        Libro libro = new Libro();
        libro.setTitolo(request.getTitolo());
        libro.setAutore(request.getAutore());
        libro.setGenere(request.getGenere());
        libro.setAnno(request.getAnno());
        libro.setNumeroCopie(request.getNumeroCopie());

        libroRepository.save(libro);
        return "Nuovo libro aggiunto!";
    }

    @PutMapping("/admin/modifica")
    public String modificaLibro(@RequestBody LibroRequest request) {

        if (!isAdmin()) {
            new Messaggio("Accesso negato, permessi insufficienti");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Accesso negato: permessi insufficienti");
        }

        Libro libro = libroRepository.findById(request.getId())
                .orElseThrow(() -> {
                    new Messaggio("Libro non trovato");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro non trovato");
                });

        libro.setTitolo(request.getTitolo());
        libro.setAutore(request.getAutore());
        libro.setGenere(request.getGenere());
        libro.setAnno(request.getAnno());
        libro.setNumeroCopie(request.getNumeroCopie());

        libroRepository.save(libro);
        return "Libro aggiornato con successo!";
    }

    @DeleteMapping("/admin/delete")
    public String delete(@RequestBody LibroRequest request) {

        if (!isAdmin()) {
            new Messaggio("Accesso negato, permessi insufficienti");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Accesso negato: permessi insufficienti");
        }

        Libro libro = libroRepository.findById(request.getId())
                .orElseThrow(() -> {
                    new Messaggio("Libro non trovato");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro non trovato");
                });

        libroRepository.delete(libro);
        return "Utente eliminato con successo";
    }

    @PutMapping("/copie")
    public String modificaCopie(@RequestBody LibroRequest request) {

        Libro libro = libroRepository.findById(request.getId()).orElse(null);

        if (libro == null) {
            List<Libro> risultati = libroRepository.findByTitoloContainingIgnoreCase(request.getTitolo());
            if (!risultati.isEmpty()) {
                libro = risultati.get(0);
            }
        }

        if (libro == null) {
            new Messaggio("Libro non trovato");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro non trovato");
        }

        int copieAttuali = libro.getNumeroCopie();
        int incremento = request.getNumeroCopie();
        libro.setNumeroCopie(copieAttuali + incremento);

        libroRepository.save(libro);

        return "Numero copie aggiornato!";
    }

}

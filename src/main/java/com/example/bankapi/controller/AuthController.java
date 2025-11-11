package com.example.bankapi.controller;

import com.example.bankapi.GUI.Messaggi.Messaggio;
import com.example.bankapi.config.SessioneUtente;
import com.example.bankapi.dto.AuthRequest;
import com.example.bankapi.model.Utente;
import com.example.bankapi.repository.UtenteRepository;
import com.example.bankapi.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements Ruolo{

    private final UtenteRepository utenteRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UtenteRepository utenteRepository, JwtTokenUtil jwtTokenUtil) {
        this.utenteRepository = utenteRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/admin/view")
    public List<Utente> view() {

        if (!isAdmin()) {
            new Messaggio("Accesso negato, permessi insufficienti");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Accesso negato: permessi insufficienti");
        }

        return utenteRepository.findAll();
    }


    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {

        if (!utenteRepository.findByNomeContainingIgnoreCase(request.getNome()).isEmpty()) {
            new Messaggio("Errore: utente già registrato");
            return "Utente già registrato";
        }

        Utente nuovo = new Utente();
        nuovo.setNome(request.getNome());
        nuovo.setEmail(request.getEmail());
        nuovo.setPassword(passwordEncoder.encode(request.getPassword()));
        nuovo.setPermessi(request.getPermessi());

        utenteRepository.save(nuovo);
        return "Registrazione avvenuta";
    }


    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {

        Utente utente = utenteRepository.findByNomeContainingIgnoreCase(request.getNome())
                .stream()
                .filter(u -> u.getNome().equals(request.getNome()))
                .findFirst()
                .orElseThrow(() -> {
                    new Messaggio("Utente non trovato");
                    return new RuntimeException("Utente non trovato");
                });

        if (passwordEncoder.matches(request.getPassword(), utente.getPassword())) {
            String token = jwtTokenUtil.generateToken(utente.getNome(), utente.getPermessi());
            SessioneUtente.getInstance().setSessione(utente.getNome(), utente.getPermessi(), utente.getEmail(), token);
            return token;
        } else {
            new Messaggio("Password errata");
            throw new RuntimeException("Password errata");
        }
    }

    @DeleteMapping("/admin/delete")
    public String delete(@RequestBody AuthRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        String currentRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        if (!currentRole.equals("Admin")) {
            new Messaggio("Accesso negato, permessi insufficienti");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Accesso negato: permessi insufficienti");
        }

        if (currentUsername.equals(request.getNome())) {
            new Messaggio("Non puoi elimare te stesso");
            return "Non puoi eliminare te stesso";
        }

        Utente utente = utenteRepository.findByNomeContainingIgnoreCase(request.getNome())
                .stream()
                .filter(u -> u.getNome().equals(request.getNome()))
                .findFirst()
                .orElseThrow(() -> {
                    new Messaggio("Utente non trovato");
                    return new RuntimeException("Utente non trovato");
                });

        utenteRepository.delete(utente);
        return "Utente eliminato con successo";
    }
}

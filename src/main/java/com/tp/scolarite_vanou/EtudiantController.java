package com.tp.scolarite_vanou;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantRepository repository;

    public EtudiantController(EtudiantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Etudiant> lister() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> trouver(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Etudiant creer(@RequestBody Etudiant etudiant) {
        return repository.save(etudiant);
    }
}// TP3

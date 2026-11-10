package com.tp.scolarite_vanou;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class EtudiantControllerTest {

    @Mock EtudiantRepository repository;
    @InjectMocks EtudiantController controller;

    @Test
    void listerRetourneLesEtudiants() {
        when(repository.findAll())
            .thenReturn(List.of(new Etudiant("Mballa", "Jean", "jean@mail.cm")));

        List<Etudiant> resultat = controller.lister();

        assertEquals(1, resultat.size());
        assertEquals("Nkana", resultat.get(0).getNom());
    }

    @Test
    void creerEnregistreLEtudiant() {
        Etudiant etudiant = new Etudiant("Ngono", "Marie", "marie@mail.cm", "ASIC");
        when(repository.save(etudiant)).thenReturn(etudiant);

        Etudiant resultat = controller.creer(etudiant);

        assertEquals("Ngono", resultat.getNom());
        assertEquals("ASIC", resultat.getFiliere());
        verify(repository).save(etudiant);
    }

    @Test
    void trouverRetourneLEtudiantQuandIlExiste() {
        Etudiant etudiant = new Etudiant("Mballa", "Jean", "jean@mail.cm", "ASIC");
        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));

        ResponseEntity<Etudiant> reponse = controller.trouver(1L);

        assertEquals(200, reponse.getStatusCode().value());
        assertEquals("Mballa", reponse.getBody().getNom());
    }

    @Test
    void trouverRetourne404QuandIdInconnu() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Etudiant> reponse = controller.trouver(99L);

        assertEquals(404, reponse.getStatusCode().value());
    }
}
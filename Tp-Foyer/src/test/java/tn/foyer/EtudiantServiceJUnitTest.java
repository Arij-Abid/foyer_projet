package tn.foyer;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.foyer.entities.Etudiant;
import tn.foyer.repositories.EtudiantRepository;
import tn.foyer.services.service.EtudiantService;

import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class EtudiantServiceJUnitTest {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Test
    @Order(1)
    void addEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Alice");
        etudiant.setPrenomEtudiant("Smith");
        etudiant.setCinEtudiant(654321);
        etudiant.setDateNaissance(new Date());

        Etudiant savedEtudiant = etudiantService.addEtudiants(etudiant);
        assertNotNull(savedEtudiant);
        assertEquals("Alice", savedEtudiant.getNomEtudiant());
        System.out.println("Add Etudiant: Ok");
    }

    @Test
    @Order(2)
    void retrieveAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();
        assertNotNull(etudiants);
        assertFalse(etudiants.isEmpty());
        System.out.println("Retrieve All Etudiants: Ok");
    }

    @Test
    @Order(3)
    void retrieveEtudiant() {
        // Ensure an Etudiant with ID 1 exists before running the test
        Etudiant etudiant = etudiantService.retrieveEtudiant(1L); 
        assertNotNull(etudiant);
        assertEquals("Alice", etudiant.getNomEtudiant());
        System.out.println("Retrieve Etudiant: Ok");
    }

    @Test
    @Order(4)
    void removeEtudiant() {
        // Ensure an Etudiant with ID 1 exists before running the test
        etudiantService.removeEtudiant(1L);
        assertThrows(EntityNotFoundException.class, () -> etudiantService.retrieveEtudiant(1L));
        System.out.println("Remove Etudiant: Ok");
    }

    @Test
    @Order(5)
    void findByReservationsAnneeUniversitaire() {
        List<Etudiant> etudiants = etudiantService.findByReservationsAnneeUniversitaire();
        assertNotNull(etudiants);
        assertFalse(etudiants.isEmpty(), "Expected non-empty list of Etudiants with reservations");
        System.out.println("Find By Reservations Annee Universitaire: Ok");
    }

    @Test
    @Order(6)
    void modifyEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setCinEtudiant(654321); // Use a valid CIN for the Etudiant
        etudiant.setNomEtudiant("Updated Name");

        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);
        assertNotNull(updatedEtudiant);
        assertEquals("Updated Name", updatedEtudiant.getNomEtudiant());
        System.out.println("Modify Etudiant: Ok");
    }
}

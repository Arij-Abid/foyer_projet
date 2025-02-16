package tn.foyer.services.interfaces;


import tn.foyer.entities.Etudiant;
import java.util.List;

public interface IEtudiantService {
    List<Etudiant> retrieveAllEtudiants();

    Etudiant addEtudiants(Etudiant etudiant);

    Etudiant updateEtudiant (Etudiant e);
    Etudiant retrieveEtudiant(long  idEtudiant);
    void removeEtudiant(long idEtudiant);
    List<Etudiant> findByReservationsAnneeUniversitaire();
}

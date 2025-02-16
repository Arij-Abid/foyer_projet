package tn.foyer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.foyer.entities.Etudiant;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EtudiantRepository extends CrudRepository<Etudiant,Long> {
List<Etudiant> findByReservationsAnneeUniversitaire(LocalDate date);
Etudiant findByCinEtudiant(long cin);
}

package tn.foyer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.foyer.entities.Reservation;
import tn.foyer.entities.Universite;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UniversiteRepository extends CrudRepository<Universite,Long> {
    Universite findByNomUniversiteLike(String nomu);

   // List<Chambre> findByNomUniversiteLikeAndFoyerBlocsChambres(String nom);

    List<Reservation>
    findByFoyerBlocsChambresReservationsAnneeUniversitaireAndNomUniversite(
            LocalDate anneeUniversite,
                                                                                             String nomUniversite);

}

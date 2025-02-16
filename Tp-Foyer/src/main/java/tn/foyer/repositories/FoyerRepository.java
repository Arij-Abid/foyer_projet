package tn.foyer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.foyer.entities.Foyer;

@Repository
public interface FoyerRepository extends CrudRepository<Foyer,Long> {
    Foyer findByUniversiteIdUniversite(Long idUniversite);
}

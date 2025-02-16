package tn.foyer.services.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.foyer.entities.Foyer;
import tn.foyer.entities.Universite;
import tn.foyer.repositories.FoyerRepository;
import tn.foyer.repositories.UniversiteRepository;
import tn.foyer.services.interfaces.IUniversiteService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversiteService implements IUniversiteService {
    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;
    @Override
    public List<Universite> retrieveAllUniversities() {
        return (List<Universite>) universiteRepository.findAll();
    }

    @Override
    public Universite addUniversity(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversity(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversity(long idUniversity) {
        return universiteRepository.findById(idUniversity).orElse(null);
    }


    @Override
    public Universite desaffecterFoyerAUniversite( long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        universite.setFoyer(null);
        return  universiteRepository.save(universite);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        Universite universite = universiteRepository.findByNomUniversiteLike(nomUniversite);
        universite.setFoyer(foyer);
        return universiteRepository.save(universite);

    }
}

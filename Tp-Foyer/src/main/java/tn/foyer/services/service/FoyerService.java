package tn.foyer.services.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.foyer.entities.Bloc;
import tn.foyer.entities.Foyer;
import tn.foyer.entities.Universite;
import tn.foyer.repositories.BlocRepository;
import tn.foyer.repositories.FoyerRepository;
import tn.foyer.repositories.UniversiteRepository;
import tn.foyer.services.interfaces.IFoyerService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoyerService implements IFoyerService {
    FoyerRepository foyerRepository;
    BlocRepository blocRepository;
    UniversiteRepository universiteRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        return (List<Foyer>) foyerRepository.findAll();
    }

    @Override
    public Foyer addFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer updateFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer retrieveFoyer(long idFoyer) {
        return foyerRepository.findById(idFoyer).orElse(null);
    }

    @Override
    public void removeFoyer(long idFoyer) {
    foyerRepository.deleteById(idFoyer);
    }

    @Transactional
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
foyerRepository.save(foyer);
for(Bloc bloc : foyer.getBlocs())
{
    bloc.setFoyer(foyer);
    blocRepository.save(bloc);
}

        assert universite != null;
        universite.setFoyer(foyer);
        universiteRepository.save(universite);

        return foyer;
    }
}

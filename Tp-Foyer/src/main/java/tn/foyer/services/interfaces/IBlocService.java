package tn.foyer.services.interfaces;


import tn.foyer.entities.Bloc;

import java.util.List;

public interface IBlocService {
    List<Bloc> retrieveBlocs();
    Bloc updateBloc (Bloc bloc);
    Bloc addBloc (Bloc bloc);
    Bloc retrieveBloc (long idBloc);
    void removeBloc (long idBloc);

    List<Bloc> findByFoyerIdFoyer(long idFoyer);
    Bloc findByChambresIdChambre(Long idChambre);


}

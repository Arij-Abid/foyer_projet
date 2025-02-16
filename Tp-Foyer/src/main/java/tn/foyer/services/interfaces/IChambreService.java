package tn.foyer.services.interfaces;


import tn.foyer.entities.Bloc;
import tn.foyer.entities.Chambre;
import tn.foyer.entities.enumerations.TypeChambre;

import java.util.List;

public interface IChambreService {
    List<Chambre> retrieveAllChambres();
    Chambre addChambre(Chambre c); // ajouter l’équipe avec son détail
    Chambre updateChambre (Chambre c);
    Chambre retrieveChambre (long idChambre);

    List<Chambre> findByTypeChambre();
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) ;
    public List<Chambre>  getChambresParNomUniversite( String nomUniversite) ;
    public List<Chambre> getChambresParBlocEtType (long idBloc, TypeChambre typeC) ;
    public List<Chambre> getChambresParBlocEtTypeJPQL (long idBloc, TypeChambre typeC) ;
    public List<Chambre>  getChambresNonReserveParNomUniversiteEtTypeChambre( String nomUniversite,TypeChambre type) ;
    public void getChambresNonReserve() ;
}

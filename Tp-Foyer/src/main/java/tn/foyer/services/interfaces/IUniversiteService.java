package tn.foyer.services.interfaces;

import tn.foyer.entities.Universite;

import java.util.List;

public interface IUniversiteService {
    List<Universite> retrieveAllUniversities();
    Universite addUniversity (Universite u);
    Universite updateUniversity (Universite u);
    Universite retrieveUniversity (long idUniversity);



    public Universite desaffecterFoyerAUniversite (  long idUniversite) ;

    public Universite affecterFoyerAUniversite (long idFoyer, String nomUniversite) ;

}

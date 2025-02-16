package tn.foyer.services.interfaces;


import tn.foyer.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {
    List<Reservation> retrieveAllReservation();
    Reservation updateReservation (Reservation res);
    Reservation retrieveReservation (String idReservation);
    public Reservation ajouterReservation (long idChambre, long cinEtudiant) ;
    public Reservation annulerReservation (long cinEtudiant) ;
    public List<Reservation>  getReservationParAnneeUniversitaireEtNomUniversite(LocalDate anneeUniversite, String nomUniversite) ;
    public List<Reservation>  getReservationParAnneeUniversitaireEtNomUniversiteKeyWord(LocalDate anneeUniversite, String nomUniversite) ;

}

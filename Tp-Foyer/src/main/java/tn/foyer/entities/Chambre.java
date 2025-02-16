package tn.foyer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.foyer.entities.enumerations.TypeChambre;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long idChambre;

    long numeroChambre;

    @Enumerated(EnumType.STRING)
    TypeChambre typeChambre;

    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    Bloc bloc;

    @ToString.Exclude
    @OneToMany
    @JsonIgnore
    private Set<Reservation> reservations ;
}

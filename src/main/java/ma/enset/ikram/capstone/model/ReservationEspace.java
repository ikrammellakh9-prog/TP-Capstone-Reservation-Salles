package ma.enset.ikram.capstone.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reservations_espaces_capstone")
public class ReservationEspace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PlageHoraire plageHoraire;

    @Column(name = "motif_evenement", nullable = false)
    private String motifEvenement;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_reservation", nullable = false)
    private EtatReservation statut = EtatReservation.CONFIRMEE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_collaborateur", nullable = false)
    private CollaborateurDemandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_espace_salle", nullable = false)
    private EspaceSalle espaceReserve;

    public ReservationEspace() {}

    public ReservationEspace(PlageHoraire plageHoraire, String motifEvenement, CollaborateurDemandeur demandeur, EspaceSalle espaceReserve) {
        this.plageHoraire = plageHoraire;
        this.motifEvenement = motifEvenement;
        this.demandeur = demandeur;
        this.espaceReserve = espaceReserve;
        this.statut = EtatReservation.CONFIRMEE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PlageHoraire getPlageHoraire() { return plageHoraire; }
    public void setPlageHoraire(PlageHoraire plageHoraire) { this.plageHoraire = plageHoraire; }
    public String getMotifEvenement() { return motifEvenement; }
    public void setMotifEvenement(String motifEvenement) { this.motifEvenement = motifEvenement; }
    public EtatReservation getStatut() { return statut; }
    public void setStatut(EtatReservation statut) { this.statut = statut; }
    public CollaborateurDemandeur getDemandeur() { return demandeur; }
    public void setDemandeur(CollaborateurDemandeur demandeur) { this.demandeur = demandeur; }
    public EspaceSalle getEspaceReserve() { return espaceReserve; }
    public void setEspaceReserve(EspaceSalle espaceReserve) { this.espaceReserve = espaceReserve; }
}
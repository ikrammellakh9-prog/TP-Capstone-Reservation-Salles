package ma.enset.ikram.capstone.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
public class PlageHoraire implements Serializable {

    @Column(name = "date_heure_debut", nullable = false)
    private LocalDateTime dateHeureDebut;

    @Column(name = "date_heure_fin", nullable = false)
    private LocalDateTime dateHeureFin;

    public PlageHoraire() {}

    public PlageHoraire(LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin) {
        if (dateHeureDebut != null && dateHeureFin != null && dateHeureFin.isBefore(dateHeureDebut)) {
            throw new IllegalArgumentException("La fin du crÃ©neau ne peut Ãªtre antÃ©rieure au dÃ©but.");
        }
        this.dateHeureDebut = dateHeureDebut;
        this.dateHeureFin = dateHeureFin;
    }

    public LocalDateTime getDateHeureDebut() { return dateHeureDebut; }
    public void setDateHeureDebut(LocalDateTime dateHeureDebut) { this.dateHeureDebut = dateHeureDebut; }
    public LocalDateTime getDateHeureFin() { return dateHeureFin; }
    public void setDateHeureFin(LocalDateTime dateHeureFin) { this.dateHeureFin = dateHeureFin; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "[" + (dateHeureDebut != null ? dateHeureDebut.format(fmt) : "") + " -> " +
               (dateHeureFin != null ? dateHeureFin.format(fmt) : "") + "]";
    }
}
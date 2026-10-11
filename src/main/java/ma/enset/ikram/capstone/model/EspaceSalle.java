package ma.enset.ikram.capstone.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "espaces_salles_capstone")
public class EspaceSalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_espace", unique = true, nullable = false, length = 30)
    private String codeEspace;

    @Column(name = "designation_salle", nullable = false, length = 100)
    private String designation;

    @Column(name = "capacite_accueil", nullable = false)
    private int capaciteAccueil;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "jointure_salle_dispositif",
            joinColumns = @JoinColumn(name = "fk_espace_salle"),
            inverseJoinColumns = @JoinColumn(name = "fk_dispositif")
    )
    private List<DispositifEquipement> equipements = new ArrayList<>();

    public EspaceSalle() {}

    public EspaceSalle(String codeEspace, String designation, int capaciteAccueil) {
        this.codeEspace = codeEspace;
        this.designation = designation;
        this.capaciteAccueil = capaciteAccueil;
    }

    public void ajouterDispositif(DispositifEquipement eq) {
        this.equipements.add(eq);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodeEspace() { return codeEspace; }
    public void setCodeEspace(String codeEspace) { this.codeEspace = codeEspace; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public int getCapaciteAccueil() { return capaciteAccueil; }
    public void setCapaciteAccueil(int capaciteAccueil) { this.capaciteAccueil = capaciteAccueil; }
    public List<DispositifEquipement> getEquipements() { return equipements; }
    public void setEquipements(List<DispositifEquipement> equipements) { this.equipements = equipements; }
}
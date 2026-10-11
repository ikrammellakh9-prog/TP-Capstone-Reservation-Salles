package ma.enset.ikram.capstone.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dispositifs_equipements")
public class DispositifEquipement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_inventaire", unique = true, nullable = false, length = 35)
    private String codeInventaire;

    @Column(name = "nom_dispositif", nullable = false, length = 120)
    private String nomDispositif;

    @Column(name = "etat_operationnel")
    private String etatOperationnel = "DISPONIBLE";

    public DispositifEquipement() {}

    public DispositifEquipement(String codeInventaire, String nomDispositif) {
        this.codeInventaire = codeInventaire;
        this.nomDispositif = nomDispositif;
        this.etatOperationnel = "DISPONIBLE";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodeInventaire() { return codeInventaire; }
    public void setCodeInventaire(String codeInventaire) { this.codeInventaire = codeInventaire; }
    public String getNomDispositif() { return nomDispositif; }
    public void setNomDispositif(String nomDispositif) { this.nomDispositif = nomDispositif; }
    public String getEtatOperationnel() { return etatOperationnel; }
    public void setEtatOperationnel(String etatOperationnel) { this.etatOperationnel = etatOperationnel; }
}
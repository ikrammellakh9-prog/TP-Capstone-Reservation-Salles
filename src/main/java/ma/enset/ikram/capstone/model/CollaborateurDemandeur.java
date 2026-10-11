package ma.enset.ikram.capstone.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "collaborateurs_demandeurs")
public class CollaborateurDemandeur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricule_collab", unique = true, nullable = false, length = 30)
    private String matricule;

    @Column(name = "nom_complet", nullable = false, length = 100)
    private String nomComplet;

    @Column(name = "email_pro", unique = true, nullable = false, length = 100)
    private String emailProfessionnel;

    @Column(name = "departement", length = 80)
    private String departement;

    public CollaborateurDemandeur() {}

    public CollaborateurDemandeur(String matricule, String nomComplet, String emailProfessionnel, String departement) {
        this.matricule = matricule;
        this.nomComplet = nomComplet;
        this.emailProfessionnel = emailProfessionnel;
        this.departement = departement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }
    public String getEmailProfessionnel() { return emailProfessionnel; }
    public void setEmailProfessionnel(String emailProfessionnel) { this.emailProfessionnel = emailProfessionnel; }
    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }
}
package ma.enset.ikram.capstone;

import ma.enset.ikram.capstone.exception.ConflitReservationException;
import ma.enset.ikram.capstone.model.*;
import ma.enset.ikram.capstone.service.GestionnaireReservationService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(">>> DEMARRAGE CAPSTONE - GESTION RESERVATIONS SALLES & CONFLITS JPA <<<");
        System.out.println("                       (IKRAM MELLAKH - ENSET)                   ");
        System.out.println("==========================================================================");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ikramCapstonePU");
        GestionnaireReservationService service = new GestionnaireReservationService(emf);

        // [1] Initialisation
        System.out.println("\n--- [1] INITIALISATION DES COLLABORATEURS, EQUIPEMENTS ET ESPACES ---");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        CollaborateurDemandeur c1 = new CollaborateurDemandeur("IKRAM-2026-01", "Ikram Mellakh", "ikrammelakh9@gmail.com", "R&D Intelligence Artificielle");
        CollaborateurDemandeur c2 = new CollaborateurDemandeur("COL-2026-02", "Hamza Alami", "h.alami@entreprise.ma", "Direction Systemes d'Information");
        em.persist(c1);
        em.persist(c2);

        DispositifEquipement eqVisio = new DispositifEquipement("DISP-VISIO-01", "Borne de Visioconference 4K Poly Studio");
        DispositifEquipement eqTableau = new DispositifEquipement("DISP-TAB-01", "Tableau Blanc Interactif 85 Pouces");
        em.persist(eqVisio);
        em.persist(eqTableau);

        EspaceSalle amphiIbnKhaldoun = new EspaceSalle("ESP-AMPHI-01", "Amphitheatre Ibn Khaldoun", 120);
        amphiIbnKhaldoun.ajouterDispositif(eqVisio);

        EspaceSalle salleAlKindi = new EspaceSalle("ESP-SALLE-02", "Salle de Conference Al Kindi", 35);
        salleAlKindi.ajouterDispositif(eqVisio);
        salleAlKindi.ajouterDispositif(eqTableau);

        em.persist(amphiIbnKhaldoun);
        em.persist(salleAlKindi);

        em.getTransaction().commit();
        Long c1Id = c1.getId();
        Long c2Id = c2.getId();
        Long salleAlKindiId = salleAlKindi.getId();
        em.close();

        System.out.println("âœ“ Collaborateurs, equipements et espaces inseres en base avec succes.");

        // [2] RÃ©servation nominale
        System.out.println("\n--- [2] CREATION D'UNE RESERVATION NOMINALE ---");
        LocalDateTime debut1 = LocalDateTime.now().plusDays(2).withHour(10).withMinute(0);
        LocalDateTime fin1 = LocalDateTime.now().plusDays(2).withHour(12).withMinute(0);
        PlageHoraire plage1 = new PlageHoraire(debut1, fin1);

        ReservationEspace res1 = service.reserverEspace(c1Id, salleAlKindiId, plage1, "Comite Strategique & Revue de Sprint IA");
        System.out.println("âœ“ Reservation confirmee avec succes : ID=" + res1.getId() + " | Salle: Al Kindi | Plage: " + plage1);

        // [3] Conflit de rÃ©servation sur crÃ©neau chevauchant
        System.out.println("\n--- [3] TEST DE DETECTION DU CONFLIT TEMPOREL ---");
        LocalDateTime debutConflit = LocalDateTime.now().plusDays(2).withHour(11).withMinute(0);
        LocalDateTime finConflit = LocalDateTime.now().plusDays(2).withHour(13).withMinute(0);
        PlageHoraire plageConflit = new PlageHoraire(debutConflit, finConflit);

        try {
            service.reserverEspace(c2Id, salleAlKindiId, plageConflit, "Reunion Direction DSI");
            System.err.println("ERREUR : Le conflit temporel n'a pas ete detecte !");
        } catch (ConflitReservationException ex) {
            System.out.println("âœ“ Conflit detecte avec succes par le service metier !");
            System.out.println("  Raison : " + ex.getMessage());
        }

        // [4] Recherche des espaces disponibles
        System.out.println("\n--- [4] RECHERCHE D'ESPACES DISPONIBLES ALTERNATIFS ---");
        List<EspaceSalle> disponibles = service.rechercherEspacesDisponibles(plageConflit, 20);
        System.out.println("Espaces disponibles pour capacite >= 20 participants :");
        for (EspaceSalle esp : disponibles) {
            System.out.println("  -> " + esp.getCodeEspace() + " (" + esp.getDesignation() + ") - Capacite: " + esp.getCapaciteAccueil() + " personnes");
        }

        // [5] Annulation
        System.out.println("\n--- [5] ANNULATION D'UNE RESERVATION ---");
        service.annulerReservation(res1.getId());
        System.out.println("âœ“ Reservation #" + res1.getId() + " annulee (Statut mis a jour a ANNULEE).");

        emf.close();
        System.out.println("\n==========================================================================");
        System.out.println(">>> PROJET CAPSTONE TERMINE AVEC SUCCES (BUILD SUCCESS) <<<");
        System.out.println("==========================================================================");
    }
}
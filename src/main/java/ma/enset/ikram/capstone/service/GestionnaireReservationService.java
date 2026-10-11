package ma.enset.ikram.capstone.service;

import ma.enset.ikram.capstone.exception.ConflitReservationException;
import ma.enset.ikram.capstone.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GestionnaireReservationService {

    private final EntityManagerFactory emf;

    public GestionnaireReservationService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ReservationEspace reserverEspace(Long collabId, Long espaceId, PlageHoraire plage, String motif) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            EspaceSalle espace = em.find(EspaceSalle.class, espaceId);
            if (espace == null) {
                throw new IllegalArgumentException("Espace salle introuvable avec ID : " + espaceId);
            }

            CollaborateurDemandeur demandeur = em.find(CollaborateurDemandeur.class, collabId);
            if (demandeur == null) {
                throw new IllegalArgumentException("Collaborateur introuvable avec ID : " + collabId);
            }

            // Detection stricte de chevauchement temporel
            String queryConflit = "SELECT count(r) FROM ReservationEspace r WHERE r.espaceReserve.id = :espaceId " +
                    "AND r.statut = :statutActif " +
                    "AND r.plageHoraire.dateHeureDebut < :fin AND r.plageHoraire.dateHeureFin > :debut";

            Long nbConflits = em.createQuery(queryConflit, Long.class)
                    .setParameter("espaceId", espaceId)
                    .setParameter("statutActif", EtatReservation.CONFIRMEE)
                    .setParameter("debut", plage.getDateHeureDebut())
                    .setParameter("fin", plage.getDateHeureFin())
                    .getSingleResult();

            if (nbConflits > 0) {
                throw new ConflitReservationException("L'espace '" + espace.getDesignation() + "' est deja reserve sur la plage " + plage);
            }

            ReservationEspace reservation = new ReservationEspace(plage, motif, demandeur, espace);
            em.persist(reservation);

            em.getTransaction().commit();
            return reservation;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public List<EspaceSalle> rechercherEspacesDisponibles(PlageHoraire plage, int capaciteMin) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT s FROM EspaceSalle s WHERE s.capaciteAccueil >= :capaciteMin AND s.id NOT IN (" +
                    "   SELECT r.espaceReserve.id FROM ReservationEspace r " +
                    "   WHERE r.statut = :statutActif " +
                    "   AND r.plageHoraire.dateHeureDebut < :fin AND r.plageHoraire.dateHeureFin > :debut" +
                    ")";

            return em.createQuery(jpql, EspaceSalle.class)
                    .setParameter("capaciteMin", capaciteMin)
                    .setParameter("statutActif", EtatReservation.CONFIRMEE)
                    .setParameter("debut", plage.getDateHeureDebut())
                    .setParameter("fin", plage.getDateHeureFin())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void annulerReservation(Long reservationId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ReservationEspace r = em.find(ReservationEspace.class, reservationId);
            if (r != null) {
                r.setStatut(EtatReservation.ANNULEE);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
# TP Capstone : SystÃ¨me Complet de Gestion & RÃ©servation de Salles (Ikram Mellakh)

Projet d'ingÃ©nierie JPA / Hibernate modÃ©lisant un systÃ¨me de gestion d'amphithÃ©Ã¢tres et laboratoires :
- EntitÃ©s `CollaborateurDemandeur`, `EspaceSalle`, `DispositifEquipement` (`@ManyToMany`)
- Composant temporel `@Embeddable` `PlageHoraire`
- DÃ©tection mathÃ©matique stricte de chevauchement temporel via JPQL
- Gestion des annulations et recherche d'espaces alternatifs disponibles (`NOT IN`)

---

## Captures d'Ã‰cran IntelliJ IDEA & Terminal CMD

### 1. Service MÃ©tier de RÃ©servation & DÃ©tection de Conflit (IntelliJ IDEA)
![Service RÃ©servation](docs/images/01-intellij-service-reservation.png)

### 2. EntitÃ© ReservationEspace & PlageHoraire Embeddable (IntelliJ IDEA)
![EntitÃ© RÃ©servation](docs/images/02-intellij-entity-reservation.png)

### 3. RÃ©sultat des Tests et ExÃ©cution (CMD / PowerShell)
![RÃ©sultat ExÃ©cution CMD](docs/images/03-cmd-test-result.png)

---

## Lancement
```bash
mvn clean compile exec:java -Dexec.mainClass=ma.enset.ikram.capstone.App
```
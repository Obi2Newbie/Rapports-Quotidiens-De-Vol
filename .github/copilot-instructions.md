# Copilot Instructions – Rapports Quotidiens De Vol

## Project Overview

French-language Java 17 application for managing daily theft reports (déclarations de vol). Police agents (`AgentPolicier`) create daily reports aggregating theft declarations submitted by victims or witnesses.

## Architecture & Domain Model

```
RapportQuotidienVol (daily report)
    └── Declaration (theft declaration)
            ├── Utilisateur (abstract: Victime | Temoin)
            ├── Vehicule / Velo / ProprieteVolee (stolen property)
            └── Lieu (location)
```

**Key relationships:**
- `Utilisateur` is abstract – always use concrete `Victime` or `Temoin`
- `Declaration` uses static counter for auto-incrementing `identifiant`
- State machine: `EtatDeclaration` enum (`EN_COURS` → `RESOLUE` → `ARCHIVEE`)
- Only `AgentPolicier` can modify declaration state via `modifierEtatDeclaration()`

## Naming Conventions (French)

| Pattern | Example |
|---------|---------|
| Classes | `RapportQuotidienVol`, `AgentPolicier`, `EtatDeclaration` |
| Methods | `creerDeclaration()`, `AjouterDeclaration()`, `getNouvellesDeclarations()` |
| Fields | `dateCreation`, `derniereModifiaction`, `numeroCNI` |

**Note:** Mixed PascalCase/camelCase exists in methods (e.g., `AjouterDeclaration`). Follow existing patterns in each file.

## Project Structure

```
main/src/
├── Main.java                    # Entry point
├── model/                       # Domain entities
│   ├── Declaration.java
│   ├── Utilisateur.java (abstract)
│   ├── Victime.java
│   ├── Temoin.java
│   ├── Lieu.java
│   ├── ProprieteVolee.java (abstract)
│   ├── Vehicule.java
│   └── Velo.java
├── enums/                       # Enumerations
│   ├── EtatDeclaration.java
│   └── Role.java
├── service/                     # Business logic
│   ├── AgentPolicier.java
│   └── RapportQuotidienVol.java
├── persistence/                 # Data persistence
│   └── JsonDataManager.java
└── observer/                    # Observer pattern
    └── Observer.java
```

## Build & Test

```bash
# Build
mvn compile

# Run tests (JUnit 5)
mvn test

# Package
mvn package
```

Source files are in `main/src/` (non-standard Maven layout).

## Key Patterns

### Creating a Declaration
```java
import model.*;
import enums.Role;

Victime victime = new Victime("CNI123", "Dupont", "Jean", "12 rue...", "0612345678");
Lieu lieu = new Lieu("12", "rue de Paris", "Paris", "75001");
Vehicule vehicule = new Vehicule("Rouge", "Peugeot", "AB-123-CD");
Declaration decl = victime.creerDedclaration(dateVol, heureVol, Role.VICTIME, vehicule, lieu);
```

### Filtering Declarations in Reports
`RapportQuotidienVol` uses Java Streams for filtering:
- `getNouvellesDeclarations()` – created after report date
- `getDeclarationsMisesAJour()` – modified after creation
- `getDeclarationsResolues()` – state is `RESOLUE`

### State Transitions
```java
import service.AgentPolicier;
import enums.EtatDeclaration;

AgentPolicier agent = new AgentPolicier("id", "pwd");
agent.modifierEtatDeclaration(declaration, EtatDeclaration.RESOLUE);
```

## Package Reference

| Package | Purpose |
|---------|---------|
| `model` | Domain entities (Declaration, Utilisateur, Victime, Temoin, Lieu, ProprieteVolee, Vehicule, Velo) |
| `enums` | Enumerations (EtatDeclaration, Role) |
| `service` | Business logic (AgentPolicier, RapportQuotidienVol) |
| `persistence` | JSON data management (JsonDataManager) |
| `observer` | Observer pattern interface |

## Data Persistence (JSON)

All data is persisted in `DATA/` folder as JSON files:
- `DATA/victimes.json` – registered victims
- `DATA/temoins.json` – registered witnesses  
- `DATA/declarations.json` – theft declarations
- `DATA/rapports.json` – daily reports
- `DATA/objetsTrouves.json` – found objects

**Persistence Pattern:**
```java
// All create/update/delete operations must save to JSON
JsonDataManager.sauvegarder("declarations", declarations);
List<Declaration> declarations = JsonDataManager.charger("declarations", Declaration.class);
```

## TODO: Features to Implement (from UML Diagrams)

### 1. Victim Features (Utilisateur/Victime)
- [ ] `consulterDeclarations()` – view own declarations
- [ ] Complete `editerDeclaration()` – currently empty in `Utilisateur.java`

### 2. Found Objects System (new module)
- [ ] Create `ObjetTrouve` class – found items registered by agents
- [ ] Create `EtatObjetTrouve` enum: `ENREGISTRE`, `RESTITUE`, `VENDU_AUX_ENCHERES`, `DETRUIT`
- [ ] `AgentPolicier.enregistrerObjetTrouve()` – register found items
- [ ] `rechercherCorrespondances()` – match found objects with theft declarations

### 3. Notification System (Observer Pattern)
`Victime` implements `Observer` interface. When `AgentPolicier` changes declaration state to `RESOLUE`, the victim is automatically notified.

```java
// Observer interface
public interface Observer {
    void notifier(String message);
}

// Victime implements Observer
public class Victime extends Utilisateur implements Observer {
    @Override
    public void notifier(String message) {
        System.out.println("Notification pour " + nom + ": " + message);
    }
}

// Declaration notifies victim on state change to RESOLUE
public void setEtat(EtatDeclaration nouvelEtat) {
    this.etat = nouvelEtat;
    if (nouvelEtat == EtatDeclaration.RESOLUE && utilisateur instanceof Observer) {
        ((Observer) utilisateur).notifier("Votre déclaration #" + identifiant + " a été résolue.");
    }
}
```

### 4. RapportQuotidienVol Enhancements
- [ ] `mettreAJourRapport()` – update existing reports
- [ ] Add constructor with `identifiant` and `dateRapport`
- [ ] Add missing getters

### 5. Inheritance Fixes
- [ ] `Vehicule` should extend `ProprieteVolee`
- [ ] `Velo` should extend `ProprieteVolee`
- [ ] Add constructors/getters to `Lieu`, `Vehicule`, `Velo`, `ProprieteVolee`

### 6. JSON Persistence Layer
- [ ] Create `JsonDataManager` utility class for read/write operations
- [ ] Modify all entity creation methods to persist data
- [ ] Add JSON serialization support (Gson or Jackson dependency)

## Known Typos (preserve for compatibility)

- `creerDedclaration` (not `creerDeclaration`)
- `derniereModifiaction` (not `derniereModification`)

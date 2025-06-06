package Entite.Personnages;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DeroulementDuDonjon.Donjon;
import Entite.*;
import Entite.Monstres.*;
import Entite.Equipement.*;
import Dice.Dice;

/**
 * Représente un personnage jouable dans le donjon
 */
public class Personnage extends Entite {
    // ===================== ATTRIBUTS =====================
    private final String m_nom;
    private final Race m_race;
    private final Classe m_classe;
    private Caracteristique m_stats;
    private List<Equipement> m_inventaire;
    private Arme m_armeEquipee;
    private Armure m_armureEquipee;

    // ===================== CONSTRUCTEUR =====================
    /**
     * Crée un nouveau personnage avec son nom, sa race et sa classe.
     * Initialise ses caractéristiques et son inventaire selon sa race et sa classe.
     * @param nom nom du personnage
     * @param race race choisie
     * @param classe classe choisie
     */
    public Personnage(String nom, Race race, Classe classe) {
        m_nom = nom;
        m_race = race;
        m_classe = classe;
        m_stats = new Caracteristique(this);

        m_inventaire = new ArrayList<>();
        switch (getClasse()) {
            case CLERC -> {
                m_inventaire.add(Armure.creerArmure(ListeEquipements.ARMURE_D_ECAILLES));
                m_inventaire.add(Arme.creerArme(ListeEquipements.MASSE_D_ARMES));
                m_inventaire.add(Arme.creerArme(ListeEquipements.ARBALETE_LEGERE));
            }
            case GUERRIER -> {
                m_inventaire.add(Armure.creerArmure(ListeEquipements.COTTE_DE_MAILLES));
                m_inventaire.add(Arme.creerArme(ListeEquipements.EPEE_LONGUE));
                m_inventaire.add(Arme.creerArme(ListeEquipements.ARBALETE_LEGERE));
            }
            case MAGICIEN -> {
                m_inventaire.add(Arme.creerArme(ListeEquipements.BATON));
                m_inventaire.add(Arme.creerArme(ListeEquipements.FRONDE));
            }
            case ROUBLARD -> {
                m_inventaire.add(Arme.creerArme(ListeEquipements.RAPIERE));
                m_inventaire.add(Arme.creerArme(ListeEquipements.ARC_COURT));
            }
        }
    }

    // ===================== METHODES =====================
    /**
     * Permet au joueur d'équiper un équipement de son inventaire
     * @param i index de l’équipement dans l’inventaire (à partir de 1)
     * @return chaîne indiquant ce qui a été équipé
     */
    public String equiper(int i) {
        if (m_inventaire.isEmpty()) {
            return "Votre inventaire est vide";
        }
        try {
            i--;
            Equipement e = m_inventaire.get(i);
            m_inventaire.remove(i);
            if (e.getTypeEquipement() == TypeEquipement.ARME) {
                if (m_armeEquipee != null)
                    m_inventaire.add(m_armeEquipee);
                m_armeEquipee = (Arme) e;
            } else if (e.getTypeEquipement() == TypeEquipement.ARMURE) {
                if (m_armureEquipee != null)
                    m_inventaire.add(m_armureEquipee);
                m_armureEquipee = (Armure) e;
            }
            return "Vous avez équipé votre " + e.getNom();
        } catch (IndexOutOfBoundsException ex) {
            return "Erreur : l’équipement sélectionné n’existe pas dans l’inventaire.";
        }
    }


    /**
     * Permet au joueur de se déplacer dans le donjon en fonction de sa vitesse
     * @param donjon le donjon dans lequel se déplace le joueur
     * @return chaîne indiquant sa nouvelle position dans le donjon
     */
    public String seDeplacer(Donjon donjon) {
        Scanner scanner = new Scanner(System.in);
        int nbCases = getStats().getVitesse() / 3;

        int xActuel = getX();
        int yActuel = getY();

        int xMin = Math.max(xActuel - nbCases, 0);
        int xMax = Math.min(xActuel + nbCases, donjon.getTailleCarte() - 1);
        int yMin = Math.max(yActuel - nbCases, 0);
        int yMax = Math.min(yActuel + nbCases, donjon.getTailleCarte() - 1);
        int xNew = -1;
        int yNew = -1;

        while (true) {
            try {
                System.out.print("\nEntrez les coordonnées X puis Y : ");
                xNew = Integer.parseInt(scanner.nextLine());
                yNew = Integer.parseInt(scanner.nextLine());

                if (xNew < xMin || xNew > xMax || yNew < yMin || yNew > yMax) {
                    System.out.println("Vous ne pouvez pas vous déplacer ici : hors de portée.");
                    continue;
                }

                if (!donjon.getCase(xNew, yNew).equals(".") || !donjon.getCase(xNew, yNew).equals("*")) {
                    System.out.println("La case est occupée, choisissez une autre.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez entrer des entiers valides.");
            }
        }

        donjon.changeCase(xActuel, yActuel, ".");
        setCoordonnees(xNew, yNew);
        donjon.placerJoueur(this, xNew, yNew);

        return ("Déplacement effectué. Nouvelle position : (" + getX() + "," + getY() + ")");
    }


    /**
     * Permet au personnage d’attaquer un monstre
     * @param cible le monstre ciblé
     * @return un message indiquant le résultat de l’attaque
     */
    public String attaquer(Monstre cible) {
        //erreur si aucune arme équipée
        //gerer les portees
        Dice de = new Dice(20);
        int jet = de.lanceDes(1);
        int bonus = m_armeEquipee.getEstDistance() ? m_stats.getDexterite() : m_stats.getForce();
        int puissance = jet + bonus;
        if (puissance > cible.getClasseArmure()) {
            Dice deDegat = new Dice(m_armeEquipee.getDegats());
            int degats = deDegat.lanceDes(1);
            cible.retirerPV(degats);
            return "Vous avez infligé " + degats + " au monstre " + cible.getEspece();
        }
        else {
            return "Vous êtes faible, vous n'avez infligé aucun dégât...";
        }
    }

    /**
     * Permet au personnage de ramasser un équipement au sol
     * @param donjon donjon dans lequel se trouve le joueur
     * @return chaîne indiquant l'équipement ramassé
     */
    public String ramasser(Donjon donjon){
        if (donjon.getCase(getX(), getY()) == "*")
        {
            // deplacer les print
            System.out.println("Vous pouvez ramasser l'arme");
        }
        return "";
    }

    /**
     * Convertit l'inventaire en chaîne pour l'affichage
     * @return chaîne listant les équipements de l’inventaire
     */
    public String inventaireToString(){
        String inventaire = "";
        if (!m_inventaire.isEmpty()) {
            for (Equipement e : m_inventaire) {
                inventaire += (m_inventaire.indexOf(e) + 1) + " - " + e.toString() + "\n";
            }
        }
        return inventaire;
    }

    /**
     * Affiche les informations du personnage : nom, race, classe et stats
     * @return une chaîne décrivant le personnage
     */
    @Override
    public String toString() {
        String infosJoueur = m_nom + " - " + m_race + " " + m_classe + "\n"
                           + m_stats.toString();
        return infosJoueur;
    }

    // ===================== GETTERS =====================

    /**
     * @return nom du personnage
     * */
    public String getNom() {
        return this.m_nom;
    }

    /**
     * @return race du personnage
     * */
    public Race getRace() {
        return this.m_race;
    }

    /**
     * @return classe du personnage
     * */
    public Classe getClasse() {
        return this.m_classe;
    }

    /**
     * @return caractéristiques du personnage
     * */
    public Caracteristique getStats() {
        return this.m_stats;
    }

    /**
     * @return arme actuellement équipée
     * */
    public Arme getArmeEquipee() {
        return m_armeEquipee;
    }

    /**
     * @return armure actuellement équipée
     * */
    public Armure getArmureEquipee() {
        return m_armureEquipee;
    }
}

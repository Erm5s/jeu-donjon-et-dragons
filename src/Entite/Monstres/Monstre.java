package Entite.Monstres;

import DeroulementDuDonjon.Donjon;
import Dice.Dice;
import Entite.Personnages.*;
import Entite.TypeEntite;
import Entite.Entite;
import affichage.Affichage;

import java.util.Scanner;

/**
 * Classe représentant un monstre dans le jeu
 */
public class Monstre extends Entite {
    // ===================== ATTRIBUTS =====================
    private TypeEntite m_typeEntite;
    private String m_espece;
    private int m_numero;
    private int m_portee;
    private String m_degats;
    private int m_PV;
    private int m_PVInitial;
    private int m_vitesse;
    private int m_force;
    private int m_dexterite;
    private int m_classeArmure;
    private int m_initiative;

    // ===================== CONSTRUCTEUR =====================
    /**
     * Initialise un monstre avec toutes ses caractéristiques.
     * @param espece nom/espèce du monstre
     * @param portee portée de l’attaque (1 = corps-à-corps, >1 = distance)
     * @param degats valeur maximale d’un dé de dégâts
     * @param PV points de vie initiaux du monstre
     * @param vitesse vitesse de déplacement
     * @param force force utilisée en combat
     * @param dexterite dextérité utilisée en combat
     * @param classeArmure valeur de défense du monstre
     * @param initiative initiative du monstre
     */
    public Monstre (String espece, int portee, String degats,
                    int PV, int vitesse, int force, int dexterite, int classeArmure, int initiative) {
        this.m_typeEntite = TypeEntite.MONSTRE;
        this.m_espece = espece;
        this.m_portee = portee;
        this.m_degats = degats;
        this.m_PVInitial = PV;
        this.m_PV = PV;
        this.m_vitesse = vitesse;
        this.m_force = force;
        this.m_dexterite = dexterite;
        this.m_classeArmure = classeArmure;
        this.m_initiative = initiative;
    }

    // ===================== MÉTHODES =====================
    /**
     * Retire un nombre de points de vie au monstre
     * @param degats nombre de points de vie à retirer
     */
    public void retirerPV(int degats) {
        m_PV -= degats;
    }

    /**
     * Effectue une attaque contre un personnage
     * L’attaque réussit si le jet d’attaque + bonus dépasse la classe d’armure de la cible
     * @param cible personnage attaqué
     * @return chaîne décrivant le résultat de l’attaque
     */
    public String attaquer(Personnage cible) {
        if (!estCibleAPortee(cible)) {
            return "Erreur : le personnage est hors de portée de votre attaque (portée max : " + m_portee + ").";
        }

        Dice de = new Dice();
        int jet = de.lancer("1d20");
        int bonus = m_portee > 1 ? m_dexterite : m_force;
        int puissance = jet + bonus;
        int armureJoueur = cible.getArmureEquipee() == null ? 0 : cible.getArmureEquipee().getClasseArmure();
        if (puissance > armureJoueur) {
            int degats = de.lancer(m_degats);
            cible.getStats().setPV(getPV() - degats);
            return "Vous avez infligé " + degats + " au joueur " + cible.getNom();
        }
        else {
            return "Vous êtes faible, votre puissance est de " + puissance + ", le personnage à une protection de " + cible.getArmureEquipee().getClasseArmure();
        }
    }

    /**
     * Méthode pour simuler un déplacement
     * @return chaîne
     */
    public String seDeplacer(Donjon donjon){
        Affichage affichage = new Affichage();
        Scanner scanner = new Scanner(System.in);
        int nbCases = getVitesse() / 3;

        int xActuel = getX();
        int yActuel = getY();

        int xMin = Math.max(xActuel - nbCases, 0);
        int xMax = Math.min(xActuel + nbCases, donjon.getTailleCarte() - 1);
        int yMin = Math.max(yActuel - nbCases, 0);
        int yMax = Math.min(yActuel + nbCases, donjon.getTailleCarte() - 1);
        int xNew = -1;
        int yNew = -1;

        try {
            affichage.mdjAfficherMessageAvecEntree("\nEntrez les coordonnées X puis Y : ");
            xNew = Integer.parseInt(scanner.nextLine());
            affichage.afficherMessage("> ");
            yNew = Integer.parseInt(scanner.nextLine());

            if (xNew < xMin || xNew > xMax || yNew < yMin || yNew > yMax) {
                return ("Erreur : Cette case est hors de portée.");
            }

            if (!donjon.getCase(xNew, yNew).equals(".") && !donjon.getCase(xNew, yNew).equals("*")) {
                return ("Erreur : Cette case est occupée, choisissez une autre.");
            }

        } catch (NumberFormatException e) {
            return ("Entrée invalide, veuillez entrer des entiers valides.");
        }

        donjon.changeCase(xActuel, yActuel, ".");
        setCoordonnees(xNew, yNew);
        donjon.placerMonstre(this, xNew, yNew);

        return ("Déplacement effectué. Nouvelle position : (" + getX() + "," + getY() + ")");
    }

    /**
     * Vérifie si la cible est à portée de l'arme équipée.
     * @param cible Le monstre ciblé
     * @return true si à portée, false sinon
     */
    public boolean estCibleAPortee(Personnage cible) {
        int distanceX = Math.abs(this.getX() - cible.getX());
        int distanceY = Math.abs(this.getY() - cible.getY());
        int distance = Math.max(distanceX, distanceY);

        return distance <= m_portee;
    }

    /**
     * Affiche une fiche descriptive du monstre avec toutes ses stats
     * @return une chaîne contenant les infos du monstre
     */
    @Override
    public String toString()
    {
        String infoMonstre =
            m_espece + " " + m_numero + " :"
            + "\n-- PDV : " + m_PV + "/" + m_PVInitial + " --"
            + "\nATQ : " + m_degats + " | POR : " + m_portee
            + "\nFOR : " + m_force + "   | DEX : " + m_dexterite
            + "\nARM : " + m_classeArmure + "   | ITV : " + m_initiative;
        return infoMonstre;
    }

    // ===================== GETTERS =====================
    /**
     * @return l'espece du monstre
     */
    public String getNom()
    {
        return this.m_espece;
    }

    /**
     * @return points de vie actuels du monstre
     */
    public int getPV() {
        return m_PV;
    }

    /**
     * @return vitesse du monstre
     */
    public int getVitesse() {
        return m_vitesse;
    }

    /**
     * @return force du monstre
     */
    public int getForce() {
        return m_force;
    }

    /**
     * @return dextérité du monstre
     */
    public int getDexterite() {
        return m_dexterite;
    }

    /**
     * @return classe d’armure du monstre
     */
    public int getClasseArmure() {
        return m_classeArmure;
    }

    /**
     * @return initiative du monstre
     */
    public int getInitiative() {
        return m_initiative;
    }

    /**
     * @return type de l'entité (MONSTRE)
     */
    public TypeEntite getTypeEntite() {
        return m_typeEntite;
    }
}

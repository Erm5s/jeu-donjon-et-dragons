package Entite.Monstres;

import Dice.Dice;
import Entite.Personnages.*;

/**
 * Classe représentant un monstre dans le jeu
 */
public class Monstre extends Entite.Entite{
    // ===================== ATTRIBUTS =====================
    private String m_espece;
    private int m_numero;
    private int m_portee;
    private int m_degats;
    private int m_nbLance;
    private int m_PV;
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
     * @param nbLance nombre de dés lancés pour infliger des dégâts
     * @param PV points de vie initiaux du monstre
     * @param force force utilisée en combat
     * @param dexterite dextérité utilisée en combat
     * @param classeArmure valeur de défense du monstre
     * @param initiative initiative du monstre
     */
    public Monstre (String espece, int portee, int degats, int nbLance,
                    int PV, int force, int dexterite, int classeArmure, int initiative) {
        this.m_espece = espece;
        this.m_portee = portee;
        this.m_degats = degats;
        this.m_nbLance = nbLance;
        this.m_PV = PV;
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
        //gerer les portees
        Dice de = new Dice(20);
        int jet = de.lanceDes(1);
        int bonus = m_portee == 1 ? m_dexterite : m_force;
        int puissance = jet + bonus;

        if (puissance > cible.getArmureEquipee().getClasseArmure()) {
            Dice deDegat = new Dice(m_degats);
            int degats = deDegat.lanceDes(m_nbLance);
            cible.getStats().retirerPV(degats);
            return "Vous avez infligé " + degats + " au joueur " + cible.getNom();
        } else {
            return "Vous êtes faible, vous n'avez infligé aucun dégât...";

        }
    }

    /**
     * Méthode pour simuler un déplacement
     * @return chaîne
     */
    public String seDeplacer(){
        return "On codera plus tard";
    }

    /**
     * Affiche une fiche descriptive du monstre avec toutes ses stats
     * @return une chaîne contenant les infos du monstre
     */
    @Override
    public String toString()
    {
        String infoMonstre = m_espece + " " + m_numero + " :"
                + "\n ATQ : " + m_nbLance + "d" + m_degats + "portée : " + m_portee
                + "\n PDV : " + m_PV
                + "\n FOR : " + m_force
                + "\n DEX : " + m_dexterite
                + "\n ARM : " + m_classeArmure
                + "\n ITV : " + m_initiative;
        return infoMonstre;
    }

    // ===================== GETTERS =====================
    /**
     * @return nom du monstre
     */
    public String getNom()
    {
        return this.m_espece;
    }

    /**
     * @return espèce du monstre
     */
    public String getEspece() {
        return m_espece;
    }

    /**
     * @return points de vie actuels du monstre
     */
    public int getPV() {
        return m_PV;
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
}

package Entite.Personnages;
import Dice.Dice;
import Entite.Monstres.Monstre;

/**
 * Classe représentant les caractéristiques d’un personnage (PV, force, vitesse...)
 */
public class Caracteristique {
    // ===================== ATTRIBUTS =====================
    private int m_PV;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_initiative;

    // ===================== CONSTRUCTEUR =====================
    /**
     * Initialise les caractéristiques d’un personnage en fonction de sa classe et de sa race.
     * Utilise des jets de dés pour générer les caractéristiques de base, puis les ajuste selon la race.
     * @param personnage personnage auquel les caractéristiques sont associées
     */
    public Caracteristique (Personnage personnage)
    {
        // Attribution force, dexterite, vitesse, initiative (commune à chaque instance de Personnage)
        Dice de = new Dice(4);
        m_force = de.lanceDes(4) + 3;
        m_dexterite = de.lanceDes(4) + 3;
        m_vitesse = de.lanceDes(4) + 3;
        m_initiative = de.lanceDes(4) + 3;

        // Attribution de points de vie selon la classe
        switch (personnage.getClasse()) { // ajouter un case en cas de nouvelle classe
            case GUERRIER -> m_PV = 20;
            case CLERC -> m_PV = 16;
            case MAGICIEN -> m_PV = 12;
            case ROUBLARD -> m_PV = 16;
        }

        // Ajustement de stats selon la race
        switch (personnage.getRace()){ // ajouter un case en cas de nouvelle race
            case HUMAIN ->
            {
                m_PV += 2;
                m_force += 2;
                m_dexterite += 2;
                m_vitesse += 2;
                m_initiative += 2;
            }
            case NAIN -> m_force += 6;
            case ELFE ->  m_dexterite += 6;
            case HALFELIN ->
            {
                m_dexterite += 4;
                m_vitesse += 2;
            }
        }
    }

    // ===================== MÉTHODES =====================
    /**
     * Ajuste la vitesse et la force du personnage en fonction du poids de son équipement.
     * Une arme ou une armure lourde réduit la vitesse. Une arme lourde augmente la force.
     * @param personnage personnage dont les caractéristiques sont ajustées
     */
    public void statsPoidsEquipement (Personnage personnage){
        if(personnage.getArmeEquipee().getEstLourde()){
            m_vitesse -= 2;
            m_force += 4;
        }
        if(personnage.getArmureEquipee().getEstLourde()){
            m_vitesse -= 4;
        }
    }

    /**
     * Retire des points de vie au personnage
     * @param degats nombre de points de vie à retirer
     */
    public void retirerPV(int degats) {
        m_PV -= degats;
    }

    /**
     * Affiche une fiche des caractéristiques du personnage
     * @return chaîne contenant les caractéristiques du personnage
     */
    @Override
    public String toString() {
        String infosStats =
            "----- PDV : " + m_PV + "-----"+
            "\nFOR : " + m_force + " | DEX : " + m_dexterite +
            "\nVIT : " + m_vitesse + " | ITV : " + m_initiative;
        return infosStats;
    }

    // ===================== GETTERS =====================
    /**
     * @return points de vie restants
     */
    public int getPV() {
        return m_PV;
    }

    /**
     * @return force du personnage
     */
    public int getForce() {
        return m_force;
    }

    /**
     * @return dextérité du personnage
     */
    public int getDexterite() {
        return m_dexterite;
    }

    /**
     * @return vitesse du personnage
     */
    public int getVitesse() {
        return m_vitesse;
    }

    /**
     * @return initiative du personnage
     */
    public int getInitiative() {
        return m_initiative;
    }
}

package Entite.Personnages;
import Dice.Dice;
import Entite.Monstres.Monstre;

public class Caracteristique {
    private int m_PV;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_initiative;

    public Caracteristique (Personnage personnage)
    {
        // Attribution point de vie selon la classe
        switch (personnage.getClasse()) { // ajouter un case en cas de nouvelle classe
            case GUERRIER -> m_PV = 20;
            case CLERC -> m_PV = 16;
            case MAGICIEN -> m_PV = 12;
            case ROUBLARD -> m_PV = 16;
        }

        // Attribution force, dexterite, vitesse, initiative (commune à chaque type de Personnage)
        Dice de = new Dice(4);
        m_force = de.lanceDes(4) + 3;
        m_dexterite = de.lanceDes(4) + 3;
        m_vitesse = de.lanceDes(4) + 3;
        m_initiative = de.lanceDes(4) + 3;

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
    public void statsPoidEquipement (Personnage personnage){
        if(personnage.getArmeEquipee().getEstLourde()){
            m_vitesse -= 2;
            m_force += 4;
        }
        if(personnage.getArmureEquipee().getEstLourde()){
            m_vitesse -= 4;
        }
    }

    // GETTERS
    public void retirerPV(int degats) {
        m_PV -= degats;
    }
    public int getPV() {
        return m_PV;
    }

    public int getForce() {
        return m_force;
    }

    public int getDexterite() {
        return m_dexterite;
    }

    public int getVitesse() {
        return m_vitesse;
    }

    public int getInitiative() {
        return m_initiative;
    }
}

package Personnages;
import Dice.Dice;

public class Caracteristique {
    private int m_pointsDeVie;
    private int m_force;
    private int m_dexterite;
    private int m_vitesse;
    private int m_initiative;
    private int m_classeArmure;

    public Caracteristique (Personnage personnage)
    {
        // Attribution point de vie selon la classe
        switch (personnage.getClasse()) { // ajouter un case en cas de nouvelle classe
            case GUERRIER -> m_pointsDeVie = 20;
            case CLERC -> m_pointsDeVie = 16;
            case MAGICIEN -> m_pointsDeVie = 12;
            case ROUBLARD -> m_pointsDeVie = 16;
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
                m_pointsDeVie += 2;
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

    public Caracteristique (Monstre monstre)
    {
        if (monstre.getEstCac()) {
            m_dexterite = 0;
        }
        else {
            m_force = 0;
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

    public int getPV() {
        return m_pointsDeVie;
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

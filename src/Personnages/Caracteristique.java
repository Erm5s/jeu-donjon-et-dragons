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
        switch (personnage.getClasse()) {
            case GUERRIER -> m_pointsDeVie = 20;
            case CLERC -> m_pointsDeVie = 16;
            case MAGICIEN -> m_pointsDeVie = 12;
            case ROUBLARD -> m_pointsDeVie = 16;
        }

        Dice de = new Dice(4);
        m_force = de.lanceDes(1) + 3;
        m_dexterite = de.lanceDes(1) + 3;
        m_vitesse = de.lanceDes(1) + 3;
        m_initiative = de.lanceDes(1) + 3;


        switch (personnage.getRace()){
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

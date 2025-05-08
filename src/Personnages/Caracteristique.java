package Personnages;
import Personnages.*;

public class Caracteristique {
    private int m_pointsDeVie = 0;
    private int m_force = 0;
    private int m_dexterite = 0;
    private int m_vitesse = 0;
    private int m_initiative = 0;
    private int m_classeArmure = 0;

    public Caracteristique (Personnage personnage)
    {
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

        switch (personnage.getClasse()) {
            case GUERRIER -> m_pointsDeVie += 20;
            case CLERC -> m_pointsDeVie += 16;
            case MAGICIEN -> m_pointsDeVie += 12;
            case ROUBLARD -> m_pointsDeVie += 16;
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
}

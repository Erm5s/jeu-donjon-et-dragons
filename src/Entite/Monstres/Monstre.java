package Entite.Monstres;

import Entite.Personnages.Caracteristique;

public class Monstre {
    private String m_espece;
    private int m_numero;
    private Attaque m_attaque;
    private int m_PV;
    private int m_force;
    private int m_dexterite;
    private int m_classeArmure;
    private int m_initiative;

    public Monstre (String espece, int numero, int portee, String degats,
                    int PV, int force, int dexterite, int classeArmure, int initiative) {
        this.m_espece = espece;
        this.m_numero = numero;
        this.m_attaque = new Attaque(portee, degats);
        this.m_PV = PV;
        this.m_force = force;
        this.m_dexterite = dexterite;
        this.m_classeArmure = classeArmure;
        this.m_initiative = initiative;
    }

    // METHODES

    public void retirerPV(int degats) {
        m_PV -= degats;
    }

    public String attaquer(){
        return "On codera plus tard";
    }
    public String seDeplacer(){
        return "On codera plus tard";
    }


    // GETTERS

    public String getEspece() {
        return m_espece;
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

    public int getClasseArmure() {
        return m_classeArmure;
    }

    public int getInitiative() {
        return m_initiative;
    }
}

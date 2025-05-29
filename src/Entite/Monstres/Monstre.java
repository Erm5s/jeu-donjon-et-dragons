package Entite.Monstres;

import Dice.Dice;
import Entite.Personnages.*;
import Entite.Equipement.*;

public class Monstre {
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

    public Monstre (String espece, int numero, int portee, int degats, int nbLance,
                    int PV, int force, int dexterite, int classeArmure, int initiative) {
        this.m_espece = espece;
        this.m_numero = numero;
        this.m_portee = portee;
        this.m_degats = degats;
        this.m_nbLance = nbLance;
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

    public String attaquer(Personnage cible) {
        //erreur si aucune arme équipée
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
}

package Entite.Personnages;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DeroulementDuDonjon.Donjon;
import Entite.*;
import Entite.Monstres.*;
import Entite.Equipement.*;
import Dice.Dice;


public class Personnage extends Entite {
    private final String m_nom;
    private final Race m_race;
    private final Classe m_classe;
    private int m_scoreTour;
    private Caracteristique m_stats;
    private List<Equipement> m_inventaire;
    private Arme m_armeEquipee;
    private Armure m_armureEquipee;

    public Personnage(String nom, Race race, Classe classe) {
        m_nom = nom;
        m_race = race;
        m_classe = classe;
        m_stats = new Caracteristique(this);

        m_inventaire = new ArrayList<>();
        switch (getClasse()) {
            case CLERC -> {
                m_inventaire.add(Armure.creerArmure(1));
                m_inventaire.add(Arme.creerArme(5));
                m_inventaire.add(Arme.creerArme(7));
            }
            case GUERRIER -> {
                m_inventaire.add(Armure.creerArmure(3));
                m_inventaire.add(Arme.creerArme(1));
                m_inventaire.add(Arme.creerArme(7));
            }
            case MAGICIEN -> {
                m_inventaire.add(Arme.creerArme(4));
                m_inventaire.add(Arme.creerArme(8));
            }
            case ROUBLARD -> {
                m_inventaire.add(Arme.creerArme(6));
                m_inventaire.add(Arme.creerArme(9));
            }
        }

        Dice deTour = new Dice(20);
        m_scoreTour = m_stats.getInitiative() + deTour.lanceDes(1);
    }


    // METHODES
    public String equiper(int i) {
        i--;
        Equipement e = m_inventaire.get(i);
        if (m_inventaire.get(i).getTypeEquipement() == TypeEquipement.ARME) {
            m_inventaire.remove(i);
            m_inventaire.add(m_armeEquipee);
            m_armeEquipee = (Arme) e;
        } else if (m_inventaire.get(i).getTypeEquipement() == TypeEquipement.ARMURE) {
            m_inventaire.remove(i);
            m_inventaire.add(m_armureEquipee);
            m_armureEquipee = (Armure) e;
        }
        return "Vous avez équipé votre " + e.getNom();
    }

    public String seDeplacer(Donjon donjon){
        // gérer les différents erreurs de déplacement, déplacer les system.out.println
        int nbCase = getStats().getVitesse()/3;
        String dialog = "";
        dialog += ("\nVous êtes en " + getX() + ";" + getY());
        dialog += ("\nVous pouvez vous déplacer de " + nbCase + " cases");

        int Xmin = Math.max(getX() - nbCase, 0);
        int Xmax = Math.min(getX() + nbCase, 25);
        int Ymin = Math.max(getY() - nbCase, 0);
        int Ymax = Math.min(getY() + nbCase, 25);

        dialog += "\nVous pouvez vous déplacer entre x[" + Xmin + "," + Xmax + "] et y[" + Ymin + "," + Ymax +"]";
        System.out.println(dialog);
        int newX = -1;
        int newY = -1;


        while (newX<Xmin || newX>Xmax || newY<Ymin || newY>Ymax ||
                !donjon.getCase(newX,newY).equals(".") || !donjon.getCase(newX,newY).equals("*"))
        {
            System.out.println("\n\nTapez les coordonnées X puis Y pour vous déplacer");
            Scanner scanner = new Scanner(System.in);
            newX = Integer.parseInt(scanner.nextLine());
            newY =  Integer.parseInt(scanner.nextLine());
            if(newX<Xmin || newX>Xmax || newY<Ymin || newY>Ymax)
            {
                System.out.println("\nVous ne pouvez pas vous déplacer ici, trop loin");
            }
        }
        setCoordonnees(newX, newY);
        System.out.println("Vous êtes en " + getX() + ";" + getY());
        return null;
    }

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

    public String ramasser(){
        return
                "";
    }



    // GETTERS

    public String getNom() {
        return this.m_nom;
    }

    public Race getRace() {
        return this.m_race;
    }

    public Classe getClasse() {
        return this.m_classe;
    }

    public Caracteristique getStats() {
        return this.m_stats;
    }

    public Arme getArmeEquipee() {
        return m_armeEquipee;
    }

    public Armure getArmureEquipee() {
        return m_armureEquipee;
    }

    public int getScoreTour() {
        return m_scoreTour;
    }


    // AFFICHAGE A DEPLACER DANS UNE FONCTION AFFICHAGE
    public void afficheStats() {
        String stats = "";
        stats += "PV         : " + m_stats.getPV();
        stats += "\nForce      : " + m_stats.getForce();
        stats += "\nDextérité  : " + m_stats.getDexterite();
        stats += "\nVitesse    : " + m_stats.getVitesse();
        stats += "\nInitiative : " + m_stats.getInitiative();
        System.out.println(stats);
    }

    public void afficheInventaire() {
        for (Equipement e : m_inventaire)
        {
            if (e!=null){
                System.out.println((m_inventaire.indexOf(e)+1) + " - " + e.toString());
            }
        }
    }
}

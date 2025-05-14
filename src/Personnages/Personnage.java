package Personnages;

import java.util.ArrayList;
import java.util.List;
import Equipement.*;


public class Personnage {
    private final String m_nom;
    private final Race m_race;
    private final Classe m_classe;
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
    }

    public String getNom() {
        return this.m_nom;
    }

    public Race getRace() {
        return this.m_race;
    }

    public Classe getClasse() {
        return this.m_classe;
    }

    public String afficheStats() {
        String stats = "";
        stats += "PV           : " + m_stats.getPV();
        stats += "\nForce      : " + m_stats.getDexterite();
        stats += "\nVitesse    : " + m_stats.getVitesse();
        stats += "\nInitiative : " + m_stats.getInitiative();
        return stats;
    }

    public void afficheInventaire() {
        for (Equipement e : m_inventaire)
        {
            if (e!=null)
            System.out.println(e.getNom());
        }
    }
}

package entite.personnages.sorts;


import entite.monstres.Monstre;
import entite.personnages.Personnage;
import deroulementDuDonjon.*;

import java.util.List;

public abstract class Sort {
    private String m_nom;

    public Sort(String nom) {
        m_nom = nom;
    }

    public String getNom() {
        return m_nom;
    }

    public abstract String lancer(Personnage lanceur, Donjon donjon, List<Personnage> persos, List<Monstre> monstres);
}

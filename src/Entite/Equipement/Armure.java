package Entite.Equipement;

import java.util.Hashtable;

public class Armure extends Equipement
{
    private String m_nom;
    private int m_classe;
    private boolean m_estLourde;

    public Armure(String nom, int classe, boolean estLourde) {
        m_nom = nom;
        m_classe = classe;
        m_estLourde = estLourde;
    }

    public static final Hashtable<Integer, Armure> listeArmures = new Hashtable<>();
    static {
        listeArmures.put(1, new Armure("armure d'écailles", 9, false));
        listeArmures.put(2, new Armure("demi-plate", 10, false));
        listeArmures.put(3, new Armure("cotte de mailles", 11, true));
        listeArmures.put(4, new Armure("harnois", 12, true));
    }

    // METHODES
    public static Armure creerArmure(Integer id) {
        return listeArmures.get(id);
    }


    // GETTERS

    public String getNom() {
        return m_nom;
    }

    public boolean getEstLourde() {
        return m_estLourde;
    }

    public int getClasse() {
        return m_classe;
    }

}

package Entite.Equipement;

import java.util.Hashtable;

public class Arme extends Equipement
{
    private String m_nom;
    private int m_degats;
    private int m_portee;
    private boolean m_estLourde;
    private boolean m_estDistance;

    public Arme(String nom, int degats, int portee, boolean estLourde) {
        m_nom = nom;
        m_degats = degats;
        m_portee = portee;
        m_estLourde = estLourde;
        m_estDistance = (m_portee > 1);
    }

    private static final Hashtable<Integer, Arme> listeArmes = new Hashtable<>();
    static {
        listeArmes.put(1, new Arme("épée longue", 8, 1, true));
        listeArmes.put(2, new Arme("dague", 4, 1, false));
        listeArmes.put(3, new Arme("arc", 6, 16, false));
        listeArmes.put(4, new Arme("bâton", 6, 1, false));
        listeArmes.put(5, new Arme("masse d'armes", 8, 1, false));
        listeArmes.put(6, new Arme("rapière", 8, 1, true));
        listeArmes.put(7, new Arme("arbalète légère", 8, 16, false));
        listeArmes.put(8, new Arme("fronde", 4, 6, false));
        listeArmes.put(9, new Arme("arc court", 6, 16, false));
    }

    // METHODES

    public static Arme creerArme(Integer id) {
        return listeArmes.get(id);
    }


    // GETTERS

    public String getNom() {
        return m_nom;
    }

    public int getDegats() {
        return m_degats;
    }

    public int getPortee() {
        return m_portee;
    }

    public boolean getEstLourde() {
        return m_estLourde;
    }

    public boolean getEstDistance() {
        return m_estDistance;
    }
}

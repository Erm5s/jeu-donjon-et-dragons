package Entite.Equipement;

import java.util.Hashtable;

public class Arme extends Equipement
{
    private String m_nom;
    private TypeEquipement m_typeEquipement;
    private int m_degats;
    private int m_portee;
    private boolean m_estLourde;
    private boolean m_estDistance;

    public Arme(String nom, int degats, int portee, boolean estLourde) {
        m_typeEquipement = TypeEquipement.ARME;
        m_nom = nom;
        m_degats = degats;
        m_portee = portee;
        m_estLourde = estLourde;
        m_estDistance = (m_portee > 1);
    }

    private static final Hashtable<ListeEquipements, Arme> listeArmes = new Hashtable<>();
    static {
        listeArmes.put(ListeEquipements.BATON, new Arme("bâton", 6, 1, false));
        listeArmes.put(ListeEquipements.MASSE_D_ARMES, new Arme("masse d'armes", 6, 1, false));
        listeArmes.put(ListeEquipements.EPEE_LONGUE, new Arme("épée longue", 8, 1, true));
        listeArmes.put(ListeEquipements.RAPIERE, new Arme("rapière", 8, 1, true));
        listeArmes.put(ListeEquipements.ARBALETE_LEGERE, new Arme("arbalète légère", 8, 16, false));
        listeArmes.put(ListeEquipements.FRONDE, new Arme("fronde", 4, 6, false));
        listeArmes.put(ListeEquipements.ARC_COURT, new Arme("arc court", 6, 16, false));
    }

    // METHODES

    public static Arme creerArme(ListeEquipements equipement) {
        return listeArmes.get(equipement);
    }


    // GETTERS

    public String getNom() {
        return m_nom;
    }

    public TypeEquipement getTypeEquipement() {
        return m_typeEquipement;
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

    @Override
    public String toString() {
        return "Arme   : " + m_nom +
                "\t[Degats:1d" + m_degats +
                ", Portee:" + m_portee +
                ", " +(m_estLourde?"Lourde]":"Legere]");
    }
}

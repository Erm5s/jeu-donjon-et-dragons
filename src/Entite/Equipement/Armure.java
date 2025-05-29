package Entite.Equipement;

import java.util.Hashtable;

public class Armure extends Equipement
{
    private String m_nom;
    private TypeEquipement m_typeEquipement;
    private int m_classe;
    private boolean m_estLourde;

    public Armure(String nom, int classe, boolean estLourde) {
        m_typeEquipement = TypeEquipement.ARMURE;
        m_nom = nom;
        m_classe = classe;
        m_estLourde = estLourde;
    }

    public static final Hashtable<ListeEquipements, Armure> listeArmures = new Hashtable<>();
    static {
        listeArmures.put(ListeEquipements.ARMURE_D_ECAILLES, new Armure("armure d'écailles", 9, false));
        listeArmures.put(ListeEquipements.DEMI_PLATE, new Armure("demi-plate", 10, false));
        listeArmures.put(ListeEquipements.COTTE_DE_MAILLES, new Armure("cotte de mailles", 11, true));
        listeArmures.put(ListeEquipements.HARNOIS, new Armure("harnois", 12, true));
    }

    // METHODES
    public static Armure creerArmure(ListeEquipements equipement) {
        return listeArmures.get(equipement);
    }


    // GETTERS

    public String getNom(){
        return m_nom;
    }

    public TypeEquipement getTypeEquipement() {
        return m_typeEquipement;
    }

    public boolean getEstLourde() {
        return m_estLourde;
    }

    public int getClasseArmure() {
        return m_classe;
    }

    @Override
    public String toString() {
        return "Armure : " + m_nom +
                "\t[Classe:" + m_classe +
                ", " +(m_estLourde?"Lourde]":"Legere]");
    }

}

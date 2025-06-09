package Entite.Equipement;

import Entite.TypeEntite;

import java.util.HashMap;
/**
 * Classe représentant une armure dans le jeu
 */
public class Armure extends Equipement
{
    // ===================== ATTRIBUTS =====================
    private String m_nom;
    private TypeEntite m_typeEntite;
    private int m_classe;
    private boolean m_estLourde;

    // ===================== CONSTRUCTEUR =====================
    /**
     * Crée une armure avec un nom, une classe d’armure et une propriété lourde ou non
     * @param nom nom de l’armure
     * @param classe classe d’armure, un entier positif représentant la défense
     * @param estLourde indique si l’armure est lourde (true) ou légère (false)
     */
    public Armure(String nom, int classe, boolean estLourde) {
        m_typeEntite = TypeEntite.ARMURE;
        m_nom = nom;
        m_classe = classe;
        m_estLourde = estLourde;
    }

    // ===================== DONNÉES STATIQUES =====================
    /**
     * Liste statique contenant toutes les armures disponibles dans le jeu,
     * associées à une clé de type ListeEquipements
     */
    private static final HashMap<ListeEquipements, Object[]> listeArmures = new HashMap<>();
    static {
        listeArmures.put(ListeEquipements.ARMURE_D_ECAILLES, new Object[] { "armure d'écailles", 9, false });
        listeArmures.put(ListeEquipements.DEMI_PLATE, new Object[] { "demi-plate", 10, false });
        listeArmures.put(ListeEquipements.COTTE_DE_MAILLES, new Object[] { "cotte de mailles", 11, true });
        listeArmures.put(ListeEquipements.HARNOIS, new Object[] { "harnois", 12, true });
    }

    // ===================== METHODES =====================
    /**
     * Crée une armure à partir d’une valeur de l’énumération ListeEquipements
     * @param equipement clé correspondant à l’armure souhaitée
     * @return armure correspondante, ou null si elle n’existe pas dans la map
     */
    public static Armure creerArmure(ListeEquipements equipement) {
        Object[] stats = listeArmures.get(equipement);
        return new Armure((String) stats[0], (int) stats[1], (boolean) stats[2]);
    }

    /**
     * Affiche une représentation textuelle de l’armure avec son nom, sa classe et son poids
     * @return chaîne décrivant l’armure
     */
    @Override
    public String toString() {
        return "Armure : " + m_nom +
                " (Classe:" + m_classe +
                ", " +(m_estLourde?"Lourde)":"Legere)");
    }

    // ===================== GETTERS =====================
    /**
     * @return nom de l’armure
     */
    public String getNom(){
        return m_nom;
    }

    /**
     * @return type d’équipement (ici toujours ARMURE)
     */
    public TypeEntite getTypeEntite() {
        return m_typeEntite;
    }

    /**
     * @return true si l’armure est lourde, false si elle est légère
     */
    public boolean getEstLourde() {
        return m_estLourde;
    }

    /**
     * @return classe d’armure (valeur de défense)
     */
    public int getClasseArmure() {
        return m_classe;
    }

    /**
     * @return la liste des armures possibles à créer
     */
    public static HashMap<ListeEquipements, Object[]> getListeArmures() {
        return listeArmures;
    }
}

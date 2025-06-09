package entite.equipements;

import entite.TypeEntite;

import java.util.HashMap;

/**
 * Classe représentant une arme dans le jeu
 */
public class Arme extends Equipement
{
    // ===================== ATTRIBUTS =====================
    private String m_nom;
    private TypeEntite m_typeEntite;
    private String m_degats;
    private int m_bonusDegats;
    private int m_portee;
    private boolean m_estLourde;
    private boolean m_estDistance;

    // ===================== CONSTRUCTEUR =====================
    /**
     * Crée une arme avec un nom, des dégâts, une portée et une propriété lourde ou non,
     * c'est une arme de distance si la portée > 18
     * @param nom nom de l’arme
     * @param degats nombre de points de dégâts infligés
     * @param portee portée de l’arme (≥ 1)
     * @param estLourde indique si l’arme est lourde (true) ou légère (false)
     */
    public Arme(String nom, String degats, int portee, boolean estLourde) {
        m_typeEntite = TypeEntite.ARME;
        m_nom = nom;
        m_degats = degats;
        m_portee = portee;
        m_estLourde = estLourde;
        m_estDistance = (m_portee > 1);
        m_bonusDegats = 0;
    }

    // ===================== DONNÉES STATIQUES =====================
    /**
     * Liste statique contenant toutes les armes disponibles dans le jeu,
     * associées à une clé de type ListeEquipements
     */
    private static final HashMap<ListeEquipements, Object[]> listeArmes = new HashMap<>();
    static {
        listeArmes.put(ListeEquipements.BATON, new Object[]{"bâton", "1d6", 1, false});
        listeArmes.put(ListeEquipements.MASSE_D_ARMES, new Object[]{"masse d'armes", "1d6", 1, false});
        listeArmes.put(ListeEquipements.EPEE_LONGUE, new Object[]{"épée longue", "1d8", 1, true});
        listeArmes.put(ListeEquipements.RAPIERE, new Object[]{"rapière", "1d8", 1, true});
        listeArmes.put(ListeEquipements.ARBALETE_LEGERE, new Object[]{"arbalète légère", "1d8", 16, false});
        listeArmes.put(ListeEquipements.FRONDE, new Object[]{"fronde", "1d4", 6, false});
        listeArmes.put(ListeEquipements.ARC_COURT, new Object[]{"arc court", "1d6", 16, false});
        listeArmes.put(ListeEquipements.EPEE_A_DEUX_MAINS, new Object[]{"épée à deux mains", "2d6", 1, true});
    }

    // ===================== METHODES =====================
    /**
     * Crée une arme à partir d’une valeur de l’énumération ListeEquipements
     * @param equipement clé correspondant à l’arme souhaitée
     * @return arme correspondante, ou null si elle n’existe pas dans la map
     */
    public static Arme creerArme(ListeEquipements equipement) {
        Object[] stats = listeArmes.get(equipement);
        return new Arme((String) stats[0], (String) stats[1], (int) stats[2], (boolean) stats[3]);
    }

    /**
     * Améliore les dégâts de l'arme avec le sort ArmeMagique
     */
    public void ameliorer() {
        m_bonusDegats++;
    }

    /**
     * Affiche une représentation textuelle de l’arme avec son nom, ses dégâts, sa portée et son type (lourde/légère)
     * @return chaîne décrivant l’arme
     */
    @Override
    public String toString() {
        return "Arme   : " + m_nom +
                " (Degats: " + m_degats +
                ", Portee:" + m_portee +
                ", " +(m_estLourde?"Lourde)":"Legere)");
    }

    // ===================== GETTERS =====================
    /**
     * @return nom de l’arme
     */
    public String getNom() {
        return m_nom;
    }

    /**
     * @return type d’équipement (ici toujours ARME)
     */
    public TypeEntite getTypeEntite() {
        return m_typeEntite;
    }

    /**
     * @return nombre de dégâts infligés par l’arme
     */
    public String getDegats() {
        return m_degats;
    }

    /**
     * @return bonus de dégâts de l'arme
     */
    public int getBonusDegats() {
        return m_bonusDegats;
    }

    /**
     * @return portée de l’arme
     */
    public int getPortee() {
        return m_portee;
    }

    /**
     * @return true si l’arme est lourde, false si elle est légère
     */
    public boolean getEstLourde() {
        return m_estLourde;
    }

    /**
     * @return true si l’arme est à distance (portée > 1), false sinon
     */
    public boolean getEstDistance() {
        return m_estDistance;
    }

    /**
     * @return la liste des armes possibles à créer
     */
    public static HashMap<ListeEquipements, Object[]> getListeArmes() {
        return listeArmes;
    }
}

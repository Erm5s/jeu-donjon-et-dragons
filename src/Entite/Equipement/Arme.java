package Entite.Equipement;

import java.util.HashMap;

/**
 * Classe représentant une arme dans le jeu
 */
public class Arme extends Equipement
{
    // ===================== ATTRIBUTS =====================
    private String m_nom;
    private TypeEquipement m_typeEquipement;
    private int m_degats;
    private int m_portee;
    private boolean m_estLourde;
    private boolean m_estDistance;

    // ===================== CONSTRUCTEUR =====================
    /**
     * Crée une arme avec un nom, des dégâts, une portée et une propriété lourde ou non,
     * c'est une arme de distance si la portée > 1
     * @param nom nom de l’arme
     * @param degats nombre de points de dégâts infligés
     * @param portee portée de l’arme (≥ 1)
     * @param estLourde indique si l’arme est lourde (true) ou légère (false)
     */
    public Arme(String nom, int degats, int portee, boolean estLourde) {
        m_typeEquipement = TypeEquipement.ARME;
        m_nom = nom;
        m_degats = degats;
        m_portee = portee;
        m_estLourde = estLourde;
        m_estDistance = (m_portee > 1);
    }

    // ===================== DONNÉES STATIQUES =====================
    /**
     * Liste statique contenant toutes les armes disponibles dans le jeu,
     * associées à une clé de type ListeEquipements
     */
    private static final HashMap<ListeEquipements, Arme> listeArmes = new HashMap<>();
    static {
        listeArmes.put(ListeEquipements.BATON, new Arme("bâton", 6, 1, false));
        listeArmes.put(ListeEquipements.MASSE_D_ARMES, new Arme("masse d'armes", 6, 1, false));
        listeArmes.put(ListeEquipements.EPEE_LONGUE, new Arme("épée longue", 8, 1, true));
        listeArmes.put(ListeEquipements.RAPIERE, new Arme("rapière", 8, 1, true));
        listeArmes.put(ListeEquipements.ARBALETE_LEGERE, new Arme("arbalète légère", 8, 16, false));
        listeArmes.put(ListeEquipements.FRONDE, new Arme("fronde", 4, 6, false));
        listeArmes.put(ListeEquipements.ARC_COURT, new Arme("arc court", 6, 16, false));
    }

    // ===================== METHODES =====================
    /**
     * Crée une arme à partir d’une valeur de l’énumération ListeEquipements
     * @param equipement clé correspondant à l’arme souhaitée
     * @return arme correspondante, ou null si elle n’existe pas dans la map
     */
    public static Arme creerArme(ListeEquipements equipement) {
        return listeArmes.get(equipement);
    }

    /**
     * Affiche une représentation textuelle de l’arme avec son nom, ses dégâts, sa portée et son type (lourde/légère)
     * @return chaîne décrivant l’arme
     */
    @Override
    public String toString() {
        return "Arme   : " + m_nom +
                "\t[Degats:1d" + m_degats +
                ", Portee:" + m_portee +
                ", " +(m_estLourde?"Lourde]":"Legere]");
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
    public TypeEquipement getTypeEquipement() {
        return m_typeEquipement;
    }

    /**
     * @return nombre de dégâts infligés par l’arme
     */
    public int getDegats() {
        return m_degats;
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
}

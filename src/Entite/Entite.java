package Entite;

/**
 * Classe abstraite représentant une entité positionnée dans le donjon (joueur, monstre, etc.).
 * Contient les coordonnées X et Y de l'entité.
 */
public abstract class Entite
{
    private String m_nom;
    private int m_x;
    private int m_y;

    public abstract TypeEntite getTypeEntite();
    /**
     * Retourne les coordonnées de l'entité sous forme de chaîne
     * @return une chaîne du type "x: X, y: Y"
     */
    public String getCoordonnees()
    {
        String coord = "x: " + this.m_x + ", y: " + this.m_y;
        return coord;
    }

    /**
     * Modifie les coordonnées de l'entité
     * @param x nouvelle valeur de la coordonnée X
     * @param y nouvelle valeur de la coordonnée Y
     */
    public void setCoordonnees(int x, int y)
    {
        this.m_x = x;
        this.m_y = y;
    }

    /**
     * @return la coordonnée X de l'entité
     */
    public int getX(){
        return m_x;
    }

    /**
     * @return la coordonnée Y de l'entité
     */
    public int getY() {
        return m_y;
    }

    public abstract String getNom();
}

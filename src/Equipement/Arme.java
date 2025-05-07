package Equipement;

public class Arme
{
    private boolean m_estADistance;
    private boolean m_estCourante;
    private int m_portee;
    private int m_degats;
    private String m_nom;

    public Arme(String nom,int portee, int degats, boolean estCourante, boolean estAdistance)
    {
        this.m_estADistance = estAdistance;
        this.m_estCourante = estCourante;
        this.m_portee = portee;
        this.m_degats = degats;
        this.m_nom = nom;
    }

    public int getDegats(Arme arme)
    {
        return arme.m_degats;
    }

    public int getPortee(Arme arme)
    {
        return arme.m_portee;
    }

    public boolean getType(Arme arme)
    {
        return arme.m_estCourante;
    }

    public boolean getEstADistance(Arme arme)
    {
        return arme.m_estADistance;
    }
}

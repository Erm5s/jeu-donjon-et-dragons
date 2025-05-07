package Equipement;

public class Armure
{
    private int m_classe;
    private boolean m_estLourde;
    private String m_nom;
    public Armure(int classe, boolean estLourde, String nom)
    {
        this.m_classe = classe;
        this.m_estLourde = estLourde;
        this.m_nom = nom;
    }

    public boolean getType(Armure armure)
    {
        return armure.m_estLourde;
    }

    public int getClasse(Armure armure)
    {
        return armure.m_classe;
    }

}

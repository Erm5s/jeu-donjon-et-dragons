package Entite;

public abstract class Entite
{
    private int m_x;
    private int m_y;

    public String getCoordonnees()
    {
        String coord = "x: " + this.m_x + ", y: " + this.m_y;
        return coord;
    }
    public void setCoordonnees(int x, int y)
    {
        this.m_x = x;
        this.m_y = y;
    }
}

package Personnages;
import Dice.Dice;

public class Attaque {
    private int m_portee;
    private Dice m_degats;

    public Attaque (int portee,Dice degats){
        m_portee = portee;
        m_degats = degats;
    }
}

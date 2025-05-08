package Dice;

import java.util.Random;

public class Dice
{
    private int m_nbfaces;
    private int m_nbDes;
    private Random m_rand;

    public Dice(int nbFaces)
    {
        this.m_nbfaces = nbFaces;
        this.m_rand = new Random();
    }

    public int random(int nbFaces)
    {
        return this.m_rand.nextInt(nbFaces);
    }

    public int lanceDes(int nbLances)
    {
        int resultat=0;
        for(int i = 0; i < nbLances; i++)
        {
            resultat+= this.random(this.m_nbfaces)+1;
        }
        return resultat;
    }

}

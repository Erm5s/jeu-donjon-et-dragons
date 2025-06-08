package Dice;
import java.util.Random;

public class Dice
{
    private Random m_rand;

    public Dice() {
        this.m_rand = new Random();
    }

    public int lanceDes(int nbLances, int nbFaces) {
        int resultat = 0;
        for (int i = 0; i < nbLances; i++) {
            resultat += m_rand.nextInt(nbFaces) + 1;
        }
        return resultat;
    }

    // Lance un dé avec notation "XdY" (ex : "2d6")
    public int lancer(String XdY) {
        try {
            String[] partie = XdY.split("d");
            int nbLances = Integer.parseInt(partie[0]);
            int nbFaces = Integer.parseInt(partie[1]);
            return lanceDes(nbLances, nbFaces);
        } catch (Exception e) {
            System.out.println("Erreur : notation de dés invalide (" + XdY + ")");
            return 0;
        }
    }
}

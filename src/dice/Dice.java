package dice;
import java.util.Random;

/**
 * Classe utilitaire représentant un système de dés.
 */
public class Dice
{
    // ===================== ATTRIBUT =====================
    private Random m_rand;

    // ===================== CONSTRUCTEUR =====================
    public Dice() {
        this.m_rand = new Random();
    }

    // ===================== MÉTHODES =====================
    /**
     * Lance un nombre de dés donnés avec un certain nombre de faces.
     * @param nbLances nombre de dés à lancer
     * @param nbFaces nombre de faces sur chaque dé
     * @return la somme des résultats de tous les dés lancés
     */
    public int lanceDes(int nbLances, int nbFaces) {
        int resultat = 0;
        for (int i = 0; i < nbLances; i++) {
            resultat += m_rand.nextInt(nbFaces) + 1;
        }
        return resultat;
    }

    /**
     * Lance des dés selon une notation de type "XdY" (ex: "2d6" pour deux dés à 6 faces).
     * @param XdY chaîne représentant le type de jet de dé
     * @return résultat du lancer ou 0 si erreur de format
     */
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

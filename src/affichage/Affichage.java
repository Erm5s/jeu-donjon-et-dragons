package affichage;

import DeroulementDuDonjon.Donjon;
import Entite.Entite;
import Entite.Equipement.Equipement;
import Entite.Monstres.Monstre;
import Entite.Personnages.Personnage;
import Entite.TypeEntite;
import MeneurDeJeu.MeneurDeJeu;


import java.util.Scanner;

/**
 * Classe gérant tous les affichages textuels du jeu Donjons & Dragons.
 * Utilise des couleurs ANSI pour différencier les types de messages.
 */
public class Affichage
{
    // ===================== COULEURS ANSI =====================
    private final String blanc = "\u001B[0m";
    private final String jaune = "\u001B[33m";
    private final String rouge = "\u001B[31m";
    private final String vert = "\u001B[32m";
    private final String bleu ="\u001B[34m";
    private final String cyan = "\u001B[36m";
    private final String violet = "\u001B[35m";

    // ===================== MÉTHODES D'AFFICHAGE =====================
    /**
     * Affiche l’introduction et le logo du jeu.
     */
    public void introduction()
    {
        String logo = violet + "╔══════════════════════════════════════╗" +
                "\n║     DOONJONS & DRAGONS - SAE P21     ║" +
                "\n╚══════════════════════════════════════╝";
        System.out.println(logo);

        System.out.println("\nBienvenue MDJ, accueillez les visiteurs comme il se doit");
    }

    /**
     * Affiche toutes les infos d’un personnage (statistiques, inventaire, équipement).
     * @param p Le personnage à afficher.
     */
    public void afficherInfoPersonnage(Personnage p)
    {
        System.out.println(vert + p.toString() + blanc);
        afficherInventaire(p);
        afficherEquipements(p);
    }

    /**
     * Affiche l’inventaire d’un personnage.
     * @param p Le personnage concerné.
     */
    public void afficherInventaire(Personnage p)
    {
        int i = 0;
        System.out.println("\nInventaire :");
        System.out.print(vert);
        if (!p.getInventaire().isEmpty())
            for (Equipement e : p.getInventaire()) {
                i++;
                System.out.println("- [" + i + "] " + e.toString());
            }
        else
            System.out.println("vide");
        System.out.print(blanc);
    }

    /**
     * Affiche les équipements portés par un personnage.
     * @param p Le personnage concerné.
     */
    public void afficherEquipements(Personnage p)
    {
        System.out.println("\nÉquipés :");
        if (p.getArmeEquipee() == null)
            System.out.println(vert + "- Aucune arme équipée");
        else
            System.out.println(vert + "- " + p.getArmeEquipee().toString());

        if (p.getArmureEquipee() == null)
            System.out.println(vert + "- Aucune armure équipée");
        else
            System.out.println(vert + "- " + p.getArmureEquipee().toString());
        System.out.print(blanc);
    }

    /**
     * Affiche tous les personnages en vie.
     * @param mdj Le MeneurDeJeu contenant les joueurs.
     */
    public void afficherPersonnages(MeneurDeJeu mdj)
    {
        mdjAfficherMessage("\nVoici les personnages en vie :");
        for (int i = 0; i < mdj.getJoueurs().size(); i++) {
            this.mdjAfficherMessage((i + 1) + " - " + mdj.getJoueurs().get(i).getNom() + "\n");
        }
    }

    /**
     * Affiche un message dit par le MDJ avec une saisie attendue.
     * @param message Le message à afficher.
     */
    public void mdjAfficherMessageAvecEntree(String message)
    {
        System.out.println(bleu + message + blanc);
        System.out.print("> ");
    }

    /**
     * Affiche un message destiné dit par le MDJ sans saisie.
     * @param message Le message à afficher.
     */
    public void mdjAfficherMessage(String message)
    {
        System.out.println(bleu + message + blanc);
    }

    /**
     * Affiche un message système dit par le jeu avec entrée.
     * @param message Le message à afficher.
     */
    public void DDAfficherMessageAvecEntree(String message)
    {
    System.out.println(violet + message + blanc);
    System.out.print("> ");
    }

    /**
     * Affiche un message système dans le style D&D sans saisie.
     * @param message Le message à afficher.
     */
    public void DDAfficherMessage(String message)
    {
        System.out.println(violet + message + blanc);
    }

    /**
     * Affiche un message simple sans style.
     * @param message Le message à afficher.
     */
    public void afficherMessage(String message)
    {
        System.out.print(message);
    }

    /**
     * Affiche l’état du donjon sous forme de grille colorée.
     * @param donjon Le donjon à afficher.
     */
    public void afficherDonjon(Donjon donjon)
    {
        int count_colonne = 0;
        int count_ligne = 0;
        String affichage = "\t";
        for(int i = 0; i < donjon.getTailleCarte();i++)
        {
            affichage+= blanc;
            affichage+= count_colonne + "\t";
            count_colonne+=1;
        }
        affichage += "\n";
        for(int i = 0; i < donjon.getTailleCarte() ;i++)
        {
            affichage += blanc;
            affichage += count_ligne + "\t";
            count_ligne+=1;
            for(int j = 0; j < donjon.getTailleCarte() ;j++)
            {
                if (donjon.getCase(i,j).equals("."))
                    affichage += jaune;
                else
                    affichage += bleu;
                affichage += donjon.getCase(i,j) + "\t";
            }
            affichage += "\n";
        }
        System.out.println(affichage);
    }

    /**
     * Affiche les limites de déplacement possibles pour un personnage.
     * @param p Le personnage à déplacer.
     * @param d Le donjon.
     */
    public void afficherDeplacementJoueur(Personnage p, Donjon d)
    {
        int nbCases = p.getStats().getVitesse() / 3;

        int xActuel = p.getX();
        int yActuel = p.getY();
        int Xmin = Math.max(xActuel - nbCases, 0);
        int Xmax = Math.min(xActuel + nbCases, d.getTailleCarte() - 1);
        int Ymin = Math.max(yActuel - nbCases, 0);
        int Ymax = Math.min(yActuel + nbCases, d.getTailleCarte() - 1);

        mdjAfficherMessage("Position actuelle : (" + xActuel + "," + yActuel + ")"
                                + "\nNombre de cases max : " + nbCases
                                + "\nZone autorisée : x[" + Xmin + "," + Xmax + "] / y[" + Ymin + "," + Ymax + "]");
    }

    /**
     * Affiche les limites de déplacement possibles pour un monstre.
     * @param m Le monstre à déplacer.
     * @param d Le donjon.
     */
    public void afficherDeplacementMonstre(Monstre m, Donjon d)
    {
        int nbCases = m.getVitesse() / 3;

        int xActuel = m.getX();
        int yActuel = m.getY();
        int Xmin = Math.max(xActuel - nbCases, 0);
        int Xmax = Math.min(xActuel + nbCases, d.getTailleCarte() - 1);
        int Ymin = Math.max(yActuel - nbCases, 0);
        int Ymax = Math.min(yActuel + nbCases, d.getTailleCarte() - 1);

        mdjAfficherMessage("Position actuelle : (" + xActuel + "," + yActuel + ")"
                + "\nNombre de cases max : " + nbCases
                + "\nZone autorisée : x[" + Xmin + "," + Xmax + "] / y[" + Ymin + "," + Ymax + "]");
    }


    /**
     * Affiche les informations d’un monstre (avec couleur rouge).
     * @param m Le monstre à afficher.
     */
    public void afficherInfoMonstre(Monstre m)
    {
        System.out.println("\u001B[31m" + m.toString() + "\u001B[0m");
    }

    /**
     * Affiche une ligne de séparation entre deux blocs d'information.
     */
    public void transition()
    {
        System.out.println(blanc + "\n===========================================");
    }

    /**
     * Vérifie que la saisie utilisateur est un entier valide.
     * @return L’entier saisi.
     */
    public int verifInt()
    {
        Scanner scanner = new Scanner(System.in);
        int entier = 0;
        boolean entierValide = false;
        String input = "";

        while (!entierValide) {
            input = scanner.nextLine();

            try {
                entier = Integer.parseInt(input);
                entierValide = true;
            } catch (NumberFormatException e) {
                afficherErreur("Erreur : ce n'est pas un entier valide. Essayez encore.");
                System.out.print("> ");
            }
        }
        return entier;
    }

    /**
     * Affiche un message d'erreur en jaune.
     * @param message Message à afficher.
     */
    public void afficherErreur(String message)
    {
        System.out.println(jaune + message + blanc);
    }

    /**
     * Affiche les monstres en vie dans la partie.
     * @param mdj Le meneur de jeu.
     */
    public void afficherMonstres(MeneurDeJeu mdj) {
        this.mdjAfficherMessage("\nVoici les monstres en vie :");
        for (int i = 0; i < mdj.getMonstres().size(); i++) {
            this.mdjAfficherMessage((i + 1) + " - " + mdj.getMonstres().get(i).getNom() + "\n");
        }
    }

    /**
     * Affiche l'ordre d'initiative des entités (joueurs et monstres).
     * @param mdj Le meneur de jeu.
     */
    public void afficherOrdre(MeneurDeJeu mdj)
    {
        int j = 0;
        int m = 0;
        String ordre = "";
        for (Entite entite : mdj.getOrdre().keySet())
        {
            Entite key = entite;
            if(entite.getTypeEntite() == TypeEntite.PERSONNAGE) {
                j++;
                ordre += ("P" + j + ": " + key.getNom() + "\n");
            }
            if (entite.getTypeEntite() == TypeEntite.MONSTRE) {
                m++;
                ordre += ("M" + m + ": " + key.getNom() + "\n");
            }
        }
        this.DDAfficherMessage(ordre);
    }
}


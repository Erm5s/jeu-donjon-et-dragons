 import DeroulementDuDonjon.Donjon;
 import Entite.Monstres.Monstre;
 import Entite.Personnages.Personnage;

 import java.util.List;
 import java.util.Scanner;

 public class Affichage
{
    private final String blanc = "\u001B[0m";
    private final String jaune = "\u001B[33m";
    private final String rouge = "\u001B[31m";
    private final String vert = "\u001B[32m";
    private final String bleu ="\u001B[34m";
    public void mdjAfficherMessage(String message)
    {
        System.out.println(bleu + message + blanc);
        System.out.print("> ");
    }

    public void PersonnageAfficherMessage(String message)
    {
        System.out.println(vert + message + blanc);
        System.out.print("> ");
    }

    public void DDAfficherMessage(String message)
    {
        System.out.println(jaune + message + blanc);
    }


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

    public void afficherInfoPersonnage(Personnage p)
    {
        System.out.println("\u001B[32m" + p.toString() + "\u001B[0m");
    }

    public void afficherInfoMonstre(Monstre m)
    {
        System.out.println("\u001B[31m" + m.toString() + "\u001B[0m");
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String majPremiereLettre(String texte) {
        if (texte == null || texte.isEmpty()) return texte;
        String[] mots = texte.toLowerCase().split("\\s+");
        StringBuilder resultat = new StringBuilder();
        for (String mot : mots) {
            if (!mot.isEmpty()) {
                resultat.append(Character.toUpperCase(mot.charAt(0)))
                        .append(mot.substring(1))
                        .append(" ");
            }
        }
        return resultat.toString().trim();
    }

    public void affichageRegles()
    {
        String regles = ( jaune + "\nBIENVENUE DANS DOOJONS ET DRAGONS !\n" + blanc
                        + "\nVoici quelques explications du code couleur avant de débuter le jeu :"
                        + "\n- En " + bleu + "bleu " + blanc + "les messages liés au " + bleu + "meneur de jeu" + blanc
                        + "\n- En " + vert +  "vert " + blanc + "les messages liés aux " + vert + "joueurs" + blanc
                        + "\n- En " + jaune + "jaune " + blanc + "les messages " + jaune + "divers " + blanc + "et ceux liés au " + jaune + "donjon");
        System.out.println(regles);
    }

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
                this.DDAfficherMessage("Erreur : ce n'est pas un entier valide. Essayez encore.");
            }
        }
        return entier;
    }
}


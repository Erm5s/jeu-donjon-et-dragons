import DeroulementDuDonjon.Donjon;
import Entite.Equipement.Equipement;
import Entite.Personnages.Classe;
import Entite.Personnages.Personnage;
import Entite.Personnages.Race;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MeneurDeJeu
{
    public void creerDonjon()
    {
        //MISE EN PLACE DU JEU
        Scanner scanner = new Scanner(System.in);
        List<Personnage> joueurs = new ArrayList<Personnage>();

        System.out.println("Bienvenue dans DOOnjon et Dragons\n");
        System.out.println("Veuillez indiquer le nombre de joueurs: ");
        int nb_joueurs = scanner.nextInt();
        for(int i =0; i < nb_joueurs; i++)
        {
            joueurs.add(CreationPerso());
        }

        //MISE EN PLACE DU DONJON
        Donjon donjon1 = new Donjon();
        System.out.println("Il est l'heure de commencer le premier donjon de votre aventure !\n\n");
        System.out.println("Meneur de jeu, combien d'obstacle souhaitez vous placer ? (10 max)\n\n");

        int nb_obstacles = scanner.nextInt();
        for(int i = 0; i < nb_obstacles; i++)
        {
            System.out.println("Où voulez-vous placer l'obstacle? (x puis y)");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            this.placerObstacle(donjon1,x,y);
            System.out.println("\n\nCarte mise à jour:\n\n");
            String carte =donjon1.afficherDonjon();
            System.out.println(carte);
        }
        System.out.println("Passons aux joueurs, où souhaitez vous les placer ?\n\n");
        for(int i = 0; i < nb_joueurs; i++)
        {
            System.out.println("Entrez les coordonnées pour placer le joueur numéro %d (x, PUIS y: \n\n");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            this.placerJoueur(donjon1, joueurs.get(i), x, y);

            System.out.println("\n\nCarte mise à jour:\n\n");
            String carte =donjon1.afficherDonjon();
            System.out.println(carte);
        }
    }

    public static Personnage CreationPerso()
    {
        Scanner scanner = new Scanner(System.in);


        // FONCTION CREATION DE PERSONNAGE
        // le nom
        System.out.print("\nEntrez votre nom : ");
        String nom = scanner.nextLine();

        // la race
        System.out.print("Choisissez votre race :\n1,2,3 ou 4 ");
        int raceNb = scanner.nextInt();
        Race race = null;
        switch (raceNb){
            case 1 -> race = Race.HUMAIN;
            case 2 -> race = Race.NAIN;
            case 3 -> race = Race.ELFE;
            case 4 -> race = Race.HALFELIN;
        }

        // la classe
        System.out.print("Choisissez votre classe :\n 1,2,3 ou 4 ");
        Classe classe = null;
        int classeNb = scanner.nextInt();
        switch (classeNb) {
            case 1 -> classe = Classe.CLERC;
            case 2 -> classe = Classe.GUERRIER;
            case 3 -> classe = Classe.MAGICIEN;
            case 4 -> classe = Classe.ROUBLARD;
        }



        Personnage p = new Personnage(nom, race, classe);

        System.out.println();
        p.afficheInventaire();
        System.out.println();
        p.afficheStats();

        return p;

    }

    public Boolean placerObstacle(Donjon donjon, int x, int y)
    {
        return donjon.placerObstacle(x,y);
    }

    public Boolean placerJoueur(Donjon donjon, Personnage personnage, int x, int y)
    {
        return donjon.placerJoueur(personnage, x, y);
    }

    public Boolean placerEquipement(Donjon donjon, Equipement equipement, int x, int y)
    {
        return donjon.placerEquipement(equipement, x, y);
    }

}

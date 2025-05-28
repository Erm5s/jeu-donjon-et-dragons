import java.util.ArrayList;
import java.util.Scanner;

import DeroulementDuDonjon.Donjon;
import Entite.Personnages.*;
import java.util.List;

public class Main
{
    public static void main(String args[])
    {
        /*
        MeneurDeJeu mdj = new MeneurDeJeu();
        Donjon donjon1 = new Donjon();
        mdj.creerDonjon(donjon1);
        mdj.creerDonjon();
        */
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
    }
}
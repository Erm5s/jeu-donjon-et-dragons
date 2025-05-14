import java.util.Scanner;

import DeroulementDuDonjon.Donjon;
import Personnages.*;

public class Main {
    public static void main(String args[]){

        System.out.println("Bienvenue dans DOOnjon et Dragons");

        Donjon bonjour = new Donjon();
        String jsp  = bonjour.afficherDonjon();

        System.out.println(jsp);
        CreationPerso();


    }

    public static void CreationPerso()
    {
        Scanner scanner = new Scanner(System.in);


        // FONCTION CREATION DE PERSONNAGE
        // le nom
        System.out.print("\nEntrez votre nom : ");
        String nom = scanner.nextLine();

        // la race
        System.out.print("Choisissez votre race : ");
        int raceNb = scanner.nextInt();
        Race race = null;
        switch (raceNb){
            case 1 -> race = Race.HUMAIN;
            case 2 -> race = Race.NAIN;
            case 3 -> race = Race.ELFE;
            case 4 -> race = Race.HALFELIN;
        }

        // la classe
        System.out.print("Choisissez votre classe : ");
        Classe classe = null;
        int classeNb = scanner.nextInt();
        switch (classeNb) {
            case 1 -> classe = Classe.CLERC;
            case 2 -> classe = Classe.GUERRIER;
            case 3 -> classe = Classe.MAGICIEN;
            case 4 -> classe = Classe.ROUBLARD;
        }



        Personnage p = new Personnage(nom, race, classe);

        p.afficheInventaire();
        p.afficheStats();
        p.getStats().statsPoidEquipement(p);
        p.afficheStats();

    }


}
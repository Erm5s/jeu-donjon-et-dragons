package Entite.Personnages.Sorts;

import Dice.*;
import DeroulementDuDonjon.Donjon;
import Entite.Monstres.Monstre;
import Entite.Personnages.Personnage;

import java.util.List;
import java.util.Scanner;

public class Guerison extends Sort {
    public Guerison() {
        super("Guérison");
    }

    @Override
    public String lancer(Personnage lanceur, Donjon donjon, List<Personnage> persos, List<Monstre> monstres) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Qui souhaitez-vous soigner ? (donnez un numéro)");
        for (int i = 0; i < persos.size(); i++)
            System.out.println((i+1) + " - " + persos.get(i).getNom());
        int choix = Integer.parseInt(sc.nextLine()) - 1;

        Personnage cible = persos.get(choix);
        Dice de = new Dice();
        int soin = de.lancer("1d10");
        int pvAvant = cible.getStats().getPV();
        int pvMax = cible.getStats().getPVInitial();
        int nouveauPV = Math.min(pvAvant + soin, pvMax);
        cible.getStats().setPV(nouveauPV);

        return cible.getNom() + " a été soigné de " + (nouveauPV - pvAvant) + " PV.\nPV actuels : " + cible.getStats().getPVInitial();
    }
}

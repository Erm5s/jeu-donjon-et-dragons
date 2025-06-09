package Entite.Personnages.Sorts;

import DeroulementDuDonjon.Donjon;
import Entite.Entite;
import Entite.Monstres.Monstre;
import Entite.Personnages.Personnage;
import affichage.Affichage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoogieWoogie extends Sort {
    public BoogieWoogie() {
        super("Boogie Woogie");
    }

    @Override
    public String lancer(Personnage lanceur, Donjon donjon, List<Personnage> persos, List<Monstre> monstres) {
        Scanner sc = new Scanner(System.in);
        Affichage affichage = new Affichage();

        List<Entite> toutes = new ArrayList<>();
        toutes.addAll(persos);
        toutes.addAll(monstres);

        for (int i = 0; i < toutes.size(); i++)
            affichage.mdjAfficherMessage((i + 1) + " - " + toutes.get(i).getNom());

        affichage.mdjAfficherMessageAvecEntree("Choisissez le premier à échanger :");
        Entite e1 = toutes.get(Integer.parseInt(sc.nextLine()) - 1);
        affichage.mdjAfficherMessageAvecEntree("Choisissez le second à échanger :");
        Entite e2 = toutes.get(Integer.parseInt(sc.nextLine()) - 1);

        int x1 = e1.getX(), y1 = e1.getY();
        int x2 = e2.getX(), y2 = e2.getY();

        donjon.changeCase(x1, y1, ".");
        donjon.changeCase(x2, y2, ".");

        e1.setCoordonnees(x2, y2);
        e2.setCoordonnees(x1, y1);

        donjon.changeCase(x2, y2, e1.getNom().substring(0, 3));
        donjon.changeCase(x1, y1, e2.getNom().substring(0, 3));

        return "Les positions de " + e1.getNom() + " et " + e2.getNom() + " ont été échangées.";
    }
}
package Entite.Personnages.Sorts;

import Entite.TypeEntite;
import DeroulementDuDonjon.Donjon;
import Entite.Equipement.Arme;
import Entite.Equipement.Equipement;
import Entite.Monstres.Monstre;
import Entite.Personnages.Personnage;
import affichage.Affichage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArmeMagique extends Sort {
    public ArmeMagique() {
        super("Arme magique");
    }

    @Override
    public String lancer(Personnage lanceur, Donjon donjon, List<Personnage> persos, List<Monstre> monstres) {
        Scanner sc = new Scanner(System.in);
        Affichage affichage = new Affichage();

        affichage.mdjAfficherMessage("L'arme auquel personnage souhaitez-vous améliorer ? ");
        for (int i = 0; i < persos.size(); i++)
            affichage.mdjAfficherMessage((i+1) + " - " + persos.get(i).getNom());
        int choixPerso = Integer.parseInt(sc.nextLine()) - 1;
        affichage.afficherMessage("> ");
        Personnage cible = persos.get(choixPerso);

        List<Equipement> inventaire = cible.getInventaire();
        List<Arme> armes = new ArrayList<>();

        for (Equipement e : inventaire) {
            if (e.getTypeEntite() == TypeEntite.ARME) {
                armes.add((Arme) e);
            }
        }
        if (cible.getArmeEquipee() != null)
            armes.add(cible.getArmeEquipee());

        affichage.mdjAfficherMessage("Voici ses armes :");
        for (int i = 0; i < armes.size(); i++)
            affichage.mdjAfficherMessageAvecEntree((i+1) + " - " + armes.get(i).getNom());
        affichage.mdjAfficherMessageAvecEntree("Quelle arme voulez-vous améliorer ?");
        int choixArme = Integer.parseInt(sc.nextLine()) - 1;
        Arme arme = armes.get(choixArme);
        arme.ameliorer();

        return "L’arme " + arme.getNom() + " a été améliorée !";
    }
}

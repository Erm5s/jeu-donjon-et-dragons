package DeroulementDuDonjon;

import Entite.Entite;
import Entite.Monstres.Monstre;
import Entite.Personnages.Personnage;
import Entite.TypeEntite;
import MeneurDeJeu.MeneurDeJeu;
import affichage.*;

import java.util.Scanner;

public class Tour
{
    private Affichage affichage = new Affichage();
    public void jouerDonjon(Donjon donjon, MeneurDeJeu mdj)
    {
        while(mdj.joueursEnVie(donjon) && mdj.monstresEnVie(donjon)) {

            for (Entite key : mdj.getOrdre().keySet()) {
                if (mdj.monstresEnVie(donjon) && mdj.joueursEnVie(donjon)) {
                    Entite entite = key;
                    affichage.DDAfficherMessage("C'est au tour de : " + entite.getNom());
                    if(entite.getTypeEntite() == TypeEntite.PERSONNAGE)
                    {
                        Personnage p = (Personnage) entite;
                        this.actionsPersonnage(p, donjon, mdj);
                    }
                    else if(entite.getTypeEntite() == TypeEntite.MONSTRE)
                    {
                        Monstre m = (Monstre) entite;
                        this.actionsMonstre(m, donjon, mdj);
                    }
                }
            }
        }
        affichage.mdjAfficherMessageAvecEntree("Le donjon est terminé!");
    }

    public void actionsPersonnage(Personnage personnage, Donjon donjon, MeneurDeJeu mdj)
    {
        Scanner scanner  = new Scanner(System.in);
        int nb_actions = 1;
        boolean continuer = true;
        while (nb_actions <= 3 && continuer)
        {
            affichage.DDAfficherMessage("ACTION " + nb_actions + "/3");
            affichage.PersonnageAfficherMessage("Quelle action souhaitez-vous effectuer ?\n1 - équiper une arme\n2 - se déplacer\n3 - ramasser un équipement\n4 - attaquer un monstre\n5 - Passer le tour");
            int numero_action = affichage.verifInt();
            switch (numero_action)
            {
                case 1 ->
                {
                    affichage.DDAfficherMessage(personnage.inventaireToString());
                    affichage.mdjAfficherMessageAvecEntree("Quelle arme souhaitez vous équiper ?");
                    int num = affichage.verifInt();
                    affichage.DDAfficherMessage(personnage.equiper(num));
                    break;
                }
                case 2 ->
                {
                    affichage.afficherDeplacementJoueur(personnage, donjon);
                    affichage.DDAfficherMessage(personnage.seDeplacer(donjon));
                }
                case 3 -> personnage.ramasser(donjon);
                case 4 ->
                {
                    affichage.afficherMonstres();
                    affichage.mdjAfficherMessageAvecEntree("Quel monstre souhaitez-vous attaquer ?");
                    int num = affichage.verifInt();
                    affichage.mdjAfficherMessageAvecEntree(personnage.attaquer(mdj.getMonstres().get(num-1)));

                }
                case 5 -> {
                    continuer = false;
                    break;
                }
                default ->
                {
                    affichage.mdjAfficherMessageAvecEntree("Action non valide.");
                    nb_actions --;
                }
            }
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);
            nb_actions++;
        }
    }

    public void actionsMonstre(Monstre monstre, Donjon donjon, MeneurDeJeu mdj)
    {
        Scanner scanner  = new Scanner(System.in);
        int nb_actions = 1;
        boolean continuer = true;
        while (nb_actions <= 3 && continuer)
        {
            affichage.DDAfficherMessage("ACTION " + nb_actions + "/3");
            affichage.PersonnageAfficherMessage("Quelle action souhaitez-vous effectuer ?\n1 - se déplacer\n2 - attaquer un personnage\n3 - Passer le tour");
            int numero_action = affichage.verifInt();
            switch (numero_action)
            {
                case 1 ->
                {
                    //RAJOUTER UNE FONCTION PR VOIR OU LE MONSTRE PEUT ALLER
                    affichage.afficherMessage(monstre.seDeplacer(donjon));
                }
                case 2 ->
                {
                    affichage.afficherPersonnages();
                    affichage.mdjAfficherMessageAvecEntree("Quel personnage souhaitez-vous attaquer ?");
                    int num = affichage.verifInt();
                    affichage.mdjAfficherMessageAvecEntree(monstre.attaquer(mdj.getJoueurs().get(num-1)));

                }
                case 3   -> {
                    continuer = false;
                    break;
                }
                default -> affichage.mdjAfficherMessageAvecEntree("Action non valide.");
            }
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);
            nb_actions+=1;
        }
    }
}

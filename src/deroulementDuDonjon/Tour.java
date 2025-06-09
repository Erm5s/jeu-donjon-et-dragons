package deroulementDuDonjon;

import entite.Entite;
import entite.monstres.Monstre;
import entite.personnages.Classe;
import entite.personnages.Personnage;
import entite.personnages.sorts.ArmeMagique;
import entite.personnages.sorts.BoogieWoogie;
import entite.personnages.sorts.Guerison;
import entite.TypeEntite;
import meneurDeJeu.MeneurDeJeu;
import affichage.*;

import java.util.Scanner;

/**
 * Classe représentant le déroulement d'un tour dans le donjon.
 * Chaque entité (joueur ou monstre) peut effectuer jusqu'à 3 actions par tour.
 */
public class Tour
{
    // ===================== ATTRIBUT =====================
    private Affichage affichage = new Affichage();
    private int m_tour;

    // ===================== CONSTRUCTEUR =====================
    /**
     * Constructeur par défaut avec le numéro de tour.
     */
    public Tour()
    {
        m_tour = 0;
    }

    // ===================== MÉTHODES =====================
    /**
     * Lance la boucle de jeu du donjon. Tant qu’aucun joueur n'est mort et que tous les monstres sont vivants,
     * chaque entité dans l’ordre d’initiative effectue ses actions.
     * @param donjon Le donjon dans lequel se déroule le combat.
     * @param mdj Le meneur de jeu qui contrôle les entités.
     */
    public void jouerDonjon(Donjon donjon, MeneurDeJeu mdj)
    {
        while(mdj.joueursEnVie(donjon) && mdj.monstresEnVie(donjon)) {

            for (Entite key : mdj.getOrdre().keySet()) {
                m_tour++;
                if (mdj.monstresEnVie(donjon) && mdj.joueursEnVie(donjon)) {
                    Entite entite = key;
                    affichage.DDAfficherMessage("TOUR n°" + m_tour + " - " + entite.getNom());
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

    /**
     * Gère les actions possibles d’un personnage durant son tour (jusqu’à 3).
     * @param personnage Le personnage jouant son tour.
     * @param donjon Le donjon en cours.
     * @param mdj Le meneur de jeu (pour accéder aux listes d'entités).
     */
    public void actionsPersonnage(Personnage personnage, Donjon donjon, MeneurDeJeu mdj)
    {
        Scanner scanner  = new Scanner(System.in);
        int nb_actions = 1;
        boolean continuer = true;
        while (nb_actions <= 3 && continuer)
        {
            affichage.DDAfficherMessage("ACTION " + nb_actions + "/3");
            affichage.mdjAfficherMessageAvecEntree("Quelle action souhaitez-vous effectuer ?\n1 - équiper une arme\n2 - se déplacer\n3 - ramasser un équipement\n4 - attaquer un monstre\n5 - lancer un sort\n6 - passer le tour");
            int numero_action = affichage.verifInt();
            switch (numero_action)
            {
                case 1 ->
                {
                    affichage.afficherInventaire(personnage);
                    affichage.mdjAfficherMessageAvecEntree("Quelle arme souhaitez vous équiper ?");
                    int num = affichage.verifInt();
                    affichage.mdjAfficherMessage(personnage.equiper(num));
                    break;
                }
                case 2 ->
                {
                    affichage.afficherDeplacementJoueur(personnage, donjon);
                    affichage.DDAfficherMessage(personnage.seDeplacer(donjon));
                }
                case 3 -> affichage.mdjAfficherMessage(personnage.ramasser(donjon, mdj.getEquipementsSurCarte()));
                case 4 ->
                {
                    affichage.afficherMonstres(mdj);
                    affichage.mdjAfficherMessageAvecEntree("Quel monstre souhaitez-vous attaquer ?");
                    int num = affichage.verifInt();
                    affichage.mdjAfficherMessageAvecEntree(personnage.attaquer(mdj.getMonstres().get(num-1)));

                }
                case 6 -> {
                    continuer = false;
                    break;
                }
                case 5 ->
                {
                    if (personnage.getClasse() == Classe.MAGICIEN)  {
                        affichage.mdjAfficherMessageAvecEntree("\nQuel sort voulez-vous lancer ? \n1 - arme magique\n2 - boogie woogie\n3 - guérison");
                        int num_sort = scanner.nextInt();
                        switch (num_sort) {
                            case 1 -> {
                                ArmeMagique armeMagique = new ArmeMagique();
                                affichage.mdjAfficherMessage(armeMagique.lancer(personnage, donjon, mdj.getJoueurs(), mdj.getMonstres()));
                            }
                            case 2 -> {
                                BoogieWoogie boogieWoogie = new BoogieWoogie();
                                affichage.mdjAfficherMessage(boogieWoogie.lancer(personnage, donjon, mdj.getJoueurs(), mdj.getMonstres()));
                            }
                            case 3 -> {
                                Guerison guerison = new Guerison();
                                affichage.mdjAfficherMessage(guerison.lancer(personnage, donjon, mdj.getJoueurs(), mdj.getMonstres()));
                            }
                            default -> affichage.mdjAfficherMessage("Ce sort n'est pas valide");
                        }
                    } else if (personnage.getClasse() == Classe.CLERC) {
                        affichage.mdjAfficherMessageAvecEntree("\nQuel sort voulez-vous lancer ?\n1 - guérison");
                        int num_sort = scanner.nextInt();
                        if (num_sort == 1) {
                            Guerison guerison = new Guerison();
                            affichage.mdjAfficherMessage(guerison.lancer(personnage, donjon, mdj.getJoueurs(), mdj.getMonstres()));
                        } else {
                            affichage.mdjAfficherMessage("Ce sort n'est pas valide");
                        }
                    } else {
                        affichage.mdjAfficherMessage("Vous ne possédez aucun sort.");
                    }
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

    /**
     * Gère les actions possibles d’un monstre durant son tour (jusqu’à 3).
     * @param monstre Le monstre jouant son tour.
     * @param donjon Le donjon en cours.
     * @param mdj Le meneur de jeu (pour accéder aux listes d'entités).
     */
    public void actionsMonstre(Monstre monstre, Donjon donjon, MeneurDeJeu mdj)
    {
        Scanner scanner  = new Scanner(System.in);
        int nb_actions = 1;
        boolean continuer = true;
        while (nb_actions <= 3 && continuer)
        {
            affichage.DDAfficherMessage("ACTION " + nb_actions + "/3");
            affichage.mdjAfficherMessageAvecEntree("Quelle action souhaitez-vous effectuer ?\n1 - se déplacer\n2 - attaquer un personnage\n3 - Passer le tour");
            int numero_action = affichage.verifInt();
            switch (numero_action)
            {
                case 1 ->
                {
                    affichage.afficherDeplacementMonstre(monstre, donjon);
                    affichage.mdjAfficherMessage(monstre.seDeplacer(donjon));
                }
                case 2 ->
                {
                    affichage.afficherPersonnages(mdj);
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

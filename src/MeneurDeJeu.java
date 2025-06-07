import DeroulementDuDonjon.Donjon;
import Entite.Equipement.Arme;
import Entite.TypeEntite;
import Entite.Entite;
import Entite.Equipement.Equipement;
import Entite.Monstres.Monstre;
import Entite.Personnages.Classe;
import Entite.Personnages.Personnage;
import Entite.Personnages.Race;
import Dice.Dice;

import java.util.*;

public class MeneurDeJeu {
    private List<Personnage> m_joueurs;
    private List<Monstre> m_monstres;
    private HashMap<Entite, Integer> m_JoueursEtMonstresInitiative;
    private HashMap<Entite, Integer> m_OrdreJoueurs = new HashMap<Entite,Integer>();
    private Affichage affichage = new Affichage();
    private List<Equipement> m_EquipementDonjon;

    public MeneurDeJeu()
    {
        this.m_joueurs = new ArrayList<Personnage>();
        this.m_monstres = new ArrayList<Monstre>();
    }

    public void creerDonjon(Donjon donjon) {
        Affichage affichage = new Affichage();
        Scanner scanner = new Scanner(System.in);

        affichage.affichageRegles();
        affichage.transition();

        creationJoueursPartie();

        affichage.DDAfficherMessage("\nIl est l'heure de commencer le premier donjon de votre aventure !");
        affichage.mdjAfficherMessage("Combien d'obstacles souhaitez vous placer ? (10 max)");
        int nb_obstacles = affichage.verifInt();
        for (int i = 0; i < nb_obstacles; i++) {
            affichage.DDAfficherMessage("\nOBSTACLE " + (i+1) +"/" + nb_obstacles +" :");
            affichage.mdjAfficherMessage("Où voulez-vous placer l'obstacle? (Y puis X)");
            int x = affichage.verifInt();
            int y = affichage.verifInt();
            this.placerObstacle(donjon, x, y);
            affichage.DDAfficherMessage("\n\nCarte mise à jour : \n");
            affichage.afficherDonjon(donjon);
        }

        System.out.println("\nPassons aux joueurs, où souhaitez vous les placer ?");
        for (int i = 0; i < m_joueurs.size(); i++) {
            affichage.mdjAfficherMessage("Entrez les coordonnées pour placer le joueur suivant : "+ m_joueurs.get(i).getNom() +" (Y, PUIS X):");
            int x = affichage.verifInt();
            int y = affichage.verifInt();
            this.placerJoueur(donjon, this.m_joueurs.get(i), x, y);

            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n");
            affichage.afficherDonjon(donjon);
        }

        affichage.DDAfficherMessage("Meneur de jeu créez vos montres!\n");
        affichage.mdjAfficherMessage("Combien de monstres souhaitez-vous créer ? (max 3)");
        int nb_Monstres = affichage.verifInt();
        for(int i = 0; i < nb_Monstres;i++)
        {
            creationMonstre();
            affichage.DDAfficherMessage("Où souhaitez-vous placer le monstre ? (Y puis X)");
            int x = affichage.verifInt();
            int y = affichage.verifInt();
            placerMonstre(donjon,m_monstres.get(i),x,y);
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);
        }

        //ON DETERMINE L'ORDRE DES JOUEURS
        affichage.DDAfficherMessage("Maintenant, passons à l'ordre de jeu de chaque personnage et monstre.\n" +
                "Il est déterminé par l'initiative de l'entité à laquelle on additionne le résultat d'un lancé de dé à 20 faces :\n");
        determinerOrdre();
        affichage.afficherOrdre();
    }

    public void jouerDonjon(Donjon donjon)
    {
        while(joueursEnVie(donjon) && monstresEnVie(donjon)) {

            for (Entite key : m_OrdreJoueurs.keySet()) {
                if (monstresEnVie(donjon) && joueursEnVie(donjon)) {
                    Entite entite = key;
                    affichage.DDAfficherMessage("C'est au tour de : " + entite.getNom());
                    if(entite.getTypeEntite() == TypeEntite.PERSONNAGE)
                    {
                        Personnage p = (Personnage) entite;
                        actionsPersonnage(p, donjon);
                    }
                    else if(entite.getTypeEntite() == TypeEntite.MONSTRE)
                    {
                        Monstre m = (Monstre) entite;
                        actionsMonstre(m, donjon);
                    }
                }
            }
        }
        affichage.mdjAfficherMessage("Le donjon est terminé!");
    }

    public Personnage creationPerso() {
        Scanner scanner = new Scanner(System.in);
        Affichage affichage = new Affichage();

        affichage.PersonnageAfficherMessage("Entrez votre nom");
        String nom = scanner.nextLine();

        affichage.PersonnageAfficherMessage("\nChoisissez votre race :\n1 - Humain | 2 - Nain\n3 - Elfe   | 4 - Halfelin");
        int raceNb = affichage.verifInt();
        Race race = null;
        switch (raceNb) {
            case 1 -> race = Race.HUMAIN;
            case 2 -> race = Race.NAIN;
            case 3 -> race = Race.ELFE;
            case 4 -> race = Race.HALFELIN;
        }

        affichage.PersonnageAfficherMessage("\nChoisissez votre classe :\n1 - Clerc    | 2 - Guerrier\n3 - Magicien | 4 - Roublard ");
        Classe classe = null;
        int classeNb = affichage.verifInt();
        switch (classeNb) {
            case 1 -> classe = Classe.CLERC;
            case 2 -> classe = Classe.GUERRIER;
            case 3 -> classe = Classe.MAGICIEN;
            case 4 -> classe = Classe.ROUBLARD;
        }

        Personnage p = new Personnage(nom, race, classe);
        return p;
    }

    //fonction pour créer les personnages des joueurs de la partie
    public void creationJoueursPartie()
    {
        affichage.mdjAfficherMessage("Avant de commencer la partie, créez les personnages que vous jouerez dans les donjons!\n" +
                                     "Veuillez entrez le nombre de joueurs pour procéder à la création des personnages");
        int nb_joueurs = affichage.verifInt();
        for (int i = 0; i < nb_joueurs; i++) {
            affichage.DDAfficherMessage("\n\nJOUEUR " + (i+1) + " :");
            this.m_joueurs.add(creationPerso());
            affichage.DDAfficherMessage("\nPersonnage crée :");
            affichage.afficherInfoPersonnage(m_joueurs.get(i));
        }
    }

    //fonction pour créer les monstres d'un donjon
    public void creationMonstre()
    {
        int numero = m_monstres.size() +1;
        Scanner scanner = new Scanner(System.in);

        affichage.mdjAfficherMessage("\nQuelle espèce souhaitez vous donner à votre monstre ?");
        String espece = scanner.nextLine();

        affichage.mdjAfficherMessage("Quelle portée souhaitez vous donner à votre monstre ?");
        int portee = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien de dégâts souhaitez vous donner à votre monstre ?");
        int degats = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien de lancés de dés souhaitez vous que votre monstre fasse lors de son attaque ?");
        int nb_lances = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien de Points de vie souhaitez vous donner à votre monstre ?");
        int pv = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien de vitesse souhaitez vous donner à votre monstre ?");
        int vitesse = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien de force souhaitez vous donner à votre monstre ?");
        int force = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien de dégâts souhaitez vous donner à votre monstre ?");
        int dexterite = affichage.verifInt();

        affichage.mdjAfficherMessage("Quelle armure souhaitez vous donner à votre monstre ?");
        int classe_armure = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien d'initiative souhaitez vous donner à votre monstre ?");
        int initiative = affichage.verifInt();

        Monstre m = new Monstre(espece,portee,degats,nb_lances,pv,vitesse,force,dexterite,classe_armure,initiative);
        m_monstres.add(m);
        affichage.DDAfficherMessage("\n"+m.toString());
    }

    public void creationEquipementDonjon()
    {
        Scanner scanner = new Scanner(System.in);
        affichage.DDAfficherMessage("Meneur de jeu, créez les équipements qui seront présents dans le donjon\nCombien d'équipements souhaitez-vous créer ?");
        int nbEquipements = scanner.nextInt();
        for(int i = 0; i < nbEquipements;i++)
        {
            affichage.DDAfficherMessage("Quel nom souhaitez vous donner à votre arme ?\n");
            String nom = scanner.nextLine();
            affichage.DDAfficherMessage("Combien de dégâts souhaitez-vous donner à votre arme?\n");
            int degats = affichage.verifInt();
            affichage.DDAfficherMessage("Quelle portée souhaitez-vous donner à votre arme?\n");
            int portee = affichage.verifInt();
            affichage.DDAfficherMessage("Votre arme est-elle légère ou lourde? 1 - si lourde, 2 - si légère\n");
            int lourdelegere = affichage.verifInt();
            boolean estlourde = true;
            if(lourdelegere == 2){estlourde = false;}
            Arme a = new Arme(nom,degats,portee,estlourde);

        }

    }

    public void placerEquipement(Donjon donjon)
    {
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < m_EquipementDonjon.size();i++)
        {
            affichage.DDAfficherMessage("Meneur de jeu, où souhaitez vous placer l'arme: "+m_EquipementDonjon.get(i).getNom()+". Y puis X");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            m_EquipementDonjon.get(i).setCoordonnees(x,y);
            m_EquipementDonjon.add(i,m_EquipementDonjon.get(i));
            donjon.changeCase(x,y,"*");
        }
    }
    public void actionsPersonnage(Personnage personnage, Donjon donjon)
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
                    affichage.mdjAfficherMessage("Quelle arme souhaitez vous équiper ?");
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
                    affichage.mdjAfficherMessage("Quel monstre souhaitez-vous attaquer ?");
                    int num = affichage.verifInt();
                    affichage.mdjAfficherMessage(personnage.attaquer(m_monstres.get(num-1)));

                }
                case 5 -> {
                    continuer = false;
                    break;
                }
                default ->
                {
                    affichage.mdjAfficherMessage("Action non valide.");
                    nb_actions --;
                }
            }
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);
            nb_actions++;
        }
    }

    public void actionsMonstre(Monstre monstre, Donjon donjon)
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
                    affichage.mdjAfficherMessage("Quel personnage souhaitez-vous attaquer ?");
                    int num = affichage.verifInt();
                    affichage.mdjAfficherMessage(monstre.attaquer(m_joueurs.get(num-1)));

                }
                case 3   -> {
                    continuer = false;
                    break;
                }
                default -> affichage.mdjAfficherMessage("Action non valide.");
            }
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);
            nb_actions+=1;
        }
    }

    public Boolean placerObstacle(Donjon donjon, int x, int y) {
        return donjon.placerObstacle(x, y);
    }

    public boolean placerJoueur(Donjon donjon, Personnage personnage, int x, int y) {
        return donjon.placerJoueur(personnage, x, y);
    }

    public boolean placerMonstre(Donjon donjon,Monstre monstre, int x, int y)
    {
        return donjon.placerMonstre(monstre,x,y);
    }

    public boolean placerEquipement(Donjon donjon, Equipement equipement, int x, int y) {
        return donjon.placerEquipement(equipement, x,y);
    }


    //FONCTION POUR DETERMINER L'ORDRE DE JEUUUUUUUUUUUUUUUUUUUU
    public void determinerOrdre() {
        m_JoueursEtMonstresInitiative = new HashMap<>();
        Dice de = new Dice(20);
        //on remplit la hashmap
        for (int i = 0; i < m_joueurs.size(); i++) {
            m_JoueursEtMonstresInitiative.put(m_joueurs.get(i), m_joueurs.get(i).getStats().getInitiative() + de.lanceDes(1));
        }
        for (int i = 0; i < m_monstres.size(); i++) {
            m_JoueursEtMonstresInitiative.put(m_monstres.get(i), m_monstres.get(i).getInitiative() + de.lanceDes(1));
        }
        while (!m_JoueursEtMonstresInitiative.isEmpty()) {
            Entite maxKey = null;
            int max = Integer.MIN_VALUE;

            for (Entite key : m_JoueursEtMonstresInitiative.keySet()) {
                int initiative = m_JoueursEtMonstresInitiative.get(key);
                if (initiative > max) {
                    max = initiative;
                    maxKey = key;
                }
            }
            m_OrdreJoueurs.put(maxKey, max);
            m_JoueursEtMonstresInitiative.remove(maxKey);
        }
    }


    //fonction pour savoir si tt les joueurs sont encore en vie
    public boolean joueursEnVie(Donjon donjon)
    {
        for(int i = 0; i < m_joueurs.size();i++)
        {
            if(m_joueurs.get(i).getStats().getPV() < 1)
            {
                return false;
            }
        }
        return true;
    }

    public boolean monstresEnVie(Donjon donjon)
    {
        int count = 0;
        for(int i = 0; i < m_monstres.size();i++)
        {
            if(m_monstres.get(i).getPV() < 1)

            {
                count+=1;
                int x = m_monstres.get(i).getX();
                int y = m_monstres.get(i).getY();
                donjon.changeCase(x,y,"X");
            }
        }
        if(count ==  m_monstres.size())
        {
            return false;
        }
        return true;
    }

    public List<Personnage> getJoueurs(){return m_joueurs;}

    public List<Monstre> getMonstres(){return m_monstres;}

    public HashMap<Entite,Integer> getOrdre(){return m_OrdreJoueurs;}
}
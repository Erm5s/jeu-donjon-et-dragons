import DeroulementDuDonjon.Donjon;
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
    private HashMap<Personnage, Integer> m_JoueursInitiative;
    private HashMap<Personnage, Integer> m_OrdreJoueurs = new HashMap<Personnage,Integer>();
    private Affichage affichage = new Affichage();


    public void creerDonjon(Donjon donjon) {
        //MISE EN PLACE DU JEU
        Affichage affichage = new Affichage();
        Scanner scanner = new Scanner(System.in);
        this.m_joueurs = new ArrayList<Personnage>();
        this.m_monstres = new ArrayList<Monstre>();
        affichage.DDAfficherMessage("Bienvenue dans DOOnjon et Dragons\n");
        affichage.DDAfficherMessage("Veuillez indiquer le nombre de joueurs: ");
        int nb_joueurs = scanner.nextInt();
        for (int i = 0; i < nb_joueurs; i++) {
            this.m_joueurs.add(CreationPerso());
        }
        //MISE EN PLACE DU DONJON
        affichage.DDAfficherMessage("Il est l'heure de commencer le premier donjon de votre aventure !\n\nMeneur de jeu, combien d'obstacle souhaitez vous placer ? (10 max)");

        int nb_obstacles = scanner.nextInt();
        for (int i = 0; i < nb_obstacles; i++) {
            affichage.DDAfficherMessage("Où voulez-vous placer l'obstacle? (x puis y)");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            this.placerObstacle(donjon, x, y);
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);
        }
        System.out.println("Passons aux joueurs, où souhaitez vous les placer ?\n\n");
        for (int i = 0; i < nb_joueurs; i++) {
            affichage.DDAfficherMessage("Entrez les coordonnées pour placer le joueur numéro %d (x, PUIS y: \n\n");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            this.placerJoueur(donjon, this.m_joueurs.get(i), x, y);

            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);
        }
        //ON DETERMINE L'ORDRE DES JOUEURS
        affichage.DDAfficherMessage("Maintenant, passons à l'ordre de jeu de chaque personnage. " +
                "Il est déterminé par l'initiative du personnage à laquelle on additionne le résultat d'un lancé de dé à 20 faces :\n\n");
        //on trie la hashmap
        determinerOrdre();
        //ON AFFICHE LE QUOICOUORDRE
        afficherOrdre();

        affichage.DDAfficherMessage("Meneur de jeu créez vos montres!\n");
        affichage.DDAfficherMessage("Combien de monstrez souhaitez-vous créer ? (max 3)");
        int nb_Monstres = scanner.nextInt();
        for(int i = 0; i < nb_Monstres;i++)
        {
            creationMonstre();
            affichage.DDAfficherMessage("Où souhaitez-vous placer le monstre ? (x puis y)");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            placerMonstre(donjon,m_monstres.get(i),x,y);
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);

            System.out.println("Taille de m_joueurs : " + m_joueurs.size());
            System.out.println("Taille de m_JoueursInitiative : " + m_JoueursInitiative.size());
        }
    }

    public void jouerDonjon(Donjon donjon)
    {
        System.out.println("État initial des joueurs :");
        for (Personnage p : m_joueurs) {
            System.out.println(p.getNom() + " a " + p.getStats().getPV() + " PV");
        }

        System.out.println("joueursEnVie() = " + joueursEnVie());
        while(joueursEnVie())
        {
            for(Personnage key : m_JoueursInitiative.keySet())
            {
                Personnage personnage =key;
                actionsPersonnage(personnage, donjon);
            }
        }
    }

    public static Personnage CreationPerso() {
        Scanner scanner = new Scanner(System.in);
        Affichage affichage = new Affichage();

        // FONCTION CREATION DE PERSONNAGE
        // le nom
        affichage.DDAfficherMessage("\nEntrez votre nom : ");
        String nom = scanner.nextLine();

        // la race
        affichage.DDAfficherMessage("Choisissez votre race :\n1,2,3 ou 4 ");
        int raceNb = scanner.nextInt();
        Race race = null;
        switch (raceNb) {
            case 1 -> race = Race.HUMAIN;
            case 2 -> race = Race.NAIN;
            case 3 -> race = Race.ELFE;
            case 4 -> race = Race.HALFELIN;
        }

        // la classe
        affichage.DDAfficherMessage("Choisissez votre classe :\n 1,2,3 ou 4 ");
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
        p.toString();

        return p;

    }

    public void creationMonstre()
    {
        int numero = m_monstres.size() +1;
        Scanner scanner = new Scanner(System.in);

        affichage.DDAfficherMessage("Quelle espèce souhaitez vous donner à votre monstre ?");
        String espece = scanner.nextLine();

        affichage.DDAfficherMessage("Quelle portée souhaitez vous donner à votre monstre ?");
        int portee = scanner.nextInt();

        affichage.DDAfficherMessage("Combien de dégâts souhaitez vous donner à votre monstre ?");
        int degats = scanner.nextInt();

        affichage.DDAfficherMessage("Combien de lancés de dés souhaitez vous que votre monstre fasse lors de son attaque ?");
        int nb_lances = scanner.nextInt();

        affichage.DDAfficherMessage("Combien de Points de vie souhaitez vous donner à votre monstre ?");
        int pv = scanner.nextInt();

        affichage.DDAfficherMessage("Combien de force souhaitez vous donner à votre monstre ?");
        int force = scanner.nextInt();

        affichage.DDAfficherMessage("Combien de dégâts souhaitez vous donner à votre monstre ?");
        int dexterite = scanner.nextInt();

        affichage.DDAfficherMessage("Quelle armure souhaitez vous donner à votre monstre ?");
        int classe_armure = scanner.nextInt();

        affichage.DDAfficherMessage("Combien d'initiative souhaitez vous donner à votre monstre ?");
        int initiative = scanner.nextInt();

        Monstre m = new Monstre(espece,numero,portee,degats,nb_lances,pv,force,dexterite,classe_armure,initiative);
        m_monstres.add(m);
    }

    public void actionsPersonnage(Personnage personnage, Donjon donjon)
    {
        Scanner scanner  = new Scanner(System.in);
        int nb_actions = 0;
        while (nb_actions < 3)
        {
            affichage.mdjAfficherMessage("Quelle action souhaitez-vous effectuer ?");
            int numero_action = scanner.nextInt();
            switch (numero_action)
            {
                case 2 ->
                {
                    personnage.seDeplacer(donjon);
                }
            }
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
        return donjon.placerEquipement(equipement, x, y);
    }


    //FONCTION POUR DETERMINER L'ORDRE DE JEUUUUUUUUUUUUUUUUUUUU
    public void determinerOrdre() {
        m_JoueursInitiative = new HashMap<>();
        Dice de = new Dice(20);
        //on remplit la hashmap
        for (int i = 0; i < m_joueurs.size(); i++) {
            m_JoueursInitiative.put(m_joueurs.get(i), m_joueurs.get(i).getStats().getInitiative() + de.lanceDes(1));
        }
        while (!m_JoueursInitiative.isEmpty()) {
            Personnage maxKey = null;
            int max = Integer.MIN_VALUE;

            for (Personnage key : m_JoueursInitiative.keySet()) {
                int initiative = m_JoueursInitiative.get(key);
                if (initiative > max) {
                    max = initiative;
                    maxKey = key;
                }
            }
                m_OrdreJoueurs.put(maxKey, max);
                m_JoueursInitiative.remove(maxKey);
        }
    }

    //FONCTION POUR AFFICHER L'ORDRE DES PUTAIN DE JOUEURS
    public void afficherOrdre()
    {
        int index = 0;
        for (Personnage personnage : m_OrdreJoueurs.keySet())
        {
            index +=1;
            Personnage key = personnage;
            System.out.println("Joueur numero "+ index +": " + key.getNom());
        }
    }

    //fonction pour savoir si tt les joueurs sont encore en vie
    public boolean joueursEnVie()
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

    //actions du joueur
    public boolean attaquer()
    {
        Scanner scanner = new Scanner(System.in);
        affichage.mdjAfficherMessage("Quel monstre souhaitez-vous attaquer ?");
        int num_monstre = scanner.nextInt();
        return true;
    }
}
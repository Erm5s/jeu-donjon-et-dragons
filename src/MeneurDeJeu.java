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
        affichage.affichageRegles();
        affichage.DDAfficherMessage("\nMeneur du jeu, mettez en place le donjon !");
        affichage.mdjAfficherMessage("Veuillez indiquer le nombre de joueurs");
        int nb_joueurs = affichage.verifInt();
        for (int i = 0; i < nb_joueurs; i++) {
            affichage.DDAfficherMessage("\n\nJOUEUR " + (i+1) + " :");
            this.m_joueurs.add(creationPerso());
        }
        //MISE EN PLACE DU DONJON
        affichage.DDAfficherMessage("\nIl est l'heure de commencer le premier donjon de votre aventure !");
        affichage.mdjAfficherMessage("Combien d'obstacles souhaitez vous placer ? (10 max)");
        int nb_obstacles = affichage.verifInt();
        for (int i = 0; i < nb_obstacles; i++) {
            affichage.DDAfficherMessage("\nOBSTACLE " + (i+1) +"/" + nb_obstacles +" :");
            affichage.mdjAfficherMessage("Où voulez-vous placer l'obstacle? (x puis y)");
            int x = affichage.verifInt();
            int y = affichage.verifInt();
            this.placerObstacle(donjon, x, y);
            affichage.DDAfficherMessage("\n\nCarte mise à jour : \n");
            affichage.afficherDonjon(donjon);
        }
        System.out.println("\nPassons aux joueurs, où souhaitez vous les placer ?");
        for (int i = 0; i < nb_joueurs; i++) {
            affichage.mdjAfficherMessage("Entrez les coordonnées pour placer le joueur suivant : "+ m_joueurs.get(i).getNom() +" (x, PUIS y):");
            int x = affichage.verifInt();
            int y = affichage.verifInt();
            this.placerJoueur(donjon, this.m_joueurs.get(i), x, y);

            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n");
            affichage.afficherDonjon(donjon);
        }
        //ON DETERMINE L'ORDRE DES JOUEURS
        affichage.DDAfficherMessage("Maintenant, passons à l'ordre de jeu de chaque personnage. " +
                "Il est déterminé par l'initiative du personnage à laquelle on additionne le résultat d'un lancé de dé à 20 faces :\n\n");
        //on trie la hashmap
        determinerOrdre();
        //ON AFFICHE LE QUOICOUORDRE, supprime moi ce commentaire de merde jvais tniker
        afficherOrdre();

        affichage.DDAfficherMessage("Meneur de jeu créez vos montres!\n");
        affichage.mdjAfficherMessage("Combien de monstres souhaitez-vous créer ? (max 3)");
        int nb_Monstres = affichage.verifInt();
        for(int i = 0; i < nb_Monstres;i++)
        {
            creationMonstre();
            affichage.DDAfficherMessage("Où souhaitez-vous placer le monstre ? (x puis y)");
            int x = affichage.verifInt();
            int y = affichage.verifInt();
            placerMonstre(donjon,m_monstres.get(i),x,y);
            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n\n");
            affichage.afficherDonjon(donjon);

            System.out.println("Taille de m_joueurs : " + m_joueurs.size());
            System.out.println("Taille de m_JoueursInitiative : " + m_JoueursInitiative.size());
        }
    }

    public void jouerDonjon(Donjon donjon)
    {
        while(joueursEnVie(donjon) && monstresEnVie(donjon)) {

            for (Personnage key : m_OrdreJoueurs.keySet()) {
                if (monstresEnVie(donjon) && joueursEnVie(donjon)) {
                    Personnage personnage = key;
                    affichage.mdjAfficherMessage("C'est au tour du joueur: " + personnage.getNom());
                    actionsPersonnage(personnage, donjon);
                }
            }
        }
        affichage.mdjAfficherMessage("Le donjon est terminé!");
    }

    public static Personnage creationPerso() {
        Scanner scanner = new Scanner(System.in);
        Affichage affichage = new Affichage();

        // FONCTION CREATION DE PERSONNAGE
        // le nom
        affichage.PersonnageAfficherMessage("Entrez votre nom");
        String nom = scanner.nextLine();

        // la race
        affichage.PersonnageAfficherMessage("\nChoisissez votre race :\n1 - Humain\n2 - Nain\n3 - Elfe\n4 - Halfelin");
        int raceNb = affichage.verifInt();
        Race race = null;
        switch (raceNb) {
            case 1 -> race = Race.HUMAIN;
            case 2 -> race = Race.NAIN;
            case 3 -> race = Race.ELFE;
            case 4 -> race = Race.HALFELIN;
        }

        // la classe
        affichage.PersonnageAfficherMessage("\nChoisissez votre classe :\n1 - Clerc\n2 - Guerrier\n3 - Magicien\n4 - Roublard ");
        Classe classe = null;
        int classeNb = affichage.verifInt();
        switch (classeNb) {
            case 1 -> classe = Classe.CLERC;
            case 2 -> classe = Classe.GUERRIER;
            case 3 -> classe = Classe.MAGICIEN;
            case 4 -> classe = Classe.ROUBLARD;
        }


        Personnage p = new Personnage(nom, race, classe);
        affichage.DDAfficherMessage("\n"+p.toString());
        return p;
    }

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

        affichage.mdjAfficherMessage("Combien de force souhaitez vous donner à votre monstre ?");
        int force = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien de dégâts souhaitez vous donner à votre monstre ?");
        int dexterite = affichage.verifInt();

        affichage.mdjAfficherMessage("Quelle armure souhaitez vous donner à votre monstre ?");
        int classe_armure = affichage.verifInt();

        affichage.mdjAfficherMessage("Combien d'initiative souhaitez vous donner à votre monstre ?");
        int initiative = affichage.verifInt();

        Monstre m = new Monstre(espece,portee,degats,nb_lances,pv,force,dexterite,classe_armure,initiative);
        m_monstres.add(m);
        affichage.DDAfficherMessage("\n"+m.toString());
    }

    public void actionsPersonnage(Personnage personnage, Donjon donjon)
    {
        Scanner scanner  = new Scanner(System.in);
        int nb_actions = 0;
        while (nb_actions < 3)
        {
            affichage.mdjAfficherMessage("Quelle action souhaitez-vous effectuer ?\n1 - équiper une arme\n2 - se déplacer\n3 - ramasser un équipement\n4 - attaquer un monstre");
            int numero_action = affichage.verifInt();
            scanner.nextLine();
            switch (numero_action)
            {
                case 1 ->
                {
                    affichage.mdjAfficherMessage("Quelle arme souhaitez vous équiper ?");
                    int num = affichage.verifInt();
                    personnage.equiper(num);
                }
                case 2 -> personnage.seDeplacer(donjon);
                case 3 -> personnage.ramasser(donjon);
                case 4 ->
                {
                    this.afficherMonstres();
                    affichage.mdjAfficherMessage("Quel monstre souhaitez-vous attaquer ?");
                    int num = affichage.verifInt();
                    affichage.mdjAfficherMessage(personnage.attaquer(m_monstres.get(num-1)));

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

    public void afficherMonstres()
    {
        affichage.mdjAfficherMessage("Voici les monstres en vie:\n");
        for(int i = 0; i < m_monstres.size();i++)
        {
            affichage.mdjAfficherMessage((i+1) +" - "+m_monstres.get(i).getNom() +"\n");
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


    //actions du joueur
    public boolean attaquer()
    {
        Scanner scanner = new Scanner(System.in);
        affichage.mdjAfficherMessage("Quel monstre souhaitez-vous attaquer ?");
        int num_monstre = affichage.verifInt();
        return true;
    }
}
package meneurDeJeu;

import deroulementDuDonjon.Donjon;
import entite.equipements.Arme;
import entite.equipements.Armure;
import entite.equipements.ListeEquipements;
import entite.Entite;
import entite.equipements.Equipement;
import entite.monstres.Monstre;
import entite.personnages.Classe;
import entite.personnages.Personnage;
import entite.personnages.Race;
import dice.Dice;
import affichage.*;

import java.util.*;

/**
 * Classe principale du Meneur de Jeu (MDJ) permettant de gérer la configuration du donjon
 */
public class MeneurDeJeu {
    // ===================== ATTRIBUTS =====================
    private List<Personnage> m_joueurs;
    private List<Monstre> m_monstres;
    private List<Equipement> m_equipements;
    private Map<String, Equipement> m_equipementsSurCarte = new HashMap<>();
    private HashMap<Entite, Integer> m_JoueursEtMonstresInitiative;
    private HashMap<Entite, Integer> m_OrdreJoueurs = new HashMap<Entite,Integer>();
    private Affichage affichage = new Affichage();

    // ===================== CONSTRUCTEUR =====================
    public MeneurDeJeu()
    {
        this.m_joueurs = new ArrayList<Personnage>();
        this.m_monstres = new ArrayList<Monstre>();
        this.m_equipements = new ArrayList<Equipement>();
    }

    // ===================== MÉTHODES POUR DONJON =====================
    /**
     * Gère l'intégralité de la création d'un donjon : personnages, monstres,
     * obstacles, équipements, placement et ordre de jeu.
     * @param donjon Le donjon à configurer.
     */
    public void creerDonjon(Donjon donjon) {
        Affichage affichage = new Affichage();
        Scanner scanner = new Scanner(System.in);

        affichage.introduction();

        affichage.mdjAfficherMessageAvecEntree("\nOhé visiteurs courageux ! Combien êtes vous à vouloir entrer dans ce donjon ?");
        creationPersonnagesPartie();
        affichage.transition();

        affichage.DDAfficherMessageAvecEntree("\nMDJ, créez vos montres!\nCombien de monstres souhaitez-vous créer ? (max 3)");
        creationMonstresPartie();
        affichage.transition();

        affichage.DDAfficherMessageAvecEntree("\nMDJ, créez les équipements!\nCombien d'équipements souhaitez-vous créer ? (max 3)");
        creationEquipementsPartie();
        affichage.transition();

        int choix = -1;
        while (choix != 1 && choix != 2) {
            affichage.DDAfficherMessageAvecEntree("\nMDJ, souhaitez vous utiliser:\n1 - un donjon pré-fait\n2 - en créer un vous même");
            choix = scanner.nextInt();
        }

        switch(choix) {
            case 1 -> {
                this.donjonPrefait(donjon);
                affichage.afficherDonjon(donjon);
            }
            case 2 -> {
                affichage.DDAfficherMessage("\nMDJ, où souhaitez vous placer les joueurs ?");
                choixPlacementPersonnage(donjon);
                affichage.transition();

                affichage.DDAfficherMessage("\nMDJ, où souhaitez-vous placer les monstres ?");
                choixPlacementMonstre(donjon);
                affichage.transition();

                affichage.DDAfficherMessage("\nMDJ, où souhaitez-vous placer les équipements ?");
                choixPlacementEquipement(donjon);
                affichage.transition();

                affichage.DDAfficherMessage("\nMDJ, combien d'obstacles souhaitez-vous placer votre donjon ?");
                creerObstacles(donjon);
                affichage.transition();
            }
        }


        //ON DETERMINE L'ORDRE DES JOUEURS
        affichage.DDAfficherMessage("Maintenant, passons à l'ordre de jeu de chaque personnage et monstre.\n" +
                "Il est déterminé par l'initiative de l'entité à laquelle on additionne le résultat d'un lancé de dé à 20 faces :\n");
        determinerOrdre();
        affichage.afficherOrdre(this);
    }

    /**
     * Génère un donjon avec un placement automatique des entités (pré-fait).
     * @param donjon Donjon à configurer automatiquement.
     */
    public void donjonPrefait(Donjon donjon)
    {
        int x = 9;
        int y = 9;
        for(int i = 0; i < m_joueurs.size();i++)
        {
            this.placerPersonnage(donjon,m_joueurs.get(i),x,y);
            y+=1;
        }
        for(int i = 0; i < m_monstres.size();i++)
        {
            this.placerMonstre(donjon,m_monstres.get(i),x+1,y);
            y+=1;
        }

    }

    // ===================== MÉTHODES POUR PERSONNAGE =====================
    /**
     * Crée l'ensemble des personnages joueurs à partir des saisies utilisateurs.
     */
    public void creationPersonnagesPartie()
    {
        int nb_joueurs = affichage.verifInt();
        for (int i = 0; i < nb_joueurs; i++) {
            affichage.afficherMessage("\nJOUEUR " + (i+1) + "/" + nb_joueurs + " :");
            this.m_joueurs.add(creationPersonnage());
            affichage.mdjAfficherMessage("\nEntrez dans le donjon, " + m_joueurs.get(i).getNom() + "\n");
            affichage.afficherInfoPersonnage(m_joueurs.get(i));
            affichage.transition();
        }
    }

    /**
     * Crée un personnage avec nom, race et classe à partir des choix utilisateur.
     * @return Le personnage créé.
     */
    public Personnage creationPersonnage() {
        Scanner scanner = new Scanner(System.in);
        Affichage affichage = new Affichage();

        affichage.mdjAfficherMessageAvecEntree("\nQuel est votre nom ?");
        String nom = scanner.nextLine();

        affichage.mdjAfficherMessageAvecEntree("\nQuelle est votre race :\n1 - Humain | 2 - Nain | 3 - Elfe | 4 - Halfelin");
        int raceNb = affichage.verifInt();
        Race race = null;
        switch (raceNb) {
            case 1 -> race = Race.HUMAIN;
            case 2 -> race = Race.NAIN;
            case 3 -> race = Race.ELFE;
            case 4 -> race = Race.HALFELIN;
        }

        affichage.mdjAfficherMessageAvecEntree("\nChoisissez votre classe :\n1 - Clerc | 2 - Guerrier | 3 - Magicien | 4 - Roublard ");
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

    /**
     * Permet au MDJ de placer les personnages joueurs manuellement sur la carte.
     * @param donjon Le donjon dans lequel les joueurs sont placés.
     */
    public void choixPlacementPersonnage(Donjon donjon) {
        for (int i = 0; i < m_joueurs.size(); i++) {
            int x = -1;
            int y = -1;
            Boolean caseLibre = false;
            while (!caseLibre) {
                affichage.DDAfficherMessageAvecEntree("Entrez les coordonnées pour placer le joueur suivant : " + m_joueurs.get(i).getNom() + " (Y, puis X):");
                x = affichage.verifInt();
                affichage.afficherMessage("> ");
                y = affichage.verifInt();
                if (donjon.getCase(x, y).equals("."))
                    caseLibre = true;
                else {
                    caseLibre = false;
                    affichage.afficherErreur("Erreur : cette case est occupée !");
                }
            }
            this.placerPersonnage(donjon, this.m_joueurs.get(i), x, y);

            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n");
            affichage.afficherDonjon(donjon);
        }
    }

    /**
     * Place un équipement sur une case spécifique du donjon.
     * @param donjon Donjon ciblé.
     * @param personnage Personnage à placer.
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @return true si le placement a réussi, false sinon.
     */
    public boolean placerPersonnage(Donjon donjon, Personnage personnage, int x, int y)
    {
        return donjon.placerJoueur(personnage, x, y);
    }

    // ===================== MÉTHODES POUR MONSTRE =====================
    /**
     * Crée l'ensemble des personnages à partir des saisies utilisateurs.
     */
    public void creationMonstresPartie()
    {
        int nb_monstres = affichage.verifInt();
        for (int i = 0; i < nb_monstres; i++) {
            affichage.afficherMessage("\nMONSTRE " + (i+1) + "/" + nb_monstres + " :");
            this.m_monstres.add(creationMonstre());
            affichage.mdjAfficherMessage("\nAttention, " + m_monstres.get(i).getNom() + " arrive !\n");
            affichage.afficherInfoMonstre(m_monstres.get(i));
            affichage.transition();
        }
    }

    /**
     * Crée un monstre à partir des statistiques renseignées par le MDJ.
     * @return Le monstre créé.
     */
    public Monstre creationMonstre()
    {
        int numero = m_monstres.size() +1;
        Scanner scanner = new Scanner(System.in);

        affichage.DDAfficherMessageAvecEntree("\nQuelle espèce souhaitez vous donner à votre monstre ?");
        String espece = scanner.nextLine();

        affichage.DDAfficherMessageAvecEntree("Quelle portée souhaitez vous donner à votre monstre ?");
        int portee = affichage.verifInt();

        affichage.DDAfficherMessageAvecEntree("Combien de dégâts souhaitez vous donner à votre monstre ? (sous la forme XdY)");
        String degats = scanner.nextLine();

        affichage.DDAfficherMessageAvecEntree("Combien de Points de vie souhaitez vous donner à votre monstre ?");
        int pv = affichage.verifInt();

        affichage.DDAfficherMessageAvecEntree("Combien de vitesse souhaitez vous donner à votre monstre ?");
        int vitesse = affichage.verifInt();

        affichage.DDAfficherMessageAvecEntree("Combien de force souhaitez vous donner à votre monstre ?");
        int force = affichage.verifInt();

        affichage.DDAfficherMessageAvecEntree("Combien de dextérité souhaitez vous donner à votre monstre ?");
        int dexterite = affichage.verifInt();

        affichage.DDAfficherMessageAvecEntree("Quelle armure souhaitez vous donner à votre monstre ?");
        int classe_armure = affichage.verifInt();

        affichage.DDAfficherMessageAvecEntree("Combien d'initiative souhaitez vous donner à votre monstre ?");
        int initiative = affichage.verifInt();

        Monstre m = new Monstre(espece, portee, degats, pv, vitesse, force, dexterite, classe_armure, initiative);
        return (m);
    }

    /**
     * Permet au MDJ de placer les monstres manuellement sur la carte.
     * @param donjon Le donjon dans lequel les monstres sont placés.
     */
    public void choixPlacementMonstre(Donjon donjon) {
        for (int i = 0; i < m_monstres.size(); i++) {
            int x = -1;
            int y = -1;
            Boolean caseLibre = false;
            while (!caseLibre) {
                affichage.DDAfficherMessageAvecEntree("Entrez les coordonnées pour placer le monstre suivant : " + m_monstres.get(i).getNom() + " (Y, PUIS X):");
                x = affichage.verifInt();
                affichage.afficherMessage("> ");
                y = affichage.verifInt();
                if (donjon.getCase(x, y).equals("."))
                    caseLibre = true;
                else {
                    caseLibre = false;
                    affichage.afficherErreur("Erreur : cette case est occupée !");
                }
            }
            this.placerMonstre(donjon, this.m_monstres.get(i), x, y);

            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n");
            affichage.afficherDonjon(donjon);
        }
    }

    /**
     * Place un équipement sur une case spécifique du donjon.
     * @param donjon Donjon ciblé.
     * @param monstre Monstre à placer.
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @return true si le placement a réussi, false sinon.
     */
    public boolean placerMonstre(Donjon donjon,Monstre monstre, int x, int y)
    {
        return donjon.placerMonstre(monstre,x,y);
    }

    // ===================== MÉTHODES POUR ÉQUIPEMENT =====================
    /**
     * Crée l'ensemble des équipements à partir des saisies utilisateurs.
     */
    public void creationEquipementsPartie()
    {
        int nb_equipements = affichage.verifInt();
        for (int i = 0; i < nb_equipements; i++) {
            affichage.afficherMessage("\nEQUIPEMENT " + (i+1) + "/" + nb_equipements + " :");
            this.m_equipements.add(creationEquipement());
            affichage.mdjAfficherMessage("\nOh, " + m_equipements.get(i).getNom() + " est trouvable dans le donjon !\n");
            affichage.afficherMessage(m_equipements.get(i).toString());
            affichage.transition();
        }
    }

    /**
     * Crée l'équipement choisi par le MDJ.
     * @return Le monstre créé.
     */
    public Equipement creationEquipement()
    {
        Scanner scanner = new Scanner(System.in);

        ListeEquipements[] equipementsDisponibles = ListeEquipements.values();
        affichage.DDAfficherMessage("\nVoici la liste des équipements disponibles :");

        for (int i = 0; i < equipementsDisponibles.length; i++) {
            affichage.DDAfficherMessage(i+1 + " - " + equipementsDisponibles[i].name());
        }

        int choix = -1;
        while (choix < 0 || choix >= equipementsDisponibles.length + 1) {
            affichage.DDAfficherMessageAvecEntree("Entrez le numéro de l'équipement que vous souhaitez créer :");
            choix = affichage.verifInt() - 1;
            if ((choix < 0 || choix >= equipementsDisponibles.length + 1))
                affichage.afficherErreur("Choix invalide. Veuillez réessayer.");
        }

        ListeEquipements equipementACreer = equipementsDisponibles[choix];
        Equipement nouveauEquipement;

        if (Arme.getListeArmes().containsKey(equipementACreer)) {
            nouveauEquipement = Arme.creerArme(equipementACreer);
        } else {
            nouveauEquipement = Armure.creerArmure(equipementACreer);
        }

        return nouveauEquipement;
    }

    /**
     * Permet au MDJ de placer les équipements manuellement sur la carte.
     * @param donjon Le donjon dans lequel les monstres sont placés.
     */
    public void choixPlacementEquipement(Donjon donjon)
    {
        for (int i = 0; i < m_equipements.size(); i++) {
            int x = -1;
            int y = -1;
            Boolean caseLibre = false;
            while (!caseLibre) {
                affichage.DDAfficherMessageAvecEntree("Entrez les coordonnées pour placer l'équipement suivant : " + m_equipements.get(i).getNom() + " (Y, PUIS X):");
                x = affichage.verifInt();
                affichage.afficherMessage("> ");
                y = affichage.verifInt();
                if (donjon.getCase(x, y).equals("."))
                    caseLibre = true;
                else {
                    caseLibre = false;
                    affichage.afficherErreur("Erreur : cette case est occupée !");
                }
            }
            this.placerEquipement(donjon, this.m_equipements.get(i), x, y);

            affichage.DDAfficherMessage("\n\nCarte mise à jour:\n");
            affichage.afficherDonjon(donjon);
        }
    }

    /**
     * Permet au MDJ de créer un nouvel équipement.
     */
    public void creationNouvelEquipementDonjon()
    {
        Scanner scanner = new Scanner(System.in);
        affichage.DDAfficherMessage("Meneur de jeu, créez les équipements qui seront présents dans le donjon\nCombien d'équipements souhaitez-vous créer ?");
        int nbEquipements = scanner.nextInt();
        for(int i = 0; i < nbEquipements;i++)
        {
            affichage.DDAfficherMessage("Quel nom souhaitez vous donner à votre arme ?\n");
            String nom = scanner.nextLine();
            affichage.DDAfficherMessage("Combien de dégâts souhaitez-vous donner à votre arme? (XdY)\n");
            String degats = scanner.nextLine();
            affichage.DDAfficherMessage("Quelle portée souhaitez-vous donner à votre arme?\n");
            int portee = affichage.verifInt();
            affichage.DDAfficherMessage("Votre arme est-elle légère ou lourde? 1 - si lourde, 2 - si légère\n");
            int lourdelegere = affichage.verifInt();
            boolean estlourde = true;
            if(lourdelegere == 2){estlourde = false;}
            Arme a = new Arme(nom,degats,portee,estlourde);
        }
    }

    /**
     * Place un équipement sur une case spécifique du donjon.
     * @param donjon Donjon ciblé.
     * @param equipement Équipement à placer.
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @return true si le placement a réussi, false sinon.
     */
    public boolean placerEquipement(Donjon donjon, Equipement equipement, int x, int y)
    {
        m_equipementsSurCarte.put(x + "-" + y, equipement);
        return donjon.placerEquipement(equipement, x,y);
    }


    // ===================== MÉTHODES POUR OBSTACLES =====================
    /**
     * Permet au MJ de créer un certain nombre d'obstacles dans le donjon.
     * @param donjon Le donjon concerné.
     */
    public void creerObstacles(Donjon donjon) {
        int nb_obstacles = affichage.verifInt();
        for (int i = 0; i < nb_obstacles; i++) {
            affichage.afficherMessage("\nOBSTACLE " + (i+1) +"/" + nb_obstacles +" :");
            affichage.DDAfficherMessageAvecEntree("\nOù voulez-vous placer l'obstacle? (Y puis X)");
            int x = affichage.verifInt();
            affichage.afficherMessage("> ");
            int y = affichage.verifInt();
            this.placerObstacle(donjon, x, y);
            affichage.DDAfficherMessage("\n\nCarte mise à jour : \n");
            affichage.afficherDonjon(donjon);
        }
    }

    /**
     * Place un obstacle sur une case spécifique du donjon.
     * @param donjon Donjon ciblé.
     * @param x Coordonnée X.
     * @param y Coordonnée Y.
     * @return true si le placement a réussi, false sinon.
     */
    public Boolean placerObstacle(Donjon donjon, int x, int y)
    {
        return donjon.placerObstacle(x, y);
    }

    // ===================== MÉTHODES POUR ÉTAT DU JEU =====================
    /**
     * Calcule l'ordre de jeu des entités selon leur initiative et un lancer de dé.
     */
    public void determinerOrdre() {
        m_JoueursEtMonstresInitiative = new HashMap<>();
        Dice de = new Dice();
        //on remplit la hashmap
        for (int i = 0; i < m_joueurs.size(); i++) {
            m_JoueursEtMonstresInitiative.put(m_joueurs.get(i), m_joueurs.get(i).getStats().getInitiative() + de.lancer("1d20"));
        }
        for (int i = 0; i < m_monstres.size(); i++) {
            m_JoueursEtMonstresInitiative.put(m_monstres.get(i), m_monstres.get(i).getInitiative() + de.lancer("1d20"));
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

    /**
     * Vérifie si tous les joueurs sont encore en vie.
     * @param donjon Le donjon contenant les entités.
     * @return true s'il reste au moins un joueur en vie, false sinon.
     */
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

    /**
     * Vérifie si tous les monstres sont encore en vie et marque les morts sur la carte.
     * @param donjon Le donjon contenant les entités.
     * @return true s'il reste au moins un monstre en vie, false sinon.
     */
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

    // ===================== GETTERS =====================
    /**
     * Accesseur de la liste des personnages joueurs.
     * @return Liste des joueurs.
     */
    public List<Personnage> getJoueurs(){return m_joueurs;}

    /**
     * Accesseur de la liste des monstres du donjon.
     * @return Liste des monstres.
     */
    public List<Monstre> getMonstres(){return m_monstres;}

    public Map<String, Equipement> getEquipementsSurCarte() {
        return m_equipementsSurCarte;
    }

    /**
     * Accesseur de l'ordre de jeu des entités.
     * @return HashMap contenant l'ordre de jeu.
     */
    public HashMap<Entite,Integer> getOrdre(){return m_OrdreJoueurs;}

}
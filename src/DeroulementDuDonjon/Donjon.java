package DeroulementDuDonjon;


import Entite.Personnages.Personnage;
import Entite.Monstres.Monstre;
import Entite.Equipement.Equipement;

/**
 * Classe représentant un Donjon sous forme de grille 25x25.
 */
public class Donjon
    {
        // ===================== ATTRIBUT =====================
        private String[][] m_carte;

        // ===================== CONSTRUCTEUR =====================
        /**
         * Constructeur : initialise la carte 25x25 avec des "." pour signifier des cases vides.
         */
        public Donjon()
        {
            this.m_carte = new String[25][25];
            for(int i = 0; i < this.m_carte.length; i++)
            {
                for(int j = 0; j < this.m_carte[i].length; j++)
                {
                    m_carte[i][j] = ".";
                }
            }
        }

        // ===================== MÉTHODES =====================
        /**
         * Modifie le contenu d'une case donnée de la carte.
         * @param x Coordonnée en X.
         * @param y Coordonnée en Y.
         * @param caractere Symbole à placer dans la case.
         */
        public void changeCase(int x, int y, String caractere)
        {
            this.m_carte[x][y] = caractere;
        }

        /**
         * Place un obstacle à la position spécifiée si la case est vide.
         * @param x Coordonnée en X.
         * @param y Coordonnée en Y.
         * @return true si l'obstacle a été placé, false sinon.
         */
        public boolean placerObstacle(int x, int y)
        {
            if (x >= 0 && x < this.m_carte.length && y >= 0 && y < this.m_carte[0].length)
            {
                if(this.m_carte[x][y] == ".")
                {
                    this.m_carte[x][y] = "[]";
                    return true;
                }
            }
            return false;
        }

        /**
         * Place un joueur à la position spécifiée si la case est vide.
         * @param x Coordonnée en X.
         * @param y Coordonnée en Y.
         * @return true si le joueur a été placé, false sinon.
         */
        public boolean placerJoueur(Personnage personnage,int x, int y)
        {
            if (x >= 0 && x < this.m_carte.length && y >= 0 && y < this.m_carte[0].length)
            {
                if(this.m_carte[x][y] == "." || this.m_carte[x][y] == "*" )
                {
                    if(personnage.getNom().length() > 3)
                    {
                        this.m_carte[x][y] = personnage.getNom().substring(0, 3);
                        personnage.setCoordonnees(x, y);
                        return true;
                    }
                    this.m_carte[x][y] = personnage.getNom().substring(0, personnage.getNom().length());
                    return true;
                }
            }
            return false;
        }

        /**
         * Place un monstre à la position spécifiée si la case est vide.
         * @param x Coordonnée en X.
         * @param y Coordonnée en Y.
         * @return true si le monstre a été placé, false sinon.
         */
        public boolean placerMonstre(Monstre monstre,int x, int y)
        {
            if (x >= 0 && x < this.m_carte.length && y >= 0 && y < this.m_carte[0].length)
            {
                if(this.m_carte[x][y] == "." )
                {
                    if(monstre.getNom().length() > 3)
                    {
                        this.m_carte[x][y] = monstre.getNom().substring(0, 3);
                        monstre.setCoordonnees(x, y);
                        return true;
                    }
                    this.m_carte[x][y] = monstre.getNom().substring(0, monstre.getNom().length());
                    return true;
                }
            }
            return false;
        }

        /**
         * Place un équipement à la position spécifiée si la case est vide.
         * @param x Coordonnée en X.
         * @param y Coordonnée en Y.
         * @return true si l'équipement a été placé, false sinon.
         */
        public boolean placerEquipement(Equipement equipement,int x, int y)
        {
            if (x >= 0 && x < this.m_carte.length && y >= 0 && y < this.m_carte[0].length)
            {
                if(this.m_carte[x][y] == "." )
                {
                    this.m_carte[x][y] = "*";
                    return true;
                }
            }
            return false;
        }

        // ===================== GETTERS =====================
        /**
         * Retourne la taille de la carte (la grille est carrée).
         * @return la longueur d'un côté de la carte (25).
         */
        public int getTailleCarte()
        {
            return this.m_carte.length;
        }
        /**
         * Retourne le contenu d'une case donnée de la carte.
         * @param x Coordonnée en X.
         * @param y Coordonnée en Y.
         * @return Le symbole présent à la case (x, y).
         */
        public String getCase(int x, int y)
        {
            return this.m_carte[x][y];
        }
    }

package DeroulementDuDonjon;


import Entite.Personnages.Personnage;
import Entite.Monstres.Monstre;
import Entite.Equipement.Equipement;

public class Donjon
    {
        private String[][] m_carte;
        private int m_x;
        private int m_y;

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

        public int getTailleCarte()
        {
            return this.m_carte.length;
        }

        public String getCase(int x, int y)
        {
            return this.m_carte[x][y];
        }

        public void changeCase(int x, int y, String caractere)
        {
            this.m_carte[x][y] = caractere;
        }

        //placement des éléments sur la carte

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

        public boolean placerJoueur(Personnage personnage,int x, int y)
        {
            if (x >= 0 && x < this.m_carte.length && y >= 0 && y < this.m_carte[0].length)
            {
                if(this.m_carte[x][y] == "." )
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
    }

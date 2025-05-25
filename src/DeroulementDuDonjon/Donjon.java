package DeroulementDuDonjon;


import Entite.Personnages.Personnage;
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

        public String afficherDonjon()
        {
            int count_colonne = 0;
            int count_ligne = 0;
            String affichage = "";
                affichage+= "   A\tB\tC\tD\tF\tG\tH\tI\tJ\tK\tL\tM\tN\tO\tP\tQ\tR\tS\tT\tU\tV\tW\tX\tY\tZ\n";
            for(int i = 0; i < this.m_carte.length ;i++)
            {
                affichage+= count_colonne + "\t";
                count_colonne+=1;
                for(int j = 0; j < this.m_carte[i].length ;j++)
                {
                    affichage += this.m_carte[i][j] + "\t";
                }
                affichage += "\n";
            }
            return affichage;
        }

        public String getCase(int x, int y)
        {
            return this.m_carte[x][y];
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

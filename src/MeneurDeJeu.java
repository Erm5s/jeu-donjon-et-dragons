import DeroulementDuDonjon.Donjon;
import Entite.Equipement.Equipement;
import Entite.Personnages.Personnage;

public class MeneurDeJeu
{
    public void creerDonjon()
    {
        Donjon donjon_actuel  = new Donjon();
    }

    public Boolean placerObstacle(Donjon donjon, int x, int y)
    {
        return donjon.placerObstacle(x,y);
    }

    public Boolean placerJoueur(Donjon donjon, Personnage personnage, int x, int y)
    {
        return donjon.placerJoueur(personnage, x, y);
    }

    public Boolean placerEquipement(Donjon donjon, Equipement equipement, int x, int y)
    {
        return donjon.placerEquipement(equipement, x, y);
    }

}

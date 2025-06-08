import DeroulementDuDonjon.Donjon;
import MeneurDeJeu.MeneurDeJeu;
import DeroulementDuDonjon.Tour;
public class Main
{
    public static void main(String args[])
    {
        MeneurDeJeu mdj  = new MeneurDeJeu();
        Tour t = new Tour();
        Donjon donjon1 = new Donjon();
        mdj.creerDonjon(donjon1);
        t.jouerDonjon(donjon1, mdj);
    }
}
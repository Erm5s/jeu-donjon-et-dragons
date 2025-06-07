import java.util.ArrayList;
import java.util.Scanner;

import DeroulementDuDonjon.Donjon;
import Entite.Personnages.*;
import java.util.List;

public class Main
{
    public static void main(String args[])
    {
        MeneurDeJeu mdj  = new MeneurDeJeu();
        Donjon donjon1 = new Donjon();
        mdj.creationJoueursPartie();
        mdj.creerDonjon(donjon1);
        mdj.jouerDonjon(donjon1);
    }
}
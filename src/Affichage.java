import DeroulementDuDonjon.Donjon;

public class Affichage
{
    public void afficherMessage(String message)
    {
        System.out.println(message);
    }

    public void afficherDonjon(Donjon donjon)
    {
        int count_colonne = 0;
        int count_ligne = 0;
        String affichage = "\t";
        for(int i = 0; i < donjon.getTailleCarte();i++)
        {
            affichage+= count_colonne + "\t";
            count_colonne+=1;
        }
        affichage += "\n";
        for(int i = 0; i < donjon.getTailleCarte() ;i++)
        {
            affichage += count_ligne + "\t";
            count_ligne+=1;
            for(int j = 0; j < donjon.getTailleCarte() ;j++)
            {
                affichage += donjon.getCase(i,j) + "\t";
            }
            affichage += "\n";
        }
        System.out.println(affichage);
    }
}

package Personnages;

public class Personnage {
    private final String m_nom;
    private final Race m_race;
    private final Classe m_classe;
    private Caracteristique m_caract;
    /*
    private List<Equipement> inventaire;
    private Arme armeEquipee;
    private Armure armureEquipee;*/

    public Personnage(String nom, Race race, Classe classe) {
        m_nom = nom;
        m_race = race;
        m_classe = classe;
        m_caract = new Caracteristique(this);
        /*- m_inventaire : Array<Equipement>*/
    }

    public String getNom() {
        return this.m_nom;
    }

    public Race getRace() {
        return this.m_race;
    }

    public Classe getClasse() {
        return this.m_classe;
    }

    public String afficheStats() {
        String stats = "";
        stats += "PV         : " + m_caract.getPV();
        stats += "\nForce      : " + m_caract.getDexterite();
        stats += "\nVitesse    : " + m_caract.getVitesse();
        stats += "\nInitiative : " + m_caract.getInitiative();
        return stats;
    }
}

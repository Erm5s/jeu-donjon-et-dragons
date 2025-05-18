package Entite.Monstres;

import Entite.Personnages.Caracteristique;

public class Monstre {
    private String m_espece;
    private int m_numero;
    private Attaque m_attaque;
    private Caracteristique m_caract;
    private Boolean m_estCac;

    public Monstre (String espece, int numero, Attaque attaque, Boolean estCac) {
        m_espece = espece;
        m_numero = numero;
        m_attaque = attaque;
        m_caract = new Caracteristique(this);
    }

    public Boolean getEstCac() {
        return m_estCac;
    }

}

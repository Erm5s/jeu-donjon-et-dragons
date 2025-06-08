package Entite.Equipement;

import Entite.Entite;

/**
 * Classe abstraite représentant un équipement générique (arme, armure).
 * Tous les équipements doivent fournir un nom, un type et une méthode d’affichage.
 */
public abstract class Equipement extends Entite {
    // ===================== METHODES ABSTRAITES =====================

    /**
     * Affiche une représentation textuelle de l’équipement
     * @return chaîne décrivant l’équipement
     */
    @Override
    public abstract String toString();

    /**
     * @return le type de l'équipement
     */
}

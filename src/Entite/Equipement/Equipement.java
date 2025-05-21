package Entite.Equipement;


import Entite.Entite;

public abstract class Equipement extends Entite {

    public abstract String getNom();
    public abstract TypeEquipement getTypeEquipement();
    @Override
    public abstract String toString();
}

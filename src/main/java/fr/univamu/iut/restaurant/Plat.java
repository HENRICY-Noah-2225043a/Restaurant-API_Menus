package fr.univamu.iut.restaurant;

public class Plat {

    private int id;
    private String nom;
    String description;
    private double prix;
    boolean disponible;
    String categorie;

    public Plat(int id, String nom, String description, double prix, boolean disponible, String categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.disponible = disponible;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", disponible=" + disponible +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
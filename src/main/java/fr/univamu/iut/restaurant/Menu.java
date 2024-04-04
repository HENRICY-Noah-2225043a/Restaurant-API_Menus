package fr.univamu.iut.restaurant;

import java.util.Date;
import java.util.List;

public class Menu {

    int id;
    String author;
    String description;
    Date creation_date;
    double prix;
    List<Integer> plats;

    public Menu(String author, String description, double prix, Date creation_date) {

        this.author = author;
        this.description = description;
        this.creation_date = creation_date;
        this.plats = getPlats();

    }

    public Menu(int id, String author, String description, double prix, Date creation_date) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.creation_date = creation_date;
        this.prix = prix;
        this.plats = getPlats();
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public List<Integer> getPlats() {
        // requete pour récuperer l'id des plats dans la table menu_plat de la base de
        // données des plats
        // je vais recevoir une string que je vais split pour avoir une liste de plats

        String stringPlat = /* requete ici */ null;

    }

    public void setCreation_date(Date localTime) {
        this.creation_date = localTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", author='" + author + '\'' + ", description='" + description + '\''
                + ", creation_date=" + creation_date + ", prix=" + prix + '}';
    }

}

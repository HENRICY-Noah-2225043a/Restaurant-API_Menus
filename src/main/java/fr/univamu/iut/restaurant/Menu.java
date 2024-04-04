package fr.univamu.iut.restaurant;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class Menu {

    int id;
    String author;
    String description;
    Date creation_date;
    double prix;
    Plat entree;
    Plat plat;
    Plat dessert;

    public Menu(String author, String description, Date creation_date) {

        this.author = author;
        this.description = description;
        this.creation_date = creation_date;

    }

    public Menu(int id, String author, String description, double prix, Date creation_date, int entree, int plat,
            int dessert) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.creation_date = creation_date;
        try {
            this.entree = getPlat(entree);
            this.plat = getPlat(plat);
            this.dessert = getPlat(dessert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.prix = this.entree.getPrix() + this.plat.getPrix() + this.dessert.getPrix();

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

    public void setEntree(Plat entree) {
        this.entree = entree;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public void setDessert(Plat dessert) {
        this.dessert = dessert;
    }

    public Plat getPlat(int id) throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/restaurant/api/plat/" + id);
        HttpResponse response = client.execute(request);
        ObjectMapper mapper = new ObjectMapper();
        Plat plat = mapper.readValue(response.getEntity().getContent(), Plat.class);
        return plat;
    }

    public Plat getEntree() {
        return entree;
    }

    public Plat getPlat() {
        return plat;
    }

    public Plat getDessert() {
        return dessert;
    }

    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", author='" + author + '\'' + ", description='" + description + '\''
                + ", creation_date=" + creation_date + ", prix=" + prix + '}';
    }

}

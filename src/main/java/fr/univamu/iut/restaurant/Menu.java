package fr.univamu.iut.restaurant;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

/**
 * Cette classe représente un menu dans un restaurant.
 */
public class Menu {

    // Attributs de la classe Menu
    int id;
    String author;
    String description;
    Date creation_date;
    double prix;
    Plat entree;
    Plat plat;
    Plat dessert;

    /**
     * Constructeur de Menu avec auteur, description et date de création.
     * 
     * @param author        L'auteur du menu.
     * @param description   La description du menu.
     * @param creation_date La date de création du menu.
     */
    public Menu(String author, String description, Date creation_date) {
        this.author = author;
        this.description = description;
        this.creation_date = creation_date;
    }

    /**
     * Constructeur de Menu avec tous les détails.
     * 
     * @param id            L'identifiant du menu.
     * @param author        L'auteur du menu.
     * @param description   La description du menu.
     * @param prix          Le prix du menu.
     * @param creation_date La date de création du menu.
     * @param entree        L'entrée du menu.
     * @param plat          Le plat principal du menu.
     * @param dessert       Le dessert du menu.
     */
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
            this.prix = this.entree.getPrix() + this.plat.getPrix() + this.dessert.getPrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters et Setters

    /**
     * Méthode pour obtenir l'identifiant du menu.
     * 
     * @return L'identifiant du menu.
     */
    public int getId() {
        return id;
    }

    /**
     * Méthode pour obtenir l'auteur du menu.
     * 
     * @return L'auteur du menu.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Méthode pour obtenir la description du menu.
     * 
     * @return La description du menu.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Méthode pour obtenir le prix du menu.
     * 
     * @return Le prix du menu.
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Méthode pour obtenir la date de création du menu.
     * 
     * @return La date de création du menu.
     */
    public Date getCreation_date() {
        return creation_date;
    }

    /**
     * Méthode pour définir la date de création du menu.
     * 
     * @param localTime La nouvelle date de création du menu.
     */
    public void setCreation_date(Date localTime) {
        this.creation_date = localTime;
    }

    /**
     * Méthode pour définir l'identifiant du menu.
     * 
     * @param id Le nouvel identifiant du menu.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode pour définir l'auteur du menu.
     * 
     * @param author Le nouvel auteur du menu.
     */
    public void setNom(String author) {
        this.author = author;
    }

    /**
     * Méthode pour définir la description du menu.
     * 
     * @param description La nouvelle description du menu.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Méthode pour définir le prix du menu.
     * 
     * @param prix Le nouveau prix du menu.
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * Méthode pour définir l'entrée du menu.
     * 
     * @param entree La nouvelle entrée du menu.
     */
    public void setEntree(Plat entree) {
        this.entree = entree;
    }

    /**
     * Méthode pour définir le plat principal du menu.
     * 
     * @param plat Le nouveau plat principal du menu.
     */
    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    /**
     * Méthode pour définir le dessert du menu.
     * 
     * @param dessert Le nouveau dessert du menu.
     */
    public void setDessert(Plat dessert) {
        this.dessert = dessert;
    }

    /**
     * Méthode pour obtenir l'entrée du menu.
     * 
     * @return L'entrée du menu.
     */
    public Plat getEntree() {
        return entree;
    }

    /**
     * Méthode pour obtenir le plat principal du menu.
     * 
     * @return Le plat principal du menu.
     */
    public Plat getPlat() {
        return plat;
    }

    /**
     * Méthode pour obtenir le dessert du menu.
     * 
     * @return Le dessert du menu.
     */
    public Plat getDessert() {
        return dessert;
    }

    // Autres méthodes

    /**
     * Méthode pour récupérer un plat à partir de son identifiant.
     * 
     * @param id L'identifiant du plat à récupérer.
     * @return Le plat correspondant à l'identifiant spécifié.
     * @throws Exception Si une erreur se produit lors de la récupération du plat.
     */
    public Plat getPlat(int id) throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(
                "http://localhost:8080/Restaurant_API_Plats-110658575666960537751.0-SNAPSHOT/api/plats/" + id);
        request.addHeader("Accept", "application/json");
        HttpResponse response = client.execute(request);
        ObjectMapper mapper = new ObjectMapper();
        Plat plat = mapper.readValue(response.getEntity().getContent(), Plat.class);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception(
                    "Erreur lors de la récupération du plat de l'API : " + response.getStatusLine().getStatusCode());
        }
        if (plat == null) {
            throw new Exception("Erreur lors de la récupération du plat de l'API : plat null");
        }
        System.out.println(plat);
        return plat;
    }

    /**
     * Méthode pour obtenir une représentation sous forme de chaîne de caractères du
     * menu.
     * 
     * @return Une chaîne de caractères représentant le menu.
     */
    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", author='" + author + '\'' + ", description='" + description + '\''
                + ", creation_date=" + creation_date + ", prix=" + prix + ", entree=" + entree + ", plat=" + plat
                + ", dessert=" + dessert + '}';
    }
}

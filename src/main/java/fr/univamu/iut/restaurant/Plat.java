package fr.univamu.iut.restaurant;

/**
 * Classe représentant un plat
 */
public class Plat {

    /**
     * Identifiant du plat
     */
    protected int id;

    /**
     * Nom du plat
     */
    protected String nom;

    /**
     * Description du plat
     */
    protected String description;

    /**
     * Prix du plat
     */
    protected double prix;

    /**
     * Catégorie du plat (ex: "entrée", "plat principal", "dessert")
     */
    protected String categorie;

    /**
     * Statut du plat (disponible/indisponible)
     */
    protected boolean disponible;

    /**
     * Constructeur par défaut
     */
    public Plat() {
    }

    /**
     * Constructeur d'un plat
     * 
     * @param id          identifiant du plat
     * @param nom         nom du plat
     * @param description description du plat
     * @param prix        prix du plat
     * @param categorie   catégorie du plat
     */
    public Plat(int id, String nom, String description, double prix, String categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.categorie = categorie;
        this.disponible = true;
    }

    // Accesseurs et mutateurs (getters et setters)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Méthode toString

    @Override
    public String toString() {
        return "Plat{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", categorie='" + categorie + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}

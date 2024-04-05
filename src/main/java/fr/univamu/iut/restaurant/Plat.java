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

    /**
     * Méthode permettant d'obtenir l'identifiant du plat.
     * 
     * @return L'identifiant du plat.
     */
    public int getId() {
        return id;
    }

    /**
     * Méthode permettant de définir l'identifiant du plat.
     * 
     * @param id Le nouvel identifiant du plat.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode permettant d'obtenir le nom du plat.
     * 
     * @return Le nom du plat.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode permettant de définir le nom du plat.
     * 
     * @param nom Le nouveau nom du plat.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Méthode permettant d'obtenir la description du plat.
     * 
     * @return La description du plat.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Méthode permettant de définir la description du plat.
     * 
     * @param description La nouvelle description du plat.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Méthode permettant d'obtenir le prix du plat.
     * 
     * @return Le prix du plat.
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Méthode permettant de définir le prix du plat.
     * 
     * @param prix Le nouveau prix du plat.
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }

    /**
     * Méthode permettant d'obtenir la catégorie du plat.
     * 
     * @return La catégorie du plat.
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Méthode permettant de définir la catégorie du plat.
     * 
     * @param categorie La nouvelle catégorie du plat.
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Méthode permettant de savoir si le plat est disponible ou non.
     * 
     * @return true si le plat est disponible, sinon false.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Méthode permettant de définir si le plat est disponible ou non.
     * 
     * @param disponible Le statut du plat (true si disponible, sinon false).
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Méthode pour obtenir une représentation sous forme de chaîne de caractères du
     * plat.
     * 
     * @return Une chaîne de caractères représentant le plat.
     */
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

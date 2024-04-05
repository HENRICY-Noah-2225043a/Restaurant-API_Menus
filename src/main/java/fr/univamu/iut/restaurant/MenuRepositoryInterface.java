package fr.univamu.iut.restaurant;

import java.util.*;

/**
 * Interface d'accès aux données des livres
 */
public interface MenuRepositoryInterface {

    /**
     * Méthode pour obtenir un menu à partir de son identifiant.
     * 
     * @param id L'identifiant du menu à récupérer.
     * @return Le menu correspondant à l'identifiant spécifié.
     */
    Menu getMenu(int id);

    /**
     * Méthode pour obtenir tous les menus.
     * 
     * @return Une liste contenant tous les menus.
     */
    ArrayList<Menu> getAllMenu();

    /**
     * Méthode pour mettre à jour un menu existant.
     * 
     * @param id          L'identifiant du menu à mettre à jour.
     * @param author      Le nouvel auteur du menu.
     * @param description La nouvelle description du menu.
     * @param prix        Le nouveau prix du menu.
     * @return true si la mise à jour est réussie, sinon false.
     */
    boolean updateMenu(int id, String author, String description, double prix);

    /**
     * Méthode pour supprimer un menu.
     * 
     * @param id L'identifiant du menu à supprimer.
     * @return true si la suppression est réussie, sinon false.
     */
    boolean deleteMenu(int id);

    /**
     * Méthode pour créer un nouveau menu.
     * 
     * @param menu Le menu à créer.
     * @return true si la création est réussie, sinon false.
     */
    boolean createMenu(Menu menu);

    /**
     * Méthode pour fermer la connexion ou libérer les ressources associées.
     */
    void close();
}

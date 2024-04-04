package fr.univamu.iut.restaurant;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class MenuService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les
     * menus
     */
    private MenuRepositoryInterface menuRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     *
     * @param menuRepo objet implémentant l'interface d'accès aux données
     */
    public MenuService(MenuRepositoryInterface menuRepo) {
        this.menuRepo = menuRepo;
    }

    /**
     * Méthode retournant les informations sur les menus au format JSON
     *
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllMenusJSON() {

        ArrayList<Menu> allMenus = menuRepo.getAllMenu();

        // Création du JSON et conversion de la liste de menus
        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allMenus);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /*
     * Méthode retournant au format JSON les informations sur un menu recherché
     *
     * @param id id du menu recherché
     * 
     * @return une chaîne de caractère contenant les informations au format JSON
     */

    public String getMenuJSON(int id) throws SQLException, ClassNotFoundException {
        menuRepo = new MenuRepositoryMariaDB(
                "jdbc:mariadb://mysql-archilogicieltibo.alwaysdata.net/archilogicieltibo_restaurantmenu",
                "345226_menu", "Deliveroo$");
        String result = null;
        Menu myMenu = menuRepo.getMenu(id);

        // Si le menu a été trouvé
        if (myMenu != null) {

            // Création du JSON et conversion du menu
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myMenu);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return result;
    }

    /**
     * Méthode permettant de mettre à jours les informations d'un menu
     *
     * @param id   id du menu à mettre à jours
     * @param menu les nouvelles informations à été utiliser
     * @return true si le menu a pu être mis à jours
     */
    public boolean updateMenu(int id, Menu menu) {
        return menuRepo.updateMenu(id, menu.getAuthor(), menu.getDescription(), menu.getPrix());
    }

    /**
     * Méthode permettant de créer un menu
     *
     * @param menu les informations du menu à créer
     * @return true si le menu a pu être créé
     */
    public boolean createMenu(Menu menu) {
        return menuRepo.createMenu(menu);
    }
}
package fr.univamu.iut.restaurant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Classe principale de l'application REST.
 * Définit le chemin de base de l'API REST et les méthodes pour la gestion de la
 * connexion à la base de données.
 */
@ApplicationPath("/api")
@ApplicationScoped
public class HelloApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connexion à la base de données
     * au moment de la création de la ressource.
     *
     * @return Un objet implémentant l'interface MenuRepositoryInterface utilisée
     *         pour accéder aux données des menus.
     */
    @Produces
    private MenuRepositoryInterface openDbConnection() {
        MenuRepositoryMariaDB db = null;
        try {
            db = new MenuRepositoryMariaDB(
                    "jdbc:mariadb://mysql-archilogicieltibo.alwaysdata.net/archilogicieltibo_restaurantmenu",
                    "345226_menu", "Deliveroo$");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque
     * l'application est arrêtée.
     *
     * @param menuRepo La connexion à la base de données instanciée dans la méthode
     *                 openDbConnection.
     */
    private void closeDbConnection(@Disposes MenuRepositoryInterface menuRepo) {
        menuRepo.close();
    }
}

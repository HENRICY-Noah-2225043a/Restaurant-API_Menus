package fr.univamu.iut.restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe implémentant l'interface MenuRepositoryInterface pour accéder aux
 * données des menus depuis une base de données MariaDB.
 */
public class MenuRepositoryMariaDB implements MenuRepositoryInterface {

    protected Connection dbConnection;

    /**
     * Constructeur de la classe MenuRepositoryMariaDB.
     * 
     * @param infoConnection Les informations de connexion à la base de données (URL
     *                       de connexion).
     * @param user           Le nom d'utilisateur pour la connexion à la base de
     *                       données.
     * @param pwd            Le mot de passe pour la connexion à la base de données.
     * @throws SQLException           Si une erreur SQL survient lors de la
     *                                connexion.
     * @throws ClassNotFoundException Si la classe du pilote JDBC n'est pas trouvée.
     */
    public MenuRepositoryMariaDB(String infoConnection, String user, String pwd)
            throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     * Méthode pour fermer la connexion à la base de données.
     */
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Méthode pour récupérer un menu à partir de son identifiant.
     * 
     * @param id L'identifiant du menu à récupérer.
     * @return Le menu correspondant à l'identifiant spécifié.
     */
    @Override
    public Menu getMenu(int id) {

        Menu selectedMenu = null;

        String query = "SELECT * FROM menu WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                int idMenu = result.getInt("id");
                String description = result.getString("description");
                String author = result.getString("author");
                double prix = result.getDouble("prix");
                Date creation_date = result.getDate("creation_date");
                int idEntree = result.getInt("entree");
                int idPlat = result.getInt("plat");
                int idDessert = result.getInt("dessert");

                selectedMenu = new Menu(idMenu, author, description, prix, creation_date, idEntree, idPlat, idDessert);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedMenu;
    }

    /**
     * Méthode pour récupérer tous les menus depuis la base de données.
     * 
     * @return Une liste contenant tous les menus.
     */
    @Override
    public ArrayList<Menu> getAllMenu() {
        ArrayList<Menu> listMenu;

        String query = "SELECT * FROM menu";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            listMenu = new ArrayList<>();

            while (result.next()) {
                int idMenu = result.getInt("id");
                String description = result.getString("description");
                String author = result.getString("author");
                double prix = result.getDouble("prix");
                Date creation_date = result.getDate("creation_date");
                int idEntree = result.getInt("entree");
                int idPlat = result.getInt("plat");
                int idDessert = result.getInt("dessert");
                listMenu.add(new Menu(idMenu, author, description, prix, creation_date, idEntree, idPlat, idDessert));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMenu;
    }

    /**
     * Méthode pour mettre à jour un menu existant dans la base de données.
     * 
     * @param id          L'identifiant du menu à mettre à jour.
     * @param author      Le nouvel auteur du menu.
     * @param description La nouvelle description du menu.
     * @param prix        Le nouveau prix du menu.
     * @return true si la mise à jour est réussie, sinon false.
     */
    @Override
    public boolean updateMenu(int id, String author, String description, double prix) {

        String query = "UPDATE Menu SET author=?, description=?, prix=? WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, author);
            ps.setString(2, description);
            ps.setDouble(3, prix);

            int result = ps.executeUpdate();

            Menu currentMenu = getMenu(id);
            currentMenu.setCreation_date(new Date());

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode pour supprimer un menu de la base de données.
     * 
     * @param id L'identifiant du menu à supprimer.
     * @return true si la suppression est réussie, sinon false.
     */
    @Override
    public boolean deleteMenu(int id) {

        String query = "DELETE FROM Menu WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            int result = ps.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode pour créer un nouveau menu dans la base de données.
     * 
     * @param menu Le menu à créer.
     * @return true si la création est réussie, sinon false.
     */
    public boolean createMenu(Menu menu) {

        String query = "INSERT INTO Menu (author, description, entree, plat, dessert) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, menu.getAuthor());
            ps.setString(2, menu.getDescription());
            ps.setInt(3, menu.getEntree().id);
            ps.setInt(4, menu.getPlat().id);
            ps.setInt(5, menu.getDessert().id);

            int result = ps.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du menu" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

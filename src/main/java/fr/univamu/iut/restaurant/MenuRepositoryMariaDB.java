package fr.univamu.iut.restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class MenuRepositoryMariaDB implements MenuRepositoryInterface {

    protected Connection dbConnection;

    public MenuRepositoryMariaDB(String infoConnection, String user, String pwd)
            throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

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
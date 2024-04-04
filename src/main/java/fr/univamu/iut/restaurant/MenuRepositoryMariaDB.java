package fr.univamu.iut.restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

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

        String query = "INSERT INTO Menu (author, description, prix,) VALUES (?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, menu.getAuthor());
            ps.setString(2, menu.getDescription());
            ps.setDouble(3, menu.getPrix());

            int result = ps.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Plat getPlat(int idPlat) throws Exception {
        String url = "VOTRE_URL_API_PLATS" + idPlat;

        HttpClient client = HttpClients.createDefault(); // Correction: utiliser HttpClients.createDefault()
        try {
            HttpGet requete = new HttpGet(url);
            HttpResponse reponse = null; // Initialize the variable
            reponse = client.execute(requete); // Use the initialized variable

            if (reponse.getStatusLine().getStatusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Plat plat = mapper.readValue(reponse.getEntity().getContent(), Plat.class); // Correction: TypeReference
                                                                                            // -> Plat.class
                return plat; // Retourner le plat récupéré
            } else {
                throw new Exception(
                        "Erreur lors de la récupération du plat de l'API : " + reponse.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération du plat de l'API : " + e.getMessage());
        }
    }

}
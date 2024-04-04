package fr.univamu.iut.restaurant;

import java.io.Closeable;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuRepositoryMariaDB implements MenuRepositoryInterface, Closeable {

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
                List<Integer> plats = getPlats(idMenu);
                selectedMenu = new Menu(idMenu, author, description, prix, creation_date);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedMenu;
    }

    private List<Plats> getPlats(int idMenu) throws Exception {
        // Replace "YOUR_DISHES_API_URL" with the actual URL of your dishes API
        String urlString = "YOUR_DISHES_API_URL" + idMenu;

        // Use a library like Apache HttpComponents or OkHttp to make the API call
        // This example uses HttpComponents (you'll need to add the dependency)
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                // Parse the response body to get the list of dishes (implementation depends on
                // API format)
                // This example assumes JSON response and uses Jackson library (add dependency)
                ObjectMapper mapper = new ObjectMapper();
                List<Dish> dishes = mapper.readValue(response.getEntity().getContent(),
                        new TypeReference<List<Dish>>() {
                        });
                return dishes;
            } else {
                throw new Exception("Error retrieving dishes from API: " + response.getStatusLine().getStatusCode());
            }
        } finally {
            httpClient.close();
        }
    }

    @Override
    public ArrayList<Menu> getAllMenu() {
        ArrayList<Menu> listMenu;

        String query = "SELECT * FROM menu";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            listMenu = new ArrayList<>();

            while (result.next()) {
                Menu currentMenu = new Menu(result.getInt("id"), result.getString("author"),
                        result.getString("description"), result.getDouble("prix"), result.getDate("creation_date"));

                listMenu.add(currentMenu);
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

        String query = "INSERT INTO Menu (author, description, prix) VALUES (?, ?, ?)";

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
}
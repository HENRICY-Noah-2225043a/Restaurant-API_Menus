import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuResourceTest {
    @Test
    public void testGetMenu() {
        MenuResource menuResource = new MenuResource();
        Menu menu = menuResource.getMenu(1);
        assertEquals("Menu 1", menu.getDescription());
    }

    @Test
    public void testUpdateMenu() {
        MenuResource menuResource = new MenuResource();
        Menu menu = new Menu();
        menu.setAuthor("Auteur");
        menu.setDescription("Description");
        menu.setPrix(10.0);
        assertEquals(true, menuResource.updateMenu(1, menu));
    }

    @Test
    public void testCreateMenu() {
        MenuResource menuResource = new MenuResource();
        Menu menu = new Menu();
        menu.setAuthor("Auteur");
        menu.setDescription("Description");
        menu.setPrix(10.0);
        assertEquals(true, menuResource.createMenu(menu));
    }

    @Test
    public void testGetPlat() {
        MenuResource menuResource = new MenuResource();
        Plat plat = menuResource.getPlat(1);
        assertEquals("Plat 1", plat.getDescription());
    }

    @Test
    public void testGetPlatException() {
        MenuResource menuResource = new MenuResource();
        try {
            Plat plat = menuResource.getPlat(0);
        } catch (Exception e) {
            assertEquals("Erreur lors de la récupération du plat de l'API : 404", e.getMessage());
        }
    }

    @Test
    public void testDeleteMenu() {
        MenuResource menuResource = new MenuResource();
        assertEquals(true, menuResource.deleteMenu(1));
    }
    
    @Test
    public void testDeleteMenuException() {
        MenuResource menuResource = new MenuResource();
        try {
            assertEquals(true, menuResource.deleteMenu(0));
        } catch (Exception e) {
            assertEquals("Erreur lors de la suppression du menu", e.getMessage());
        }
    }

}
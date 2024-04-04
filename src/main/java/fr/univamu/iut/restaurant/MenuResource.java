package fr.univamu.iut.restaurant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import fr.univamu.iut.restaurant.MenuService;
import fr.univamu.iut.restaurant.MenuRepositoryInterface;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Ressource associée aux Menus
 * (point d'accès de l'API REST)
 */
@Path("/menus")
public class MenuResource {

    /**
     * Service utilisé pour accéder aux données des Menus et récupérer/modifier
     * leurs informations
     */
    private MenuService service;

    /**
     * Constructeur par défaut
     */
    public MenuResource() {
    }

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès
     * aux données
     * 
     * @param menuRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject MenuResource(MenuRepositoryInterface menuRepo) {
        this.service = new MenuService(menuRepo);
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux Menus
     */
    public MenuResource(MenuService service) {
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les Menus enregistrés
     * 
     * @return la liste des Menus (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllMenus() {
        return service.getAllMenusJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un Menu dont la référence
     * est passée paramètre dans le chemin
     * 
     * @param id référence du Menu recherché
     * @return les informations du Menu recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getMenu(@PathParam("id") int id) throws SQLException, ClassNotFoundException {

        String result = service.getMenuJSON(id);

        // si le Menu n'a pas été trouvé
        if (result == null)
            throw new NotFoundException();

        return result;
    }

    @GET
    @Path("/test")
    @Produces("application/json")
    public String getTest() {

        return "test";
    }

    /**
     * Endpoint permettant de mettre à jours le statut d'un Menu uniquement
     * (la requête patch doit fournir le nouveau statut sur Menu, les autres
     * informations sont ignorées)
     * 
     * @param id   id du Menu dont il faut changer le statut
     * @param menu le menu transmis en HTTP au format JSON et convertit en
     *             objet Menu
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur
     *         NotFound sinon
     */
    @PUT
    @Path("/update/{id}")
    @Consumes("application/json")
    public Response updateMenu(@PathParam("id") int id, Menu menu) {

        // si le Menu n'a pas été trouvé
        if (!service.updateMenu(id, menu))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de créer un Menu
     * 
     * @param menu le menu transmis en HTTP au format JSON et convertit en
     *             objet Menu
     * @return une réponse "created" si la création a été effectuée, une erreur
     *         NotFound sinon
     */
    @POST
    @Path("/create")
    @Consumes("application/json")
    public Response createMenu(String nom, String description, double prix, Date creation_date) {

        // si le Menu n'a pas été trouvé
        Menu menu = new Menu(nom, description, prix, creation_date);
        if (!service.createMenu(menu))
            throw new NotFoundException();
        else
            return Response.ok("created").build();
    }

}
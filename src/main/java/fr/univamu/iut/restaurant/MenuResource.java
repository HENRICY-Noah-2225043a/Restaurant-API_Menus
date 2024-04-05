package fr.univamu.iut.restaurant;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

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

        if (result == null)
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de publier un message de test
     * 
     * @return un message de test
     */
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

        if (!service.updateMenu(id, menu))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de créer un Menu
     * 
     * @param nom           nom du Menu
     * @param description   description du Menu
     * @param creation_date date de création du Menu
     * @param idEntree      id de l'entrée du Menu
     * @param idPlat        id du plat principal du Menu
     * @param idDessert     id du dessert du Menu
     * @return une réponse "created" si la création a été effectuée, une erreur
     *         NotFound sinon
     * @throws Exception si une erreur survient lors de la récupération des plats
     */
    @POST
    @Path("/create")
    @Consumes("application/json")

    public Response createMenu(
            @PathParam("nom") String nom,
            @PathParam("description") String description,
            @PathParam("date_creation") Date creation_date,
            @PathParam("entree") int idEntree,
            @PathParam("plat") int idPlat,
            @PathParam("dessert") int idDessert) throws Exception {

        Menu menu = new Menu(nom, description, creation_date);

        Plat entree = service.getPlat(idEntree);
        Plat plat = service.getPlat(idPlat);
        Plat dessert = service.getPlat(idDessert);

        menu.setEntree(entree);
        menu.setPlat(plat);
        menu.setDessert(dessert);

        if (!service.createMenu(menu)) {
            throw new NotFoundException();
        } else {
            return Response.ok("created").build();
        }
    }

    /**
     * Endpoint permettant de supprimer un Menu
     * 
     * @param id id du Menu à supprimer
     * @return une réponse "deleted" si la suppression a été effectuée, une erreur
     *         NotFound sinon
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteMenu(@PathParam("id") int id) {

        // si le Menu n'a pas été trouvé
        if (!service.deleteMenu(id))
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }

}
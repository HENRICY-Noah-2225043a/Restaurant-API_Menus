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
     * @throws Exception
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

        // On crée un nouveau menu à partir des paramètres
        Menu menu = new Menu(nom, description, creation_date);

        // On récupère les plats associés au menu
        Plat entree = service.getPlat(idEntree);
        Plat plat = service.getPlat(idPlat);
        Plat dessert = service.getPlat(idDessert);

        // On associe les plats au menu
        menu.setEntree(entree);
        menu.setPlat(plat);
        menu.setDessert(dessert);

        // On crée le menu en base de données
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
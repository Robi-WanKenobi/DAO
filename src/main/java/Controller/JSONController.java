package Controller;

import DAO.DAO;
import Models.Oficina;
import Models.Usuario;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Roberto on 21/03/2017.
 */

@Path("/json")
public class JSONController extends DAO {

    protected List<Usuario> usuarios;
    protected List<Oficina> oficinas;

    @Singleton
    public JSONController() throws SQLException {
        usuarios = Usuario.getAllUsers();
        oficinas = Oficina.getAllOficinas();
    }

    @GET
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@PathParam("id") int id){

        Usuario finded = new Usuario();

        for (int i = 0; i<usuarios.size(); i++){
            if (usuarios.get(i).getId() == id){
                finded = usuarios.get(i);
            }
        }
        return finded;
    }

    @POST
    @Path("/usuario/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUsuarioInJSON(Usuario usuario) {

        if (validateRegister(usuario.getEmail())){
            usuario.insert();
            String yesResult = "Usuario guardado: " + usuario.getNombre();
            return Response.status(201).entity(yesResult).build();
        }
            String noResult = "El email ya esta registrado";
            return Response.status(201).entity(noResult).build();
    }

    @GET
    @Path("/oficina/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Oficina getOficinaByName(@PathParam("nombre") String nombre){

        Oficina finded = new Oficina();

        for (int i = 0; i<oficinas.size(); i++){
            if (oficinas.get(i).getNombre().equalsIgnoreCase(nombre)){
                finded = oficinas.get(i);
            }
        }
        return finded;
    }

    @POST
    @Path("/oficina/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOficinaInJSON(Oficina oficina) {

        oficina.insert();
        String yesResult = "Oficina guardada: " + oficina.getNombre();
        return Response.status(201).entity(yesResult).build();
    }

    public boolean validateRegister(String email){

        for (int i = 0; i<usuarios.size(); i++)
        {
            if (usuarios.get(i).getEmail().equalsIgnoreCase(email))
            {
                return false;
            }
        }
        return true;
    }

}

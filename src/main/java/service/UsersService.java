package service;

import entities.Topic;
import entities.User;
import entities.alert.Alert;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clase de servicio para la gestion de usuarios
 * @author Albano Zupichiatti
 */
public class UsersService {

    private ArrayList<User> users;

    /**
     * Construye el servicio de usuarios con la lista de usuarios vacia
     */
    public UsersService() {
        this.users = new ArrayList<>();
    }

    /**
     * Registra un nuevo usuario si no existe
     * @param userName Nombre de usuario
     * @return Usuario registrado o null si ya existia
     */
    public User registerUser(String userName){

        User user = new User(userName);

        if(users.contains(user)){
            return null;
        }
        else{
            users.add(user);
            return user;
        }
    }

    /**
     * Busca un usuario por nombre
     * @param name Nombre de usuario
     * @return Usuario buscado o null si no se encontro
     */
    public User findUser(String name){

        return users.stream()
                .filter(x->x.getUserName().equals(name))
                .findFirst().orElse(null);
    }

    /**
     * Elige un tema a suscribir por el usuario
     * @param userName Nombre del usuario que se suscribe
     * @param topic Tema a suscribir
     * @return true si existe el usuario con ese nombre y se suscribio correctamente
     */
    public boolean selectTopic(String userName, Topic topic){

        User user = findUser(userName);

        if(user!=null){
            user.subscribeTopic(topic);
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Busca los usuarios suscriptos a un tema
     * @param topic Tema a buscar sus usuarios
     * @return Conjunto de usuarios suscrptos al tema
     */
    public Set<User> findUsersByTopic(Topic topic){

        return users.stream()
                .filter(x->x.isSubscribed(topic))
                .collect(Collectors.toSet());
    }

    /**
     * Marca una alerta de un usuario como leida
     * @param user Usuario a marcar su alerta como leida
     * @param alert Alerta a marcar como leida
     * @return True si existia la alerta en el usuario y se marco correctamente
     */
    public boolean markAlertAsRead(User user, Alert alert){
        return user.markAlertAsRead(alert);
    }

}

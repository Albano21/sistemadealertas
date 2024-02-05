package entities;

import entities.alert.Alert;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Esta clase se utiliza para el manejo de los usuarios del sistema
 * Es una version simplificada, solo se contempla un nombre de usuario, temas suscriptos y alerta no leidas
 * @author Albano Zupichiatti
 */
public class User {

    private String userName;

    private Set<Topic> subscribedTopics;
    
    private ArrayList<Alert> unreadAlerts;

    /**
     * Construye un usuario con todos sus atributos
     * @param userName Nombre de usuario
     */
    public User(String userName) {
        this.userName = userName;
        this.subscribedTopics = new HashSet<>();
        this.unreadAlerts = new ArrayList<>();
    }

    /**
     * Devuelve el nombre de usuario
     * @return Nombre de usuario
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Establece el nombre de usuario
     * @param userName Nombre de usuario
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Devuelve el conjunto de temas suscriptos por el usuario
     * @return Conjunto de temas suscriptos
     */
    public Set<Topic> getSubscribedTopics() {
        return subscribedTopics;
    }

    /**
     * Establece el conjunto de temas suscriptos por el usuario
     * @param subscribedTopics Conjunto de temas suscriptos
     */
    public void setSubscribedTopics(Set<Topic> subscribedTopics) {
        this.subscribedTopics = subscribedTopics;
    }

    /**
     * Devuelve la lista de alertas no leidas y no expiradas (o que no tienen fecha de expiracion)
     * @return Lista de alertas no leidas y no expiradas
     */
    public ArrayList<Alert> getUnexpiredAlerts() {
        return (ArrayList<Alert>) unreadAlerts.stream()
                .filter(x -> x.getExpirationDate()==null || x.getExpirationDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    /**
     * Establece la lista de alertas no leidas
     * @param unreadAlerts Lista de alertas no leidas
     */
    public void setUnreadAlerts(ArrayList<Alert> unreadAlerts) {
        this.unreadAlerts = unreadAlerts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUserName().equals(user.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }

    /**
     * Suscribe a un tema
     * @param topic Tema a suscribir
     */
    public void subscribeTopic(Topic topic){
        subscribedTopics.add(topic);
    }

    /**
     * Añade una alerta a la lista de alertas no leidas
     * @param alert Alerta a añadir
     */
    public void addAlert(Alert alert){
        unreadAlerts.add(alert);
    }

    /**
     * Devuelve true si el usuario esta suscripto a un tema
     * @param topic Tema a consultar
     * @return true si esta suscripto
     */
    public boolean isSubscribed(Topic topic){
        return subscribedTopics.contains(topic);
    }

    /**
     * Devuelve Marca una alerta como leida
     * @param alert Alerta a marcar
     * @return True si la alerta estaba en la lista de alertas no leidas
     */
    public boolean markAlertAsRead(Alert alert){
        return this.unreadAlerts.remove(alert);
    }
    
}

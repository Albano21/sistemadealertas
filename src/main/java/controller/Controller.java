package controller;

import entities.Topic;
import entities.User;
import entities.alert.Alert;
import entities.alert.AlertType;
import service.AlertsService;
import service.TopicsService;
import service.UsersService;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase controladora del sistema, sirve para relacionar los distintos servicios
 * @author Albano Zupichiatti
 */
public class Controller {

    private UsersService usersService;

    private TopicsService topicsService;

    private AlertsService alertsService;

    /**
     * Construye el controlador con todos los servicios instanciados
     */
    public Controller() {
        this.usersService = new UsersService();
        this.topicsService = new TopicsService();
        this.alertsService = new AlertsService();
    }

    /**
     * Registra un usuario (funcionalidad 1)
     * @param userName Nombre de usuario
     * @return true si se registro exitosamente
     */
    public boolean registerUser(String userName){

        if(usersService.registerUser(userName) != null){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Registra un tema (funcionalidad 2)
     * @param name Nombre de tema
     * @return true si se registro exitosamente
     */
    public boolean registerTopic(String name){

        if(topicsService.registerTopic(name) != null){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Suscribe a un usuario a un tema (funcionalidad 3)
     * @param userName Nombre de usuario a suscribir
     * @param topicName Nombre de tema a suscribir
     * @return true si se suscribio correctamente, existe el tema y el usuario
     */
    public boolean selectTopic(String userName, String topicName){


        Topic topic = topicsService.findTopic(topicName);

        if(topic != null){
            return usersService.selectTopic(userName, topic);
        }
        else{
            return false;
        }

    }

    /**
     * Envia una alerta por tema a todos sus usuarios sin fecha de expiracion (funcionalidad 4),
     * delega al metodo con fecha de expiracion ya que coinciden en comportamiento
     * @param message Mensaje a enviar
     * @param type Tipo de alerta
     * @param topicName Nombre de tema
     * @return Id de la alerta
     */
    public int sendAlertByTopic(String message, AlertType type, String topicName){
        return sendAlertByTopic(message, type, topicName, null);
    }

    /**
     * Envia una alerta por tema a un usuario sin fecha de expiracion (funcionalidad 5),
     * delega al metodo con fecha de expiracion ya que coinciden en comportamiento
     * @param message Mensaje a enviar
     * @param type Tipo de alerta
     * @param topicName Nombre de tema
     * @param userName Nombre de usuario
     * @return Id de la alerta
     */
    public int sendAlertByUser(String message, AlertType type, String topicName, String userName){
        return sendAlertByUser(message, type, topicName, userName, null);
    }

    /**
     * Envia una alerta por tema a todos sus usuarios con fecha de expiracion (funcionalidad 6)
     * @param message Mensaje a enviar
     * @param type Tipo de alerta
     * @param topicName Nombre de tema
     * @param expirationDate Fecha de expiracion
     * @return Id de la alerta
     */
    public int sendAlertByTopic(String message, AlertType type, String topicName, LocalDateTime expirationDate){

        Topic topic = topicsService.findTopic(topicName);

        if(topic != null){
            Set<User> subscribedUsers = usersService.findUsersByTopic(topic);
            Alert alert = alertsService.sendAlert(message, type, subscribedUsers, expirationDate);
            topic.addAlert(alert);
            return alert.getId();
        }
        else{
            return 0;
        }

    }

    /**
     * Envia una alerta por tema a un usuario con fecha de expiracion (funcionalidad 6)
     * @param message Mensaje a enviar
     * @param type Tipo de alerta
     * @param topicName Nombre de tema
     * @param userName Nombre de usuario
     * @param expirationDate Fecha de expiracion
     * @return Id de la alerta
     */
    public int sendAlertByUser(String message, AlertType type, String topicName, String userName, LocalDateTime expirationDate){

        User user = usersService.findUser(userName);
        Topic topic = topicsService.findTopic(topicName);

        if(user != null && topic != null && user.isSubscribed(topic)){
            Alert alert = alertsService.sendAlert(message, type, user, expirationDate);
            topic.addAlert(alert);
            return alert.getId();
        }
        else{
            return 0;
        }

    }

    /**
     * Marca una alerta de un usuario como leida (funcionalidad 8)
     * @param userName Nombre de usuario
     * @param alertId Id de alerta
     * @return true si existe el usuario, la alerta y se marca correctamente como leida
     */
    public boolean markAlertAsRead(String userName, int alertId){

        User user = usersService.findUser(userName);
        Alert alert = alertsService.findAlertById(alertId);

        if(user != null && alert != null){
            return usersService.markAlertAsRead(user, alert);
        }
        else{
            return false;
        }

    }

    /**
     * Devuelve la lista de alertas no leidas y no expiradas de un usuario (funcionalidad 9 y 11)
     * @param userName Nombre de usuario
     * @return Lista de alertas no leidas ni expiradas
     * ordenadas por LIFO para las urgentes y luego FIFO para las informativas
     */
    public ArrayList<Alert> getUnexpiredAlertsByUser(String userName){

        User user = usersService.findUser(userName);

        if(user != null){
            ArrayList<Alert> alerts = user.getUnexpiredAlerts();
            orderAlerts(alerts);
            return alerts;
        }
        else{
            return null;
        }

    }

    /**
     * Devuelve la lista de alertas no expiradas relacionadas a un tema (funcionalidad 10 y 11)
     * @param topicName Nombre de tema
     * @return Lista de alertas no leidas ni expiradas
     * ordenadas por LIFO para las urgentes y luego FIFO para las informativas
     */
    public ArrayList<Alert> getUnexpiredAlertsByTopic(String topicName){

        Topic topic = topicsService.findTopic(topicName);

        if(topic != null){
            ArrayList<Alert> alerts = topic.getUnexpiredAlerts();
            orderAlerts(alerts);
            return alerts;
        }
        else{
            return null;
        }

    }

    /**
     * Ordena las alertas delegando la logica de comparacion a la clase AlertComparator
     * @param alerts Lista de alertas a ordenar
     */
    private void orderAlerts(ArrayList<Alert> alerts){
        Collections.sort(alerts, new AlertComparator());
    }

    /**
     * Clase que se encarga de la comparar las alertas para su ordenamiento
     */
    private static class AlertComparator implements Comparator<Alert> {

        /**
         * Compara las alertas para ordenarlas primero por LIFO para las urgentes y luego por FIFO las informativas
         * @param alert1 the first object to be compared.
         * @param alert2 the second object to be compared.
         * @return Comparacion para ordenar
         */
        @Override
        public int compare(Alert alert1, Alert alert2) {

            if (alert1.getType() == AlertType.URGENT && alert2.getType() == AlertType.URGENT) {
                return Integer.compare(alert2.getId(), alert1.getId());
            } else if (alert1.getType() == AlertType.URGENT) {
                return -1;
            } else if (alert2.getType() == AlertType.URGENT) {
                return 1;
            } else {
                return Integer.compare(alert1.getId(), alert2.getId());
            }
        }

    }

}

package service;

import entities.User;
import entities.alert.Alert;
import entities.alert.AlertBuilder;
import entities.alert.AlertDestination;
import entities.alert.AlertType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;


/**
 * Clase de servicio para la gestion de alertas
 * @author Albano Zupichiatti
 */
public class AlertsService {

    private ArrayList<Alert> alerts;

    /**
     * Construye el servicio de alertas con la lista de alertas vacias
     */
    public AlertsService() {
        this.alerts = new ArrayList<>();
    }

    /**
     * Envia una alerta por tema a un usuario especifico
     * @param message Mensaje a enviar
     * @param type Tipo de alerta
     * @param user Usuario destino
     * @param expirationDate Fecha de expiracion (puede ser null)
     * @return Alerta enviada
     */
    public Alert sendAlert(String message, AlertType type, User user, LocalDateTime expirationDate) {

        Alert alert = createAlert(message, type, AlertDestination.PERSONAL, expirationDate);
        alerts.add(alert);

        user.addAlert(alert);

        return alert;
    }

    /**
     * Envia una alerta por tema a un conjunto de usuarios
     * @param message Mensaje a enviar
     * @param type Tipo de alerta
     * @param users Conjunto de usuarios
     * @param expirationDate Fecha de expiracion (puede ser null)
     * @return Alerta enviada
     */
    public Alert sendAlert(String message, AlertType type, Set<User> users, LocalDateTime expirationDate) {

        Alert alert = createAlert(message, type, AlertDestination.GENERAL, expirationDate);
        alerts.add(alert);

        for (User u : users) {
            u.addAlert(alert);
        }

        return alert;
    }

    /**
     * Delega la creacion de la alerta a AlertBuilder, si tiene fecha utilizara el paso de agregar fecha si no no
     * @param message Mensaje a enviar
     * @param type Tipo de alerta
     * @param destination Tipo de destino
     * @param expirationDate Fecha de expiracion (puede ser null)
     * @return Alerta creada
     */
    private Alert createAlert(String message, AlertType type, AlertDestination destination, LocalDateTime expirationDate) {

        int id = alerts.size()+1;
        Alert alert;

        if (expirationDate != null) {
            alert = new AlertBuilder()
                    .setId(id)
                    .setMessage(message)
                    .setType(type)
                    .setDestination(destination)
                    .setExpirationDate(expirationDate)
                    .build();
        } else {
            alert = new AlertBuilder()
                    .setId(id)
                    .setMessage(message)
                    .setType(type)
                    .setDestination(destination)
                    .build();
        }

        return alert;
    }

    /**
     * Busca una alerta por id
     * @param id Id a buscar
     * @return Alerta encontrada o null si no existe
     */
    public Alert findAlertById(int id){
        return alerts.stream().filter(x->x.getId()==id).findFirst().orElse(null);
    }

}

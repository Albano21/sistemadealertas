package entities.alert;

import java.time.LocalDateTime;

/**
 * Esta clase se utiliza para la construccion de las alertas,
 * implementacion del patron Builder sin director
 * @author Albano Zupichiatti
 */
public class AlertBuilder {

    private int id;

    private String message;

    private AlertType type;

    private AlertDestination destination;

    private LocalDateTime expirationDate;

    /**
     * Estable el id a utilizar
     * @param id Id
     * @return AlertBuilder con el id establecido
     */
    public AlertBuilder setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Establece el mensaje a utilizar
     * @param message Mensaje
     * @return AlertBuilder con el mensaje establecido
     */
    public AlertBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Establece el tipo de alerta a utilizar
     * @param type Tipo de alerta
     * @return Alertbuilder con el tipo de alerta establecido
     */
    public AlertBuilder setType(AlertType type) {
        this.type = type;
        return this;
    }

    /**
     * Establece la fecha de expiracion a utilizar
     * @param expirationDate Fecha de expiracion
     * @return AlertBuilder con la fecha de expiracion establecida
     */
    public AlertBuilder setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    /**
     * Establece el tipo de destino a utilizar
     * @param destination Tipo de destino
     * @return AlertBuilder con el tipo de destino establecido
     */
    public AlertBuilder setDestination(AlertDestination destination) {
        this.destination = destination;
        return this;
    }

    /**
     * Construye la alerta con todos los campos establecidos previamente en la instancia de AlertBuilder
     * @return Alerta construida
     */
    public Alert build() {

        if (type == null) {
            type = AlertType.INFORMATIVE;
        }

        if (destination == null) {
            destination = AlertDestination.GENERAL;
        }

        return new Alert(id, message, type, destination, expirationDate);
    }

}

package entities.alert;

import java.time.LocalDateTime;

/**
 * Esta clase se utiliza para el manejo de las alertas del sistema
 * Es una version simplificada, solo se contempla id, mensaje, tipo de alerta, tipo de destino y
 * fecha de expiracion (opcional)
 * @author Albano Zupichiatti
 */
public class Alert {

    private int id;

    private String message;

    private AlertType type;

    private AlertDestination destination;

    private LocalDateTime expirationDate;

    /**
     * Construye una alerta con todos sus atributos
     * @param id Id de la alerta
     * @param message Mensaje
     * @param type Tipo de alerta
     * @param destination Tipo de destino
     * @param expirationDate Fecha de expiracion
     */
    public Alert(int id, String message, AlertType type, AlertDestination destination, LocalDateTime expirationDate) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.destination = destination;
        this.expirationDate = expirationDate;
    }

    /**
     * Devuelve el id
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id
     * @param id Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el mensaje
     * @return Mensaje
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje
     * @param message Mensaje
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Devuelve el tipo de alerta
     * @return Tipo de alerta
     */
    public AlertType getType() {
        return type;
    }

    /**
     * Establece el tipo de alerta
     * @param type Tipo de alerta
     */
    public void setType(AlertType type) {
        this.type = type;
    }

    /**
     * Devuelve la fecha de expiracion
     * @return Fecha de expiracion
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Establece la fecha de expiracion
     * @param expirationDate Fecha de expiracion
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Devuelve el tipo de destino de la alerta
     * @return Tipo de destino
     */
    public AlertDestination getDestination() {
        return destination;
    }

    /**
     * Establece el tipo de destino de la alerta
     * @param destination Tipo de destino
     */
    public void setDestination(AlertDestination destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", destination=" + destination +
                ", expirationDate=" + expirationDate +
                '}';
    }
}

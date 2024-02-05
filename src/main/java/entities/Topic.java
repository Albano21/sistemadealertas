package entities;

import entities.alert.Alert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Esta clase se utiliza para el manejo de los temas del sistema
 * Es una version simplificada, solo se contempla nombre y alertas relacionadas
 * @author Albano Zupichiatti
 */
public class Topic {

    private String name;

    private ArrayList<Alert> alerts;


    /**
     * Construye un tema con todos sus atributos
     * @param name Nombre del tema
     */
    public Topic(String name) {
        this.name = name;
        this.alerts = new ArrayList<>();
    }

    /**
     * Devuelve el nombre del tema
     * @return Nombre del tema
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del tema
     * @param name Nombre del tema
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la lista de alertas relacionadas
     * @return Lista de alertas relacionadas
     */
    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    /**
     * Establece la lista de alertas relacionadas
     * @param alerts Lista de alertas relacionadas
     */
    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return getName().equals(topic.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    /**
     * Añade una alerta a la lista de alertas relacionadas
     * @param alert Alerta a añadir
     */
    public void addAlert(Alert alert){
        alerts.add(alert);
    }

    /**
     * Devuelve la lista de alertas no expiradas
     * @return Lista de alertas no expiradas
     */
    public ArrayList<Alert> getUnexpiredAlerts() {
        return (ArrayList<Alert>) this.alerts.stream()
                .filter(x -> x.getExpirationDate()==null || x.getExpirationDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

}

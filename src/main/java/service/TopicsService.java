package service;

import entities.Topic;

import java.util.ArrayList;


/**
 * Clase de servicio para la gestion de los temas
 * @author Albano Zupichiatti
 */
public class TopicsService {

    private ArrayList<Topic> topics;

    /**
     * Construte el servicio de temas con la lista de temas vacia
     */
    public TopicsService() {
        this.topics = new ArrayList<>();
    }

    /**
     * Registra un nuevo tema si no existe
     * @param name Nombre del tema
     * @return Tema registrado o null si ya existia
     */
    public Topic registerTopic(String name){

        Topic topic = new Topic(name);

        if(topics.contains(topic)){
            return null;
        }
        else{
            topics.add(topic);
            return topic;
        }

    }

    /**
     * Busca un tema por nombre
     * @param name Nombre a buscar
     * @return Tema buscado o null si no se encontro
     */
    public Topic findTopic(String name){

        return topics.stream()
                .filter(x->x.getName().equals(name))
                .findFirst().orElse(null);
    }

}

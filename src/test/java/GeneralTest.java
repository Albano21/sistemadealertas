import controller.Controller;
import entities.alert.Alert;
import entities.alert.AlertType;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/**
 * Esta clase se usa para contener todos los test unitarios de las distintas funcionalidades requeridas,
 * se contempla el llamado camino feliz en todos los casos
 */
public class GeneralTest {

    private final Controller controller = new Controller();

    @Test
    public void registerUser(){
        controller.registerUser("Albano");
    }

    @Test
    public void registerTopic(){
        controller.registerTopic("Programming");
    }

    @Test
    public void selectTopic(){

        controller.registerUser("Albano");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");

    }

    @Test
    public void sendAlertByTopic(){

        controller.registerUser("Albano");
        controller.registerUser("WoowUp");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");

        controller.sendAlertByTopic("New Node.js courses!",
                AlertType.INFORMATIVE, "Programming");

    }

    @Test
    public void sendAlertByUser(){

        controller.registerUser("Albano");
        controller.registerUser("WoowUp");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");

        controller.sendAlertByUser("New Node.js courses!", AlertType.INFORMATIVE,
                "Programming", "Albano");

    }

    @Test
    public void sendAlertWithExpirationByTopic(){

        controller.registerUser("Albano");
        controller.registerUser("WoowUp");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");

        LocalDateTime expirationDate = LocalDateTime.of(2024, Month.AUGUST, 20, 15, 0);
        controller.sendAlertByTopic("New Node.js courses!", AlertType.INFORMATIVE,
                "Programming", expirationDate);

    }

    @Test
    public void sendAlertWithExpirationByUser(){

        controller.registerUser("Albano");
        controller.registerUser("WoowUp");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");

        LocalDateTime expirationDate = LocalDateTime.of(2024, Month.AUGUST, 20, 15, 0);
        controller.sendAlertByUser("New Node.js courses!", AlertType.INFORMATIVE, "Programming",
                "Albano", expirationDate);

    }

    @Test
    public void markAlertAsRead(){

        controller.registerUser("Albano");
        controller.registerUser("WoowUp");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");
        controller.selectTopic("WoowUp", "Programming");

        int alertId = controller.sendAlertByTopic("New Node.js courses!",
                AlertType.INFORMATIVE, "Programming");

        controller.markAlertAsRead("Albano", alertId);
    }

    @Test
    public void getUnexpiredAlertsByUser(){

        controller.registerUser("Albano");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");

        controller.sendAlertByTopic("New Node.js courses 1!",
                AlertType.INFORMATIVE, "Programming");

        LocalDateTime expirationDate2 = LocalDateTime.of(2024, Month.AUGUST, 20, 15, 0);
        controller.sendAlertByUser("New Node.js courses 2!", AlertType.INFORMATIVE,
                "Programming", "Albano", expirationDate2);

        LocalDateTime expirationDate3 = LocalDateTime.of(2023, Month.AUGUST, 20, 15, 0);
        controller.sendAlertByUser("New Node.js courses 3!", AlertType.INFORMATIVE,
                "Programming", "Albano", expirationDate3);

        controller.sendAlertByTopic("New Node.js courses 4!",
                AlertType.URGENT, "Programming");

        controller.sendAlertByTopic("New Node.js courses 5!",
                AlertType.URGENT, "Programming");

        List<Alert> alerts = controller.getUnexpiredAlertsByUser("Albano");

    }

    @Test
    public void getUnexpiredAlertsByTopic(){

        controller.registerUser("Albano");
        controller.registerTopic("Programming");

        controller.selectTopic("Albano", "Programming");

        controller.sendAlertByTopic("New Node.js courses 1!",
                AlertType.INFORMATIVE, "Programming");

        LocalDateTime expirationDate2 = LocalDateTime.of(2024, Month.AUGUST, 20, 15, 0);
        controller.sendAlertByUser("New Node.js courses 2!", AlertType.INFORMATIVE,
                "Programming", "Albano", expirationDate2);

        LocalDateTime expirationDate3 = LocalDateTime.of(2023, Month.AUGUST, 20, 15, 0);
        controller.sendAlertByUser("New Node.js courses 3!", AlertType.INFORMATIVE,
                "Programming", "Albano", expirationDate3);

        controller.sendAlertByTopic("New Node.js courses 4!",
                AlertType.URGENT, "Programming");

        controller.sendAlertByTopic("New Node.js courses 5!",
                AlertType.URGENT, "Programming");

        List<Alert> alerts = controller.getUnexpiredAlertsByTopic("Programming");

    }

}

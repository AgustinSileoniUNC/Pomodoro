package sample;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CronometroTest {
    Text text;
    Button button;

    @BeforeEach
    void setUp() {
        text = new Text();
        button = new Button();
    }

    @Test
    void getInstance() {
        Controller controller = new Controller();
        Cronometro cronometro = Cronometro.getInstance(1,controller );
        cronometro.start();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0,cronometro.tiempoRestante());

    }
}
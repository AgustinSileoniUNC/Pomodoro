package sample;

import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CronometroTest {
    Text text;
    Mostrador_Auxiliar mostrador_auxiliar ;
    @BeforeEach
    void setUp() {
        text = new Text();
        mostrador_auxiliar = Mostrador_Auxiliar.getInstance(text);
    }

    @Test
    void getInstance() {
        Cronometro cronometro = Cronometro.getInstance(1,mostrador_auxiliar);
        cronometro.start();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0,cronometro.tiempoRestante());

    }
}
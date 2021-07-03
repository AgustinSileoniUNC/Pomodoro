package sample;
import com.sun.scenario.effect.Glow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class Controller {

    @FXML private Text timer;
    @FXML private Button button;
    private boolean funcionando = false;
    public void iniciarPomodoro(MouseEvent evento){
        Cronometro cronometro = Cronometro.getInstance(25);
        Mostrador_Auxiliar mostrador_auxiliar = Mostrador_Auxiliar.getInstance(cronometro, timer);
        if(!cronometro.isFuncionando()){
            timer.setEffect(new javafx.scene.effect.Glow());
            Thread h1 = new Thread(cronometro);
            Thread h2 = new Thread(mostrador_auxiliar);
            h1.start();
            h2.start();
        }
        button.setText("PAUSAR");
    }
}
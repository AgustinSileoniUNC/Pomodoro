package sample;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class Controller {

    @FXML private Text timer;
    @FXML private Button button;
    private boolean isModoTrabajo = false;
    private Mostrador_Auxiliar mostrador_auxiliar;
    private Cronometro cronometro;


    public void iniciarPomodoro(MouseEvent evento){
        mostrador_auxiliar = Mostrador_Auxiliar.getInstance(timer);
        cronometro = Cronometro.getInstance(25,mostrador_auxiliar);
        if(cronometro.isFuncionando()){
            cronometro.changeState();
        }
        else {
           modoTrabajo();
        }
    }


    private void modoTrabajo(){
        isModoTrabajo = true;
        if(!cronometro.isFuncionando()){
            timer.setEffect(new javafx.scene.effect.Glow());
            Thread h1 = new Thread(cronometro);
            h1.start();
        }
        button.setText("PAUSAR");
    }
}
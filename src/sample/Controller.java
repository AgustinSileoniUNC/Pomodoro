package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private Text timer;
    @FXML private Text title;
    @FXML private Button button;
    private boolean isModoTrabajo = false;
    private Mostrador_Auxiliar mostrador_auxiliar;
    private Cronometro cronometro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goSettingsScreen(MouseEvent event) throws IOException {
        Parent settingScreen = FXMLLoader.load(getClass().getResource("pantallaDescanso.fxml"));
        Scene  settingScreenScene= new Scene(settingScreen) ;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(settingScreenScene);
        stage.show();
    }

    public void goWorkScreen(MouseEvent event) throws IOException {
        Parent settingScreen = FXMLLoader.load(getClass().getResource("pantallaTrabajo.fxml"));
        Scene  settingScreenScene= new Scene(settingScreen) ;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(settingScreenScene);
        stage.show();
    }

    public void goInitialScreen(MouseEvent event)throws IOException{
        Parent settingScreen = FXMLLoader.load(getClass().getResource("pantallaInicio.fxml"));
        Scene  settingScreenScene= new Scene(settingScreen) ;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(settingScreenScene);
        stage.show();
    }

    public void exit(){
        System.exit(0);
    }

    public void iniciarPomodoro(MouseEvent evento){
        mostrador_auxiliar = Mostrador_Auxiliar.getInstance(timer);
        cronometro = Cronometro.getInstance(25*60,mostrador_auxiliar);
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
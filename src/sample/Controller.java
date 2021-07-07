package sample;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private Text timer;
    @FXML private Text title;
    @FXML private Button button;
    @FXML private AnchorPane body;
    @FXML private AnchorPane head;
    private boolean isModoTrabajo = true;
    private Cronometro cronometro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    //Carga la ventana de configuraciones
    public void goSettingsScreen(MouseEvent event) throws IOException {
        Parent settingScreen = FXMLLoader.load(getClass().getResource("pantallaDescanso.fxml"));
        Scene  settingScreenScene= new Scene(settingScreen) ;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(settingScreenScene);
        stage.show();
    }

    //Carga la ventana incial
    public void goInitialScreen(MouseEvent event)throws IOException{
        Parent settingScreen = FXMLLoader.load(getClass().getResource("pantallaInicio.fxml"));
        Scene  settingScreenScene= new Scene(settingScreen) ;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(settingScreenScene);
        stage.show();
    }

    //Carga la ventana de trabajo
    private void goWorkScreen(MouseEvent event) throws IOException {
        Parent workScreen = FXMLLoader.load(getClass().getResource("pantallaTrabajo.fxml"));
        Scene  workScreenScene= new Scene(workScreen) ;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(workScreenScene);
        stage.show();
    }

    //Carga la ventana de descanso(Intentando hacerlo desde cronómetro)
    private void goBreakScreen(MouseEvent event) throws IOException {
        Parent breakScreen = FXMLLoader.load(getClass().getResource("pantallaDescanso.fxml"));
        Scene  breakScreenScene= new Scene(breakScreen) ;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(breakScreenScene);
        stage.show();
    }

    //Describe el comportamiento al clickear el boton con el cronometro en cero
    private void endTimer(MouseEvent event) throws IOException {
        if(isModoTrabajo){
            cronometro.setTiempo_seg(5*60);
            button.setText("DESCANSAR");
            title.setText("Descanso");
            head.setStyle("-fx-background-color: green");
            title.setStyle("-fx-fill: black ");
            body.setStyle("-fx-background-color:  #5cc57e");
            timer.setStyle("-fx-fill: black ");
            button.setStyle("-fx-background-color: #3f884c ");
           // button.setStyle("-fx-text-fill: #38753d");
           // button.setStyle("-fx-font-size: 25");

        }
        else if(!isModoTrabajo){
            cronometro.setTiempo_seg(25*60);
            goWorkScreen(event);
        }
    }

    //Cierra el programa
    public void exit(){
        System.exit(0);
    }

    public void setTextButton(String string){
        button.setText(string);
    }

    //Inicia el cronometro si no lo está ya, y en ese caso cambia su estado
    public void iniciarPomodoro(MouseEvent evento) throws IOException {
        cronometro = Cronometro.getInstance(5,this);
        if(cronometro.isFuncionando()){
                cronometro.changeState();
        }
        else if(!cronometro.isFuncionando()&&cronometro.tiempoRestante()==0){
            endTimer(evento);
            isModoTrabajo= !isModoTrabajo;
        }
        else {
           modoTrabajo();
        }
    }

    //Logica privada del metodo iniciarPomodoro
    private void modoTrabajo(){
        System.out.println("Trabajo");
        if(!cronometro.isFuncionando()){
            timer.setEffect(new javafx.scene.effect.Glow());
            Thread h1 = new Thread(cronometro);
            h1.start();
            System.out.println("Corriendo");
        }
        button.setText("PAUSAR");
        if(cronometro.tiempoRestante() ==0){
            button.setText("Descansar");
        }
    }

    public void actualizarTimer(int tiempoRestante){
        System.out.println(""+(int) tiempoRestante);
        timer.setText( tiempoRestante/60+":"+ tiempoRestante%60);
    }

}
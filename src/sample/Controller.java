package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.css.StyleClass;
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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private Text timer;
    @FXML private Text title;
    @FXML private Button button;
    @FXML private AnchorPane conteiner;
    @FXML private AnchorPane body;
    @FXML private AnchorPane head;
    private boolean isModoTrabajo = true;
    private Cronometro cronometro;
    private int tiempoTrabajo=25;
    private int tiempoDescansoCorto=5;
    private int tiempoDescansoLargo=15;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  conteiner.getStylesheets().clear();
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
    public void goWorkScreen(MouseEvent event) throws IOException {
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
            cronometro.setTiempo_seg(tiempoDescansoCorto*60);
            vistaModoDescanso();
        }
        else if(!isModoTrabajo){
            cronometro.setTiempo_seg(tiempoTrabajo*60);
            vistaModoTrabajo();
        }
        Thread t2 = new Thread(cronometro);
        t2.start();

    }

    //Cierra el programa
    public void exit(){
        System.exit(0);
    }

    public void setTextButton(String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                button.setText(string);
            }
        });
    }

    //Inicia el cronometro si no lo está ya, y en ese caso cambia su estado
    public void iniciarPomodoro(MouseEvent evento) throws IOException {
        cronometro = Cronometro.getInstance(tiempoTrabajo*60,this);
        if(cronometro.isFuncionando()){
                cronometro.changeState();
                System.out.println("Cambio de Estado");
        }
        else if(!cronometro.isFuncionando() && cronometro.tiempoRestante()==0){
            System.out.println("Cambio endTimer");
            endTimer(evento);
            isModoTrabajo= !isModoTrabajo;
        }
        else {
           modoTrabajo();
        }
    }

    //Logica privada del metodo iniciarPomodoro
    private void modoTrabajo(){
        timer.setEffect(new javafx.scene.effect.Glow());
        Thread h1 = new Thread(cronometro);
        h1.start();
        button.setText("PAUSAR");
    }

    public void actualizarTimer(int tiempoRestante){
        System.out.println(""+(int) tiempoRestante);
        timer.setText( String.format("%02d"+":"+"%02d",tiempoRestante/60,tiempoRestante%60));
    }

    public boolean getIsModoTrabajo(){
        return isModoTrabajo;
    }

    private void vistaModoDescanso(){
        button.setText("PAUSAR");
        title.setText("Descanso");
        head.setStyle("-fx-background-color: green");
        title.setStyle("-fx-fill: black ");
        body.setStyle("-fx-background-color:  #c6fc9a");
        timer.setStyle("-fx-fill: black ");
        button.setStyle("-fx-background-color: #55ea6c ");
        button.setStyle("-fx-text-fill: #073900");
        button.setStyle("-fx-font-family:  Dyuthi");
        conteiner.getStylesheets().clear();
        conteiner.getStylesheets().addAll("file:/home/agustin/IdeaProjects/Pomodoro/src/sample/estiloDescanso.css");

    }

    private void vistaModoTrabajo(){
        button.setText("PAUSAR");
        title.setText("Trabajo");
        head.setStyle("-fx-background-color: red");
        title.setStyle("-fx-fill: black ");
        body.setStyle("-fx-background-color:  #fcc69a");
        timer.setStyle("-fx-fill: black ");
        button.setStyle("-fx-background-color: #da0000 ");
        button.setStyle("-fx-text-fill: #640000");
        button.setStyle("-fx-font-family:  Dyuthi");
        conteiner.getStylesheets().clear();
        conteiner.getStylesheets().addAll("file:/home/agustin/IdeaProjects/Pomodoro/src/sample/estiloTrabajo.css");
    }

}
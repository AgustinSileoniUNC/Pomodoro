package sample;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class Cronometro extends Thread{

    private static Cronometro Cronometro_;
    private Controller controller;
    private double tiempo_seg;
    private boolean funcionando = false;
    private boolean state = false;

    private Cronometro(long tiempo_seg, Controller controller){
        this.tiempo_seg = tiempo_seg;
        this.controller = controller;
    }

    public static Cronometro getInstance(long tiempo_seg,Controller controller){
        if(Cronometro_==null){
            Cronometro_ = new Cronometro(tiempo_seg,controller);
        }
        return Cronometro_;
    }

    @Override
    public void run() {
        funcionando = true;
        state = true;
        while (tiempo_seg>0 && state){
            try {
                Thread.sleep(1000);
                if(state) {
                    tiempo_seg--;
                    controller.actualizarTimer((int) tiempo_seg);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state = false;
        funcionando = false;
    }

    public double tiempoRestante(){
        return tiempo_seg;
    }

    public boolean isFuncionando(){
        return funcionando;
    }

    public void setTiempo_seg(double time){
        this.tiempo_seg = time;
    }

    public void changeState(){
        state = !state;
    }
}

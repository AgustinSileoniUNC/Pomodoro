package sample;

import javafx.scene.text.Text;

public class Mostrador_Auxiliar extends Thread{
    private static Mostrador_Auxiliar Mostrador_auxiliar_;
    Cronometro cronometro;
    Text timer;
    private Mostrador_Auxiliar(Cronometro cronometro, Text timer){
        this.cronometro= cronometro;
        this.timer = timer;
    }

    public static Mostrador_Auxiliar getInstance(Cronometro cronometro, Text timer){
        if(Mostrador_auxiliar_==null){
            Mostrador_auxiliar_ = new Mostrador_Auxiliar(cronometro,timer);
        }
        return Mostrador_auxiliar_;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                timer.setText((int) cronometro.tiempoRestante()/60+":"+(int) cronometro.tiempoRestante()%60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


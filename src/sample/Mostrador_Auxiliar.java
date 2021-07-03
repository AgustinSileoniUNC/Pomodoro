package sample;

import javafx.scene.text.Text;

public class Mostrador_Auxiliar{
    private static Mostrador_Auxiliar Mostrador_auxiliar_;
    private Text timer;

    private Mostrador_Auxiliar( Text timer){
        this.timer = timer;
    }

    public static Mostrador_Auxiliar getInstance(Text timer){
        if(Mostrador_auxiliar_ == null){
            Mostrador_auxiliar_ = new Mostrador_Auxiliar(timer);
        }
        return Mostrador_auxiliar_;
    }

    public void actualizar(int tiempoRestante){
        timer.setText( tiempoRestante/60+":"+ tiempoRestante%60);
    }
    public Text modificar(){
        return timer;
    }

}


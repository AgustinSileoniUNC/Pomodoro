package sample;

import javafx.scene.text.Text;

public class Cronometro extends Thread{
    private static Cronometro Cronometro_;
    private Mostrador_Auxiliar mostrador_auxiliar;
    private double tiempo_seg;
    private boolean funcionando = false;
    private boolean state = false;

    private Cronometro(int tiempo_min, Mostrador_Auxiliar mostrador_auxiliar){
        this.tiempo_seg = tiempo_min*60;
        this.mostrador_auxiliar = mostrador_auxiliar;
    }

    public static Cronometro getInstance(int tiempo_min, Mostrador_Auxiliar mostrador_auxiliar){
        if(Cronometro_==null){
            Cronometro_ = new Cronometro(tiempo_min,mostrador_auxiliar);
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
                    mostrador_auxiliar.actualizar((int) tiempo_seg);
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

    public void changeState(){
        state = !state;
    }
}

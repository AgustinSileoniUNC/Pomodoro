package sample;

public class Cronometro extends Thread{
    private static Cronometro Cronometro_;
    private double tiempo_seg;
    private boolean funcionando = false;

    public static Cronometro getInstance(int tiempo_min){
        if(Cronometro_==null){
            Cronometro_ = new Cronometro(tiempo_min);
        }
        return Cronometro_;
    }

    private Cronometro(int tiempo_min){
        this.tiempo_seg = tiempo_min*60;
    }
    @Override
    public void run() {
        funcionando = true;
        while (tiempo_seg>0){
            try {
                Thread.sleep(1000);
                tiempo_seg --;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        funcionando = false;
    }

    public double tiempoRestante(){
        return tiempo_seg;
    }

    public boolean isFuncionando(){
        return funcionando;
    }
}

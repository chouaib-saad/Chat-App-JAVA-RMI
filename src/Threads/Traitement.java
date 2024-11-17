package Threads;

public class Traitement extends Thread{

    //extends ou ona autre methode (runnable)


    String nom;

    Traitement(String nom) {
        this.nom = nom;
    }


    public void affiche() {

        for (int i = 1; i <= 10; i++) {

            {
                System.out.println("Traitement A , iteration" + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


    @Override
    public void run() {

        for (int i = 1; i <= 10; i++) {

            {
                System.out.println("Traitement "+this.nom+" , iteration" + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }


    //start /stop boucle while(true) if 10 && b (b : boolean) if(i==10) => i = 0
}
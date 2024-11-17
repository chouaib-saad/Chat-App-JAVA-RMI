package Threads;

public class Test {

    //threads:
    //optimisation
    //animation
    // connexion internet

    public static void main(String [] args){


        System.out.println("debut prog principale");



        /*

        for(int i  = 1 ; i<=10 ; i++){

            System.out.println("Traitement  A , iteration "+i);

            try {

                //sleep est unem methode statique
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        for(int i  = 1 ; i<=10 ; i++){

            System.out.println("Traitement  B , iteration "+i);


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


         */




        Traitement tA = new Traitement("A");
//        tA.affiche();
//        tA.run();
        tA.start();




        Traitement tB = new Traitement("B");
//        tB.affiche();
//        tB.run();
        tB.start();



        try {
            tA.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            tA.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        System.out.println("Fin programme principale");

    }



}

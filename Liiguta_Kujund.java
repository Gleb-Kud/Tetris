package com.example.tetriss;

import java.util.Arrays;

import static com.example.tetriss.Peaklass.*;

public class Liiguta_Kujund {
    public static final int suurus = Peaklass.suurus;
    public static final int käiguSuurus = suurus;   //kuna liigutamine toimub suuruse võrra, siis on sama, aga koodis on lihtsam jälgida
    public static int kõrgus = Peaklass.kõrgus;
    public static int laius = Peaklass.laius;
    public static int[][] väli = Peaklass.väli;
    public static Kujund järgmineKujund = Peaklass.getJärgmineKujund();



    /** liigutamine toimub ainult siis, kui seda miski ei tõkesta
     * ning välja plokk, kuhu tahetakse liigutada iga kujundi osa, on samuti mitte hõivatud ehk 0
     */


    public static void liigutaParemale (Kujund kujund) {
        if (kujund.a.getX() + käiguSuurus <= laius - suurus && kujund.b.getX() + käiguSuurus <= laius - suurus
                && kujund.c.getX() + käiguSuurus <= laius - suurus && kujund.d.getX() + käiguSuurus <= laius - suurus) {
            int aJaoks = väli[((int) kujund.a.getY() / suurus)][((int) kujund.a.getX() / suurus)];
            int bJaoks = väli[((int) kujund.b.getY() / suurus)][((int) kujund.b.getX() / suurus)];
            int cJaoks = väli[((int) kujund.c.getY() / suurus)][((int) kujund.c.getX() / suurus)];
            int dJaoks = väli[((int) kujund.d.getY() / suurus)][((int) kujund.d.getX() / suurus)];
            if (aJaoks == 0 && aJaoks == bJaoks && bJaoks == cJaoks && cJaoks == dJaoks) {
                kujund.a.setX(kujund.a.getX() + käiguSuurus);
                kujund.b.setX(kujund.b.getX() + käiguSuurus);
                kujund.c.setX(kujund.c.getX() + käiguSuurus);
                kujund.d.setX(kujund.d.getX() + käiguSuurus);
            }
        }
    }
    /** täpselt sama põhimõte vasakule ning alla liigutamise korral
     */

    public static void liigutaVasakule (Kujund kujund) {
        if (kujund.a.getX() - käiguSuurus >= 0 && kujund.b.getX() - käiguSuurus >= 0
                && kujund.c.getX() - käiguSuurus >= 0 && kujund.d.getX() - käiguSuurus >= 0) {
            int aJaoks = väli[((int) kujund.a.getY() / suurus)][((int) kujund.a.getX() / suurus)];
            int bJaoks = väli[((int) kujund.b.getY() / suurus)][((int) kujund.b.getX() / suurus)];
            int cJaoks = väli[((int) kujund.c.getY() / suurus)][((int) kujund.c.getX() / suurus)];
            int dJaoks = väli[((int) kujund.d.getY() / suurus)][((int) kujund.d.getX() / suurus)];
            if (aJaoks == 0 && aJaoks == bJaoks && bJaoks == cJaoks && cJaoks == dJaoks) {
                kujund.a.setX(kujund.a.getX() - käiguSuurus);
                kujund.b.setX(kujund.b.getX() - käiguSuurus);
                kujund.c.setX(kujund.c.getX() - käiguSuurus);
                kujund.d.setX(kujund.d.getX() - käiguSuurus);
            }
        }
    }
    /** siin on kaks juhtu
     * 1. kui jõuame põrandani või varasema kujundini, siis kujund juba ei saa kuskile liikuda, seega teeme kõik tema poolt hõivatud välja blokid ühtedeks
     * 2. kui veel pole takistuseni jõudnud, siis liiguta kujundit edasi
     */

    public static void liigutaAlla(Kujund kujund1) {
        if (kujund1.a.getY() == kõrgus - suurus || kujund1.b.getY() == kõrgus - suurus || kujund1.c.getY() == kõrgus - suurus // Kui kujund on jõudnud põrandani või varasema kujundini...
                || kujund1.d.getY() == kõrgus - suurus || kontrolliA(kujund1) || kontrolliB(kujund1) || kontrolliC(kujund1) || kontrolliD(kujund1)) {

            väli[(int) kujund1.a.getY() / suurus][(int) kujund1.a.getX() / suurus] = 1;  // ... muuda vastavad blokid väljal ühtedeks(1)
            väli[(int) kujund1.b.getY() / suurus][(int) kujund1.b.getX() / suurus] = 1;
            väli[(int) kujund1.c.getY() / suurus][(int) kujund1.c.getX() / suurus] = 1;
            väli[(int) kujund1.d.getY() / suurus][(int) kujund1.d.getX() / suurus] = 1;
            ruudud[(int) kujund1.a.getY() / suurus][(int) kujund1.a.getX() / suurus] = kujund1.a;  // ... muuda vastavad blokid väljal ühtedeks(1)
            ruudud[(int) kujund1.b.getY() / suurus][(int) kujund1.b.getX() / suurus] = kujund1.b;
            ruudud[(int) kujund1.c.getY() / suurus][(int) kujund1.c.getX() / suurus] = kujund1.c;
            ruudud[(int) kujund1.d.getY() / suurus][(int) kujund1.d.getX() / suurus] = kujund1.d;

            Kujund a = järgmineKujund; // Võta järgmine kujund,
            aktiivneKujund = a;  // muuda ta aktiivseks
            järgmineKujund = Tee_Kujund.teeKujund(); // ja kuva ekraanile
            paan.getChildren().addAll(a.a, a.b, a.c, a.d);
            for (int[] ints : väli) {
                System.out.println(Arrays.toString(ints));
            }

            System.out.println();
            for (int i = väli.length - 1; i >= 0; i--) {
                boolean taisRida = true;
                for (int j = väli[0].length - 1; j >= 0; j--) {
                    if (väli[i][j] == 0) {
                        taisRida = false;
                    }
                }
                if (taisRida) {
                    for (int j = väli[0].length - 1; j >= 0; j--) {
                        väli[i][j] = 0;
                    }
                    for (int j = ruudud[0].length - 1; j >= 0; j--) {
                        System.out.println("eemaldatud");
                        paan.getChildren().remove(ruudud[i][j]);
                    }
                    for (int k = i; k >= 0; k--) {
                        for (int m = ruudud[0].length - 1; m >= 0; m--) {
                            try {
                                ruudud[k][m] = ruudud[k - 1][m];
                                väli[k][m] = väli[k-1][m];
                                ruudud[k][m].setY(ruudud[k][m].getY() + käiguSuurus);
                            }
                            catch (IndexOutOfBoundsException e){

                            }
                            catch (NullPointerException e){

                            }


                        }

                    }
                }
            }
        }


        if (kujund1.a.getY() + käiguSuurus < kõrgus && kujund1.b.getY() + käiguSuurus < kõrgus && kujund1.c.getY() + käiguSuurus < kõrgus  // Kui pole jõutud põrandani
                && kujund1.d.getY() + käiguSuurus < kõrgus) {
            int aNext = väli[(int) kujund1.a.getY() / suurus][((int) kujund1.a.getX() / suurus)];  // Kujundi blokkidest ühe sammu võrra all pool asuvate blokkide väärtused
            int bNext = väli[(int) kujund1.b.getY() / suurus][((int) kujund1.b.getX() / suurus)];  // 0, kui tühi blokk, 1 kui hõivatud blokk
            int cNext = väli[(int) kujund1.c.getY() / suurus][((int) kujund1.c.getX() / suurus)];
            int dNext = väli[(int) kujund1.d.getY() / suurus][((int) kujund1.d.getX() / suurus)];
            if (aNext == 0 && aNext == bNext && bNext == cNext && cNext == dNext) {  // Kui kõik all pool olevad blokid ei ole osa mingist kujundist (st. == 0),
                kujund1.a.setY(kujund1.a.getY() + käiguSuurus); // Siis liiguta aktiivset kujundit ühe sammu allapoole
                kujund1.b.setY(kujund1.b.getY() + käiguSuurus);
                kujund1.c.setY(kujund1.c.getY() + käiguSuurus);
                kujund1.d.setY(kujund1.d.getY() + käiguSuurus);
            }
        }
    }
    public static void keera(Kujund kujund){

        if (kujund.kujundiNimi.equals("b3")) {                          //   **
            kujund.a.setX(kujund.a.getX() - suurus);                    //   *   -->   *
            kujund.a.setY(kujund.a.getY() + suurus);                    //   *         ***
            //kujund.b.setX(kujund.b.getX() - suurus);
            kujund.b.setY(kujund.b.getY() + suurus*2);
            kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() + suurus*2);
            //kujund.d.setY(kujund.d.getY() + suurus);
            kujund.kujundiNimi = "b";
        }

        else if (kujund.kujundiNimi.equals("b2")) {                     //            **
            //kujund.a.setX(kujund.a.getX() + suurus);                  // ***   -->  *
            kujund.a.setY(kujund.a.getY() - suurus*2);                  //   *        *
            kujund.b.setX(kujund.b.getX() - suurus);
            kujund.b.setY(kujund.b.getY() - suurus);
            //kujund.c.setX(kujund.c.getX() - suurus);
            //kujund.c.setY(kujund.c.getY() - suurus);
            kujund.d.setX(kujund.d.getX() + suurus);
            kujund.d.setY(kujund.d.getY() + suurus);
            kujund.kujundiNimi = "b3";
        }
        else if (kujund.kujundiNimi.equals("b1")) {                        //   *
            kujund.a.setX(kujund.a.getX() + suurus);                       //   *   -->    ***
            //kujund.a.setY(kujund.a.getY() + suurus );                    //  **            *
            //kujund.b.setX(kujund.b.getX() + suurus);
            kujund.b.setY(kujund.b.getY() - suurus);
            kujund.c.setX(kujund.c.getX() - suurus);
            //kujund.c.setY(kujund.c.getY() - suurus);
            kujund.d.setX(kujund.d.getX() - suurus*2);
            kujund.d.setY(kujund.d.getY() + suurus);
            kujund.kujundiNimi = "b2";
        }
        else if (kujund.kujundiNimi.equals("b")) {                        //             *
            //kujund.a.setX(kujund.a.getX());                             // *    -->    *
            kujund.a.setY(kujund.a.getY() + suurus );                     // ***        **
            kujund.b.setX(kujund.b.getX() + suurus);
          //  kujund.b.setY();
            //kujund.c.setX();
            kujund.c.setY(kujund.c.getY() - suurus);
            kujund.d.setX(kujund.d.getX() - suurus);
            kujund.d.setY(kujund.d.getY() - suurus*2);
            kujund.kujundiNimi = "b1";
        }


        //          **
        //   *  -->  *
        // ***       *
        else if (kujund.kujundiNimi.equals("bb")) {
            kujund.a.setX(kujund.a.getX() - suurus);
            kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() + suurus*2);
            //kujund.b.setY(kujund.b.getY() - suurus*2);
            kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() - suurus);
            //kujund.d.setX(kujund.d.getX() + suurus*2);
            kujund.d.setY(kujund.d.getY() - suurus*2);
            kujund.kujundiNimi = "bb1";
        }
        // **
        //  *   -->   ***
        //  *         *
        else if (kujund.kujundiNimi.equals("bb1")) {
            //kujund.a.setX(kujund.a.getX() - suurus);
            kujund.a.setY(kujund.a.getY() + suurus*2);
            kujund.b.setX(kujund.b.getX() + suurus);
            kujund.b.setY(kujund.b.getY() - suurus);
            //kujund.c.setX(kujund.c.getX() - suurus);
            //kujund.c.setY(kujund.c.getY() - suurus);
            kujund.d.setX(kujund.d.getX() - suurus);
            kujund.d.setY(kujund.d.getY() + suurus);
            kujund.kujundiNimi = "bb2";
        }
        //            *
        //  ***  -->  *
        //  *         **
        else if (kujund.kujundiNimi.equals("bb2")) {
            kujund.a.setX(kujund.a.getX() + suurus);
            //kujund.a.setY(kujund.a.getY() + suurus*2);
            kujund.b.setX(kujund.b.getX() - suurus*2);
            kujund.b.setY(kujund.b.getY() - suurus);
            kujund.c.setX(kujund.c.getX() - suurus);
            //kujund.c.setY(kujund.c.getY() - suurus);
            //kujund.d.setX(kujund.d.getX() - suurus);
            kujund.d.setY(kujund.d.getY() + suurus);
            kujund.kujundiNimi = "bb3";
        }
        //  *
        //  *    -->      *
        //  **          ***
        else if (kujund.kujundiNimi.equals("bb3")) {
            //kujund.a.setX(kujund.a.getX() + suurus);
            kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() - suurus);
            kujund.b.setY(kujund.b.getY() + suurus*2);
            //kujund.c.setX(kujund.c.getX() - suurus);
            kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() + suurus);
            //kujund.d.setY(kujund.d.getY() - suurus);
            kujund.kujundiNimi = "bb";
        }
        //   *           *
        //  ***   -->   **
        //               *
        else if (kujund.kujundiNimi.equals("t")) {
            kujund.a.setX(kujund.a.getX() - suurus);
            kujund.a.setY(kujund.a.getY() + suurus);
            kujund.b.setX(kujund.b.getX() + suurus);
            kujund.b.setY(kujund.b.getY() + suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            //kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() - suurus);
            kujund.d.setY(kujund.d.getY() - suurus);
            kujund.kujundiNimi = "t1";
        }
        //   *
        //  **   -->   ***
        //   *          *
        else if (kujund.kujundiNimi.equals("t1")) {
            kujund.a.setX(kujund.a.getX() + suurus);
            kujund.a.setY(kujund.a.getY() + suurus);
            kujund.b.setX(kujund.b.getX() + suurus);
            kujund.b.setY(kujund.b.getY() - suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            //kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() - suurus);
            kujund.d.setY(kujund.d.getY() + suurus);
            kujund.kujundiNimi = "t2";
        }
        //             *
        //  ***  -->   **
        //   *         *
        else if (kujund.kujundiNimi.equals("t2")) {
            kujund.a.setX(kujund.a.getX() + suurus);
            kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() - suurus);
            kujund.b.setY(kujund.b.getY() - suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            //kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() + suurus);
            kujund.d.setY(kujund.d.getY() + suurus);
            kujund.kujundiNimi = "t3";
        }
        //   *            *
        //   **   --->   ***
        //   *
        else if (kujund.kujundiNimi.equals("t3")) {
            kujund.a.setX(kujund.a.getX() - suurus);
            kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() - suurus);
            kujund.b.setY(kujund.b.getY() + suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            //kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() + suurus);
            kujund.d.setY(kujund.d.getY() - suurus);
            kujund.kujundiNimi = "t";
        }
        //               *
        //               *
        //  ****  --->   *
        //               *
        else if (kujund.kujundiNimi.equals("g")) {
            kujund.a.setX(kujund.a.getX() + suurus*2);
            kujund.a.setY(kujund.a.getY() + suurus);
            kujund.b.setX(kujund.b.getX() + suurus);
            //kujund.b.setY(kujund.b.getY() + suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() - suurus);
            kujund.d.setX(kujund.d.getX() - suurus);
            kujund.d.setY(kujund.d.getY() - suurus*2);
            kujund.kujundiNimi = "g1";
        }
        //               *
        //               *
        //  ****  <---   *
        //               *
        else if (kujund.kujundiNimi.equals("g1")) {
            kujund.a.setX(kujund.a.getX() - suurus*2);
            kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() - suurus);
            //kujund.b.setY(kujund.b.getY() + suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() + suurus);
            kujund.d.setY(kujund.d.getY() + suurus*2);
            kujund.kujundiNimi = "g";
        }
        //             *
        //  **  -->   **
        //   **       *
        else if (kujund.kujundiNimi.equals("oo")) {
            //kujund.a.setX(kujund.a.getX() - suurus*2);
            kujund.a.setY(kujund.a.getY() + suurus);
            kujund.b.setX(kujund.b.getX() - suurus);
            //kujund.b.setY(kujund.b.getY() + suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() - suurus);
            kujund.d.setX(kujund.d.getX() - suurus);
            kujund.d.setY(kujund.d.getY() - suurus*2);
            kujund.kujundiNimi = "oo1";
        }
        //   *
        //  **  -->  **
        //  *         **
        else if (kujund.kujundiNimi.equals("oo1")) {
            //kujund.a.setX(kujund.a.getX() - suurus*2);
            kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() + suurus);
            //kujund.b.setY(kujund.b.getY() + suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() + suurus);
            kujund.d.setY(kujund.d.getY() + suurus*2);
            kujund.kujundiNimi = "oo";
        }
        //             *
        //   **   -->  **
        //  **          *
        else if (kujund.kujundiNimi.equals("o")) {
            kujund.a.setX(kujund.a.getX() - suurus*2);
            //kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() - suurus);
            kujund.b.setY(kujund.b.getY() - suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() - suurus);
            kujund.d.setX(kujund.d.getX() + suurus);
            //kujund.d.setY(kujund.d.getY() + suurus*2);
            kujund.kujundiNimi = "o1";
        }
        //             *
        //   **   <--  **
        //  **          *
        else if (kujund.kujundiNimi.equals("o1")) {
            kujund.a.setX(kujund.a.getX() + suurus*2);
            //kujund.a.setY(kujund.a.getY() - suurus);
            kujund.b.setX(kujund.b.getX() + suurus);
            kujund.b.setY(kujund.b.getY() + suurus);
            //kujund.c.setX(kujund.c.getX() + suurus);
            kujund.c.setY(kujund.c.getY() + suurus);
            kujund.d.setX(kujund.d.getX() - suurus);
            //kujund.d.setY(kujund.d.getY() + suurus*2);
            kujund.kujundiNimi = "o";
        }





    }

    /**
     * siin selleks, et kontrollida, kas on kujundi mingi osa all olemas teine kujund
     */

    static boolean kontrolliA(Kujund form) {
        return (väli[(int) form.a.getY() / suurus+1][((int) form.a.getX() / suurus)] == 1);
    }
    static boolean kontrolliB(Kujund form) {
        return (väli[(int) form.b.getY() / suurus+1][((int) form.b.getX() / suurus)] == 1);
    }
    static boolean kontrolliC(Kujund form) {
        return (väli[(int) form.c.getY() / suurus+1][((int) form.c.getX() / suurus)] == 1);
    }
    static boolean kontrolliD(Kujund form) {
        return (väli[(int) form.d.getY() / suurus+1][((int) form.d.getX() / suurus) ] == 1);
    }
}

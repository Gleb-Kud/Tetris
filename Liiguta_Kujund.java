package com.example.tetriss;

public class Liiguta_Kujund {
    public static final int suurus = Peaklass.suurus;
    public static final int käiguSuurus = suurus;   //kuna liigutamine toimub suuruse võrra, siis on sama, aga koodis on lihtsam jälgida
    public static int kõrgus = Peaklass.kõrgus;
    public static int laius = Peaklass.laius;
    public static int[][] väli = Peaklass.väli;
    public static Kujundid järgmineKujund = Peaklass.getJärgmineKujund();


    /** liigutamine toimub ainult siis, kui seda miski ei tõkesta
     * ning välja plokk, kuhu tahetakse liigutada iga kujundi osa, on samuti mitte hõivatud ehk 0
     */


    public static void liigutaParemale (Kujundid kujund) {
        if (kujund.a.getX() + käiguSuurus <= laius - suurus && kujund.b.getX() + käiguSuurus <= laius - suurus
                && kujund.c.getX() + käiguSuurus <= laius - suurus && kujund.d.getX() + käiguSuurus <= laius - suurus) {
            int aJaoks = väli[((int) kujund.a.getX() / suurus) + 1][((int) kujund.a.getY() / suurus)];
            int bJaoks = väli[((int) kujund.b.getX() / suurus) + 1][((int) kujund.b.getY() / suurus)];
            int cJaoks = väli[((int) kujund.c.getX() / suurus) + 1][((int) kujund.c.getY() / suurus)];
            int dJaoks = väli[((int) kujund.d.getX() / suurus) + 1][((int) kujund.d.getY() / suurus)];
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

    public static void liigutaVasakule (Kujundid kujund) {
        if (kujund.a.getX() - käiguSuurus >= 0 && kujund.b.getX() - käiguSuurus >= 0
                && kujund.c.getX() - käiguSuurus >= 0 && kujund.d.getX() - käiguSuurus >= 0) {
            int aJaoks = väli[((int) kujund.a.getX() / suurus) - 1][((int) kujund.a.getY() / suurus)];
            int bJaoks = väli[((int) kujund.b.getX() / suurus) - 1][((int) kujund.b.getY() / suurus)];
            int cJaoks = väli[((int) kujund.c.getX() / suurus) - 1][((int) kujund.c.getY() / suurus)];
            int dJaoks = väli[((int) kujund.d.getX() / suurus) - 1][((int) kujund.d.getY() / suurus)];
            if (aJaoks == 0 && aJaoks == bJaoks && bJaoks == cJaoks && cJaoks == dJaoks) {
                kujund.a.setX(kujund.a.getX() - käiguSuurus);
                kujund.b.setX(kujund.b.getX() - käiguSuurus);
                kujund.c.setX(kujund.c.getX() - käiguSuurus);
                kujund.d.setX(kujund.d.getX() - käiguSuurus);
            }
        }
    }

    /** siin on kaks juhtu
     * 1. kui jõuame põrandani, siis kujund juba ei saa kuskile liikuda, seega teeme kõik tema poolt hõivatud välja plokid ühtedeks
     * 2. kui veel pole takistuseni jõudnud, siis on mõttekäik sama nagu ülespool
     */

}

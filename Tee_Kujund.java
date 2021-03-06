package com.example.tetriss;

import javafx.scene.shape.Rectangle;

public class Tee_Kujund {
    public static final int suurus = Peaklass.suurus;  // ühe bloki suurus
    //public static final int kõrgus = Peaklass.kõrgus;
    public static int laius = Peaklass.laius;



    public static Kujundid teeKujund() {
        double random =  (Math.random() * 100);
        String kujundiNimi;
        Rectangle a = new Rectangle(suurus - 1, suurus - 1);  //suurus - 1, et oleksid väikesed vahed iga ploki vahel (et näeks paremini välja)
        Rectangle b = new Rectangle(suurus - 1, suurus - 1);
        Rectangle c = new Rectangle(suurus - 1, suurus - 1);
        Rectangle d = new Rectangle(suurus - 1, suurus - 1);

        if (random < 14.29) {
            a.setX(laius / 2 - suurus);    // *
            a.setY(0);                     // ***
            b.setX(laius / 2 - suurus);
            b.setY(suurus);
            c.setX(laius / 2);
            c.setY(suurus);
            d.setX(laius / 2 + suurus);
            d.setY(suurus);
            kujundiNimi = "b";
        }

        else if (random < 28.58) {
            a.setX(laius / 2 + suurus);    //   *
            a.setY(0);                     // ***
            b.setX(laius / 2 - suurus);
            b.setY(suurus);
            c.setX(laius / 2);
            c.setY(suurus);
            d.setX(laius / 2 + suurus);
            d.setY(suurus);
            kujundiNimi = "bb";
        }

        else if (random < 42.87) {
            a.setX(laius / 2);             //  *
            a.setY(0);                     // ***
            b.setX(laius / 2 - suurus);
            b.setY(suurus);
            c.setX(laius / 2);
            c.setY(suurus);
            d.setX(laius / 2 + suurus);
            d.setY(suurus);
            kujundiNimi = "t";
        }

        else if (random < 57.16) {
            a.setX(laius / 2 - suurus);  // **
            a.setY(0);                   // **
            b.setX(laius / 2);
            b.setY(0);
            c.setX(laius / 2 - suurus);
            c.setY(suurus);
            d.setX(laius / 2);
            d.setY(suurus);
            kujundiNimi = "r";
        }
                                      //   **
        else if (random < 71.45) {    //    **
            a.setX(laius / 2 + suurus);
            a.setY(0);
            b.setX(laius / 2);
            b.setY(0);
            c.setX(laius / 2);
            c.setY(suurus);
            d.setX(laius / 2 - suurus);
            d.setY(suurus);
            kujundiNimi = "o";
        }
                                      //     **
        else if (random < 85.74) {    //    **
            a.setX(laius / 2 - suurus);
            a.setY(0);
            b.setX(laius / 2);
            b.setY(0);
            c.setX(laius / 2);
            c.setY(suurus);
            d.setX(laius / 2 + suurus);
            d.setY(suurus);
            kujundiNimi = "oo";
        }
        else {
            a.setX(laius / 2 - 2 * suurus);  // ****
            a.setY(0);
            b.setX(laius / 2 - suurus);
            b.setY(0);
            c.setX(laius / 2);
            c.setY(0);
            d.setX(laius / 2 + suurus);
            d.setY(0);
            kujundiNimi = "g";
        }
        return new Kujundid(a, b, c, d, kujundiNimi);

    }
}

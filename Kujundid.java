package com.example.tetriss;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**klass potentsiaalselt selleks, et lasta kasutajal muuta nende kujundite orientatsiooni
 * praegu aga siin toimub kujundi värvi määramine
 * ka eeldame, et iga kujund koosneb vaid neljast plokist
 * need on Rectangles a,b,c,d
 */
public class Kujundid {
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    int praeguneOrientatsioon = 1;
    String kujundiNimi;
    Color värv;

    public Kujundid(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String kujundiNimi) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.kujundiNimi = kujundiNimi;

        switch (kujundiNimi) {
            case "b" -> värv = Color.BLUE;
            case "r" -> värv = Color.RED;
            case "o" -> värv = Color.ORANGE;
            case "g" -> värv = Color.GREEN;
            case "t" -> värv = Color.DEEPPINK;
            case "oo" -> värv = Color.YELLOWGREEN;
            case "bb" -> värv = Color.LIGHTBLUE;
        }

        this.a.setFill(värv);
        this.b.setFill(värv);
        this.c.setFill(värv);
        this.d.setFill(värv);
    }

    public void muudaOrientatsiooni() {
        if (praeguneOrientatsioon == 4)
            praeguneOrientatsioon = 1;
        else
            praeguneOrientatsioon++;
    }

    public void setKujundiNimi(String kujundiNimi) {
        this.kujundiNimi = kujundiNimi;
    }

    public String getKujundiNimi() {
        return kujundiNimi;
    }
}

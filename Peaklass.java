package com.example.tetris;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Peaklass extends Application {
    public static final int suurus = 25;
    public static final int käiguSuurus = suurus;
    public static int kõrgus = suurus * 30;
    public static int laius = suurus * 15;
    public static int[][] väli = new int[laius / suurus][kõrgus / suurus];
    private static Pane paan = new Pane();
    private static Scene stseen = new Scene(paan, laius, kõrgus);
    private static Kujundid kujund;
    private static Kujundid järgmineKujund = Tee_Kujund.teeKujund();


    public static Kujundid getJärgmineKujund() {
        return järgmineKujund;
    }

    @Override
    public void start(Stage stage) throws IOException {
        for (int[] ints : väli) {
            Arrays.fill(ints, 0);    //alguses kui midagi veel pole, siis iga välja plok on null
        }                                //selline nummerdamine võib aidata, kui tahame täied read alt eemaldada

        Kujundid a = järgmineKujund;
        paan.getChildren().addAll(a.a, a.b, a.c, a.d);

        liiguta(a);
        kujund = a;
        järgmineKujund = Tee_Kujund.teeKujund();
        stage.setScene(stseen);
        stage.setTitle("Mängime!");
        stage.show();
    }

    private static void liiguta(Kujundid a) {
        stseen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT -> Liiguta_Kujund.liigutaParemale(kujund);
                    case LEFT -> Liiguta_Kujund.liigutaVasakule(kujund);
                    case DOWN -> liigutaAlla(kujund);         //siin on vaja teha peaklassis
                }
            }
        });
    }

    public static void liigutaAlla(Kujundid kujund1) {
        if (kujund1.a.getY() == kõrgus - suurus || kujund1.b.getY() == kõrgus - suurus || kujund1.c.getY() == kõrgus - suurus
                || kujund1.d.getY() == kõrgus - suurus || kontrolliA(kujund1) || kontrolliB(kujund1) || kontrolliC(kujund1) || kontrolliD(kujund1)) {
            väli[(int) kujund1.a.getX() / suurus][(int) kujund1.a.getY() / suurus] = 1;
            väli[(int) kujund1.b.getX() / suurus][(int) kujund1.b.getY() / suurus] = 1;
            väli[(int) kujund1.c.getX() / suurus][(int) kujund1.c.getY() / suurus] = 1;
            väli[(int) kujund1.d.getX() / suurus][(int) kujund1.d.getY() / suurus] = 1;

            Kujundid a = järgmineKujund;
            kujund = a;
            järgmineKujund = Tee_Kujund.teeKujund();
            paan.getChildren().addAll(a.a, a.b, a.c, a.d);
            liiguta(a);
        }
        if (kujund1.a.getY() + käiguSuurus < kõrgus && kujund1.b.getY() + käiguSuurus < kõrgus && kujund1.c.getY() + käiguSuurus < kõrgus
                && kujund1.d.getY() + käiguSuurus < kõrgus) {
            int aJaoks = väli[(int) kujund1.a.getX() / suurus][((int) kujund1.a.getY() / suurus) + 1];
            int bJaoks = väli[(int) kujund1.b.getX() / suurus][((int) kujund1.b.getY() / suurus) + 1];
            int cJaoks = väli[(int) kujund1.c.getX() / suurus][((int) kujund1.c.getY() / suurus) + 1];
            int dJaoks = väli[(int) kujund1.d.getX() / suurus][((int) kujund1.d.getY() / suurus) + 1];
            if (aJaoks == 0 && bJaoks == bJaoks && bJaoks == cJaoks && cJaoks == dJaoks) {
                kujund1.a.setY(kujund1.a.getY() + käiguSuurus);
                kujund1.b.setY(kujund1.b.getY() + käiguSuurus);
                kujund1.c.setY(kujund1.c.getY() + käiguSuurus);
                kujund1.d.setY(kujund1.d.getY() + käiguSuurus);
            }
        }
    }

    /**
     * siin selleks, et kontrollida, kas on kujundi mingi osa all olemas teine kujund
     */

    private static boolean kontrolliA(Kujundid form) {
        return (väli[(int) form.a.getX() / suurus][((int) form.a.getY() / suurus) + 1] == 1);
    }
    private static boolean kontrolliB(Kujundid form) {
        return (väli[(int) form.b.getX() / suurus][((int) form.b.getY() / suurus) + 1] == 1);
    }
    private static boolean kontrolliC(Kujundid form) {
        return (väli[(int) form.c.getX() / suurus][((int) form.c.getY() / suurus) + 1] == 1);
    }
    private static boolean kontrolliD(Kujundid form) {
        return (väli[(int) form.d.getX() / suurus][((int) form.d.getY() / suurus) + 1] == 1);
    }



    public static void main(String[] args) {
        launch();
    }
}

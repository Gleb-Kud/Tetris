package com.example.tetriss;

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
    public static final int suurus = 25;   // Ühe ruudu suurus mänguväljal
    public static final int käiguSuurus = suurus;
    public static int kõrgus = suurus * 30;  // Stseeni mõõdud:  y koordinaadid
    public static int laius = suurus * 15;   // x koordinaadid
    public static int[][] väli = new int[laius / suurus][kõrgus / suurus]; // Mängu väli, jagatud ruutudeks
    private static Pane paan = new Pane();
    private static Scene stseen = new Scene(paan, laius, kõrgus);
    private static Kujundid aktiivneKujund;  // Liigutatav kujund (aktiivne kujund)
    private static Kujundid järgmineKujund = Tee_Kujund.teeKujund(); // Esimene kujund, mis ekraanile tekib


    public static Kujundid getJärgmineKujund() {
        return järgmineKujund;
    }

    public static void initVäli(int[][] väli){
        for (int[] rida : väli) {
            Arrays.fill(rida, 0);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        initVäli(väli);                 // alguses kui midagi veel pole, siis iga välja blokk on null(0)
                                        // Kui liigutada kujundit sobivasse kohta, muutuvad vastavad kujundi blokid väljas ühtedeks(1)
                                        // selline nummerdamine võib aidata, kui tahame täis read alt eemaldada

        Kujundid a = järgmineKujund;
        paan.getChildren().addAll(a.a, a.b, a.c, a.d);  // Lisa kujundi blokid ekraanile

        liiguta();
        aktiivneKujund = a; // Seo genereeritud kujund aktiivseks kujundiks
        järgmineKujund = Tee_Kujund.teeKujund(); // Genereeri uus järgmine kujund
        stage.setScene(stseen);
        stage.setTitle("Tetris");
        stage.show();
    }
    // Liigutab aktiivset kujundit vastavalt nupuvajutusele
    private static void liiguta() {
        stseen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT -> Liiguta_Kujund.liigutaParemale(aktiivneKujund);
                    case LEFT -> Liiguta_Kujund.liigutaVasakule(aktiivneKujund);
                    case DOWN -> liigutaAlla(aktiivneKujund);         //siin on vaja teha peaklassis
                }
            }
        });
    }
    /** siin on kaks juhtu
     * 1. kui jõuame põrandani või varasema kujundini, siis kujund juba ei saa kuskile liikuda, seega teeme kõik tema poolt hõivatud välja blokid ühtedeks
     * 2. kui veel pole takistuseni jõudnud, siis liiguta kujundit edasi
     */
    public static void liigutaAlla(Kujundid kujund1) {
        if (kujund1.a.getY() == kõrgus - suurus || kujund1.b.getY() == kõrgus - suurus || kujund1.c.getY() == kõrgus - suurus // Kui kujund on jõudnud põrandani või varasema kujundini...
                || kujund1.d.getY() == kõrgus - suurus || kontrolliA(kujund1) || kontrolliB(kujund1) || kontrolliC(kujund1) || kontrolliD(kujund1)) {
            väli[(int) kujund1.a.getX() / suurus][(int) kujund1.a.getY() / suurus] = 1;  // ... muuda vastavad blokid väljal ühtedeks(1)
            väli[(int) kujund1.b.getX() / suurus][(int) kujund1.b.getY() / suurus] = 1;
            väli[(int) kujund1.c.getX() / suurus][(int) kujund1.c.getY() / suurus] = 1;
            väli[(int) kujund1.d.getX() / suurus][(int) kujund1.d.getY() / suurus] = 1;

            Kujundid a = järgmineKujund; // Võta järgmine kujund,
            aktiivneKujund = a;  // muuda ta aktiivseks
            järgmineKujund = Tee_Kujund.teeKujund(); // ja kuva ekraanile
            paan.getChildren().addAll(a.a, a.b, a.c, a.d);

        }
        if (kujund1.a.getY() + käiguSuurus < kõrgus && kujund1.b.getY() + käiguSuurus < kõrgus && kujund1.c.getY() + käiguSuurus < kõrgus  // Kui pole jõutud põrandani
                && kujund1.d.getY() + käiguSuurus < kõrgus) {
            int aNext = väli[(int) kujund1.a.getX() / suurus][((int) kujund1.a.getY() / suurus) + 1];  // Kujundi blokkidest ühe sammu võrra all pool asuvate blokkide väärtused
            int bNext = väli[(int) kujund1.b.getX() / suurus][((int) kujund1.b.getY() / suurus) + 1];  // 0, kui tühi blokk, 1 kui hõivatud blokk
            int cNext = väli[(int) kujund1.c.getX() / suurus][((int) kujund1.c.getY() / suurus) + 1];
            int dNext = väli[(int) kujund1.d.getX() / suurus][((int) kujund1.d.getY() / suurus) + 1];
            if (aNext == 0 && aNext == bNext && bNext == cNext && cNext == dNext) {  // Kui kõik all pool olevad blokid ei ole osa mingist kujundist (st. == 0),
                kujund1.a.setY(kujund1.a.getY() + käiguSuurus); // Siis liiguta aktiivset kujundit ühe sammu allapoole
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

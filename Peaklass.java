package com.example.tetriss;

import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.example.tetriss.Liiguta_Kujund.*;

public class Peaklass extends Application {
    public static final int suurus = 25;   // Ühe ruudu suurus mänguväljal
    public static final int käiguSuurus = suurus;
    public static int kõrgus = suurus * 30;  // Stseeni mõõdud:  y koordinaadid
    public static int laius = suurus * 15;   // x koordinaadid
    public static int[][] väli = new int[kõrgus / suurus][laius / suurus - 1]; // Mängu väli, jagatud ruutudeks
    public static Rectangle[][] ruudud = new Rectangle[kõrgus / suurus][laius / suurus]; // Mängu väli, jagatud ruutudeks
    protected static Pane paan = new Pane();
    private static Scene stseen = new Scene(paan, laius, kõrgus);
    protected static Kujund aktiivneKujund;  // Liigutatav kujund (aktiivne kujund)
    private static Kujund järgmineKujund = Tee_Kujund.teeKujund(); // Esimene kujund, mis ekraanile tekib



    public static Kujund getJärgmineKujund() {
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

        Kujund a = järgmineKujund;
        paan.getChildren().addAll(a.a, a.b, a.c, a.d);  // Lisa kujundi blokid ekraanile

        setUpControls();
        aktiivneKujund = a; // Seo genereeritud kujund aktiivseks kujundiks
        järgmineKujund = Tee_Kujund.teeKujund(); // Genereeri uus järgmine kujund
        stage.setScene(stseen);
        stage.setTitle("Tetris");
        stage.show();
        for (int[] ints : väli) {
            System.out.println(Arrays.toString(ints));
        }


    }
    // Liigutab aktiivset kujundit vastavalt nupuvajutusele
    private static void setUpControls() {
        stseen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT -> Liiguta_Kujund.liigutaParemale(aktiivneKujund);
                    case LEFT -> Liiguta_Kujund.liigutaVasakule(aktiivneKujund);
                    case DOWN -> {liigutaAlla(aktiivneKujund);}
                }
            }
        });
    }

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
            for (int i = väli.length-1; i >= 0 ; i--) {
                boolean taisRida = true;
                for (int j = väli[0].length-1; j >= 0; j--) {
                    if(väli[i][j] == 0){
                        taisRida = false;
                    }
                }
                if(taisRida){
                    for (int j = väli[0].length-1; j >= 0; j--) {
                        väli[i][j] = 0;
                    }
                    for (int j = ruudud[0].length-1; j >= 0; j--) {
                        System.out.println("eemaldatud");
                        paan.getChildren().remove(ruudud[i][j]);
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


    public static void main(String[] args) {
        launch();
    }


}



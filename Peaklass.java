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
    public static Rectangle[][] ruudud = new Rectangle[kõrgus / suurus][laius / suurus - 1]; // Mängu väli, jagatud ruutudeks
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
        System.out.println(väli.length);


    }
    // Liigutab aktiivset kujundit vastavalt nupuvajutusele
    private static void setUpControls() {
        stseen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT -> Liiguta_Kujund.liigutaParemale(aktiivneKujund);
                    case LEFT -> Liiguta_Kujund.liigutaVasakule(aktiivneKujund);
                    case DOWN -> {Liiguta_Kujund.liigutaAlla(aktiivneKujund);}
                    case SPACE -> Liiguta_Kujund.keera(aktiivneKujund);
                }
            }
        });
    }




    public static void main(String[] args) {
        launch();
    }


}



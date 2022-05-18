package com.example.tetrisparandatud;

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
    public static int kõrgus = suurus * 24;  // Stseeni mõõdud:  y koordinaadid   //muudetud
    public static int laius = suurus * 12;   // x koordinaadid                   //muudetud
    public static int[][] väli = new int[laius / suurus][kõrgus / suurus]; // Mängu väli, jagatud ruutudeks
    private static Pane paan = new Pane();
    private static Scene stseen = new Scene(paan, laius, kõrgus);
    private static Kujundid aktiivneKujund;  // Liigutatav kujund (aktiivne kujund)
    private static Kujundid järgmineKujund = Tee_Kujund.teeKujund(); // Esimene kujund, mis ekraanile tekib
    private static int kontrolli = 0;    //kui väli on üleni täis, siis game over
    private static boolean veelMängime = true;  //game overi puhul muutub falsiks

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

        /**
         * siin pidin googeldama
         */

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (aktiivneKujund.a.getY() == 0 || aktiivneKujund.b.getY() == 0 || aktiivneKujund.c.getY() == 0 || aktiivneKujund.d.getY() == 0)
                            kontrolli++;
                        else
                            kontrolli = 0;
                        if (kontrolli > 10) { //põhimõte on see, et kui kujundi mingi ruut jääb koordinaatide algusesse rohkem kui 10 iteratsiooni (võib reguleerida), see tähendab et teda liikuda enam ei saa
                            Text gameOver = new Text("GAME OVER");
                            gameOver.setX(50);
                            gameOver.setY(200);
                            paan.getChildren().add(gameOver);
                            veelMängime = false;
                        }

                        if (veelMängime) {
                            liigutaAlla(aktiivneKujund);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 200); //siin mida väiksem on period, seda kiirem nad alla liiguvad
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
                    case SPACE -> keera(aktiivneKujund);
                }
            }
        });
    }

    //meetodid, mis liigutavad eraldi iga ristkülikut roteerimiseks (ka pole vaja)

   /* private void liigutaRistkülikAlla(Rectangle rectangle) {
        if (rectangle.getY() + käiguSuurus < kõrgus)
            rectangle.setY((rectangle.getY() + käiguSuurus));
    }

    private void liigutaRistkülikÜles(Rectangle rectangle) {
        if (rectangle.getY() + käiguSuurus > 0)
            rectangle.setY((rectangle.getY() - käiguSuurus));
    }

    private void liigutaRistkülikParemale(Rectangle rectangle) {
        if (rectangle.getX() + käiguSuurus <= laius - suurus)
            rectangle.setX((rectangle.getX() + käiguSuurus));
    }

    private void liigutaRistkülikVasakule(Rectangle rectangle) {
        if (rectangle.getX() - käiguSuurus >= 0)
            rectangle.setX((rectangle.getX() - käiguSuurus));
    }*/
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
            eemaldaRead(paan);

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

    public static void keera(Kujundid kujund){

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

    private static void eemaldaRead(Pane paan) {
        ArrayList<Integer> readEemaldamiseks = new ArrayList<>();
        ArrayList<Node> kõikRistkülikud = new ArrayList<>();
        ArrayList<Node> allesJäänudRistkülikud = new ArrayList<>();

        int täidetudRuutudegaVälju = 0;
        for (int i = 0; i < väli[0].length; i++) {     //loodan, et siin sassi ei läinud
            for (int j = 0; j < väli.length; j++) {
                if (väli[j][i] == 1)
                    täidetudRuutudegaVälju++;
            }
            if (täidetudRuutudegaVälju == väli.length)
                readEemaldamiseks.add(i);
            täidetudRuutudegaVälju = 0;
        }
        while (readEemaldamiseks.size() > 0) {
            for (Node node: paan.getChildren()) {  //tundub, et lihtsalt läbi käia kõiki ristkülikuid pole võimalik (vist ei ole meetodit getAllRectangle vms..)
                if (node instanceof Rectangle)
                    kõikRistkülikud.add(node);
            }
            for (Node node : kõikRistkülikud) {
                Rectangle praegune = (Rectangle) node;
                if (praegune.getY() == readEemaldamiseks.get(0) * suurus) {
                    väli[(int) praegune.getX() / suurus][(int) praegune.getY() / suurus] = 0;
                    paan.getChildren().remove(node);
                }
                else
                    allesJäänudRistkülikud.add(node);
            }
            for (Node node : allesJäänudRistkülikud) {
                Rectangle praegune = (Rectangle) node;
                if (praegune.getY() < readEemaldamiseks.get(0) * suurus) {
                    väli[(int) praegune.getX() / suurus][(int) praegune.getY() / suurus] = 0;
                    praegune.setY(praegune.getY() + suurus);
                    väli[(int) praegune.getX() / suurus][(int) praegune.getY() / suurus] = 1;  //siin pole kindel, kas saab kohe
                }
            }
            readEemaldamiseks.remove(0);
            kõikRistkülikud.clear();
            allesJäänudRistkülikud.clear();
            /*for (Node node: paan.getChildren()) {
                if (node instanceof Rectangle)
                    kõikRistkülikud.add(node);
            }
            for (Node node : kõikRistkülikud) {   //siin vist seda vaja, vähemalt sellega töötab paremini
                Rectangle praegune = (Rectangle) node;
                väli[(int) praegune.getX() / suurus][(int) praegune.getY() / suurus] = 1;
            }
            kõikRistkülikud.clear();*/
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

   /* /**  Seda polnud vaja
     *
     * @param rectangle parasjagu vaadeldav ristküliku ruut (a, b, c või d)
     * @param horisontaalselt kuhu me tahame selle ruudu orienteerida (nt 1 --> ühe võrra paremale, või nt -1 --> ühe võrra vasakule, 0 tähendab, et ta ei liigu)
     * @param vertikaalselt  kuhu me tahame selle ruudu orienteerida (nt 1 --> ühe võrra üles, või nt -1 --> ühe võrra alla, 0 tähendab, et ta ei liigu)
     * @return  kas me saame orienteerida seda ruutu nii, nagu tahame


    private static boolean kontrolliRotatsioon(Rectangle rectangle, int horisontaalselt, int vertikaalselt) {
        boolean saabHorisontaalselt = false;
        boolean saabVertikaalselt = false;

        //esiteks vaatame, kas on see ruut meie mänguvälja sees
        if (horisontaalselt >= 0)
            saabHorisontaalselt = rectangle.getX() + horisontaalselt * käiguSuurus <= laius - suurus;
        else
            saabHorisontaalselt = rectangle.getX() + horisontaalselt * käiguSuurus > 0;

        if (vertikaalselt >= 0)
            saabVertikaalselt = rectangle.getY() - vertikaalselt * käiguSuurus > 0;
        else
            saabVertikaalselt = rectangle.getY() + vertikaalselt * käiguSuurus < kõrgus;

        //tagastamises arvestame ka seda, et see välja ruut, kuhu me tahame meie parasjagu oleva ruudu paigutada, oles mitte hõivatud ehk null
        return väli[((int) rectangle.getX() / suurus) + horisontaalselt][((int) rectangle.getY() / suurus - vertikaalselt)] == 0 && saabHorisontaalselt && saabVertikaalselt;
    }*/



    public static void main(String[] args) {
        launch();
    }
}


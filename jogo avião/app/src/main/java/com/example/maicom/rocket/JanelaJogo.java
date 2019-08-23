package com.example.maicom.rocket;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class JanelaJogo extends View {
    public final int LARGURA = 100, ALTURA = 100;
    public final int MULT = 5;
    private Ator bola;
    private Ator alvo;
    //private Ator explosao;
    private ArrayList<Ator> avioes;
    private Random rng;
    private int x, y;
    private int minX = 0, minY = 0;
    private int maxX = 0, maxY = 0;

    public JanelaJogo(Context contexto) {
        super(contexto);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.bola);
        bola = new Ator(200, 200, src);
        //ceu
        setBackgroundColor(Color.parseColor("#87CEEB"));
        // avioes!
        avioes = new ArrayList<>();
        rng = new Random(System.currentTimeMillis());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bola.desenha(canvas);
        invalidate();

        for (Ator j : avioes) {
            j.desenha(canvas);
        }
        if (alvo != null) {
            alvo.desenha(canvas);
        }
    }

    public void setLimits(int maxX, int maxY) {
        this.maxX = maxX - LARGURA;
        this.maxY = maxY - ALTURA;

        iniciaAvioes();
    }

    private void iniciaAvioes() {
        Bitmap srcAviao = BitmapFactory.decodeResource(getResources(),
                R.drawable.aviao);

        avioes = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            while (true) {
                Ator aviao = new Ator(100, 100, srcAviao);
                aviao.setPos(
                        100 + rng.nextInt(maxX - aviao.getLargura() - 300),
                        rng.nextInt(maxY - aviao.getAltura()));

                boolean colidiu = false;

                for (Ator j : avioes) {
                    if (aviao.colide(j)) {
                        colidiu = true;
                        break;
                    }
                }

                if (!colidiu) {


                    avioes.add(aviao);
                    break;
                }
            }
        }

        Bitmap srcAlvo = BitmapFactory.decodeResource(getResources(),
                R.drawable.alvo);

        alvo = new Ator(400, 350, srcAlvo);
        alvo.setPos(maxX - 250, (maxY - 250) / 2);
    }

    private int limita(int valor, int min, int max) {
        if (valor < min) {
            valor = min;
        } else {
            if (valor > max) {
                valor = max;
            }
        }
        return valor;
    }

    public void move(int x, int y) {
        bola.setPos(
                limita(bola.getX() + x * MULT, minX, maxX),
                limita(bola.getY() + y * MULT, minY, maxY)
        );

        for (Ator j : avioes)
            if (bola.colide(j)) {

                final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.explosao);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                mp.start();
                fimDeJogo(false);
            }

        if (alvo != null && alvo.colide(bola)) {
            /*try {
                new Thread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            fimDeJogo(true);
        }
    }

    private void fimDeJogo(boolean ganhou) {


        String msg = ganhou ? "Você ganhou!" : "Você perdeu";


        if (msg == "Você ganhou!") {

            final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.vitoria);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();


        }


        //metodo inicia avioes dentro do thread
        try {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            bola.setPos(0, 0);
            iniciaAvioes();
            new Thread().sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

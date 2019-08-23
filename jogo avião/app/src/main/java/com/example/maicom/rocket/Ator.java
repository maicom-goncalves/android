package com.example.maicom.rocket;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ator {
    private int x, y;
    private int largura, altura;
    Bitmap imagem;

    public Ator(int largura, int altura, Bitmap imagem) {
        this.largura = largura;
        this.altura = altura;
        this.imagem = Bitmap.createScaledBitmap(imagem, largura, altura, true);
    }

    public boolean colide(Ator b) {
        return x < b.getX2() && getX2() > b.getX() &&
                y < b.getY2() && getY2() > b.getY();
    }

    public int getX() {
        return x;
    }

    public int getX2() {
        return x + largura;
    }

    public int getY() {
        return y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY2() {
        return y + altura;
    }

    public void desenha(Canvas canvas) {
        canvas.drawBitmap(imagem, x, y, null);
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}

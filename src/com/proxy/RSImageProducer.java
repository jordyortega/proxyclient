package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;
import java.awt.image.*;

final class RSImageProducer
        implements ImageProducer, ImageObserver {

    public final int[] anIntArray315;
    final int anInt316;
    final int anInt317;
    private final ColorModel aColorModel318;
    private final Image anImage320;
    private ImageConsumer anImageConsumer319;

    public RSImageProducer(int i, int j, Component component) {
        anInt316 = i;
        anInt317 = j;
        anIntArray315 = new int[i * j];
        aColorModel318 = new DirectColorModel(32, 0xff0000, 65280, 255);
        anImage320 = component.createImage(this);
        method239();
        component.prepareImage(anImage320, this);
        method239();
        component.prepareImage(anImage320, this);
        method239();
        component.prepareImage(anImage320, this);
        initDrawingArea();
    }

    public void initDrawingArea() {
        DrawingArea.initDrawingArea(anInt317, anInt316, anIntArray315);
    }

    public void drawGraphics(int i, Graphics g, int k) {
        method239();
        g.drawImage(anImage320, k, i, this);
    }

    public synchronized void addConsumer(ImageConsumer imageconsumer) {
        anImageConsumer319 = imageconsumer;
        imageconsumer.setDimensions(anInt316, anInt317);
        imageconsumer.setProperties(null);
        imageconsumer.setColorModel(aColorModel318);
        imageconsumer.setHints(14);
    }

    public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
        return anImageConsumer319 == imageconsumer;
    }

    public synchronized void removeConsumer(ImageConsumer imageconsumer) {
        if (anImageConsumer319 == imageconsumer)
            anImageConsumer319 = null;
    }

    public void startProduction(ImageConsumer imageconsumer) {
        addConsumer(imageconsumer);
    }

    public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
        System.out.println("TDLR");
    }

    private synchronized void method239() {
        if (anImageConsumer319 != null) {
            anImageConsumer319.setPixels(0, 0, anInt316, anInt317, aColorModel318, anIntArray315, 0, anInt316);
            anImageConsumer319.imageComplete(2);
        }
    }

    public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
        return true;
    }
}

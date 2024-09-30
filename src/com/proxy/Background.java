package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.proxy.sign.signlink;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Background extends DrawingArea {

    public final int[] palette;
    public int[] myPixels;
    public int myWidth;
    public int myHeight;
    public int myXOffset;
    public int myYOffset;
    public int myMaxWidth;
    private int myMaxHeight;

    public Background(String relativeCachePath) throws IOException {
        File file = new File(signlink.findcachedir(), relativeCachePath);
        if (!file.exists()) {
            throw new RuntimeException("Background file not found: " + file);
        }

        BufferedImage bufferedImage = ImageIO.read(file);
        myWidth = bufferedImage.getWidth();
        myHeight = bufferedImage.getHeight();
        myMaxWidth = myWidth;
        myMaxHeight = myHeight;
        myPixels = new int[myWidth * myHeight];
        List<Integer> palette = new ArrayList<>();
        palette.add(0);
        int paletteIndex = 0;
        for (int j = 0; j < myWidth; j++) {
            for (int k = 0; k < myHeight; k++) {
                int pixel = bufferedImage.getRGB(j, k);
                if (pixel == 0) {
                    myPixels[j + k * myWidth] = 0;
                } else {
                    int index = palette.indexOf(pixel & 0xffffff);
                    if (index == -1) {
                        index = paletteIndex++;
                        palette.add(pixel & 0xffffff);
                    }
                    myPixels[j + k * myWidth] = index & 0xff;
                }
            }
        }
        int[] intPalette = new int[palette.size()];
        for (int i = 0; i < palette.size(); i++) {
            intPalette[i] = palette.get(i);
        }
        this.palette = intPalette;
    }

    public Background(StreamLoader streamLoader, String s, int i) {
        Stream stream = new Stream(streamLoader.getDataForName(s + ".dat"));
        Stream stream_1 = new Stream(streamLoader.getDataForName("index.dat"));
        stream_1.currentOffset = stream.readUnsignedWord();
        myMaxWidth = stream_1.readUnsignedWord();
        myMaxHeight = stream_1.readUnsignedWord();
        int j = stream_1.readUnsignedByte();
        palette = new int[j];
        for (int k = 0; k < j - 1; k++)
            palette[k + 1] = stream_1.read3Bytes();

        for (int l = 0; l < i; l++) {
            stream_1.currentOffset += 2;
            stream.currentOffset += stream_1.readUnsignedWord() * stream_1.readUnsignedWord();
            stream_1.currentOffset++;
        }

        myXOffset = stream_1.readUnsignedByte();
        myYOffset = stream_1.readUnsignedByte();
        myWidth = stream_1.readUnsignedWord();
        myHeight = stream_1.readUnsignedWord();
        int i1 = stream_1.readUnsignedByte();
        int j1 = myWidth * myHeight;
        myPixels = new int[j1];
        if (i1 == 0) {
            for (int k1 = 0; k1 < j1; k1++)
                myPixels[k1] = stream.readSignedByte() & 0xff;

            return;
        }
        if (i1 == 1) {
            for (int l1 = 0; l1 < myWidth; l1++) {
                for (int i2 = 0; i2 < myHeight; i2++)
                    myPixels[l1 + i2 * myWidth] = stream.readSignedByte() & 0xff;

            }

        }
    }

    @Override
    public String toString() {
        return "Background{" +
                "myMaxHeight=" + myMaxHeight +
                ", myMaxWidth=" + myMaxWidth +
                ", myYOffset=" + myYOffset +
                ", myXOffset=" + myXOffset +
                ", myHeight=" + myHeight +
                ", myWidth=" + myWidth +
                '}';
    }

    public void method356() {
        myMaxWidth /= 2;
        myMaxHeight /= 2;
        int abyte0[] = new int[myMaxWidth * myMaxHeight];
        int i = 0;
        for (int j = 0; j < myHeight; j++) {
            for (int k = 0; k < myWidth; k++)
                abyte0[(k + myXOffset >> 1) + (j + myYOffset >> 1) * myMaxWidth] = myPixels[i++];

        }

        myPixels = abyte0;
        myWidth = myMaxWidth;
        myHeight = myMaxHeight;
        myXOffset = 0;
        myYOffset = 0;
    }

    public void method357() {
        if (myWidth == myMaxWidth && myHeight == myMaxHeight)
            return;
        int abyte0[] = new int[myMaxWidth * myMaxHeight];
        int i = 0;
        for (int j = 0; j < myHeight; j++) {
            for (int k = 0; k < myWidth; k++)
                abyte0[k + myXOffset + (j + myYOffset) * myMaxWidth] = myPixels[i++];

        }

        myPixels = abyte0;
        myWidth = myMaxWidth;
        myHeight = myMaxHeight;
        myXOffset = 0;
        myYOffset = 0;
    }

    public void method358() {
        int abyte0[] = new int[myWidth * myHeight];
        int j = 0;
        for (int k = 0; k < myHeight; k++) {
            for (int l = myWidth - 1; l >= 0; l--)
                abyte0[j++] = myPixels[l + k * myWidth];

        }

        myPixels = abyte0;
        myXOffset = myMaxWidth - myWidth - myXOffset;
    }

    public void method359() {
        int abyte0[] = new int[myWidth * myHeight];
        int i = 0;
        for (int j = myHeight - 1; j >= 0; j--) {
            for (int k = 0; k < myWidth; k++)
                abyte0[i++] = myPixels[k + j * myWidth];

        }

        myPixels = abyte0;
        myYOffset = myMaxHeight - myHeight - myYOffset;
    }

    public void method360(int i, int j, int k) {
        for (int i1 = 0; i1 < palette.length; i1++) {
            int j1 = palette[i1] >> 16 & 0xff;
            j1 += i;
            if (j1 < 0)
                j1 = 0;
            else if (j1 > 255)
                j1 = 255;
            int k1 = palette[i1] >> 8 & 0xff;
            k1 += j;
            if (k1 < 0)
                k1 = 0;
            else if (k1 > 255)
                k1 = 255;
            int l1 = palette[i1] & 0xff;
            l1 += k;
            if (l1 < 0)
                l1 = 0;
            else if (l1 > 255)
                l1 = 255;
            palette[i1] = (j1 << 16) + (k1 << 8) + l1;
        }
    }

    public void drawBackground(int i, int k) {
        i += myXOffset;
        k += myYOffset;
        int l = i + k * DrawingArea.width;
        int i1 = 0;
        int j1 = myHeight;
        int k1 = myWidth;
        int l1 = DrawingArea.width - k1;
        int i2 = 0;
        if (k < DrawingArea.topY) {
            int j2 = DrawingArea.topY - k;
            j1 -= j2;
            k = DrawingArea.topY;
            i1 += j2 * k1;
            l += j2 * DrawingArea.width;
        }
        if (k + j1 > DrawingArea.bottomY)
            j1 -= (k + j1) - DrawingArea.bottomY;
        if (i < DrawingArea.topX) {
            int k2 = DrawingArea.topX - i;
            k1 -= k2;
            i = DrawingArea.topX;
            i1 += k2;
            l += k2;
            i2 += k2;
            l1 += k2;
        }
        if (i + k1 > DrawingArea.bottomX) {
            int l2 = (i + k1) - DrawingArea.bottomX;
            k1 -= l2;
            i2 += l2;
            l1 += l2;
        }
        if (!(k1 <= 0 || j1 <= 0)) {
            method362(j1, DrawingArea.pixels, myPixels, l1, l, k1, i1, palette, i2);
        }
    }

    private void method362(int i, int ai[], int abyte0[], int j, int k, int l,
                           int i1, int ai1[], int j1) {
        int k1 = -(l >> 2);
        l = -(l & 3);
        for (int l1 = -i; l1 < 0; l1++) {
            for (int i2 = k1; i2 < 0; i2++) {
                int byte1 = abyte0[i1++];
                if (byte1 != 0)
                    ai[k++] = ai1[byte1];
                else
                    k++;
                byte1 = abyte0[i1++];
                if (byte1 != 0)
                    ai[k++] = ai1[byte1];
                else
                    k++;
                byte1 = abyte0[i1++];
                if (byte1 != 0)
                    ai[k++] = ai1[byte1];
                else
                    k++;
                byte1 = abyte0[i1++];
                if (byte1 != 0)
                    ai[k++] = ai1[byte1];
                else
                    k++;
            }

            for (int j2 = l; j2 < 0; j2++) {
                int byte2 = abyte0[i1++];
                if (byte2 != 0)
                    ai[k++] = ai1[byte2];
                else
                    k++;
            }

            k += j;
            i1 += j1;
        }

    }
}

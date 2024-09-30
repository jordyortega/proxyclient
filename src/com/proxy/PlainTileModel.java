package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


final class PlainTileModel {

    final int colour1;
    final int colour2;
    final int colour3;
    final int colour4;
    final int textureId;
    final int minimapColour;
    boolean flat;

    public PlainTileModel(int i, int j, int k, int l, int i1, int j1, boolean flat) {
        this.flat = true;
        colour1 = i;
        colour2 = j;
        colour3 = k;
        colour4 = l;
        textureId = i1;
        minimapColour = j1;
        this.flat = flat;
    }
}

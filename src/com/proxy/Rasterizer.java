package com.proxy;

class Rasterizer extends DrawingArea {

    public static final TextureCache textureCache = new TextureCache();
    public static int textureAmount = 671;
    public static boolean lowMem = true;
    public static boolean clippedHorizontally;
    public static boolean transparentTexture;
    public static boolean smooth_edges = true;
    public static int blending_alpha;
    public static int originX;
    public static int originY;
    public static int[] anIntArray839;
    public static int[] anIntArray841;
    public static int SINE[];
    public static int COSINE[];
    public static int rowOffsets[];
    public static int loadedTextureCount;
    public static Background textures[] = new Background[textureAmount];
    public static boolean[] transparent = new boolean[textureAmount];
    public static int[] anIntArray1476 = new int[textureAmount];
    public static int texelPoolPointer;
    public static int[][] texelArrayPool;
    public static int[][] texelCache = new int[textureAmount][];
    public static int textureLastUsed[] = new int[textureAmount];
    public static int textureGetCount;
    public static int HSLtoRGB[] = new int[0x10000];
    public static int[][] texturePalettes = new int[textureAmount][];
    private static boolean small_texture;
    private static boolean alpha_opaque;

    static {
        anIntArray839 = new int[512];
        anIntArray841 = new int[2048];
        SINE = new int[2048];
        COSINE = new int[2048];
        for (int i = 1; i < 512; i++)
            anIntArray839[i] = 32768 / i;

        for (int j = 1; j < 2048; j++)
            anIntArray841[j] = 0x10000 / j;

        for (int k = 0; k < 2048; k++) {
            SINE[k] = (int) (65536D * Math
                    .sin((double) k * 0.0030679614999999999D));
            COSINE[k] = (int) (65536D * Math
                    .cos((double) k * 0.0030679614999999999D));
        }

    }

    public static void nullLoader() {
        anIntArray839 = null;
        anIntArray839 = null;
        SINE = null;
        COSINE = null;
        rowOffsets = null;
        textures = null;
        transparent = null;
        anIntArray1476 = null;
        texelArrayPool = null;
        texelCache = null;
        textureLastUsed = null;
        HSLtoRGB = null;
        texturePalettes = null;
    }

    public static void method364() {
        rowOffsets = new int[DrawingArea.height];
        for (int j = 0; j < DrawingArea.height; j++)
            rowOffsets[j] = DrawingArea.width * j;

        originX = DrawingArea.width / 2;
        originY = DrawingArea.height / 2;
    }

    public static void method365(int j, int k) {
        rowOffsets = new int[k];
        for (int l = 0; l < k; l++)
            rowOffsets[l] = j * l;

        originX = j / 2;
        originY = k / 2;
    }

    public static void method366() {
        texelArrayPool = null;
        for (int j = 0; j < textureAmount; j++)
            texelCache[j] = null;

    }

    public static void method367() {
        if (texelArrayPool == null) {
            texelPoolPointer = 20;// was parameter
            if (lowMem)
                texelArrayPool = new int[texelPoolPointer][16384];
            else
                texelArrayPool = new int[texelPoolPointer][0x10000];
            for (int k = 0; k < textureAmount; k++)
                texelCache[k] = null;

        }
    }

    public static void unpack(StreamLoader streamLoader) {
        loadedTextureCount = 0;
        for (int j = 0; j < textureAmount; j++)
            try {
                textures[j] = new Background("textures/texture_" + j + ".png");
                if (lowMem && textures[j].myMaxWidth == 128)
                    textures[j].method356();
                else
                    textures[j].method357();
                loadedTextureCount++;
            } catch (Exception _ex) {
            }

    }

    public static void method368(StreamLoader streamLoader) {
        loadedTextureCount = 0;
        for (int j = 0; j < textureAmount; j++)
            try {
                textures[j] = new Background("textures/texture_" + j + ".png");
                if (lowMem && textures[j].myMaxWidth == 128)
                    textures[j].method356();
                else
                    textures[j].method357();
                loadedTextureCount++;
            } catch (Exception _ex) {
            }

    }

    public static int method369(int i) {
        if (anIntArray1476[i] != 0)
            return anIntArray1476[i];
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = texturePalettes[i].length;
        for (int k1 = 0; k1 < j1; k1++) {
            k += texturePalettes[i][k1] >> 16 & 0xff;
            l += texturePalettes[i][k1] >> 8 & 0xff;
            i1 += texturePalettes[i][k1] & 0xff;
        }

        int l1 = (k / j1 << 16) + (l / j1 << 8) + i1 / j1;
        l1 = method373(l1, 1.3999999999999999D);
        if (l1 == 0)
            l1 = 1;
        anIntArray1476[i] = l1;
        return l1;
    }

    public static void method370(int i) {
        if (texelCache[i] == null)
            return;
        texelArrayPool[texelPoolPointer++] = texelCache[i];
        texelCache[i] = null;
    }

    public static int[] method371(int i) {
        textureLastUsed[i] = textureGetCount++;
        if (texelCache[i] != null)
            return texelCache[i];
        int ai[];
        if (texelPoolPointer > 0) {
            ai = texelArrayPool[--texelPoolPointer];
            texelArrayPool[texelPoolPointer] = null;
        } else {
            int j = 0;
            int k = -1;
            for (int l = 0; l < loadedTextureCount; l++)
                if (texelCache[l] != null
                        && (textureLastUsed[l] < j || k == -1)) {
                    j = textureLastUsed[l];
                    k = l;
                }

            ai = texelCache[k];
            texelCache[k] = null;
        }
        texelCache[i] = ai;
        Background background = textures[i];
        int ai1[] = texturePalettes[i];
        if (lowMem) {
            transparent[i] = false;
            for (int i1 = 0; i1 < 4096; i1++) {
                int i2 = ai[i1] = ai1[background.myPixels[i1]] & 0xf8f8ff;
                if (i2 == 0)
                    transparent[i] = true;
                ai[4096 + i1] = i2 - (i2 >>> 3) & 0xf8f8ff;
                ai[8192 + i1] = i2 - (i2 >>> 2) & 0xf8f8ff;
                ai[12288 + i1] = i2 - (i2 >>> 2) - (i2 >>> 3) & 0xf8f8ff;
            }

        } else {
            if (background.myWidth == 64) {
                for (int j1 = 0; j1 < 128; j1++) {
                    for (int j2 = 0; j2 < 128; j2++)
                        ai[j2 + (j1 << 7)] = ai1[background.myPixels[(j2 >> 1)
                                + ((j1 >> 1) << 6)]];

                }

            } else {
                for (int k1 = 0; k1 < 16384; k1++)
                    ai[k1] = ai1[background.myPixels[k1]];

            }
            transparent[i] = false;
            for (int l1 = 0; l1 < 16384; l1++) {
                ai[l1] &= 0xf8f8ff;
                int k2 = ai[l1];
                if (k2 == 0)
                    transparent[i] = true;
                ai[16384 + l1] = k2 - (k2 >>> 3) & 0xf8f8ff;
                ai[32768 + l1] = k2 - (k2 >>> 2) & 0xf8f8ff;
                ai[49152 + l1] = k2 - (k2 >>> 2) - (k2 >>> 3) & 0xf8f8ff;
            }

        }
        return ai;
    }

    public static void method372(double d) {
        d += Math.random() * 0.029999999999999999D - 0.014999999999999999D;
        int j = 0;
        for (int k = 0; k < 512; k++) {
            double d1 = (double) (k / 8) / 64D + 0.0078125D;
            double d2 = (double) (k & 7) / 8D + 0.0625D;
            for (int k1 = 0; k1 < 128; k1++) {
                double d3 = (double) k1 / 128D;
                double d4 = d3;
                double d5 = d3;
                double d6 = d3;
                if (d2 != 0.0D) {
                    double d7;
                    if (d3 < 0.5D)
                        d7 = d3 * (1.0D + d2);
                    else
                        d7 = (d3 + d2) - d3 * d2;
                    double d8 = 2D * d3 - d7;
                    double d9 = d1 + 0.33333333333333331D;
                    if (d9 > 1.0D)
                        d9--;
                    double d10 = d1;
                    double d11 = d1 - 0.33333333333333331D;
                    if (d11 < 0.0D)
                        d11++;
                    if (6D * d9 < 1.0D)
                        d4 = d8 + (d7 - d8) * 6D * d9;
                    else if (2D * d9 < 1.0D)
                        d4 = d7;
                    else if (3D * d9 < 2D)
                        d4 = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
                    else
                        d4 = d8;
                    if (6D * d10 < 1.0D)
                        d5 = d8 + (d7 - d8) * 6D * d10;
                    else if (2D * d10 < 1.0D)
                        d5 = d7;
                    else if (3D * d10 < 2D)
                        d5 = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
                    else
                        d5 = d8;
                    if (6D * d11 < 1.0D)
                        d6 = d8 + (d7 - d8) * 6D * d11;
                    else if (2D * d11 < 1.0D)
                        d6 = d7;
                    else if (3D * d11 < 2D)
                        d6 = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
                    else
                        d6 = d8;
                }
                int l1 = (int) (d4 * 256D);
                int i2 = (int) (d5 * 256D);
                int j2 = (int) (d6 * 256D);
                int k2 = (l1 << 16) + (i2 << 8) + j2;
                k2 = method373(k2, d);
                if (k2 == 0)
                    k2 = 1;
                HSLtoRGB[j++] = k2;
            }

        }

        for (int l = 0; l < textureAmount; l++)
            if (textures[l] != null) {
                int ai[] = textures[l].palette;
                texturePalettes[l] = new int[ai.length];
                for (int j1 = 0; j1 < ai.length; j1++) {
                    texturePalettes[l][j1] = method373(ai[j1], d);
                    if ((texturePalettes[l][j1] & 0xf8f8ff) == 0 && j1 != 0)
                        texturePalettes[l][j1] = 1;
                }

            }

        for (int i1 = 0; i1 < textureAmount; i1++)
            method370(i1);

    }

    public static int method373(int i, double d) {
        double d1 = (double) (i >> 16) / 256D;
        double d2 = (double) (i >> 8 & 0xff) / 256D;
        double d3 = (double) (i & 0xff) / 256D;
        d1 = Math.pow(d1, d);
        d2 = Math.pow(d2, d);
        d3 = Math.pow(d3, d);
        int j = (int) (d1 * 256D);
        int k = (int) (d2 * 256D);
        int l = (int) (d3 * 256D);
        return (j << 16) + (k << 8) + l;
    }

    static void method1154(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9 = var4 - var3;
        int var10 = var1 - var0;
        int var11 = var5 - var3;
        int var12 = var2 - var0;
        int var13 = var7 - var6;
        int var14 = var8 - var6;
        int var15;
        if (var2 != var1) {
            var15 = (var5 - var4 << 16) / (var2 - var1);
        } else {
            var15 = 0;
        }

        int var16;
        if (var1 != var0) {
            var16 = (var9 << 16) / var10;
        } else {
            var16 = 0;
        }

        int var17;
        if (var2 != var0) {
            var17 = (var11 << 16) / var12;
        } else {
            var17 = 0;
        }

        int var18 = var9 * var12 - var11 * var10;
        if (var18 != 0) {
            int var19 = (var13 * var12 - var14 * var10 << 8) / var18;
            int var20 = (var14 * var9 - var13 * var11 << 8) / var18;
            if (var0 <= var1 && var0 <= var2) {
                if (var0 < DrawingArea.bottomY) {
                    if (var1 > DrawingArea.bottomY) {
                        var1 = DrawingArea.bottomY;
                    }

                    if (var2 > DrawingArea.bottomY) {
                        var2 = DrawingArea.bottomY;
                    }

                    var6 = (var6 << 8) - var19 * var3 + var19;
                    if (var1 < var2) {
                        var5 = var3 <<= 16;
                        if (var0 < 0) {
                            var5 -= var17 * var0;
                            var3 -= var16 * var0;
                            var6 -= var20 * var0;
                            var0 = 0;
                        }

                        var4 <<= 16;
                        if (var1 < 0) {
                            var4 -= var15 * var1;
                            var1 = 0;
                        }

                        if ((var0 == var1 || var17 >= var16) && (var0 != var1 || var17 <= var15)) {
                            var2 -= var1;
                            var1 -= var0;
                            var0 = rowOffsets[var0];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var2;
                                        if (var2 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var0, 0, 0, var4 >> 16, var5 >> 16, var6, var19);
                                        var5 += var17;
                                        var4 += var15;
                                        var6 += var20;
                                        var0 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var0, 0, 0, var3 >> 16, var5 >> 16, var6, var19);
                                var5 += var17;
                                var3 += var16;
                                var6 += var20;
                                var0 += DrawingArea.width;
                            }
                        } else {
                            var2 -= var1;
                            var1 -= var0;
                            var0 = rowOffsets[var0];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var2;
                                        if (var2 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var0, 0, 0, var5 >> 16, var4 >> 16, var6, var19);
                                        var5 += var17;
                                        var4 += var15;
                                        var6 += var20;
                                        var0 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var0, 0, 0, var5 >> 16, var3 >> 16, var6, var19);
                                var5 += var17;
                                var3 += var16;
                                var6 += var20;
                                var0 += DrawingArea.width;
                            }
                        }
                    } else {
                        var4 = var3 <<= 16;
                        if (var0 < 0) {
                            var4 -= var17 * var0;
                            var3 -= var16 * var0;
                            var6 -= var20 * var0;
                            var0 = 0;
                        }

                        var5 <<= 16;
                        if (var2 < 0) {
                            var5 -= var15 * var2;
                            var2 = 0;
                        }

                        if ((var0 == var2 || var17 >= var16) && (var0 != var2 || var15 <= var16)) {
                            var1 -= var2;
                            var2 -= var0;
                            var0 = rowOffsets[var0];

                            while (true) {
                                --var2;
                                if (var2 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var0, 0, 0, var3 >> 16, var5 >> 16, var6, var19);
                                        var5 += var15;
                                        var3 += var16;
                                        var6 += var20;
                                        var0 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var0, 0, 0, var3 >> 16, var4 >> 16, var6, var19);
                                var4 += var17;
                                var3 += var16;
                                var6 += var20;
                                var0 += DrawingArea.width;
                            }
                        } else {
                            var1 -= var2;
                            var2 -= var0;
                            var0 = rowOffsets[var0];

                            while (true) {
                                --var2;
                                if (var2 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var0, 0, 0, var5 >> 16, var3 >> 16, var6, var19);
                                        var5 += var15;
                                        var3 += var16;
                                        var6 += var20;
                                        var0 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var0, 0, 0, var4 >> 16, var3 >> 16, var6, var19);
                                var4 += var17;
                                var3 += var16;
                                var6 += var20;
                                var0 += DrawingArea.width;
                            }
                        }
                    }
                }
            } else if (var1 <= var2) {
                if (var1 < DrawingArea.bottomY) {
                    if (var2 > DrawingArea.bottomY) {
                        var2 = DrawingArea.bottomY;
                    }

                    if (var0 > DrawingArea.bottomY) {
                        var0 = DrawingArea.bottomY;
                    }

                    var7 = (var7 << 8) - var19 * var4 + var19;
                    if (var2 < var0) {
                        var3 = var4 <<= 16;
                        if (var1 < 0) {
                            var3 -= var16 * var1;
                            var4 -= var15 * var1;
                            var7 -= var20 * var1;
                            var1 = 0;
                        }

                        var5 <<= 16;
                        if (var2 < 0) {
                            var5 -= var17 * var2;
                            var2 = 0;
                        }

                        if ((var1 == var2 || var16 >= var15) && (var1 != var2 || var16 <= var17)) {
                            var0 -= var2;
                            var2 -= var1;
                            var1 = rowOffsets[var1];

                            while (true) {
                                --var2;
                                if (var2 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var1, 0, 0, var5 >> 16, var3 >> 16, var7, var19);
                                        var3 += var16;
                                        var5 += var17;
                                        var7 += var20;
                                        var1 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var1, 0, 0, var4 >> 16, var3 >> 16, var7, var19);
                                var3 += var16;
                                var4 += var15;
                                var7 += var20;
                                var1 += DrawingArea.width;
                            }
                        } else {
                            var0 -= var2;
                            var2 -= var1;
                            var1 = rowOffsets[var1];

                            while (true) {
                                --var2;
                                if (var2 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var1, 0, 0, var3 >> 16, var5 >> 16, var7, var19);
                                        var3 += var16;
                                        var5 += var17;
                                        var7 += var20;
                                        var1 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var1, 0, 0, var3 >> 16, var4 >> 16, var7, var19);
                                var3 += var16;
                                var4 += var15;
                                var7 += var20;
                                var1 += DrawingArea.width;
                            }
                        }
                    } else {
                        var5 = var4 <<= 16;
                        if (var1 < 0) {
                            var5 -= var16 * var1;
                            var4 -= var15 * var1;
                            var7 -= var20 * var1;
                            var1 = 0;
                        }

                        var3 <<= 16;
                        if (var0 < 0) {
                            var3 -= var17 * var0;
                            var0 = 0;
                        }

                        if (var16 < var15) {
                            var2 -= var0;
                            var0 -= var1;
                            var1 = rowOffsets[var1];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var2;
                                        if (var2 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var1, 0, 0, var3 >> 16, var4 >> 16, var7, var19);
                                        var3 += var17;
                                        var4 += var15;
                                        var7 += var20;
                                        var1 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var1, 0, 0, var5 >> 16, var4 >> 16, var7, var19);
                                var5 += var16;
                                var4 += var15;
                                var7 += var20;
                                var1 += DrawingArea.width;
                            }
                        } else {
                            var2 -= var0;
                            var0 -= var1;
                            var1 = rowOffsets[var1];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var2;
                                        if (var2 < 0) {
                                            return;
                                        }

                                        method1146(DrawingArea.pixels, var1, 0, 0, var4 >> 16, var3 >> 16, var7, var19);
                                        var3 += var17;
                                        var4 += var15;
                                        var7 += var20;
                                        var1 += DrawingArea.width;
                                    }
                                }

                                method1146(DrawingArea.pixels, var1, 0, 0, var4 >> 16, var5 >> 16, var7, var19);
                                var5 += var16;
                                var4 += var15;
                                var7 += var20;
                                var1 += DrawingArea.width;
                            }
                        }
                    }
                }
            } else if (var2 < DrawingArea.bottomY) {
                if (var0 > DrawingArea.bottomY) {
                    var0 = DrawingArea.bottomY;
                }

                if (var1 > DrawingArea.bottomY) {
                    var1 = DrawingArea.bottomY;
                }

                var8 = (var8 << 8) - var19 * var5 + var19;
                if (var0 < var1) {
                    var4 = var5 <<= 16;
                    if (var2 < 0) {
                        var4 -= var15 * var2;
                        var5 -= var17 * var2;
                        var8 -= var20 * var2;
                        var2 = 0;
                    }

                    var3 <<= 16;
                    if (var0 < 0) {
                        var3 -= var16 * var0;
                        var0 = 0;
                    }

                    if (var15 < var17) {
                        var1 -= var0;
                        var0 -= var2;
                        var2 = rowOffsets[var2];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method1146(DrawingArea.pixels, var2, 0, 0, var4 >> 16, var3 >> 16, var8, var19);
                                    var4 += var15;
                                    var3 += var16;
                                    var8 += var20;
                                    var2 += DrawingArea.width;
                                }
                            }

                            method1146(DrawingArea.pixels, var2, 0, 0, var4 >> 16, var5 >> 16, var8, var19);
                            var4 += var15;
                            var5 += var17;
                            var8 += var20;
                            var2 += DrawingArea.width;
                        }
                    } else {
                        var1 -= var0;
                        var0 -= var2;
                        var2 = rowOffsets[var2];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method1146(DrawingArea.pixels, var2, 0, 0, var3 >> 16, var4 >> 16, var8, var19);
                                    var4 += var15;
                                    var3 += var16;
                                    var8 += var20;
                                    var2 += DrawingArea.width;
                                }
                            }

                            method1146(DrawingArea.pixels, var2, 0, 0, var5 >> 16, var4 >> 16, var8, var19);
                            var4 += var15;
                            var5 += var17;
                            var8 += var20;
                            var2 += DrawingArea.width;
                        }
                    }
                } else {
                    var3 = var5 <<= 16;
                    if (var2 < 0) {
                        var3 -= var15 * var2;
                        var5 -= var17 * var2;
                        var8 -= var20 * var2;
                        var2 = 0;
                    }

                    var4 <<= 16;
                    if (var1 < 0) {
                        var4 -= var16 * var1;
                        var1 = 0;
                    }

                    if (var15 < var17) {
                        var0 -= var1;
                        var1 -= var2;
                        var2 = rowOffsets[var2];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method1146(DrawingArea.pixels, var2, 0, 0, var4 >> 16, var5 >> 16, var8, var19);
                                    var4 += var16;
                                    var5 += var17;
                                    var8 += var20;
                                    var2 += DrawingArea.width;
                                }
                            }

                            method1146(DrawingArea.pixels, var2, 0, 0, var3 >> 16, var5 >> 16, var8, var19);
                            var3 += var15;
                            var5 += var17;
                            var8 += var20;
                            var2 += DrawingArea.width;
                        }
                    } else {
                        var0 -= var1;
                        var1 -= var2;
                        var2 = rowOffsets[var2];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method1146(DrawingArea.pixels, var2, 0, 0, var5 >> 16, var4 >> 16, var8, var19);
                                    var4 += var16;
                                    var5 += var17;
                                    var8 += var20;
                                    var2 += DrawingArea.width;
                                }
                            }

                            method1146(DrawingArea.pixels, var2, 0, 0, var5 >> 16, var3 >> 16, var8, var19);
                            var3 += var15;
                            var5 += var17;
                            var8 += var20;
                            var2 += DrawingArea.width;
                        }
                    }
                }
            }
        }
    }

    private static void method1146(int[] var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (clippedHorizontally) {
            if (var5 > DrawingArea.centerX) {
                var5 = DrawingArea.centerX;
            }

            if (var4 < 0) {
                var4 = 0;
            }
        }

        if (var4 < var5) {
            var1 += var4;
            var6 += var7 * var4;
            int var8;
            int var9;
            int var10;
            if (smooth_edges) {
                var3 = var5 - var4 >> 2;
                var7 <<= 2;
                if (blending_alpha == 0) {
                    if (var3 > 0) {
                        do {
                            var2 = HSLtoRGB[var6 >> 8];
                            var6 += var7;
                            var0[var1++] = var2;
                            var0[var1++] = var2;
                            var0[var1++] = var2;
                            var0[var1++] = var2;
                            --var3;
                        } while (var3 > 0);
                    }

                    var3 = var5 - var4 & 3;
                    if (var3 > 0) {
                        var2 = HSLtoRGB[var6 >> 8];

                        do {
                            var0[var1++] = var2;
                            --var3;
                        } while (var3 > 0);
                    }
                } else {
                    var8 = blending_alpha;
                    var9 = 256 - blending_alpha;
                    if (var3 > 0) {
                        do {
                            var2 = HSLtoRGB[var6 >> 8];
                            var6 += var7;
                            var2 = ((var2 & 16711935) * var9 >> 8 & 16711935) + ((var2 & '\uff00') * var9 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            --var3;
                        } while (var3 > 0);
                    }

                    var3 = var5 - var4 & 3;
                    if (var3 > 0) {
                        var2 = HSLtoRGB[var6 >> 8];
                        var2 = ((var2 & 16711935) * var9 >> 8 & 16711935) + ((var2 & '\uff00') * var9 >> 8 & '\uff00');

                        do {
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            --var3;
                        } while (var3 > 0);
                    }
                }

            } else {
                var3 = var5 - var4;
                if (blending_alpha == 0) {
                    do {
                        var0[var1++] = HSLtoRGB[var6 >> 8];
                        var6 += var7;
                        --var3;
                    } while (var3 > 0);
                } else {
                    var8 = blending_alpha;
                    var9 = 256 - blending_alpha;

                    do {
                        var2 = HSLtoRGB[var6 >> 8];
                        var6 += var7;
                        var2 = ((var2 & 16711935) * var9 >> 8 & 16711935) + ((var2 & '\uff00') * var9 >> 8 & '\uff00');
                        var10 = var0[var1];
                        var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                        --var3;
                    } while (var3 > 0);
                }

            }
        }
    }

    static void method1144(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = 0;
        if (var1 != var0) {
            var7 = (var4 - var3 << 16) / (var1 - var0);
        }

        int var8 = 0;
        if (var2 != var1) {
            var8 = (var5 - var4 << 16) / (var2 - var1);
        }

        int var9 = 0;
        if (var2 != var0) {
            var9 = (var3 - var5 << 16) / (var0 - var2);
        }

        if (var0 <= var1 && var0 <= var2) {
            if (var0 < DrawingArea.bottomY) {
                if (var1 > DrawingArea.bottomY) {
                    var1 = DrawingArea.bottomY;
                }

                if (var2 > DrawingArea.bottomY) {
                    var2 = DrawingArea.bottomY;
                }

                if (var1 < var2) {
                    var5 = var3 <<= 16;
                    if (var0 < 0) {
                        var5 -= var9 * var0;
                        var3 -= var7 * var0;
                        var0 = 0;
                    }

                    var4 <<= 16;
                    if (var1 < 0) {
                        var4 -= var8 * var1;
                        var1 = 0;
                    }

                    if ((var0 == var1 || var9 >= var7) && (var0 != var1 || var9 <= var8)) {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = rowOffsets[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var0, var6, 0, var4 >> 16, var5 >> 16);
                                    var5 += var9;
                                    var4 += var8;
                                    var0 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var0, var6, 0, var3 >> 16, var5 >> 16);
                            var5 += var9;
                            var3 += var7;
                            var0 += DrawingArea.width;
                        }
                    } else {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = rowOffsets[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var0, var6, 0, var5 >> 16, var4 >> 16);
                                    var5 += var9;
                                    var4 += var8;
                                    var0 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var0, var6, 0, var5 >> 16, var3 >> 16);
                            var5 += var9;
                            var3 += var7;
                            var0 += DrawingArea.width;
                        }
                    }
                } else {
                    var4 = var3 <<= 16;
                    if (var0 < 0) {
                        var4 -= var9 * var0;
                        var3 -= var7 * var0;
                        var0 = 0;
                    }

                    var5 <<= 16;
                    if (var2 < 0) {
                        var5 -= var8 * var2;
                        var2 = 0;
                    }

                    if ((var0 == var2 || var9 >= var7) && (var0 != var2 || var8 <= var7)) {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = rowOffsets[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var0, var6, 0, var3 >> 16, var5 >> 16);
                                    var5 += var8;
                                    var3 += var7;
                                    var0 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var0, var6, 0, var3 >> 16, var4 >> 16);
                            var4 += var9;
                            var3 += var7;
                            var0 += DrawingArea.width;
                        }
                    } else {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = rowOffsets[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var0, var6, 0, var5 >> 16, var3 >> 16);
                                    var5 += var8;
                                    var3 += var7;
                                    var0 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var0, var6, 0, var4 >> 16, var3 >> 16);
                            var4 += var9;
                            var3 += var7;
                            var0 += DrawingArea.width;
                        }
                    }
                }
            }
        } else if (var1 <= var2) {
            if (var1 < DrawingArea.bottomY) {
                if (var2 > DrawingArea.bottomY) {
                    var2 = DrawingArea.bottomY;
                }

                if (var0 > DrawingArea.bottomY) {
                    var0 = DrawingArea.bottomY;
                }

                if (var2 < var0) {
                    var3 = var4 <<= 16;
                    if (var1 < 0) {
                        var3 -= var7 * var1;
                        var4 -= var8 * var1;
                        var1 = 0;
                    }

                    var5 <<= 16;
                    if (var2 < 0) {
                        var5 -= var9 * var2;
                        var2 = 0;
                    }

                    if ((var1 == var2 || var7 >= var8) && (var1 != var2 || var7 <= var9)) {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = rowOffsets[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var1, var6, 0, var5 >> 16, var3 >> 16);
                                    var3 += var7;
                                    var5 += var9;
                                    var1 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var1, var6, 0, var4 >> 16, var3 >> 16);
                            var3 += var7;
                            var4 += var8;
                            var1 += DrawingArea.width;
                        }
                    } else {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = rowOffsets[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var1, var6, 0, var3 >> 16, var5 >> 16);
                                    var3 += var7;
                                    var5 += var9;
                                    var1 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var1, var6, 0, var3 >> 16, var4 >> 16);
                            var3 += var7;
                            var4 += var8;
                            var1 += DrawingArea.width;
                        }
                    }
                } else {
                    var5 = var4 <<= 16;
                    if (var1 < 0) {
                        var5 -= var7 * var1;
                        var4 -= var8 * var1;
                        var1 = 0;
                    }

                    var3 <<= 16;
                    if (var0 < 0) {
                        var3 -= var9 * var0;
                        var0 = 0;
                    }

                    if (var7 < var8) {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = rowOffsets[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var1, var6, 0, var3 >> 16, var4 >> 16);
                                    var3 += var9;
                                    var4 += var8;
                                    var1 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var1, var6, 0, var5 >> 16, var4 >> 16);
                            var5 += var7;
                            var4 += var8;
                            var1 += DrawingArea.width;
                        }
                    } else {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = rowOffsets[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method1149(DrawingArea.pixels, var1, var6, 0, var4 >> 16, var3 >> 16);
                                    var3 += var9;
                                    var4 += var8;
                                    var1 += DrawingArea.width;
                                }
                            }

                            method1149(DrawingArea.pixels, var1, var6, 0, var4 >> 16, var5 >> 16);
                            var5 += var7;
                            var4 += var8;
                            var1 += DrawingArea.width;
                        }
                    }
                }
            }
        } else if (var2 < DrawingArea.bottomY) {
            if (var0 > DrawingArea.bottomY) {
                var0 = DrawingArea.bottomY;
            }

            if (var1 > DrawingArea.bottomY) {
                var1 = DrawingArea.bottomY;
            }

            if (var0 < var1) {
                var4 = var5 <<= 16;
                if (var2 < 0) {
                    var4 -= var8 * var2;
                    var5 -= var9 * var2;
                    var2 = 0;
                }

                var3 <<= 16;
                if (var0 < 0) {
                    var3 -= var7 * var0;
                    var0 = 0;
                }

                if (var8 < var9) {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = rowOffsets[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method1149(DrawingArea.pixels, var2, var6, 0, var4 >> 16, var3 >> 16);
                                var4 += var8;
                                var3 += var7;
                                var2 += DrawingArea.width;
                            }
                        }

                        method1149(DrawingArea.pixels, var2, var6, 0, var4 >> 16, var5 >> 16);
                        var4 += var8;
                        var5 += var9;
                        var2 += DrawingArea.width;
                    }
                } else {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = rowOffsets[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method1149(DrawingArea.pixels, var2, var6, 0, var3 >> 16, var4 >> 16);
                                var4 += var8;
                                var3 += var7;
                                var2 += DrawingArea.width;
                            }
                        }

                        method1149(DrawingArea.pixels, var2, var6, 0, var5 >> 16, var4 >> 16);
                        var4 += var8;
                        var5 += var9;
                        var2 += DrawingArea.width;
                    }
                }
            } else {
                var3 = var5 <<= 16;
                if (var2 < 0) {
                    var3 -= var8 * var2;
                    var5 -= var9 * var2;
                    var2 = 0;
                }

                var4 <<= 16;
                if (var1 < 0) {
                    var4 -= var7 * var1;
                    var1 = 0;
                }

                if (var8 < var9) {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = rowOffsets[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method1149(DrawingArea.pixels, var2, var6, 0, var4 >> 16, var5 >> 16);
                                var4 += var7;
                                var5 += var9;
                                var2 += DrawingArea.width;
                            }
                        }

                        method1149(DrawingArea.pixels, var2, var6, 0, var3 >> 16, var5 >> 16);
                        var3 += var8;
                        var5 += var9;
                        var2 += DrawingArea.width;
                    }
                } else {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = rowOffsets[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method1149(DrawingArea.pixels, var2, var6, 0, var5 >> 16, var4 >> 16);
                                var4 += var7;
                                var5 += var9;
                                var2 += DrawingArea.width;
                            }
                        }

                        method1149(DrawingArea.pixels, var2, var6, 0, var5 >> 16, var3 >> 16);
                        var3 += var8;
                        var5 += var9;
                        var2 += DrawingArea.width;
                    }
                }
            }
        }
    }

    private static void method1149(int[] var0, int var1, int var2, int var3, int var4, int var5) {
        if (clippedHorizontally) {
            if (var5 > DrawingArea.centerX) {
                var5 = DrawingArea.centerX;
            }

            if (var4 < 0) {
                var4 = 0;
            }
        }

        if (var4 < var5) {
            var1 += var4;
            var3 = var5 - var4 >> 2;
            if (blending_alpha == 0) {
                while (true) {
                    --var3;
                    if (var3 < 0) {
                        var3 = var5 - var4 & 3;

                        while (true) {
                            --var3;
                            if (var3 < 0) {
                                return;
                            }

                            var0[var1++] = var2;
                        }
                    }

                    var0[var1++] = var2;
                    var0[var1++] = var2;
                    var0[var1++] = var2;
                    var0[var1++] = var2;
                }
            } else if (blending_alpha == 254) {
                while (true) {
                    --var3;
                    if (var3 < 0) {
                        var3 = var5 - var4 & 3;

                        while (true) {
                            --var3;
                            if (var3 < 0) {
                                return;
                            }

                            var0[var1++] = var0[var1];
                        }
                    }

                    var0[var1++] = var0[var1];
                    var0[var1++] = var0[var1];
                    var0[var1++] = var0[var1];
                    var0[var1++] = var0[var1];
                }
            } else {
                int var6 = blending_alpha;
                int var7 = 256 - blending_alpha;
                var2 = ((var2 & 16711935) * var7 >> 8 & 16711935) + ((var2 & '\uff00') * var7 >> 8 & '\uff00');

                while (true) {
                    --var3;
                    int var8;
                    if (var3 < 0) {
                        var3 = var5 - var4 & 3;

                        while (true) {
                            --var3;
                            if (var3 < 0) {
                                return;
                            }

                            var8 = var0[var1];
                            var0[var1++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                        }
                    }

                    var8 = var0[var1];
                    var0[var1++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                    var8 = var0[var1];
                    var0[var1++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                    var8 = var0[var1];
                    var0[var1++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                    var8 = var0[var1];
                    var0[var1++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                }
            }
        }
    }

    static final void method1138(int var0, int var1, int var2,
                                 int var3, int var4, int var5,
                                 int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
        int[] var19 = textureCache.getPixels(var18);
        int var20;
        if (var19 != null && blending_alpha <= 10) {
            small_texture = textureCache.is_large(var18);
            alpha_opaque = textureCache.is_alpha_opaque(var18);
            var20 = var4 - var3;
            int var21 = var1 - var0;
            int var22 = var5 - var3;
            int var23 = var2 - var0;
            int var24 = var7 - var6;
            int var25 = var8 - var6;
            int var26 = 0;
            if (var1 != var0) {
                var26 = (var4 - var3 << 16) / (var1 - var0);
            }

            int var27 = 0;
            if (var2 != var1) {
                var27 = (var5 - var4 << 16) / (var2 - var1);
            }

            int var28 = 0;
            if (var2 != var0) {
                var28 = (var3 - var5 << 16) / (var0 - var2);
            }

            int var29 = var20 * var23 - var22 * var21;
            if (var29 != 0) {
                int var30 = (var24 * var23 - var25 * var21 << 9) / var29;
                int var31 = (var25 * var20 - var24 * var22 << 9) / var29;
                var10 = var9 - var10;
                var13 = var12 - var13;
                var16 = var15 - var16;
                var11 -= var9;
                var14 -= var12;
                var17 -= var15;
                int var32 = var11 * var12 - var14 * var9 << (Client.log_view_dist == 9 ? 14 : 15);
                int var33 = var14 * var15 - var17 * var12 << 8;
                int var34 = var17 * var9 - var11 * var15 << 5;
                int var35 = var10 * var12 - var13 * var9 << (Client.log_view_dist == 9 ? 14 : 15);
                int var36 = var13 * var15 - var16 * var12 << 8;
                int var37 = var16 * var9 - var10 * var15 << 5;
                int var38 = var13 * var11 - var10 * var14 << (Client.log_view_dist == 9 ? 14 : 15);
                int var39 = var16 * var14 - var13 * var17 << 8;
                int var40 = var10 * var17 - var16 * var11 << 5;
                int var41;
                if (var0 <= var1 && var0 <= var2) {
                    if (var0 < DrawingArea.bottomY) {
                        if (var1 > DrawingArea.bottomY) {
                            var1 = DrawingArea.bottomY;
                        }

                        if (var2 > DrawingArea.bottomY) {
                            var2 = DrawingArea.bottomY;
                        }

                        var6 = (var6 << 9) - var30 * var3 + var30;
                        if (var1 < var2) {
                            var5 = var3 <<= 16;
                            if (var0 < 0) {
                                var5 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var4 <<= 16;
                            if (var1 < 0) {
                                var4 -= var27 * var1;
                                var1 = 0;
                            }

                            var41 = var0 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var1 || var28 >= var26) && (var0 != var1 || var28 <= var27)) {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var0, var4 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var0, var5 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var4 = var3 <<= 16;
                            if (var0 < 0) {
                                var4 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var27 * var2;
                                var2 = 0;
                            }

                            var41 = var0 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var2 || var28 >= var26) && (var0 != var2 || var27 <= var26)) {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var0, var3 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var0, var4 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var1 <= var2) {
                    if (var1 < DrawingArea.bottomY) {
                        if (var2 > DrawingArea.bottomY) {
                            var2 = DrawingArea.bottomY;
                        }

                        if (var0 > DrawingArea.bottomY) {
                            var0 = DrawingArea.bottomY;
                        }

                        var7 = (var7 << 9) - var30 * var4 + var30;
                        if (var2 < var0) {
                            var3 = var4 <<= 16;
                            if (var1 < 0) {
                                var3 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var28 * var2;
                                var2 = 0;
                            }

                            var41 = var1 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var1 == var2 || var26 >= var27) && (var1 != var2 || var26 <= var28)) {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var1, var5 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var1, var3 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var5 = var4 <<= 16;
                            if (var1 < 0) {
                                var5 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var3 <<= 16;
                            if (var0 < 0) {
                                var3 -= var28 * var0;
                                var0 = 0;
                            }

                            var41 = var1 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if (var26 < var27) {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var1, var5 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1142(DrawingArea.pixels, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1142(DrawingArea.pixels, var19, 0, 0, var1, var4 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var2 < DrawingArea.bottomY) {
                    if (var0 > DrawingArea.bottomY) {
                        var0 = DrawingArea.bottomY;
                    }

                    if (var1 > DrawingArea.bottomY) {
                        var1 = DrawingArea.bottomY;
                    }

                    var8 = (var8 << 9) - var30 * var5 + var30;
                    if (var0 < var1) {
                        var4 = var5 <<= 16;
                        if (var2 < 0) {
                            var4 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var3 <<= 16;
                        if (var0 < 0) {
                            var3 -= var26 * var0;
                            var0 = 0;
                        }

                        var41 = var2 - originY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method1142(DrawingArea.pixels, var19, 0, 0, var2, var4 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1142(DrawingArea.pixels, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method1142(DrawingArea.pixels, var19, 0, 0, var2, var3 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1142(DrawingArea.pixels, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    } else {
                        var3 = var5 <<= 16;
                        if (var2 < 0) {
                            var3 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var4 <<= 16;
                        if (var1 < 0) {
                            var4 -= var26 * var1;
                            var1 = 0;
                        }

                        var41 = var2 - originY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method1142(DrawingArea.pixels, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1142(DrawingArea.pixels, var19, 0, 0, var2, var3 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method1142(DrawingArea.pixels, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1142(DrawingArea.pixels, var19, 0, 0, var2, var5 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    }
                }
            }
        } else {
            var20 = textureCache.get_colour(var18);
//            aBoolean837 = true;
            method1154(var0, var1, var2, var3, var4, var5, repack_lightness(var20, var6), repack_lightness(var20, var7), repack_lightness(var20, var8));
        }
    }

    private static final void method1142(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
        if (clippedHorizontally) {
            if (var6 > DrawingArea.centerX) {
                var6 = DrawingArea.centerX;
            }

            if (var5 < 0) {
                var5 = 0;
            }
        }

        if (var5 < var6) {
            var4 += var5;
            var7 += var8 * var5;
            int var17 = var6 - var5;
            int var15;
            int var16;
            int var19;
            int var18;
            int var21;
            int var20;
            int var23;
            int var22;
            int var10000;
            if (small_texture) {
                var23 = var5 - originX;
                var9 += (var12 >> 3) * var23;
                var10 += (var13 >> 3) * var23;
                var11 += (var14 >> 3) * var23;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12;
                var10 += var13;
                var11 += var14;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 20) + var19;
                var16 = (var20 - var18 >> 3 << 20) + (var21 - var19 >> 3);
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (alpha_opaque) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 12;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 20) + var19;
                            var16 = (var20 - var18 >> 3 << 20) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 12;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 20) + var19;
                            var16 = (var20 - var18 >> 3 << 20) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            } else {
                var23 = var5 - originX;
                var9 += (var12 >> 3) * var23;
                var10 += (var13 >> 3) * var23;
                var11 += (var14 >> 3) * var23;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12;
                var10 += var13;
                var11 += var14;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 18) + var19;
                var16 = (var20 - var18 >> 3 << 18) + (var21 - var19 >> 3);
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (alpha_opaque) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 14;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 18) + var19;
                            var16 = (var20 - var18 >> 3 << 18) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 14;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 18) + var19;
                            var16 = (var20 - var18 >> 3 << 18) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            }

        }
    }

    public static void drawLDTexturedTriangle(int y0, int y1, int y2, int x0, int x1, int x2,
                                              int k1, int l1, int i2, int j2, int k2, int l2,
                                              int i3, int j3, int k3, int l3, int i4, int j4,
                                              int textureId) {
        int ai[] = method371(textureId);
        transparentTexture = !transparent[textureId];
        k2 = j2 - k2;
        j3 = i3 - j3;
        i4 = l3 - i4;
        l2 -= j2;
        k3 -= i3;
        j4 -= l3;
        int l4 = l2 * i3 - k3 * j2 << (Client.log_view_dist == 9 ? 14 : 15);
        int i5 = k3 * l3 - j4 * i3 << 8;
        int j5 = j4 * j2 - l2 * l3 << 5;
        int k5 = k2 * i3 - j3 * j2 << (Client.log_view_dist == 9 ? 14 : 15);
        int l5 = j3 * l3 - i4 * i3 << 8;
        int i6 = i4 * j2 - k2 * l3 << 5;
        int j6 = j3 * l2 - k2 * k3 << (Client.log_view_dist == 9 ? 14 : 15);
        int k6 = i4 * k3 - j3 * j4 << 8;
        int l6 = k2 * j4 - i4 * l2 << 5;
        int i7 = 0;
        int j7 = 0;
        if (y1 != y0) {
            i7 = (x1 - x0 << 16) / (y1 - y0);
            j7 = (l1 - k1 << 16) / (y1 - y0);
        }
        int k7 = 0;
        int l7 = 0;
        if (y2 != y1) {
            k7 = (x2 - x1 << 16) / (y2 - y1);
            l7 = (i2 - l1 << 16) / (y2 - y1);
        }
        int i8 = 0;
        int j8 = 0;
        if (y2 != y0) {
            i8 = (x0 - x2 << 16) / (y0 - y2);
            j8 = (k1 - i2 << 16) / (y0 - y2);
        }
        if (y0 <= y1 && y0 <= y2) {
            if (y0 >= DrawingArea.bottomY)
                return;
            if (y1 > DrawingArea.bottomY)
                y1 = DrawingArea.bottomY;
            if (y2 > DrawingArea.bottomY)
                y2 = DrawingArea.bottomY;
            if (y1 < y2) {
                x2 = x0 <<= 16;
                i2 = k1 <<= 16;
                if (y0 < 0) {
                    x2 -= i8 * y0;
                    x0 -= i7 * y0;
                    i2 -= j8 * y0;
                    k1 -= j7 * y0;
                    y0 = 0;
                }
                x1 <<= 16;
                l1 <<= 16;
                if (y1 < 0) {
                    x1 -= k7 * y1;
                    l1 -= l7 * y1;
                    y1 = 0;
                }
                int k8 = y0 - originY;
                l4 += j5 * k8;
                k5 += i6 * k8;
                j6 += l6 * k8;
                if (y0 != y1 && i8 < i7 || y0 == y1 && i8 > k7) {
                    y2 -= y1;
                    y1 -= y0;
                    y0 = rowOffsets[y0];
                    while (--y1 >= 0) {
                        drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x2 >> 16, x0 >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                        x2 += i8;
                        x0 += i7;
                        i2 += j8;
                        k1 += j7;
                        y0 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    while (--y2 >= 0) {
                        drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x2 >> 16, x1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                        x2 += i8;
                        x1 += k7;
                        i2 += j8;
                        l1 += l7;
                        y0 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    return;
                }
                y2 -= y1;
                y1 -= y0;
                y0 = rowOffsets[y0];
                while (--y1 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x0 >> 16, x2 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                    x2 += i8;
                    x0 += i7;
                    i2 += j8;
                    k1 += j7;
                    y0 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y2 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x1 >> 16, x2 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                    x2 += i8;
                    x1 += k7;
                    i2 += j8;
                    l1 += l7;
                    y0 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            x1 = x0 <<= 16;
            l1 = k1 <<= 16;
            if (y0 < 0) {
                x1 -= i8 * y0;
                x0 -= i7 * y0;
                l1 -= j8 * y0;
                k1 -= j7 * y0;
                y0 = 0;
            }
            x2 <<= 16;
            i2 <<= 16;
            if (y2 < 0) {
                x2 -= k7 * y2;
                i2 -= l7 * y2;
                y2 = 0;
            }
            int l8 = y0 - originY;
            l4 += j5 * l8;
            k5 += i6 * l8;
            j6 += l6 * l8;
            if (y0 != y2 && i8 < i7 || y0 == y2 && k7 > i7) {
                y1 -= y2;
                y2 -= y0;
                y0 = rowOffsets[y0];
                while (--y2 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x1 >> 16, x0 >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    x1 += i8;
                    x0 += i7;
                    l1 += j8;
                    k1 += j7;
                    y0 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y1 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x2 >> 16, x0 >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    x2 += k7;
                    x0 += i7;
                    i2 += l7;
                    k1 += j7;
                    y0 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            y1 -= y2;
            y2 -= y0;
            y0 = rowOffsets[y0];
            while (--y2 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x0 >> 16, x1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                x1 += i8;
                x0 += i7;
                l1 += j8;
                k1 += j7;
                y0 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y1 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y0, x0 >> 16, x2 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                x2 += k7;
                x0 += i7;
                i2 += l7;
                k1 += j7;
                y0 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        if (y1 <= y2) {
            if (y1 >= DrawingArea.bottomY)
                return;
            if (y2 > DrawingArea.bottomY)
                y2 = DrawingArea.bottomY;
            if (y0 > DrawingArea.bottomY)
                y0 = DrawingArea.bottomY;
            if (y2 < y0) {
                x0 = x1 <<= 16;
                k1 = l1 <<= 16;
                if (y1 < 0) {
                    x0 -= i7 * y1;
                    x1 -= k7 * y1;
                    k1 -= j7 * y1;
                    l1 -= l7 * y1;
                    y1 = 0;
                }
                x2 <<= 16;
                i2 <<= 16;
                if (y2 < 0) {
                    x2 -= i8 * y2;
                    i2 -= j8 * y2;
                    y2 = 0;
                }
                int i9 = y1 - originY;
                l4 += j5 * i9;
                k5 += i6 * i9;
                j6 += l6 * i9;
                if (y1 != y2 && i7 < k7 || y1 == y2 && i7 > i8) {
                    y0 -= y2;
                    y2 -= y1;
                    y1 = rowOffsets[y1];
                    while (--y2 >= 0) {
                        drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x0 >> 16, x1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                        x0 += i7;
                        x1 += k7;
                        k1 += j7;
                        l1 += l7;
                        y1 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    while (--y0 >= 0) {
                        drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x0 >> 16, x2 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                        x0 += i7;
                        x2 += i8;
                        k1 += j7;
                        i2 += j8;
                        y1 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    return;
                }
                y0 -= y2;
                y2 -= y1;
                y1 = rowOffsets[y1];
                while (--y2 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x0 >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    x0 += i7;
                    x1 += k7;
                    k1 += j7;
                    l1 += l7;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y0 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x0 >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    x0 += i7;
                    x2 += i8;
                    k1 += j7;
                    i2 += j8;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            x2 = x1 <<= 16;
            i2 = l1 <<= 16;
            if (y1 < 0) {
                x2 -= i7 * y1;
                x1 -= k7 * y1;
                i2 -= j7 * y1;
                l1 -= l7 * y1;
                y1 = 0;
            }
            x0 <<= 16;
            k1 <<= 16;
            if (y0 < 0) {
                x0 -= i8 * y0;
                k1 -= j8 * y0;
                y0 = 0;
            }
            int j9 = y1 - originY;
            l4 += j5 * j9;
            k5 += i6 * j9;
            j6 += l6 * j9;
            if (i7 < k7) {
                y2 -= y0;
                y0 -= y1;
                y1 = rowOffsets[y1];
                while (--y0 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                    x2 += i7;
                    x1 += k7;
                    i2 += j7;
                    l1 += l7;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y2 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x0 >> 16, x1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                    x0 += i8;
                    x1 += k7;
                    k1 += j8;
                    l1 += l7;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            y2 -= y0;
            y0 -= y1;
            y1 = rowOffsets[y1];
            while (--y0 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x2 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                x2 += i7;
                x1 += k7;
                i2 += j7;
                l1 += l7;
                y1 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y2 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x0 >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                x0 += i8;
                x1 += k7;
                k1 += j8;
                l1 += l7;
                y1 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        if (y2 >= DrawingArea.bottomY)
            return;
        if (y0 > DrawingArea.bottomY)
            y0 = DrawingArea.bottomY;
        if (y1 > DrawingArea.bottomY)
            y1 = DrawingArea.bottomY;
        if (y0 < y1) {
            x1 = x2 <<= 16;
            l1 = i2 <<= 16;
            if (y2 < 0) {
                x1 -= k7 * y2;
                x2 -= i8 * y2;
                l1 -= l7 * y2;
                i2 -= j8 * y2;
                y2 = 0;
            }
            x0 <<= 16;
            k1 <<= 16;
            if (y0 < 0) {
                x0 -= i7 * y0;
                k1 -= j7 * y0;
                y0 = 0;
            }
            int k9 = y2 - originY;
            l4 += j5 * k9;
            k5 += i6 * k9;
            j6 += l6 * k9;
            if (k7 < i8) {
                y1 -= y0;
                y0 -= y2;
                y2 = rowOffsets[y2];
                while (--y0 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                    x1 += k7;
                    x2 += i8;
                    l1 += l7;
                    i2 += j8;
                    y2 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y1 >= 0) {
                    drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x0 >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
                    x1 += k7;
                    x0 += i7;
                    l1 += l7;
                    k1 += j7;
                    y2 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            y1 -= y0;
            y0 -= y2;
            y2 = rowOffsets[y2];
            while (--y0 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                x1 += k7;
                x2 += i8;
                l1 += l7;
                i2 += j8;
                y2 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y1 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x0 >> 16, x1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
                x1 += k7;
                x0 += i7;
                l1 += l7;
                k1 += j7;
                y2 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        x0 = x2 <<= 16;
        k1 = i2 <<= 16;
        if (y2 < 0) {
            x0 -= k7 * y2;
            x2 -= i8 * y2;
            k1 -= l7 * y2;
            i2 -= j8 * y2;
            y2 = 0;
        }
        x1 <<= 16;
        l1 <<= 16;
        if (y1 < 0) {
            x1 -= i7 * y1;
            l1 -= j7 * y1;
            y1 = 0;
        }
        int l9 = y2 - originY;
        l4 += j5 * l9;
        k5 += i6 * l9;
        j6 += l6 * l9;
        if (k7 < i8) {
            y0 -= y1;
            y1 -= y2;
            y2 = rowOffsets[y2];
            while (--y1 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x0 >> 16, x2 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                x0 += k7;
                x2 += i8;
                k1 += l7;
                i2 += j8;
                y2 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y0 >= 0) {
                drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
                x1 += i7;
                x2 += i8;
                l1 += j7;
                i2 += j8;
                y2 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        y0 -= y1;
        y1 -= y2;
        y2 = rowOffsets[y2];
        while (--y1 >= 0) {
            drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x0 >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
            x0 += k7;
            x2 += i8;
            k1 += l7;
            i2 += j8;
            y2 += DrawingArea.width;
            l4 += j5;
            k5 += i6;
            j6 += l6;
        }
        while (--y0 >= 0) {
            drawLDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
            x1 += i7;
            x2 += i8;
            l1 += j7;
            i2 += j8;
            y2 += DrawingArea.width;
            l4 += j5;
            k5 += i6;
            j6 += l6;
        }
    }

    public static void drawLDTexturedScanline(int ai[], int ai1[], int k, int l, int i1,
                                              int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3) {
        int i = 0;// was parameter
        int j = 0;// was parameter
        if (l >= i1)
            return;
        int j3;
        int k3;
        if (clippedHorizontally) {
            j3 = (k1 - j1) / (i1 - l);
            if (i1 > DrawingArea.centerX)
                i1 = DrawingArea.centerX;
            if (l < 0) {
                j1 -= l * j3;
                l = 0;
            }
            if (l >= i1)
                return;
            k3 = i1 - l >> 3;
            j3 <<= 12;
            j1 <<= 9;
        } else {
            if (i1 - l > 7) {
                k3 = i1 - l >> 3;
                j3 = (k1 - j1) * anIntArray839[k3] >> 6;
            } else {
                k3 = 0;
                j3 = 0;
            }
            j1 <<= 9;
        }
        k += l;
        if (lowMem) {
            int i4 = 0;
            int k4 = 0;
            int k6 = l - originX;
            l1 += (k2 >> 3) * k6;
            i2 += (l2 >> 3) * k6;
            j2 += (i3 >> 3) * k6;
            int i5 = j2 >> 12;
            if (i5 != 0) {
                i = l1 / i5;
                j = i2 / i5;
                if (i < 0)
                    i = 0;
                else if (i > 4032)
                    i = 4032;
            }
            l1 += k2;
            i2 += l2;
            j2 += i3;
            i5 = j2 >> 12;
            if (i5 != 0) {
                i4 = l1 / i5;
                k4 = i2 / i5;
                if (i4 < 7)
                    i4 = 7;
                else if (i4 > 4032)
                    i4 = 4032;
            }
            int i7 = i4 - i >> 3;
            int k7 = k4 - j >> 3;
            i += (j1 & 0x600000) >> 3;
            int i8 = j1 >> 23;
            if (transparentTexture) {
                while (k3-- > 0) {
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i = i4;
                    j = k4;
                    l1 += k2;
                    i2 += l2;
                    j2 += i3;
                    int j5 = j2 >> 12;
                    if (j5 != 0) {
                        i4 = l1 / j5;
                        k4 = i2 / j5;
                        if (i4 < 7)
                            i4 = 7;
                        else if (i4 > 4032)
                            i4 = 4032;
                    }
                    i7 = i4 - i >> 3;
                    k7 = k4 - j >> 3;
                    j1 += j3;
                    i += (j1 & 0x600000) >> 3;
                    i8 = j1 >> 23;
                }
                for (k3 = i1 - l & 7; k3-- > 0; ) {
                    ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
                    i += i7;
                    j += k7;
                }

                return;
            }
            while (k3-- > 0) {
                int k8;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i += i7;
                j += k7;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i += i7;
                j += k7;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i += i7;
                j += k7;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i += i7;
                j += k7;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i += i7;
                j += k7;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i += i7;
                j += k7;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i += i7;
                j += k7;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = k8;
                k++;
                i = i4;
                j = k4;
                l1 += k2;
                i2 += l2;
                j2 += i3;
                int k5 = j2 >> 12;
                if (k5 != 0) {
                    i4 = l1 / k5;
                    k4 = i2 / k5;
                    if (i4 < 7)
                        i4 = 7;
                    else if (i4 > 4032)
                        i4 = 4032;
                }
                i7 = i4 - i >> 3;
                k7 = k4 - j >> 3;
                j1 += j3;
                i += (j1 & 0x600000) >> 3;
                i8 = j1 >> 23;
            }
            for (k3 = i1 - l & 7; k3-- > 0; ) {
                int l8;
                if ((l8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
                    ai[k] = l8;
                k++;
                i += i7;
                j += k7;
            }

            return;
        }
        int j4 = 0;
        int l4 = 0;
        int l6 = l - originX;
        l1 += (k2 >> 3) * l6;
        i2 += (l2 >> 3) * l6;
        j2 += (i3 >> 3) * l6;
        int l5 = j2 >> 14;
        if (l5 != 0) {
            i = l1 / l5;
            j = i2 / l5;
            if (i < 0)
                i = 0;
            else if (i > 16256)
                i = 16256;
        }
        l1 += k2;
        i2 += l2;
        j2 += i3;
        l5 = j2 >> 14;
        if (l5 != 0) {
            j4 = l1 / l5;
            l4 = i2 / l5;
            if (j4 < 7)
                j4 = 7;
            else if (j4 > 16256)
                j4 = 16256;
        }
        int j7 = j4 - i >> 3;
        int l7 = l4 - j >> 3;
        i += j1 & 0x600000;
        int j8 = j1 >> 23;
        if (transparentTexture) {
            while (k3-- > 0) {
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i = j4;
                j = l4;
                l1 += k2;
                i2 += l2;
                j2 += i3;
                int i6 = j2 >> 14;
                if (i6 != 0) {
                    j4 = l1 / i6;
                    l4 = i2 / i6;
                    if (j4 < 7)
                        j4 = 7;
                    else if (j4 > 16256)
                        j4 = 16256;
                }
                j7 = j4 - i >> 3;
                l7 = l4 - j >> 3;
                j1 += j3;
                i += j1 & 0x600000;
                j8 = j1 >> 23;
            }
            for (k3 = i1 - l & 7; k3-- > 0; ) {
                ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
                i += j7;
                j += l7;
            }

            return;
        }
        while (k3-- > 0) {
            int i9;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i += j7;
            j += l7;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i += j7;
            j += l7;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i += j7;
            j += l7;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i += j7;
            j += l7;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i += j7;
            j += l7;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i += j7;
            j += l7;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i += j7;
            j += l7;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = i9;
            k++;
            i = j4;
            j = l4;
            l1 += k2;
            i2 += l2;
            j2 += i3;
            int j6 = j2 >> 14;
            if (j6 != 0) {
                j4 = l1 / j6;
                l4 = i2 / j6;
                if (j4 < 7)
                    j4 = 7;
                else if (j4 > 16256)
                    j4 = 16256;
            }
            j7 = j4 - i >> 3;
            l7 = l4 - j >> 3;
            j1 += j3;
            i += j1 & 0x600000;
            j8 = j1 >> 23;
        }
        for (int l3 = i1 - l & 7; l3-- > 0; ) {
            int j9;
            if ((j9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
                ai[k] = j9;
            k++;
            i += j7;
            j += l7;
        }

    }

    public static void drawHDTexturedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int l1, int l2,
                                              int l3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3,
                                              int tz1, int tz2, int tz3, int tex) {
        l1 = 0x7f - l1 << 1;
        l2 = 0x7f - l2 << 1;
        l3 = 0x7f - l3 << 1;
        int ai[] = method371(tex);
        transparentTexture = !transparent[tex];
        tx2 = tx1 - tx2;
        ty2 = ty1 - ty2;
        tz2 = tz1 - tz2;
        tx3 -= tx1;
        ty3 -= ty1;
        tz3 -= tz1;
        int l4 = tx3 * ty1 - ty3 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
        int i5 = ty3 * tz1 - tz3 * ty1 << 8;
        int j5 = tz3 * tx1 - tx3 * tz1 << 5;
        int k5 = tx2 * ty1 - ty2 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
        int l5 = ty2 * tz1 - tz2 * ty1 << 8;
        int i6 = tz2 * tx1 - tx2 * tz1 << 5;
        int j6 = ty2 * tx3 - tx2 * ty3 << (Client.log_view_dist == 9 ? 14 : 15);
        int k6 = tz2 * ty3 - ty2 * tz3 << 8;
        int l6 = tx2 * tz3 - tz2 * tx3 << 5;
        int i7 = 0;
        int j7 = 0;
        if (y2 != y1) {
            i7 = (x2 - x1 << 16) / (y2 - y1);
            j7 = (l2 - l1 << 16) / (y2 - y1);
        }
        int k7 = 0;
        int l7 = 0;
        if (y3 != y2) {
            k7 = (x3 - x2 << 16) / (y3 - y2);
            l7 = (l3 - l2 << 16) / (y3 - y2);
        }
        int i8 = 0;
        int j8 = 0;
        if (y3 != y1) {
            i8 = (x1 - x3 << 16) / (y1 - y3);
            j8 = (l1 - l3 << 16) / (y1 - y3);
        }
        if (y1 <= y2 && y1 <= y3) {
            if (y1 >= DrawingArea.bottomY)
                return;
            if (y2 > DrawingArea.bottomY)
                y2 = DrawingArea.bottomY;
            if (y3 > DrawingArea.bottomY)
                y3 = DrawingArea.bottomY;
            if (y2 < y3) {
                x3 = x1 <<= 16;
                l3 = l1 <<= 16;
                if (y1 < 0) {
                    x3 -= i8 * y1;
                    x1 -= i7 * y1;
                    l3 -= j8 * y1;
                    l1 -= j7 * y1;
                    y1 = 0;
                }
                x2 <<= 16;
                l2 <<= 16;
                if (y2 < 0) {
                    x2 -= k7 * y2;
                    l2 -= l7 * y2;
                    y2 = 0;
                }
                int k8 = y1 - originY;
                l4 += j5 * k8;
                k5 += i6 * k8;
                j6 += l6 * k8;
                if (y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7) {
                    y3 -= y2;
                    y2 -= y1;
                    y1 = rowOffsets[y1];
                    while (--y2 >= 0) {
                        drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
                        x3 += i8;
                        x1 += i7;
                        l3 += j8;
                        l1 += j7;
                        y1 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    while (--y3 >= 0) {
                        drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
                        x3 += i8;
                        x2 += k7;
                        l3 += j8;
                        l2 += l7;
                        y1 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    return;
                }
                y3 -= y2;
                y2 -= y1;
                y1 = rowOffsets[y1];
                while (--y2 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
                    x3 += i8;
                    x1 += i7;
                    l3 += j8;
                    l1 += j7;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y3 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
                    x3 += i8;
                    x2 += k7;
                    l3 += j8;
                    l2 += l7;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            x2 = x1 <<= 16;
            l2 = l1 <<= 16;
            if (y1 < 0) {
                x2 -= i8 * y1;
                x1 -= i7 * y1;
                l2 -= j8 * y1;
                l1 -= j7 * y1;
                y1 = 0;
            }
            x3 <<= 16;
            l3 <<= 16;
            if (y3 < 0) {
                x3 -= k7 * y3;
                l3 -= l7 * y3;
                y3 = 0;
            }
            int l8 = y1 - originY;
            l4 += j5 * l8;
            k5 += i6 * l8;
            j6 += l6 * l8;
            if (y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7) {
                y2 -= y3;
                y3 -= y1;
                y1 = rowOffsets[y1];
                while (--y3 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
                    x2 += i8;
                    x1 += i7;
                    l2 += j8;
                    l1 += j7;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y2 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
                    x3 += k7;
                    x1 += i7;
                    l3 += l7;
                    l1 += j7;
                    y1 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            y2 -= y3;
            y3 -= y1;
            y1 = rowOffsets[y1];
            while (--y3 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
                x2 += i8;
                x1 += i7;
                l2 += j8;
                l1 += j7;
                y1 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y2 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
                x3 += k7;
                x1 += i7;
                l3 += l7;
                l1 += j7;
                y1 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        if (y2 <= y3) {
            if (y2 >= DrawingArea.bottomY)
                return;
            if (y3 > DrawingArea.bottomY)
                y3 = DrawingArea.bottomY;
            if (y1 > DrawingArea.bottomY)
                y1 = DrawingArea.bottomY;
            if (y3 < y1) {
                x1 = x2 <<= 16;
                l1 = l2 <<= 16;
                if (y2 < 0) {
                    x1 -= i7 * y2;
                    x2 -= k7 * y2;
                    l1 -= j7 * y2;
                    l2 -= l7 * y2;
                    y2 = 0;
                }
                x3 <<= 16;
                l3 <<= 16;
                if (y3 < 0) {
                    x3 -= i8 * y3;
                    l3 -= j8 * y3;
                    y3 = 0;
                }
                int i9 = y2 - originY;
                l4 += j5 * i9;
                k5 += i6 * i9;
                j6 += l6 * i9;
                if (y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8) {
                    y1 -= y3;
                    y3 -= y2;
                    y2 = rowOffsets[y2];
                    while (--y3 >= 0) {
                        drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
                        x1 += i7;
                        x2 += k7;
                        l1 += j7;
                        l2 += l7;
                        y2 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    while (--y1 >= 0) {
                        drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
                        x1 += i7;
                        x3 += i8;
                        l1 += j7;
                        l3 += j8;
                        y2 += DrawingArea.width;
                        l4 += j5;
                        k5 += i6;
                        j6 += l6;
                    }
                    return;
                }
                y1 -= y3;
                y3 -= y2;
                y2 = rowOffsets[y2];
                while (--y3 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
                    x1 += i7;
                    x2 += k7;
                    l1 += j7;
                    l2 += l7;
                    y2 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y1 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
                    x1 += i7;
                    x3 += i8;
                    l1 += j7;
                    l3 += j8;
                    y2 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            x3 = x2 <<= 16;
            l3 = l2 <<= 16;
            if (y2 < 0) {
                x3 -= i7 * y2;
                x2 -= k7 * y2;
                l3 -= j7 * y2;
                l2 -= l7 * y2;
                y2 = 0;
            }
            x1 <<= 16;
            l1 <<= 16;
            if (y1 < 0) {
                x1 -= i8 * y1;
                l1 -= j8 * y1;
                y1 = 0;
            }
            int j9 = y2 - originY;
            l4 += j5 * j9;
            k5 += i6 * j9;
            j6 += l6 * j9;
            if (i7 < k7) {
                y3 -= y1;
                y1 -= y2;
                y2 = rowOffsets[y2];
                while (--y1 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
                    x3 += i7;
                    x2 += k7;
                    l3 += j7;
                    l2 += l7;
                    y2 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y3 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
                    x1 += i8;
                    x2 += k7;
                    l1 += j8;
                    l2 += l7;
                    y2 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            y3 -= y1;
            y1 -= y2;
            y2 = rowOffsets[y2];
            while (--y1 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
                x3 += i7;
                x2 += k7;
                l3 += j7;
                l2 += l7;
                y2 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y3 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
                x1 += i8;
                x2 += k7;
                l1 += j8;
                l2 += l7;
                y2 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        if (y3 >= DrawingArea.bottomY)
            return;
        if (y1 > DrawingArea.bottomY)
            y1 = DrawingArea.bottomY;
        if (y2 > DrawingArea.bottomY)
            y2 = DrawingArea.bottomY;
        if (y1 < y2) {
            x2 = x3 <<= 16;
            l2 = l3 <<= 16;
            if (y3 < 0) {
                x2 -= k7 * y3;
                x3 -= i8 * y3;
                l2 -= l7 * y3;
                l3 -= j8 * y3;
                y3 = 0;
            }
            x1 <<= 16;
            l1 <<= 16;
            if (y1 < 0) {
                x1 -= i7 * y1;
                l1 -= j7 * y1;
                y1 = 0;
            }
            int k9 = y3 - originY;
            l4 += j5 * k9;
            k5 += i6 * k9;
            j6 += l6 * k9;
            if (k7 < i8) {
                y2 -= y1;
                y1 -= y3;
                y3 = rowOffsets[y3];
                while (--y1 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
                    x2 += k7;
                    x3 += i8;
                    l2 += l7;
                    l3 += j8;
                    y3 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                while (--y2 >= 0) {
                    drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
                    x2 += k7;
                    x1 += i7;
                    l2 += l7;
                    l1 += j7;
                    y3 += DrawingArea.width;
                    l4 += j5;
                    k5 += i6;
                    j6 += l6;
                }
                return;
            }
            y2 -= y1;
            y1 -= y3;
            y3 = rowOffsets[y3];
            while (--y1 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
                x2 += k7;
                x3 += i8;
                l2 += l7;
                l3 += j8;
                y3 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y2 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
                x2 += k7;
                x1 += i7;
                l2 += l7;
                l1 += j7;
                y3 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        x1 = x3 <<= 16;
        l1 = l3 <<= 16;
        if (y3 < 0) {
            x1 -= k7 * y3;
            x3 -= i8 * y3;
            l1 -= l7 * y3;
            l3 -= j8 * y3;
            y3 = 0;
        }
        x2 <<= 16;
        l2 <<= 16;
        if (y2 < 0) {
            x2 -= i7 * y2;
            l2 -= j7 * y2;
            y2 = 0;
        }
        int l9 = y3 - originY;
        l4 += j5 * l9;
        k5 += i6 * l9;
        j6 += l6 * l9;
        if (k7 < i8) {
            y1 -= y2;
            y2 -= y3;
            y3 = rowOffsets[y3];
            while (--y2 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
                x1 += k7;
                x3 += i8;
                l1 += l7;
                l3 += j8;
                y3 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            while (--y1 >= 0) {
                drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
                x2 += i7;
                x3 += i8;
                l2 += j7;
                l3 += j8;
                y3 += DrawingArea.width;
                l4 += j5;
                k5 += i6;
                j6 += l6;
            }
            return;
        }
        y1 -= y2;
        y2 -= y3;
        y3 = rowOffsets[y3];
        while (--y2 >= 0) {
            drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
            x1 += k7;
            x3 += i8;
            l1 += l7;
            l3 += j8;
            y3 += DrawingArea.width;
            l4 += j5;
            k5 += i6;
            j6 += l6;
        }
        while (--y1 >= 0) {
            drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
            x2 += i7;
            x3 += i8;
            l2 += j7;
            l3 += j8;
            y3 += DrawingArea.width;
            l4 += j5;
            k5 += i6;
            j6 += l6;
        }
    }

    public static void drawHDTexturedScanline(int ai[], int ai1[], int k, int x1, int x2, int l1,
                                              int l2, int a1, int i2, int j2, int k2, int a2, int i3) {
        int i = 0;//was parameter
        int j = 0;//was parameter
        if (x1 >= x2)
            return;
        int dl = (l2 - l1) / (x2 - x1);
        int n;
        if (clippedHorizontally) {
            if (x2 > DrawingArea.centerX)
                x2 = DrawingArea.centerX;
            if (x1 < 0) {
                l1 -= x1 * dl;
                x1 = 0;
            }
        }
        if (x1 >= x2)
            return;
        n = x2 - x1 >> 3;
        k += x1;
        if (lowMem) {
            int i4 = 0;
            int k4 = 0;
            int k6 = x1 - originX;
            a1 += (k2 >> 3) * k6;
            i2 += (a2 >> 3) * k6;
            j2 += (i3 >> 3) * k6;
            int i5 = j2 >> 12;
            if (i5 != 0) {
                i = a1 / i5;
                j = i2 / i5;
                if (i < 0)
                    i = 0;
                else if (i > 4032)
                    i = 4032;
            }
            a1 += k2;
            i2 += a2;
            j2 += i3;
            i5 = j2 >> 12;
            if (i5 != 0) {
                i4 = a1 / i5;
                k4 = i2 / i5;
                if (i4 < 7)
                    i4 = 7;
                else if (i4 > 4032)
                    i4 = 4032;
            }
            int i7 = i4 - i >> 3;
            int k7 = k4 - j >> 3;
            if (transparentTexture) {
                int rgb;
                int l;
                while (n-- > 0) {
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                    a1 += k2;
                    i2 += a2;
                    j2 += i3;
                    int j5 = j2 >> 12;
                    if (j5 != 0) {
                        i4 = a1 / j5;
                        k4 = i2 / j5;
                        if (i4 < 7)
                            i4 = 7;
                        else if (i4 > 4032)
                            i4 = 4032;
                    }
                    i7 = i4 - i >> 3;
                    k7 = k4 - j >> 3;
                    l1 += dl;
                }
                for (n = x2 - x1 & 7; n-- > 0; ) {
                    rgb = ai1[(j & 0xfc0) + (i >> 6)];
                    l = l1 >> 16;
                    ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                    i += i7;
                    j += k7;
                    l1 += dl;
                }
                return;
            }
            while (n-- > 0) {
                int k8;
                int l;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                if ((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
                a1 += k2;
                i2 += a2;
                j2 += i3;
                int k5 = j2 >> 12;
                if (k5 != 0) {
                    i4 = a1 / k5;
                    k4 = i2 / k5;
                    if (i4 < 7)
                        i4 = 7;
                    else if (i4 > 4032)
                        i4 = 4032;
                }
                i7 = i4 - i >> 3;
                k7 = k4 - j >> 3;
                l1 += dl;
            }
            for (n = x2 - x1 & 7; n-- > 0; ) {
                int l8;
                int l;
                if ((l8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
                    l = l1 >> 16;
                    ai[k] = ((l8 & 0xff00ff) * l & ~0xff00ff) + ((l8 & 0xff00) * l & 0xff0000) >> 8;
                }
                k++;
                i += i7;
                j += k7;
                l1 += dl;
            }

            return;
        }
        int j4 = 0;
        int l4 = 0;
        int l6 = x1 - originX;
        a1 += (k2 >> 3) * l6;
        i2 += (a2 >> 3) * l6;
        j2 += (i3 >> 3) * l6;
        int l5 = j2 >> 14;
        if (l5 != 0) {
            i = a1 / l5;
            j = i2 / l5;
            if (i < 0)
                i = 0;
            else if (i > 16256)
                i = 16256;
        }
        a1 += k2;
        i2 += a2;
        j2 += i3;
        l5 = j2 >> 14;
        if (l5 != 0) {
            j4 = a1 / l5;
            l4 = i2 / l5;
            if (j4 < 7)
                j4 = 7;
            else if (j4 > 16256)
                j4 = 16256;
        }
        int j7 = j4 - i >> 3;
        int l7 = l4 - j >> 3;
        if (transparentTexture) {
            while (n-- > 0) {
                int rgb;
                int l;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
                a1 += k2;
                i2 += a2;
                j2 += i3;
                int i6 = j2 >> 14;
                if (i6 != 0) {
                    j4 = a1 / i6;
                    l4 = i2 / i6;
                    if (j4 < 7)
                        j4 = 7;
                    else if (j4 > 16256)
                        j4 = 16256;
                }
                j7 = j4 - i >> 3;
                l7 = l4 - j >> 3;
                l1 += dl;
            }
            for (n = x2 - x1 & 7; n-- > 0; ) {
                int rgb;
                int l;
                rgb = ai1[(j & 0x3f80) + (i >> 7)];
                l = l1 >> 16;
                ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
                i += j7;
                j += l7;
                l1 += dl;
            }

            return;
        }
        while (n-- > 0) {
            int i9;
            int l;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            if ((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
            a1 += k2;
            i2 += a2;
            j2 += i3;
            int j6 = j2 >> 14;
            if (j6 != 0) {
                j4 = a1 / j6;
                l4 = i2 / j6;
                if (j4 < 7)
                    j4 = 7;
                else if (j4 > 16256)
                    j4 = 16256;
            }
            j7 = j4 - i >> 3;
            l7 = l4 - j >> 3;
            l1 += dl;
        }
        for (int l3 = x2 - x1 & 7; l3-- > 0; ) {
            int j9;
            int l;
            if ((j9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
                l = l1 >> 16;
                ai[k] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 8;
                ;
            }
            k++;
            i += j7;
            j += l7;
            l1 += dl;
        }

    }

    private static int repack_lightness(int var0, int var1) {
        var1 = var1 * (var0 & 127) >> 7;
        if (var1 < 2) {
            var1 = 2;
        } else if (var1 > 126) {
            var1 = 126;
        }

        return (var0 & '\uff80') + var1;
    }

    static final void method1135(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
        int[] var19 = textureCache.getPixels(var18);
        int var20;
        if (var19 == null) {
            var20 = textureCache.get_colour(var18);
            method1154(var0, var1, var2, var3, var4, var5, repack_lightness(var20, var6), repack_lightness(var20, var7), repack_lightness(var20, var8));
        } else {
            small_texture = textureCache.is_large(var18);
            alpha_opaque = textureCache.is_alpha_opaque(var18);
            var20 = var4 - var3;
            int var21 = var1 - var0;
            int var22 = var5 - var3;
            int var23 = var2 - var0;
            int var24 = var7 - var6;
            int var25 = var8 - var6;
            int var26 = 0;
            if (var1 != var0) {
                var26 = (var4 - var3 << 16) / (var1 - var0);
            }

            int var27 = 0;
            if (var2 != var1) {
                var27 = (var5 - var4 << 16) / (var2 - var1);
            }

            int var28 = 0;
            if (var2 != var0) {
                var28 = (var3 - var5 << 16) / (var0 - var2);
            }

            int var29 = var20 * var23 - var22 * var21;
            if (var29 != 0) {
                int var30 = (var24 * var23 - var25 * var21 << 9) / var29;
                int var31 = (var25 * var20 - var24 * var22 << 9) / var29;
                var10 = var9 - var10;
                var13 = var12 - var13;
                var16 = var15 - var16;
                var11 -= var9;
                var14 -= var12;
                var17 -= var15;
                int var32 = var11 * var12 - var14 * var9 << 14;
                int var33 = var14 * var15 - var17 * var12 << 5;
                int var34 = var17 * var9 - var11 * var15 << 5;
                int var35 = var10 * var12 - var13 * var9 << 14;
                int var36 = var13 * var15 - var16 * var12 << 5;
                int var37 = var16 * var9 - var10 * var15 << 5;
                int var38 = var13 * var11 - var10 * var14 << 14;
                int var39 = var16 * var14 - var13 * var17 << 5;
                int var40 = var10 * var17 - var16 * var11 << 5;
                int var41;
                if (var0 <= var1 && var0 <= var2) {
                    if (var0 < bottomY) {
                        if (var1 > bottomY) {
                            var1 = bottomY;
                        }

                        if (var2 > bottomY) {
                            var2 = bottomY;
                        }

                        var6 = (var6 << 9) - var30 * var3 + var30;
                        if (var1 < var2) {
                            var5 = var3 <<= 16;
                            if (var0 < 0) {
                                var5 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var4 <<= 16;
                            if (var1 < 0) {
                                var4 -= var27 * var1;
                                var1 = 0;
                            }

                            var41 = var0 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var1 || var28 >= var26) && (var0 != var1 || var28 <= var27)) {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var0, var4 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var0, var5 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var4 = var3 <<= 16;
                            if (var0 < 0) {
                                var4 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var27 * var2;
                                var2 = 0;
                            }

                            var41 = var0 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var2 || var28 >= var26) && (var0 != var2 || var27 <= var26)) {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var0, var3 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = rowOffsets[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var0, var4 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var1 <= var2) {
                    if (var1 < bottomY) {
                        if (var2 > bottomY) {
                            var2 = bottomY;
                        }

                        if (var0 > bottomY) {
                            var0 = bottomY;
                        }

                        var7 = (var7 << 9) - var30 * var4 + var30;
                        if (var2 < var0) {
                            var3 = var4 <<= 16;
                            if (var1 < 0) {
                                var3 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var28 * var2;
                                var2 = 0;
                            }

                            var41 = var1 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var1 == var2 || var26 >= var27) && (var1 != var2 || var26 <= var28)) {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var1, var5 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var1, var3 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var5 = var4 <<= 16;
                            if (var1 < 0) {
                                var5 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var3 <<= 16;
                            if (var0 < 0) {
                                var3 -= var28 * var0;
                                var0 = 0;
                            }

                            var41 = var1 - originY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if (var26 < var27) {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var1, var5 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = rowOffsets[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method1143(DrawingArea.pixels, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += DrawingArea.width;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method1143(DrawingArea.pixels, var19, 0, 0, var1, var4 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += DrawingArea.width;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var2 < bottomY) {
                    if (var0 > bottomY) {
                        var0 = bottomY;
                    }

                    if (var1 > bottomY) {
                        var1 = bottomY;
                    }

                    var8 = (var8 << 9) - var30 * var5 + var30;
                    if (var0 < var1) {
                        var4 = var5 <<= 16;
                        if (var2 < 0) {
                            var4 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var3 <<= 16;
                        if (var0 < 0) {
                            var3 -= var26 * var0;
                            var0 = 0;
                        }

                        var41 = var2 - originY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method1143(DrawingArea.pixels, var19, 0, 0, var2, var4 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1143(DrawingArea.pixels, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method1143(DrawingArea.pixels, var19, 0, 0, var2, var3 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1143(DrawingArea.pixels, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    } else {
                        var3 = var5 <<= 16;
                        if (var2 < 0) {
                            var3 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var4 <<= 16;
                        if (var1 < 0) {
                            var4 -= var26 * var1;
                            var1 = 0;
                        }

                        var41 = var2 - originY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method1143(DrawingArea.pixels, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1143(DrawingArea.pixels, var19, 0, 0, var2, var3 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = rowOffsets[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method1143(DrawingArea.pixels, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += DrawingArea.width;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method1143(DrawingArea.pixels, var19, 0, 0, var2, var5 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += DrawingArea.width;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    }
                }
            }
        }
    }

    private static final void method1143(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
        if (clippedHorizontally) {
            if (var6 > centerX) {
                var6 = centerX;
            }

            if (var5 < 0) {
                var5 = 0;
            }
        }

        if (var5 < var6) {
            var4 += var5;
            var7 += var8 * var5;
            int var17 = var6 - var5;
            int var15;
            int var16;
            int var19;
            int var18;
            int var21;
            int var20;
            int var23;
            int var22;
            if (small_texture) {
                var23 = var5 - originX;
                var9 += var12 * var23;
                var10 += var13 * var23;
                var11 += var14 * var23;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12 * var17;
                var10 += var13 * var17;
                var11 += var14 * var17;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 20) + var19;
                var16 = ((var20 - var18) / var17 << 20) + (var21 - var19) / var17;
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (alpha_opaque) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            } else {
                var23 = var5 - originX;
                var9 += var12 * var23;
                var10 += var13 * var23;
                var11 += var14 * var23;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12 * var17;
                var10 += var13 * var17;
                var11 += var14 * var17;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 18) + var19;
                var16 = ((var20 - var18) / var17 << 18) + (var21 - var19) / var17;
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (alpha_opaque) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            }

        }
    }

}
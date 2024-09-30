package com.proxy;

import static com.proxy.Rasterizer.method371;
import static com.proxy.Rasterizer.transparent;

public class TextureCache {

    public boolean is_large(int var18) {
        return Rasterizer.lowMem;
    }

    public int[] getPixels(int var18) {
        return method371(var18);
    }

    public boolean is_alpha_opaque(int var18) {
        return !transparent[var18];
    }

    public int get_colour(int var18) {
//        return anIntArray485[var18]; //  TODO: HSL
        return 0;
    }
}

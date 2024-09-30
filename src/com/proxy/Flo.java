package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Flo {

    public static Flo[] underlay;
    public static Flo[] overlay;
    public int rgb;
    public int textureId;
    public boolean aBoolean393;
    public int tile_colour;
    public int minimap_colour;
    int hue;
    int chroma;
    int saturation;
    int luminance;

    private Flo() {
        textureId = -1;
        aBoolean393 = true;
    }

    public static void unpackConfig(StreamLoader streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("flo.dat"));
        int underlayCount = stream.readUnsignedWord();
        underlay = new Flo[underlayCount];
        System.out.println("Loaded " + underlayCount + " underlays");
        for (int j = 0; j < underlayCount; j++) {
            underlay[j] = new Flo();
            underlay[j].decodeUnderlay(stream);
            underlay[j].postDecode();
        }
        int overlayCount = stream.readUnsignedWord();
        overlay = new Flo[overlayCount];
        System.out.println("Loaded " + overlayCount + " overlays");
        for (int j = 0; j < overlayCount; j++) {
            overlay[j] = new Flo();
            overlay[j].decodeOverlay(stream);
            overlay[j].postDecode();
        }
        if (stream.currentOffset != stream.buffer.length) {
            throw new RuntimeException("Not all underlays/overlays were loaded.");
        }
    }

    static int method1252(int i) {
        if (i == 0xff00ff) {
            return -1;
        }
        return rgb2hsl(i);
    }

    static int rgb2hsl(int i) {
        double d = (double) (i & 0xff) / 256.0;
        double d_21_ = (double) (i >> 8 & 0xff) / 256.0;
        double d_22_ = (double) ((i & 0xffb3ea) >> 16) / 256.0;
        double d_23_ = d_22_;
        double d_24_ = d_22_;
        if (d_23_ < d_21_) {
            d_23_ = d_21_;
        }
        double d_25_ = 0.0;
        if (d_23_ < d) {
            d_23_ = d;
        }
        double d_26_ = 0.0;
        if (d_21_ < d_24_) {
            d_24_ = d_21_;
        }
        if (d < d_24_) {
            d_24_ = d;
        }
        double d_27_ = (d_24_ + d_23_) / 2.0;
        if (d_24_ != d_23_) {
            if (d_27_ < 0.5) {
                d_26_ = (d_23_ - d_24_) / (d_23_ + d_24_);
            }
            if (d_23_ == d_22_) {
                d_25_ = (-d + d_21_) / (d_23_ - d_24_);
            } else if (d_21_ != d_23_) {
                if (d == d_23_) {
                    d_25_ = (-d_21_ + d_22_) / (d_23_ - d_24_) + 4.0;
                }
            } else {
                d_25_ = (-d_22_ + d) / (d_23_ - d_24_) + 2.0;
            }
            if (d_27_ >= 0.5) {
                d_26_ = (-d_24_ + d_23_) / (-d_23_ + 2.0 - d_24_);
            }
        }
        d_25_ /= 6.0;
        int i_28_ = (int) (d_25_ * 256.0);
        int i_29_ = (int) (d_27_ * 256.0);
        if ((i_29_ ^ 0xffffffff) > -1) {
            i_29_ = 0;
        } else if (i_29_ > 255) {
            i_29_ = 255;
        }
        int i_30_ = (int) (256.0 * d_26_);
        if ((i_30_ ^ 0xffffffff) <= -1) {
            if (i_30_ > 255) {
                i_30_ = 255;
            }
        } else {
            i_30_ = 0;
        }
        if (i_29_ <= 243) {
            if (i_29_ <= 217) {
                if (i_29_ <= 192) {
                    if (i_29_ > 179) {
                        i_30_ >>= 1;
                    }
                } else {
                    i_30_ >>= 2;
                }
            } else {
                i_30_ >>= 3;
            }
        } else {
            i_30_ >>= 4;
        }
        return (i_28_ >> 2 << 10) + ((i_30_ >> 5 << 7) + (i_29_ >> 1));
    }

    private void postDecode() {
    }

    private void decodeOverlay(Stream stream) {
        do {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                return;
            decodeOverlay(stream, opcode);
        } while (true);
    }

    private void decodeOverlay(Stream stream, int opcode) {
        if (opcode == 1) {
            rgb = stream.read3Bytes();
            transformSnow();
            tile_colour = method1252(rgb);
        } else if (opcode == 2) {
            textureId = stream.readUnsignedByte();
        } else if (opcode == 3) {
            textureId = stream.readUnsignedWord();
            if (textureId == 65535) {
                textureId = -1;
            }
        } else if (opcode == 5) {
            aBoolean393 = false;
        } else if (opcode == 7) {
            minimap_colour = method1252(stream.read3Bytes());
        } else if (opcode == 8) {
            // empty
        } else if (opcode == 9) {
            stream.readUnsignedWord();
        } else if (opcode == 10) {
            // aBool283 = false;
        } else if (opcode == 11) {
            stream.readUnsignedByte();
        } else if (opcode == 12) {
            // aBool281 = true;
        } else if (opcode == 13) {
            stream.read3Bytes();
        } else if (opcode == 14) {
            stream.readUnsignedByte();
        } else {
            throw new RuntimeException("Error unrecognised config code: " + opcode);
        }
    }

    private void decodeUnderlay(Stream stream) {
        do {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                return;
            decodeUnderlay(stream, opcode);
        } while (true);
    }

    private void decodeUnderlay(Stream stream, int opcode) {
        if (opcode == 1) {
            rgb = stream.read3Bytes();
            transformSnow();
            setHSL(rgb);
        } else if (opcode == 2) {
            textureId = stream.readUnsignedWord();
            if (textureId == 65535) {
                textureId = -1;
            }
        } else if (opcode == 3) {
            stream.readUnsignedWord();
        } else if (opcode == 4) {
            // empty
        } else {
            throw new RuntimeException("Error unrecognised config code: " + opcode);
        }
    }

    private void transformSnow() {
        if (DateAndTime.getTodaysDate().contains(".12")) {
            if (rgb == 0x35720A || rgb == 0x50680B || rgb == 0x78680B || rgb == 0x6CAC10 || rgb == 0x819531)//whitefloors
                rgb = 0xffffff;//end
        }
    }

    private final void setHSL(int var1) {
        double red = (255 & var1 >> 16) / 256.0D;
        double green = (255 & var1 >> 8) / 256.0D;
        double blue = (var1 & 255) / 256.0D;
        double lightest = red;
        if (green < red) {
            lightest = green;
        }
        if (blue < lightest) {
            lightest = blue;
        }
        double darkest = red;
        if (green > red) {
            darkest = green;
        }
        if (blue > darkest) {
            darkest = blue;
        }
        double h = 0.0D;
        double s = 0.0D;
        double l = (darkest + lightest) / 2.0D;
        if (lightest != darkest) {
            if (0.5D > l) {
                s = (darkest - lightest) / (darkest + lightest);
            }

            if (darkest != red) {
                if (green != darkest) {
                    if (blue == darkest) {
                        h = 4.0D + (-green + red) / (-lightest + darkest);
                    }
                } else {
                    h = (blue - red) / (darkest - lightest) + 2.0D;
                }
            } else {
                h = (-blue + green) / (-lightest + darkest);
            }

            if (0.5D <= l) {
                s = (darkest - lightest) / (-lightest + (2.0D - darkest));
            }
        }

        if (l > 0.5D) {
            luminance = (int) (s * (-l + 1.0D) * 512.0D);
        } else {
            luminance = (int) (s * l * 512.0D);
        }

        if (1 > luminance) {
            luminance = 1;
        }

        hue = (int) (s * 256.0D);
        saturation = (int) (256.0D * l);
        if (~saturation <= -1) {
            if (255 < saturation) {
                saturation = 255;
            }
        } else {
            saturation = 0;
        }
        h /= 6.0D;
        chroma = (int) (luminance * h);
        if (-1 >= ~hue) {
            if (hue > 255) {
                hue = 255;
            }
        } else {
            hue = 0;
        }
    }
}

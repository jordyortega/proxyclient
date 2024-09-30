package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class FrameReader {


    private static FrameReader animationlist[][];
    public int displayLength;
    public SkinList mySkinList;
    public int stepCount;
    public int opCodeLinkTable[];
    public int xOffset[];
    public int yOffset[];
    public int zOffset[];

    private FrameReader() {
    }

    public static void method528(int i) {
        animationlist = new FrameReader[4000][0];
    }

    public static void load(int file, byte[] fileData) {
        try {
            Stream datStream = new Stream(fileData);
            SkinList skinList = new SkinList(datStream);
            int framesArrayLength = datStream.readUnsignedWord();
            int frameCount = datStream.readUnsignedWord();
            animationlist[file] = new FrameReader[framesArrayLength];
            int ai[] = new int[500];
            int ai1[] = new int[500];
            int ai2[] = new int[500];
            int ai3[] = new int[500];
            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {

                int frameId = datStream.readUnsignedWord();
                FrameReader frameReader = animationlist[file][frameId] = new FrameReader();
                frameReader.mySkinList = skinList;

                int magic1 = datStream.readUnsignedByte();
                int magic2 = datStream.readUnsignedByte();
                if (magic1 != 'W' || magic2 != 'Y') {
                    throw new RuntimeException("Invalid frame data");
                }
                byte[] frameData = new byte[datStream.readUnsignedWord()];
                datStream.readBytes(frameData.length, 0, frameData);

                Stream header = new Stream(frameData);
                Stream content = new Stream(frameData);
                header.readUnsignedWord(); // base id
                int transformCount = header.readUnsignedByte();
                content.currentOffset = header.currentOffset + transformCount;

                int transformId = 0;
                int lastTransformOrigin = -1;
                for (int transformIndex = 0; transformIndex < transformCount; transformIndex++) {
                    int flags = header.readUnsignedByte();
                    if (flags > 0) {
                        if (skinList.transformTypes[transformIndex] != 0) {
                            for (int l3 = transformIndex - 1; l3 > lastTransformOrigin; l3--) {
                                if (skinList.transformTypes[l3] != 0)
                                    continue;
                                ai[transformId] = l3;
                                ai1[transformId] = 0;
                                ai2[transformId] = 0;
                                ai3[transformId] = 0;
                                transformId++;
                                break;
                            }
                        }
                        ai[transformId] = transformIndex;
                        short c = 0;
                        if (skinList.transformTypes[transformIndex] == 3)
                            c = (short) 128;
                        if ((flags & 1) != 0)
                            ai1[transformId] = (short) content.gSmart1or2();
                        else
                            ai1[transformId] = c;
                        if ((flags & 2) != 0)
                            ai2[transformId] = (short) content.gSmart1or2();
                        else
                            ai2[transformId] = c;
                        if ((flags & 4) != 0)
                            ai3[transformId] = (short) content.gSmart1or2();
                        else
                            ai3[transformId] = c;
                        lastTransformOrigin = transformIndex;
                        transformId++;
                    }
                }
                frameReader.stepCount = transformId;
                frameReader.opCodeLinkTable = new int[transformId];
                frameReader.xOffset = new int[transformId];
                frameReader.yOffset = new int[transformId];
                frameReader.zOffset = new int[transformId];
                for (int k3 = 0; k3 < transformId; k3++) {
                    frameReader.opCodeLinkTable[k3] = ai[k3];
                    frameReader.xOffset[k3] = ai1[k3];
                    frameReader.yOffset[k3] = ai2[k3];
                    frameReader.zOffset[k3] = ai3[k3];
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void nullLoader() {
        animationlist = null;
    }

    public static FrameReader forID(int i) {
        try {
            String s = "";
            int file = 0;
            int k = 0;
            s = Integer.toHexString(i);
            file = Integer.parseInt(s.substring(0, s.length() - 4), 16);
            k = Integer.parseInt(s.substring(s.length() - 4), 16);
            if (animationlist[file].length == 0) {
                Client.onDemandFetcher.method558(1, file);
                return null;
            }
            return animationlist[file][k];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNullFrame(int i) {
        return i == -1;
    }

}

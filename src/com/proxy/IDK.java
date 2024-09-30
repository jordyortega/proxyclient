package com.proxy;

public final class IDK {

    public static int length;
    public static IDK cache[];
    private final int[] anIntArray661 = {
            -1, -1, -1, -1, -1
    };
    public int anInt657;
    public boolean aBoolean662;
    private int[] anIntArray659;
    private int[] anIntArray660;
    private int[] anIntArray658;

    private IDK() {
        anInt657 = -1;
        aBoolean662 = false;
    }

    public static void unpackConfig(StreamLoader streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("idk.dat"));
        length = stream.readUnsignedWord();
        System.out.println("Loaded " + length + " IDKs");
        cache = new IDK[length];
        for (int j = 0; j < length; j++) {
            cache[j] = new IDK();
            cache[j].readValues(stream);
        }
    }

    private void readValues(Stream stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            if (i == 1)
                anInt657 = stream.readUnsignedByte();
            else if (i == 2) {
                int j = stream.readUnsignedByte();
                anIntArray658 = new int[j];
                for (int k = 0; k < j; k++)
                    anIntArray658[k] = stream.readUnsignedWord();

            } else if (i == 3) {
                aBoolean662 = true;

            } else if (i == 40) {
                int length = stream.readUnsignedByte();
                anIntArray659 = new int[length];
                anIntArray660 = new int[length];
                for (int faceRecolor = 0; faceRecolor < length; faceRecolor++) {
                    anIntArray659[faceRecolor] = stream.readUnsignedWord();
                    anIntArray660[faceRecolor] = stream.readUnsignedWord();
                }
            } else if (i == 41) {
                int length = stream.readUnsignedByte();
                short[] aShortArray471 = new short[length];
                short[] aShortArray470 = new short[length];

                for (int var5 = 0; var5 < length; ++var5) {
                    aShortArray471[var5] = (short) stream.readUnsignedWord();
                    aShortArray470[var5] = (short) stream.readUnsignedWord();
                }
            } else if (i >= 60 && i < 70)
                anIntArray661[i - 60] = stream.readUnsignedWord();
            else
                System.out.println("Error unrecognised config code: " + i);
        } while (true);
    }

    public boolean method537() {
        if (anIntArray658 == null)
            return true;
        boolean flag = true;
        for (int j = 0; j < anIntArray658.length; j++)
            if (!Model.method463(anIntArray658[j]))
                flag = false;

        return flag;
    }

    public Model method538() {
        if (anIntArray658 == null)
            return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray658.length];
        for (int i = 0; i < anIntArray658.length; i++)
            aclass30_sub2_sub4_sub6s[i] = Model.method462(anIntArray658[i]);

        Model model;
        if (aclass30_sub2_sub4_sub6s.length == 1)
            model = aclass30_sub2_sub4_sub6s[0];
        else
            model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
        if (anIntArray659 != null) {
            for (int j = 0; j < anIntArray659.length; j++) {
                model.method476(anIntArray659[j], anIntArray660[j]);
            }
        }
        return model;
    }

    public boolean method539() {
        boolean flag1 = true;
        for (int i = 0; i < 5; i++)
            if (anIntArray661[i] != -1 && !Model.method463(anIntArray661[i]))
                flag1 = false;

        return flag1;
    }

    public Model method540() {
        Model aclass30_sub2_sub4_sub6s[] = new Model[5];
        int j = 0;
        for (int k = 0; k < 5; k++)
            if (anIntArray661[k] != -1)
                aclass30_sub2_sub4_sub6s[j++] = Model.method462(anIntArray661[k]);

        Model model = new Model(j, aclass30_sub2_sub4_sub6s);
        if (anIntArray659 != null) {
            for (int l = 0; l < anIntArray659.length; l++) {
                model.method476(anIntArray659[l], anIntArray660[l]);
            }

        }
        return model;
    }
}

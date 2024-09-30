package com.proxy;

public final class SkinList {

    public final int[] transformTypes;
    public final int[][] labels;

    public SkinList(Stream stream) {
        int transformCount = stream.readUnsignedByte();
        boolean[] shadowed = new boolean[transformCount];
        transformTypes = new int[transformCount];
        labels = new int[transformCount][];
        for (int i_0_ = 0; i_0_ < transformCount; i_0_++)
            transformTypes[i_0_] = stream.readUnsignedByte();
        for (int i_1_ = 0; i_1_ < transformCount; i_1_++)
            shadowed[i_1_] = stream.readUnsignedByte() == 1;
        for (int i_2_ = 0; transformCount > i_2_; i_2_++)
            labels[i_2_] = new int[stream.readUnsignedByte()];
        for (int i_3_ = 0; i_3_ < transformCount; i_3_++) {
            for (int i_4_ = 0; i_4_ < labels[i_3_].length; i_4_++)
                labels[i_3_][i_4_] = stream.readUnsignedByte();
        }
    }
}


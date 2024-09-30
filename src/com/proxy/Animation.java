package com.proxy;

public final class Animation {

    public static Animation anims[];
    public static int anInt367;
    public int frameCount;
    public int frameIDs[];
    public int frameIDs2[];
    public int[] delays;
    public int loopDelay;
    public int animationFlowControl[];
    public boolean oneSquareAnimation;
    public int forcedPriority;
    public int leftHandItem;
    public int rightHandItem;
    public int frameStep;
    public int resetWhenWalk;
    public int priority;
    public int delayType;

    public Animation() {
        loopDelay = -1;
        oneSquareAnimation = false;
        forcedPriority = 5;
        leftHandItem = -1;
        rightHandItem = -1;
        frameStep = 99;
        resetWhenWalk = -1;
        priority = -1;
        delayType = 2;
    }

    public static void unpackConfig(StreamLoader streamLoader) {
        Stream datStream = new Stream(streamLoader.getDataForName("seq.dat"));
        int seqCount = datStream.readUnsignedWord();
        anims = new Animation[seqCount];
        System.out.println("Loading 502 animations: " + seqCount);
        for (int j = 0; j < seqCount; j++) {
            Animation animation = anims[j] = new Animation();

            int dataLength = datStream.readUnsignedWord();

            byte[] data = new byte[dataLength];
            datStream.readBytes(dataLength, 0, data);
            Stream seqStream = new Stream(data);

            animation.decode(seqStream);
        }
    }

    public int getFrameLength(int i) {
        int j = delays[i];
        if (j == 0) {
            FrameReader frameReader = FrameReader.forID(frameIDs[i]);
            if (frameReader != null) j = delays[i] = frameReader.displayLength;
        }
        if (j == 0) j = 1;
        return j;
    }

    public void decode(Stream stream) {
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0) {
                break;
            }
            decode(stream, opcode);
        }
        postDecode();
    }

    private void postDecode() {
        if (frameCount == 0) {
            frameCount = 1;
            frameIDs = new int[1];
            frameIDs[0] = -1;
            frameIDs2 = new int[1];
            frameIDs2[0] = -1;
            delays = new int[1];
            delays[0] = -1;
        }
        if (resetWhenWalk == -1) if (animationFlowControl != null) resetWhenWalk = 2;
        else resetWhenWalk = 0;
        if (priority == -1) {
            if (animationFlowControl != null) {
                priority = 2;
                return;
            }
            priority = 0;
        }
    }

    private void decode(Stream stream, int opcode) {
        if (opcode == 1) {
            int frameCount = stream.readUnsignedWord();
            this.frameCount = frameCount;
            delays = new int[frameCount];
            for (int frame = 0; frame < frameCount; frame++)
                delays[frame] = stream.readUnsignedWord();
            frameIDs = new int[frameCount];
            for (int frame = 0; frame < frameCount; frame++)
                frameIDs[frame] = stream.readUnsignedWord();
            for (int frame = 0; frame < frameCount; frame++)
                frameIDs[frame] = (stream.readUnsignedWord() << 16) + frameIDs[frame];
        } else if (opcode == 2) {
            loopDelay = stream.readUnsignedWord();
        } else if (opcode == 3) {
            int i_36_ = stream.readUnsignedByte();
            animationFlowControl = new int[1 + i_36_];
            for (int i_37_ = 0; i_36_ > i_37_; i_37_++)
                animationFlowControl[i_37_] = stream.readUnsignedByte();
            animationFlowControl[i_36_] = 9999999;
        } else if (opcode == 4) {
            oneSquareAnimation = true;
        } else if (opcode == 5) {
            forcedPriority = stream.readUnsignedByte();
        } else if (opcode == 6) {
            leftHandItem = stream.readUnsignedWord();
        } else if (opcode == 7) {
            rightHandItem = stream.readUnsignedWord();
        } else if (opcode == 8) {
            frameStep = stream.readUnsignedByte();
        } else if (opcode == 9) {
            resetWhenWalk = stream.readUnsignedByte();
        } else if (opcode == 10) {
            priority = stream.readUnsignedByte();
        } else if (opcode == 11) {
            delayType = stream.readUnsignedByte();
        } else if (opcode == 12) {
            int frameCount = stream.readUnsignedByte();
            frameIDs2 = new int[frameCount];
            for (int i_41_ = 0; i_41_ < frameCount; i_41_++)
                frameIDs2[i_41_] = stream.readUnsignedWord();
            for (int i_42_ = 0; i_42_ < frameCount; i_42_++)
                frameIDs2[i_42_] = (stream.readUnsignedWord() << 16) + frameIDs2[i_42_];
        } else if (opcode == 13) {
            int frameCount = stream.readUnsignedWord();
            int[][] anIntArrayArray776 = new int[frameCount][];
            for (int i = 0; i < frameCount; i++) {
                int soundCount = stream.readUnsignedByte();
                if (soundCount > 0) {
                    anIntArrayArray776[i] = new int[soundCount];
                    anIntArrayArray776[i][0] = stream.read3Bytes();
                    for (int j = 1; j < soundCount; j++)
                        anIntArrayArray776[i][j] = stream.readUnsignedWord();
                }
            }
        } else if (opcode == 14) {
            boolean aBoolean4207 = true;
        } else {
            throw new RuntimeException("Unrecognized seq.dat config code: " + opcode);
        }
    }
}

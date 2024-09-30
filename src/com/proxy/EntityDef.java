package com.proxy;

public final class EntityDef {

    public static int anInt56;
    public static Stream stream;
    public static int[] streamIndices;
    public static EntityDef[] cache;
    public static Client clientInstance;
    public static MRUNodes mruNodes = new MRUNodes(30);
    public final int anInt64;
    public int walkLeftAnim;
    public int anInt57;
    public int walkBackAnim;
    public int anInt59;
    public int combatLevel;
    public String name;
    public String actions[];
    public int walkAnim;
    public byte aByte68;
    public int[] anIntArray70;
    public int[] anIntArray73;
    public int anInt75;
    public int[] anIntArray76;
    public int readyAnim;
    public long interfaceType;
    public int anInt79;
    public int walkRightAnim;
    public boolean aBoolean84;
    public int anInt85;
    public int anInt86;
    public boolean aBoolean87;
    public int childrenIDs[];
    public byte description[];
    public int anInt91;
    public int anInt92;
    public boolean aBoolean93;
    public int[] anIntArray94;
    public int renderAnimationId = -1;

    public EntityDef() {
        walkLeftAnim = -1;
        anInt57 = -1;
        walkBackAnim = -1;
        anInt59 = -1;
        combatLevel = -1;
        anInt64 = 1834;
        walkAnim = -1;
        aByte68 = 1;
        anInt75 = -1;
        readyAnim = -1;
        interfaceType = -1L;
        anInt79 = 32;
        walkRightAnim = -1;
        aBoolean84 = true;
        anInt86 = 128;
        aBoolean87 = true;
        anInt91 = 128;
        aBoolean93 = false;
    }

    public static EntityDef forID(int i) {
        for (int j = 0; j < 20; j++)
            if (cache[j].interfaceType == (long) i)
                return cache[j];

        anInt56 = (anInt56 + 1) % 20;
        EntityDef entityDef = cache[anInt56] = new EntityDef();
        stream.currentOffset = streamIndices[i];
        entityDef.interfaceType = i;
        entityDef.readValues(stream);
        entityDef.postDecode();
        switch (i) {
            case 445: // 6282
                entityDef.actions = new String[5];
                entityDef.actions[1] = "Talk to";
                entityDef.name = "General Kobalt";
                entityDef.combatLevel = 0;
                break;
            case 1625:
                entityDef.name = "Smoke devil";
                entityDef.combatLevel = 160;
                entityDef.anIntArray94[0] = 28302;
                EntityDef smoke = forID(1624);
                entityDef.readyAnim = smoke.readyAnim;
                entityDef.walkAnim = smoke.walkAnim;
                entityDef.actions = smoke.actions;
                entityDef.anInt86 = smoke.anInt86;
                entityDef.anInt91 = smoke.anInt91;
                break;
            case 105:
                entityDef.name = "Callisto";
                entityDef.combatLevel = 470;
                entityDef.anIntArray94[0] = 28298;
                EntityDef callisto = forID(105);
                entityDef.readyAnim = callisto.readyAnim;
                entityDef.walkAnim = callisto.walkAnim;
                entityDef.actions = callisto.actions;
                entityDef.anInt86 = 70;
                entityDef.anInt91 = 60;
                break;
        }
        return entityDef;
    }

    public static void dumpInfo(int id) {
        try {
            EntityDef entityDef = EntityDef.forID(id);
            System.out.println("name : " + entityDef.name);
            System.out.println("modelID : " + entityDef.anIntArray94[0]);
            System.out.println("standAnim : " + entityDef.readyAnim);
            System.out.println("walkAnim : " + entityDef.walkAnim);
        } catch (Exception e) {

        }
    }

    public static void unpackConfig(StreamLoader streamLoader) {
        stream = new Stream(streamLoader.getDataForName("npc.dat"));
        Stream stream2 = new Stream(streamLoader.getDataForName("npc.idx"));
        int totalNPCs = stream2.readUnsignedWord();
        System.out.println("Loaded " + totalNPCs + " npcs");
        streamIndices = new int[totalNPCs];
        int i = 2;
        for (int j = 0; j < totalNPCs; j++) {
            streamIndices[j] = i;
            i += stream2.readUnsignedWord();
        }

        cache = new EntityDef[20];
        for (int k = 0; k < 20; k++)
            cache[k] = new EntityDef();
        for (int index = 0; index < totalNPCs; index++) {
            EntityDef ed = forID(index);
            if (ed == null)
                continue;
            if (ed.name == null)
                continue;
        }
    }

    public static void nullLoader() {
        mruNodes = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public Model method160() {
        if (childrenIDs != null) {
            EntityDef entityDef = getAlteredNPCDef();
            if (entityDef == null)
                return null;
            else
                return entityDef.method160();
        }
        if (anIntArray73 == null)
            return null;
        boolean flag1 = false;
        for (int i = 0; i < anIntArray73.length; i++)
            if (!Model.method463(anIntArray73[i]))
                flag1 = true;

        if (flag1)
            return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray73.length];
        for (int j = 0; j < anIntArray73.length; j++)
            aclass30_sub2_sub4_sub6s[j] = Model.method462(anIntArray73[j]);

        Model model;
        if (aclass30_sub2_sub4_sub6s.length == 1)
            model = aclass30_sub2_sub4_sub6s[0];
        else
            model = new Model(aclass30_sub2_sub4_sub6s.length,
                    aclass30_sub2_sub4_sub6s);
        if (anIntArray76 != null) {
            for (int k = 0; k < anIntArray76.length; k++)
                model.method476(anIntArray76[k], anIntArray70[k]);

        }
        return model;
    }

    public EntityDef getAlteredNPCDef() {
        int j = -1;
        if (anInt57 != -1 && anInt57 <= 2113) {
            VarBit varBit = VarBit.cache[anInt57];
            int k = varBit.anInt648;
            int l = varBit.anInt649;
            int i1 = varBit.anInt650;
            int j1 = Client.anIntArray1232[i1 - l];
            j = clientInstance.variousSettings[k] >> l & j1;
        } else if (anInt59 != -1)
            j = clientInstance.variousSettings[anInt59];
        if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1)
            return null;
        else
            return forID(childrenIDs[j]);
    }

    public Model method164(int j, int frame, int ai[], int nextFrame, int idk, int idk2) {
        if (childrenIDs != null) {
            EntityDef entityDef = getAlteredNPCDef();
            if (entityDef == null)
                return null;
            else
                return entityDef.method164(j, frame, ai, nextFrame, idk, idk2);
        }
        Model completedModel = (Model) mruNodes.insertFromCache(interfaceType);
        if (completedModel == null) {
            boolean flag = false;
            for (int i1 = 0; i1 < anIntArray94.length; i1++)
                if (!Model.method463(anIntArray94[i1]))
                    flag = true;

            if (flag)
                return null;
            Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray94.length];
            for (int j1 = 0; j1 < anIntArray94.length; j1++)
                aclass30_sub2_sub4_sub6s[j1] = Model
                        .method462(anIntArray94[j1]);

            if (aclass30_sub2_sub4_sub6s.length == 1)
                completedModel = aclass30_sub2_sub4_sub6s[0];
            else
                completedModel = new Model(aclass30_sub2_sub4_sub6s.length,
                        aclass30_sub2_sub4_sub6s);
            if (anIntArray76 != null) {
                for (int k1 = 0; k1 < anIntArray76.length; k1++)
                    completedModel.method476(anIntArray76[k1], anIntArray70[k1]);

            }
            completedModel.method469();
            //model.method479(64 + anInt85, 850 + anInt92, -30, -50, -30, true);
            completedModel.method479(84 + anInt85, 1000 + anInt92, -90, -580, -90, true);
            mruNodes.removeFromCache(completedModel, interfaceType);
        }
        Model animatedModel = Model.aModel_1621;
        animatedModel.method464(completedModel, FrameReader.isNullFrame(frame) & FrameReader.isNullFrame(j));

        if (frame != -1 && j != -1)
            animatedModel.method471(ai, j, frame);
        else if (frame != -1 && nextFrame != -1)
            animatedModel.applyTransform(frame, nextFrame, idk, idk2);
        else if (frame != -1)
            animatedModel.applyTransform(frame);

        if (anInt91 != 128 || anInt86 != 128)
            animatedModel.method478(anInt91, anInt91, anInt86);
        animatedModel.method466();
        animatedModel.triangleSkinTable = null;
        animatedModel.vertexSkinTable = null;
        if (aByte68 == 1)
            animatedModel.aBoolean1659 = true;
        return animatedModel;
    }

    public void readValues(Stream stream) {
        while (true) {
            int code = stream.readUnsignedByte();
            if (code == 0)
                return;
            decode(stream, code);
        }
    }

    private void decode(Stream stream, int i) {
        if (i == 1) {
            int j = stream.readUnsignedByte();
            anIntArray94 = new int[j];
            for (int j1 = 0; j1 < j; j1++) {
                anIntArray94[j1] = stream.readUnsignedWord();
                if (this.anIntArray94[j1] == '\uffff') {
                    this.anIntArray94[j1] = -1;
                }
            }
        } else if (i == 2)
            name = stream.readNewString();
        else if (i == 12)
            aByte68 = stream.readSignedByte();
        else if (i == 13)
            readyAnim = stream.readUnsignedWord();
        else if (i == 14)
            walkAnim = stream.readUnsignedWord();
        else if (i == 17) {
            walkAnim = stream.readUnsignedWord();
            walkBackAnim = stream.readUnsignedWord();
            walkRightAnim = stream.readUnsignedWord();
            walkLeftAnim = stream.readUnsignedWord();
        } else if (i >= 30 && i < 40) {
            if (actions == null)
                actions = new String[5];
            actions[i - 30] = stream.readNewString();
            if (actions[i - 30].equalsIgnoreCase("hidden"))
                actions[i - 30] = null;
        } else if (i == 40) {
            int k = stream.readUnsignedByte();
            anIntArray76 = new int[k];
            anIntArray70 = new int[k];
            for (int k1 = 0; k1 < k; k1++) {
                anIntArray76[k1] = stream.readUnsignedWord();
                anIntArray70[k1] = stream.readUnsignedWord();
            }
        } else if (i == 41) {
            int var4 = stream.readUnsignedByte();
            short[] aShortArray1246 = new short[var4];
            short[] aShortArray1271 = new short[var4];

            for (int var5 = 0; var5 < var4; ++var5) {
                aShortArray1271[var5] = (short) stream.readUnsignedWord();
                aShortArray1246[var5] = (short) stream.readUnsignedWord();
            }
        } else if (i == 42) {
            int var4 = stream.readUnsignedByte();
            byte[] aByteArray1247 = new byte[var4];

            for (int var5 = 0; var4 > var5; ++var5) {
                aByteArray1247[var5] = stream.readSignedByte();
            }
        } else if (i == 60) {
            int l = stream.readUnsignedByte();
            anIntArray73 = new int[l];
            for (int l1 = 0; l1 < l; l1++)
                anIntArray73[l1] = stream.readUnsignedWord();
        } else if (i == 93)
            aBoolean87 = false;
        else if (i == 95)
            combatLevel = stream.readUnsignedWord();
        else if (i == 97)
            anInt91 = stream.readUnsignedWord();
        else if (i == 98)
            anInt86 = stream.readUnsignedWord();
        else if (i == 99)
            aBoolean93 = true;
        else if (i == 100)
            anInt85 = stream.readSignedByte();
        else if (i == 101)
            anInt92 = stream.readSignedByte() * 5;
        else if (i == 102)
            anInt75 = stream.readUnsignedWord();
        else if (i == 103)
            anInt79 = stream.readUnsignedWord();
        else if (i == 106 || i == 118) {
            anInt57 = stream.readUnsignedWord();
            if (anInt57 == 65535)
                anInt57 = -1;
            anInt59 = stream.readUnsignedWord();
            if (anInt59 == 65535)
                anInt59 = -1;
            int defaultId = -1;
            if (i == 118) {
                defaultId = stream.readUnsignedWord();
                if (defaultId == 65535) {
                    defaultId = -1;
                }
            }
            int i1 = stream.readUnsignedByte();
            childrenIDs = new int[i1 + 2];
            for (int i2 = 0; i2 <= i1; i2++) {
                childrenIDs[i2] = stream.readUnsignedWord();
                if (childrenIDs[i2] == 65535)
                    childrenIDs[i2] = -1;
            }
            childrenIDs[i1 + 1] = defaultId;
        } else if (i == 107) {
            aBoolean84 = false;
        } else if (i == 109) {
            boolean aBoolean1255 = false;
        } else if (i == 111) {
            boolean spotshadowed = false;
        } else if (113 == i) {
            int aShort1286 = (short) stream.g2();
            int aShort1256 = (short) stream.g2();
        } else if (114 == i) {
            int aByte1287 = stream.readSignedByte();
            int aByte1275 = stream.readSignedByte();
        } else if (i == 115) {
            stream.readUnsignedByte();
            stream.readUnsignedByte();
        } else if (119 == i) {
            int aByte1267 = stream.readSignedByte();
        } else if (121 == i) {
            int var4 = stream.readUnsignedByte();
            for (int var5 = 0; var5 < var4; ++var5) {
                int var6 = stream.readUnsignedByte();
                stream.readSignedByte();
                stream.readSignedByte();
                stream.readSignedByte();
            }
        } else if (122 == i) {
            int anInt1279 = stream.g2();
        } else if (123 == i) {
            int anInt1265 = stream.g2();
        } else if (125 == i) {
            int defaultFaceDirection = stream.readSignedByte();
        } else if (126 == i) {
            int anInt1283 = stream.g2();
        } else if (127 == i) {
            renderAnimationId = stream.g2();
        } else if (128 == i) {
            stream.readUnsignedByte();
        } else if (i == 134) {
            int anInt1262 = stream.g2();
            if (anInt1262 == '\uffff') {
                anInt1262 = -1;
            }

            int anInt1290 = stream.g2();
            if (anInt1290 == 65535) {
                anInt1290 = -1;
            }

            int anInt1293 = stream.g2();
            if (65535 == anInt1293) {
                anInt1293 = -1;
            }

            int anInt1276 = stream.g2();
            if (65535 == anInt1276) {
                anInt1276 = -1;
            }

            int anInt1291 = stream.readUnsignedByte();
        } else if (i == 135) {
            int anInt1296 = stream.readUnsignedByte();
            int anInt1253 = stream.g2();
        } else if (i == 136) {
            int anInt1289 = stream.readUnsignedByte();
            int anInt1278 = stream.g2();
        } else if (137 == i) {
            int anInt1298 = stream.g2();
        } else if (i == 249) {
            int var4 = stream.readUnsignedByte();
            for (int var5 = 0; ~var5 > ~var4; ++var5) {
                boolean var11 = 1 == stream.readUnsignedByte();
                int var10 = stream.read3Bytes();
                Object var8;
                if (!var11) {
                    var8 = (stream.g4());
                } else {
                    var8 = (stream.readNewString());
                }
            }
        } else {
            throw new RuntimeException("Missing .npc config code: " + i);
        }
    }

    private void postDecode() {
    }
}

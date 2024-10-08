package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class ItemDef {

    public static MRUNodes mruNodes2 = new MRUNodes(50);
    public static boolean isMembers = true;
    public static int totalItems;
    static MRUNodes mruNodes1 = new MRUNodes(100);
    private static ItemDef[] cache;
    private static int cacheIndex;
    private static Stream stream;
    private static int[] streamIndices;
    public int value;// anInt155
    public int[] modifiedModelColors;// newModelColor
    public int id;// anInt157
    public int[] originalModelColors;
    public boolean membersObject;// aBoolean161
    public int anInt164;// femArmModel
    public int anInt165;// maleWieldModel
    public String groundActions[];
    public int modelOffset1;
    public String name;// itemName
    public int modelID;// dropModel
    public int anInt175;
    public boolean stackable;// itemStackable
    public byte description[];// itemExamine
    public int certID;
    public int modelZoom;
    public int anInt188;// maleArmModel
    public String itemActions[];// itemMenuOption
    public int modelRotationY;// modelRotateUp
    public int[] stackIDs;// modelStack
    public int modelOffset2;//
    public int anInt197;
    public int modelRotationX;// modelRotateRight
    public int anInt200;// femWieldModel
    public int[] stackAmounts;// itemAmount
    public int team;
    public int anInt204;// modelPositionUp
    private byte aByte154;
    private int anInt162;
    private int certTemplateID;
    private int anInt166;
    private int sizeX;
    private int anInt173;
    private int anInt184;
    private int anInt185;
    private int sizeZ;
    private int sizeY;
    private int anInt196;
    private byte aByte205;

    private ItemDef() {
        id = -1;
    }

    public static void nullLoader() {
        mruNodes2 = null;
        mruNodes1 = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public static void unpackConfig(StreamLoader archive) {
        stream = new Stream(archive.getDataForName("obj.dat"));
        Stream stream = new Stream(archive.getDataForName("obj.idx"));
        totalItems = stream.readUnsignedWord();
        System.out.println("Loaded " + totalItems + " items");
        streamIndices = new int[totalItems];
        int i = 2;
        for (int j = 0; j < totalItems; j++) {
            streamIndices[j] = i;
            i += stream.readUnsignedWord();
        }

        cache = new ItemDef[10];
        for (int k = 0; k < 10; k++)
            cache[k] = new ItemDef();

    }

    public static ItemDef forID(int i) {
        for (int j = 0; j < 10; j++)
            if (cache[j].id == i)
                return cache[j];

        cacheIndex = (cacheIndex + 1) % 10;
        ItemDef itemDef = cache[cacheIndex];
        stream.currentOffset = streamIndices[i];
        itemDef.id = i;
        itemDef.setDefaults();
        itemDef.readValues(stream);
        // ItemDef_2.Items(i);
        /* Customs added here? */
        switch (i) {

            case 12100: // dragon defender
                itemDef.itemActions = new String[5];
                itemDef.itemActions[1] = "Wear";
                itemDef.originalModelColors = new int[2];
                itemDef.modifiedModelColors = new int[2];
                itemDef.originalModelColors[0] = 920;
                itemDef.modifiedModelColors[0] = 28;
                itemDef.originalModelColors[1] = 926;
                itemDef.modifiedModelColors[1] = 74;
                itemDef.modelID = 15335;
                itemDef.modelZoom = 490;
                itemDef.modelRotationY = 344;
                itemDef.modelRotationX = 192;
                itemDef.modelOffset1 = 1;
                itemDef.modelOffset2 = 20;
                itemDef.anInt165 = 15413;
                itemDef.anInt200 = 15413;
                itemDef.name = "Dragon Defender";
                itemDef.description = "It's a Dragon Defender.".getBytes();
                break;

            case 6617: // dragon plate
                itemDef.name = "Dragon Platebody";
                itemDef.description = "It's a Dragon Platebody.".getBytes();
                itemDef.originalModelColors = new int[3];
                itemDef.modifiedModelColors = new int[3];
                itemDef.originalModelColors[0] = 920;
                itemDef.modifiedModelColors[0] = 24;
                itemDef.originalModelColors[1] = 926;
                itemDef.modifiedModelColors[1] = 61;
                itemDef.originalModelColors[2] = 930;
                itemDef.modifiedModelColors[2] = 41;
                break;
            case 6618: // noted
                itemDef.name = "Dragon Platebody";
                itemDef.description = "Swap this note at any bank for a Dragon Platebody.".getBytes();
                itemDef.certID = 6617;
                itemDef.certTemplateID = 799;
                itemDef.stackable = true;
                break;

            // whips
            case 12000:
                itemDef.itemActions = new String[5];
                itemDef.itemActions[1] = "Wear";
                itemDef.originalModelColors = new int[1];
                itemDef.modifiedModelColors = new int[1];
                itemDef.originalModelColors[0] = 926;
                itemDef.modifiedModelColors[0] = 528;
                itemDef.modelID = 5412;
                itemDef.modelZoom = 840;
                itemDef.modelRotationY = 280;
                itemDef.modelRotationX = 0;
                itemDef.modelOffset1 = -2;
                itemDef.modelOffset2 = 56;
                itemDef.anInt165 = 5409;
                itemDef.anInt200 = 5409;
                itemDef.name = "Abyssal Dragon Whip";
                itemDef.description = "It's a Abyssal Dragon Whip.".getBytes();
                break;
            case 12001: // noted
                itemDef.certID = 12000;
                itemDef.certTemplateID = 799;
                itemDef.stackable = true;
                break;

//		case 4155:
////			itemDef.anInt377 = 1860;
//		break;
            case 6804: //extreme
                itemDef.name = "@gre@Extreme Donator Exchange";
                itemDef.description = "Receive your extreme donator status." //recieve receive
                        .getBytes();
                break;
            case 6805: //normal donor
                itemDef.name = "@red@Donator Exchange";
                itemDef.description = "Receive your donator status." //recieve receive
                        .getBytes();
                break;
            case 6807: //super donator
                itemDef.name = "@blu@Super Donator Exchange";
                itemDef.description = "Receive your super donator status." //recieve receive
                        .getBytes();
                break;
            case 702:
                itemDef.name = "Occult necklace";
                itemDef.description = "A satanic evil embodies this amulet."
                        .getBytes();
                itemDef.anInt200 = 7824;
                itemDef.anInt165 = 7824;
                itemDef.modelID = 7825;
                itemDef.modelZoom = 240;
                itemDef.modelRotationY = 265;
                itemDef.modelRotationX = 50;
                itemDef.modelOffset1 = 2;
                itemDef.modelOffset2 = 230;
                itemDef.itemActions = new String[5];
                itemDef.itemActions[1] = "Wield";
                break;
//        case 704:
//     itemDef.name = "Trident of the seas";
//     itemDef.description = "A weapon from the deep."
//     .getBytes();
//     itemDef.anInt200 = 1052;
//     itemDef.anInt165 = 1052;
//     itemDef.modelID = 1051;
//     itemDef.modelRotationY = 420;
//     itemDef.modelRotationX = 1130;
//     itemDef.modelZoom = 2755;
//     itemDef.modelOffset2 = 0;
//     itemDef.modelOffset1 = 0;
//     itemDef.itemActions = new String[5];
//     itemDef.itemActions[1] = "Wield";
//     itemDef.itemActions[3] = "Check";
//     break;
            case 700:
                itemDef.name = "Abyssal tentacle";
                itemDef.description = "A weapon from the abyss, embedded in a slimy tentacle."
                        .getBytes();
                itemDef.modelID = 4185;
                itemDef.modelZoom = 913;
                itemDef.modelRotationY = 304;
                itemDef.modelRotationX = 148;
                itemDef.modelOffset1 = 0;
                itemDef.modelOffset2 = 3;
                itemDef.anInt165 = 4186;
                itemDef.anInt200 = 4186;
                itemDef.itemActions = new String[5];
                itemDef.itemActions[1] = "Wield";
                itemDef.itemActions[3] = "Check";
                itemDef.itemActions[4] = "Dissolve";
                break;
            case 708:
                itemDef.name = "Mystic smoke staff";
                itemDef.description = "It's a slightly magical stick."
                        .getBytes();
                itemDef.anInt200 = 28301;
                itemDef.anInt165 = 28301;
                itemDef.modelID = 28300;
                itemDef.modelRotationY = 360;
                itemDef.modelRotationX = 1550;
                itemDef.modelZoom = 2905;
                itemDef.modelOffset2 = -1;
                itemDef.modelOffset1 = -4;
                itemDef.itemActions = new String[5];
                itemDef.itemActions[1] = "Wield";
                break;
            case 706:
                itemDef.name = "Staff of the dead";
                itemDef.itemActions = new String[5];
                itemDef.itemActions[1] = "Wield";
                itemDef.description = "A ghastly weapon with evil origins.".getBytes();
                itemDef.modelID = 2810;
                itemDef.anInt165 = 5232;
                itemDef.anInt200 = 5232;
                itemDef.modelRotationY = 148;
                itemDef.modelRotationX = 1300;
                itemDef.modelZoom = 1420;
                itemDef.modelOffset1 = -5;
                itemDef.modelOffset2 = 2;
                break;
            case 2568:
                itemDef.itemActions[2] = "Check charges";
                break;
        }

        if (itemDef.certTemplateID != -1)
            itemDef.toNote();
        return itemDef;
    }

    public static void dumpInfo(int item) {
        try {
            ItemDef itemDef = ItemDef.forID(item);
            try {
                System.out.println("color to edit0 =" + itemDef.modifiedModelColors[0]);
            } catch (Exception e3) {
                // TODO Auto-generated catch block
                e3.printStackTrace();
            }
            try {
                System.out.println("color to edit1 =" + itemDef.modifiedModelColors[1]);
            } catch (Exception e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            try {
                System.out.println("color to edit2 =" + itemDef.modifiedModelColors[2]);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                System.out.println("color to edit3 =" + itemDef.modifiedModelColors[3]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("name : " + itemDef.name);
            System.out.println("modelID : " + itemDef.modelID);
            System.out.println("modelZoom : " + itemDef.modelZoom);
            System.out.println("modelRotationY : " + itemDef.modelRotationY);
            System.out.println("modelRotationX : " + itemDef.modelRotationX);
            System.out.println("modelOffset1 : " + itemDef.modelOffset1);
            System.out.println("modelOffset2 : " + itemDef.modelOffset2);
            System.out.println("anInt165(MALE WIELD) : " + itemDef.anInt165);
            System.out.println("anInt200(FEMALE WIELD) : " + itemDef.anInt200);
			/*System.out.println();
			System.out.println("Name : "+itemDef.name);
			System.out.println("Model : " + itemDef.modelID);
			System.out.println("Zoom : " + itemDef.modelZoom);
			System.out.println("RotY : " + itemDef.modelRotationY);
			System.out.println("RotX : " + itemDef.modelRotationX);
			System.out.println("Offset1 : "+itemDef.modelOffset1);
			System.out.println("Offset2 : "+itemDef.modelOffset2);
			System.out.println("anInt165(MALE WIELD) : "+itemDef.anInt165);
			System.out.println("anInt200(FEMALE WIELD) : "+itemDef.anInt200);
			System.out.println();
			System.out.println("Original Color[0] : "
					+ itemDef.originalModelColors[0]);
			System.out.println("Modified Color[0] : "
					+ itemDef.modifiedModelColors[0]);
			System.out.println("Original Color[1] : "
					+ itemDef.originalModelColors[1]);
			System.out.println("Modified Color[1] : "
					+ itemDef.modifiedModelColors[1]);
			System.out.println("Original Color[2] : "
					+ itemDef.originalModelColors[2]);
			System.out.println("Modified Color[2] : "
					+ itemDef.modifiedModelColors[2]);
			System.out.println();
			System.out.println("itemAction0 : "+itemDef.itemActions[0]);
			System.out.println("itemAction1 : "+itemDef.itemActions[1]);
			System.out.println("itemAction2 : "+itemDef.itemActions[2]);
			System.out.println("itemAction3 : "+itemDef.itemActions[3]);
			System.out.println("itemAction4 : "+itemDef.itemActions[4]);*/
        } catch (Exception e) {

        }
    }

    public static Sprite getSprite(int i, int j, int k) {
        if (k == 0) {
            Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);
            if (sprite != null && sprite.maxHeight != j
                    && sprite.maxHeight != -1) {
                sprite.unlink();
                sprite = null;
            }
            if (sprite != null)
                return sprite;
        }
        ItemDef itemDef = forID(i);
        if (itemDef.stackIDs == null)
            j = -1;
        if (j > 1) {
            int i1 = -1;
            for (int j1 = 0; j1 < 10; j1++)
                if (j >= itemDef.stackAmounts[j1]
                        && itemDef.stackAmounts[j1] != 0)
                    i1 = itemDef.stackIDs[j1];

            if (i1 != -1)
                itemDef = forID(i1);
        }
        Model model = itemDef.method201(1);
        if (model == null)
            return null;
        Sprite sprite = null;
        if (itemDef.certTemplateID != -1) {
            sprite = getSprite(itemDef.certID, 10, -1);
            if (sprite == null)
                return null;
        }
        Sprite enabledSprite = new Sprite(32, 32);
        int k1 = Rasterizer.originX;
        int l1 = Rasterizer.originY;
        int ai[] = Rasterizer.rowOffsets;
        int ai1[] = DrawingArea.pixels;
        int i2 = DrawingArea.width;
        int j2 = DrawingArea.height;
        int k2 = DrawingArea.topX;
        int l2 = DrawingArea.bottomX;
        int i3 = DrawingArea.topY;
        int j3 = DrawingArea.bottomY;
        Rasterizer.smooth_edges = false;
        DrawingArea.initDrawingArea(32, 32, enabledSprite.myPixels);
        DrawingArea.method336(32, 0, 0, 0, 32);
        Rasterizer.method364();
        int k3 = itemDef.modelZoom;
        if (k == -1)
            k3 = (int) ((double) k3 * 1.5D);
        if (k > 0)
            k3 = (int) ((double) k3 * 1.04D);
        int l3 = Rasterizer.SINE[itemDef.modelRotationY] * k3 >> 16;
        int i4 = Rasterizer.COSINE[itemDef.modelRotationY] * k3 >> 16;
        model.method482(itemDef.modelRotationX, itemDef.anInt204,
                itemDef.modelRotationY, itemDef.modelOffset1, l3
                        + model.modelHeight / 2 + itemDef.modelOffset2, i4
                        + itemDef.modelOffset2);
        for (int i5 = 31; i5 >= 0; i5--) {
            for (int j4 = 31; j4 >= 0; j4--)
                if (enabledSprite.myPixels[i5 + j4 * 32] == 0)
                    if (i5 > 0
                            && enabledSprite.myPixels[(i5 - 1) + j4 * 32] > 1)
                        enabledSprite.myPixels[i5 + j4 * 32] = 1;
                    else if (j4 > 0
                            && enabledSprite.myPixels[i5 + (j4 - 1) * 32] > 1)
                        enabledSprite.myPixels[i5 + j4 * 32] = 1;
                    else if (i5 < 31
                            && enabledSprite.myPixels[i5 + 1 + j4 * 32] > 1)
                        enabledSprite.myPixels[i5 + j4 * 32] = 1;
                    else if (j4 < 31
                            && enabledSprite.myPixels[i5 + (j4 + 1) * 32] > 1)
                        enabledSprite.myPixels[i5 + j4 * 32] = 1;

        }

        if (k > 0) {
            for (int j5 = 31; j5 >= 0; j5--) {
                for (int k4 = 31; k4 >= 0; k4--)
                    if (enabledSprite.myPixels[j5 + k4 * 32] == 0)
                        if (j5 > 0
                                && enabledSprite.myPixels[(j5 - 1) + k4 * 32] == 1)
                            enabledSprite.myPixels[j5 + k4 * 32] = k;
                        else if (k4 > 0
                                && enabledSprite.myPixels[j5 + (k4 - 1) * 32] == 1)
                            enabledSprite.myPixels[j5 + k4 * 32] = k;
                        else if (j5 < 31
                                && enabledSprite.myPixels[j5 + 1 + k4 * 32] == 1)
                            enabledSprite.myPixels[j5 + k4 * 32] = k;
                        else if (k4 < 31
                                && enabledSprite.myPixels[j5 + (k4 + 1) * 32] == 1)
                            enabledSprite.myPixels[j5 + k4 * 32] = k;

            }

        } else if (k == 0) {
            for (int k5 = 31; k5 >= 0; k5--) {
                for (int l4 = 31; l4 >= 0; l4--)
                    if (enabledSprite.myPixels[k5 + l4 * 32] == 0
                            && k5 > 0
                            && l4 > 0
                            && enabledSprite.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
                        enabledSprite.myPixels[k5 + l4 * 32] = 0x302020;

            }

        }
        if (itemDef.certTemplateID != -1) {
            int l5 = sprite.maxWidth;
            int j6 = sprite.maxHeight;
            sprite.maxWidth = 32;
            sprite.maxHeight = 32;
            sprite.drawSprite(0, 0);
            sprite.maxWidth = l5;
            sprite.maxHeight = j6;
        }
        if (k == 0)
            mruNodes1.removeFromCache(enabledSprite, i);
        DrawingArea.initDrawingArea(j2, i2, ai1);
        DrawingArea.setDrawingArea(k2, i3, l2, j3);
        Rasterizer.originX = k1;
        Rasterizer.originY = l1;
        Rasterizer.rowOffsets = ai;
        Rasterizer.smooth_edges = true;
        if (itemDef.stackable)
            enabledSprite.maxWidth = 33;
        else
            enabledSprite.maxWidth = 32;
        enabledSprite.maxHeight = j;
        return enabledSprite;
    }

    public boolean method192(int j) {
        int k = anInt175;
        int l = anInt166;
        if (j == 1) {
            k = anInt197;
            l = anInt173;
        }
        if (k == -1)
            return true;
        boolean flag = true;
        if (!Model.method463(k))
            flag = false;
        if (l != -1 && !Model.method463(l))
            flag = false;
        return flag;
    }

    public Model method194(int j) {
        int k = anInt175;
        int l = anInt166;
        if (j == 1) {
            k = anInt197;
            l = anInt173;
        }
        if (k == -1)
            return null;
        Model model = Model.method462(k);
        if (l != -1) {
            Model model_1 = Model.method462(l);
            Model aclass30_sub2_sub4_sub6s[] = {model, model_1};
            model = new Model(2, aclass30_sub2_sub4_sub6s);
        }
        if (modifiedModelColors != null) {
            for (int i1 = 0; i1 < modifiedModelColors.length; i1++)
                model.method476(modifiedModelColors[i1],
                        originalModelColors[i1]);

        }
        return model;
    }

    public boolean method195(int j) {
        int k = anInt165;
        int l = anInt188;
        int i1 = anInt185;
        if (j == 1) {
            k = anInt200;
            l = anInt164;
            i1 = anInt162;
        }
        if (k == -1)
            return true;
        boolean flag = true;
        if (!Model.method463(k))
            flag = false;
        if (l != -1 && !Model.method463(l))
            flag = false;
        if (i1 != -1 && !Model.method463(i1))
            flag = false;
        return flag;
    }

    public Model method196(int i) {
        int j = anInt165;
        int k = anInt188;
        int l = anInt185;
        if (i == 1) {
            j = anInt200;
            k = anInt164;
            l = anInt162;
        }
        if (j == -1)
            return null;
        Model model = Model.method462(j);
        if (k != -1)
            if (l == -1) {
                Model model_2 = Model.method462(k);
                Model aclass30_sub2_sub4_sub6s[] = {model, model_2};
                model = new Model(2, aclass30_sub2_sub4_sub6s);
            } else {
                Model model_1 = Model.method462(k);
                Model model_3 = Model.method462(l);
                Model aclass30_sub2_sub4_sub6_1s[] = {model, model_1, model_3};
                model = new Model(3, aclass30_sub2_sub4_sub6_1s);
            }
        if (i == 0 && aByte205 != 0)
            model.method475(0, aByte205, 0);
        if (i == 1 && aByte154 != 0)
            model.method475(0, aByte154, 0);
        if (modifiedModelColors != null) {
            for (int i1 = 0; i1 < modifiedModelColors.length; i1++)
                model.method476(modifiedModelColors[i1],
                        originalModelColors[i1]);

        }
        return model;
    }

    private void setDefaults() {
        modelID = 0;
        name = null;
        description = null;
        modifiedModelColors = null;
        originalModelColors = null;
        modelZoom = 2000;
        modelRotationY = 0;
        modelRotationX = 0;
        anInt204 = 0;
        modelOffset1 = 0;
        modelOffset2 = 0;
        stackable = false;
        value = 1;
        membersObject = false;
        groundActions = null;
        itemActions = null;
        anInt165 = -1;
        anInt188 = -1;
        aByte205 = 0;
        anInt200 = -1;
        anInt164 = -1;
        aByte154 = 0;
        anInt185 = -1;
        anInt162 = -1;
        anInt175 = -1;
        anInt166 = -1;
        anInt197 = -1;
        anInt173 = -1;
        stackIDs = null;
        stackAmounts = null;
        certID = -1;
        certTemplateID = -1;
        sizeX = 128;
        sizeY = 128;
        sizeZ = 128;
        anInt196 = 0;
        anInt184 = 0;
        team = 0;
    }

    private void toNote() {
        ItemDef itemDef = forID(certTemplateID);
        modelID = itemDef.modelID;
        modelZoom = itemDef.modelZoom;
        modelRotationY = itemDef.modelRotationY;
        modelRotationX = itemDef.modelRotationX;

        anInt204 = itemDef.anInt204;
        modelOffset1 = itemDef.modelOffset1;
        modelOffset2 = itemDef.modelOffset2;
        modifiedModelColors = itemDef.modifiedModelColors;
        originalModelColors = itemDef.originalModelColors;
        ItemDef itemDef_1 = forID(certID);
        name = itemDef_1.name;
        membersObject = itemDef_1.membersObject;
        value = itemDef_1.value;
        String s = "a";
        char c = itemDef_1.name.charAt(0);
        if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
            s = "an";
        description = ("Swap this note at any bank for " + s + " "
                + itemDef_1.name + ".").getBytes();
        stackable = true;
    }

    public Model method201(int i) {
        if (stackIDs != null && i > 1) {
            int j = -1;
            for (int k = 0; k < 10; k++)
                if (i >= stackAmounts[k] && stackAmounts[k] != 0)
                    j = stackIDs[k];

            if (j != -1)
                return forID(j).method201(1);
        }
        Model model = (Model) mruNodes2.insertFromCache(id);
        if (model != null)
            return model;
        model = Model.method462(modelID);
        if (model == null)
            return null;
        if (sizeX != 128 || sizeY != 128 || sizeZ != 128)
            model.method478(sizeX, sizeZ, sizeY);
        if (modifiedModelColors != null) {
            for (int l = 0; l < modifiedModelColors.length; l++)
                model.method476(modifiedModelColors[l], originalModelColors[l]);

        }
        model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
        model.aBoolean1659 = true;
        mruNodes2.removeFromCache(model, id);
        return model;
    }

    public Model method202(int i) {
        if (stackIDs != null && i > 1) {
            int j = -1;
            for (int k = 0; k < 10; k++)
                if (i >= stackAmounts[k] && stackAmounts[k] != 0)
                    j = stackIDs[k];

            if (j != -1)
                return forID(j).method202(1);
        }
        Model model = Model.method462(modelID);
        if (model == null)
            return null;
        if (modifiedModelColors != null) {
            for (int l = 0; l < modifiedModelColors.length; l++)
                model.method476(modifiedModelColors[l], originalModelColors[l]);

        }
        return model;
    }

    private void readValues(Stream stream) {
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            if (i == 1)
                modelID = stream.readUnsignedWord();
            else if (i == 2)
                name = stream.readNewString();
            else if (i == 4)
                modelZoom = stream.readUnsignedWord();
            else if (i == 5)
                modelRotationY = stream.readUnsignedWord();
            else if (i == 6)
                modelRotationX = stream.readUnsignedWord();
            else if (i == 7) {
                modelOffset1 = stream.readUnsignedWord();
                if (modelOffset1 > 32767)
                    modelOffset1 -= 0x10000;
            } else if (i == 8) {
                modelOffset2 = stream.readUnsignedWord();
                if (modelOffset2 > 32767)
                    modelOffset2 -= 0x10000;
            } else if (i == 11)
                stackable = true;
            else if (i == 12)
                value = stream.readDWord();
            else if (i == 16)
                membersObject = true;
            else if (i == 23) {
                anInt165 = stream.readUnsignedWord();
            } else if (i == 24)
                anInt188 = stream.readUnsignedWord();
            else if (i == 25) {
                anInt200 = stream.readUnsignedWord();
            } else if (i == 26)
                anInt164 = stream.readUnsignedWord();
            else if (i >= 30 && i < 35) {
                if (groundActions == null)
                    groundActions = new String[5];
                groundActions[i - 30] = stream.readNewString();
                if (groundActions[i - 30].equalsIgnoreCase("hidden"))
                    groundActions[i - 30] = null;
            } else if (i >= 35 && i < 40) {
                if (itemActions == null)
                    itemActions = new String[5];
                itemActions[i - 35] = stream.readNewString();
            } else if (i == 40) {
                int j = stream.readUnsignedByte();
                modifiedModelColors = new int[j];
                originalModelColors = new int[j];
                for (int k = 0; k < j; k++) {
                    modifiedModelColors[k] = stream.readUnsignedWord();
                    originalModelColors[k] = stream.readUnsignedWord();
                }
            } else if (i == 41) {
                int texLen = stream.readUnsignedByte();
                short[] origTexture = new short[texLen];
                short[] modifiedTexture = new short[texLen];
                for (int integ = 0; integ < texLen; integ++) {
                    origTexture[integ] = (short) stream.readUnsignedWord();
                    modifiedTexture[integ] = (short) stream.readUnsignedWord();
                }
            } else if (42 == i) {
                int var5 = stream.readUnsignedByte();
                byte[] aByteArray785 = new byte[var5];

                for (int var6 = 0; ~var6 > ~var5; ++var6) {
                    aByteArray785[var6] = stream.readSignedByte();
                }
            } else if (i == 65) {
                boolean aBoolean807 = true;
            } else if (i == 78)
                anInt185 = stream.readUnsignedWord();
            else if (i == 79)
                anInt162 = stream.readUnsignedWord();
            else if (i == 90)
                anInt175 = stream.readUnsignedWord();
            else if (i == 91)
                anInt197 = stream.readUnsignedWord();
            else if (i == 92)
                anInt166 = stream.readUnsignedWord();
            else if (i == 93)
                anInt173 = stream.readUnsignedWord();
            else if (i == 95)
                anInt204 = stream.readUnsignedWord();
            else if (i== 96) {
                int anInt800 = stream.readUnsignedByte();
            }else if (i == 97)
                certID = stream.readUnsignedWord();
            else if (i == 98)
                certTemplateID = stream.readUnsignedWord();
            else if (i >= 100 && i < 110) {
                if (stackIDs == null) {
                    stackIDs = new int[10];
                    stackAmounts = new int[10];
                }
                stackIDs[i - 100] = stream.readUnsignedWord();
                stackAmounts[i - 100] = stream.readUnsignedWord();
            } else if (i == 110)
                sizeX = stream.readUnsignedWord();
            else if (i == 111)
                sizeY = stream.readUnsignedWord();
            else if (i == 112)
                sizeZ = stream.readUnsignedWord();
            else if (i == 113)
                anInt196 = stream.readSignedByte();
            else if (i == 114)
                anInt184 = stream.readSignedByte() * 5;
            else if (i == 115) {
                team = stream.readUnsignedByte();
            } else if (i == 121) {
                int anInt795 = stream.readUnsignedWord();
            } else if (i == 122) {
                int anInt762 = stream.readUnsignedWord();
            } else if (125 == i) {
                aByte205 = stream.readSignedByte();
                int anInt778 = stream.readSignedByte();
                int anInt775 = stream.readSignedByte();
            } else if (i == 126) {
                aByte154 = stream.readSignedByte();
                int anInt802 = stream.readSignedByte();
                int anInt752 = stream.readSignedByte();
            } else if (i == 127) {
                int anInt767 = stream.readUnsignedByte();
                int anInt758 = stream.readUnsignedWord();
            } else if (i == 128) {
                int anInt788 = stream.readUnsignedByte();
                int anInt756 = stream.readUnsignedWord();
            } else if (i == 129) {
                stream.readUnsignedByte();
                stream.readUnsignedWord();
            } else if (i == 130) {
                stream.readUnsignedByte();
                stream.readUnsignedWord();
            } else if (i == 249) {
                int len = stream.readUnsignedByte();
                for (int j = 0; j < len; j++) {
                    boolean var7 = stream.readUnsignedByte() == 1;
                    int var8 = stream.read3Bytes();
                    if (var7) {
                        stream.readString();
                    } else {
                        stream.g4();
                    }
                }
            } else {
                throw new RuntimeException("Missing Opcode " + i);
            }
        } while (true);
    }

}

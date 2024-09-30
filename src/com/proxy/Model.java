package com.proxy;

public final class Model extends Animable {
    public static final Model aModel_1621 = new Model();
    public static final int[] anIntArray1688 = new int[1000];
    private static final int[] anIntArray3919 = new int[10];
    private static final int[] anIntArray3925 = new int[10];
    private static final int[] anIntArray3936 = new int[10];
    public static boolean aBoolean1684;
    public static int anInt1685;
    public static int anInt1686;
    public static int anInt1687;
    public static int SINE[];
    public static int COSINE[];
    private static int[] anIntArray1622 = new int[2000];
    private static int[] anIntArray1623 = new int[2000];
    private static int[] anIntArray1624 = new int[2000];
    private static int[] anIntArray1625 = new int[2000];
    private static Class21[] aClass21Array1661;
    private static OnDemandFetcherParent aOnDemandFetcherParent_1662;
    private static boolean[] aBooleanArray1663 = new boolean[4096];
    private static boolean[] aBooleanArray1664 = new boolean[4096];
    private static int[] projected_vertices_x = new int[4096];
    private static int[] projected_vertices_y = new int[4096];
    private static int[] anIntArray1667 = new int[4096];
    private static int[] camera_vertex_x = new int[4096];
    private static int[] camera_vertex_y = new int[4096];
    private static int[] camera_vertex_z = new int[4096];
    private static int[] anIntArray1671 = new int[1500];
    private static int[][] anIntArrayArray1672 = new int[1500][512];
    private static int[] anIntArray1673 = new int[12];
    private static int[][] anIntArrayArray1674 = new int[12][2000];
    private static int[] anIntArray1675 = new int[2000];
    private static int[] anIntArray1676 = new int[2000];
    private static int[] anIntArray1677 = new int[12];
    private static int anInt1681;
    private static int anInt1682;
    private static int anInt1683;
    private static int[] hslToRgbTable;
    private static int[] modelIntArray4;

    static {
        SINE = Rasterizer.SINE;
        COSINE = Rasterizer.COSINE;
        hslToRgbTable = Rasterizer.HSLtoRGB;
        modelIntArray4 = Rasterizer.anIntArray841;
    }

    public FaceNormal[] faceNormal;
    public int vertexCount;
    public int vertexX[];
    public int vertexY[];
    public int vertexZ[];
    public int faceCount;
    public int facesA[];
    public int facesB[];
    public int facesC[];
    public int faceType[];
    public int faceColour[];
    public int anInt1646;
    public int anInt1647;
    public int anInt1648;
    public int anInt1649;
    public int anInt1650;
    public int anInt1651;
    public int anInt1654;
    public int vertexSkinTable[][];
    public int triangleSkinTable[][];
    public boolean aBoolean1659;
    byte[] facesMapping = null;
    short[] faceMaterial = null;
    byte[] texturesMapping = null;
    VertexNormal sharedNormals[];
    private int[] faceColourA;
    private int[] faceColourB;
    private int[] faceColourC;
    private int[] faceLayer;
    private int[] faceTrans;
    private int priority;
    private int textureCount;
    private int[] texturesMappingP;
    private int[] texturesMappingM;
    private int[] texturesMappingN;
    private int anInt1652;
    private int anInt1653;
    private int[] vertexSkin;
    private int[] faceLabel;

    private Model() {
        aBoolean1659 = false;
    }

    private Model(int modelId) {
        byte[] is = aClass21Array1661[modelId].aByteArray368;
        if (is[is.length - 1] == -1 && is[is.length - 2] == -1)
            method1041(is);
        else
            method1067(is);
        postLoad();
    }

    public Model(int submeshCount, Model[] submeshes) {
        aBoolean1659 = false;
        boolean hasFaceType = false;
        boolean hasFaceLayer = false;
        boolean hasFaceTrans = false;
        boolean hasFaceLabel = false;
        boolean hasFaceMaterial = false;
        boolean hasFaceMapping = false;

        boolean hasLitColours = false;

        vertexCount = 0;
        faceCount = 0;
        textureCount = 0;
        priority = -1;
        for (int i = 0; i < submeshCount; i++) {
            Model mesh = submeshes[i];
            if (mesh == null) {
                continue;
            }
            vertexCount += mesh.vertexCount;
            faceCount += mesh.faceCount;
            textureCount += mesh.textureCount;
            hasFaceType |= mesh.faceType != null;
            if (mesh.faceLayer != null) {
                hasFaceLayer = true;
            } else {
                if (priority == -1)
                    priority = mesh.priority;
                if (priority != mesh.priority)
                    hasFaceLayer = true;
            }
            hasFaceTrans |= mesh.faceTrans != null;
            hasFaceLabel |= mesh.faceLabel != null;
            hasFaceMaterial |= mesh.faceMaterial != null;
            hasFaceMapping |= mesh.facesMapping != null;
            hasLitColours |= mesh.faceColourA != null;
        }

        vertexX = new int[vertexCount];
        vertexY = new int[vertexCount];
        vertexZ = new int[vertexCount];
        vertexSkin = new int[vertexCount];
        facesA = new int[faceCount];
        facesB = new int[faceCount];
        facesC = new int[faceCount];
        if (textureCount > 0) {
            texturesMapping = new byte[textureCount];
            texturesMappingP = new int[textureCount];
            texturesMappingM = new int[textureCount];
            texturesMappingN = new int[textureCount];
        }
        if (hasFaceType) {
            faceType = new int[faceCount];
        }
        if (hasFaceLayer) {
            faceLayer = new int[faceCount];
        }
        if (hasFaceTrans) {
            faceTrans = new int[faceCount];
        }
        if (hasFaceLabel) {
            faceLabel = new int[faceCount];
        }
        if (hasFaceMaterial) {
            faceMaterial = new short[faceCount];
        }
        if (hasFaceMapping) {
            facesMapping = new byte[faceCount];
        }
        faceColour = new int[faceCount];
        if (hasLitColours) {
            faceColourA = new int[faceCount];
            faceColourB = new int[faceCount];
            faceColourC = new int[faceCount];
        }
        vertexCount = 0;
        faceCount = 0;
        textureCount = 0;
        for (int i = 0; i < submeshCount; i++) {
            Model submesh = submeshes[i];
            if (submesh == null) {
                continue;
            }
            for (int face = 0; face < submesh.faceCount; face++) {
                if (hasFaceType) {
                    if (submesh.faceType == null) {
                        faceType[faceCount] = 0;
                    } else {
                        faceType[faceCount] = submesh.faceType[face];
                    }
                }
                if (hasFaceLayer) {
                    if (submesh.faceLayer == null) {
                        faceLayer[faceCount] = submesh.priority;
                    } else {
                        faceLayer[faceCount] = submesh.faceLayer[face];
                    }
                }
                if (hasFaceTrans) {
                    if (submesh.faceTrans == null) {
                        faceTrans[faceCount] = 0;
                    } else {
                        faceTrans[faceCount] = submesh.faceTrans[face];
                    }
                }
                if (hasFaceLabel && submesh.faceLabel != null) {
                    faceLabel[faceCount] = submesh.faceLabel[face];
                }
                if (hasFaceMaterial) {
                    if (submesh.faceMaterial != null) {
                        faceMaterial[faceCount] = submesh.faceMaterial[face];
                    } else {
                        faceMaterial[faceCount] = -1;
                    }
                }
                if (hasFaceMapping) {
                    if (submesh.facesMapping != null && submesh.facesMapping[face] != -1) {
                        facesMapping[faceCount] = (byte) (submesh.facesMapping[face] + textureCount);
                    } else {
                        facesMapping[faceCount] = -1;
                    }
                }
                faceColour[faceCount] = submesh.faceColour[face];
                if (hasLitColours) {
                    faceColourA[faceCount] = submesh.faceColourA[face];
                    faceColourB[faceCount] = submesh.faceColourB[face];
                    faceColourC[faceCount] = submesh.faceColourC[face];
                }
                facesA[faceCount] = addVertex(submesh, submesh.facesA[face]);
                facesB[faceCount] = addVertex(submesh, submesh.facesB[face]);
                facesC[faceCount] = addVertex(submesh, submesh.facesC[face]);
                faceCount++;
            }

            for (int index = 0; index < submesh.textureCount; index++) {
                byte type = texturesMapping[textureCount] = submesh.texturesMapping[index];
                if (type == 0) {
                    texturesMappingP[textureCount] = addVertex(submesh, submesh.texturesMappingP[index]);
                    texturesMappingM[textureCount] = addVertex(submesh, submesh.texturesMappingM[index]);
                    texturesMappingN[textureCount] = addVertex(submesh, submesh.texturesMappingN[index]);
                }
                if (type >= 1 && type <= 3) {
                    texturesMappingP[textureCount] = submesh.texturesMappingP[index];
                    texturesMappingM[textureCount] = submesh.texturesMappingM[index];
                    texturesMappingN[textureCount] = submesh.texturesMappingN[index];
                }
                textureCount++;
            }
        }

    }

    public Model(boolean flag, boolean flag1, boolean flag2, Model model) {
        aBoolean1659 = false;
        vertexCount = model.vertexCount;
        faceCount = model.faceCount;
        textureCount = model.textureCount;
        if (flag2) {
            vertexX = model.vertexX;
            vertexY = model.vertexY;
            vertexZ = model.vertexZ;
        } else {
            vertexX = new int[vertexCount];
            vertexY = new int[vertexCount];
            vertexZ = new int[vertexCount];
            for (int j = 0; j < vertexCount; j++) {
                vertexX[j] = model.vertexX[j];
                vertexY[j] = model.vertexY[j];
                vertexZ[j] = model.vertexZ[j];
            }

        }
        if (flag) {
            faceColour = model.faceColour;
        } else {
            faceColour = new int[faceCount];
            System.arraycopy(model.faceColour, 0, faceColour, 0, faceCount);

        }
        if (flag1) {
            faceTrans = model.faceTrans;
        } else {
            faceTrans = new int[faceCount];
            if (model.faceTrans == null) {
                for (int l = 0; l < faceCount; l++)
                    faceTrans[l] = 0;

            } else {
                System.arraycopy(model.faceTrans, 0, faceTrans, 0, faceCount);

            }
        }
        vertexSkin = model.vertexSkin;
        faceLabel = model.faceLabel;
        faceType = model.faceType;
        facesA = model.facesA;
        facesB = model.facesB;
        facesC = model.facesC;
        faceLayer = model.faceLayer;
        priority = model.priority;
        texturesMapping = model.texturesMapping;
        texturesMappingP = model.texturesMappingP;
        texturesMappingM = model.texturesMappingM;
        texturesMappingN = model.texturesMappingN;
        faceMaterial = model.faceMaterial;
        facesMapping = model.facesMapping;
    }

    public Model(boolean flag, boolean flag1, Model model) {
        aBoolean1659 = false;
        vertexCount = model.vertexCount;
        faceCount = model.faceCount;
        textureCount = model.textureCount;
        if (flag) {
            vertexY = new int[vertexCount];
            System.arraycopy(model.vertexY, 0, vertexY, 0, vertexCount);

        } else {
            vertexY = model.vertexY;
        }
        if (flag1) {
            faceColourA = new int[faceCount];
            faceColourB = new int[faceCount];
            faceColourC = new int[faceCount];
            for (int k = 0; k < faceCount; k++) {
                faceColourA[k] = model.faceColourA[k];
                faceColourB[k] = model.faceColourB[k];
                faceColourC[k] = model.faceColourC[k];
            }

            faceType = new int[faceCount];
            if (model.faceType == null) {
                for (int l = 0; l < faceCount; l++)
                    faceType[l] = 0;

            } else {
                System.arraycopy(model.faceType, 0, faceType, 0, faceCount);

            }
            super.vertexNormals = new VertexNormal[vertexCount];
            for (int j1 = 0; j1 < vertexCount; j1++) {
                VertexNormal vertexNormal = super.vertexNormals[j1] = new VertexNormal();
                VertexNormal vertexNormal_1 = model.vertexNormals[j1];
                vertexNormal.x = vertexNormal_1.x;
                vertexNormal.y = vertexNormal_1.y;
                vertexNormal.z = vertexNormal_1.z;
                vertexNormal.magnitude = vertexNormal_1.magnitude;
            }

            sharedNormals = model.sharedNormals;
        } else {
            faceColourA = model.faceColourA;
            faceColourB = model.faceColourB;
            faceColourC = model.faceColourC;
            faceType = model.faceType;
        }
        vertexX = model.vertexX;
        vertexZ = model.vertexZ;
        faceColour = model.faceColour;
        faceTrans = model.faceTrans;
        faceLayer = model.faceLayer;
        priority = model.priority;
        facesA = model.facesA;
        facesB = model.facesB;
        facesC = model.facesC;
        texturesMapping = model.texturesMapping;
        texturesMappingP = model.texturesMappingP;
        texturesMappingM = model.texturesMappingM;
        texturesMappingN = model.texturesMappingN;
        faceMaterial = model.faceMaterial;
        facesMapping = model.facesMapping;
        super.modelHeight = model.modelHeight;
        anInt1651 = model.anInt1651;
        anInt1650 = model.anInt1650;
        anInt1653 = model.anInt1653;
        anInt1652 = model.anInt1652;
        anInt1646 = model.anInt1646;
        anInt1648 = model.anInt1648;
        anInt1649 = model.anInt1649;
        anInt1647 = model.anInt1647;
    }

    public static void nullLoader() {
        aClass21Array1661 = null;
        aBooleanArray1663 = null;
        aBooleanArray1664 = null;
        projected_vertices_x = null;
        projected_vertices_y = null;
        anIntArray1667 = null;
        camera_vertex_x = null;
        camera_vertex_y = null;
        camera_vertex_z = null;
        anIntArray1671 = null;
        anIntArrayArray1672 = null;
        anIntArray1673 = null;
        anIntArrayArray1674 = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        anIntArray1677 = null;
        SINE = null;
        COSINE = null;
        hslToRgbTable = null;
        modelIntArray4 = null;
    }

    public static void method459(int i, OnDemandFetcherParent onDemandFetcherParent) {
        aClass21Array1661 = new Class21[i + 50000];
        aOnDemandFetcherParent_1662 = onDemandFetcherParent;
    }

    public static void method460(byte[] abyte0, int j) {
        boolean newFormat = abyte0[abyte0.length - 1] == -1 && abyte0[abyte0.length - 2] == -1;
        // FileOperations.WriteFile(sign.signlink.findcachedir()+"./Models Dump/"+j+".dat", abyte0);
        // System.out.println(j + " Written");
        Stream class30_sub2_sub2 = new Stream(abyte0);
        class30_sub2_sub2.currentOffset = abyte0.length - (!newFormat ? 18 : 23);
        Class21 class21_1 = aClass21Array1661[j] = new Class21();
        class21_1.aByteArray368 = abyte0;
        class21_1.vertexCount = class30_sub2_sub2.readUnsignedWord();
        class21_1.faceCount = class30_sub2_sub2.readUnsignedWord();
        class21_1.texturedFaceCount = class30_sub2_sub2.readUnsignedByte();
        int k = class30_sub2_sub2.readUnsignedByte();
        int l = class30_sub2_sub2.readUnsignedByte();
        int i1 = class30_sub2_sub2.readUnsignedByte();
        int j1 = class30_sub2_sub2.readUnsignedByte();
        int k1 = class30_sub2_sub2.readUnsignedByte();
        if (newFormat) {
            class30_sub2_sub2.readUnsignedByte();
        }
        int l1 = class30_sub2_sub2.readUnsignedWord();
        int i2 = class30_sub2_sub2.readUnsignedWord();
        int j2 = class30_sub2_sub2.readUnsignedWord();
        int k2 = class30_sub2_sub2.readUnsignedWord();
        if (newFormat) {
            class30_sub2_sub2.readUnsignedWord();
        }
        int l2 = 0;
        class21_1.anInt372 = l2;
        l2 += class21_1.vertexCount;
        class21_1.anInt378 = l2;
        l2 += class21_1.faceCount;
        class21_1.anInt381 = l2;
        if (l == 255)
            l2 += class21_1.faceCount;
        else
            class21_1.anInt381 = -l - 1;
        class21_1.anInt383 = l2;
        if (j1 == 1)
            l2 += class21_1.faceCount;
        else
            class21_1.anInt383 = -1;
        class21_1.anInt380 = l2;
        if (k == 1)
            l2 += class21_1.faceCount;
        else
            class21_1.anInt380 = -1;
        class21_1.anInt376 = l2;
        if (k1 == 1)
            l2 += class21_1.vertexCount;
        else
            class21_1.anInt376 = -1;
        class21_1.anInt382 = l2;
        if (i1 == 1)
            l2 += class21_1.faceCount;
        else
            class21_1.anInt382 = -1;
        class21_1.anInt377 = l2;
        l2 += k2;
        class21_1.anInt379 = l2;
        l2 += class21_1.faceCount * 2;
        class21_1.anInt384 = l2;
        l2 += class21_1.texturedFaceCount * 6;
        class21_1.anInt373 = l2;
        l2 += l1;
        class21_1.anInt374 = l2;
        l2 += i2;
        class21_1.anInt375 = l2;
        l2 += j2;
    }

    /*public static void method460(byte[] abyte0, int j) {
         boolean newFormat = abyte0[abyte0.length - 1] == -1 && abyte0[abyte0.length - 2] == -1;
         if (abyte0 == null) {
             Class21 class21 = aClass21Array1661[j] = new Class21();
             class21.anInt369 = 0;
             class21.anInt370 = 0;
             class21.anInt371 = 0;
             return;
         }
         Stream class30_sub2_sub2 = new Stream(abyte0);
         class30_sub2_sub2.currentOffset = abyte0.length - (!newFormat ? 18 : 23);
         Class21 class21_1 = aClass21Array1661[j] = new Class21();
         class21_1.aByteArray368 = abyte0;
         class21_1.anInt369 = class30_sub2_sub2.readUnsignedByte();
         class21_1.anInt370 = class30_sub2_sub2.readUnsignedByte();
         class21_1.anInt371 = class30_sub2_sub2.readUnsignedByte();
         int k = class30_sub2_sub2.readUnsignedByte();
         int l = class30_sub2_sub2.readUnsignedByte();
         int i1 = class30_sub2_sub2.readUnsignedByte();
         int j1 = class30_sub2_sub2.readUnsignedByte();
         int k1 = class30_sub2_sub2.readUnsignedByte();
         if (newFormat) {
             int ignore = class30_sub2_sub2.readUnsignedByte();
         }
         int l1 = class30_sub2_sub2.readUnsignedByte();
         int i2 = class30_sub2_sub2.readUnsignedByte();
         int j2 = class30_sub2_sub2.readUnsignedByte();
         int k2 = class30_sub2_sub2.readUnsignedByte();
         if (newFormat) {
             int ignore = class30_sub2_sub2.readUnsignedByte();
         }
         int l2 = 0;
         class21_1.anInt372 = l2;
         l2 += class21_1.anInt369;
         class21_1.anInt378 = l2;
         l2 += class21_1.anInt370;
         class21_1.anInt381 = l2;
         if(l == 255)
             l2 += class21_1.anInt370;
         else
             class21_1.anInt381 = -l - 1;
         class21_1.anInt383 = l2;
         if(j1 == 1)
             l2 += class21_1.anInt370;
         else
             class21_1.anInt383 = -1;
         class21_1.anInt380 = l2;
         if(k == 1)
             l2 += class21_1.anInt370;
         else
             class21_1.anInt380 = -1;
         class21_1.anInt376 = l2;
         if(k1 == 1)
             l2 += class21_1.anInt369;
         else
             class21_1.anInt376 = -1;
         class21_1.anInt382 = l2;
         if(i1 == 1)
             l2 += class21_1.anInt370;
         else
             class21_1.anInt382 = -1;
         class21_1.anInt377 = l2;
         l2 += k2;
         class21_1.anInt379 = l2;
         l2 += class21_1.anInt370 * 2;
         class21_1.anInt384 = l2;
         l2 += class21_1.anInt371 * 6;
         class21_1.anInt373 = l2;
         l2 += l1;
         class21_1.anInt374 = l2;
         l2 += i2;
         class21_1.anInt375 = l2;
         l2 += j2;
     }*/
    public static void method461(int j) {
        aClass21Array1661[j] = null;
    }

    public static Model method462(int j) {
        if (aClass21Array1661 == null)
            return null;
        Class21 class21 = aClass21Array1661[j];
        if (class21 == null) {
            aOnDemandFetcherParent_1662.method548(j);
            return null;
        } else {
            return new Model(j);
        }
    }

    public static boolean method463(int i) {
        if (aClass21Array1661 == null)
            return false;
        Class21 class21 = aClass21Array1661[i];
        if (class21 == null) {
            aOnDemandFetcherParent_1662.method548(i);
            return false;
        } else {
            return true;
        }
    }

    public static int repackHSL(int hsl, int lightness) {
        lightness = lightness * (hsl & 127) >> 7;
        if (lightness < 2) {
            lightness = 2;
        } else if (lightness > 126) {
            lightness = 126;
        }

        return (hsl & '\uff80') + lightness;
    }

    private static int method1937(int var0) {
        if (var0 < 2) {
            var0 = 2;
        } else if (var0 > 126) {
            var0 = 126;
        }

        return var0;
    }

    private static int method481(int i, int j, int k) {
        if ((k & 2) == 2) {
            if (j < 0)
                j = 0;
            else if (j > 127)
                j = 127;
            j = 127 - j;
            return j;
        }
        j = j * (i & 0x7f) >> 7;
        if (j < 2)
            j = 2;
        else if (j > 126)
            j = 126;
        return (i & 0xff80) + j;
    }

    public static boolean isHdTexture(int i) {
        if (i == -1 || i == 65535) {
            return false;
        }
        return !UnpackedMaterials.LOW_DETAIL[i];
    }

    public void readNewModel(byte abyte0[], int modelID) {
        Stream nc1 = new Stream(abyte0);
        Stream nc2 = new Stream(abyte0);
        Stream nc3 = new Stream(abyte0);
        Stream nc4 = new Stream(abyte0);
        Stream nc5 = new Stream(abyte0);
        Stream nc6 = new Stream(abyte0);
        Stream nc7 = new Stream(abyte0);
        nc1.currentOffset = abyte0.length - 23;
        int numVertices = nc1.readUnsignedWord();
        int numTriangles = nc1.readUnsignedWord();
        int numTexTriangles = nc1.readUnsignedByte();
        Class21 ModelDef_1 = aClass21Array1661[modelID] = new Class21();
        ModelDef_1.aByteArray368 = abyte0;
        ModelDef_1.vertexCount = numVertices;
        ModelDef_1.faceCount = numTriangles;
        ModelDef_1.texturedFaceCount = numTexTriangles;
        int l1 = nc1.readUnsignedByte();
        boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
        int i2 = nc1.readUnsignedByte();
        int j2 = nc1.readUnsignedByte();
        int k2 = nc1.readUnsignedByte();
        int l2 = nc1.readUnsignedByte();
        int i3 = nc1.readUnsignedByte();
        int j3 = nc1.readUnsignedWord();
        int k3 = nc1.readUnsignedWord();
        int l3 = nc1.readUnsignedWord();
        int i4 = nc1.readUnsignedWord();
        int j4 = nc1.readUnsignedWord();
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        byte[] x = null;
        byte[] O = null;
        byte[] J = null;
        byte[] F = null;
        byte[] cb = null;
        byte[] gb = null;
        byte[] lb = null;
        int[] kb = null;
        int[] y = null;
        int[] N = null;
        short[] D = null;
        int[] triangleColours2 = new int[numTriangles];
        if (numTexTriangles > 0) {
            O = new byte[numTexTriangles];
            nc1.currentOffset = 0;
            for (int j5 = 0; j5 < numTexTriangles; j5++) {
                byte byte0 = O[j5] = nc1.readSignedByte();
                if (byte0 == 0)
                    k4++;
                if (byte0 >= 1 && byte0 <= 3)
                    l4++;
                if (byte0 == 2)
                    i5++;
            }
        }
        int k5 = numTexTriangles;
        int l5 = k5;
        k5 += numVertices;
        int i6 = k5;
        if (l1 == 1)
            k5 += numTriangles;
        int j6 = k5;
        k5 += numTriangles;
        int k6 = k5;
        if (i2 == 255)
            k5 += numTriangles;
        int l6 = k5;
        if (k2 == 1)
            k5 += numTriangles;
        int i7 = k5;
        if (i3 == 1)
            k5 += numVertices;
        int j7 = k5;
        if (j2 == 1)
            k5 += numTriangles;
        int k7 = k5;
        k5 += i4;
        int l7 = k5;
        if (l2 == 1)
            k5 += numTriangles * 2;
        int i8 = k5;
        k5 += j4;
        int j8 = k5;
        k5 += numTriangles * 2;
        int k8 = k5;
        k5 += j3;
        int l8 = k5;
        k5 += k3;
        int i9 = k5;
        k5 += l3;
        int j9 = k5;
        k5 += k4 * 6;
        int k9 = k5;
        k5 += l4 * 6;
        int l9 = k5;
        k5 += l4 * 6;
        int i10 = k5;
        k5 += l4;
        int j10 = k5;
        k5 += l4;
        int k10 = k5;
        k5 += l4 + i5 * 2;
        int[] vertexX = new int[numVertices];
        int[] vertexY = new int[numVertices];
        int[] vertexZ = new int[numVertices];
        int[] facePoint1 = new int[numTriangles];
        int[] facePoint2 = new int[numTriangles];
        int[] facePoint3 = new int[numTriangles];
        vertexSkin = new int[numVertices];
        faceType = new int[numTriangles];
        faceLayer = new int[numTriangles];
        faceTrans = new int[numTriangles];
        faceLabel = new int[numTriangles];


        if (i3 == 1)
            vertexSkin = new int[numVertices];
        if (bool)
            faceType = new int[numTriangles];
        if (i2 == 255)
            faceLayer = new int[numTriangles];
        else {
        }
        if (j2 == 1)
            faceTrans = new int[numTriangles];
        if (k2 == 1)
            faceLabel = new int[numTriangles];
        if (l2 == 1)
            D = new short[numTriangles];
        if (l2 == 1 && numTexTriangles > 0)
            x = new byte[numTriangles];
        triangleColours2 = new int[numTriangles];
        int[] texTrianglesPoint1 = null;
        int[] texTrianglesPoint2 = null;
        int[] texTrianglesPoint3 = null;
        if (numTexTriangles > 0) {
            texTrianglesPoint1 = new int[numTexTriangles];
            texTrianglesPoint2 = new int[numTexTriangles];
            texTrianglesPoint3 = new int[numTexTriangles];
            if (l4 > 0) {
                kb = new int[l4];
                N = new int[l4];
                y = new int[l4];
                gb = new byte[l4];
                lb = new byte[l4];
                F = new byte[l4];
            }
            if (i5 > 0) {
                cb = new byte[i5];
                J = new byte[i5];
            }
        }
        nc1.currentOffset = l5;
        nc2.currentOffset = k8;
        nc3.currentOffset = l8;
        nc4.currentOffset = i9;
        nc5.currentOffset = i7;
        int l10 = 0;
        int i11 = 0;
        int j11 = 0;
        for (int k11 = 0; k11 < numVertices; k11++) {
            int l11 = nc1.readUnsignedByte();
            int j12 = 0;
            if ((l11 & 1) != 0)
                j12 = nc2.gSmart1or2();
            int l12 = 0;
            if ((l11 & 2) != 0)
                l12 = nc3.gSmart1or2();
            int j13 = 0;
            if ((l11 & 4) != 0)
                j13 = nc4.gSmart1or2();
            vertexX[k11] = l10 + j12;
            vertexY[k11] = i11 + l12;
            vertexZ[k11] = j11 + j13;
            l10 = vertexX[k11];
            i11 = vertexY[k11];
            j11 = vertexZ[k11];
            if (vertexSkin != null)
                vertexSkin[k11] = nc5.readUnsignedByte();
        }
        nc1.currentOffset = j8;
        nc2.currentOffset = i6;
        nc3.currentOffset = k6;
        nc4.currentOffset = j7;
        nc5.currentOffset = l6;
        nc6.currentOffset = l7;
        nc7.currentOffset = i8;
        for (int i12 = 0; i12 < numTriangles; i12++) {


            triangleColours2[i12] = nc1.readUnsignedWord();
            if (l1 == 1) {
                faceType[i12] = nc2.readSignedByte();
                if (faceType[i12] == 2) triangleColours2[i12] = 65535;
                faceType[i12] = 0;
            }
            if (i2 == 255) {
                faceLayer[i12] = nc3.readSignedByte();
            }
            if (j2 == 1) {
                faceTrans[i12] = nc4.readSignedByte();
                if (faceTrans[i12] < 0)
                    faceTrans[i12] = (256 + faceTrans[i12]);
            }
            if (k2 == 1)
                faceLabel[i12] = nc5.readUnsignedByte();
            if (l2 == 1)
                D[i12] = (short) (nc6.readUnsignedWord() - 1);

            if (x != null)
                if (D[i12] != -1)
                    x[i12] = (byte) (nc7.readUnsignedByte() - 1);
                else
                    x[i12] = -1;
        }
        nc1.currentOffset = k7;
        nc2.currentOffset = j6;
        int k12 = 0;
        int i13 = 0;
        int k13 = 0;
        int l13 = 0;
        for (int i14 = 0; i14 < numTriangles; i14++) {
            int j14 = nc2.readUnsignedByte();
            if (j14 == 1) {
                k12 = nc1.gSmart1or2() + l13;
                l13 = k12;
                i13 = nc1.gSmart1or2() + l13;
                l13 = i13;
                k13 = nc1.gSmart1or2() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 2) {
                i13 = k13;
                k13 = nc1.gSmart1or2() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 3) {
                k12 = k13;
                k13 = nc1.gSmart1or2() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 4) {
                int l14 = k12;
                k12 = i13;
                i13 = l14;
                k13 = nc1.gSmart1or2() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
        }
        nc1.currentOffset = j9;
        nc2.currentOffset = k9;
        nc3.currentOffset = l9;
        nc4.currentOffset = i10;
        nc5.currentOffset = j10;
        nc6.currentOffset = k10;
        for (int k14 = 0; k14 < numTexTriangles; k14++) {
            int i15 = O[k14] & 0xff;
            if (i15 == 0) {
                texTrianglesPoint1[k14] = nc1.readUnsignedWord();
                texTrianglesPoint2[k14] = nc1.readUnsignedWord();
                texTrianglesPoint3[k14] = nc1.readUnsignedWord();
            }
            if (i15 == 1) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                kb[k14] = nc3.readUnsignedWord();
                N[k14] = nc3.readUnsignedWord();
                y[k14] = nc3.readUnsignedWord();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
            if (i15 == 2) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                kb[k14] = nc3.readUnsignedWord();
                N[k14] = nc3.readUnsignedWord();
                y[k14] = nc3.readUnsignedWord();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
                cb[k14] = nc6.readSignedByte();
                J[k14] = nc6.readSignedByte();
            }
            if (i15 == 3) {
                texTrianglesPoint1[k14] = nc2.readUnsignedWord();
                texTrianglesPoint2[k14] = nc2.readUnsignedWord();
                texTrianglesPoint3[k14] = nc2.readUnsignedWord();
                kb[k14] = nc3.readUnsignedWord();
                N[k14] = nc3.readUnsignedWord();
                y[k14] = nc3.readUnsignedWord();
                gb[k14] = nc4.readSignedByte();
                lb[k14] = nc5.readSignedByte();
                F[k14] = nc6.readSignedByte();
            }
        }

        if (i2 != 255) {
            for (int i12 = 0; i12 < numTriangles; i12++)
                faceLayer[i12] = i2;

        }
        faceColour = triangleColours2;
        this.vertexCount = numVertices;
        this.faceCount = numTriangles;
        this.vertexX = vertexX;
        this.vertexY = vertexY;
        this.vertexZ = vertexZ;
        facesA = facePoint1;
        facesB = facePoint2;
        facesC = facePoint3;
        filterTriangles();
    }

    private void method1067(byte[] bs) {
        boolean bool = false;
        boolean hasMaterial = false;
        Stream class23_sub5 = new Stream(bs);
        Stream class23_sub5_277_ = new Stream(bs);
        Stream class23_sub5_278_ = new Stream(bs);
        Stream class23_sub5_279_ = new Stream(bs);
        Stream class23_sub5_280_ = new Stream(bs);
        class23_sub5.currentOffset = bs.length - 18;
        int i = class23_sub5.readUnsignedWord();
        int i_281_ = class23_sub5.readUnsignedWord();
        int i_282_ = class23_sub5.readUnsignedByte();
        int i_283_ = class23_sub5.readUnsignedByte();
        int i_284_ = class23_sub5.readUnsignedByte();
        int i_285_ = class23_sub5.readUnsignedByte();
        int i_286_ = class23_sub5.readUnsignedByte();
        int i_287_ = class23_sub5.readUnsignedByte();
        int i_288_ = class23_sub5.readUnsignedWord();
        int i_289_ = class23_sub5.readUnsignedWord();
        int i_290_ = class23_sub5.readUnsignedWord();
        int i_291_ = class23_sub5.readUnsignedWord();
        int i_292_ = 0;
        int i_293_ = i_292_;
        i_292_ += i;
        int i_294_ = i_292_;
        i_292_ += i_281_;
        int i_295_ = i_292_;
        if (i_284_ == 255) {
            i_292_ += i_281_;
        }
        int i_296_ = i_292_;
        if (i_286_ == 1) {
            i_292_ += i_281_;
        }
        int i_297_ = i_292_;
        if (i_283_ == 1) {
            i_292_ += i_281_;
        }
        int i_298_ = i_292_;
        if (i_287_ == 1) {
            i_292_ += i;
        }
        int i_299_ = i_292_;
        if (i_285_ == 1) {
            i_292_ += i_281_;
        }
        int i_300_ = i_292_;
        i_292_ += i_291_;
        int i_301_ = i_292_;
        i_292_ += i_281_ * 2;
        int i_302_ = i_292_;
        i_292_ += i_282_ * 6;
        int i_303_ = i_292_;
        i_292_ += i_288_;
        int i_304_ = i_292_;
        i_292_ += i_289_;
        int i_305_ = i_292_;
        i_292_ += i_290_;
        vertexCount = i;
        faceCount = i_281_;
        textureCount = i_282_;
        vertexX = new int[i];
        vertexY = new int[i];
        vertexZ = new int[i];
        facesA = new int[i_281_];
        facesB = new int[i_281_];
        facesC = new int[i_281_];
        if (i_282_ > 0) {
            texturesMapping = new byte[i_282_];
            texturesMappingP = new int[i_282_];
            texturesMappingM = new int[i_282_];
            texturesMappingN = new int[i_282_];
        }
        if (i_287_ == 1) {
            vertexSkin = new int[i];
        }
        if (i_283_ == 1) {
            faceType = new int[i_281_];
            facesMapping = new byte[i_281_];
            faceMaterial = new short[i_281_];
        }
        if (i_284_ == 255) {
            faceLayer = new int[i_281_];
        } else {
            priority = (byte) i_284_;
        }
        if (i_285_ == 1) {
            faceTrans = new int[i_281_];
        }
        if (i_286_ == 1) {
            faceLabel = new int[i_281_];
        }
        faceColour = new int[i_281_];
        class23_sub5.currentOffset = i_293_;
        class23_sub5_277_.currentOffset = i_303_;
        class23_sub5_278_.currentOffset = i_304_;
        class23_sub5_279_.currentOffset = i_305_;
        class23_sub5_280_.currentOffset = i_298_;
        int i_306_ = 0;
        int i_307_ = 0;
        int i_308_ = 0;
        for (int i_309_ = 0; i_309_ < i; i_309_++) {
            int i_310_ = class23_sub5.readUnsignedByte();
            int i_311_ = 0;
            if ((i_310_ & 0x1) != 0) {
                i_311_ = class23_sub5_277_.gSmart1or2();
            }
            int i_312_ = 0;
            if ((i_310_ & 0x2) != 0) {
                i_312_ = class23_sub5_278_.gSmart1or2();
            }
            int i_313_ = 0;
            if ((i_310_ & 0x4) != 0) {
                i_313_ = class23_sub5_279_.gSmart1or2();
            }
            vertexX[i_309_] = i_306_ + i_311_;
            vertexY[i_309_] = i_307_ + i_312_;
            vertexZ[i_309_] = i_308_ + i_313_;
            i_306_ = vertexX[i_309_];
            i_307_ = vertexY[i_309_];
            i_308_ = vertexZ[i_309_];
            if (i_287_ == 1) {
                vertexSkin[i_309_] = class23_sub5_280_.readUnsignedByte();
            }
        }
        class23_sub5.currentOffset = i_301_;
        class23_sub5_277_.currentOffset = i_297_;
        class23_sub5_278_.currentOffset = i_295_;
        class23_sub5_279_.currentOffset = i_299_;
        class23_sub5_280_.currentOffset = i_296_;
        for (int i_314_ = 0; i_314_ < i_281_; i_314_++) {
            faceColour[i_314_] = (short) class23_sub5.readUnsignedWord();
            if (i_283_ == 1) {
                int i_315_ = class23_sub5_277_.readUnsignedByte();
                if ((i_315_ & 0x1) == 1) {
                    faceType[i_314_] = (byte) 1;
                    bool = true;
                } else {
                    faceType[i_314_] = (byte) 0;
                }
                if ((i_315_ & 0x2) == 2) {
                    facesMapping[i_314_] = (byte) (i_315_ >> 2);
                    faceMaterial[i_314_] = (short) faceColour[i_314_];
                    faceColour[i_314_] = (short) 127;
                    if (faceMaterial[i_314_] != -1) {
                        hasMaterial = true;
                    }
                } else {
                    facesMapping[i_314_] = (byte) -1;
                    faceMaterial[i_314_] = (short) -1;
                }
            }
            if (i_284_ == 255) {
                faceLayer[i_314_] = class23_sub5_278_.readSignedByte();
            }
            if (i_285_ == 1) {
                faceTrans[i_314_] = class23_sub5_279_.readSignedByte();
            }
            if (i_286_ == 1) {
                faceLabel[i_314_] = class23_sub5_280_.readUnsignedByte();
            }
        }
        class23_sub5.currentOffset = i_300_;
        class23_sub5_277_.currentOffset = i_294_;
        int i_316_ = 0;
        int i_317_ = 0;
        int i_318_ = 0;
        int i_319_ = 0;
        for (int i_320_ = 0; i_320_ < i_281_; i_320_++) {
            int i_321_ = class23_sub5_277_.readUnsignedByte();
            if (i_321_ == 1) {
                i_316_ = class23_sub5.gSmart1or2() + i_319_;
                i_319_ = i_316_;
                i_317_ = class23_sub5.gSmart1or2() + i_319_;
                i_319_ = i_317_;
                i_318_ = class23_sub5.gSmart1or2() + i_319_;
                i_319_ = i_318_;
                facesA[i_320_] = i_316_;
                facesB[i_320_] = i_317_;
                facesC[i_320_] = i_318_;
            }
            if (i_321_ == 2) {
                i_317_ = i_318_;
                i_318_ = class23_sub5.gSmart1or2() + i_319_;
                i_319_ = i_318_;
                facesA[i_320_] = i_316_;
                facesB[i_320_] = i_317_;
                facesC[i_320_] = i_318_;
            }
            if (i_321_ == 3) {
                i_316_ = i_318_;
                i_318_ = class23_sub5.gSmart1or2() + i_319_;
                i_319_ = i_318_;
                facesA[i_320_] = i_316_;
                facesB[i_320_] = i_317_;
                facesC[i_320_] = i_318_;
            }
            if (i_321_ == 4) {
                int i_322_ = i_316_;
                i_316_ = i_317_;
                i_317_ = i_322_;
                i_318_ = class23_sub5.gSmart1or2() + i_319_;
                i_319_ = i_318_;
                facesA[i_320_] = i_316_;
                facesB[i_320_] = i_317_;
                facesC[i_320_] = i_318_;
            }
        }
        class23_sub5.currentOffset = i_302_;
        for (int i_323_ = 0; i_323_ < i_282_; i_323_++) {
            texturesMapping[i_323_] = (byte) 0;
            texturesMappingP[i_323_] = (short) class23_sub5.readUnsignedWord();
            texturesMappingM[i_323_] = (short) class23_sub5.readUnsignedWord();
            texturesMappingN[i_323_] = (short) class23_sub5.readUnsignedWord();
        }
        if (facesMapping != null) {
            boolean bool_324_ = false;
            for (int i_325_ = 0; i_325_ < i_281_; i_325_++) {
                int i_326_ = facesMapping[i_325_] & 0xff;
                if (i_326_ != 255) {
                    if ((texturesMappingP[i_326_] & 0xffff) == facesA[i_325_] && (texturesMappingM[i_326_] & 0xffff) == facesB[i_325_] && (texturesMappingN[i_326_] & 0xffff) == facesC[i_325_]) {
                        facesMapping[i_325_] = (byte) -1;
                    } else {
                        bool_324_ = true;
                    }
                }
            }
            if (!bool_324_) {
                facesMapping = null;
            }
        }
        if (!hasMaterial) {
            faceMaterial = null;
        }
        if (!bool) {
            faceType = null;
        }
    }

    private void method1041(byte[] bs) {
        Stream class23_sub5 = new Stream(bs);
        Stream class23_sub5_58_ = new Stream(bs);
        Stream class23_sub5_59_ = new Stream(bs);
        Stream class23_sub5_60_ = new Stream(bs);
        Stream class23_sub5_61_ = new Stream(bs);
        Stream class23_sub5_62_ = new Stream(bs);
        Stream class23_sub5_63_ = new Stream(bs);
        class23_sub5.currentOffset = bs.length - 23;
        int i = class23_sub5.readUnsignedWord();
        int i_64_ = class23_sub5.readUnsignedWord();
        int i_65_ = class23_sub5.readUnsignedByte();
        int i_66_ = class23_sub5.readUnsignedByte();
        int i_67_ = class23_sub5.readUnsignedByte();
        int i_68_ = class23_sub5.readUnsignedByte();
        int i_69_ = class23_sub5.readUnsignedByte();
        int i_70_ = class23_sub5.readUnsignedByte();
        int i_71_ = class23_sub5.readUnsignedByte();
        int i_72_ = class23_sub5.readUnsignedWord();
        int i_73_ = class23_sub5.readUnsignedWord();
        int i_74_ = class23_sub5.readUnsignedWord();
        int i_75_ = class23_sub5.readUnsignedWord();
        int i_76_ = class23_sub5.readUnsignedWord();
        int i_77_ = 0;
        int i_78_ = 0;
        int i_79_ = 0;
        if (i_65_ > 0) {
            texturesMapping = new byte[i_65_];
            class23_sub5.currentOffset = 0;
            for (int i_80_ = 0; i_80_ < i_65_; i_80_++) {
                byte b = texturesMapping[i_80_] = class23_sub5.readSignedByte();
                if (b == 0) {
                    i_77_++;
                }
                if (b >= 1 && b <= 3) {
                    i_78_++;
                }
                if (b == 2) {
                    i_79_++;
                }
            }
        }
        int i_81_ = i_65_;
        int i_82_ = i_81_;
        i_81_ += i;
        int i_83_ = i_81_;
        if (i_66_ == 1) {
            i_81_ += i_64_;
        }
        int i_84_ = i_81_;
        i_81_ += i_64_;
        int i_85_ = i_81_;
        if (i_67_ == 255) {
            i_81_ += i_64_;
        }
        int i_86_ = i_81_;
        if (i_69_ == 1) {
            i_81_ += i_64_;
        }
        int i_87_ = i_81_;
        if (i_71_ == 1) {
            i_81_ += i;
        }
        int i_88_ = i_81_;
        if (i_68_ == 1) {
            i_81_ += i_64_;
        }
        int i_89_ = i_81_;
        i_81_ += i_75_;
        int i_90_ = i_81_;
        if (i_70_ == 1) {
            i_81_ += i_64_ * 2;
        }
        int i_91_ = i_81_;
        i_81_ += i_76_;
        int i_92_ = i_81_;
        i_81_ += i_64_ * 2;
        int i_93_ = i_81_;
        i_81_ += i_72_;
        int i_94_ = i_81_;
        i_81_ += i_73_;
        int i_95_ = i_81_;
        i_81_ += i_74_;
        int i_96_ = i_81_;
        i_81_ += i_77_ * 6;
        int i_97_ = i_81_;
        i_81_ += i_78_ * 6;
        int i_98_ = i_81_;
        i_81_ += i_78_ * 6;
        int i_99_ = i_81_;
        i_81_ += i_78_;
        int i_100_ = i_81_;
        i_81_ += i_78_;
        int i_101_ = i_81_;
        i_81_ += i_78_ + i_79_ * 2;
        vertexCount = i;
        faceCount = i_64_;
        textureCount = i_65_;
        vertexX = new int[i];
        vertexY = new int[i];
        vertexZ = new int[i];
        facesA = new int[i_64_];
        facesB = new int[i_64_];
        facesC = new int[i_64_];
        if (i_71_ == 1) {
            vertexSkin = new int[i];
        }
        if (i_66_ == 1) {
            faceType = new int[i_64_];
        }
        if (i_67_ == 255) {
            faceLayer = new int[i_64_];
        } else {
            priority = (byte) i_67_;
        }
        if (i_68_ == 1) {
            faceTrans = new int[i_64_];
        }
        if (i_69_ == 1) {
            faceLabel = new int[i_64_];
        }
        if (i_70_ == 1) {
            faceMaterial = new short[i_64_];
        }
        if (i_70_ == 1 && i_65_ > 0) {
            facesMapping = new byte[i_64_];
        }
        faceColour = new int[i_64_];
        short[] aShortArray2535 = null;
        short[] aShortArray2526 = null;
        short[] aShortArray2543 = null;
        byte[] aByteArray2531 = null;
        byte[] aByteArray2519 = null;
        byte[] aByteArray2530 = null;
        byte[] aByteArray2525 = null;
        byte[] aByteArray2547 = null;
        if (i_65_ > 0) {
            texturesMappingP = new int[i_65_];
            texturesMappingM = new int[i_65_];
            texturesMappingN = new int[i_65_];
            if (i_78_ > 0) {
                aShortArray2535 = new short[i_78_];
                aShortArray2526 = new short[i_78_];
                aShortArray2543 = new short[i_78_];
                aByteArray2531 = new byte[i_78_];
                aByteArray2519 = new byte[i_78_];
                aByteArray2530 = new byte[i_78_];
            }
            if (i_79_ > 0) {
                aByteArray2525 = new byte[i_79_];
                aByteArray2547 = new byte[i_79_];
            }
        }
        class23_sub5.currentOffset = i_82_;
        class23_sub5_58_.currentOffset = i_93_;
        class23_sub5_59_.currentOffset = i_94_;
        class23_sub5_60_.currentOffset = i_95_;
        class23_sub5_61_.currentOffset = i_87_;
        int i_102_ = 0;
        int i_103_ = 0;
        int i_104_ = 0;
        for (int i_105_ = 0; i_105_ < i; i_105_++) {
            int i_106_ = class23_sub5.readUnsignedByte();
            int i_107_ = 0;
            if ((i_106_ & 0x1) != 0) {
                i_107_ = class23_sub5_58_.gSmart1or2();
            }
            int i_108_ = 0;
            if ((i_106_ & 0x2) != 0) {
                i_108_ = class23_sub5_59_.gSmart1or2();
            }
            int i_109_ = 0;
            if ((i_106_ & 0x4) != 0) {
                i_109_ = class23_sub5_60_.gSmart1or2();
            }
            vertexX[i_105_] = i_102_ + i_107_;
            vertexY[i_105_] = i_103_ + i_108_;
            vertexZ[i_105_] = i_104_ + i_109_;
            i_102_ = vertexX[i_105_];
            i_103_ = vertexY[i_105_];
            i_104_ = vertexZ[i_105_];
            if (i_71_ == 1) {
                vertexSkin[i_105_] = class23_sub5_61_.readUnsignedByte();
            }
        }
        class23_sub5.currentOffset = i_92_;
        class23_sub5_58_.currentOffset = i_83_;
        class23_sub5_59_.currentOffset = i_85_;
        class23_sub5_60_.currentOffset = i_88_;
        class23_sub5_61_.currentOffset = i_86_;
        class23_sub5_62_.currentOffset = i_90_;
        class23_sub5_63_.currentOffset = i_91_;
        for (int i_110_ = 0; i_110_ < i_64_; i_110_++) {
            faceColour[i_110_] = (short) class23_sub5.readUnsignedWord();
            if (i_66_ == 1) {
                faceType[i_110_] = class23_sub5_58_.readSignedByte();
            }
            if (i_67_ == 255) {
                faceLayer[i_110_] = class23_sub5_59_.readSignedByte();
            }
            if (i_68_ == 1) {
                faceTrans[i_110_] = class23_sub5_60_.readSignedByte();
            }
            if (i_69_ == 1) {
                faceLabel[i_110_] = class23_sub5_61_.readUnsignedByte();
            }
            if (i_70_ == 1) {
                faceMaterial[i_110_] = (short) (class23_sub5_62_.readUnsignedWord() - 1);
            }
            if (facesMapping != null) {
                if (faceMaterial[i_110_] != -1) {
                    facesMapping[i_110_] = (byte) (class23_sub5_63_.readUnsignedByte() - 1);
                } else {
                    facesMapping[i_110_] = (byte) -1;
                }
            }
        }
        class23_sub5.currentOffset = i_89_;
        class23_sub5_58_.currentOffset = i_84_;
        int i_111_ = 0;
        int i_112_ = 0;
        int i_113_ = 0;
        int i_114_ = 0;
        for (int i_115_ = 0; i_115_ < i_64_; i_115_++) {
            int i_116_ = class23_sub5_58_.readUnsignedByte();
            if (i_116_ == 1) {
                i_111_ = class23_sub5.gSmart1or2() + i_114_;
                i_114_ = i_111_;
                i_112_ = class23_sub5.gSmart1or2() + i_114_;
                i_114_ = i_112_;
                i_113_ = class23_sub5.gSmart1or2() + i_114_;
                i_114_ = i_113_;
                facesA[i_115_] = i_111_;
                facesB[i_115_] = i_112_;
                facesC[i_115_] = i_113_;
            }
            if (i_116_ == 2) {
                i_112_ = i_113_;
                i_113_ = class23_sub5.gSmart1or2() + i_114_;
                i_114_ = i_113_;
                facesA[i_115_] = i_111_;
                facesB[i_115_] = i_112_;
                facesC[i_115_] = i_113_;
            }
            if (i_116_ == 3) {
                i_111_ = i_113_;
                i_113_ = class23_sub5.gSmart1or2() + i_114_;
                i_114_ = i_113_;
                facesA[i_115_] = i_111_;
                facesB[i_115_] = i_112_;
                facesC[i_115_] = i_113_;
            }
            if (i_116_ == 4) {
                int i_117_ = i_111_;
                i_111_ = i_112_;
                i_112_ = i_117_;
                i_113_ = class23_sub5.gSmart1or2() + i_114_;
                i_114_ = i_113_;
                facesA[i_115_] = i_111_;
                facesB[i_115_] = i_112_;
                facesC[i_115_] = i_113_;
            }
        }
        class23_sub5.currentOffset = i_96_;
        class23_sub5_58_.currentOffset = i_97_;
        class23_sub5_59_.currentOffset = i_98_;
        class23_sub5_60_.currentOffset = i_99_;
        class23_sub5_61_.currentOffset = i_100_;
        class23_sub5_62_.currentOffset = i_101_;
        for (int i_118_ = 0; i_118_ < i_65_; i_118_++) {
            int i_119_ = texturesMapping[i_118_] & 0xff;
            if (i_119_ == 0) {
                texturesMappingP[i_118_] = (short) class23_sub5.readUnsignedWord();
                texturesMappingM[i_118_] = (short) class23_sub5.readUnsignedWord();
                texturesMappingN[i_118_] = (short) class23_sub5.readUnsignedWord();
            }
            if (i_119_ == 1) {
                texturesMappingP[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                texturesMappingM[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                texturesMappingN[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                aShortArray2535[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aShortArray2526[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aShortArray2543[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aByteArray2531[i_118_] = class23_sub5_60_.readSignedByte();
                aByteArray2519[i_118_] = class23_sub5_61_.readSignedByte();
                aByteArray2530[i_118_] = class23_sub5_62_.readSignedByte();
            }
            if (i_119_ == 2) {
                texturesMappingP[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                texturesMappingM[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                texturesMappingN[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                aShortArray2535[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aShortArray2526[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aShortArray2543[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aByteArray2531[i_118_] = class23_sub5_60_.readSignedByte();
                aByteArray2519[i_118_] = class23_sub5_61_.readSignedByte();
                aByteArray2530[i_118_] = class23_sub5_62_.readSignedByte();
                aByteArray2525[i_118_] = class23_sub5_62_.readSignedByte();
                aByteArray2547[i_118_] = class23_sub5_62_.readSignedByte();
            }
            if (i_119_ == 3) {
                texturesMappingP[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                texturesMappingM[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                texturesMappingN[i_118_] = (short) class23_sub5_58_.readUnsignedWord();
                aShortArray2535[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aShortArray2526[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aShortArray2543[i_118_] = (short) class23_sub5_59_.readUnsignedWord();
                aByteArray2531[i_118_] = class23_sub5_60_.readSignedByte();
                aByteArray2519[i_118_] = class23_sub5_61_.readSignedByte();
                aByteArray2530[i_118_] = class23_sub5_62_.readSignedByte();
            }
        }
    }

    private void readOldModel(int i) {
        aBoolean1659 = false;
        Class21 class21 = aClass21Array1661[i];
        vertexCount = class21.vertexCount;
        faceCount = class21.faceCount;
        textureCount = class21.texturedFaceCount;
        vertexX = new int[vertexCount];
        vertexY = new int[vertexCount];
        vertexZ = new int[vertexCount];
        facesA = new int[faceCount];
        facesB = new int[faceCount];
        facesC = new int[faceCount];
        texturesMappingP = new int[textureCount];
        texturesMappingM = new int[textureCount];
        texturesMappingN = new int[textureCount];
        if (class21.anInt376 >= 0)
            vertexSkin = new int[vertexCount];
        if (class21.anInt380 >= 0)
            faceType = new int[faceCount];
        if (class21.anInt381 >= 0)
            faceLayer = new int[faceCount];
        else
            priority = -class21.anInt381 - 1;
        if (class21.anInt382 >= 0)
            faceTrans = new int[faceCount];
        if (class21.anInt383 >= 0)
            faceLabel = new int[faceCount];
        faceColour = new int[faceCount];
        Stream stream = new Stream(class21.aByteArray368);
        stream.currentOffset = class21.anInt372;
        Stream stream_1 = new Stream(class21.aByteArray368);
        stream_1.currentOffset = class21.anInt373;
        Stream stream_2 = new Stream(class21.aByteArray368);
        stream_2.currentOffset = class21.anInt374;
        Stream stream_3 = new Stream(class21.aByteArray368);
        stream_3.currentOffset = class21.anInt375;
        Stream stream_4 = new Stream(class21.aByteArray368);
        stream_4.currentOffset = class21.anInt376;
        int k = 0;
        int l = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < vertexCount; j1++) {
            int k1 = stream.readUnsignedByte();
            int i2 = 0;
            if ((k1 & 1) != 0)
                i2 = stream_1.gSmart1or2();
            int k2 = 0;
            if ((k1 & 2) != 0)
                k2 = stream_2.gSmart1or2();
            int i3 = 0;
            if ((k1 & 4) != 0)
                i3 = stream_3.gSmart1or2();
            vertexX[j1] = k + i2;
            vertexY[j1] = l + k2;
            vertexZ[j1] = i1 + i3;
            k = vertexX[j1];
            l = vertexY[j1];
            i1 = vertexZ[j1];
            if (vertexSkin != null)
                vertexSkin[j1] = stream_4.readUnsignedByte();
        }

        stream.currentOffset = class21.anInt379;
        stream_1.currentOffset = class21.anInt380;
        stream_2.currentOffset = class21.anInt381;
        stream_3.currentOffset = class21.anInt382;
        stream_4.currentOffset = class21.anInt383;
        for (int l1 = 0; l1 < faceCount; l1++) {
            faceColour[l1] = stream.readUnsignedWord();
            if (faceType != null)
                faceType[l1] = stream_1.readUnsignedByte();
            if (faceLayer != null)
                faceLayer[l1] = stream_2.readUnsignedByte();
            if (faceTrans != null)
                faceTrans[l1] = stream_3.readUnsignedByte();
            if (faceLabel != null)
                faceLabel[l1] = stream_4.readUnsignedByte();
        }

        stream.currentOffset = class21.anInt377;
        stream_1.currentOffset = class21.anInt378;
        int j2 = 0;
        int l2 = 0;
        int j3 = 0;
        int k3 = 0;
        for (int l3 = 0; l3 < faceCount; l3++) {
            int i4 = stream_1.readUnsignedByte();
            if (i4 == 1) {
                j2 = stream.gSmart1or2() + k3;
                k3 = j2;
                l2 = stream.gSmart1or2() + k3;
                k3 = l2;
                j3 = stream.gSmart1or2() + k3;
                k3 = j3;
                facesA[l3] = j2;
                facesB[l3] = l2;
                facesC[l3] = j3;
            }
            if (i4 == 2) {
                l2 = j3;
                j3 = stream.gSmart1or2() + k3;
                k3 = j3;
                facesA[l3] = j2;
                facesB[l3] = l2;
                facesC[l3] = j3;
            }
            if (i4 == 3) {
                j2 = j3;
                j3 = stream.gSmart1or2() + k3;
                k3 = j3;
                facesA[l3] = j2;
                facesB[l3] = l2;
                facesC[l3] = j3;
            }
            if (i4 == 4) {
                int k4 = j2;
                j2 = l2;
                l2 = k4;
                j3 = stream.gSmart1or2() + k3;
                k3 = j3;
                facesA[l3] = j2;
                facesB[l3] = l2;
                facesC[l3] = j3;
            }
        }

        stream.currentOffset = class21.anInt384;
        for (int j4 = 0; j4 < textureCount; j4++) {
            texturesMappingP[j4] = stream.readUnsignedWord();
            texturesMappingM[j4] = stream.readUnsignedWord();
            texturesMappingN[j4] = stream.readUnsignedWord();
        }
        if (faceTrans == null) {
            faceTrans = new int[faceCount];
        }
        filterTriangles();
    }

    public void filterTriangles() {
        for (int triangleId = 0; triangleId < faceCount; triangleId++) {
            int l = facesA[triangleId];
            int k1 = facesB[triangleId];
            int j2_ = facesC[triangleId];
            boolean b = true;
            label2:
            for (int triId = 0; triId < faceCount; triId++) {
                if (triId == triangleId)
                    continue label2;
                if (facesA[triId] == l) {
                    b = false;
                    break label2;
                }
                if (facesB[triId] == k1) {
                    b = false;
                    break label2;
                }
                if (facesC[triId] == j2_) {
                    b = false;
                    break label2;
                }
            }
            if (b) {
                if (faceType != null)
                    //	face_render_type[triangleId] = -1;
                    faceTrans[triangleId] = 255;

            }
        }
    }

    public void method464(Model model, boolean flag) {
        vertexCount = model.vertexCount;
        faceCount = model.faceCount;
        textureCount = model.textureCount;
        if (anIntArray1622.length < vertexCount) {
            anIntArray1622 = new int[vertexCount + 100];
            anIntArray1623 = new int[vertexCount + 100];
            anIntArray1624 = new int[vertexCount + 100];
        }
        vertexX = anIntArray1622;
        vertexY = anIntArray1623;
        vertexZ = anIntArray1624;
        for (int k = 0; k < vertexCount; k++) {
            vertexX[k] = model.vertexX[k];
            vertexY[k] = model.vertexY[k];
            vertexZ[k] = model.vertexZ[k];
        }

        if (flag) {
            faceTrans = model.faceTrans;
        } else {
            if (anIntArray1625.length < faceCount)
                anIntArray1625 = new int[faceCount + 100];
            faceTrans = anIntArray1625;
            if (model.faceTrans == null) {
                for (int l = 0; l < faceCount; l++)
                    faceTrans[l] = 0;

            } else {
                System.arraycopy(model.faceTrans, 0, faceTrans, 0, faceCount);

            }
        }
        faceType = model.faceType;
        faceColour = model.faceColour;
        faceLayer = model.faceLayer;
        priority = model.priority;
        triangleSkinTable = model.triangleSkinTable;
        vertexSkinTable = model.vertexSkinTable;
        facesA = model.facesA;
        facesB = model.facesB;
        facesC = model.facesC;
        faceColourA = model.faceColourA;
        faceColourB = model.faceColourB;
        faceColourC = model.faceColourC;
        texturesMapping = model.texturesMapping;
        texturesMappingP = model.texturesMappingP;
        texturesMappingM = model.texturesMappingM;
        texturesMappingN = model.texturesMappingN;
        faceMaterial = model.faceMaterial;
        facesMapping = model.facesMapping;
    }

    private int addVertex(Model model, int i) {
        int j = -1;

        int k = model.vertexX[i & 0xffff];
        int l = model.vertexY[i & 0xffff];
        int i1 = model.vertexZ[i & 0xffff];
        for (int j1 = 0; j1 < vertexCount; j1++) {
            if (k != vertexX[j1 & 0xffff] || l != vertexY[j1 & 0xffff] || i1 != vertexZ[j1 & 0xffff])
                continue;
            j = j1;
            break;
        }

        if (j == -1) {
            vertexX[vertexCount] = k;
            vertexY[vertexCount] = l;
            vertexZ[vertexCount] = i1;
            if (model.vertexSkin != null)
                vertexSkin[vertexCount] = model.vertexSkin[i];
            j = vertexCount++;
        }
        return j;
    }

    public void method466() {
        super.modelHeight = 0;
        anInt1650 = 0;
        anInt1651 = 0;
        for (int i = 0; i < vertexCount; i++) {
            int j = vertexX[i];
            int k = vertexY[i];
            int l = vertexZ[i];
            if (-k > super.modelHeight)
                super.modelHeight = -k;
            if (k > anInt1651)
                anInt1651 = k;
            int i1 = j * j + l * l;
            if (i1 > anInt1650)
                anInt1650 = i1;
        }
        anInt1650 = (int) (Math.sqrt(anInt1650) + 0.98999999999999999D);
        anInt1653 = (int) (Math.sqrt(anInt1650 * anInt1650 + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        anInt1652 = anInt1653 + (int) (Math.sqrt(anInt1650 * anInt1650 + anInt1651 * anInt1651) + 0.98999999999999999D);
    }

    public void method467() {
        super.modelHeight = 0;
        anInt1651 = 0;
        for (int i = 0; i < vertexCount; i++) {
            int j = vertexY[i];
            if (-j > super.modelHeight)
                super.modelHeight = -j;
            if (j > anInt1651)
                anInt1651 = j;
        }

        anInt1653 = (int) (Math.sqrt(anInt1650 * anInt1650 + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        anInt1652 = anInt1653 + (int) (Math.sqrt(anInt1650 * anInt1650 + anInt1651 * anInt1651) + 0.98999999999999999D);
    }

    public void method468() {
        super.modelHeight = 0;
        anInt1650 = 0;
        anInt1651 = 0;
        anInt1646 = 0xf423f;
        anInt1647 = 0xfff0bdc1;
        anInt1648 = 0xfffe7961;
        anInt1649 = 0x1869f;
        for (int j = 0; j < vertexCount; j++) {
            int k = vertexX[j];
            int l = vertexY[j];
            int i1 = vertexZ[j];
            if (k < anInt1646)
                anInt1646 = k;
            if (k > anInt1647)
                anInt1647 = k;
            if (i1 < anInt1649)
                anInt1649 = i1;
            if (i1 > anInt1648)
                anInt1648 = i1;
            if (-l > super.modelHeight)
                super.modelHeight = -l;
            if (l > anInt1651)
                anInt1651 = l;
            int j1 = k * k + i1 * i1;
            if (j1 > anInt1650)
                anInt1650 = j1;
        }

        anInt1650 = (int) Math.sqrt(anInt1650);
        anInt1653 = (int) Math.sqrt(anInt1650 * anInt1650 + super.modelHeight * super.modelHeight);
        anInt1652 = anInt1653 + (int) Math.sqrt(anInt1650 * anInt1650 + anInt1651 * anInt1651);
    }

    public void method469() {
        if (vertexSkin != null) {
            int ai[] = new int[256];
            int j = 0;
            for (int l = 0; l < vertexCount; l++) {
                int j1 = vertexSkin[l];
                ai[j1]++;
                if (j1 > j)
                    j = j1;
            }

            vertexSkinTable = new int[j + 1][];
            for (int k1 = 0; k1 <= j; k1++) {
                vertexSkinTable[k1] = new int[ai[k1]];
                ai[k1] = 0;
            }

            for (int j2 = 0; j2 < vertexCount; j2++) {
                int l2 = vertexSkin[j2];
                vertexSkinTable[l2][ai[l2]++] = j2;
            }

            vertexSkin = null;
        }
        if (faceLabel != null) {
            int ai1[] = new int[256];
            int k = 0;
            for (int i1 = 0; i1 < faceCount; i1++) {
                int l1 = faceLabel[i1];
                ai1[l1]++;
                if (l1 > k)
                    k = l1;
            }

            triangleSkinTable = new int[k + 1][];
            for (int i2 = 0; i2 <= k; i2++) {
                triangleSkinTable[i2] = new int[ai1[i2]];
                ai1[i2] = 0;
            }

            for (int k2 = 0; k2 < faceCount; k2++) {
                int i3 = faceLabel[k2];
                triangleSkinTable[i3][ai1[i3]++] = k2;
            }

            faceLabel = null;
        }
    }

    public void applyTransform(int i) {
        if (vertexSkinTable == null)
            return;
        if (i == -1)
            return;
        FrameReader frameReader = FrameReader.forID(i);
        if (frameReader == null)
            return;
        SkinList skinList = frameReader.mySkinList;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        for (int k = 0; k < frameReader.stepCount; k++) {
            int l = frameReader.opCodeLinkTable[k];
            method472(skinList.transformTypes[l], skinList.labels[l], frameReader.xOffset[k], frameReader.yOffset[k], frameReader.zOffset[k]);
        }

    }

    public void applyTransform(int firstFrame, int nextFrame, int end, int cycle) {

        try {
            if (vertexSkinTable != null && firstFrame != -1) {
                FrameReader currentAnimation = FrameReader.forID(firstFrame);
                if (currentAnimation == null) {
                    return;
                }
                SkinList list1 = currentAnimation.mySkinList;
                if (list1 == null) {
                    return;
                }
                anInt1681 = 0;
                anInt1682 = 0;
                anInt1683 = 0;
                FrameReader nextAnimation = null;
                SkinList list2 = null;
                if (nextFrame != -1) {
                    nextAnimation = FrameReader.forID(nextFrame);
                    if (nextAnimation.mySkinList != list1)
                        nextAnimation = null;
                    list2 = nextAnimation.mySkinList;
                }
                if (nextAnimation == null || list2 == null) {
                    for (int i_263_ = 0; i_263_ < currentAnimation.stepCount; i_263_++) {
                        int i_264_ = currentAnimation.opCodeLinkTable[i_263_];
                        method472(list1.transformTypes[i_264_], list1.labels[i_264_], currentAnimation.xOffset[i_263_], currentAnimation.yOffset[i_263_], currentAnimation.zOffset[i_263_]);

                    }
                } else {
                    for (int i1 = 0; i1 < currentAnimation.stepCount; i1++) {
                        int n1 = currentAnimation.opCodeLinkTable[i1];
                        int opcode = list1.transformTypes[n1];
                        int[] skin = list1.labels[n1];
                        int x = currentAnimation.xOffset[i1];
                        int y = currentAnimation.yOffset[i1];
                        int z = currentAnimation.zOffset[i1];
                        boolean found = false;
                        for (int i2 = 0; i2 < nextAnimation.stepCount; i2++) {
                            int n2 = nextAnimation.opCodeLinkTable[i2];
                            if (list2.labels[n2].equals(skin)) {
                                if (opcode != 2) {
                                    x += (nextAnimation.xOffset[i2] - x) * cycle / end;
                                    y += (nextAnimation.yOffset[i2] - y) * cycle / end;
                                    z += (nextAnimation.zOffset[i2] - z) * cycle / end;
                                } else {
                                    x &= 0x7ff;
                                    y &= 0x7ff;
                                    z &= 0x7ff;
                                    int dx = nextAnimation.xOffset[i2] - x & 0x7ff;
                                    int dy = nextAnimation.yOffset[i2] - y & 0x7ff;
                                    int dz = nextAnimation.zOffset[i2] - z & 0x7ff;
                                    if (dx >= 1024) {
                                        dx -= 2048;
                                    }
                                    if (dy >= 1024) {
                                        dy -= 2048;
                                    }
                                    if (dz >= 1024) {
                                        dz -= 2048;
                                    }
                                    x = x + dx * cycle / end & 0x7ff;
                                    y = y + dy * cycle / end & 0x7ff;
                                    z = z + dz * cycle / end & 0x7ff;
                                }
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            if (opcode != 3 && opcode != 2) {
                                x = x * (end - cycle) / end;
                                y = y * (end - cycle) / end;
                                z = z * (end - cycle) / end;
                            } else if (opcode == 3) {
                                x = (x * (end - cycle) + (cycle << 7)) / end;
                                y = (y * (end - cycle) + (cycle << 7)) / end;
                                z = (z * (end - cycle) + (cycle << 7)) / end;
                            } else {
                                x &= 0x7ff;
                                y &= 0x7ff;
                                z &= 0x7ff;
                                int dx = -x & 0x7ff;
                                int dy = -y & 0x7ff;
                                int dz = -z & 0x7ff;
                                if (dx >= 1024) {
                                    dx -= 2048;
                                }
                                if (dy >= 1024) {
                                    dy -= 2048;
                                }
                                if (dz >= 1024) {
                                    dz -= 2048;
                                }
                                x = x + dx * cycle / end & 0x7ff;
                                y = y + dy * cycle / end & 0x7ff;
                                z = z + dz * cycle / end & 0x7ff;
                            }
                        }
                        method472(opcode, skin, x, y, z);
                    }
                }
            }

        } catch (Exception e) {
            applyTransform(firstFrame);
        }
    }

    public void method471(int ai[], int j, int k) {
        if (k == -1)
            return;
        if (ai == null || j == -1) {
            applyTransform(k);
            return;
        }
        FrameReader frameReader = FrameReader.forID(k);
        if (frameReader == null)
            return;
        FrameReader frameReader_1 = FrameReader.forID(j);
        if (frameReader_1 == null) {
            applyTransform(k);
            return;
        }
        SkinList skinList = frameReader.mySkinList;
        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        int l = 0;
        int i1 = ai[l++];
        for (int j1 = 0; j1 < frameReader.stepCount; j1++) {
            int k1;
            for (k1 = frameReader.opCodeLinkTable[j1]; k1 > i1; i1 = ai[l++]) ;
            if (k1 != i1 || skinList.transformTypes[k1] == 0)
                method472(skinList.transformTypes[k1], skinList.labels[k1], frameReader.xOffset[j1], frameReader.yOffset[j1], frameReader.zOffset[j1]);
        }

        anInt1681 = 0;
        anInt1682 = 0;
        anInt1683 = 0;
        l = 0;
        i1 = ai[l++];
        for (int l1 = 0; l1 < frameReader_1.stepCount; l1++) {
            int i2;
            for (i2 = frameReader_1.opCodeLinkTable[l1]; i2 > i1; i1 = ai[l++]) ;
            if (i2 == i1 || skinList.transformTypes[i2] == 0)
                method472(skinList.transformTypes[i2], skinList.labels[i2], frameReader_1.xOffset[l1], frameReader_1.yOffset[l1], frameReader_1.zOffset[l1]);
        }

    }

    private void method472(int i, int ai[], int j, int k, int l) {
        try {
            int i1 = ai.length;
            if (i == 0) {
                int j1 = 0;
                anInt1681 = 0;
                anInt1682 = 0;
                anInt1683 = 0;
                for (int k2 = 0; k2 < i1; k2++) {
                    int l3 = ai[k2];
                    if (l3 < vertexSkinTable.length) {
                        int ai5[] = vertexSkinTable[l3];
                        for (int i5 = 0; i5 < ai5.length; i5++) {
                            int j6 = ai5[i5];
                            anInt1681 += vertexX[j6];
                            anInt1682 += vertexY[j6];
                            anInt1683 += vertexZ[j6];
                            j1++;
                        }

                    }
                }

                if (j1 > 0) {
                    anInt1681 = anInt1681 / j1 + j;
                    anInt1682 = anInt1682 / j1 + k;
                    anInt1683 = anInt1683 / j1 + l;
                    return;
                } else {
                    anInt1681 = j;
                    anInt1682 = k;
                    anInt1683 = l;
                    return;
                }
            }
            if (i == 1) {
                for (int k1 = 0; k1 < i1; k1++) {
                    int l2 = ai[k1];
                    if (l2 < vertexSkinTable.length) {
                        int ai1[] = vertexSkinTable[l2];
                        for (int i4 = 0; i4 < ai1.length; i4++) {
                            int j5 = ai1[i4];
                            vertexX[j5] += j;
                            vertexY[j5] += k;
                            vertexZ[j5] += l;
                        }

                    }
                }

                return;
            }
            if (i == 2) {
                for (int l1 = 0; l1 < i1; l1++) {
                    int i3 = ai[l1];
                    if (i3 < vertexSkinTable.length) {
                        int ai2[] = vertexSkinTable[i3];
                        for (int j4 = 0; j4 < ai2.length; j4++) {
                            int k5 = ai2[j4];
                            vertexX[k5] -= anInt1681;
                            vertexY[k5] -= anInt1682;
                            vertexZ[k5] -= anInt1683;
                            int k6 = (j & 0xff) * 8;
                            int l6 = (k & 0xff) * 8;
                            int i7 = (l & 0xff) * 8;
                            if (i7 != 0) {
                                int j7 = SINE[i7];
                                int i8 = COSINE[i7];
                                int l8 = vertexY[k5] * j7 + vertexX[k5] * i8 >> 16;
                                vertexY[k5] = vertexY[k5] * i8 - vertexX[k5] * j7 >> 16;
                                vertexX[k5] = l8;
                            }
                            if (k6 != 0) {
                                int k7 = SINE[k6];
                                int j8 = COSINE[k6];
                                int i9 = vertexY[k5] * j8 - vertexZ[k5] * k7 >> 16;
                                vertexZ[k5] = vertexY[k5] * k7 + vertexZ[k5] * j8 >> 16;
                                vertexY[k5] = i9;
                            }
                            if (l6 != 0) {
                                int l7 = SINE[l6];
                                int k8 = COSINE[l6];
                                int j9 = vertexZ[k5] * l7 + vertexX[k5] * k8 >> 16;
                                vertexZ[k5] = vertexZ[k5] * k8 - vertexX[k5] * l7 >> 16;
                                vertexX[k5] = j9;
                            }
                            vertexX[k5] += anInt1681;
                            vertexY[k5] += anInt1682;
                            vertexZ[k5] += anInt1683;
                        }

                    }
                }

                return;
            }
            if (i == 3) {
                for (int i2 = 0; i2 < i1; i2++) {
                    int j3 = ai[i2];
                    if (j3 < vertexSkinTable.length) {
                        int ai3[] = vertexSkinTable[j3];
                        for (int k4 = 0; k4 < ai3.length; k4++) {
                            int l5 = ai3[k4];
                            vertexX[l5] -= anInt1681;
                            vertexY[l5] -= anInt1682;
                            vertexZ[l5] -= anInt1683;
                            vertexX[l5] = (vertexX[l5] * j) / 128;
                            vertexY[l5] = (vertexY[l5] * k) / 128;
                            vertexZ[l5] = (vertexZ[l5] * l) / 128;
                            vertexX[l5] += anInt1681;
                            vertexY[l5] += anInt1682;
                            vertexZ[l5] += anInt1683;
                        }

                    }
                }

                return;
            }
            if (i == 5 && triangleSkinTable != null && faceTrans != null) {
                for (int j2 = 0; j2 < i1; j2++) {
                    int k3 = ai[j2];
                    if (k3 < triangleSkinTable.length) {
                        int ai4[] = triangleSkinTable[k3];
                        for (int l4 = 0; l4 < ai4.length; l4++) {
                            int i6 = ai4[l4];
                            faceTrans[i6] += j * 8;
                            if (faceTrans[i6] < 0)
                                faceTrans[i6] = 0;
                            if (faceTrans[i6] > 255)
                                faceTrans[i6] = 255;
                        }

                    }
                }

            }
        } finally {

        }
    }

    public void method473() {
        for (int j = 0; j < vertexCount; j++) {
            int k = vertexX[j];
            vertexX[j] = vertexZ[j];
            vertexZ[j] = -k;
        }

    }

    public void method474(int i) {
        int k = SINE[i];
        int l = COSINE[i];
        for (int i1 = 0; i1 < vertexCount; i1++) {
            int j1 = vertexY[i1] * l - vertexZ[i1] * k >> 16;
            vertexZ[i1] = vertexY[i1] * k + vertexZ[i1] * l >> 16;
            vertexY[i1] = j1;
        }
    }

    public void method475(int i, int j, int l) {
        for (int i1 = 0; i1 < vertexCount; i1++) {
            vertexX[i1] += i;
            vertexY[i1] += j;
            vertexZ[i1] += l;
        }

    }

    public void method476(int src, int dst) {
        for (int k = 0; k < faceCount; k++) {
            if ((faceColour[k] & 0xffff) == (src & 0xffff))
                faceColour[k] = dst & 0xffff;
        }

    }

    public void method477() {
        for (int j = 0; j < vertexCount; j++)
            vertexZ[j] = -vertexZ[j];

        for (int k = 0; k < faceCount; k++) {
            int l = facesA[k];
            facesA[k] = facesC[k];
            facesC[k] = l;
        }
    }

    public void method478(int i, int j, int l) {
        for (int i1 = 0; i1 < vertexCount; i1++) {
            vertexX[i1] = (vertexX[i1] * i) / 128;
            vertexY[i1] = (vertexY[i1] * l) / 128;
            vertexZ[i1] = (vertexZ[i1] * j) / 128;
        }

    }

    public void method479(int ambient, int contrast, int dirX, int dirY, int dirZ, boolean noLightShare) {
        try {
            int dirLength = (int) Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            int k1 = contrast * dirLength >> 8;
            if (faceColourA == null) {
                faceColourA = new int[faceCount];
                faceColourB = new int[faceCount];
                faceColourC = new int[faceCount];
            }
            calculateNormals();
            if (!noLightShare) {
                sharedNormals = new VertexNormal[vertexCount];
                for (int k2 = 0; k2 < vertexCount; k2++) {
                    VertexNormal vertexNormal = super.vertexNormals[k2];
                    VertexNormal vertexNormal_1 = sharedNormals[k2] = new VertexNormal();
                    vertexNormal_1.x = vertexNormal.x;
                    vertexNormal_1.y = vertexNormal.y;
                    vertexNormal_1.z = vertexNormal.z;
                    vertexNormal_1.magnitude = vertexNormal.magnitude;
                }
            }
            buildColors(dirX, dirY, dirZ, ambient, k1);
            if (noLightShare) {
                method466();
            } else {
                method468();
            }
        } catch (Exception e) {
            return;
        }
    }

    private void calculateNormals() {
        if (super.vertexNormals == null) {
            super.vertexNormals = new VertexNormal[vertexCount];
            for (int l1 = 0; l1 < vertexCount; l1++)
                super.vertexNormals[l1] = new VertexNormal();

        }
        for (int face = 0; face < faceCount; face++) {
            byte type;
            if (faceType == null) {
                type = 0;
            } else {
                type = (byte) faceType[face];
            }

            byte alpha;
            if (faceTrans == null) {
                alpha = 0;
            } else {
                alpha = (byte) faceTrans[face];
            }

            short material;
            if (faceMaterial == null) {
                material = -1;
            } else {
                material = faceMaterial[face];
            }

            int j2 = facesA[face];
            int l2 = facesB[face];
            int i3 = facesC[face];
            int j3 = vertexX[l2] - vertexX[j2];
            int k3 = vertexY[l2] - vertexY[j2];
            int l3 = vertexZ[l2] - vertexZ[j2];
            int i4 = vertexX[i3] - vertexX[j2];
            int j4 = vertexY[i3] - vertexY[j2];
            int k4 = vertexZ[i3] - vertexZ[j2];
            int dx = k3 * k4 - j4 * l3;
            int dy = l3 * i4 - k4 * j3;
            int dz;
            for (dz = j3 * j4 - i4 * k3; dx > 8192 || dy > 8192 || dz > 8192 || dx < -8192 || dy < -8192 || dz < -8192; dz >>= 1) {
                dx >>= 1;
                dy >>= 1;
            }

            int k5 = (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (k5 <= 0)
                k5 = 1;
            dx = (dx * 256) / k5;
            dy = (dy * 256) / k5;
            dz = (dz * 256) / k5;
            if (type == 0) {
                VertexNormal normal = super.vertexNormals[j2];
                normal.x += dx;
                normal.y += dy;
                normal.z += dz;
                normal.magnitude++;

                normal = super.vertexNormals[l2];
                normal.x += dx;
                normal.y += dy;
                normal.z += dz;
                normal.magnitude++;

                normal = super.vertexNormals[i3];
                normal.x += dx;
                normal.y += dy;
                normal.z += dz;
                normal.magnitude++;
            } else {
                if (faceNormal == null) {
                    faceNormal = new FaceNormal[faceCount];
                }

                FaceNormal normal = faceNormal[face] = new FaceNormal();
                normal.x = dx;
                normal.y = dy;
                normal.z = dz;
            }
        }
    }

    public void buildColors(int dirX, int dirY, int dirZ, int ambient, int contrast) {
        for (int face = 0; face < faceCount; ++face) {
            byte type;
            if (this.faceType == null) {
                type = 0;
            } else {
                type = (byte) this.faceType[face];
            }

            byte alpha;
            if (this.faceTrans == null) {
                alpha = 0;
            } else {
                alpha = (byte) this.faceTrans[face];
            }

            short material;
            if (faceMaterial == null) {
                material = -1;
            } else {
                material = faceMaterial[face];
            }

            if (alpha == -2) {
                type = 3;
            }

            if (alpha == -1) {
                type = 2;
            }

            if (material == -1) {
                if (type == 0) {
                    int colour = this.faceColour[face] & 0xffff;
                    VertexNormal normal;
                    if (this.sharedNormals != null && this.sharedNormals[facesA[face]] != null) {
                        normal = this.sharedNormals[facesA[face]];
                    } else {
                        normal = this.vertexNormals[facesA[face]];
                    }

                    int calc = ambient + (dirX * normal.x + dirY * normal.y + dirZ * normal.z) / (contrast * normal.magnitude) << 17;
                    faceColourA[face] = calc | repackHSL(colour, calc >> 17);
                    if (this.sharedNormals != null && this.sharedNormals[facesB[face]] != null) {
                        normal = this.sharedNormals[facesB[face]];
                    } else {
                        normal = this.vertexNormals[facesB[face]];
                    }

                    calc = ambient + (dirX * normal.x + dirY * normal.y + dirZ * normal.z) / (contrast * normal.magnitude) << 17;
                    faceColourB[face] = calc | repackHSL(colour, calc >> 17);
                    if (this.sharedNormals != null && this.sharedNormals[facesC[face]] != null) {
                        normal = this.sharedNormals[facesC[face]];
                    } else {
                        normal = this.vertexNormals[facesC[face]];
                    }

                    calc = ambient + (dirX * normal.x + dirY * normal.y + dirZ * normal.z) / (contrast * normal.magnitude) << 17;
                    faceColourC[face] = calc | repackHSL(colour, calc >> 17);
                } else if (type == 1) {
                    FaceNormal var20 = this.faceNormal[face];
                    int c = ambient + (dirX * var20.x + dirY * var20.y + dirZ * var20.z) / (contrast + contrast / 2) << 17;
                    faceColourA[face] = c | repackHSL(this.faceColour[face] & 0xffff, c >> 17);
                    faceColourC[face] = -1;
                } else if (type == 3) {
                    faceColourA[face] = 128;
                    faceColourC[face] = -1;
                } else {
                    faceColourC[face] = -2;
                }
            } else {
                if (type == 0) {
                    VertexNormal normal;
                    if (this.sharedNormals != null && this.sharedNormals[facesA[face]] != null) {
                        normal = this.sharedNormals[facesA[face]];
                    } else {
                        normal = this.vertexNormals[facesA[face]];
                    }

                    int c = ambient + (dirX * normal.x + dirY * normal.y + dirZ * normal.z) / (contrast * normal.magnitude);
                    faceColourA[face] = method1937(c);
                    if (this.sharedNormals != null && this.sharedNormals[facesB[face]] != null) {
                        normal = this.sharedNormals[facesB[face]];
                    } else {
                        normal = this.vertexNormals[facesB[face]];
                    }

                    c = ambient + (dirX * normal.x + dirY * normal.y + dirZ * normal.z) / (contrast * normal.magnitude);
                    faceColourB[face] = method1937(c);
                    if (this.sharedNormals != null && this.sharedNormals[facesC[face]] != null) {
                        normal = this.sharedNormals[facesC[face]];
                    } else {
                        normal = this.vertexNormals[facesC[face]];
                    }

                    c = ambient + (dirX * normal.x + dirY * normal.y + dirZ * normal.z) / (contrast * normal.magnitude);
                    faceColourC[face] = method1937(c);
                } else if (type == 1) {
                    FaceNormal var20 = this.faceNormal[face];
                    int c = ambient + (dirX * var20.x + dirY * var20.y + dirZ * var20.z) / (contrast + contrast / 2);
                    faceColourA[face] = method1937(c);
                    faceColourC[face] = -1;
                } else {
                    faceColourC[face] = -2;
                }
            }
        }
    }

    private void method1936() {
        for (int var4 = 0; var4 < faceCount; ++var4) {
            short var3 = faceMaterial != null ? faceMaterial[var4] : -1;
            if (var3 == -1) {
                int var1 = faceColour[var4] & 0xffff;
                int var2;
                if (faceColourC[var4] == -1) {
                    var2 = faceColourA[var4] & -131072;
                    faceColourA[var4] = var2 | repackHSL(var1, var2 >> 17);
                } else if (faceColourC[var4] != -2) {
                    var2 = faceColourA[var4] & -131072;
                    faceColourA[var4] = var2 | repackHSL(var1, var2 >> 17);
                    var2 = faceColourB[var4] & -131072;
                    faceColourB[var4] = var2 | repackHSL(var1, var2 >> 17);
                    var2 = faceColourC[var4] & -131072;
                    faceColourC[var4] = var2 | repackHSL(var1, var2 >> 17);
                }
            }
        }
    }

    public void method480(int i, int j, int k, int l, int i1) {
        try {
//            for (int face = 0; face < faceCount; face++) {
//                int a = facesA[face];
//                int b = facesB[face];
//                int c = facesC[face];
//                if (faceType == null) {
//                    int i3 = faceColour[face];
//                    VertexNormal vertexNormal = super.vertexNormals[a];
//                    int k2 = i + (k * vertexNormal.x + l * vertexNormal.y + i1 * vertexNormal.z) / (j * vertexNormal.magnitude);
//                    faceColourA[face] = method481(i3, k2, 0);
//                    vertexNormal = super.vertexNormals[b];
//                    k2 = i + (k * vertexNormal.x + l * vertexNormal.y + i1 * vertexNormal.z) / (j * vertexNormal.magnitude);
//                    faceColourB[face] = method481(i3, k2, 0);
//                    vertexNormal = super.vertexNormals[c];
//                    k2 = i + (k * vertexNormal.x + l * vertexNormal.y + i1 * vertexNormal.z) / (j * vertexNormal.magnitude);
//                    faceColourC[face] = method481(i3, k2, 0);
//                } else if ((faceType[face] & 1) == 0) {
//                    int j3 = faceColour[face];
//                    int k3 = faceType[face];
//                    VertexNormal vertexNormal_1 = super.vertexNormals[a];
//                    int l2 = i + (k * vertexNormal_1.x + l * vertexNormal_1.y + i1 * vertexNormal_1.z) / (j * vertexNormal_1.magnitude);
//                    faceColourA[face] = method481(j3, l2, k3);
//                    vertexNormal_1 = super.vertexNormals[b];
//                    l2 = i + (k * vertexNormal_1.x + l * vertexNormal_1.y + i1 * vertexNormal_1.z) / (j * vertexNormal_1.magnitude);
//                    faceColourB[face] = method481(j3, l2, k3);
//                    vertexNormal_1 = super.vertexNormals[c];
//                    l2 = i + (k * vertexNormal_1.x + l * vertexNormal_1.y + i1 * vertexNormal_1.z) / (j * vertexNormal_1.magnitude);
//                    faceColourC[face] = method481(j3, l2, k3);
//                }
//            }

            vertexNormals = null;
            faceNormal = null;
            sharedNormals = null;
            vertexSkin = null;
            faceLabel = null;
            if (faceType != null) {
                for (int l1 = 0; l1 < faceCount; l1++)
                    if ((faceType[l1] & 2) == 2)
                        return;

            }
            faceColour = null;
        } catch (Exception e) {
            return;
        }
    }

    public final void method482(int j, int k, int l, int i1, int j1, int k1) {
        int i = 0;
        int l1 = Rasterizer.originX;
        int i2 = Rasterizer.originY;
        int j2 = SINE[i];
        int k2 = COSINE[i];
        int l2 = SINE[j];
        int i3 = COSINE[j];
        int j3 = SINE[k];
        int k3 = COSINE[k];
        int l3 = SINE[l];
        int i4 = COSINE[l];
        int j4 = j1 * l3 + k1 * i4 >> 16;
        for (int k4 = 0; k4 < vertexCount; k4++) {
            int l4 = vertexX[k4];
            int i5 = vertexY[k4];
            int j5 = vertexZ[k4];
            if (k != 0) {
                int k5 = i5 * j3 + l4 * k3 >> 16;
                i5 = i5 * k3 - l4 * j3 >> 16;
                l4 = k5;
            }
            if (i != 0) {
                int l5 = i5 * k2 - j5 * j2 >> 16;
                j5 = i5 * j2 + j5 * k2 >> 16;
                i5 = l5;
            }
            if (j != 0) {
                int i6 = j5 * l2 + l4 * i3 >> 16;
                j5 = j5 * i3 - l4 * l2 >> 16;
                l4 = i6;
            }
            l4 += i1;
            i5 += j1;
            j5 += k1;
            int j6 = i5 * i4 - j5 * l3 >> 16;
            j5 = i5 * l3 + j5 * i4 >> 16;
            i5 = j6;
            anIntArray1667[k4] = j5 - j4;
            projected_vertices_x[k4] = l1 + (l4 << 9) / j5;
            projected_vertices_y[k4] = i2 + (i5 << 9) / j5;
            if (textureCount > 0) {
                camera_vertex_x[k4] = l4;
                camera_vertex_y[k4] = i5;
                camera_vertex_z[k4] = j5;
            }
        }

        try {
            method483(false, false, 0);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    public void method443(int i, int j, int k, int l, int i1, int j1, int k1,
                          int l1, int i2) {
        int j2 = l1 * i1 - j1 * l >> 16;
        int k2 = k1 * j + j2 * k >> 16;
        int l2 = anInt1650 * k >> 16;
        int i3 = k2 + l2;
        if (i3 <= 50 || k2 >= 3500)
            return;
        int j3 = l1 * l + j1 * i1 >> 16;
        int k3 = j3 - anInt1650 << Client.log_view_dist;
        if (k3 / i3 >= DrawingArea.centerY)
            return;
        int l3 = j3 + anInt1650 << Client.log_view_dist;
        if (l3 / i3 <= -DrawingArea.centerY)
            return;
        int i4 = k1 * k - j2 * j >> 16;
        int j4 = anInt1650 * j >> 16;
        int k4 = i4 + j4 << Client.log_view_dist;
        if (k4 / i3 <= -DrawingArea.anInt1387)
            return;
        int l4 = j4 + (super.modelHeight * k >> 16);
        int i5 = i4 - l4 << Client.log_view_dist;
        if (i5 / i3 >= DrawingArea.anInt1387)
            return;
        int j5 = l2 + (super.modelHeight * j >> 16);
        boolean flag = false;
        if (k2 - j5 <= 50)
            flag = true;
        boolean flag1 = false;
        if (i2 > 0 && aBoolean1684) {
            int k5 = k2 - l2;
            if (k5 <= 50)
                k5 = 50;
            if (j3 > 0) {
                k3 /= i3;
                l3 /= k5;
            } else {
                l3 /= i3;
                k3 /= k5;
            }
            if (i4 > 0) {
                i5 /= i3;
                k4 /= k5;
            } else {
                k4 /= i3;
                i5 /= k5;
            }
            int i6 = anInt1685 - Rasterizer.originX;
            int k6 = anInt1686 - Rasterizer.originY;
            if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4)
                if (aBoolean1659)
                    anIntArray1688[anInt1687++] = i2;
                else
                    flag1 = true;
        }
        int l5 = Rasterizer.originX;
        int j6 = Rasterizer.originY;
        int l6 = 0;
        int i7 = 0;
        if (i != 0) {
            l6 = SINE[i];
            i7 = COSINE[i];
        }
        for (int j7 = 0; j7 < vertexCount; j7++) {
            int k7 = vertexX[j7];
            int l7 = vertexY[j7];
            int i8 = vertexZ[j7];
            if (i != 0) {
                int j8 = i8 * l6 + k7 * i7 >> 16;
                i8 = i8 * i7 - k7 * l6 >> 16;
                k7 = j8;
            }
            k7 += j1;
            l7 += k1;
            i8 += l1;
            int k8 = i8 * l + k7 * i1 >> 16;
            i8 = i8 * i1 - k7 * l >> 16;
            k7 = k8;
            k8 = l7 * k - i8 * j >> 16;
            i8 = l7 * j + i8 * k >> 16;
            l7 = k8;
            anIntArray1667[j7] = i8 - k2;
            if (i8 >= 50) {
                projected_vertices_x[j7] = l5 + (k7 << Client.log_view_dist) / i8;
                projected_vertices_y[j7] = j6 + (l7 << Client.log_view_dist) / i8;
            } else {
                projected_vertices_x[j7] = -5000;
                flag = true;
            }
            if (flag || textureCount > 0) {
                camera_vertex_x[j7] = k7;
                camera_vertex_y[j7] = l7;
                camera_vertex_z[j7] = i8;
            }
        }

        try {
            method483(flag, flag1, i2);
        } catch (Exception _ex) {
        }
    }

    private void method483(boolean flag, boolean flag1, int i) {
        for (int j = 0; j < anInt1652; j++)
            anIntArray1671[j] = 0;

        for (int k = 0; k < faceCount; k++)
            if (faceColourC[k] != -2) {
                int l = facesA[k];
                int k1 = facesB[k];
                int j2 = facesC[k];
                int i3 = projected_vertices_x[l];
                int l3 = projected_vertices_x[k1];
                int k4 = projected_vertices_x[j2];
                if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
                    aBooleanArray1664[k] = true;
                    int j5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + anInt1653;
                    anIntArrayArray1672[j5][anIntArray1671[j5]++] = k;
                } else {
                    if (flag1 && method486(anInt1685, anInt1686, projected_vertices_y[l], projected_vertices_y[k1], projected_vertices_y[j2], i3, l3, k4)) {
                        anIntArray1688[anInt1687++] = i;
                        flag1 = false;
                    }
                    if ((i3 - l3) * (projected_vertices_y[j2] - projected_vertices_y[k1]) - (projected_vertices_y[l] - projected_vertices_y[k1]) * (k4 - l3) > 0) {
                        aBooleanArray1664[k] = false;
                        aBooleanArray1663[k] = i3 < 0 || l3 < 0 || k4 < 0 || i3 > DrawingArea.centerX || l3 > DrawingArea.centerX || k4 > DrawingArea.centerX;
                        int k5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + anInt1653;
                        anIntArrayArray1672[k5][anIntArray1671[k5]++] = k;
                    }
                }
            }

        if (faceLayer == null) {
            for (int i1 = anInt1652 - 1; i1 >= 0; i1--) {
                int l1 = anIntArray1671[i1];
                if (l1 > 0) {
                    int ai[] = anIntArrayArray1672[i1];
                    for (int j3 = 0; j3 < l1; j3++)
                        method484(ai[j3]);

                }
            }

            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            anIntArray1673[j1] = 0;
            anIntArray1677[j1] = 0;
        }

        for (int i2 = anInt1652 - 1; i2 >= 0; i2--) {
            int k2 = anIntArray1671[i2];
            if (k2 > 0) {
                int ai1[] = anIntArrayArray1672[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    int l5 = faceLayer[l4];
                    int j6 = anIntArray1673[l5]++;
                    anIntArrayArray1674[l5][j6] = l4;
                    if (l5 < 10)
                        anIntArray1677[l5] += i2;
                    else if (l5 == 10)
                        anIntArray1675[j6] = i2;
                    else
                        anIntArray1676[j6] = i2;
                }

            }
        }

        int l2 = 0;
        if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0)
            l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
        int k3 = 0;
        if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0)
            k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
        int j4 = 0;
        if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0)
            j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);
        int i6 = 0;
        int k6 = anIntArray1673[10];
        int ai2[] = anIntArrayArray1674[10];
        int ai3[] = anIntArray1675;
        if (i6 == k6) {
            i6 = 0;
            k6 = anIntArray1673[11];
            ai2 = anIntArrayArray1674[11];
            ai3 = anIntArray1676;
        }
        int i5;
        if (i6 < k6)
            i5 = ai3[i6];
        else
            i5 = -1000;
        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                method484(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 3 && i5 > k3) {
                method484(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 5 && i5 > j4) {
                method484(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            int i7 = anIntArray1673[l6];
            int ai4[] = anIntArrayArray1674[l6];
            for (int j7 = 0; j7 < i7; j7++)
                method484(ai4[j7]);

        }

        while (i5 != -1000) {
            method484(ai2[i6++]);
            if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                i6 = 0;
                ai2 = anIntArrayArray1674[11];
                k6 = anIntArray1673[11];
                ai3 = anIntArray1676;
            }
            if (i6 < k6)
                i5 = ai3[i6];
            else
                i5 = -1000;
        }
    }

    private void method484(int face) {
        if (aBooleanArray1664[face]) {
            method485(face);
            return;
        }
        int a = facesA[face];
        int b = facesB[face];
        int c = facesC[face];
        Rasterizer.clippedHorizontally = aBooleanArray1663[face];
        if (faceTrans == null) {
            Rasterizer.blending_alpha = 0;
        } else {
            Rasterizer.blending_alpha = faceTrans[face] & 0xff;
        }
        if (faceMaterial != null && faceMaterial[face] != -1) {
            int p;
            int m;
            int n;
            if (facesMapping != null && facesMapping[face] != -1) {
                int var8 = facesMapping[face] & 255;
                p = texturesMappingP[var8];
                m = texturesMappingM[var8];
                n = texturesMappingN[var8];
            } else {
                p = a;
                m = b;
                n = c;
            }
            if (faceColourC[face] == -1) {
                Rasterizer.method1138(projected_vertices_y[a], projected_vertices_y[b], projected_vertices_y[c],
                        projected_vertices_x[a], projected_vertices_x[b], projected_vertices_x[c],
                        faceColourA[face], faceColourA[face], faceColourA[face],
                        camera_vertex_x[p], camera_vertex_x[m], camera_vertex_x[n],
                        camera_vertex_y[p], camera_vertex_y[m], camera_vertex_y[n],
                        camera_vertex_z[p], camera_vertex_z[m], camera_vertex_z[n],
                        faceMaterial[face]);
            } else {
                Rasterizer.method1138(projected_vertices_y[a], projected_vertices_y[b], projected_vertices_y[c], projected_vertices_x[a], projected_vertices_x[b], projected_vertices_x[c], faceColourA[face], faceColourB[face], faceColourC[face], camera_vertex_x[p], camera_vertex_x[m], camera_vertex_x[n], camera_vertex_y[p], camera_vertex_y[m], camera_vertex_y[n], camera_vertex_z[p], camera_vertex_z[m], camera_vertex_z[n],
                        faceMaterial[face]);
            }
        } else if (faceColourC[face] == -1) {
            Rasterizer.method1144(projected_vertices_y[a], projected_vertices_y[b], projected_vertices_y[c], projected_vertices_x[a], projected_vertices_x[b], projected_vertices_x[c], hslToRgbTable[faceColourA[face] & 0xffff]);
        } else {
            Rasterizer.method1154(projected_vertices_y[a], projected_vertices_y[b], projected_vertices_y[c], projected_vertices_x[a], projected_vertices_x[b], projected_vertices_x[c], faceColourA[face] & 0xffff, faceColourB[face] & 0xffff, faceColourC[face] & 0xffff);
        }
    }

    private void method485(int face_index) {
        int var2 = Rasterizer.originX;
        int var3 = Rasterizer.originY;
        int var4 = 0;
        int a = facesA[face_index];
        int b = facesB[face_index];
        int c = facesC[face_index];
        int var8 = camera_vertex_z[a];
        int var9 = camera_vertex_z[b];
        int var10 = camera_vertex_z[c];
        if (faceTrans == null) {
            Rasterizer.blending_alpha = 0;
        } else {
            Rasterizer.blending_alpha = faceTrans[face_index] & 255;
        }

        int var11;
        int var12;
        int var13;
        int var14;
        if (var8 >= 50) {
            anIntArray3919[var4] = projected_vertices_x[a];
            anIntArray3925[var4] = projected_vertices_y[a];
            anIntArray3936[var4++] = faceColourA[face_index] & 0xffff;
        } else {
            var11 = camera_vertex_x[a];
            var12 = camera_vertex_y[a];
            var13 = faceColourA[face_index] & 0xffff;
            if (var10 >= 50) {
                var14 = (50 - var8) * Rasterizer.anIntArray841[var10 - var8];
                anIntArray3919[var4] = var2 + (var11 + ((camera_vertex_x[c] - var11) * var14 >> 16) << 9) / 50;
                anIntArray3925[var4] = var3 + (var12 + ((camera_vertex_y[c] - var12) * var14 >> 16) << 9) / 50;
                anIntArray3936[var4++] = var13 + (((faceColourC[face_index] & 0xffff) - var13) * var14 >> 16);
            }

            if (var9 >= 50) {
                var14 = (50 - var8) * Rasterizer.anIntArray841[var9 - var8];
                anIntArray3919[var4] = var2 + (var11 + ((camera_vertex_x[b] - var11) * var14 >> 16) << 9) / 50;
                anIntArray3925[var4] = var3 + (var12 + ((camera_vertex_y[b] - var12) * var14 >> 16) << 9) / 50;
                anIntArray3936[var4++] = var13 + (((faceColourB[face_index] & 0xffff) - var13) * var14 >> 16);
            }
        }

        if (var9 >= 50) {
            anIntArray3919[var4] = projected_vertices_x[b];
            anIntArray3925[var4] = projected_vertices_y[b];
            anIntArray3936[var4++] = faceColourB[face_index] & 0xffff;
        } else {
            var11 = camera_vertex_x[b];
            var12 = camera_vertex_y[b];
            var13 = faceColourB[face_index] & 0xffff;
            if (var8 >= 50) {
                var14 = (50 - var9) * Rasterizer.anIntArray841[var8 - var9];
                anIntArray3919[var4] = var2 + (var11 + ((camera_vertex_x[a] - var11) * var14 >> 16) << 9) / 50;
                anIntArray3925[var4] = var3 + (var12 + ((camera_vertex_y[a] - var12) * var14 >> 16) << 9) / 50;
                anIntArray3936[var4++] = var13 + (((faceColourA[face_index] & 0xffff) - var13) * var14 >> 16);
            }

            if (var10 >= 50) {
                var14 = (50 - var9) * Rasterizer.anIntArray841[var10 - var9];
                anIntArray3919[var4] = var2 + (var11 + ((camera_vertex_x[c] - var11) * var14 >> 16) << 9) / 50;
                anIntArray3925[var4] = var3 + (var12 + ((camera_vertex_y[c] - var12) * var14 >> 16) << 9) / 50;
                anIntArray3936[var4++] = var13 + (((faceColourC[face_index] & 0xffff) - var13) * var14 >> 16);
            }
        }

        if (var10 >= 50) {
            anIntArray3919[var4] = projected_vertices_x[c];
            anIntArray3925[var4] = projected_vertices_y[c];
            anIntArray3936[var4++] = faceColourC[face_index] & 0xffff;
        } else {
            var11 = camera_vertex_x[c];
            var12 = camera_vertex_y[c];
            var13 = faceColourC[face_index] & 0xffff;
            if (var9 >= 50) {
                var14 = (50 - var10) * Rasterizer.anIntArray841[var9 - var10];
                anIntArray3919[var4] = var2 + (var11 + ((camera_vertex_x[b] - var11) * var14 >> 16) << 9) / 50;
                anIntArray3925[var4] = var3 + (var12 + ((camera_vertex_y[b] - var12) * var14 >> 16) << 9) / 50;
                anIntArray3936[var4++] = var13 + (((faceColourB[face_index] & 0xffff) - var13) * var14 >> 16);
            }

            if (var8 >= 50) {
                var14 = (50 - var10) * Rasterizer.anIntArray841[var8 - var10];
                anIntArray3919[var4] = var2 + (var11 + ((camera_vertex_x[a] - var11) * var14 >> 16) << 9) / 50;
                anIntArray3925[var4] = var3 + (var12 + ((camera_vertex_y[a] - var12) * var14 >> 16) << 9) / 50;
                anIntArray3936[var4++] = var13 + (((faceColourA[face_index] & 0xffff) - var13) * var14 >> 16);
            }
        }

        int xa = anIntArray3919[0];
        int xb = anIntArray3919[1];
        int xc = anIntArray3919[2];
        int ya = anIntArray3925[0];
        int yb = anIntArray3925[1];
        int yc = anIntArray3925[2];
        Rasterizer.clippedHorizontally = false;
        int p;
        int n;
        int m;
        int var20;
        if (var4 == 3) {
            if (xa < 0 || xb < 0 || xc < 0 || xa > Rasterizer.centerX || xb > Rasterizer.centerX || xc > Rasterizer.centerX) {
                Rasterizer.clippedHorizontally = true;
            }

            if (faceMaterial != null && faceMaterial[face_index] != -1) {
                if (facesMapping != null && facesMapping[face_index] != -1) {
                    var20 = facesMapping[face_index] & 255;
                    p = texturesMappingP[var20];
                    m = texturesMappingM[var20];
                    n = texturesMappingN[var20];
                } else {
                    p = a;
                    m = b;
                    n = c;
                }

                if (faceColourC[face_index] == -1) {
                    Rasterizer.method1138(ya, yb, yc, xa, xb, xc, faceColourA[face_index], faceColourA[face_index], faceColourA[face_index], camera_vertex_x[p], camera_vertex_x[m], camera_vertex_x[n], camera_vertex_y[p], camera_vertex_y[m], camera_vertex_y[n], camera_vertex_z[p], camera_vertex_z[m], camera_vertex_z[n], faceMaterial[face_index]);
                } else {
                    Rasterizer.method1138(ya, yb, yc, xa, xb, xc,
                            anIntArray3936[0], anIntArray3936[1], anIntArray3936[2],
                            camera_vertex_x[p], camera_vertex_x[m],
                            camera_vertex_x[n], camera_vertex_y[p],
                            camera_vertex_y[m], camera_vertex_y[n],
                            camera_vertex_z[p], camera_vertex_z[m],
                            camera_vertex_z[n],
                            faceMaterial[face_index]);
                }
            } else if (faceColourC[face_index] == -1) {
                Rasterizer.method1144(ya, yb, yc, xa, xb, xc, hslToRgbTable[faceColourA[face_index] & 0xffff]);
            } else {
                Rasterizer.method1154(ya, yb, yc, xa, xb, xc, anIntArray3936[0], anIntArray3936[1], anIntArray3936[2]);
            }
        }

        if (var4 == 4) {
            if (xa < 0 || xb < 0 || xc < 0 || xa > Rasterizer.centerX || xb > Rasterizer.centerX || xc > Rasterizer.centerX || anIntArray3919[3] < 0 || anIntArray3919[3] > Rasterizer.centerX) {
                Rasterizer.clippedHorizontally = true;
            }

            if (faceMaterial != null && faceMaterial[face_index] != -1) {
                if (facesMapping != null && facesMapping[face_index] != -1) {
                    var20 = facesMapping[face_index] & 255;
                    p = texturesMappingP[var20];
                    m = texturesMappingM[var20];
                    n = texturesMappingN[var20];
                } else {
                    p = a;
                    m = b;
                    n = c;
                }

                short textureid = faceMaterial[face_index];
                if (faceColourC[face_index] == -1) {
                    Rasterizer.method1138(ya, yb, yc, xa, xb, xc, faceColourA[face_index], faceColourA[face_index], faceColourA[face_index], camera_vertex_x[p], camera_vertex_x[m], camera_vertex_x[n], camera_vertex_y[p], camera_vertex_y[m], camera_vertex_y[n], camera_vertex_z[p], camera_vertex_z[m], camera_vertex_z[n], textureid);
                    Rasterizer.method1138(ya, yc, anIntArray3925[3], xa, xc, anIntArray3919[3], faceColourA[face_index], faceColourA[face_index], faceColourA[face_index], camera_vertex_x[p], camera_vertex_x[m], camera_vertex_x[n], camera_vertex_y[p], camera_vertex_y[m], camera_vertex_y[n], camera_vertex_z[p], camera_vertex_z[m], camera_vertex_z[n], textureid);
                } else {
                    Rasterizer.method1138(ya, yb, yc, xa, xb, xc, anIntArray3936[0], anIntArray3936[1], anIntArray3936[2], camera_vertex_x[p], camera_vertex_x[m], camera_vertex_x[n], camera_vertex_y[p], camera_vertex_y[m], camera_vertex_y[n], camera_vertex_z[p], camera_vertex_z[m], camera_vertex_z[n], textureid);
                    Rasterizer.method1138(ya, yc, anIntArray3925[3], xa, xc, anIntArray3919[3], anIntArray3936[0], anIntArray3936[2], anIntArray3936[3], camera_vertex_x[p], camera_vertex_x[m], camera_vertex_x[n], camera_vertex_y[p], camera_vertex_y[m], camera_vertex_y[n], camera_vertex_z[p], camera_vertex_z[m], camera_vertex_z[n], textureid);
                }
            } else if (faceColourC[face_index] == -1) {
                p = hslToRgbTable[faceColourA[face_index] & 0xffff];
                Rasterizer.method1144(ya, yb, yc, xa, xb, xc, p);
                Rasterizer.method1144(ya, yc, anIntArray3925[3], xa, xc, anIntArray3919[3], p);
            } else {
                Rasterizer.method1154(ya, yb, yc, xa, xb, xc, anIntArray3936[0], anIntArray3936[1], anIntArray3936[2]);
                Rasterizer.method1154(ya, yc, anIntArray3925[3], xa, xc, anIntArray3919[3], anIntArray3936[0], anIntArray3936[2], anIntArray3936[3]);
            }
        }

    }

    private boolean method486(int i, int j, int k, int l, int i1, int j1, int k1,
                              int l1) {
        if (j < k && j < l && j < i1)
            return false;
        if (j > k && j > l && j > i1)
            return false;
        return !(i < j1 && i < k1 && i < l1) && (i <= j1 || i <= k1 || i <= l1);
    }

    public void postLoad() {
        removeInvalidTextures();
    }

    public void removeInvalidTextures() {
        if (facesMapping == null) {
            return;
        }
        removeHDTextures();
        int[] textureUsage = new int[textureCount];
        for (int i = 0; i < faceCount; ++i) {
            if (facesMapping[i] != -1) {
                textureUsage[facesMapping[i] & 0xff]++;
            }
        }
        int numTextures = 0;
        for (int i = 0; i < textureCount; ++i) {
            if (textureUsage[i] > 0 && texturesMapping[i] == 0) {
                numTextures++;
            }
        }
        int[] newTextureMappingP = new int[numTextures];
        int[] newTextureMappingM = new int[numTextures];
        int[] newTextureMappingN = new int[numTextures];
        numTextures = 0;

        int[] mappingTable = new int[faceCount];
        for (int i = 0; i < textureCount; ++i) {
            if (textureUsage[i] > 0 && texturesMapping[i] == 0) {
                newTextureMappingP[numTextures] = texturesMappingP[i] & 0xffff;
                newTextureMappingM[numTextures] = texturesMappingM[i] & 0xffff;
                newTextureMappingN[numTextures] = texturesMappingN[i] & 0xffff;
                mappingTable[i] = numTextures++;
            } else {
                mappingTable[i] = -1;
            }
        }
        this.texturesMappingP = newTextureMappingP;
        this.texturesMappingM = newTextureMappingM;
        this.texturesMappingN = newTextureMappingN;
        this.textureCount = numTextures;

        for (int i = 0; i < faceCount; ++i) {
            if (facesMapping[i] != -1) {
                facesMapping[i] = (byte) mappingTable[facesMapping[i] & 255];
                if (facesMapping[i] == -1 && faceMaterial != null) {
                    faceMaterial[i] = -1;
                }
            } else {
                facesMapping[i] = -1;
            }
        }
    }

    private void removeHDTextures() {
        if (faceMaterial == null) {
            return;
        }
        for (int i = 0; i < faceCount; ++i) {
            if (isHdTexture(faceMaterial[i] & 0xffff)) {
                faceMaterial[i] = -1;
            }
        }
    }
}
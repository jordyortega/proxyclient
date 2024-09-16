package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class FrameReader {


	public static void method528(int i) {
		animationlist = new FrameReader[4000][0];
    	}

   	public static void load(int file, byte[] fileData){
    		try {
	    		Stream stream = new Stream(fileData);
            		SkinList skinList = new SkinList(stream, 0);
        		int k1 = stream.readUnsignedWord();
			animationlist[file] = new FrameReader[(int) (k1 * 3.0)];
	    		int ai[] = new int[500];
        		int ai1[] = new int[500];
        		int ai2[] = new int[500];
        		int ai3[] = new int[500];
        	for(int l1 = 0; l1 < k1; l1++) {
            		int i2 = stream.readUnsignedWord();
            		FrameReader frameReader = animationlist[file][i2] = new FrameReader();
            		frameReader.mySkinList = skinList;
            		int j2 = stream.readUnsignedByte();
            		int l2 = 0;
			int k2 = -1;
            for(int i3 = 0; i3 < j2; i3++) {
                	int j3 = stream.readUnsignedByte();
                if(j3 > 0) {
                    if(skinList.opcodes[i3] != 0) {
                        for(int l3 = i3 - 1; l3 > k2; l3--) {
                            if(skinList.opcodes[l3] != 0)
                                continue;
                            		ai[l2] = l3;
                            		ai1[l2] = 0;
                            		ai2[l2] = 0;
                            		ai3[l2] = 0;
                            		l2++;
                            	break;
                        	}
                    	}
                    		ai[l2] = i3;
                    		short c = 0;
                    	if(skinList.opcodes[i3] == 3)
                        	c = (short)128;
                    	if((j3 & 1) != 0)
                        	ai1[l2] = (short)stream.readShort2();
                   	else
                        	ai1[l2] = c;
                    	if((j3 & 2) != 0)
                        	ai2[l2] = stream.readShort2();
                    	else
                        	ai2[l2] = c;
                    	if((j3 & 4) != 0)
                        	ai3[l2] = stream.readShort2();
                    	else
                        	ai3[l2] = c;
                    		k2 = i3;
                    		l2++;
            			}
			}
            			frameReader.stepCount = l2;
            			frameReader.opCodeLinkTable = new int[l2];
            			frameReader.xOffset = new int[l2];
            			frameReader.yOffset = new int[l2];
            			frameReader.zOffset = new int[l2];
            		for(int k3 = 0; k3 < l2; k3++) {
                		frameReader.opCodeLinkTable[k3] = ai[k3];
               		 	frameReader.xOffset[k3] = ai1[k3];
                		frameReader.yOffset[k3] = ai2[k3];
                		frameReader.zOffset[k3] = ai3[k3];
            			}
        		}
      		}catch(Exception exception) { }
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
        	if(animationlist[file].length == 0) {
				Client.onDemandFetcher.method558(1, file);
                	return null;
       		}
        		return animationlist[file][k];
		} catch(Exception e) {
			e.printStackTrace();
		return null;
		}
    	}


    
	public static boolean isNullFrame(int i) {
		return i == -1;
	}

	private FrameReader() {
	}
    private static FrameReader animationlist[][];
	public int displayLength;
	public SkinList mySkinList;
	public int stepCount;
	public int opCodeLinkTable[];
	public int xOffset[];
	public int yOffset[];
	public int zOffset[];

}

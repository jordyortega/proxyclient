import java.io.FileNotFoundException;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class RSInterface {
	
    public static void addTransparentSprite(int id, int spriteId, String spriteName, int op)
    {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 10;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.mOverInterToTrigger = 52;
        tab.sprite1 = imageLoader(spriteId, spriteName);
        tab.sprite2 = imageLoader(spriteId, spriteName);
        tab.width = 512;
        tab.height = 334;
        tab.aByte254 = (byte)op;
        tab.drawsTransparent = true;
    }

	public void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}

	public static void unpack(StreamLoader streamLoader,TextDrawingArea textDrawingAreas[], StreamLoader streamLoader_1) throws FileNotFoundException {
		aMRUNodes_238 = new MRUNodes(55000);
		Stream stream = new Stream(streamLoader.getDataForName("data"));
		int i = -1;
		int j = stream.readUnsignedWord();
		interfaceCache = new RSInterface[j + 999999];
		while (stream.currentOffset < stream.buffer.length) {
			int k = stream.readUnsignedWord();
			if (k == 65535) {
				i = stream.readUnsignedWord();
				k = stream.readUnsignedWord();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = stream.readUnsignedByte();
			rsInterface.atActionType = stream.readUnsignedByte();
			rsInterface.contentType = stream.readUnsignedWord();
			rsInterface.width = stream.readUnsignedWord();
			rsInterface.height = stream.readUnsignedWord();
			rsInterface.aByte254 = (byte) stream.readUnsignedByte();
			rsInterface.mOverInterToTrigger = stream.readUnsignedByte();
			if (rsInterface.mOverInterToTrigger != 0)
				rsInterface.mOverInterToTrigger = (rsInterface.mOverInterToTrigger - 1 << 8)
						+ stream.readUnsignedByte();
			else
				rsInterface.mOverInterToTrigger = -1;
			int i1 = stream.readUnsignedByte();
			if (i1 > 0) {
				rsInterface.anIntArray245 = new int[i1];
				rsInterface.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.anIntArray245[j1] = stream.readUnsignedByte();
					rsInterface.anIntArray212[j1] = stream.readUnsignedWord();
				}

			}
			int k1 = stream.readUnsignedByte();
			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = stream.readUnsignedWord();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++) {
						rsInterface.valueIndexArray[l1][l4] = stream
								.readUnsignedWord();	
					}
				}

			}
			if (rsInterface.type == 0) {
				rsInterface.drawsTransparent = false;
				rsInterface.scrollMax = stream.readUnsignedWord();
				rsInterface.isMouseoverTriggered = stream.readUnsignedByte() == 1;
				int i2 = stream.readUnsignedWord();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					rsInterface.children[j3] = stream.readUnsignedWord();
					rsInterface.childX[j3] = stream.readSignedWord();
					rsInterface.childY[j3] = stream.readSignedWord();
				}
			}
			if (rsInterface.type == 1) {
				stream.readUnsignedWord();
				stream.readUnsignedByte();
			}
			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.aBoolean259 = stream.readUnsignedByte() == 1;
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.usableItemInterface = stream.readUnsignedByte() == 1;
				rsInterface.aBoolean235 = stream.readUnsignedByte() == 1;
				rsInterface.invSpritePadX = stream.readUnsignedByte();
				rsInterface.invSpritePadY = stream.readUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = stream.readUnsignedByte();
					if (k3 == 1) {
						rsInterface.spritesX[j2] = stream.readSignedWord();
						rsInterface.spritesY[j2] = stream.readSignedWord();
						String s1 = stream.readString();
						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(
									Integer.parseInt(s1.substring(i5 + 1)),
									streamLoader_1, s1.substring(0, i5));
						}
					}
				}
				rsInterface.actions = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = stream.readString();
					if (rsInterface.actions[l3].length() == 0)
						rsInterface.actions[l3] = null;
					if(rsInterface.parentID == 3824)
                		rsInterface.actions[4] = "Buy 50";
					if (rsInterface.parentID == 1644)
						rsInterface.actions[2] = "Operate";
				}
			}
			if (rsInterface.type == 3)
				rsInterface.aBoolean227 = stream.readUnsignedByte() == 1;
			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int k2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
			}
			if (rsInterface.type == 4) {
				//rsInterface.message = stream.readString();
				//rsInterface.aString228 = stream.readString();
				rsInterface.message = stream.readString().replaceAll("RuneScape", "Web3Scape");
				rsInterface.aString228 = stream.readString();
			}
			if (rsInterface.type == 1 || rsInterface.type == 3
					|| rsInterface.type == 4)
				rsInterface.textColor = stream.readDWord();
			if (rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.anInt219 = stream.readDWord();
				rsInterface.anInt216 = stream.readDWord();
				rsInterface.anInt239 = stream.readDWord();
			}
			if (rsInterface.type == 5) {
				rsInterface.drawsTransparent = false;
				String s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.sprite1 = method207(
							Integer.parseInt(s.substring(i4 + 1)),
							streamLoader_1, s.substring(0, i4));
				}
				s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.sprite2 = method207(
							Integer.parseInt(s.substring(j4 + 1)),
							streamLoader_1, s.substring(0, j4));
				}
			}
			if (rsInterface.type == 6) {
				int l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt233 = 1;
					rsInterface.mediaID = (l - 1 << 8)
							+ stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8)
							+ stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt257 = (l - 1 << 8)
							+ stream.readUnsignedByte();
				else
					rsInterface.anInt257 = -1;
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt258 = (l - 1 << 8)
							+ stream.readUnsignedByte();
				else
					rsInterface.anInt258 = -1;
				rsInterface.modelZoom = stream.readUnsignedWord();
				rsInterface.modelRotation1 = stream.readUnsignedWord();
				rsInterface.modelRotation2 = stream.readUnsignedWord();
			}
			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width
						* rsInterface.height];
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int l2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
				rsInterface.textColor = stream.readDWord();
				rsInterface.invSpritePadX = stream.readSignedWord();
				rsInterface.invSpritePadY = stream.readSignedWord();
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.actions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = stream.readString();
					if (rsInterface.actions[k4].length() == 0)
						rsInterface.actions[k4] = null;
				}

			}
			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = stream.readString();
				rsInterface.spellName = stream.readString();
				rsInterface.spellUsableOn = stream.readUnsignedWord();
			}

			if (rsInterface.type == 8)
				rsInterface.message = stream.readString();

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4
					|| rsInterface.atActionType == 5
					|| rsInterface.atActionType == 6) {
				rsInterface.tooltip = stream.readString();
				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1)
						rsInterface.tooltip = "Ok";
					if (rsInterface.atActionType == 4)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 5)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 6)
						rsInterface.tooltip = "Continue";
				}
			}
		}
		aClass44 = streamLoader;
		Interfaces.loadInterfaces(streamLoader, textDrawingAreas);
		
		aMRUNodes_238 = null;
	}
	
	public static void drawTooltip(int id, String text) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.parentID = id;
		rsinterface.type = 0;
		rsinterface.isMouseoverTriggered = true;
		rsinterface.mOverInterToTrigger = -1;
		addTooltipBox(id + 1, text);
		rsinterface.totalChildren(1);
		rsinterface.child(0, id + 1, 0, 0);
	}

	   public static void addTextButton(int i, String s, String tooltip, int k, boolean l, boolean m, TextDrawingArea[] TDA, int j, int w) {
	        RSInterface rsinterface = addInterface(i);
	        rsinterface.parentID = i;
	        rsinterface.id = i;
	        rsinterface.type = 4;
	        rsinterface.atActionType = 1;
	        rsinterface.width = w;
	        rsinterface.height = 16;
	        rsinterface.contentType = 0;
	        rsinterface.aByte254 = (byte)0xFF981F;
	        rsinterface.mOverInterToTrigger = -1;
	        rsinterface.centerText = l;
	        rsinterface.textShadow = m;
	        rsinterface.textDrawingAreas = TDA[j];
	        rsinterface.message = s;
	        rsinterface.aString228 = "";
	        rsinterface.anInt219 = 0xFF981F;
	        rsinterface.textColor = 0xFF981F;
	        rsinterface.tooltip = tooltip;
	   }
	   
	   public static void teleportInterfaceScroll(TextDrawingArea[] tda) {
		   RSInterface rsInterface = addInterface(48000);
		   RSInterface scrollInterface = addInterface(47000);
		   
		   	//main init
	        addSprite(48001, 0, "Interfaces/Teleports/background");
	        addHoverButton(48002, "Interfaces/Teleports/close", 0, 16, 16, "Close window", 0, 48003, 1);
	        addHoveredButton(48003, "Interfaces/Teleports/close", 1, 16, 16, 48004);
	        addTextButton(48005, "Skilling Teleports", "", 0xFF981F, true, true, tda, 2, 150); //tda[2] = bold
	        
	        addTextButton(47025, "Titlehere", "", 0xFF981F, false, true, tda, 2, 150); //tda[2] = bold
	        addHoverText(47026, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(47027, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(47028, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(47029, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(47030, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(47031, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        addHoverText(47032, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        addHoverText(47033, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        addHoverText(47034, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        addHoverText(47035, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        addHoverText(47036, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        addHoverText(47037, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        
	        //scroll init
	        addHoverText(47024, "Skilling Zone", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47001, "Agility", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47002, "Attack", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47003, "Construction", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47004, "Cooking", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47005, "Crafting", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47006, "Defence", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47007, "Farming", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47008, "Firemaking", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47009, "Fishing", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47010, "Fletching", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47011, "Herblore", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47012, "Hitpoints", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47013, "Hunter", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47014, "Magic", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47015, "Mining", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47016, "Prayer", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47017, "Ranging", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47018, "Runecrafting", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47019, "Slayer", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47020, "Smithing", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47021, "Strength", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47022, "Thieving", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(47023, "Woodcutting", "", tda, 2, 0xFF981F, false, true, 150);
	        
	        //set default values
	        rsInterface.scrollMax = 0;
	    	scrollInterface.width = 138;
	    	scrollInterface.height = 263;
	    	scrollInterface.scrollMax = 482;
	        
	        //main coords
	        setChildren(18, rsInterface);
	        rsInterface.child(0, 48001, 90, 20);
	        rsInterface.child(1, 48002, 401, 28);
	        rsInterface.child(2, 48003, 401, 28);
	        rsInterface.child(3, 48005, 80, 27);
	        rsInterface.child(4, 47000, 100, 49);
	        
	        for (int j = 5; j < 18; j++) {
	        	rsInterface.child(j, 47025 + (j - 5), 262, 51 + ((j - 5) * 20));
	        }
	        
	        //scroll coords
	        setChildren(24, scrollInterface);
	        scrollInterface.child(23, 47024, 0, 2);
	        for (int j = 0; j < 24 - 1; j++) {
	        	scrollInterface.child(j, 47001 + j, 0, 0 + ((j + 1) * 20));
	        }
	   }
	   
	   public static void teleportInterface(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(49000);
	        addSprite(49001, 0, "Interfaces/Teleports/background");
	        addHoverButton(49002, "Interfaces/Teleports/close", 0, 16, 16, "Close window", 0, 49003, 1);
	        addHoveredButton(49003, "Interfaces/Teleports/close", 1, 16, 16, 49004);
	        addTextButton(49005, "Monster Teleports", "", 0xFF981F, true, true, tda, 2, 150); //tda[2] = bold
	        addHoverText(49006, "Training Zones", "", tda, 2, 0xFF981F, false, true, 150); //tda[1] = normal
	        addHoverText(49007, "Islands", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49008, "Desert", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49009, "Snow", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49010, "Dungeons", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49011, "Slayer", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49012, "Sewers", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49013, "", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49014, "", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49015, "", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49016, "", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49017, "", "", tda, 2, 0xFF981F, false, true, 150);
	        addHoverText(49018, "", "", tda, 2, 0xFF981F, false, true, 150);
	        
	        addTextButton(49019, "SUBTITLE", "", 0xFF981F, false, true, tda, 2, 150); //tda[2] = bold
	        addHoverText(49020, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49021, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49022, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49023, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49024, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49025, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49026, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49027, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49028, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49029, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49030, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        addHoverText(49031, "subtexthere", "", tda, 1, 0xFF981F, false, true, 150);
	        
	        setChildren(30, rsInterface);
	        rsInterface.child(0, 49001, 90, 20);
	        rsInterface.child(1, 49002, 401, 28);
	        rsInterface.child(2, 49003, 401, 28);
	        rsInterface.child(3, 49005, 180, 27);
	        
	        rsInterface.child(4, 49006, 100, 51);
	        rsInterface.child(5, 49007, 100, 71);
	        rsInterface.child(6, 49008, 100, 91);
	        rsInterface.child(7, 49009, 100, 111);
	        rsInterface.child(8, 49010, 100, 131);
	        rsInterface.child(9, 49011, 100, 151);
	        rsInterface.child(10, 49012, 100, 171);
	        rsInterface.child(11, 49013, 100, 191);
	        rsInterface.child(12, 49014, 100, 211);
	        rsInterface.child(13, 49015, 100, 231);
	        rsInterface.child(14, 49016, 100, 251);
	        rsInterface.child(15, 49017, 100, 271);
	        rsInterface.child(16, 49018, 100, 291);
	        
	        rsInterface.child(17, 49019, 262, 51);
	        rsInterface.child(18, 49020, 262, 71);
	        rsInterface.child(19, 49021, 262, 91);
	        rsInterface.child(20, 49022, 262, 111);
	        rsInterface.child(21, 49023, 262, 131);
	        rsInterface.child(22, 49024, 262, 151);
	        rsInterface.child(23, 49025, 262, 171);
	        rsInterface.child(24, 49026, 262, 191);
	        rsInterface.child(25, 49027, 262, 211);
	        rsInterface.child(26, 49028, 262, 231);
	        rsInterface.child(27, 49029, 262, 251);
	        rsInterface.child(28, 49030, 262, 271);
	        rsInterface.child(29, 49031, 262, 291);
	   }
	   
	   public static void smallTeleportInterface(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(49500);
	        addSprite(49501, 1, "Interfaces/Teleports/background");
	        addHoverButton(49502, "Interfaces/Teleports/close", 0, 16, 16, "Close window", 0, 49503, 1);
	        addHoveredButton(49503, "Interfaces/Teleports/close", 1, 16, 16, 49504);
	        addTextButton(49505, "Minigame Teleports", "", 0xFF981F, false, true, tda, 2, 150); //tda[2] = bold
	        
	        addHoverText(49506, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49507, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49508, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49509, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49510, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49511, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49512, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49513, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49514, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49515, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49516, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49517, "", "", tda, 2, 0xFF981F, true, true, 170);
	        addHoverText(49518, "", "", tda, 2, 0xFF981F, true, true, 170);
	        
	        setChildren(17, rsInterface);
	        rsInterface.child(0, 49501, 165, 20);
	        rsInterface.child(1, 49502, 330, 28);
	        rsInterface.child(2, 49503, 330, 28);
	        rsInterface.child(3, 49505, 175, 27);
	        rsInterface.child(4, 49506, 175, 50);
	        rsInterface.child(5, 49507, 175, 70);
	        rsInterface.child(6, 49508, 175, 90);
	        rsInterface.child(7, 49509, 175, 110);
	        rsInterface.child(8, 49510, 175, 130);
	        rsInterface.child(9, 49511, 175, 150);
	        rsInterface.child(10, 49512, 175, 170);
	        rsInterface.child(11, 49513, 175, 190);
	        rsInterface.child(12, 49514, 175, 210);
	        rsInterface.child(13, 49515, 175, 230);
	        rsInterface.child(14, 49516, 175, 250);
	        rsInterface.child(15, 49517, 175, 270);
	        rsInterface.child(16, 49518, 175, 290);
	   }
	   
	   public static void achievementsInterfaceScroll(TextDrawingArea[] tda) {
		   RSInterface rsInterface = addInterface(61000);
		   RSInterface scrollInterface = addInterface(60000);
		   
		   	//main init
	        addSprite(61001, 2, "Interfaces/Teleports/background");
	        addHoverButton(61002, "Interfaces/Teleports/close", 0, 16, 16, "Close window", 0, 61003, 1);
	        addHoveredButton(61003, "Interfaces/Teleports/close", 1, 16, 16, 61004);
	        addTextButton(61005, "Achievements", "", 0xFF981F, true, true, tda, 2, 150); //tda[2] = bold
	        
	        addTextButton(60025, "Titlehere", "", 0xFF981F, false, true, tda, 2, 150); //tda[2] = bold
        	addHoverText(60026, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
        	addHoverText(60027, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
        	addHoverText(60028, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        
	        for (int i = 0; i < (250); i++) {
	        	addHoverText(60029 + i, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        }
	        
	        //scroll init
	        addHoverText(60024, "Easy", "", tda, 2, 0xFF981F, false, true, 120);
	        addHoverText(60001, "Medium", "", tda, 2, 0xFF981F, false, true, 120);
	        addHoverText(60002, "Hard", "", tda, 2, 0xFF981F, false, true, 120);
	        addHoverText(60003, "Extreme", "", tda, 2, 0xFF981F, false, true, 120);
	        
	        //set default values
	        rsInterface.scrollMax = 0;
	    	scrollInterface.width = 241;
	    	scrollInterface.height = 263;
	    	scrollInterface.scrollMax = 5500;
	        
	        //main coords
	        setChildren(9, rsInterface);
	        rsInterface.child(0, 61001, 60, 20);
	        rsInterface.child(1, 61002, 435, 28);
	        rsInterface.child(2, 61003, 435, 28);
	        rsInterface.child(3, 61005, 40, 28);
	        rsInterface.child(4, 60000, 194, 49);
	        
	        // left side menu
	        rsInterface.child(5, 60024, 69, 51);
	        rsInterface.child(6, 60001, 69, 71);
	        rsInterface.child(7, 60002, 69, 91);
	        rsInterface.child(8, 60003, 69, 111);
	        
	        //scroll coords right side menu
	        setChildren(253, scrollInterface);
	        for (int j = 0; j < 253; j++) {
		        scrollInterface.child(j, 60025 + j, 0, 2 + (20 * j));
	        }
	   }
	   
	   public static void questsInterfaceScroll(TextDrawingArea[] tda) {
		   RSInterface rsInterface = addInterface(58000);
		   RSInterface scrollInterface = addInterface(57000);
		   RSInterface scrollInterfaceLeft = addInterface(56000);
		   
		   	//main init
	        addSprite(58001, 3, "Interfaces/Teleports/background");
	        addHoverButton(58002, "Interfaces/Teleports/close", 0, 16, 16, "Close window", 0, 58003, 1);
	        addHoveredButton(58003, "Interfaces/Teleports/close", 1, 16, 16, 58004);
	        addTextButton(58005, "Quests", "", 0xFF981F, true, true, tda, 2, 150); //tda[2] = bold
	        
	        addTextButton(57025, "Titlehere", "", 0xFF981F, false, true, tda, 2, 242); //tda[2] = bold
	        // addHoverText(57026, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        addWrappingText(57026, "journal here", tda, 1, 0xFF981F, false, true, 242); // tda[1] = hover
	        //addHoverText(57027, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        //addHoverText(57028, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        //addHoverText(57029, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        //addHoverText(57030, "placehere", "", tda, 1, 0xFF981F, false, true, 150);
	        //addHoverText(57031, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        //addHoverText(57032, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        //addHoverText(57033, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        //addHoverText(57034, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        //addHoverText(57035, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        //addHoverText(57036, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        //addHoverText(57037, "placehere", "", tda, 1, 0xFF981F, false, true, 150); 
	        
	        //scroll init	        
	        for (int i = 0; i < 300; i++) {
	        	addHoverText(56001 + i, "Quest here", "", tda, 2, 0xFF981F, false, true, 132);
	        }
	        
	        //set default values ******
	        // rsInterface.scrollMax = 0;
	        
	    	scrollInterface.width = 243;
	    	scrollInterface.height = 264;
	    	scrollInterface.scrollMax = 2000;
	    	
	      	scrollInterfaceLeft.width = 133;
	      	scrollInterfaceLeft.height = 264;
	      	scrollInterfaceLeft.scrollMax = 6600;
	        
	        //main coords
	        setChildren(6, rsInterface);
	        rsInterface.child(0, 58001, 45, 20);
	        rsInterface.child(1, 58002, 450, 28);
	        rsInterface.child(2, 58003, 450, 28);
	        rsInterface.child(3, 58005, 2, 28);
	        rsInterface.child(4, 57000, 208, 48);
	        rsInterface.child(5, 56000, 54, 48);
	        
	        // left side menu
	        setChildren(300, scrollInterfaceLeft);
	        for (int j = 0; j < 300; j++) {
	        	scrollInterfaceLeft.child(j, 56001 + j, 0, 2 + (20 * j));
	        }
	        
	        //scroll coords right side menu
	        setChildren(2, scrollInterface);
	        for (int j = 0; j < 2; j++) {
		        scrollInterface.child(j, 57025 + j, 0, 2 + (20 * j));
	        }
	   }
	   
	    public static void slayerInterface(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(41000);
	        addSprite(41001, 1, "Interfaces/SlayerInterface/IMAGE");
	        addHoverButton(41002, "Interfaces/SlayerInterface/IMAGE", 4, 16, 16, "Close window", 0, 41003, 1);
	        addHoveredButton(41003, "Interfaces/SlayerInterface/IMAGE", 5, 16, 16, 41004);
	        addHoverButton(41005, "", 0, 85, 20, "Buy", 0, 41006, 1);
	        addHoverButton(41007, "", 0, 85, 20, "Learn", 0, 41008, 1);
	        addHoverButton(41009, "", 0, 85, 20, "Assignment", 0, 41010, 1);
	        addText(41011, "Slayer Points: ", tda, 3, 0xFF981F);
	        addTextButton(41012, "Slayer Experience                           50", "Buy Slayer Experience", 0xFF981F, false, true, tda, 1, 400);
	        addTextButton(41013, "Slayer's Respite                             25", "Buy Slayer's Respite", 0xFF981F, false, true, tda, 1, 401);
	        addTextButton(41014, "Slayer Darts                                     35", "Buy Slayer Darts", 0xFF981F, false, true, tda, 1, 402);
	        addTextButton(41015, "Broad Arrows                                    25", "Buy Broad Arrows", 0xFF981F, false, true, tda, 1, 403);
	        setChildren(11, rsInterface);
	        rsInterface.child(0, 41001, 12, 10);
	        rsInterface.child(1, 41002, 473, 20);
	        rsInterface.child(2, 41003, 473, 20);
	        rsInterface.child(3, 41005, 21, 23);
	        rsInterface.child(4, 41007, 107, 23);
	        rsInterface.child(5, 41009, 193, 23);
	        rsInterface.child(6, 41011, 98, 74);
	        rsInterface.child(7, 41012, 124, 128);
	        rsInterface.child(8, 41013, 125, 160);
	        rsInterface.child(9, 41014, 125, 190);
	        rsInterface.child(10, 41015, 124, 220);
	        
	    }
	    
	    public static void slayerInterfaceSub1(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(41500);
	        addSprite(41501, 2, "Interfaces/SlayerInterface/IMAGE");
	        addHoverButton(41502, "Interfaces/SlayerInterface/IMAGE", 4, 16, 16, "Close window", 0, 41503, 1);
	        addHoveredButton(41503, "Interfaces/SlayerInterface/IMAGE", 5, 16, 16, 41504);
	        addHoverButton(41505, "", 0, 85, 20, "Buy", 0, 41506, 1);
	        addHoverButton(41507, "", 0, 85, 20, "Learn", 0, 41508, 1);
	        addHoverButton(41509, "", 0, 85, 20, "Assignment", 0, 41510, 1);
	        addText(41511, "Slayer Points: ", tda, 3, 0xFF981F);
	        setChildren(7, rsInterface);
	        rsInterface.child(0, 41501, 12, 10);
	        rsInterface.child(1, 41502, 473, 20);
	        rsInterface.child(2, 41503, 473, 20);
	        rsInterface.child(3, 41505, 21, 23);
	        rsInterface.child(4, 41507, 107, 23);
	        rsInterface.child(5, 41509, 193, 23);
	        rsInterface.child(6, 41511, 98, 74);
	    }
	    
	    public static void slayerInterfaceSub2(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(42000);
	        addSprite(42001, 3, "Interfaces/SlayerInterface/IMAGE");
	        addHoverButton(42002, "Interfaces/SlayerInterface/IMAGE", 4, 16, 16, "Close window", 0, 42003, 1);
	        addHoveredButton(42003, "Interfaces/SlayerInterface/IMAGE", 5, 16, 16, 42004);
	        addHoverButton(42005, "", 0, 85, 20, "Buy", 0, 42006, 1);
	        addHoverButton(42007, "", 0, 85, 20, "Learn", 0, 42008, 1);
	        addHoverButton(42009, "", 0, 85, 20, "Assignment", 0, 42010, 1);
	        addText(42011, "Slayer Points: ", tda, 3, 0xFF981F);
	        addTextButton(42012, "Cancel Task", "Temporarily cancel your current slayer task", 0xFF981F, false, true, tda, 1, 300);
	        addTextButton(42013, "Remove Task permanently", "Permanently remove this monster as a task", 0xFF981F, false, true, tda, 1, 305);
	        addText(42014, "line 1", tda, 1, 0xFF981F);
	        addText(42015, "line 2", tda, 1, 0xFF981F);
	        addText(42016, "line 3", tda, 1, 0xFF981F);
	        addText(42017, "line 4", tda, 1, 0xFF981F);
	        addButton(42018, 6, "Interfaces/SlayerInterface/IMAGE", "Delete removed slayer task");
	        addButton(42019, 6, "Interfaces/SlayerInterface/IMAGE", "Delete removed slayer task");
	        addButton(42020, 6, "Interfaces/SlayerInterface/IMAGE", "Delete removed slayer task");
	        addButton(42021, 6, "Interfaces/SlayerInterface/IMAGE", "Delete removed slayer task");
	        setChildren(17, rsInterface);
	        rsInterface.child(0, 42001, 12, 10);
	        rsInterface.child(1, 42002, 473, 20);
	        rsInterface.child(2, 42003, 473, 20);
	        rsInterface.child(3, 42005, 21, 23);
	        rsInterface.child(4, 42007, 107, 23);
	        rsInterface.child(5, 42009, 193, 23);
	        rsInterface.child(6, 42011, 98, 74);
	        rsInterface.child(7, 42012, 71, 127);
	        rsInterface.child(8, 42013, 71, 146);
	        rsInterface.child(9, 42014, 71, 216);
	        rsInterface.child(10, 42015, 71, 234);
	        rsInterface.child(11, 42016, 71, 252);
	        rsInterface.child(12, 42017, 71, 270);
	        rsInterface.child(13, 42018, 303, 215);
	        rsInterface.child(14, 42019, 303, 233);
	        rsInterface.child(15, 42020, 303, 251);
	        rsInterface.child(16, 42021, 303, 269);
	    }
		public static void homeTeleport(){
	        RSInterface RSInterface = addInterface(30000);
	        RSInterface.tooltip = "Cast @gre@Lunar Home Teleport";
	        RSInterface.id = 30000;
	        RSInterface.parentID = 30000;
	        RSInterface.type = 5;
	        RSInterface.atActionType = 5;
	        RSInterface.contentType = 0;
	        RSInterface.aByte254 = 0;
	        RSInterface.mOverInterToTrigger = 30001;
	        RSInterface.sprite1 =  imageLoader(1, "Lunar/SPRITE");
	        RSInterface.width = 20;
	        RSInterface.height = 20;
	        RSInterface Int = addInterface(30001);
	        Int.isMouseoverTriggered = true;
	    	Int.mOverInterToTrigger = -1;
	        setChildren(1, Int);
	        addLunarSprite(30002, 0, "SPRITE");
	        setBounds(30002, 0, 0,0, Int);
		}
		
		public static void addLunarSprite(int i, int j, String name) {
	        RSInterface RSInterface = addInterface(i);
	        RSInterface.id = i;
	        RSInterface.parentID = i;
	        RSInterface.type = 5;
	        RSInterface.atActionType = 5;
	        RSInterface.contentType = 0;
	        RSInterface.aByte254 = 0;
	        RSInterface.mOverInterToTrigger = 52;
	        RSInterface.sprite1 = LoadLunarSprite(j, name);
	        RSInterface.width = 500;
	        RSInterface.height = 500;
	        RSInterface.tooltip = "";
	    }
		public static void drawRune(int i,int id, String runeName) {
	        RSInterface RSInterface = addInterface(i);
	        RSInterface.type = 5;
	        RSInterface.atActionType = 0;
	        RSInterface.contentType = 0;
	        RSInterface.aByte254 = 0;
	        RSInterface.mOverInterToTrigger = 52;
	        RSInterface.sprite1 = LoadLunarSprite(id, "RUNE");
	        RSInterface.width = 500;
	        RSInterface.height = 500;
	    }
		
		public static void addButtons(int id, int sid, String spriteName, String tooltip, int mOver, int atAction) {
			RSInterface rsinterface = interfaceCache[id] = new RSInterface();
			rsinterface.id = id;
			rsinterface.parentID = id;
			rsinterface.type = 5;
			rsinterface.atActionType = atAction;
			rsinterface.contentType = 0;
			rsinterface.aByte254 = (byte)0;
			rsinterface.mOverInterToTrigger = mOver;
			rsinterface.sprite1 = imageLoader(sid, spriteName);
			rsinterface.sprite2 = imageLoader(sid, spriteName);
			rsinterface.width = rsinterface.sprite1.myWidth;
			rsinterface.height = rsinterface.sprite2.myHeight;
			rsinterface.tooltip = tooltip;
			rsinterface.inventoryhover = true;
		}
		
		public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2,int rune1, int lvl,String name, String descr,TextDrawingArea[] TDA,int sid,int suo,int type){
			RSInterface rsInterface = addInterface(ID);
			rsInterface.id = ID;
			rsInterface.parentID = 1151;
			rsInterface.type = 5;
			rsInterface.atActionType = type;
			rsInterface.contentType = 0;
			rsInterface.mOverInterToTrigger = ID+1;
			rsInterface.spellUsableOn = suo;
			rsInterface.selectedActionName = "Cast On";
			rsInterface.width = 20;
			rsInterface.height = 20;
			rsInterface.tooltip = "Cast @gre@"+name;
			rsInterface.spellName = name;
			rsInterface.anIntArray245 = new int[3];
			rsInterface.anIntArray212 = new int[3];
			rsInterface.anIntArray245[0] = 3;
			rsInterface.anIntArray212[0] = ra1;
			rsInterface.anIntArray245[1] = 3;
			rsInterface.anIntArray212[1] = ra2;
			rsInterface.anIntArray245[2] = 3;
			rsInterface.anIntArray212[2] = lvl;
			rsInterface.valueIndexArray = new int[3][];
			rsInterface.valueIndexArray[0] = new int[4];
			rsInterface.valueIndexArray[0][0] = 4;
			rsInterface.valueIndexArray[0][1] = 3214;
			rsInterface.valueIndexArray[0][2] = r1;
			rsInterface.valueIndexArray[0][3] = 0;
			rsInterface.valueIndexArray[1] = new int[4];
			rsInterface.valueIndexArray[1][0] = 4;
			rsInterface.valueIndexArray[1][1] = 3214;
			rsInterface.valueIndexArray[1][2] = r2;
			rsInterface.valueIndexArray[1][3] = 0;
			rsInterface.valueIndexArray[2] = new int[3];
			rsInterface.valueIndexArray[2][0] = 1;
			rsInterface.valueIndexArray[2][1] = 6;
			rsInterface.valueIndexArray[2][2] = 0;
			rsInterface.sprite2 =  imageLoader(sid, "Lunar/LUNARON");
			rsInterface.sprite1 =  imageLoader(sid, "Lunar/LUNAROFF");
			RSInterface INT = addInterface(ID+1);
			INT.isMouseoverTriggered = true;
			INT.mOverInterToTrigger = -1;
			//setChildren(7, INT);
			//addLunarSprite(ID+2, 0, "BOX");
			//setBounds(ID+2, 0, 0, 0, INT);
			//addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);
			//setBounds(ID+3, 90, 4, 1, INT);
			//addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	
			//setBounds(ID+4, 90, 19, 2, INT);
			//setBounds(30016, 37, 35, 3, INT);//Rune
			//setBounds(rune1, 112, 35, 4, INT);//Rune
			//addRuneText(ID+5, ra1+1, r1, TDA);
			//setBounds(ID+5, 50, 66, 5, INT);
			//addRuneText(ID+6, ra2+1, r2, TDA);
			//setBounds(ID+6, 123, 66, 6, INT);

		}
		
		public static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font){
			RSInterface rsInterface = addInterface(ID);
			rsInterface.id = ID;
			rsInterface.parentID = 1151;
			rsInterface.type = 4;
			rsInterface.atActionType = 0;
			rsInterface.contentType = 0;
			rsInterface.width = 0;
			rsInterface.height = 14;
			rsInterface.aByte254 = 0;
			rsInterface.mOverInterToTrigger = -1;
			rsInterface.anIntArray245 = new int[1];
			rsInterface.anIntArray212 = new int[1];
			rsInterface.anIntArray245[0] = 3;
			rsInterface.anIntArray212[0] = runeAmount;
			rsInterface.valueIndexArray = new int[1][4];
			rsInterface.valueIndexArray[0][0] = 4;
			rsInterface.valueIndexArray[0][1] = 3214;
			rsInterface.valueIndexArray[0][2] = RuneID;
			rsInterface.valueIndexArray[0][3] = 0;
			rsInterface.centerText = true;
			rsInterface.textDrawingAreas = font[0];
			//rsInterface.textShadowed = true;
			rsInterface.message = "%1/"+runeAmount+"";
			rsInterface.message = "";
			//rsInterface.disabledColour = 12582912;
			//rsInterface.enabledColour = 49152;	
		}

		public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3,int rune1, int rune2, int lvl,String name, String descr,TextDrawingArea[] TDA, int sid,int suo,int type){
			RSInterface rsInterface = addInterface(ID);
			rsInterface.id = ID;
			rsInterface.parentID = 1151;
			rsInterface.type = 5;
			rsInterface.atActionType = type;
			rsInterface.contentType = 0;
			rsInterface.mOverInterToTrigger = ID+1;
			rsInterface.spellUsableOn = suo;
			rsInterface.selectedActionName = "Cast on";
			rsInterface.width = 20;
			rsInterface.height = 20;
			rsInterface.tooltip = "Cast @gre@"+name;
			rsInterface.spellName = name;
			rsInterface.anIntArray245 = new int[4];
			rsInterface.anIntArray212 = new int[4];
			rsInterface.anIntArray245[0] = 3;
			rsInterface.anIntArray212[0] = ra1;
			rsInterface.anIntArray245[1] = 3;
			rsInterface.anIntArray212[1] = ra2;
			rsInterface.anIntArray245[2] = 3;
			rsInterface.anIntArray212[2] = ra3;
			rsInterface.anIntArray245[3] = 3;
			rsInterface.anIntArray212[3] = lvl;
			rsInterface.valueIndexArray = new int[4][];
			rsInterface.valueIndexArray[0] = new int[4];
			rsInterface.valueIndexArray[0][0] = 4;
			rsInterface.valueIndexArray[0][1] = 3214;
			rsInterface.valueIndexArray[0][2] = r1;
			rsInterface.valueIndexArray[0][3] = 0;
			rsInterface.valueIndexArray[1] = new int[4];
			rsInterface.valueIndexArray[1][0] = 4;
			rsInterface.valueIndexArray[1][1] = 3214;
			rsInterface.valueIndexArray[1][2] = r2;
			rsInterface.valueIndexArray[1][3] = 0;
			rsInterface.valueIndexArray[2] = new int[4];
			rsInterface.valueIndexArray[2][0] = 4;
			rsInterface.valueIndexArray[2][1] = 3214;
			rsInterface.valueIndexArray[2][2] = r3;
			rsInterface.valueIndexArray[2][3] = 0;
			rsInterface.valueIndexArray[3] = new int[3];
			rsInterface.valueIndexArray[3][0] = 1;
			rsInterface.valueIndexArray[3][1] = 6;
			rsInterface.valueIndexArray[3][2] = 0;
			rsInterface.sprite2 =  imageLoader(sid, "Lunar/LUNARON");
			rsInterface.sprite1 =  imageLoader(sid, "Lunar/LUNAROFF");
			RSInterface INT = addInterface(ID+1);
			INT.isMouseoverTriggered = true;
			INT.mOverInterToTrigger = -1;
			//setChildren(9, INT);
			//addLunarSprite(ID+2, 0, "BOX");
			//setBounds(ID+2, 0, 0, 0, INT);
			//addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);setBounds(ID+3, 90, 4, 1, INT);
			//addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	setBounds(ID+4, 90, 19, 2, INT);
			//setBounds(30016, 14, 35, 3, INT);
			//setBounds(rune1, 74, 35, 4, INT);
			//setBounds(rune2, 130, 35, 5, INT);
			//addRuneText(ID+5, ra1+1, r1, TDA);
			//setBounds(ID+5, 26, 66, 6, INT);
			//addRuneText(ID+6, ra2+1, r2, TDA);
			//setBounds(ID+6, 87, 66, 7, INT);
			//addRuneText(ID+7, ra3+1, r3, TDA);
			//setBounds(ID+7, 142, 66, 8, INT);
		}

		public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3,int rune1, int rune2, int lvl,String name, String descr,TextDrawingArea[] TDA, int sid,int suo,int type){
			RSInterface rsInterface = addInterface(ID);
			rsInterface.id = ID;
			rsInterface.parentID = 1151;
			rsInterface.type = 5;
			rsInterface.atActionType = type;
			rsInterface.contentType = 0;
			rsInterface.mOverInterToTrigger = ID+1;
			rsInterface.spellUsableOn = suo;
			rsInterface.selectedActionName = "Cast on";
			rsInterface.width = 20;
			rsInterface.height = 20;
			rsInterface.tooltip = "Cast @gre@"+name;
			rsInterface.spellName = name;
			rsInterface.anIntArray245 = new int[4];
			rsInterface.anIntArray212 = new int[4];
			rsInterface.anIntArray245[0] = 3;
			rsInterface.anIntArray212[0] = ra1;
			rsInterface.anIntArray245[1] = 3;
			rsInterface.anIntArray212[1] = ra2;
			rsInterface.anIntArray245[2] = 3;
			rsInterface.anIntArray212[2] = ra3;
			rsInterface.anIntArray245[3] = 3;
			rsInterface.anIntArray212[3] = lvl;
			rsInterface.valueIndexArray = new int[4][];
			rsInterface.valueIndexArray[0] = new int[4];
			rsInterface.valueIndexArray[0][0] = 4;
			rsInterface.valueIndexArray[0][1] = 3214;
			rsInterface.valueIndexArray[0][2] = r1;
			rsInterface.valueIndexArray[0][3] = 0;
			rsInterface.valueIndexArray[1] = new int[4];
			rsInterface.valueIndexArray[1][0] = 4;
			rsInterface.valueIndexArray[1][1] = 3214;
			rsInterface.valueIndexArray[1][2] = r2;
			rsInterface.valueIndexArray[1][3] = 0;
			rsInterface.valueIndexArray[2] = new int[4];
			rsInterface.valueIndexArray[2][0] = 4;
			rsInterface.valueIndexArray[2][1] = 3214;
			rsInterface.valueIndexArray[2][2] = r3;
			rsInterface.valueIndexArray[2][3] = 0;
			rsInterface.valueIndexArray[3] = new int[3];
			rsInterface.valueIndexArray[3][0] = 1;
			rsInterface.valueIndexArray[3][1] = 6;
			rsInterface.valueIndexArray[3][2] = 0;
			rsInterface.sprite2 =  imageLoader(sid, "Lunar/LUNARON");
			rsInterface.sprite1 =  imageLoader(sid, "Lunar/LUNAROFF");
			RSInterface INT = addInterface(ID+1);
			INT.isMouseoverTriggered = true;
			INT.mOverInterToTrigger = -1;
		//	setChildren(9, INT);
			//addLunarSprite(ID+2, 1, "BOX");
			//setBounds(ID+2, 0, 0, 0, INT);
			//addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);setBounds(ID+3, 90, 4, 1, INT);
			//addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	setBounds(ID+4, 90, 21, 2, INT);
			//setBounds(30016, 14, 48, 3, INT);
			//setBounds(rune1, 74, 48, 4, INT);
			//setBounds(rune2, 130, 48, 5, INT);
			//addRuneText(ID+5, ra1+1, r1, TDA);
			//setBounds(ID+5, 26, 79, 6, INT);
			//addRuneText(ID+6, ra2+1, r2, TDA);
			//setBounds(ID+6, 87, 79, 7, INT);
			//addRuneText(ID+7, ra3+1, r3, TDA);
			//setBounds(ID+7, 142, 79, 8, INT);
		}

		public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3,int rune1, int rune2, int lvl,String name, String descr,TextDrawingArea[] TDA, int sid,int suo,int type){
			RSInterface rsInterface = addInterface(ID);
			rsInterface.id = ID;
			rsInterface.parentID = 1151;
			rsInterface.type = 5;
			rsInterface.atActionType = type;
			rsInterface.contentType = 0;
			rsInterface.mOverInterToTrigger = ID+1;
			rsInterface.spellUsableOn = suo;
			rsInterface.selectedActionName = "Cast on";
			rsInterface.width = 20;
			rsInterface.height = 20;
			rsInterface.tooltip = "Cast @gre@"+name;
			rsInterface.spellName = name;
			rsInterface.anIntArray245 = new int[4];
			rsInterface.anIntArray212 = new int[4];
			rsInterface.anIntArray245[0] = 3;
			rsInterface.anIntArray212[0] = ra1;
			rsInterface.anIntArray245[1] = 3;
			rsInterface.anIntArray212[1] = ra2;
			rsInterface.anIntArray245[2] = 3;
			rsInterface.anIntArray212[2] = ra3;
			rsInterface.anIntArray245[3] = 3;
			rsInterface.anIntArray212[3] = lvl;
			rsInterface.valueIndexArray = new int[4][];
			rsInterface.valueIndexArray[0] = new int[4];
			rsInterface.valueIndexArray[0][0] = 4;
			rsInterface.valueIndexArray[0][1] = 3214;
			rsInterface.valueIndexArray[0][2] = r1;
			rsInterface.valueIndexArray[0][3] = 0;
			rsInterface.valueIndexArray[1] = new int[4];
			rsInterface.valueIndexArray[1][0] = 4;
			rsInterface.valueIndexArray[1][1] = 3214;
			rsInterface.valueIndexArray[1][2] = r2;
			rsInterface.valueIndexArray[1][3] = 0;
			rsInterface.valueIndexArray[2] = new int[4];
			rsInterface.valueIndexArray[2][0] = 4;
			rsInterface.valueIndexArray[2][1] = 3214;
			rsInterface.valueIndexArray[2][2] = r3;
			rsInterface.valueIndexArray[2][3] = 0;
			rsInterface.valueIndexArray[3] = new int[3];
			rsInterface.valueIndexArray[3][0] = 1;
			rsInterface.valueIndexArray[3][1] = 6;
			rsInterface.valueIndexArray[3][2] = 0;
			rsInterface.sprite2 =  imageLoader(sid, "Lunar/LUNARON");
			rsInterface.sprite1 =  imageLoader(sid, "Lunar/LUNAROFF");
			RSInterface INT = addInterface(ID+1);
			INT.isMouseoverTriggered = true;
			INT.mOverInterToTrigger = -1;
			//setChildren(9, INT);
			//addLunarSprite(ID+2, 2, "BOX");
			//setBounds(ID+2, 0, 0, 0, INT);
			//addText(ID+3, "Level "+(lvl+1)+": "+name, 0xFF981F, true, true, 52, TDA, 1);
			//setBounds(ID+3, 90, 4, 1, INT);
			//addText(ID+4, descr, 0xAF6A1A, true, true, 52, TDA, 0);	
			//setBounds(ID+4, 90, 34, 2, INT);
			//setBounds(30016, 14, 61, 3, INT);
			//setBounds(rune1, 74, 61, 4, INT);
			//setBounds(rune2, 130, 61, 5, INT);
			//addRuneText(ID+5, ra1+1, r1, TDA);
			//setBounds(ID+5, 26, 92, 6, INT);
			//addRuneText(ID+6, ra2+1, r2, TDA);
			//setBounds(ID+6, 87, 92, 7, INT);
			//addRuneText(ID+7, ra3+1, r3, TDA);
			//setBounds(ID+7, 142, 92, 8, INT);
		}
		
		private static Sprite LoadLunarSprite(int i, String s) {
			Sprite sprite = imageLoader(i,"/Lunar/" + s);
			return sprite;
		}

	public static void addActionButton(int id, int sprite, int sprite2,
			int width, int height, String s) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		if (sprite2 == sprite)
			rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		else
			rsi.sprite2 = CustomSpriteLoader(sprite2, "");
		rsi.tooltip = s;
		rsi.contentType = 0;
		rsi.atActionType = 1;
		rsi.width = width;
		rsi.mOverInterToTrigger = 52;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
	}

	public static void addButton(int id, int sid, String spriteName,
			String tooltip, int mOver, int atAction, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = atAction;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = mOver;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = tooltip;
		tab.inventoryhover = true;
	}

	public static void addText(int id, String text, TextDrawingArea wid[],
			int idx, int color) {
		RSInterface rsinterface = addTab(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = false;
		rsinterface.textShadow = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0;
		rsinterface.anInt239 = 0;
	}

	public static void sprite1(int id, int sprite) {
		RSInterface class9 = interfaceCache[id];
		class9.sprite1 = CustomSpriteLoader(sprite, "");
	}

	public static RSInterface addInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.id = id;
		rsi.parentID = id;
		rsi.width = 512;
		rsi.height = 334;
		return rsi;
	}
	
	/**
	 * Adds newlines to a text for a certain TextDrawingArea so each line is never longer than width.
	 * Param tda The textDrawing Area for the text, basically the font
	 * Param text The text to convert to wrapped text
	 * Param width The width above which wrapping is applied
	 * Return The wrapped text
	 */
	public static String getWrappedText(TextDrawingArea tda, String text, int width) {
		if (tda.getTextWidth(text) <= width) { // text.contains("\\n") || 
			return text;
		}
		int spaceWidth = tda.getTextWidth(" ");
		StringBuilder result = new StringBuilder(text.length());
		StringBuilder line = new StringBuilder();
		int lineLength = 0;
		int curIndex = 0;
		String lastColor = "";
		while (true) {
			int spaceIndex = text.indexOf(' ', curIndex);
			int newLength = lineLength;
			boolean last = false;
			String curWord;
			if (spaceIndex < 0) {
				last = true;
				curWord = text.substring(curIndex);
			} else {
				curWord = text.substring(curIndex, spaceIndex);
				newLength += spaceWidth;
			}
			curIndex = spaceIndex + 1;
			int w = tda.getTextWidth(curWord);
			if (curWord.startsWith("@") && curWord.startsWith("@", 4)) {
				w = tda.getTextWidth(curWord.substring(5));
				lastColor = curWord.substring(0, 5);
			}
			if (curWord.endsWith("@") && curWord.startsWith("@", curWord.length() - 5)) {
				w = tda.getTextWidth(curWord.substring(0, curWord.length() - 5));
			}
			newLength += w;
			if (curWord.equalsIgnoreCase("\\n")) {
				result.append(line);
				// result.append("\\n");
				line = new StringBuilder(lastColor + curWord);
				// line.append(' ');
				lineLength = 0;
				continue;
			}
			if (newLength > width) {
				result.append(line);
				result.append("\\n");
				line = new StringBuilder(lastColor + curWord);
				line.append(' ');
				lineLength = w;
			} else {
				line.append(curWord);
				line.append(' ');
				lineLength = newLength;
			}
			if (last) {
				break;
			}
		}
		result.append(line);
		return result.toString();
	}
	
	/**
	 * Adds text with the specified properties. Automatically wraps text so it doesn't exceed width.
	 * Only use for dynamic interfaces as there is some computation to check if wrapping is required.
	 * If static text, use another addText method and pass the text into RSInterface.getWrappedText() firstly.
	 *    Param id The child id for the text
	 *    Param text The text message
	 *    Param tda The tdas available
	 *    Param idx The index of the tda to use
	 *    Param color The text color
	 *    Param center Whether the text is centered
	 *    Param shadow Whether the text has shadow
	 *    Param width The maximum width of each line before wrapping applies
	 *    Return
	 */
	public static RSInterface addWrappingText(int id, String text, TextDrawingArea tda[],
									  int idx, int color, boolean center, boolean shadow, int width) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 17;
		tab.atActionType = 0;
		tab.width = width;
		tab.height = 11;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = getWrappedText(tab.textDrawingAreas, text, tab.width);
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
		return tab;
	}

	public static void addHoverText(int id, String text, String tooltip,
			TextDrawingArea tda[], int idx, int color, boolean center,
			boolean textShadowed, int width) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = 12;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0xffffff;
		rsinterface.anInt239 = 0;
		rsinterface.tooltip = tooltip;
	}

	public static RSInterface addTab(int i) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.id = i;
		rsinterface.parentID = i;
		rsinterface.type = 0;
		rsinterface.atActionType = 0;
		rsinterface.contentType = 0;
		rsinterface.width = 512;
		rsinterface.height = 334;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = 0;
		return rsinterface;
	}

	public static void addConfigButton(int ID, int pID, int bID, int bID2,
			String bName, int width, int height, String tT, int configID,
			int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.aByte254 = 0;
		Tab.mOverInterToTrigger = -1;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(bID, bName);
		Tab.sprite2 = imageLoader(bID2, bName);
		Tab.tooltip = tT;
	}

	public static void drawBlackBox(int xPos, int yPos) {
		// /Light Coloured Borders\\\
		DrawingArea.drawPixels(71, yPos - 1, xPos - 2, 0x726451, 1); // Left
																		// line
		DrawingArea.drawPixels(69, yPos, xPos + 174, 0x726451, 1); // Right line
		DrawingArea.drawPixels(1, yPos - 2, xPos - 2, 0x726451, 178); // Top
																		// Line
		DrawingArea.drawPixels(1, yPos + 68, xPos, 0x726451, 174); // Bottom
																	// Line

		// /Dark Coloured Borders\\\
		DrawingArea.drawPixels(71, yPos - 1, xPos - 1, 0x2E2B23, 1); // Left
																		// line
		DrawingArea.drawPixels(71, yPos - 1, xPos + 175, 0x2E2B23, 1); // Right
																		// line
		DrawingArea.drawPixels(1, yPos - 1, xPos, 0x2E2B23, 175); // Top line
		DrawingArea.drawPixels(1, yPos + 69, xPos, 0x2E2B23, 175); // Top line

		// /Black Box\\\
		DrawingArea.method335(0x000000, yPos, 174, 68, 220, xPos); // Yes
																	// method335
																	// is
																	// galkons
																	// opacity
																	// method
	}

	public Sprite disabledHover;
	public Sprite enabledHover;

	public static void addPrayer(int i, int configId, int configFrame,
			int requiredValues, int spriteID, String prayerName) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = 5608;
		tab.type = 5;
		tab.atActionType = 4;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.sprite1 = imageLoader(0, "PRAYERGLOW");
		tab.sprite2 = imageLoader(1, "PRAYERGLOW");
		tab.width = 34;
		tab.height = 34;
		tab.anIntArray245 = new int[1];
		tab.anIntArray212 = new int[1];
		tab.anIntArray245[0] = 1;
		tab.anIntArray212[0] = configId;
		tab.valueIndexArray = new int[1][3];
		tab.valueIndexArray[0][0] = 5;
		tab.valueIndexArray[0][1] = configFrame;
		tab.valueIndexArray[0][2] = 0;
		// tab.tooltip = "Activate@or2@ " + prayerName;
		tab.tooltip = "Select";
		RSInterface tab2 = addTabInterface(i + 1);
		tab2.id = i + 1;
		tab2.parentID = 5608;
		tab2.type = 5;
		tab2.atActionType = 0;
		tab2.contentType = 0;
		tab2.aByte254 = 0;
		tab2.mOverInterToTrigger = -1;
		tab2.sprite1 = imageLoader(spriteID, "Prayer/PRAYON");
		tab2.sprite2 = imageLoader(spriteID, "Prayer/PRAYOFF");
		tab2.width = 34;
		tab2.height = 34;
		tab2.anIntArray245 = new int[1];
		tab2.anIntArray212 = new int[1];
		tab2.anIntArray245[0] = 2;
		tab2.anIntArray212[0] = requiredValues + 1;
		tab2.valueIndexArray = new int[1][3];
		tab2.valueIndexArray[0][0] = 2;
		tab2.valueIndexArray[0][1] = 5;
		tab2.valueIndexArray[0][2] = 0;
	}

	public static void addToggleButton(int id, int sprite, int setconfig,
			int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public static void removeSomething(int id) {
@SuppressWarnings("unused")
RSInterface rsi = interfaceCache[id] = new RSInterface();
	}

	public static void setBounds(int ID, int X, int Y, int frame,
			RSInterface RSinterface) {
		RSinterface.children[frame] = ID;
		RSinterface.childX[frame] = X;
		RSinterface.childY[frame] = Y;
	}

	public static void textSize(int id, TextDrawingArea tda[], int idx) {
		RSInterface rsi = interfaceCache[id];
		rsi.textDrawingAreas = tda[idx];
	}

	/**
	 * Adds your current character to an interface.
	 **/

	protected static void addOldPrayer(int id, String prayerName) {
		RSInterface rsi = interfaceCache[id];
		rsi.tooltip = "Activate@or2@ " + prayerName;
	}

	public static void addPrayerHover(int i, int hoverID, int prayerSpriteID,
			String hoverText) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 5608;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.mOverInterToTrigger = hoverID;
		Interface.sprite2 = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.sprite1 = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.width = 34;
		Interface.height = 34;

		Interface = addTabInterface(hoverID);
		Interface.id = hoverID;
		Interface.parentID = 5608;
		Interface.type = 0;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.mOverInterToTrigger = -1;
		Interface.width = 512;
		Interface.height = 334;
		Interface.isMouseoverTriggered = true;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, Interface);
		setBounds(hoverID + 1, 0, 0, 0, Interface);
	}

	public static void addChar(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.aByte254 = 0;
		t.mOverInterToTrigger = 0;
		t.modelZoom = 560;
		t.modelRotation1 = 150;
		t.modelRotation2 = 0;
		t.anInt257 = -1;
		t.anInt258 = -1;
	}

	public static void addCacheSprite(int id, int sprite1, int sprite2,
			String sprites) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = method207(sprite1, aClass44, sprites);
		rsi.sprite2 = method207(sprite2, aClass44, sprites);
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}

	public void specialBar(int id, TextDrawingArea[] tda) // 7599
	{
		addActionButton(id - 12, 7587, -1, 150, 26, "Use @gre@Special Attack");
		for (int i = id - 11; i < id; i++)
			removeSomething(i);

		RSInterface rsi = interfaceCache[id - 12];
		rsi.width = 150;
		rsi.height = 26;
		rsi.mOverInterToTrigger = 40005;

		rsi = interfaceCache[id];
		rsi.width = 150;
		rsi.height = 26;

		rsi.child(0, id - 12, 0, 0);

		rsi.child(12, id + 1, 3, 7);

		rsi.child(23, id + 12, 16, 8);

		for (int i = 13; i < 23; i++) {
			rsi.childY[i] -= 1;
		}

		rsi = interfaceCache[id + 1];
		rsi.type = 5;
		rsi.sprite1 = CustomSpriteLoader(7600, "");

		for (int i = id + 2; i < id + 12; i++) {
			rsi = interfaceCache[i];
			rsi.type = 5;
		}

		sprite1(id + 2, 7601);
		sprite1(id + 3, 7602);
		sprite1(id + 4, 7603);
		sprite1(id + 5, 7604);
		sprite1(id + 6, 7605);
		sprite1(id + 7, 7606);
		sprite1(id + 8, 7607);
		sprite1(id + 9, 7608);
		sprite1(id + 10, 7609);
		sprite1(id + 11, 7610);

		rsi = addInterface(40005);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = 40005;
		rsi.id = 40005;
		addBox(40006, 0, false, 0x000000,
				"Select to perform a special\nattack.");
		setChildren(1, rsi);
		setBounds(40006, 0, 0, 0, rsi);
	}

	public static void addAttackHover(int id, int hoverID, String hoverText,
			TextDrawingArea[] TDA) {
		RSInterface rsi = interfaceCache[id];
		rsi.mOverInterToTrigger = hoverID;

		rsi = addInterface(hoverID);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = hoverID;
		rsi.id = hoverID;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID + 1, 0, 0, 0, rsi);
	}

	public static void addAttackText(int id, String text,
			TextDrawingArea tda[], int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		if (centered)
			rsi.centerText = true;
		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.textColor = color;
		rsi.id = id;
		rsi.type = 4;
	}

	public static void addAttackStyleButton2(int id, int sprite, int setconfig,
			int width, int height, String s, int hoverID, int hW, int hH,
			String hoverText, TextDrawingArea[] TDA) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.mOverInterToTrigger = hoverID;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
		rsi = addInterface(hoverID);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = hoverID;
		rsi.id = hoverID;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID + 1, 0, 0, 0, rsi);
	}

	public static void addBox(int id, int byte1, boolean filled, int color,
			String text) {
		RSInterface Interface = addInterface(id);
		Interface.id = id;
		Interface.parentID = id;
		Interface.type = 9;
		Interface.aByte254 = (byte) byte1;
		Interface.aBoolean227 = filled;
		Interface.mOverInterToTrigger = -1;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.textColor = color;
		Interface.message = text;
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	protected static Sprite CustomSpriteLoader(int id, String s) {
		long l = (TextClass.method585(s) << 8) + (long) id;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite("/Attack/" + id + s);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public static void addTooltipBox(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 9;
		rsi.message = text;
	}

	public static void addTooltip(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.type = 0;
		rsi.isMouseoverTriggered = true;
		rsi.mOverInterToTrigger = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}

	public static void addText(int i, String s, int k, boolean l, boolean m,
			int a, TextDrawingArea[] TDA, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.aString228 = "";
		RSInterface.textColor = k;
	}
	
	public static void addText(int i, String s, int k, boolean l, boolean m,
			int a, TextDrawingArea[] TDA, int j, int w) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = w;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.aString228 = "";
		RSInterface.textColor = k;
	}

	public static void addSprite(int i, int j, int k) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.id = i;
		rsinterface.parentID = i;
		rsinterface.type = 5;
		rsinterface.atActionType = 1;
		rsinterface.contentType = 0;
		rsinterface.width = 20;
		rsinterface.height = 20;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = 52;
		rsinterface.sprite1 = imageLoader(j, "Equipment/SPRITE");
		rsinterface.sprite2 = imageLoader(k, "Equipment/SPRITE");
	}

	public static void addHover(int i, int aT, int cT, int hoverid, int sId,
			String NAME, int W, int H, String tip) {
		RSInterface rsinterfaceHover = addInterface(i);
		rsinterfaceHover.id = i;
		rsinterfaceHover.parentID = i;
		rsinterfaceHover.type = 5;
		rsinterfaceHover.atActionType = aT;
		rsinterfaceHover.contentType = cT;
		rsinterfaceHover.mOverInterToTrigger = hoverid;
		rsinterfaceHover.sprite1 = imageLoader(sId, NAME);
		rsinterfaceHover.sprite2 = imageLoader(sId, NAME);
		rsinterfaceHover.width = W;
		rsinterfaceHover.height = H;
		rsinterfaceHover.tooltip = tip;
	}

	public static void addHovered(int i, int j, String imageName, int w, int h,
			int IMAGEID) {
		RSInterface rsinterfaceHover = addInterface(i);
		rsinterfaceHover.parentID = i;
		rsinterfaceHover.id = i;
		rsinterfaceHover.type = 0;
		rsinterfaceHover.atActionType = 0;
		rsinterfaceHover.width = w;
		rsinterfaceHover.height = h;
		rsinterfaceHover.isMouseoverTriggered = true;
		rsinterfaceHover.mOverInterToTrigger = -1;
		addSprite(IMAGEID, j, imageName);
		setChildren(1, rsinterfaceHover);
		setBounds(IMAGEID, 0, 0, 0, rsinterfaceHover);
	}

	public static void addText(int id, String text, TextDrawingArea tda[],
			int idx, int color, boolean center, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}

	public String hoverText;

	public static void addHoverBox(int id, String text) {
		RSInterface rsi = interfaceCache[id];// addTabInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.isMouseoverTriggered = true;
		rsi.type = 8;
		rsi.hoverText = text;
	}

	public static void addButton(int id, int sid, String spriteName,
			String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = tab.sprite1.myWidth;
		tab.height = tab.sprite2.myHeight;
		tab.tooltip = tooltip;
	}

	public static void addSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
	}

	public static void addHoverButton(int i, String imageName, int j,
			int width, int height, String text, int contentType, int hoverOver,
			int aT) {// hoverable button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = hoverOver;
		tab.sprite1 = imageLoader(j, imageName);
		tab.sprite2 = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w,
			int h, int IMAGEID) {// hoverable button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j, imageName);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverImage(int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(j, name);
		tab.sprite2 = imageLoader(k, name);
	}

	public static void addTransparentSprite(int id, int spriteId,
			String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static RSInterface addScreenInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 0;
		return tab;
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = -1;// Int 230
		return tab;
	}

	protected static Sprite imageLoader(int i, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(s + " " + i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	private Model method206(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null)
			return model;
		if (i == 1)
			model = Model.method462(j);
		if (i == 2)
			model = EntityDef.forID(j).method160();
		if (i == 3)
			model = client.myPlayer.method453();
		if (i == 4)
			model = ItemDef.forID(j).method202(50);
		if (i == 5)
			model = null;
		if (model != null)
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		return model;
	}

	private static Sprite method207(int i, StreamLoader streamLoader, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(streamLoader, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void method208(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag)
			return;
		aMRUNodes_264.unlinkAll();
		if (model != null && j != 4)
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag)
			model = method206(anInt255, anInt256);
		else
			model = method206(anInt233, mediaID);
		if (model == null)
			return null;
		if (k == -1 && j == -1 && model.anIntArray1640 == null)
			return model;
		Model model_1 = new Model(true, Class36.method532(k)
				& Class36.method532(j), false, model);
		if (k != -1 || j != -1)
			model_1.method469();
		if (k != -1)
			model_1.method470(k);
		if (j != -1)
			model_1.method470(j);
		model_1.method479(64, 768, -50, -10, -50, true);
		return model_1;
	}

	public RSInterface() {
	}

	public static StreamLoader aClass44;
	public boolean drawsTransparent;
	public Sprite sprite1;
	public int anInt208;
	public Sprite sprites[];
	public static RSInterface interfaceCache[];
	public int anIntArray212[];
	public int contentType;// anInt214
	public int spritesX[];
	public int anInt216;
	public int atActionType;
	public String spellName;
	public int anInt219;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean centerText;
	public int scrollPosition;
	public String actions[];
	public int valueIndexArray[][];
	public boolean aBoolean227;
	public String aString228;
	public int mOverInterToTrigger;
	public int invSpritePadX;
	public int textColor;
	public int anInt233;
	public int mediaID;
	public boolean aBoolean235;
	public int parentID;
	public int spellUsableOn;
	private static MRUNodes aMRUNodes_238;
	public int anInt239;
	public int children[];
	public int childX[];
	public boolean usableItemInterface;
	public TextDrawingArea textDrawingAreas;
	public int invSpritePadY;
	public int anIntArray245[];
	public int anInt246;
	public int spritesY[];
	public String message;
	public boolean isInventoryInterface;
	public int id;
	public int invStackSizes[];
	public int inv[];
	public byte aByte254;
	private int anInt255;
	private int anInt256;
	public int anInt257;
	public int anInt258;
	public boolean aBoolean259;
	public Sprite sprite2;
	public int scrollMax;
	public int type;
	public int anInt263;
	private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
	public int anInt265;
	public boolean isMouseoverTriggered;
	public int height;
	public boolean textShadow;
	public int modelZoom;
	public int modelRotation1;
	public int modelRotation2;
	public int childY[];
	public boolean inventoryhover;

}

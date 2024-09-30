package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Player extends Entity {
    static short[][] aShortArrayArray1619 = new short[][]{{(short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898}, {(short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10347, (short) 10582, (short) 10429, (short) 10407, (short) 10359, (short) 8414, (short) 9540, (short) 10456, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0}, {(short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898}, {(short) 4300, (short) 3294, (short) 3303, (short) 3264, (short) 4506, (short) 4382, (short) 4387, (short) 5293, (short) 7622, (short) 7384, (short) 8412, (short) 7496, (short) 86, (short) 123, (short) 111, (short) 99, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 13766, (short) 13745, (short) 13726, (short) 13890, (short) 13743, (short) 13852, (short) 17602, (short) 18605, (short) 21660, (short) 24000, (short) 24997, (short) 24088, (short) 27972, (short) 25903, (short) 26904, (short) 27193, (short) 27175, (short) 27156, (short) 30020, (short) 28975, (short) 29976, (short) 12482, (short) 13485, (short) 10392, (short) 10692, (short) 10669, (short) 10776, (short) 6717, (short) 6695, (short) 7830, (short) 6971, (short) 6951, (short) 5910, (short) 3389, (short) 3369, (short) 3356, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0}, {(short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898, (short) 10, (short) 30, (short) 50, (short) 70, (short) 90, (short) 110, (short) 310, (short) 684, (short) 704, (short) 556, (short) 940, (short) 960, (short) 6454, (short) 6952, (short) 6972, (short) 2358, (short) 2732, (short) 2752, (short) 10550, (short) 10924, (short) 10944, (short) 10310, (short) 10556, (short) 10576, (short) 14646, (short) 15020, (short) 15040, (short) 19766, (short) 20140, (short) 20160, (short) -29386, (short) -29012, (short) -28992, (short) 31030, (short) 31276, (short) 31296, (short) -24266, (short) -23892, (short) -23872, (short) -19146, (short) -18772, (short) -18752, (short) -14026, (short) -13652, (short) -13632, (short) -6858, (short) -6484, (short) -6464, (short) 522, (short) 542, (short) 6794, (short) 6814, (short) 11018, (short) 11038, (short) 14986, (short) 15006, (short) 21130, (short) 21150, (short) -28918, (short) -28898, (short) -22006, (short) -21986, (short) -12918, (short) -12898}};
    static short[][] aShortArrayArray2634 = new short[][]{{(short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 4, (short) 24, (short) 44, (short) 64, (short) 84, (short) 104, (short) 304, (short) 678, (short) 698, (short) 550, (short) 934, (short) 954, (short) 6448, (short) 6946, (short) 6966, (short) 2352, (short) 2726, (short) 2746, (short) 10544, (short) 10918, (short) 10938, (short) 10304, (short) 10550, (short) 10570, (short) 14640, (short) 15014, (short) 15034, (short) 19760, (short) 20134, (short) 20154, (short) -29392, (short) -29018, (short) -28998, (short) 31024, (short) 31270, (short) 31290, (short) -24272, (short) -23898, (short) -23878, (short) -19152, (short) -18778, (short) -18758, (short) -14032, (short) -13658, (short) -13638, (short) -6864, (short) -6490, (short) -6470, (short) 516, (short) 536, (short) 6788, (short) 6808, (short) 11012, (short) 11032, (short) 14980, (short) 15000, (short) 21124, (short) 21144, (short) -28924, (short) -28904, (short) -22012, (short) -21992, (short) -12924, (short) -12904}, {(short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 10339, (short) 10574, (short) 10425, (short) 10398, (short) 10345, (short) 7512, (short) 8507, (short) 7378, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0}, {(short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 4, (short) 24, (short) 44, (short) 64, (short) 84, (short) 104, (short) 304, (short) 678, (short) 698, (short) 550, (short) 934, (short) 954, (short) 6448, (short) 6946, (short) 6966, (short) 2352, (short) 2726, (short) 2746, (short) 10544, (short) 10918, (short) 10938, (short) 10304, (short) 10550, (short) 10570, (short) 14640, (short) 15014, (short) 15034, (short) 19760, (short) 20134, (short) 20154, (short) -29392, (short) -29018, (short) -28998, (short) 31024, (short) 31270, (short) 31290, (short) -24272, (short) -23898, (short) -23878, (short) -19152, (short) -18778, (short) -18758, (short) -14032, (short) -13658, (short) -13638, (short) -6864, (short) -6490, (short) -6470, (short) 516, (short) 536, (short) 6788, (short) 6808, (short) 11012, (short) 11032, (short) 14980, (short) 15000, (short) 21124, (short) 21144, (short) -28924, (short) -28904, (short) -22012, (short) -21992, (short) -12924, (short) -12904}, {(short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 13753, (short) 13737, (short) 13719, (short) 13883, (short) 13863, (short) 13974, (short) 19643, (short) 18601, (short) 16532, (short) 23993, (short) 25121, (short) 24980, (short) 26944, (short) 26921, (short) 24854, (short) 27191, (short) 27171, (short) 26130, (short) 26941, (short) 28696, (short) 30100, (short) 12477, (short) 10407, (short) 10388, (short) 10685, (short) 10665, (short) 10646, (short) 6711, (short) 6693, (short) 6674, (short) 6965, (short) 7073, (short) 7056, (short) 2361, (short) 4387, (short) 3346, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0}, {(short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 0, (short) 4, (short) 24, (short) 44, (short) 64, (short) 84, (short) 104, (short) 304, (short) 678, (short) 698, (short) 550, (short) 934, (short) 954, (short) 6448, (short) 6946, (short) 6966, (short) 2352, (short) 2726, (short) 2746, (short) 10544, (short) 10918, (short) 10938, (short) 10304, (short) 10550, (short) 10570, (short) 14640, (short) 15014, (short) 15034, (short) 19760, (short) 20134, (short) 20154, (short) -29392, (short) -29018, (short) -28998, (short) 31024, (short) 31270, (short) 31290, (short) -24272, (short) -23898, (short) -23878, (short) -19152, (short) -18778, (short) -18758, (short) -14032, (short) -13658, (short) -13638, (short) -6864, (short) -6490, (short) -6470, (short) 516, (short) 536, (short) 6788, (short) 6808, (short) 11012, (short) 11032, (short) 14980, (short) 15000, (short) 21124, (short) 21144, (short) -28924, (short) -28904, (short) -22012, (short) -21992, (short) -12924, (short) -12904}};

    static short[] aShortArray63 = new short[]{(short) 960, (short) 957, (short) -21568, (short) -21571, (short) 22464};
    static short[] aShortArray2219 = new short[]{(short) -4160, (short) -4163, (short) -8256, (short) -8259, (short) 22461};

    static short[][] field3306 = aShortArrayArray1619;
    static short[][] field2638 = aShortArrayArray2634;
    static short[] field385 = aShortArray63;
    static short[] field2642 = aShortArray2219;

    static MRUNodes mruNodes = new MRUNodes(260);
    public final int[] equipment;
    final int[] colors;
    public int privelage;
    public EntityDef desc;
    public int team;
    public String name;
    public int combatLevel;
    public int headIcon;
    public int skullIcon;
    public int hintIcon;
    public int anInt1707;
    boolean aBoolean1699;
    int anInt1708;
    int anInt1709;
    boolean visible;
    int anInt1711;
    int anInt1712;
    int anInt1713;
    Model aModel_1714;
    int anInt1719;
    int anInt1720;
    int anInt1721;
    int anInt1722;
    int skill;
    private long aLong1697;
    private int anInt1702;
    private long aLong1718;

    Player() {
        aLong1697 = -1L;
        aBoolean1699 = false;
        colors = new int[5];
        visible = false;
        equipment = new int[12];
    }

    public Model getRotatedModel() {
        if (!visible) return null;
        Model model = method452();
        if (model == null) return null;
        super.height = model.modelHeight;
        model.aBoolean1659 = true;
        if (aBoolean1699) return model;
        if (super.anInt1520 != -1 && super.currentAnim != -1) {
            SpotAnim spotAnim = SpotAnim.cache[super.anInt1520];
            Model model_2 = spotAnim.getModel();
            if (model_2 != null) {
                Model model_3 = new Model(true, FrameReader.isNullFrame(super.currentAnim), false, model_2);
                model_3.method475(0, -super.graphicHeight, 0);
                model_3.method469();
                model_3.applyTransform(spotAnim.animation.frameIDs[super.currentAnim]);
                model_3.triangleSkinTable = null;
                model_3.vertexSkinTable = null;
                if (spotAnim.anInt410 != 128 || spotAnim.anInt411 != 128)
                    model_3.method478(spotAnim.anInt410, spotAnim.anInt410, spotAnim.anInt411);
                //model_3.method479(64 + spotAnim.anInt413, 850 + spotAnim.anInt414, -30, -50, -30, true);
                model_3.method479(84 + spotAnim.anInt413, 1550 + spotAnim.anInt414, -50, -110, -50, true);
                Model aclass30_sub2_sub4_sub6_1s[] = {model, model_3};
                model = new Model(2, aclass30_sub2_sub4_sub6_1s);
                model.method468();
            }
        }
        if (aModel_1714 != null) {
            if (Client.loopCycle >= anInt1708) aModel_1714 = null;
            if (Client.loopCycle >= anInt1707 && Client.loopCycle < anInt1708) {
                Model model_1 = aModel_1714;
                model_1.method475(anInt1711 - super.x, anInt1712 - anInt1709, anInt1713 - super.y);
                if (super.turnDirection == 512) {
                    model_1.method473();
                    model_1.method473();
                    model_1.method473();
                } else if (super.turnDirection == 1024) {
                    model_1.method473();
                    model_1.method473();
                } else if (super.turnDirection == 1536) model_1.method473();
                Model aclass30_sub2_sub4_sub6s[] = {model, model_1};
                model = new Model(2, aclass30_sub2_sub4_sub6s);
                if (super.turnDirection == 512) model_1.method473();
                else if (super.turnDirection == 1024) {
                    model_1.method473();
                    model_1.method473();
                } else if (super.turnDirection == 1536) {
                    model_1.method473();
                    model_1.method473();
                    model_1.method473();
                }
                model_1.method475(super.x - anInt1711, anInt1709 - anInt1712, super.y - anInt1713);
            }
        }
        model.aBoolean1659 = true;
        return model;
    }

    public void updatePlayer(Stream stream) {
        stream.currentOffset = 0;
        anInt1702 = stream.readUnsignedByte();
        headIcon = stream.readUnsignedByte();
        skullIcon = stream.readUnsignedByte();
        //hintIcon = stream.readUnsignedByte();
        desc = null;
        team = 0;
        for (int j = 0; j < 12; j++) {
            int k = stream.readUnsignedByte();
            if (k == 0) {
                equipment[j] = 0;
                continue;
            }
            int i1 = stream.readUnsignedByte();
            equipment[j] = (k << 8) + i1;
            if (j == 0 && equipment[0] == 65535) {
                desc = EntityDef.forID(stream.readUnsignedWord());
                break;
            }
            if (equipment[j] >= 512 && equipment[j] - 512 < ItemDef.totalItems) {
                int l1 = ItemDef.forID(equipment[j] - 512).team;
                if (l1 != 0) team = l1;
            }
        }

        for (int l = 0; l < 5; l++) {
            int j1 = stream.readUnsignedByte();
            if (j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length) j1 = 0;
            colors[l] = j1;
        }

        super.standAnim = stream.readUnsignedWord();
        if (super.standAnim == 65535) super.standAnim = -1;
        super.anInt1512 = stream.readUnsignedWord();
        if (super.anInt1512 == 65535) super.anInt1512 = -1;
        super.anInt1554 = stream.readUnsignedWord();
        if (super.anInt1554 == 65535) super.anInt1554 = -1;
        super.anInt1555 = stream.readUnsignedWord();
        if (super.anInt1555 == 65535) super.anInt1555 = -1;
        super.anInt1556 = stream.readUnsignedWord();
        if (super.anInt1556 == 65535) super.anInt1556 = -1;
        super.anInt1557 = stream.readUnsignedWord();
        if (super.anInt1557 == 65535) super.anInt1557 = -1;
        super.anInt1505 = stream.readUnsignedWord();
        if (super.anInt1505 == 65535) super.anInt1505 = -1;
        name = TextClass.fixName(TextClass.nameForLong(stream.readQWord()));
        combatLevel = stream.readUnsignedByte();
        skill = stream.readUnsignedWord();
        visible = true;
        aLong1718 = 0L;
        for (int k1 = 0; k1 < 12; k1++) {
            aLong1718 <<= 4;
            if (equipment[k1] >= 256) aLong1718 += equipment[k1] - 256;
        }

        if (equipment[0] >= 256) aLong1718 += equipment[0] - 256 >> 4;
        if (equipment[1] >= 256) aLong1718 += equipment[1] - 256 >> 8;
        for (int i2 = 0; i2 < 5; i2++) {
            aLong1718 <<= 3;
            aLong1718 += colors[i2];
        }

        aLong1718 <<= 1;
        aLong1718 += anInt1702;
    }

    public Model method452() {
        if (desc != null) {
            int currentFrame = -1;
            int nextFrame = -1;
            int cycle1 = 0;
            int cycle2 = 0;
            if (super.anim >= 0 && super.animationDelay == 0) {
                Animation animation = Animation.anims[super.anim];
                currentFrame = animation.frameIDs[super.currentAnimFrame];
                if (Client.instance.tweenAnims && super.nextAnimationFrame != -1) {
                    nextFrame = animation.frameIDs[super.nextAnimationFrame];
                    cycle1 = animation.delays[super.currentAnimFrame];
                    cycle2 = super.anInt1528;
                }
            } else if (super.entityAnimation >= 0) {
                Animation animation = Animation.anims[super.entityAnimation];
                currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
                if (Client.instance.tweenAnims && super.nextAnimationFrame != -1) {
                    nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
                    cycle1 = animation.delays[super.currentForcedAnimFrame];
                    cycle2 = super.anInt1519;
                }
            }
            Model model = desc.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
            return model;
        }
        long l = aLong1718;
        int currentFrame = -1;
        int nextFrame = -1;
        int cycle1 = 0;
        int cycle2 = 0;
        int i1 = -1;
        int j1 = -1;
        int k1 = -1;
        if (super.anim >= 0 && super.animationDelay == 0) {
            Animation animation = Animation.anims[super.anim];
            currentFrame = animation.frameIDs[super.currentAnimFrame];
            if (Client.instance.tweenAnims && super.nextAnimationFrame != -1) {
                try {
                    nextFrame = animation.frameIDs[super.nextAnimationFrame];
                    cycle1 = animation.delays[super.currentAnimFrame];
                    cycle2 = super.anInt1528;
                } catch (Exception e) {
                }
            }
            if (super.entityAnimation >= 0 && super.entityAnimation != super.standAnim)
                i1 = Animation.anims[super.entityAnimation].frameIDs[super.currentForcedAnimFrame];
            if (animation.leftHandItem >= 0) {
                j1 = animation.leftHandItem;
                l += j1 - equipment[5] << 40;
            }
            if (animation.rightHandItem >= 0) {
                k1 = animation.rightHandItem;
                l += k1 - equipment[3] << 48;
            }
        } else if (super.entityAnimation >= 0) {
            Animation animation = Animation.anims[super.entityAnimation];
            currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
            if (Client.instance.tweenAnims && super.nextAnimationFrame != -1) {
                nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
                cycle1 = animation.delays[super.currentForcedAnimFrame];
                cycle2 = super.anInt1519;
            }
        }
        Model model_1 = (Model) mruNodes.insertFromCache(l);
        if (model_1 == null) {
            boolean flag = false;
            for (int i2 = 0; i2 < 12; i2++) {
                int k2 = equipment[i2];
                if (k1 >= 0 && i2 == 3) k2 = k1;
                if (j1 >= 0 && i2 == 5) k2 = j1;
                if (k2 >= 256 && k2 < 512 && !IDK.cache[k2 - 256].method537()) flag = true;
                if (k2 >= 512 && !ItemDef.forID(k2 - 512).method195(anInt1702)) flag = true;
            }

            if (flag) {
                if (aLong1697 != -1L) model_1 = (Model) mruNodes.insertFromCache(aLong1697);
                if (model_1 == null) return null;
            }
        }
        if (model_1 == null) {
            Model aclass30_sub2_sub4_sub6s[] = new Model[12];
            int j2 = 0;
            for (int l2 = 0; l2 < 12; l2++) {
                int i3 = equipment[l2];
                if (k1 >= 0 && l2 == 3) i3 = k1;
                if (j1 >= 0 && l2 == 5) i3 = j1;
                if (i3 >= 256 && i3 < 512) {
                    Model model_3 = IDK.cache[i3 - 256].method538();
                    if (model_3 != null) aclass30_sub2_sub4_sub6s[j2++] = model_3;
                }
                if (i3 >= 512) {
                    Model model_4 = ItemDef.forID(i3 - 512).method196(anInt1702);
                    if (model_4 != null) aclass30_sub2_sub4_sub6s[j2++] = model_4;
                }
            }

            model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
            if (false) {
                for (int var15 = 0; var15 < 5; ++var15) {
                    if (~colors[var15] > ~field3306[var15].length) {
                        model_1.method476(field385[var15], field3306[var15][colors[var15]]);
                    }
                    if (~field2638[var15].length < ~colors[var15]) {
                        model_1.method476(field2642[var15], field2638[var15][colors[var15]]);
                    }
                }
            } else {
                for (int i = 0; i < 5; i++)
                    if (colors[i] != 0) {
                        model_1.method476(Client.anIntArrayArray1003[i][0], Client.anIntArrayArray1003[i][colors[i]]);
                        if (i == 1) model_1.method476(Client.anIntArray1204[0], Client.anIntArray1204[colors[i]]);
                    }
            }
            model_1.method469();
            //model_1.method479(64, 850, -30, -50, -30, true);
            model_1.method479(84, 1000, -90, -580, -90, true);
            mruNodes.removeFromCache(model_1, l);
            aLong1697 = l;
        }
        if (aBoolean1699) return model_1;
        Model model_2 = Model.aModel_1621;
        model_2.method464(model_1, FrameReader.isNullFrame(currentFrame) & FrameReader.isNullFrame(i1));
        if (currentFrame != -1 && i1 != -1)
            model_2.method471(Animation.anims[super.anim].animationFlowControl, i1, currentFrame);
        else if (currentFrame != -1 && nextFrame != -1 && Client.instance.tweenAnims)
            model_2.applyTransform(currentFrame, nextFrame, cycle1, cycle2);
        else model_2.applyTransform(currentFrame);
        model_2.method466();
        model_2.triangleSkinTable = null;
        model_2.vertexSkinTable = null;
        return model_2;
    }

    public boolean isVisible() {
        return visible;
    }

    public Model method453() {
        if (!visible) return null;
        if (desc != null) return desc.method160();
        boolean flag = false;
        for (int i = 0; i < 12; i++) {
            int j = equipment[i];
            if (j >= 256 && j < 512 && !IDK.cache[j - 256].method539()) flag = true;
            if (j >= 512 && !ItemDef.forID(j - 512).method192(anInt1702)) flag = true;
        }

        if (flag) return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[12];
        int k = 0;
        for (int l = 0; l < 12; l++) {
            int i1 = equipment[l];
            if (i1 >= 256 && i1 < 512) {
                Model model_1 = IDK.cache[i1 - 256].method540();
                if (model_1 != null) aclass30_sub2_sub4_sub6s[k++] = model_1;
            }
            if (i1 >= 512) {
                Model model_2 = ItemDef.forID(i1 - 512).method194(anInt1702);
                if (model_2 != null) aclass30_sub2_sub4_sub6s[k++] = model_2;
            }
        }

        Model model = new Model(k, aclass30_sub2_sub4_sub6s);
        for (int j1 = 0; j1 < 5; j1++)
            if (colors[j1] != 0) {
                model.method476(Client.anIntArrayArray1003[j1][0], Client.anIntArrayArray1003[j1][colors[j1]]);
                if (j1 == 1) model.method476(Client.anIntArray1204[0], Client.anIntArray1204[colors[j1]]);
            }

        return model;
    }

}

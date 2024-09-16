package com.proxy;

final class Rasterizer extends DrawingArea {

	public static void nullLoader() {
		anIntArray1468 = null;
		anIntArray1468 = null;
		SINE = null;
		COSINE = null;
		rowOffsets = null;
		textures = null;
		transparent = null;
		anIntArray1476 = null;
		texelArrayPool = null;
		texelCache = null;
		textureLastUsed = null;
		HSLtoRGB = null;
		texturePalettes = null;
	}

	public static void method364() {
		rowOffsets = new int[DrawingArea.height];
		for (int j = 0; j < DrawingArea.height; j++)
			rowOffsets[j] = DrawingArea.width * j;

		originX = DrawingArea.width / 2;
		originY = DrawingArea.height / 2;
	}

	public static void method365(int j, int k) {
		rowOffsets = new int[k];
		for (int l = 0; l < k; l++)
			rowOffsets[l] = j * l;

		originX = j / 2;
		originY = k / 2;
	}

	public static void method366() {
		texelArrayPool = null;
		for (int j = 0; j < 51; j++)
			texelCache[j] = null;

	}

	public static void method367() {
		if (texelArrayPool == null) {
			texelPoolPointer = 20;// was parameter
			if (lowMem)
				texelArrayPool = new int[texelPoolPointer][16384];
			else
				texelArrayPool = new int[texelPoolPointer][0x10000];
			for (int k = 0; k < 51; k++)
				texelCache[k] = null;

		}
	}

	public static void unpack(StreamLoader streamLoader) {
		loadedTextureCount = 0;
		for (int j = 0; j < 51; j++)
			try {
				textures[j] = new Background(streamLoader,
						String.valueOf(j), 0);
				if (lowMem && textures[j].anInt1456 == 128)
					textures[j].method356();
				else
					textures[j].method357();
				loadedTextureCount++;
			} catch (Exception _ex) {
			}

	}

	public static void method368(StreamLoader streamLoader) {
		loadedTextureCount = 0;
		for (int j = 0; j < 51; j++)
			try {
				textures[j] = new Background(streamLoader,
						String.valueOf(j), 0);
				if (lowMem && textures[j].anInt1456 == 128)
					textures[j].method356();
				else
					textures[j].method357();
				loadedTextureCount++;
			} catch (Exception _ex) {
			}

	}

	public static int method369(int i) {
		if (anIntArray1476[i] != 0)
			return anIntArray1476[i];
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = texturePalettes[i].length;
		for (int k1 = 0; k1 < j1; k1++) {
			k += texturePalettes[i][k1] >> 16 & 0xff;
			l += texturePalettes[i][k1] >> 8 & 0xff;
			i1 += texturePalettes[i][k1] & 0xff;
		}

		int l1 = (k / j1 << 16) + (l / j1 << 8) + i1 / j1;
		l1 = method373(l1, 1.3999999999999999D);
		if (l1 == 0)
			l1 = 1;
		anIntArray1476[i] = l1;
		return l1;
	}

	public static void method370(int i) {
		if (texelCache[i] == null)
			return;
		texelArrayPool[texelPoolPointer++] = texelCache[i];
		texelCache[i] = null;
	}

	private static int[] method371(int i) {
		textureLastUsed[i] = textureGetCount++;
		if (texelCache[i] != null)
			return texelCache[i];
		int ai[];
		if (texelPoolPointer > 0) {
			ai = texelArrayPool[--texelPoolPointer];
			texelArrayPool[texelPoolPointer] = null;
		} else {
			int j = 0;
			int k = -1;
			for (int l = 0; l < loadedTextureCount; l++)
				if (texelCache[l] != null
						&& (textureLastUsed[l] < j || k == -1)) {
					j = textureLastUsed[l];
					k = l;
				}

			ai = texelCache[k];
			texelCache[k] = null;
		}
		texelCache[i] = ai;
		Background background = textures[i];
		int ai1[] = texturePalettes[i];
		if (lowMem) {
			transparent[i] = false;
			for (int i1 = 0; i1 < 4096; i1++) {
				int i2 = ai[i1] = ai1[background.aByteArray1450[i1]] & 0xf8f8ff;
				if (i2 == 0)
					transparent[i] = true;
				ai[4096 + i1] = i2 - (i2 >>> 3) & 0xf8f8ff;
				ai[8192 + i1] = i2 - (i2 >>> 2) & 0xf8f8ff;
				ai[12288 + i1] = i2 - (i2 >>> 2) - (i2 >>> 3) & 0xf8f8ff;
			}

		} else {
			if (background.anInt1452 == 64) {
				for (int j1 = 0; j1 < 128; j1++) {
					for (int j2 = 0; j2 < 128; j2++)
						ai[j2 + (j1 << 7)] = ai1[background.aByteArray1450[(j2 >> 1)
								+ ((j1 >> 1) << 6)]];

				}

			} else {
				for (int k1 = 0; k1 < 16384; k1++)
					ai[k1] = ai1[background.aByteArray1450[k1]];

			}
			transparent[i] = false;
			for (int l1 = 0; l1 < 16384; l1++) {
				ai[l1] &= 0xf8f8ff;
				int k2 = ai[l1];
				if (k2 == 0)
					transparent[i] = true;
				ai[16384 + l1] = k2 - (k2 >>> 3) & 0xf8f8ff;
				ai[32768 + l1] = k2 - (k2 >>> 2) & 0xf8f8ff;
				ai[49152 + l1] = k2 - (k2 >>> 2) - (k2 >>> 3) & 0xf8f8ff;
			}

		}
		return ai;
	}

	public static void method372(double d) {
		d += Math.random() * 0.029999999999999999D - 0.014999999999999999D;
		int j = 0;
		for (int k = 0; k < 512; k++) {
			double d1 = (double) (k / 8) / 64D + 0.0078125D;
			double d2 = (double) (k & 7) / 8D + 0.0625D;
			for (int k1 = 0; k1 < 128; k1++) {
				double d3 = (double) k1 / 128D;
				double d4 = d3;
				double d5 = d3;
				double d6 = d3;
				if (d2 != 0.0D) {
					double d7;
					if (d3 < 0.5D)
						d7 = d3 * (1.0D + d2);
					else
						d7 = (d3 + d2) - d3 * d2;
					double d8 = 2D * d3 - d7;
					double d9 = d1 + 0.33333333333333331D;
					if (d9 > 1.0D)
						d9--;
					double d10 = d1;
					double d11 = d1 - 0.33333333333333331D;
					if (d11 < 0.0D)
						d11++;
					if (6D * d9 < 1.0D)
						d4 = d8 + (d7 - d8) * 6D * d9;
					else if (2D * d9 < 1.0D)
						d4 = d7;
					else if (3D * d9 < 2D)
						d4 = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
					else
						d4 = d8;
					if (6D * d10 < 1.0D)
						d5 = d8 + (d7 - d8) * 6D * d10;
					else if (2D * d10 < 1.0D)
						d5 = d7;
					else if (3D * d10 < 2D)
						d5 = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
					else
						d5 = d8;
					if (6D * d11 < 1.0D)
						d6 = d8 + (d7 - d8) * 6D * d11;
					else if (2D * d11 < 1.0D)
						d6 = d7;
					else if (3D * d11 < 2D)
						d6 = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
					else
						d6 = d8;
				}
				int l1 = (int) (d4 * 256D);
				int i2 = (int) (d5 * 256D);
				int j2 = (int) (d6 * 256D);
				int k2 = (l1 << 16) + (i2 << 8) + j2;
				k2 = method373(k2, d);
				if (k2 == 0)
					k2 = 1;
				HSLtoRGB[j++] = k2;
			}

		}

		for (int l = 0; l < 51; l++)
			if (textures[l] != null) {
				int ai[] = textures[l].anIntArray1451;
				texturePalettes[l] = new int[ai.length];
				for (int j1 = 0; j1 < ai.length; j1++) {
					texturePalettes[l][j1] = method373(ai[j1], d);
					if ((texturePalettes[l][j1] & 0xf8f8ff) == 0 && j1 != 0)
						texturePalettes[l][j1] = 1;
				}

			}

		for (int i1 = 0; i1 < 51; i1++)
			method370(i1);

	}

	private static int method373(int i, double d) {
		double d1 = (double) (i >> 16) / 256D;
		double d2 = (double) (i >> 8 & 0xff) / 256D;
		double d3 = (double) (i & 0xff) / 256D;
		d1 = Math.pow(d1, d);
		d2 = Math.pow(d2, d);
		d3 = Math.pow(d3, d);
		int j = (int) (d1 * 256D);
		int k = (int) (d2 * 256D);
		int l = (int) (d3 * 256D);
		return (j << 16) + (k << 8) + l;
	}

	public static void method374(int i, int j, int k, int l, int i1, int j1,
			int k1, int l1, int i2) {
		int j2 = 0;
		int k2 = 0;
		if (j != i) {
			j2 = (i1 - l << 16) / (j - i);
			k2 = (l1 - k1 << 15) / (j - i);
		}
		int l2 = 0;
		int i3 = 0;
		if (k != j) {
			l2 = (j1 - i1 << 16) / (k - j);
			i3 = (i2 - l1 << 15) / (k - j);
		}
		int j3 = 0;
		int k3 = 0;
		if (k != i) {
			j3 = (l - j1 << 16) / (i - k);
			k3 = (k1 - i2 << 15) / (i - k);
		}
		if (i <= j && i <= k) {
			if (i >= DrawingArea.bottomY)
				return;
			if (j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if (k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if (j < k) {
				j1 = l <<= 16;
				i2 = k1 <<= 15;
				if (i < 0) {
					j1 -= j3 * i;
					l -= j2 * i;
					i2 -= k3 * i;
					k1 -= k2 * i;
					i = 0;
				}
				i1 <<= 16;
				l1 <<= 15;
				if (j < 0) {
					i1 -= l2 * j;
					l1 -= i3 * j;
					j = 0;
				}
				if (i != j && j3 < j2 || i == j && j3 > l2) {
					k -= j;
					j -= i;
					for (i = rowOffsets[i]; --j >= 0; i += DrawingArea.width) {
						method375(DrawingArea.pixels, i, j1 >> 16, l >> 16,
								i2 >> 7, k1 >> 7);
						j1 += j3;
						l += j2;
						i2 += k3;
						k1 += k2;
					}

					while (--k >= 0) {
						method375(DrawingArea.pixels, i, j1 >> 16, i1 >> 16,
								i2 >> 7, l1 >> 7);
						j1 += j3;
						i1 += l2;
						i2 += k3;
						l1 += i3;
						i += DrawingArea.width;
					}
					return;
				}
				k -= j;
				j -= i;
				for (i = rowOffsets[i]; --j >= 0; i += DrawingArea.width) {
					method375(DrawingArea.pixels, i, l >> 16, j1 >> 16,
							k1 >> 7, i2 >> 7);
					j1 += j3;
					l += j2;
					i2 += k3;
					k1 += k2;
				}

				while (--k >= 0) {
					method375(DrawingArea.pixels, i, i1 >> 16, j1 >> 16,
							l1 >> 7, i2 >> 7);
					j1 += j3;
					i1 += l2;
					i2 += k3;
					l1 += i3;
					i += DrawingArea.width;
				}
				return;
			}
			i1 = l <<= 16;
			l1 = k1 <<= 15;
			if (i < 0) {
				i1 -= j3 * i;
				l -= j2 * i;
				l1 -= k3 * i;
				k1 -= k2 * i;
				i = 0;
			}
			j1 <<= 16;
			i2 <<= 15;
			if (k < 0) {
				j1 -= l2 * k;
				i2 -= i3 * k;
				k = 0;
			}
			if (i != k && j3 < j2 || i == k && l2 > j2) {
				j -= k;
				k -= i;
				for (i = rowOffsets[i]; --k >= 0; i += DrawingArea.width) {
					method375(DrawingArea.pixels, i, i1 >> 16, l >> 16,
							l1 >> 7, k1 >> 7);
					i1 += j3;
					l += j2;
					l1 += k3;
					k1 += k2;
				}

				while (--j >= 0) {
					method375(DrawingArea.pixels, i, j1 >> 16, l >> 16,
							i2 >> 7, k1 >> 7);
					j1 += l2;
					l += j2;
					i2 += i3;
					k1 += k2;
					i += DrawingArea.width;
				}
				return;
			}
			j -= k;
			k -= i;
			for (i = rowOffsets[i]; --k >= 0; i += DrawingArea.width) {
				method375(DrawingArea.pixels, i, l >> 16, i1 >> 16, k1 >> 7,
						l1 >> 7);
				i1 += j3;
				l += j2;
				l1 += k3;
				k1 += k2;
			}

			while (--j >= 0) {
				method375(DrawingArea.pixels, i, l >> 16, j1 >> 16, k1 >> 7,
						i2 >> 7);
				j1 += l2;
				l += j2;
				i2 += i3;
				k1 += k2;
				i += DrawingArea.width;
			}
			return;
		}
		if (j <= k) {
			if (j >= DrawingArea.bottomY)
				return;
			if (k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if (i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if (k < i) {
				l = i1 <<= 16;
				k1 = l1 <<= 15;
				if (j < 0) {
					l -= j2 * j;
					i1 -= l2 * j;
					k1 -= k2 * j;
					l1 -= i3 * j;
					j = 0;
				}
				j1 <<= 16;
				i2 <<= 15;
				if (k < 0) {
					j1 -= j3 * k;
					i2 -= k3 * k;
					k = 0;
				}
				if (j != k && j2 < l2 || j == k && j2 > j3) {
					i -= k;
					k -= j;
					for (j = rowOffsets[j]; --k >= 0; j += DrawingArea.width) {
						method375(DrawingArea.pixels, j, l >> 16, i1 >> 16,
								k1 >> 7, l1 >> 7);
						l += j2;
						i1 += l2;
						k1 += k2;
						l1 += i3;
					}

					while (--i >= 0) {
						method375(DrawingArea.pixels, j, l >> 16, j1 >> 16,
								k1 >> 7, i2 >> 7);
						l += j2;
						j1 += j3;
						k1 += k2;
						i2 += k3;
						j += DrawingArea.width;
					}
					return;
				}
				i -= k;
				k -= j;
				for (j = rowOffsets[j]; --k >= 0; j += DrawingArea.width) {
					method375(DrawingArea.pixels, j, i1 >> 16, l >> 16,
							l1 >> 7, k1 >> 7);
					l += j2;
					i1 += l2;
					k1 += k2;
					l1 += i3;
				}

				while (--i >= 0) {
					method375(DrawingArea.pixels, j, j1 >> 16, l >> 16,
							i2 >> 7, k1 >> 7);
					l += j2;
					j1 += j3;
					k1 += k2;
					i2 += k3;
					j += DrawingArea.width;
				}
				return;
			}
			j1 = i1 <<= 16;
			i2 = l1 <<= 15;
			if (j < 0) {
				j1 -= j2 * j;
				i1 -= l2 * j;
				i2 -= k2 * j;
				l1 -= i3 * j;
				j = 0;
			}
			l <<= 16;
			k1 <<= 15;
			if (i < 0) {
				l -= j3 * i;
				k1 -= k3 * i;
				i = 0;
			}
			if (j2 < l2) {
				k -= i;
				i -= j;
				for (j = rowOffsets[j]; --i >= 0; j += DrawingArea.width) {
					method375(DrawingArea.pixels, j, j1 >> 16, i1 >> 16,
							i2 >> 7, l1 >> 7);
					j1 += j2;
					i1 += l2;
					i2 += k2;
					l1 += i3;
				}

				while (--k >= 0) {
					method375(DrawingArea.pixels, j, l >> 16, i1 >> 16,
							k1 >> 7, l1 >> 7);
					l += j3;
					i1 += l2;
					k1 += k3;
					l1 += i3;
					j += DrawingArea.width;
				}
				return;
			}
			k -= i;
			i -= j;
			for (j = rowOffsets[j]; --i >= 0; j += DrawingArea.width) {
				method375(DrawingArea.pixels, j, i1 >> 16, j1 >> 16, l1 >> 7,
						i2 >> 7);
				j1 += j2;
				i1 += l2;
				i2 += k2;
				l1 += i3;
			}

			while (--k >= 0) {
				method375(DrawingArea.pixels, j, i1 >> 16, l >> 16, l1 >> 7,
						k1 >> 7);
				l += j3;
				i1 += l2;
				k1 += k3;
				l1 += i3;
				j += DrawingArea.width;
			}
			return;
		}
		if (k >= DrawingArea.bottomY)
			return;
		if (i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if (j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if (i < j) {
			i1 = j1 <<= 16;
			l1 = i2 <<= 15;
			if (k < 0) {
				i1 -= l2 * k;
				j1 -= j3 * k;
				l1 -= i3 * k;
				i2 -= k3 * k;
				k = 0;
			}
			l <<= 16;
			k1 <<= 15;
			if (i < 0) {
				l -= j2 * i;
				k1 -= k2 * i;
				i = 0;
			}
			if (l2 < j3) {
				j -= i;
				i -= k;
				for (k = rowOffsets[k]; --i >= 0; k += DrawingArea.width) {
					method375(DrawingArea.pixels, k, i1 >> 16, j1 >> 16,
							l1 >> 7, i2 >> 7);
					i1 += l2;
					j1 += j3;
					l1 += i3;
					i2 += k3;
				}

				while (--j >= 0) {
					method375(DrawingArea.pixels, k, i1 >> 16, l >> 16,
							l1 >> 7, k1 >> 7);
					i1 += l2;
					l += j2;
					l1 += i3;
					k1 += k2;
					k += DrawingArea.width;
				}
				return;
			}
			j -= i;
			i -= k;
			for (k = rowOffsets[k]; --i >= 0; k += DrawingArea.width) {
				method375(DrawingArea.pixels, k, j1 >> 16, i1 >> 16, i2 >> 7,
						l1 >> 7);
				i1 += l2;
				j1 += j3;
				l1 += i3;
				i2 += k3;
			}

			while (--j >= 0) {
				method375(DrawingArea.pixels, k, l >> 16, i1 >> 16, k1 >> 7,
						l1 >> 7);
				i1 += l2;
				l += j2;
				l1 += i3;
				k1 += k2;
				k += DrawingArea.width;
			}
			return;
		}
		l = j1 <<= 16;
		k1 = i2 <<= 15;
		if (k < 0) {
			l -= l2 * k;
			j1 -= j3 * k;
			k1 -= i3 * k;
			i2 -= k3 * k;
			k = 0;
		}
		i1 <<= 16;
		l1 <<= 15;
		if (j < 0) {
			i1 -= j2 * j;
			l1 -= k2 * j;
			j = 0;
		}
		if (l2 < j3) {
			i -= j;
			j -= k;
			for (k = rowOffsets[k]; --j >= 0; k += DrawingArea.width) {
				method375(DrawingArea.pixels, k, l >> 16, j1 >> 16, k1 >> 7,
						i2 >> 7);
				l += l2;
				j1 += j3;
				k1 += i3;
				i2 += k3;
			}

			while (--i >= 0) {
				method375(DrawingArea.pixels, k, i1 >> 16, j1 >> 16, l1 >> 7,
						i2 >> 7);
				i1 += j2;
				j1 += j3;
				l1 += k2;
				i2 += k3;
				k += DrawingArea.width;
			}
			return;
		}
		i -= j;
		j -= k;
		for (k = rowOffsets[k]; --j >= 0; k += DrawingArea.width) {
			method375(DrawingArea.pixels, k, j1 >> 16, l >> 16, i2 >> 7,
					k1 >> 7);
			l += l2;
			j1 += j3;
			k1 += i3;
			i2 += k3;
		}

		while (--i >= 0) {
			method375(DrawingArea.pixels, k, j1 >> 16, i1 >> 16, i2 >> 7,
					l1 >> 7);
			i1 += j2;
			j1 += j3;
			l1 += k2;
			i2 += k3;
			k += DrawingArea.width;
		}
	}

	public static void method375(int[] ai, int i, int l, int i1, int j1, int k1) {
		int j;
		int k;
		int l1 = 0;
		if (clippedHorizontally) {
			if (i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if (l < 0) {
				j1 -= l * l1;
				l = 0;
			}
		}
		if (l < i1) {
			i += l;
			j1 += l1 * l;
			if (aBoolean1464) {
				k = i1 - l >> 2;
				if (k > 0)
					l1 = (k1 - j1) * anIntArray1468[k] >> 15;
				else
					l1 = 0;
				if (anInt1465 == 0) {
					if (k > 0) {
						do {
							j = HSLtoRGB[j1 >> 8];
							j1 += l1;
							ai[i] = j;
							i++;
							ai[i] = j;
							i++;
							ai[i] = j;
							i++;
							ai[i] = j;
							i++;
						} while (--k > 0);
					}
					k = i1 - l & 0x3;
					if (k > 0) {
						j = HSLtoRGB[j1 >> 8];
						do
							ai[i++] = j;
						while (--k > 0);
					}
				} else {
					int j2 = anInt1465;
					int l2 = 256 - anInt1465;
					if (k > 0) {
						do {
							j = HSLtoRGB[j1 >> 8];
							j1 += l1;
							j = (((j & 0xff00ff) * l2 >> 8 & 0xff00ff) + ((j & 0xff00)
									* l2 >> 8 & 0xff00));
							int h = ai[i];
							ai[i] = (j
									+ ((h & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((h & 0xff00)
									* j2 >> 8 & 0xff00));
							i++;
							h = ai[i];
							ai[i] = (j
									+ ((h & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((h & 0xff00)
									* j2 >> 8 & 0xff00));
							i++;
							h = ai[i];
							ai[i] = (j
									+ ((h & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((h & 0xff00)
									* j2 >> 8 & 0xff00));
							i++;
							h = ai[i];
							ai[i] = (j
									+ ((h & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((h & 0xff00)
									* j2 >> 8 & 0xff00));
							i++;
						} while (--k > 0);
					}
					k = i1 - l & 0x3;
					if (k > 0) {
						j = HSLtoRGB[j1 >> 8];
						j = (((j & 0xff00ff) * l2 >> 8 & 0xff00ff) + ((j & 0xff00)
								* l2 >> 8 & 0xff00));
						do {
							int i_61_ = ai[i];
							ai[i] = (j
									+ ((i_61_ & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((i_61_ & 0xff00)
									* j2 >> 8 & 0xff00));
							i++;
						} while (--k > 0);
					}
				}
			} else {
				int i2 = (k1 - j1) / (i1 - l);
				k = i1 - l;
				if (anInt1465 == 0) {
					do {
						ai[i] = HSLtoRGB[j1 >> 8];
						i++;
						j1 += i2;
					} while (--k > 0);
				} else {
					int i_62_ = anInt1465;
					int i_63_ = 256 - anInt1465;
					do {
						j = HSLtoRGB[j1 >> 8];
						j1 += i2;
						j = (((j & 0xff00ff) * i_63_ >> 8 & 0xff00ff) + ((j & 0xff00)
								* i_63_ >> 8 & 0xff00));
						int i_64_ = ai[i];
						ai[i] = (j
								+ ((i_64_ & 0xff00ff) * i_62_ >> 8 & 0xff00ff) + ((i_64_ & 0xff00)
								* i_62_ >> 8 & 0xff00));
						i++;
					} while (--k > 0);
				}
			}
		}
	}

	public static void method376(int i, int j, int k, int l, int i1, int j1,
			int k1) {
		int l1 = 0;
		if (j != i)
			l1 = (i1 - l << 16) / (j - i);
		int i2 = 0;
		if (k != j)
			i2 = (j1 - i1 << 16) / (k - j);
		int j2 = 0;
		if (k != i)
			j2 = (l - j1 << 16) / (i - k);
		if (i <= j && i <= k) {
			if (i >= DrawingArea.bottomY)
				return;
			if (j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if (k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if (j < k) {
				j1 = l <<= 16;
				if (i < 0) {
					j1 -= j2 * i;
					l -= l1 * i;
					i = 0;
				}
				i1 <<= 16;
				if (j < 0) {
					i1 -= i2 * j;
					j = 0;
				}
				if (i != j && j2 < l1 || i == j && j2 > i2) {
					k -= j;
					j -= i;
					for (i = rowOffsets[i]; --j >= 0; i += DrawingArea.width) {
						method377(DrawingArea.pixels, i, k1, j1 >> 16, l >> 16);
						j1 += j2;
						l += l1;
					}

					while (--k >= 0) {
						method377(DrawingArea.pixels, i, k1, j1 >> 16, i1 >> 16);
						j1 += j2;
						i1 += i2;
						i += DrawingArea.width;
					}
					return;
				}
				k -= j;
				j -= i;
				for (i = rowOffsets[i]; --j >= 0; i += DrawingArea.width) {
					method377(DrawingArea.pixels, i, k1, l >> 16, j1 >> 16);
					j1 += j2;
					l += l1;
				}

				while (--k >= 0) {
					method377(DrawingArea.pixels, i, k1, i1 >> 16, j1 >> 16);
					j1 += j2;
					i1 += i2;
					i += DrawingArea.width;
				}
				return;
			}
			i1 = l <<= 16;
			if (i < 0) {
				i1 -= j2 * i;
				l -= l1 * i;
				i = 0;
			}
			j1 <<= 16;
			if (k < 0) {
				j1 -= i2 * k;
				k = 0;
			}
			if (i != k && j2 < l1 || i == k && i2 > l1) {
				j -= k;
				k -= i;
				for (i = rowOffsets[i]; --k >= 0; i += DrawingArea.width) {
					method377(DrawingArea.pixels, i, k1, i1 >> 16, l >> 16);
					i1 += j2;
					l += l1;
				}

				while (--j >= 0) {
					method377(DrawingArea.pixels, i, k1, j1 >> 16, l >> 16);
					j1 += i2;
					l += l1;
					i += DrawingArea.width;
				}
				return;
			}
			j -= k;
			k -= i;
			for (i = rowOffsets[i]; --k >= 0; i += DrawingArea.width) {
				method377(DrawingArea.pixels, i, k1, l >> 16, i1 >> 16);
				i1 += j2;
				l += l1;
			}

			while (--j >= 0) {
				method377(DrawingArea.pixels, i, k1, l >> 16, j1 >> 16);
				j1 += i2;
				l += l1;
				i += DrawingArea.width;
			}
			return;
		}
		if (j <= k) {
			if (j >= DrawingArea.bottomY)
				return;
			if (k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if (i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if (k < i) {
				l = i1 <<= 16;
				if (j < 0) {
					l -= l1 * j;
					i1 -= i2 * j;
					j = 0;
				}
				j1 <<= 16;
				if (k < 0) {
					j1 -= j2 * k;
					k = 0;
				}
				if (j != k && l1 < i2 || j == k && l1 > j2) {
					i -= k;
					k -= j;
					for (j = rowOffsets[j]; --k >= 0; j += DrawingArea.width) {
						method377(DrawingArea.pixels, j, k1, l >> 16, i1 >> 16);
						l += l1;
						i1 += i2;
					}

					while (--i >= 0) {
						method377(DrawingArea.pixels, j, k1, l >> 16, j1 >> 16);
						l += l1;
						j1 += j2;
						j += DrawingArea.width;
					}
					return;
				}
				i -= k;
				k -= j;
				for (j = rowOffsets[j]; --k >= 0; j += DrawingArea.width) {
					method377(DrawingArea.pixels, j, k1, i1 >> 16, l >> 16);
					l += l1;
					i1 += i2;
				}

				while (--i >= 0) {
					method377(DrawingArea.pixels, j, k1, j1 >> 16, l >> 16);
					l += l1;
					j1 += j2;
					j += DrawingArea.width;
				}
				return;
			}
			j1 = i1 <<= 16;
			if (j < 0) {
				j1 -= l1 * j;
				i1 -= i2 * j;
				j = 0;
			}
			l <<= 16;
			if (i < 0) {
				l -= j2 * i;
				i = 0;
			}
			if (l1 < i2) {
				k -= i;
				i -= j;
				for (j = rowOffsets[j]; --i >= 0; j += DrawingArea.width) {
					method377(DrawingArea.pixels, j, k1, j1 >> 16, i1 >> 16);
					j1 += l1;
					i1 += i2;
				}

				while (--k >= 0) {
					method377(DrawingArea.pixels, j, k1, l >> 16, i1 >> 16);
					l += j2;
					i1 += i2;
					j += DrawingArea.width;
				}
				return;
			}
			k -= i;
			i -= j;
			for (j = rowOffsets[j]; --i >= 0; j += DrawingArea.width) {
				method377(DrawingArea.pixels, j, k1, i1 >> 16, j1 >> 16);
				j1 += l1;
				i1 += i2;
			}

			while (--k >= 0) {
				method377(DrawingArea.pixels, j, k1, i1 >> 16, l >> 16);
				l += j2;
				i1 += i2;
				j += DrawingArea.width;
			}
			return;
		}
		if (k >= DrawingArea.bottomY)
			return;
		if (i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if (j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if (i < j) {
			i1 = j1 <<= 16;
			if (k < 0) {
				i1 -= i2 * k;
				j1 -= j2 * k;
				k = 0;
			}
			l <<= 16;
			if (i < 0) {
				l -= l1 * i;
				i = 0;
			}
			if (i2 < j2) {
				j -= i;
				i -= k;
				for (k = rowOffsets[k]; --i >= 0; k += DrawingArea.width) {
					method377(DrawingArea.pixels, k, k1, i1 >> 16, j1 >> 16);
					i1 += i2;
					j1 += j2;
				}

				while (--j >= 0) {
					method377(DrawingArea.pixels, k, k1, i1 >> 16, l >> 16);
					i1 += i2;
					l += l1;
					k += DrawingArea.width;
				}
				return;
			}
			j -= i;
			i -= k;
			for (k = rowOffsets[k]; --i >= 0; k += DrawingArea.width) {
				method377(DrawingArea.pixels, k, k1, j1 >> 16, i1 >> 16);
				i1 += i2;
				j1 += j2;
			}

			while (--j >= 0) {
				method377(DrawingArea.pixels, k, k1, l >> 16, i1 >> 16);
				i1 += i2;
				l += l1;
				k += DrawingArea.width;
			}
			return;
		}
		l = j1 <<= 16;
		if (k < 0) {
			l -= i2 * k;
			j1 -= j2 * k;
			k = 0;
		}
		i1 <<= 16;
		if (j < 0) {
			i1 -= l1 * j;
			j = 0;
		}
		if (i2 < j2) {
			i -= j;
			j -= k;
			for (k = rowOffsets[k]; --j >= 0; k += DrawingArea.width) {
				method377(DrawingArea.pixels, k, k1, l >> 16, j1 >> 16);
				l += i2;
				j1 += j2;
			}

			while (--i >= 0) {
				method377(DrawingArea.pixels, k, k1, i1 >> 16, j1 >> 16);
				i1 += l1;
				j1 += j2;
				k += DrawingArea.width;
			}
			return;
		}
		i -= j;
		j -= k;
		for (k = rowOffsets[k]; --j >= 0; k += DrawingArea.width) {
			method377(DrawingArea.pixels, k, k1, j1 >> 16, l >> 16);
			l += i2;
			j1 += j2;
		}

		while (--i >= 0) {
			method377(DrawingArea.pixels, k, k1, j1 >> 16, i1 >> 16);
			i1 += l1;
			j1 += j2;
			k += DrawingArea.width;
		}
	}

	private static void method377(int ai[], int i, int j, int l, int i1) {
		int k;// was parameter
		if (clippedHorizontally) {
			if (i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if (l < 0)
				l = 0;
		}
		if (l >= i1)
			return;
		i += l;
		k = i1 - l >> 2;
		if (anInt1465 == 0) {
			while (--k >= 0) {
				ai[i] = j;
				i++;
				ai[i] = j;
				i++;
				ai[i] = j;
				i++;
				ai[i] = j;
				i++;
			}
			for (k = i1 - l & 3; --k >= 0;)
				ai[i++] = j;

			return;
		}
		int j1 = anInt1465;
		int k1 = 256 - anInt1465;
		j = ((j & 0xff00ff) * k1 >> 8 & 0xff00ff)
				+ ((j & 0xff00) * k1 >> 8 & 0xff00);
		while (--k >= 0) {
			ai[i] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff)
					+ ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			i++;
			ai[i] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff)
					+ ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			i++;
			ai[i] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff)
					+ ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			i++;
			ai[i] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff)
					+ ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			i++;
		}
		for (k = i1 - l & 3; --k >= 0;)
			ai[i] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff)
					+ ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
		i++;

	}

	public static void method378(int i, int j, int k, int l, int i1, int j1,
			int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3,
			int k3, int l3, int i4, int j4, int k4) {
		drawLDTexturedTriangle(i, j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4);
	}
	public static void drawLDTexturedTriangle(int i, int j, int k, int l, int i1, int j1, int k1, int l1,
											  int i2, int j2, int k2, int l2, int i3, int j3, int k3,
											  int l3, int i4, int j4, int k4)
	{
		int ai[] = method371(k4);
		aBoolean1463 = !transparent[k4];
		k2 = j2 - k2;
		j3 = i3 - j3;
		i4 = l3 - i4;
		l2 -= j2;
		k3 -= i3;
		j4 -= l3;
		int l4 = l2 * i3 - k3 * j2 << (Client.log_view_dist == 9 ? 14 : 15);
		int i5 = k3 * l3 - j4 * i3 << 8;
		int j5 = j4 * j2 - l2 * l3 << 5;
		int k5 = k2 * i3 - j3 * j2<< (Client.log_view_dist == 9 ? 14 : 15);
		int l5 = j3 * l3 - i4 * i3 << 8;
		int i6 = i4 * j2 - k2 * l3 << 5;
		int j6 = j3 * l2 - k2 * k3 << (Client.log_view_dist == 9 ? 14 : 15);
		int k6 = i4 * k3 - j3 * j4 << 8;
		int l6 = k2 * j4 - i4 * l2 << 5;
		int i7 = 0;
		int j7 = 0;
		if(j != i)
		{
			i7 = (i1 - l << 16) / (j - i);
			j7 = (l1 - k1 << 16) / (j - i);
		}
		int k7 = 0;
		int l7 = 0;
		if(k != j)
		{
			k7 = (j1 - i1 << 16) / (k - j);
			l7 = (i2 - l1 << 16) / (k - j);
		}
		int i8 = 0;
		int j8 = 0;
		if(k != i)
		{
			i8 = (l - j1 << 16) / (i - k);
			j8 = (k1 - i2 << 16) / (i - k);
		}
		if(i <= j && i <= k)
		{
			if(i >= DrawingArea.bottomY)
				return;
			if(j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(j < k)
			{
				j1 = l <<= 16;
				i2 = k1 <<= 16;
				if(i < 0)
				{
					j1 -= i8 * i;
					l -= i7 * i;
					i2 -= j8 * i;
					k1 -= j7 * i;
					i = 0;
				}
				i1 <<= 16;
				l1 <<= 16;
				if(j < 0)
				{
					i1 -= k7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				int k8 = i - originY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if(i != j && i8 < i7 || i == j && i8 > k7)
				{
					k -= j;
					j -= i;
					i = rowOffsets[i];
					while(--j >= 0)
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						l += i7;
						i2 += j8;
						k1 += j7;
						i += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--k >= 0)
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, i, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						i1 += k7;
						i2 += j8;
						l1 += l7;
						i += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				k -= j;
				j -= i;
				i = rowOffsets[i];
				while(--j >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					l += i7;
					i2 += j8;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--k >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					i1 += k7;
					i2 += j8;
					l1 += l7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			i1 = l <<= 16;
			l1 = k1 <<= 16;
			if(i < 0)
			{
				i1 -= i8 * i;
				l -= i7 * i;
				l1 -= j8 * i;
				k1 -= j7 * i;
				i = 0;
			}
			j1 <<= 16;
			i2 <<= 16;
			if(k < 0)
			{
				j1 -= k7 * k;
				i2 -= l7 * k;
				k = 0;
			}
			int l8 = i - originY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if(i != k && i8 < i7 || i == k && k7 > i7)
			{
				j -= k;
				k -= i;
				i = rowOffsets[i];
				while(--k >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += i8;
					l += i7;
					l1 += j8;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--j >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += k7;
					l += i7;
					i2 += l7;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= k;
			k -= i;
			i = rowOffsets[i];
			while(--k >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, i, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i8;
				l += i7;
				l1 += j8;
				k1 += j7;
				i += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--j >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += k7;
				l += i7;
				i2 += l7;
				k1 += j7;
				i += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(j <= k)
		{
			if(j >= DrawingArea.bottomY)
				return;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if(k < i)
			{
				l = i1 <<= 16;
				k1 = l1 <<= 16;
				if(j < 0)
				{
					l -= i7 * j;
					i1 -= k7 * j;
					k1 -= j7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				j1 <<= 16;
				i2 <<= 16;
				if(k < 0)
				{
					j1 -= i8 * k;
					i2 -= j8 * k;
					k = 0;
				}
				int i9 = j - originY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if(j != k && i7 < k7 || j == k && i7 > i8)
				{
					i -= k;
					k -= j;
					j = rowOffsets[j];
					while(--k >= 0)
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						i1 += k7;
						k1 += j7;
						l1 += l7;
						j += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--i >= 0)
					{
						drawLDTexturedScanline(DrawingArea.pixels, ai, j, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						j1 += i8;
						k1 += j7;
						i2 += j8;
						j += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				i -= k;
				k -= j;
				j = rowOffsets[j];
				while(--k >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					i1 += k7;
					k1 += j7;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--i >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					j1 += i8;
					k1 += j7;
					i2 += j8;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j1 = i1 <<= 16;
			i2 = l1 <<= 16;
			if(j < 0)
			{
				j1 -= i7 * j;
				i1 -= k7 * j;
				i2 -= j7 * j;
				l1 -= l7 * j;
				j = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if(i < 0)
			{
				l -= i8 * i;
				k1 -= j8 * i;
				i = 0;
			}
			int j9 = j - originY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if(i7 < k7)
			{
				k -= i;
				i -= j;
				j = rowOffsets[j];
				while(--i >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i7;
					i1 += k7;
					i2 += j7;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--k >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i8;
					i1 += k7;
					k1 += j8;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			k -= i;
			i -= j;
			j = rowOffsets[j];
			while(--i >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, j, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += i7;
				i1 += k7;
				i2 += j7;
				l1 += l7;
				j += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--k >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
				l += i8;
				i1 += k7;
				k1 += j8;
				l1 += l7;
				j += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(k >= DrawingArea.bottomY)
			return;
		if(i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if(j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if(i < j)
		{
			i1 = j1 <<= 16;
			l1 = i2 <<= 16;
			if(k < 0)
			{
				i1 -= k7 * k;
				j1 -= i8 * k;
				l1 -= l7 * k;
				i2 -= j8 * k;
				k = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if(i < 0)
			{
				l -= i7 * i;
				k1 -= j7 * i;
				i = 0;
			}
			int k9 = k - originY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if(k7 < i8)
			{
				j -= i;
				i -= k;
				k = rowOffsets[k];
				while(--i >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					j1 += i8;
					l1 += l7;
					i2 += j8;
					k += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--j >= 0)
				{
					drawLDTexturedScanline(DrawingArea.pixels, ai, k, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					l += i7;
					l1 += l7;
					k1 += j7;
					k += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= i;
			i -= k;
			k = rowOffsets[k];
			while(--i >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				j1 += i8;
				l1 += l7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--j >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				l += i7;
				l1 += l7;
				k1 += j7;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		l = j1 <<= 16;
		k1 = i2 <<= 16;
		if(k < 0)
		{
			l -= k7 * k;
			j1 -= i8 * k;
			k1 -= l7 * k;
			i2 -= j8 * k;
			k = 0;
		}
		i1 <<= 16;
		l1 <<= 16;
		if(j < 0)
		{
			i1 -= i7 * j;
			l1 -= j7 * j;
			j = 0;
		}
		int l9 = k - originY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if(k7 < i8)
		{
			i -= j;
			j -= k;
			k = rowOffsets[k];
			while(--j >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				l += k7;
				j1 += i8;
				k1 += l7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--i >= 0)
			{
				drawLDTexturedScanline(DrawingArea.pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i7;
				j1 += i8;
				l1 += j7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		i -= j;
		j -= k;
		k = rowOffsets[k];
		while(--j >= 0)
		{
			drawLDTexturedScanline(DrawingArea.pixels, ai, k, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
			l += k7;
			j1 += i8;
			k1 += l7;
			i2 += j8;
			k += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while(--i >= 0)
		{
			drawLDTexturedScanline(DrawingArea.pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
			i1 += i7;
			j1 += i8;
			l1 += j7;
			i2 += j8;
			k += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawLDTexturedScanline(int ai[], int ai1[], int k, int l, int i1,
											   int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3) {
		int i = 0;// was parameter
		int j = 0;// was parameter
		if (l >= i1)
			return;
		int j3;
		int k3;
		if (clippedHorizontally) {
			j3 = (k1 - j1) / (i1 - l);
			if (i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if (l < 0) {
				j1 -= l * j3;
				l = 0;
			}
			if (l >= i1)
				return;
			k3 = i1 - l >> 3;
			j3 <<= 12;
			j1 <<= 9;
		} else {
			if (i1 - l > 7) {
				k3 = i1 - l >> 3;
				j3 = (k1 - j1) * anIntArray1468[k3] >> 6;
			} else {
				k3 = 0;
				j3 = 0;
			}
			j1 <<= 9;
		}
		k += l;
		if (lowMem) {
			int i4 = 0;
			int k4 = 0;
			int k6 = l - originX;
			l1 += (k2 >> 3) * k6;
			i2 += (l2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if (i5 != 0) {
				i = l1 / i5;
				j = i2 / i5;
				if (i < 0)
					i = 0;
				else if (i > 4032)
					i = 4032;
			}
			l1 += k2;
			i2 += l2;
			j2 += i3;
			i5 = j2 >> 12;
			if (i5 != 0) {
				i4 = l1 / i5;
				k4 = i2 / i5;
				if (i4 < 7)
					i4 = 7;
				else if (i4 > 4032)
					i4 = 4032;
			}
			int i7 = i4 - i >> 3;
			int k7 = k4 - j >> 3;
			i += (j1 & 0x600000) >> 3;
			int i8 = j1 >> 23;
			if (aBoolean1463) {
				while (k3-- > 0) {
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i = i4;
					j = k4;
					l1 += k2;
					i2 += l2;
					j2 += i3;
					int j5 = j2 >> 12;
					if (j5 != 0) {
						i4 = l1 / j5;
						k4 = i2 / j5;
						if (i4 < 7)
							i4 = 7;
						else if (i4 > 4032)
							i4 = 4032;
					}
					i7 = i4 - i >> 3;
					k7 = k4 - j >> 3;
					j1 += j3;
					i += (j1 & 0x600000) >> 3;
					i8 = j1 >> 23;
				}
				for (k3 = i1 - l & 7; k3-- > 0;) {
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
				}

				return;
			}
			while (k3-- > 0) {
				int k8;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if ((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i = i4;
				j = k4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int k5 = j2 >> 12;
				if (k5 != 0) {
					i4 = l1 / k5;
					k4 = i2 / k5;
					if (i4 < 7)
						i4 = 7;
					else if (i4 > 4032)
						i4 = 4032;
				}
				i7 = i4 - i >> 3;
				k7 = k4 - j >> 3;
				j1 += j3;
				i += (j1 & 0x600000) >> 3;
				i8 = j1 >> 23;
			}
			for (k3 = i1 - l & 7; k3-- > 0;) {
				int l8;
				if ((l8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = l8;
				k++;
				i += i7;
				j += k7;
			}

			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = l - originX;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if (l5 != 0) {
			i = l1 / l5;
			j = i2 / l5;
			if (i < 0)
				i = 0;
			else if (i > 16256)
				i = 16256;
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if (l5 != 0) {
			j4 = l1 / l5;
			l4 = i2 / l5;
			if (j4 < 7)
				j4 = 7;
			else if (j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		i += j1 & 0x600000;
		int j8 = j1 >> 23;
		if (aBoolean1463) {
			while (k3-- > 0) {
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i = j4;
				j = l4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int i6 = j2 >> 14;
				if (i6 != 0) {
					j4 = l1 / i6;
					l4 = i2 / i6;
					if (j4 < 7)
						j4 = 7;
					else if (j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
				j1 += j3;
				i += j1 & 0x600000;
				j8 = j1 >> 23;
			}
			for (k3 = i1 - l & 7; k3-- > 0;) {
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
			}

			return;
		}
		while (k3-- > 0) {
			int i9;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if ((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i = j4;
			j = l4;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int j6 = j2 >> 14;
			if (j6 != 0) {
				j4 = l1 / j6;
				l4 = i2 / j6;
				if (j4 < 7)
					j4 = 7;
				else if (j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
			j1 += j3;
			i += j1 & 0x600000;
			j8 = j1 >> 23;
		}
		for (int l3 = i1 - l & 7; l3-- > 0;) {
			int j9;
			if ((j9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = j9;
			k++;
			i += j7;
			j += l7;
		}

	}
	public static void drawHDTexturedTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int l1, int l2,
											  int l3, int tx1, int tx2, int tx3, int ty1, int ty2, int ty3,
											  int tz1, int tz2, int tz3, int tex)
	{
		l1 = 0x7f - l1 << 1;
		l2 = 0x7f - l2 << 1;
		l3 = 0x7f - l3 << 1;
		int ai[] = method371(tex);
		aBoolean1463 = !transparent[tex];
		tx2 = tx1 - tx2;
		ty2 = ty1 - ty2;
		tz2 = tz1 - tz2;
		tx3 -= tx1;
		ty3 -= ty1;
		tz3 -= tz1;
		int l4 = tx3 * ty1 - ty3 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
		int i5 = ty3 * tz1 - tz3 * ty1 << 8;
		int j5 = tz3 * tx1 - tx3 * tz1 << 5;
		int k5 = tx2 * ty1 - ty2 * tx1 << (Client.log_view_dist == 9 ? 14 : 15);
		int l5 = ty2 * tz1 - tz2 * ty1 << 8;
		int i6 = tz2 * tx1 - tx2 * tz1 << 5;
		int j6 = ty2 * tx3 - tx2 * ty3 << (Client.log_view_dist == 9 ? 14 : 15);
		int k6 = tz2 * ty3 - ty2 * tz3 << 8;
		int l6 = tx2 * tz3 - tz2 * tx3 << 5;
		int i7 = 0;
		int j7 = 0;
		if(y2 != y1)
		{
			i7 = (x2 - x1 << 16) / (y2 - y1);
			j7 = (l2 - l1 << 16) / (y2 - y1);
		}
		int k7 = 0;
		int l7 = 0;
		if(y3 != y2)
		{
			k7 = (x3 - x2 << 16) / (y3 - y2);
			l7 = (l3 - l2 << 16) / (y3 - y2);
		}
		int i8 = 0;
		int j8 = 0;
		if(y3 != y1)
		{
			i8 = (x1 - x3 << 16) / (y1 - y3);
			j8 = (l1 - l3 << 16) / (y1 - y3);
		}
		if(y1 <= y2 && y1 <= y3)
		{
			if(y1 >= DrawingArea.bottomY)
				return;
			if(y2 > DrawingArea.bottomY)
				y2 = DrawingArea.bottomY;
			if(y3 > DrawingArea.bottomY)
				y3 = DrawingArea.bottomY;
			if(y2 < y3)
			{
				x3 = x1 <<= 16;
				l3 = l1 <<= 16;
				if(y1 < 0)
				{
					x3 -= i8 * y1;
					x1 -= i7 * y1;
					l3 -= j8 * y1;
					l1 -= j7 * y1;
					y1 = 0;
				}
				x2 <<= 16;
				l2 <<= 16;
				if(y2 < 0)
				{
					x2 -= k7 * y2;
					l2 -= l7 * y2;
					y2 = 0;
				}
				int k8 = y1 - originY;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if(y1 != y2 && i8 < i7 || y1 == y2 && i8 > k7)
				{
					y3 -= y2;
					y2 -= y1;
					y1 = rowOffsets[y1];
					while(--y2 >= 0)
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x1 += i7;
						l3 += j8;
						l1 += j7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--y3 >= 0)
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
						x3 += i8;
						x2 += k7;
						l3 += j8;
						l2 += l7;
						y1 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y3 -= y2;
				y2 -= y1;
				y1 = rowOffsets[y1];
				while(--y2 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x1 += i7;
					l3 += j8;
					l1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y3 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
					x3 += i8;
					x2 += k7;
					l3 += j8;
					l2 += l7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x2 = x1 <<= 16;
			l2 = l1 <<= 16;
			if(y1 < 0)
			{
				x2 -= i8 * y1;
				x1 -= i7 * y1;
				l2 -= j8 * y1;
				l1 -= j7 * y1;
				y1 = 0;
			}
			x3 <<= 16;
			l3 <<= 16;
			if(y3 < 0)
			{
				x3 -= k7 * y3;
				l3 -= l7 * y3;
				y3 = 0;
			}
			int l8 = y1 - originY;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if(y1 != y3 && i8 < i7 || y1 == y3 && k7 > i7)
			{
				y2 -= y3;
				y3 -= y1;
				y1 = rowOffsets[y1];
				while(--y3 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
					x2 += i8;
					x1 += i7;
					l2 += j8;
					l1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y2 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
					x3 += k7;
					x1 += i7;
					l3 += l7;
					l1 += j7;
					y1 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y3;
			y3 -= y1;
			y1 = rowOffsets[y1];
			while(--y3 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
				x2 += i8;
				x1 += i7;
				l2 += j8;
				l1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y2 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y1, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
				x3 += k7;
				x1 += i7;
				l3 += l7;
				l1 += j7;
				y1 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(y2 <= y3)
		{
			if(y2 >= DrawingArea.bottomY)
				return;
			if(y3 > DrawingArea.bottomY)
				y3 = DrawingArea.bottomY;
			if(y1 > DrawingArea.bottomY)
				y1 = DrawingArea.bottomY;
			if(y3 < y1)
			{
				x1 = x2 <<= 16;
				l1 = l2 <<= 16;
				if(y2 < 0)
				{
					x1 -= i7 * y2;
					x2 -= k7 * y2;
					l1 -= j7 * y2;
					l2 -= l7 * y2;
					y2 = 0;
				}
				x3 <<= 16;
				l3 <<= 16;
				if(y3 < 0)
				{
					x3 -= i8 * y3;
					l3 -= j8 * y3;
					y3 = 0;
				}
				int i9 = y2 - originY;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if(y2 != y3 && i7 < k7 || y2 == y3 && i7 > i8)
				{
					y1 -= y3;
					y3 -= y2;
					y2 = rowOffsets[y2];
					while(--y3 >= 0)
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x2 += k7;
						l1 += j7;
						l2 += l7;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--y1 >= 0)
					{
						drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
						x1 += i7;
						x3 += i8;
						l1 += j7;
						l3 += j8;
						y2 += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				y1 -= y3;
				y3 -= y2;
				y2 = rowOffsets[y2];
				while(--y3 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x2 += k7;
					l1 += j7;
					l2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y1 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
					x1 += i7;
					x3 += i8;
					l1 += j7;
					l3 += j8;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			x3 = x2 <<= 16;
			l3 = l2 <<= 16;
			if(y2 < 0)
			{
				x3 -= i7 * y2;
				x2 -= k7 * y2;
				l3 -= j7 * y2;
				l2 -= l7 * y2;
				y2 = 0;
			}
			x1 <<= 16;
			l1 <<= 16;
			if(y1 < 0)
			{
				x1 -= i8 * y1;
				l1 -= j8 * y1;
				y1 = 0;
			}
			int j9 = y2 - originY;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if(i7 < k7)
			{
				y3 -= y1;
				y1 -= y2;
				y2 = rowOffsets[y2];
				while(--y1 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
					x3 += i7;
					x2 += k7;
					l3 += j7;
					l2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y3 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
					x1 += i8;
					x2 += k7;
					l1 += j8;
					l2 += l7;
					y2 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y3 -= y1;
			y1 -= y2;
			y2 = rowOffsets[y2];
			while(--y1 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
				x3 += i7;
				x2 += k7;
				l3 += j7;
				l2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y3 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y2, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
				x1 += i8;
				x2 += k7;
				l1 += j8;
				l2 += l7;
				y2 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(y3 >= DrawingArea.bottomY)
			return;
		if(y1 > DrawingArea.bottomY)
			y1 = DrawingArea.bottomY;
		if(y2 > DrawingArea.bottomY)
			y2 = DrawingArea.bottomY;
		if(y1 < y2)
		{
			x2 = x3 <<= 16;
			l2 = l3 <<= 16;
			if(y3 < 0)
			{
				x2 -= k7 * y3;
				x3 -= i8 * y3;
				l2 -= l7 * y3;
				l3 -= j8 * y3;
				y3 = 0;
			}
			x1 <<= 16;
			l1 <<= 16;
			if(y1 < 0)
			{
				x1 -= i7 * y1;
				l1 -= j7 * y1;
				y1 = 0;
			}
			int k9 = y3 - originY;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if(k7 < i8)
			{
				y2 -= y1;
				y1 -= y3;
				y3 = rowOffsets[y3];
				while(--y1 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x3 += i8;
					l2 += l7;
					l3 += j8;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--y2 >= 0)
				{
					drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x1 >> 16, l2, l1, l4, k5, j6, i5, l5, k6);
					x2 += k7;
					x1 += i7;
					l2 += l7;
					l1 += j7;
					y3 += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			y2 -= y1;
			y1 -= y3;
			y3 = rowOffsets[y3];
			while(--y1 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x3 += i8;
				l2 += l7;
				l3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y2 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x2 >> 16, l1, l2, l4, k5, j6, i5, l5, k6);
				x2 += k7;
				x1 += i7;
				l2 += l7;
				l1 += j7;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		x1 = x3 <<= 16;
		l1 = l3 <<= 16;
		if(y3 < 0)
		{
			x1 -= k7 * y3;
			x3 -= i8 * y3;
			l1 -= l7 * y3;
			l3 -= j8 * y3;
			y3 = 0;
		}
		x2 <<= 16;
		l2 <<= 16;
		if(y2 < 0)
		{
			x2 -= i7 * y2;
			l2 -= j7 * y2;
			y2 = 0;
		}
		int l9 = y3 - originY;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if(k7 < i8)
		{
			y1 -= y2;
			y2 -= y3;
			y3 = rowOffsets[y3];
			while(--y2 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x1 >> 16, x3 >> 16, l1, l3, l4, k5, j6, i5, l5, k6);
				x1 += k7;
				x3 += i8;
				l1 += l7;
				l3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--y1 >= 0)
			{
				drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x2 >> 16, x3 >> 16, l2, l3, l4, k5, j6, i5, l5, k6);
				x2 += i7;
				x3 += i8;
				l2 += j7;
				l3 += j8;
				y3 += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		y1 -= y2;
		y2 -= y3;
		y3 = rowOffsets[y3];
		while(--y2 >= 0)
		{
			drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x1 >> 16, l3, l1, l4, k5, j6, i5, l5, k6);
			x1 += k7;
			x3 += i8;
			l1 += l7;
			l3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while(--y1 >= 0)
		{
			drawHDTexturedScanline(DrawingArea.pixels, ai, y3, x3 >> 16, x2 >> 16, l3, l2, l4, k5, j6, i5, l5, k6);
			x2 += i7;
			x3 += i8;
			l2 += j7;
			l3 += j8;
			y3 += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}
	private static void drawHDTexturedScanline(int ai[], int ai1[], int k, int x1, int x2, int l1,
											   int l2, int a1, int i2, int j2, int k2, int a2, int i3)
	{
		int i = 0;//was parameter
		int j = 0;//was parameter
		if(x1 >= x2)
			return;
		int dl = (l2 - l1) / (x2 - x1);
		int n;
		if(clippedHorizontally)
		{
			if(x2 > DrawingArea.centerX)
				x2 = DrawingArea.centerX;
			if(x1 < 0)
			{
				l1 -= x1 * dl;
				x1 = 0;
			}
		}
		if(x1 >= x2)
			return;
		n = x2 - x1 >> 3;
		k += x1;
		if(lowMem)
		{
			int i4 = 0;
			int k4 = 0;
			int k6 = x1 - originX;
			a1 += (k2 >> 3) * k6;
			i2 += (a2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if(i5 != 0)
			{
				i = a1 / i5;
				j = i2 / i5;
				if(i < 0)
					i = 0;
				else
				if(i > 4032)
					i = 4032;
			}
			a1 += k2;
			i2 += a2;
			j2 += i3;
			i5 = j2 >> 12;
			if(i5 != 0)
			{
				i4 = a1 / i5;
				k4 = i2 / i5;
				if(i4 < 7)
					i4 = 7;
				else
				if(i4 > 4032)
					i4 = 4032;
			}
			int i7 = i4 - i >> 3;
			int k7 = k4 - j >> 3;
			if(aBoolean1463)
			{
				int rgb;
				int l;
				while(n-- > 0)
				{
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
					a1 += k2;
					i2 += a2;
					j2 += i3;
					int j5 = j2 >> 12;
					if(j5 != 0)
					{
						i4 = a1 / j5;
						k4 = i2 / j5;
						if(i4 < 7)
							i4 = 7;
						else
						if(i4 > 4032)
							i4 = 4032;
					}
					i7 = i4 - i >> 3;
					k7 = k4 - j >> 3;
					l1 += dl;
				}
				for(n = x2 - x1 & 7; n-- > 0;)
				{
					rgb = ai1[(j & 0xfc0) + (i >> 6)];
					l = l1 >> 16;
					ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
					i += i7;
					j += k7;
					l1 += dl;
				}
				return;
			}
			while(n-- > 0)
			{
				int k8;
				int l;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((k8 & 0xff00ff) * l & ~0xff00ff) + ((k8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
				a1 += k2;
				i2 += a2;
				j2 += i3;
				int k5 = j2 >> 12;
				if(k5 != 0)
				{
					i4 = a1 / k5;
					k4 = i2 / k5;
					if(i4 < 7)
						i4 = 7;
					else
					if(i4 > 4032)
						i4 = 4032;
				}
				i7 = i4 - i >> 3;
				k7 = k4 - j >> 3;
				l1 += dl;
			}
			for(n = x2 - x1 & 7; n-- > 0;)
			{
				int l8;
				int l;
				if((l8 = ai1[(j & 0xfc0) + (i >> 6)]) != 0) {
					l = l1 >> 16;
					ai[k] = ((l8 & 0xff00ff) * l & ~0xff00ff) + ((l8 & 0xff00) * l & 0xff0000) >> 8;
				}
				k++;
				i += i7;
				j += k7;
				l1 += dl;
			}

			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = x1 - originX;
		a1 += (k2 >> 3) * l6;
		i2 += (a2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if(l5 != 0)
		{
			i = a1 / l5;
			j = i2 / l5;
			if(i < 0)
				i = 0;
			else
			if(i > 16256)
				i = 16256;
		}
		a1 += k2;
		i2 += a2;
		j2 += i3;
		l5 = j2 >> 14;
		if(l5 != 0)
		{
			j4 = a1 / l5;
			l4 = i2 / l5;
			if(j4 < 7)
				j4 = 7;
			else
			if(j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		if(aBoolean1463)
		{
			while(n-- > 0)
			{
				int rgb;
				int l;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
				a1 += k2;
				i2 += a2;
				j2 += i3;
				int i6 = j2 >> 14;
				if(i6 != 0)
				{
					j4 = a1 / i6;
					l4 = i2 / i6;
					if(j4 < 7)
						j4 = 7;
					else
					if(j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
				l1 += dl;
			}
			for(n = x2 - x1 & 7; n-- > 0;)
			{
				int rgb;
				int l;
				rgb = ai1[(j & 0x3f80) + (i >> 7)];
				l = l1 >> 16;
				ai[k++] = ((rgb & 0xff00ff) * l & ~0xff00ff) + ((rgb & 0xff00) * l & 0xff0000) >> 8;
				i += j7;
				j += l7;
				l1 += dl;
			}

			return;
		}
		while(n-- > 0)
		{
			int i9;
			int l;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((i9 & 0xff00ff) * l & ~0xff00ff) + ((i9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
			a1 += k2;
			i2 += a2;
			j2 += i3;
			int j6 = j2 >> 14;
			if(j6 != 0)
			{
				j4 = a1 / j6;
				l4 = i2 / j6;
				if(j4 < 7)
					j4 = 7;
				else
				if(j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
			l1 += dl;
		}
		for(int l3 = x2 - x1 & 7; l3-- > 0;)
		{
			int j9;
			int l;
			if((j9 = ai1[(j & 0x3f80) + (i >> 7)]) != 0) {
				l = l1 >> 16;
				ai[k] = ((j9 & 0xff00ff) * l & ~0xff00ff) + ((j9 & 0xff00) * l & 0xff0000) >> 8;;
			}
			k++;
			i += j7;
			j += l7;
			l1 += dl;
		}

	}
	public static int textureAmount = 51;

	public static boolean lowMem = true;
	static boolean clippedHorizontally;
	private static boolean aBoolean1463;
	public static boolean aBoolean1464 = true;
	public static int anInt1465;
	public static int originX;
	public static int originY;

	private static int[] anIntArray1468;
	public static final int[] anIntArray1469;
	public static int SINE[];
	public static int COSINE[];
	public static int rowOffsets[];
	private static int loadedTextureCount;
	public static Background textures[] = new Background[textureAmount];
	private static boolean[] transparent = new boolean[textureAmount];
	private static int[] anIntArray1476 = new int[textureAmount];
	private static int texelPoolPointer;
	private static int[][] texelArrayPool;
	private static int[][] texelCache = new int[textureAmount][];
	public static int textureLastUsed[] = new int[textureAmount];
	public static int textureGetCount;
	public static int HSLtoRGB[] = new int[0x10000];
	private static int[][] texturePalettes = new int[textureAmount][];

	static {
		anIntArray1468 = new int[512];
		anIntArray1469 = new int[2048];
		SINE = new int[2048];
		COSINE = new int[2048];
		for (int i = 1; i < 512; i++)
			anIntArray1468[i] = 32768 / i;

		for (int j = 1; j < 2048; j++)
			anIntArray1469[j] = 0x10000 / j;

		for (int k = 0; k < 2048; k++) {
			SINE[k] = (int) (65536D * Math
					.sin((double) k * 0.0030679614999999999D));
			COSINE[k] = (int) (65536D * Math
					.cos((double) k * 0.0030679614999999999D));
		}

	}
}
package com.proxy;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CacheFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel label;
	
	private File dlLocation;
	
	public CacheFrame(String locationPath, String[] args) {
		if (label == null) {
			label = new JLabel("Retrieving cache size...");
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setPreferredSize(new Dimension(400, 50));
			label.setForeground(Color.WHITE);
		}
		add(label);
		setPreferredSize(new Dimension(400, 60));
		setBackground(Color.BLACK);
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				redirectory(locationPath);
				download("https://ortagel.com/downloads/mtrx_cache.zip");	
				unzipCache();
				finishAndClean(locationPath, args);
			}
		});
		t.start();
	}
	
	private void finishAndClean(String locationPath, String[] args) {
		File confirmFile = new File(locationPath + File.separator
				+ "doneDownload");
		try {
		confirmFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dlLocation.delete();
		Client.runGame(args);
		CacheDownloaderMTRX.closeFrame();
	}
	
	private void redirectory(String locationPath) {
		File f = new File(locationPath);
		dlLocation = new File(f.getParent().toString() + "/TempCache5944.zip");
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public void download(String downloadPath) {
		BufferedInputStream in = null;
		FileOutputStream out = null;
		
		try {
			URL url = new URL(downloadPath);
			URLConnection conn = url.openConnection();
			int size = conn.getContentLength();
			
			if (size < 0) {
				System.out.println("Check your connection or contact us");
				label.setText("Check your connection or contact us");
			} else {
				System.out.println("File size: " + size);
				double mb = (size);
				double fileSize = mb / 1048576;
				label.setText("File size: " + round(fileSize, 2) + "MB");
				Thread.sleep(2000);
			}
			in = new BufferedInputStream(url.openStream());
			out = new FileOutputStream(dlLocation);
			byte data[] = new byte[1024];
			int count;
			double sumCount = 0.0;
			
			while ((count = in.read(data, 0, 1024)) != -1) {
				out.write(data, 0, count);
				
				sumCount += count;
				int progress = (int) (sumCount / size * 100.0);
				if (size > 0) {
					label.setText("Downloading cache: " + progress + "%");
				}
			}
			label.setText("Cache has been downloaded!");
			in.close();
			out.close();
			Thread.sleep(2000);
		} catch (Exception e) {
			label.setText("Check your connection or contact us");
			e.printStackTrace();
		}
	}
	
	private void unzipCache() {
		byte[] buffer = new byte[1024];
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(dlLocation));
			ZipEntry ze = zis.getNextEntry();
			
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(dlLocation.getParent() + File.separator
						+ "web3scapecache" + File.separator + fileName);
				System.out.println("File Unzipping: " + newFile);
				System.out.println("error: " + newFile.getParent());
				new File(newFile.getParent()).mkdirs();
				
				FileOutputStream zos = new FileOutputStream(newFile);
				
				int len;
				while ((len = zis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
					label.setText("Extracted: " + fileName + "...");
				}
				zos.close();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			System.out.println("Done with extracting!");
			label.setText("Done with extracting!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
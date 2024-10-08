package com.proxy;

import com.proxy.sign.signlink;

import javax.swing.*;
import java.io.File;

public class CacheDownloaderMTRX {

    private static JFrame frame;
    private File cache = new File(signlink.findcachedir());
    private File dlVerification = new File(signlink.findcachedir() + "doneDownload");

    public CacheDownloaderMTRX(String[] args) {
        if (dlRequired()) {
            downloadCache(args);
        } else {
            Client.runGame(args);
        }
    }

    public static void closeFrame() {
        frame.dispose();
    }

    public boolean dlRequired() {
        if (cache.exists() && dlVerification.exists() && !dlVerification.isDirectory()) {
            return false;
        } else {
            return true;
        }
    }

    private void downloadCache(String[] args) {
        removeOtherCache();
        createFrame(args);
    }

    /**
     * Removes previous caches
     */
    private void removeOtherCache() {
        if (cache.exists()) {
            String[] files = cache.list();
            for (String s : files) {
                File currentFile = new File(cache.getPath(), s);
                currentFile.delete();
                System.out.println("Delted file: " + currentFile.getPath());
            }
        }
        cache.delete();
    }

    private void createFrame(String[] args) {
        frame = new JFrame("Game Initializer");
        frame.add(new CacheFrame(cache.getPath(), args));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
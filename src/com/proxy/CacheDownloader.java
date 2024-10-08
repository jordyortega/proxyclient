package com.proxy;

import com.proxy.sign.signlink;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CacheDownloader {

    private final int BUFFER = 1024; //1024
    private final int VERSION = 1;
    private Client client;
    private String cacheLink = "https://web3scape.io/game/downloads/w3scache.zip";
    private String fileToExtract = getCacheDir() + getArchivedName();

    public CacheDownloader(Client client) {
        this.client = client;
    }

    private void drawLoadingText(String text) {
        client.drawLoadingText(35, text);
    }

    private void drawLoadingText(int amount, String text) {
        client.drawLoadingText(amount, text);
    }

    private String getCacheDir() {
        return signlink.findcachedir();
    }

    private String getCacheLink() {
        return cacheLink;
    }

    private int getCacheVersion() {
        return VERSION;
    }

    public CacheDownloader downloadCache() {
        try {
            File location = new File(getCacheDir());
            File version = new File(getCacheDir() + "/cacheVersion" + getCacheVersion() + ".dat");
            if (!location.exists()) {
                downloadFile(getCacheLink(), getArchivedName());
                unZip();
                BufferedWriter versionFile = new BufferedWriter(new FileWriter(getCacheDir() + "/cacheVersion" + getCacheVersion() + ".dat"));
                versionFile.close();
                deleteZIP(getArchivedName());
            } else {
                if (!version.exists()) {
                    downloadFile(getCacheLink(), getArchivedName());
                    unZip();
                    BufferedWriter versionFile = new BufferedWriter(new FileWriter(getCacheDir() + "/cacheVersion" + getCacheVersion() + ".dat"));
                    versionFile.close();
                    deleteZIP(getArchivedName());
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    private void downloadFile(String adress, String localFileName) {
        OutputStream out = null;
        URLConnection conn;
        InputStream in = null;
        try {
            URL url = new URL(adress);
            out = new BufferedOutputStream(new FileOutputStream(getCacheDir() + "/" + localFileName));
            conn = url.openConnection();
            in = conn.getInputStream();
            byte[] data = new byte[BUFFER];
            int numRead;
            long numWritten = 0;
            int length = conn.getContentLength();
            while ((numRead = in.read(data)) != -1) {
                out.write(data, 0, numRead);
                numWritten += numRead;
                int percentage = (int) (((double) numWritten / (double) length) * 100D);
                drawLoadingText(percentage, "Downloading Cache " + percentage + "%");
            }
            drawLoadingText("Updates are now complete. Please wait.");
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe) {
            }
        }
    }

    private String getArchivedName() {
        int lastSlashIndex = getCacheLink().lastIndexOf('/');
        if (lastSlashIndex >= 0
                && lastSlashIndex < getCacheLink().length() - 1) {
            // return getCacheLink().substring(lastSlashIndex + 1);
            return getCacheLink().substring(lastSlashIndex + 1, getCacheLink().length() - 4);
        } else {
        }
        return "";
    }

    private void unZip() {

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(fileToExtract));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry e;
            while ((e = zin.getNextEntry()) != null) {
                if (e.isDirectory()) {
                    (new File(getCacheDir() + e.getName())).mkdir();
                } else {
                    if (e.getName().equals(fileToExtract)) {
                        unzip(zin, fileToExtract);
                        break;
                    }
                    unzip(zin, getCacheDir() + e.getName());
                }
            }
            zin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unzip(ZipInputStream zin, String s)
            throws IOException {
        FileOutputStream out = new FileOutputStream(s);
        byte[] b = new byte[BUFFER];
        int len = 0;
        while ((len = zin.read(b)) != -1) {
            out.write(b, 0, len);
        }
        out.close();
    }

    private void deleteZIP(String fileName) {
        // A File object to represent the filename
        File f = new File(getCacheDir() + fileName);

        // Make sure the file or directory exists and isn't write protected
        if (!f.exists())
            throw new IllegalArgumentException(
                    "Delete: no such file or directory: " + fileName);

        if (!f.canWrite())
            throw new IllegalArgumentException("Delete: write protected: "
                    + fileName);

        // If it is a directory, make sure it is empty
        if (f.isDirectory()) {
            String[] files = f.list();
            if (files.length > 0)
                throw new IllegalArgumentException(
                        "Delete: directory not empty: " + fileName);
        }

        // Attempt to delete it
        boolean success = f.delete();

        if (!success)
            throw new IllegalArgumentException("Delete: deletion failed");

    }
}
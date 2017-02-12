package com.ytinrete.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 */

public class ZipTool {

  public static boolean unZip(String fileFullPath, String desRootFolderPath) {

    if (desRootFolderPath == null) {
      desRootFolderPath = fileFullPath.substring(0, fileFullPath.lastIndexOf(File.separator) + 1);
    } else {
      File desF = new File(desRootFolderPath);
      if (!desF.exists() || !desF.isDirectory()) {
        return false;
      }
    }
    InputStream is;
    ZipInputStream zis;
    try {
      is = new FileInputStream(fileFullPath);
      zis = new ZipInputStream(new BufferedInputStream(is));
      ZipEntry ze;
      byte[] buffer = new byte[4096];
      int count;
      String fileName;
      while ((ze = zis.getNextEntry()) != null) {
        fileName = ze.getName();
        if (ze.isDirectory()) {
          File fmd = new File(desRootFolderPath + fileName);
          fmd.mkdirs();
          continue;
        }
        FileOutputStream fout = new FileOutputStream(desRootFolderPath + fileName);
        while ((count = zis.read(buffer)) != -1) {
          fout.write(buffer, 0, count);
        }
        fout.close();
        zis.closeEntry();
        AppLog.d("zip running");
      }

      zis.close();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

}

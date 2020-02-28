package com.spica27.accountbook.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTools {

  public static String saveFile(InputStream inputStream, String filePath) {
    try {
      OutputStream outputStream = new FileOutputStream(new File(filePath), false);
      int len;
      byte[] buffer = new byte[1024];
      while ((len = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, len);
      }
      outputStream.flush();
      outputStream.close();
      return "成功";
    } catch (IOException e) {
      e.printStackTrace();
      return "失败";
    }
  }
}

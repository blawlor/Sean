package ie.lawlor.sean.utils;


import android.os.Environment;

import java.io.File;

public class ContentUtil {
  public static final String PEOPLE_IMAGES_DIR = "people_images";

  public static String getImagePath(String imageName) {
    File extStorageDirectory = Environment.getExternalStorageDirectory();
    return extStorageDirectory + "/" + PEOPLE_IMAGES_DIR + "/" + imageName;
  }

  public static File getImagesDir() {
    return getSDCardDir(ContentUtil.PEOPLE_IMAGES_DIR);
  }

  private static File getSDCardDir(String dirName) {
    File extStorageDirectory = Environment.getExternalStorageDirectory();
    File directory = new File(extStorageDirectory, dirName);
    if (!directory.exists()) {
      directory.mkdir();
    }
    return directory;
  }

}

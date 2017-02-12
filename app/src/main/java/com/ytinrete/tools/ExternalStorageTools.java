package com.ytinrete.tools;

import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;

import com.ytinrete.contract.YtApp;
import com.ytinrete.contract.YtConstant;

import java.io.File;

/**
 * 统一规范外部存储目录
 * <p/>
 * by lirui
 */
public class ExternalStorageTools {

  /**
   * 获取存储路径 优先返回 外部跟目录/
   *
   * @return 存储路径
   */
  public static String getStorageDir() {
    if (Environment.MEDIA_MOUNTED.equals(nullableGetExternalStorageState())) {
      return getExternalStorageDir();
    } else {
      return getInternalStorageDir();
    }
  }

  /**
   * 获取存储路径 只返回 外部跟目录/
   *
   * @return 存储路径
   */
  public static String getExternalStorageDir() {
    String path = Environment.getExternalStorageDirectory() + File.separator
        + YtConstant.FILE_FOLDER_EXTERNAL_FIRST + File.separator;
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    return path;
  }

  /**
   * 获取存储路径 只返回 Data/Data/
   *
   * @return 存储路径
   */
  public static String getInternalStorageDir() {
    String path = YtApp.getInstance().getAppContext().getFilesDir().getAbsolutePath()
        + File.separator + YtConstant.FILE_FOLDER_EXTERNAL_FIRST + File.separator;
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    return path;
  }

  /**
   * 获取下载目录地址
   *
   * @return 下载目录或，外部跟目录/，当没有外部存储时返回null
   */
  @Nullable
  public static String getDownloadPath() {
    if (Environment.MEDIA_MOUNTED.equals(nullableGetExternalStorageState())) {
      String folderPath;
      File downLoadPath = Environment.getExternalStoragePublicDirectory(Environment
          .DIRECTORY_DOWNLOADS);
      if (!downLoadPath.exists()) {
        folderPath = getExternalStorageDir();
      } else {
        folderPath = downLoadPath.getAbsolutePath() + File.separator;
      }
      return folderPath;
    } else {
      return null;
    }
  }

  public static final int EXTERNAL_STORAGE_STATE_NORMAL = 0;
  public static final int EXTERNAL_STORAGE_STATE_INSUFFICIENT = 1;
  public static final int EXTERNAL_STORAGE_STATE_UNMOUNTED = 2;
  public static final int EXTERNAL_STORAGE_STATE_PREPARING = 3;


  /**
   * 获取外部存储当前状态
   *
   * @return 0 正常 1 空间不足 2 未挂载或未知 3 准备中
   */
  public static int getExternalStorageState() {
    String state = nullableGetExternalStorageState();
    if (Environment.MEDIA_CHECKING.equals(state)) {
      return EXTERNAL_STORAGE_STATE_PREPARING;
    } else if (Environment.MEDIA_MOUNTED.equals(state)) {
      if (calculateExternalStorageSize() < 1) {
        return EXTERNAL_STORAGE_STATE_INSUFFICIENT;
      } else {
        return EXTERNAL_STORAGE_STATE_NORMAL;
      }
    } else {
      return EXTERNAL_STORAGE_STATE_UNMOUNTED;
    }
  }

  public static String nullableGetExternalStorageState() {
    try {
      return Environment.getExternalStorageState();
    } catch (NullPointerException e) {
      return null;
    }
  }

  /**
   * 计算外部存储可用空间大小
   *
   * @return 可用空间大小
   */
  public static double calculateExternalStorageSize() {
    StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
    return (double) stat.getAvailableBlocks() * (double) stat.getBlockSize();
  }


}

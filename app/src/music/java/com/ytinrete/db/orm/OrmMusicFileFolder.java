package com.ytinrete.db.orm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 *
 */

public class OrmMusicFileFolder extends RealmObject {

  private String folderName;
  private String relativePath;
  private String fullPath;
  private RealmList<OrmMusicFileItem> files;

  public String getFolderName() {
    return folderName;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public String getRelativePath() {
    return relativePath;
  }

  public void setRelativePath(String relativePath) {
    this.relativePath = relativePath;
  }

  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  public RealmList<OrmMusicFileItem> getFiles() {
    return files;
  }

  public void setFiles(RealmList<OrmMusicFileItem> files) {
    this.files = files;
  }
}

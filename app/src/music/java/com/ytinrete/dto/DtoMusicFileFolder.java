package com.ytinrete.dto;

import java.util.ArrayList;

/**
 *
 */

public class DtoMusicFileFolder {

  private String folderName;
  private String relativePath;
  private String fullPath;
  private ArrayList<DtoMusicFileItem> files;


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

  public ArrayList<DtoMusicFileItem> getFiles() {
    return files;
  }

  public void setFiles(ArrayList<DtoMusicFileItem> files) {
    this.files = files;
  }
}

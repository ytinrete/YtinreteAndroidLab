package com.ytinrete.db.orm;

import io.realm.RealmObject;

/**
 *
 */

public class OrmMusicFileItem extends RealmObject {

  private String fineName;
  private String relativePath;
  private String fullPath;
  private int score;

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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getFineName() {
    return fineName;
  }

  public void setFineName(String fineName) {
    this.fineName = fineName;
  }
}

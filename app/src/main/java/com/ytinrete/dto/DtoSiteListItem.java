package com.ytinrete.dto;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 */

public class DtoSiteListItem {

  @JsonProperty("Author")
  private String author;

  @JsonProperty("Content")
  private String content;

  @JsonProperty("Time")
  private String time;


  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}

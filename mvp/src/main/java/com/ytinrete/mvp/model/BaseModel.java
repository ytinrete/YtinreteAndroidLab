package com.ytinrete.mvp.model;

/**
 * 这个架构中 model唯一
 *
 * @param <T> 回调
 */

public abstract class BaseModel<T> {

  private T callback;

  public T getCallback() {
    return callback;
  }

  public void setCallback(T callback) {
    this.callback = callback;
  }

//  private HashMap<String, T> idToCallBack = new HashMap<>();
//  private HashMap<T, String> callBackToId = new HashMap<>();
//
//  public void addCallBack(T t) {
//    String id = t.getClass().getSimpleName() + "/" + System.nanoTime() + "/" + (int) (Math.random() * Integer.MAX_VALUE);
//    idToCallBack.put(id, t);
//    callBackToId.put(t, id);
//  }
//
//  public <T> void removeCallBack(T t) {
//    idToCallBack.remove(callBackToId.remove(t));
//  }
//
//  public ArrayList<T> getCallBacks() {
//    return new ArrayList(idToCallBack.values());
//  }

}

package com.ytinrete.task;

/**
 * 基本任务
 */

public abstract class BaseTask<T> implements Runnable {

  /**
   * 当前的任务结果
   */
  protected T result;

  /**
   * 上一次的任务结果
   */
  protected Object lastResult;

  /**
   * 可能存在的下一个需要执行的任务
   */
  protected BaseTask nextTask;

  protected TaskManager manager;

  protected BaseTask(TaskManager tm) {
    manager = tm;
  }

  /**
   * 任务执行前会进行的操作，用于设置好回调等，主线程执行
   */
  protected void doBeforeTask() {

  }

  /**
   * 任务的实际执行方法，注意本方法会在子线程执行
   *
   * @return 执行结果返回
   */
  protected abstract T doTask();

  /**
   * 对于组合任务可能需要包装，发送任务的时候必须把包在最外层的第一个拿来执行
   *
   * @return 第一个应该执行的任务，单任务则为它本身，符合任务则为最外层那个
   */
  protected BaseTask firstTask() {
    return this;
  }

  /**
   * 不允许再修改这个方法，任务执行用doTask方法
   */
  @Override
  final public void run() {
    try {
      setResult(doTask());
    } catch (Exception e) {
      e.printStackTrace();
      TaskManager.l("run task fail");
    }
    if (manager != null) {
      manager.onTaskFinish(this);
    }
  }

  /**
   * 任务完全执行结束之后（复合任务则是最后一个任务完成时的操作）主线程执行
   */
  protected void doAfterTask() {

  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public Object getLastResult() {
    return lastResult;
  }

  public void setLastResult(Object lastResult) {
    this.lastResult = lastResult;
  }

  public BaseTask getNextTask() {
    return nextTask;
  }

  public void setNextTask(BaseTask nextTask) {
    this.nextTask = nextTask;
  }
}

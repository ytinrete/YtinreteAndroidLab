package com.ytinrete.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务管理类
 */

public class TaskManager {

  private static final String LOG = "TaskManager";
  private static boolean DEBUG = false;

  //开始新任务
  private static final int FLAG_TASK_PREPARE = 1001;
  //开始新任务
  private static final int FLAG_TASK_BEGIN = 1002;
  //任务完成
  private static final int FLAG_TASK_FINISH = 1003;

  public static boolean isDEBUG() {
    return DEBUG;
  }

  public static void setDEBUG(boolean DEBUG) {
    TaskManager.DEBUG = DEBUG;
  }

  public TaskManager() {
  }

  public static void l(String msg) {
    if (msg == null) {
      return;
    }
    if (TaskManager.DEBUG) {
      Log.e(LOG, msg);
    }
  }

  //任务处理的线程池
  private final ExecutorService executorService = Executors.newFixedThreadPool(3);


  private void sendTask(BaseTask task) {
    handler.sendMessage(handler.obtainMessage(FLAG_TASK_PREPARE, task));
  }

  //消息队列，接受任务完成的消息
  private final Handler handler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case FLAG_TASK_PREPARE:
          Object taskPrepareObj = msg.obj;
          if (taskPrepareObj != null && taskPrepareObj instanceof BaseTask) {
            BaseTask newTask = (BaseTask) taskPrepareObj;
            //任务前的准备
            newTask.doBeforeTask();
            //拿第一个任务去开始
            handler.sendMessage(handler.obtainMessage(FLAG_TASK_BEGIN, newTask.firstTask()));
          } else {
            l("error on prepare task, task is wrong!");
          }
          break;
        case FLAG_TASK_BEGIN:
          //开始执行任务
          Object taskBeginObj = msg.obj;
          if (taskBeginObj != null && taskBeginObj instanceof BaseTask) {
            BaseTask newTask = (BaseTask) taskBeginObj;
            l("exc task:" + newTask);
            executorService.submit(newTask);
          } else {
            l("error on begin task, task is wrong!");
          }
          break;
        case FLAG_TASK_FINISH:
          //单个任务完成
          Object taskFinishObj = msg.obj;
          if (taskFinishObj != null && taskFinishObj instanceof BaseTask) {
            BaseTask baseTask = (BaseTask) taskFinishObj;
            if (baseTask.getNextTask() != null) {
              //说明这是一个组合任务，还未完成
              BaseTask nextTask = baseTask.getNextTask();
              nextTask.setLastResult(baseTask.getResult());
              handler.sendMessage(handler.obtainMessage(FLAG_TASK_BEGIN, nextTask));
            } else {
              //说明任务真正全部结束了，最终结果在result里面
              baseTask.doAfterTask();
            }
          } else {
            l("error on finish task, task is wrong!");
          }
          break;
        default:
          l("error on handler, unknow msg");
          break;
      }
    }
  };

  /**
   * 任务完成时调用，注意这里还在子线程
   *
   * @param baseTask 完成的任务
   */
  void onTaskFinish(BaseTask baseTask) {
    if (baseTask == null) {
      l("onTaskFinish fatal error, task is null");
      return;
    }
    handler.sendMessage(handler.obtainMessage(FLAG_TASK_FINISH, baseTask));
  }


}

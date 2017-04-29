package com.ytinrete.task;

import android.text.TextUtils;
import android.util.Pair;

import com.ytinrete.dto.DtoSiteListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * productFlag  String 产品标识 yiyuangou 必传
 * userId       String 请求用户标识 URS账号，未登录则不传不加入签名 必传
 * timestamp long 时间戳 1474940455771 必传
 * devicedId String 设备ID F14285A6-889E-4D6F-9205-B764558E8F6C 必传
 * sign String 签名 必传
 * accessIp String 访问IP 10.120.117.123 可选
 * netspeed String 网速、客户端没有则不传 可选
 * deviceType String 访问环境 Android、Iphone、wap、web 可选
 * <p>
 * 获取方案的信息列表任务，本身不做网络请求，只处理网络请求回来的数据或维护，
 * 若需要网络请求，则先包装执行一个网络请求任务
 */

public class QueryUserCaseListTask extends BaseTask<List<DtoSiteListItem>> {

  private String productFlag;
  private String userId;
  private long timestamp;
  private String deviceId;
  private String netSpeed;

  QueryUserCaseListTask(TaskManager tm, String productFlag,
                        String userId, String deviceId, String netSpeed) {
    super(tm);
    this.productFlag = productFlag;
    this.userId = userId;
    this.deviceId = deviceId;
    this.netSpeed = netSpeed;
  }

  @Override
  protected List<DtoSiteListItem> doTask() {
    return null;
  }

//
//  @Override
//  protected BaseTask firstTask() {
//    HttpTask task = new HttpTask(manager);
//    task.setUrl(HttpHelper.BASE_URL + HttpHelper.INTERFACE_queryUserCaseList);
//    List<Pair<String, String>> urlParams = new ArrayList<>();
//    urlParams.add(new Pair<String, String>("timestamp", System.currentTimeMillis() + ""));
//    if (!TextUtils.isEmpty(userId)) {
//      urlParams.add(new Pair<String, String>("userId", userId));
//    }
//    urlParams.add(new Pair<String, String>("productFlag", productFlag));
//    urlParams.add(new Pair<String, String>("deviceId", deviceId));
//    Tools.sign(urlParams);//顺序必须放在这里！
//    if (netSpeed != null && !"unknow".equals(netSpeed) && !"None".equals(netSpeed)) {
//      urlParams.add(new Pair<String, String>("netType", netSpeed));
//    }
//    urlParams.add(new Pair<String, String>("deviceType", "Android"));
//    task.setUrlParams(urlParams);
//    task.setNextTask(this);
//    return task;
//  }
//
//  @Override
//  protected List<UserCase> doTask() {
//    Object lastRes = getLastResult();
//    if (lastRes == null) {
//      TaskManager.l("error, QueryUserCaseListTask getLastResult is null!");
//    } else if (lastRes instanceof HttpResponse && ((HttpResponse) lastRes).getCode() == 200) {
//      try {
//        UserCaseListResponse response = SimpleJsonTool.getInstance()
//            .deserialize(((HttpResponse) lastRes).getBody(), UserCaseListResponse.class);
//        List<UserCase> res = response.getResult();
//        if (response.getRetCode() == 200 && res != null) {
//          return res;
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//        TaskManager.l(e.getMessage());
//        return null;
//      }
//    }
//    return null;
//  }
//
//  /**
//   * 任务完成，处理回调
//   */
//  @Override
//  protected void doAfterTask() {
//    super.doAfterTask();
//    if (getResult() == null) {
//      handleFail();
//    } else {
//      List<UserCase> res = null;
//      try {
//        res = getResult();
//      } catch (ClassCastException e) {
//        e.printStackTrace();
//        TaskManager.l(e.getMessage());
//        handleFail();
//      }
//      handleSuccess(res);
//    }
//  }
//
//  private void handleFail() {
//    if (callback != null) {
//      callback.onQueryUserCaseListFail();
//    }
//  }
//
//  private void handleSuccess(List<UserCase> list) {
//    if (callback != null && list != null) {
//      callback.onQueryUserCaseListSuccess(list);
//    }
//  }
}

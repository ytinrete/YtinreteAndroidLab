package com.ytinrete.exp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
//import com.ytinrete.db.orm.OrmMusicFileFolder;
//import com.ytinrete.db.orm.OrmMusicFileItem;
//import com.ytinrete.dto.DtoMusicFileFolder;
//import com.ytinrete.dto.DtoMusicFileItem;
import com.ytinrete.normal.NormalTest1Activity;
import com.ytinrete.tools.AppLog;
import com.ytinrete.view.NotInterceptTouchFrameLayout;
import com.ytinrete.widegt.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//import io.realm.Realm;
//import io.realm.RealmResults;

/**
 * 实验用activity
 */

public class YtExpActivity extends YtBaseActivity {

  String TAG = "lirui";


  FilePickerDialog.FilePickerRes testRes = new FilePickerDialog.FilePickerRes();

  /**
   * // 带有 Button 参数
   *
   * @OnClick(R.id.submit) public void sayHi(Button button) {
   * button.setText("Hello!");
   * }
   * <p>
   * // 不带参数
   * @OnClick(R.id.submit) public void submit() {
   * // TODO submit data to server...
   * }
   * <p>
   * // 同时注入多个 View 事件
   * @OnClick({ R.id.door1, R.id.door2, R.id.door3 })
   * public void pickDoor(DoorView door) {
   * if (door.hasPrizeBehind()) {
   * Toast.makeText(this, "You win!", LENGTH_SHORT).show();
   * } else {
   * Toast.makeText(this, "Try again", LENGTH_SHORT).show();
   * }
   * }
   */


  @BindView(R.id.btn1)
  Button btn1;
  @BindView(R.id.tv1)
  TextView tv1;
  @BindView(R.id.btn2)
  Button btn2;
  @BindView(R.id.tv2)
  TextView tv2;
  @BindView(R.id.btn3)
  Button btn3;
  @BindView(R.id.tv3)
  TextView tv3;
  @BindView(R.id.btn4)
  Button btn4;
  @BindView(R.id.tv4)
  TextView tv4;
  @BindView(R.id.btn5)
  Button btn5;
  @BindView(R.id.tv5)
  TextView tv5;

  @BindView(R.id.mask)
  NotInterceptTouchFrameLayout mask;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exp);

    ButterKnife.bind(this);

    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

  }

  private SensorManager sensorManager;
  private Vibrator vibrator;


  private SensorEventListener sensorEventListener = new SensorEventListener() {
    @Override
    public void onSensorChanged(SensorEvent event) {

      // 传感器信息改变时执行该方法
      float[] values = event.values;
      float x = values[0]; // x轴方向的重力加速度，向右为正
      float y = values[1]; // y轴方向的重力加速度，向前为正
      float z = values[2]; // z轴方向的重力加速度，向上为正
      Log.i(TAG, "x轴方向的重力加速度" + x +  "；y轴方向的重力加速度" + y +  "；z轴方向的重力加速度" + z);
      // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
      int medumValue = 19;// 如果不敏感请自行调低该数值,低于10的话就不行了,因为z轴上的加速度本身就已经达到10了
      if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
        vibrator.vibrate(200);

        Log.d(TAG, "onSensorChanged: !!!!!");

      }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };


//  private static Uri changHost(Uri uri, String hostNew) {
//
//    if (uri == null || TextUtils.isEmpty(hostNew)) {
//      return uri;
//    }
//    String schme = uri.getScheme();
//    String path = uri.getPath();
//    String str = "";
//    if (!TextUtils.isEmpty(path)) {
//      str = schme + "://" + hostNew + "?" + path;
//    }
//    return Uri.parse(str);
//  }

  @OnClick(R.id.mask)
  public void maskClick(){
    Log.d(TAG, "maskClick: ");
  }

  private static Uri changHost(Uri uri, String hostNew) {
    Uri.Builder uriBuilder = new Uri.Builder()
        .scheme(uri.getScheme())
        .path(uri.getPath())
        .authority(hostNew);
    for (String name : uri.getQueryParameterNames()) {
      uriBuilder.appendQueryParameter(name, uri.getQueryParameter(name));
    }
    return uriBuilder.build();
  }

  @OnClick(R.id.btn1)
  public void onClick1() {
    showShortToast("btn1");

    if (true) {


      String str = "fooo://bar.com/aaa/bbb?ccc=123&ddd=456";
      Uri tempUri0 = Uri.parse(str);
      Log.d("lirui", "onCreate: " + changHost(tempUri0, "bar2.net").toString());


//      startActivity(new Intent(YtExpActivity.this, YtMusicActivity.class));


      return;
    }

//    final IjkMediaPlayer player = new IjkMediaPlayer();
//    IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_VERBOSE);
//    try {
//      player.setDataSource("/storage/emulated/0/Download/alice.mp3");
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    player.prepareAsync();
//
//    btn1.postDelayed(new Runnable() {
//      @Override
//      public void run() {
//        player.start();
//      }
//    }, 1000);


//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//
//        HttpHelper.newCall(new MessageBoardGetListRequest()).enqueue(
//            new JsonListCallback<ArrayList<DtoSiteListItem>, DtoSiteListItem>(
//                (Class)ArrayList.class, DtoSiteListItem.class) {
//
//
//              @Override
//              public void onSuccess(ArrayList<DtoSiteListItem> dtoSiteListItems, Response response, Call call) {
//
//                AppLog.e(dtoSiteListItems.size()+"");
//
//                for (DtoSiteListItem item:dtoSiteListItems){
//                  Log.e("lirui", item.getContent());
//                }
//
//              }
//
//              @Override
//              public void onFailure(Exception e, Call call) {
//
//              }
//            });
//
//      }
//    }).run();


//    JsonSerializer.getInstance().deserialize(null, List.class, null);

//    Message msg = new Message();
//    msg.what = YtMusicService.START;
//    Bundle bd = new Bundle();
//    bd.putString("url", "/storage/emulated/0/Download/alice.mp3");
//    msg.setData(bd);
//    YtMusicService.sendMsg(msg);

//    FilePickerDialog.FilePickerRes sss = new FilePickerDialog.FilePickerRes();
//    sss.setStart("/mnt");

    FilePickerDialog.showDialog(this, testRes);

    AppLog.d("end btn");


  }


//  private ArrayList<DtoMusicFileFolder> scan(String root) {
//
//    Realm realm = Realm.getDefaultInstance();
//
//    if (TextUtils.isEmpty(root)) {
//      return null;
//    }
//    int rootLength = root.length();
//
//    ArrayList<DtoMusicFileFolder> folders = new ArrayList<>();
//
//    realm.beginTransaction();
//
//    File rootFile = new File(root);
//    if (rootFile.exists() && rootFile.isDirectory() && rootFile.listFiles() != null) {
//      for (File f : rootFile.listFiles()) {
//        if (f.isDirectory()) {
//          //根目录下只找二级目录，忽略文件
//          DtoMusicFileFolder newFolder = new DtoMusicFileFolder();
//          newFolder.setFullPath(f.getAbsolutePath());
//          newFolder.setRelativePath(f.getAbsolutePath().substring(rootLength));
//          newFolder.setFolderName(f.getName());
//          newFolder.setFiles(new ArrayList<DtoMusicFileItem>());
//
//
//          OrmMusicFileFolder ormFolder = realm.createObject(OrmMusicFileFolder.class);
//          ormFolder.setRelativePath(newFolder.getRelativePath());
//          ormFolder.setFolderName(newFolder.getFolderName());
//          ormFolder.setFullPath(newFolder.getFullPath());
//
//
//          if (f.listFiles() != null) {
//            for (File subFile : f.listFiles()) {
//              DtoMusicFileItem newFile = new DtoMusicFileItem();
//              newFile.setFullPath(subFile.getAbsolutePath());
//              newFile.setRelativePath(subFile.getAbsolutePath().substring(rootLength));
//              newFile.setFileName(subFile.getName());
//
//              OrmMusicFileItem ormFile = realm.createObject(OrmMusicFileItem.class);
//              ormFile.setFullPath(newFile.getFullPath());
//              ormFile.setRelativePath(newFile.getRelativePath());
//              ormFile.setFineName(newFile.getFileName());
//
//              ormFolder.getFiles().add(ormFile);
//
//              newFolder.getFiles().add(newFile);
//            }
//          }
//
//          folders.add(newFolder);
//        }
//      }
//    }
//    realm.commitTransaction();
//
//    AppLog.d("scan done");
//    return folders;
//  }


  @OnClick(R.id.btn2)
  public void onClick2() {
    showShortToast("btn2");

    if(sensorManager!=null){
      sensorManager.registerListener(sensorEventListener,
          sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
          SensorManager.SENSOR_DELAY_NORMAL);
      // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
    }

//    ArrayList<DtoMusicFileFolder> folders = scan(testRes.getRes());


//    HttpHelper.newCall(new MessageBoardGetListRequest()).enqueue(new StringCallback() {
//      @Override
//      public void onSuccess(String s, Response response, Call call) {
//
//        String test = s.substring(1, s.indexOf("}") + 1);
//
//        DtoSiteListItem kkk = JsonSerializer.getInstance().deserialize(test, DtoSiteListItem.class);
//
//        showShortToast("aaa");
//      }
//
//      @Override
//      public void onFailure(Exception e, Call call) {
//
//        showShortToast("bbbb");
//      }
//    });


//    Message msg = new Message();
//    msg.what = YtMusicService.PAUSE;
//    Bundle bd = new Bundle();
//    bd.putString("url", "/storage/emulated/0/Download/alice.mp3");
//    msg.setData(bd);
//    YtMusicService.sendMsg(msg);

    AppLog.d("end btn");

  }


  @OnClick(R.id.btn3)
  public void onClick3() {
    showShortToast("btn3");

//    Realm realm = Realm.getDefaultInstance();
//
//    final RealmResults<OrmMusicFileFolder> fs = realm.where(OrmMusicFileFolder.class).findAll();
//
//
//    for (OrmMusicFileFolder f : fs) {
//      AppLog.d(f.getFolderName());
//
//    }

//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        fs.get(3).setFolderName("lalala");
//      }
//    });


//    Message msg = new Message();
//    msg.what = YtMusicService.STOP;
//    Bundle bd = new Bundle();
//    bd.putString("url", "/storage/emulated/0/Download/alice.mp3");
//    msg.setData(bd);
//    YtMusicService.sendMsg(msg);

    AppLog.d("end btn");
  }


  @OnClick(R.id.btn4)
  public void onClick4() {
    showShortToast("btn4");

//    Realm realm = Realm.getDefaultInstance();
//
//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//
//
//        RealmResults<OrmMusicFileFolder> fs = realm.where(OrmMusicFileFolder.class).findAll();
//
//        for (OrmMusicFileFolder f : fs) {
//          f.getFiles().deleteAllFromRealm();
//
//        }
//
//        fs.deleteAllFromRealm();
//      }
//    });


//    Message msg = new Message();
//    msg.what = YtMusicService.JUMP;
//    Bundle bd = new Bundle();
//    bd.putString("url", "/storage/emulated/0/Download/alice.mp3");
//    msg.setData(bd);
//    YtMusicService.sendMsg(msg);


    AppLog.d("end btn");

  }

  @OnClick(R.id.btn5)
  public void onClick5() {
    showShortToast("btn5");


//    Message msg = new Message();
//    msg.what = YtMusicService.SEEK;
//    Bundle bd = new Bundle();
//    bd.putInt("seek", 50000);
////    bd.putString("url", "/storage/emulated/0/Download/alice.mp3");
//    msg.setData(bd);
//    YtMusicService.sendMsg(msg);

    startActivity(new Intent(YtExpActivity.this, NormalTest1Activity.class));


    AppLog.d("end btn");
  }


}

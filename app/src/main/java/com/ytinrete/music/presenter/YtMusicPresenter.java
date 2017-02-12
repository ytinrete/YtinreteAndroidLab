package com.ytinrete.music.presenter;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.SeekBar;

import com.ytinrete.android.lab.R;
import com.ytinrete.dto.DtoMusicFileFolder;
import com.ytinrete.dto.DtoMusicFileItem;
import com.ytinrete.music.contracts.YtMusicContracts;
import com.ytinrete.music.model.YtMusicModel;
import com.ytinrete.mvp.presenter.Presenter;
import com.ytinrete.tools.AppLog;
import com.ytinrete.widegt.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;

/**
 *
 */

public class YtMusicPresenter extends Presenter<YtMusicContracts.View> implements
    YtMusicContracts.Presenter, YtMusicContracts.Model.Callback {

  private YtMusicModel model;
  private FilePickerDialog.FilePickerRes mRes = new FilePickerDialog.FilePickerRes();
  private ArrayList<String> leftList = new ArrayList<>();
  private ArrayList<String> rightList = new ArrayList<>();

  private ArrayList<DtoMusicFileFolder> dataList = new ArrayList<>();

  public YtMusicPresenter() {
    model = new YtMusicModel();
    model.setCallback(this);
  }

  @Override
  protected void onTakeView(YtMusicContracts.View view) {
    super.onTakeView(view);

    if (getView() == null) {
      AppLog.e("fatal error, getview is null");
      return;
    }
    //初始化
    model.getLocalStoreMusicList();
  }

  @Override
  public void onLocalStoreMusicList(ArrayList<DtoMusicFileFolder> res) {
    if (getView() == null) {
      AppLog.e("fatal error, getview is null");
      return;
    }
    if (res == null) {
      //没有存储，应用初始化
      getView().initSettingPanel(null);
      getView().openOrCloseSettingsPanel(true);
    } else {
      //有存储
      dataList.clear();
      dataList.addAll(res);
      getView().initSettingPanel("store");
      refreshBothList();
      getView().refreshLeftList();
      getView().refreshRightList();
    }
  }

  @Override
  public void onBtnClick(int id) {
    if (getView() == null) {
      AppLog.e("fatal error, getview is null");
      return;
    }
    switch (id) {
      case R.id.activity_music_btn_scan:
        AppLog.d("2");
        if (mRes != null && !TextUtils.isEmpty(mRes.getRes())) {
          getView().setRoot(mRes.getRes());
          dataList.clear();
          scan(mRes.getRes());
          refreshBothList();
          getView().refreshLeftList();
          getView().refreshRightList();
        }
        break;

      case R.id.activity_music_btn_clear:
        AppLog.d("3");
        getView().showAlertDialog("Delete", "Are you sure to delete all current data?",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

              }
            });
        break;

      case R.id.activity_music_btn_ok:
        AppLog.d("3");
        break;

      case R.id.activity_music_btn_pre:
        AppLog.d("3");
        break;

      case R.id.activity_music_btn_player:
        AppLog.d("3");
        break;

      case R.id.activity_music_btn_next:
        AppLog.d("3");
        break;

      case R.id.activity_music_btn_del:


        AppLog.d("3");

        break;


      case R.id.activity_music_toolbar_settings:
        getView().openOrCloseSettingsPanel(null);
        break;

      case R.id.activity_music_toolbar_refresh:

        break;

      case R.id.activity_music_toolbar_server:

        break;


      default:
        break;
    }

  }

  @Override
  public void onLeftListSelected(int pos) {
    if (getView() == null) {
      AppLog.e("fatal error, getview is null");
      return;
    }
    rightList.clear();
    for (DtoMusicFileItem item : dataList.get(pos).getFiles()) {
      rightList.add(item.getFileName());
    }
    getView().selectRightList(-1);
    getView().refreshRightList();

  }

  @Override
  public void onRightListSelected(int pos) {

  }

  @Override
  public void onRightListBtn(int pos) {

  }

  private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  };

  @Override
  public ArrayList<String> getDataList(boolean isLeft) {
    return isLeft ? leftList : rightList;
  }

  @Override
  public FilePickerDialog.FilePickerRes getPickerRes() {
    return mRes;
  }

  @Override
  public SeekBar.OnSeekBarChangeListener getSeekerListener() {
    return seekBarChangeListener;
  }


  private void scan(String root) {
    if (TextUtils.isEmpty(root)) {
      return;
    }
    int rootLength = root.length();

    File rootFile = new File(root);
    if (rootFile.exists() && rootFile.isDirectory() && rootFile.listFiles() != null) {
      for (File f : rootFile.listFiles()) {
        if (f.isDirectory()) {
          //根目录下只找二级目录，忽略文件
          DtoMusicFileFolder newFolder = new DtoMusicFileFolder();
          newFolder.setFullPath(f.getAbsolutePath());
          newFolder.setRelativePath(f.getAbsolutePath().substring(rootLength));
          newFolder.setFolderName(f.getName());
          newFolder.setFiles(new ArrayList<DtoMusicFileItem>());

          if (f.listFiles() != null) {
            for (File subFile : f.listFiles()) {
              if (subFile.getName().startsWith(".")) {
                continue;
              }
              DtoMusicFileItem newFile = new DtoMusicFileItem();
              newFile.setFullPath(subFile.getAbsolutePath());
              newFile.setRelativePath(subFile.getAbsolutePath().substring(rootLength));
              newFile.setFileName(subFile.getName());

              newFolder.getFiles().add(newFile);
            }
          }
          dataList.add(newFolder);
          model.initLocalStoreMusicList(dataList);
        }
      }
    }
    AppLog.d("scan done");
  }

  private void refreshBothList() {
    leftList.clear();
    rightList.clear();
    for (int i = 0; i < dataList.size(); i++) {
      leftList.add(dataList.get(i).getFolderName());
      if (i == 0) {
        for (DtoMusicFileItem item : dataList.get(i).getFiles()) {
          rightList.add(item.getFileName());
        }
      }
    }
  }


}

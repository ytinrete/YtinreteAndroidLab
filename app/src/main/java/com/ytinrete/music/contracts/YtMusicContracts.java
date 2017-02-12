package com.ytinrete.music.contracts;

import android.content.DialogInterface;
import android.widget.SeekBar;

import com.ytinrete.dto.DtoMusicFileFolder;
import com.ytinrete.widegt.FilePickerDialog;

import java.util.ArrayList;

/**
 *
 */

public interface YtMusicContracts {

  interface View {

    void setRoot(String path);

    void setMusicPanel(String start, int seek, String end);

    void refreshLeftList();

    void selectLeftList(int pos);

    void refreshRightList();

    void selectRightList(int pos);

    void showAlertDialog(String title, String msg,
                         DialogInterface.OnClickListener listener);

    void showToast(String msg);

    void initSettingPanel(String root);

    void openOrCloseSettingsPanel(Boolean toOpen);

    void performToolbarItemAnimation(int id);
  }

  interface Presenter {

    void onBtnClick(int id);

    void onLeftListSelected(int pos);

    void onRightListSelected(int pos);

    void onRightListBtn(int pos);

    ArrayList<String> getDataList(boolean isLeft);

    FilePickerDialog.FilePickerRes getPickerRes();

    SeekBar.OnSeekBarChangeListener getSeekerListener();

  }


  interface Model {

    /**
     * 获取本地存储的音乐文件列表
     */
    void getLocalStoreMusicList();

    void initLocalStoreMusicList(ArrayList<DtoMusicFileFolder> res);

    void clearAllLocalStoreMusicList();


    interface Callback {


      void onLocalStoreMusicList(ArrayList<DtoMusicFileFolder> res);

    }
  }

}

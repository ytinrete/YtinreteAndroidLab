package com.ytinrete.music.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
import com.ytinrete.music.contracts.YtMusicContracts;
import com.ytinrete.music.presenter.YtMusicPresenter;
import com.ytinrete.mvp.factory.RequiresPresenter;
import com.ytinrete.tools.AppLog;
import com.ytinrete.widegt.FilePickerDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
@RequiresPresenter(YtMusicPresenter.class)
public class YtMusicActivity extends YtBaseActivity<YtMusicPresenter> implements YtMusicContracts.View,
    View.OnClickListener {

  @BindView(R.id.activity_music_btn_select)
  Button btnSelect;
  @BindView(R.id.activity_music_btn_scan)
  Button btnScan;
  @BindView(R.id.activity_music_btn_clear)
  Button btnClear;
  @BindView(R.id.activity_music_tv_root)
  TextView tvRoot;

  @BindView(R.id.activity_music_tv_name)
  TextView tvName;
  @BindView(R.id.activity_music_tv_time_start)
  TextView tvTimeStart;
  @BindView(R.id.activity_music_tv_time_end)
  TextView tvTimeEnd;
  @BindView(R.id.activity_music_seek)
  SeekBar seekBar;

  @BindView(R.id.activity_music_btn_ok)
  Button btnOk;
  @BindView(R.id.activity_music_btn_pre)
  Button btnPre;
  @BindView(R.id.activity_music_btn_player)
  Button btnPlayer;
  @BindView(R.id.activity_music_btn_next)
  Button btnNext;
  @BindView(R.id.activity_music_btn_del)
  Button btnDel;

  @BindView(R.id.activity_music_recycler_left)
  RecyclerView recyclerLeft;

  @BindView(R.id.activity_music_recycler_right)
  RecyclerView recyclerRight;

  @BindView(R.id.activity_music_setting_panel)
  LinearLayout settingsPanel;


  @OnClick({R.id.activity_music_btn_select, R.id.activity_music_btn_scan,
      R.id.activity_music_btn_clear, R.id.activity_music_btn_ok, R.id.activity_music_btn_pre,
      R.id.activity_music_btn_player, R.id.activity_music_btn_next, R.id.activity_music_btn_del})
  @Override
  public void onClick(View v) {

    if (v.getId() == R.id.activity_music_btn_select) {
      FilePickerDialog.showDialog(YtMusicActivity.this, getPresenter().getPickerRes());
    } else {
      getPresenter().onBtnClick(v.getId());
    }
  }

  private int settingsPanelMarginTopSave = 0;
  private Drawable selectDrawable;
  private ColorDrawable tranDrawable;
  private Toolbar toolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AppLog.d("YtMusicActivity onCreate");

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

    setContentView(R.layout.activity_music);
    ButterKnife.bind(this);

    tranDrawable = new ColorDrawable(getResources().getColor(R.color.transparent));
    selectDrawable = getResources().getDrawable(R.drawable.bg_round_rectangle_empty_blue);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
//    toolbar.inflateMenu(R.menu.activity_music_toolbar);
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        getPresenter().onBtnClick(item.getItemId());
        return true;
      }
    });

    toolbar.setNavigationIcon(R.drawable.ic_action_back);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    seekBar.setOnSeekBarChangeListener(getPresenter().getSeekerListener());

    recyclerLeft.setLayoutManager(new LinearLayoutManager(this));
    recyclerRight.setLayoutManager(new LinearLayoutManager(this));

    recyclerLeft.setAdapter(new ListAdapter(true, getPresenter().getDataList(true)));
    recyclerRight.setAdapter(new ListAdapter(false, getPresenter().getDataList(false)));

  }

  @Override
  public void performToolbarItemAnimation(int id) {
    View action = toolbar.findViewById(id);
    if (action != null) {
      ObjectAnimator animator = ObjectAnimator
          .ofFloat(action, "rotation", action.getRotation() + 180);
      animator.setDuration(200);
      animator.start();
    }
  }


  private class ListAdapter extends RecyclerView.Adapter {

    private boolean isLeft;
    private ArrayList<String> data;

    public void setCurrentSelectPos(int currentSelectPos) {
      this.currentSelectPos = currentSelectPos;
    }

    private int currentSelectPos = -1;

    ListAdapter(boolean isLeft, ArrayList<String> data) {
      this.isLeft = isLeft;
      this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new Holder(getLayoutInflater()
          .inflate(R.layout.item_recycler_music_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

      if (holder instanceof Holder) {
        Holder mHolder = (Holder) holder;
        if (position > data.size() || data.get(position) == null) {
          AppLog.e("fatal error!");
          return;
        }
        mHolder.pos = position;
        mHolder.tv.setText(data.get(position));
        if (currentSelectPos == position) {
          mHolder.tv.setBackgroundDrawable(selectDrawable);
        } else {
          mHolder.tv.setBackgroundDrawable(tranDrawable);
        }
      }
    }

    @Override
    public int getItemCount() {
      return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
      private TextView tv;
      private Button btn;
      private int pos = -1;

      public Holder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.music_item_name);
        btn = (Button) itemView.findViewById(R.id.music_item_button);
        if (isLeft) {
          btn.setVisibility(View.GONE);
        } else {
          btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getPresenter().onRightListBtn(pos);
            }
          });
        }
        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentSelectPos = pos;
            if (isLeft) {
              getPresenter().onLeftListSelected(pos);
              recyclerLeft.getAdapter().notifyDataSetChanged();
            } else {
              getPresenter().onRightListSelected(pos);
              recyclerRight.getAdapter().notifyDataSetChanged();
            }
          }
        });
      }
    }

  }

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) settingsPanel.getLayoutParams();
    settingsPanelMarginTopSave = lp.topMargin;
  }

  @Override
  public void openOrCloseSettingsPanel(Boolean toOpen) {

    final RelativeLayout.LayoutParams lp =
        (RelativeLayout.LayoutParams) settingsPanel.getLayoutParams();

    if (lp.topMargin > -10) {
      //当前是展开状态
      if (toOpen == null || !toOpen) {
        //忽略或希望关闭

        //关闭动画
        performToolbarItemAnimation(R.id.activity_music_toolbar_settings);
        ValueAnimator closeAnim = ValueAnimator.ofInt(-2, settingsPanelMarginTopSave);
        closeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            lp.topMargin = (Integer) animation.getAnimatedValue();
            settingsPanel.requestLayout();
          }
        });
        closeAnim.setDuration(200);
        closeAnim.start();
      }

    } else {
      //当前是关闭状态
      if (toOpen == null || toOpen) {
        //希望展开

        //展开动画
        performToolbarItemAnimation(R.id.activity_music_toolbar_settings);
        ValueAnimator openAnim = ValueAnimator.ofInt(settingsPanelMarginTopSave, -2);
        openAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            lp.topMargin = (Integer) animation.getAnimatedValue();
            settingsPanel.requestLayout();
          }
        });
        openAnim.setDuration(200);
        openAnim.start();
      }
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_music_toolbar, menu);
    return true;
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    AppLog.d("YtMusicActivity onDestroy");
  }

  @Override
  public void setRoot(String path) {
    tvRoot.setText(path);
  }

  @Override
  public void setMusicPanel(String start, int seek, String end) {
    tvTimeStart.setText(start);
    tvTimeEnd.setText(end);
    seekBar.setProgress(seek);

  }

  @Override
  public void refreshLeftList() {
    recyclerLeft.getAdapter().notifyDataSetChanged();
  }

  @Override
  public void selectLeftList(int pos) {
    ((ListAdapter) recyclerLeft.getAdapter()).setCurrentSelectPos(pos);
    recyclerLeft.getAdapter().notifyDataSetChanged();
  }

  @Override
  public void refreshRightList() {
    recyclerRight.getAdapter().notifyDataSetChanged();
  }

  @Override
  public void selectRightList(int pos) {
    ((ListAdapter) recyclerRight.getAdapter()).setCurrentSelectPos(pos);
    recyclerRight.getAdapter().notifyDataSetChanged();

  }

  @Override
  public void showAlertDialog(String title, String msg, DialogInterface.OnClickListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(TextUtils.isEmpty(title) ? "" : title);
    builder.setMessage(TextUtils.isEmpty(msg) ? "" : msg);
    if (listener != null) {
      builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
      });
    }
    builder.setPositiveButton("ok", listener != null ? listener :
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        });
    builder.setCancelable(true);
    builder.create().show();

  }

  @Override
  public void showToast(String msg) {
    showShortToast(msg);
  }

  @Override
  public void initSettingPanel(String root) {
    if (TextUtils.isEmpty(root)) {
      //初始化
      btnScan.setEnabled(true);
      btnSelect.setEnabled(true);
      btnClear.setEnabled(false);
    } else {
      //数据读取
      tvRoot.setText(root);
      btnScan.setEnabled(false);
      btnClear.setEnabled(true);
      btnSelect.setEnabled(false);
    }
  }
}

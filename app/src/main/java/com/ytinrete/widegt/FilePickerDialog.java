package com.ytinrete.widegt;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ytinrete.android.lab.R;
import com.ytinrete.tools.AppLog;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 */

public class FilePickerDialog extends AlertDialog {

  private Context mContext;
  private View contentView;
  private FilePickerRes mRes;
  private TextView tvCurrent;
  private Button btnUp;
  private RecyclerView recyclerView;
  private TextView tvTarget;

  private File currentFile;
  private File[] listData;

  private FilePickerDialog(@NonNull Context context, FilePickerRes res) {
    super(context);
    mContext = context;
    mRes = res;
    initView();
    init();
  }

  public static void showDialog(Context context, FilePickerRes res) {
    if (context == null) {
      return;
    }
    new FilePickerDialog(context, res).show();
  }

  private OnClickListener mPositiveButtonListener = new OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
      if (mRes != null) {
        mRes.setRes(tvTarget.getText().toString().trim());
      }
    }
  };
  private OnClickListener mNegativeButtonListener = new OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
  };

  private void initView() {

    setTitle("FilePickup");
    contentView = getLayoutInflater().inflate(R.layout.dialog_file_picker, null);
    setView(contentView);
    tvCurrent = (TextView) contentView.findViewById(R.id.file_picker_current);
    btnUp = (Button) contentView.findViewById(R.id.file_picker_last_folder);
    tvTarget = (TextView) contentView.findViewById(R.id.file_picker_res);
    setButton(DialogInterface.BUTTON_POSITIVE, "Select", mPositiveButtonListener);
    setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", mNegativeButtonListener);

    btnUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toUpFolder();
      }
    });

    recyclerView = (RecyclerView) contentView.findViewById(R.id.file_picker_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    recyclerView.setAdapter(new RecyclerView.Adapter() {
      @Override
      public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
          case 0:
            return new Holder(getLayoutInflater()
                .inflate(R.layout.item_recycler_file_picker, parent, false));
          default:
            break;
        }
        return null;
      }

      @Override
      public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
          Holder mHolder = (Holder) holder;
          if (position > listData.length || listData[position] == null) {
            AppLog.e("fatal error!");
            return;
          }
          File data = listData[position];
          mHolder.bindStr = data.getAbsolutePath();
          if (data.isDirectory()) {
            mHolder.btn.setVisibility(View.VISIBLE);
            mHolder.btn.setEnabled(true);
          } else {
            mHolder.btn.setVisibility(View.GONE);
            mHolder.btn.setEnabled(false);
          }
          mHolder.tv.setText(data.getName());
        }

      }

      @Override
      public int getItemCount() {
        if (listData == null) {
          return 0;
        } else {
          return listData.length;
        }
      }

      @Override
      public int getItemViewType(int position) {
        return 0;
      }

      class Holder extends RecyclerView.ViewHolder {
        private TextView tv;
        private Button btn;
        private String bindStr;

        public Holder(View itemView) {
          super(itemView);
          tv = (TextView) itemView.findViewById(R.id.file_picker_item_name);
          btn = (Button) itemView.findViewById(R.id.file_picker_item_button);
          btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              toSubFolder(bindStr);
            }
          });
          itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onSelectItem(bindStr);
            }
          });
        }
      }
    });
  }

  private void reFresh() {
    if (currentFile != null) {
      tvCurrent.setText(TextUtils.isEmpty(currentFile.getName()) ? "/" : currentFile.getName());
      listData = currentFile.listFiles();
      if (listData != null) {
        Arrays.sort(listData, comparator);
      }
      recyclerView.getAdapter().notifyDataSetChanged();
//      recyclerView.getAdapter().notifyItemChanged(0, recyclerView.getAdapter().getItemCount());
    }
  }

  private Comparator comparator = new Comparator<File>() {
    @Override
    public int compare(File o1, File o2) {
      if (o1.isDirectory() && !o2.isDirectory()) {
        return -1;
      } else if (!o1.isDirectory() && o2.isDirectory()) {
        return 1;
      }
      return o1.getName().compareTo(o2.getName());
    }
  };

  private void toUpFolder() {
    if (currentFile == null) {
      currentFile = new File("/");
      reFresh();
      return;
    }
    if (!"/".equals(currentFile.getAbsolutePath())) {
      currentFile = currentFile.getParentFile();
      reFresh();
    }
  }

  private void toSubFolder(String filePath) {
    if (!TextUtils.isEmpty(filePath)) {
      currentFile = new File(filePath);
      reFresh();
    }
  }

  private void onSelectItem(String filePath) {
    if (!TextUtils.isEmpty(filePath)) {
      tvTarget.setText(filePath);
    }
  }

  private void init() {
    if (mRes != null && !TextUtils.isEmpty(mRes.getStart())) {
      currentFile = new File(mRes.getStart());
      if (!currentFile.exists()) {
        currentFile = new File("/");
      }
    } else {
      currentFile = new File("/");
    }
    reFresh();
  }


  public static class FilePickerRes {
    private String res;
    private String start;


    public String getStart() {
      return start;
    }

    public void setStart(String start) {
      this.start = start;
    }

    public String getRes() {
      return res;
    }

    public void setRes(String res) {
      this.res = res;
    }
  }


}

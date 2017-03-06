package com.ytinrete.main;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ytinrete.android.lab.R;
import com.ytinrete.base.YtBaseActivity;
import com.ytinrete.tools.AppLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;

public class YtMainActivity extends YtBaseActivity implements NavigationView.OnNavigationItemSelectedListener {


  private FloatingActionButton btn;
  private TextView res;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//    drawer.setDrawerListener(toggle);
//    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    res = (TextView) findViewById(R.id.res);

    btn = (FloatingActionButton) findViewById(R.id.action);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AppLog.d("btn~~~");



      }
    });


  }


  private BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {

      String action = intent.getAction();

      if (action.equals(Intent.ACTION_SCREEN_OFF)) {

//        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        startActivity(in);

      }
    }
  };

  @Override
  protected void onResume() {
    super.onResume();
    IntentFilter f = new IntentFilter();
    f.addAction(Intent.ACTION_SCREEN_OFF);
    registerReceiver(receiver, f);

  }


  @Override
  protected void onPause() {
    super.onPause();

//    unregisterReceiver(receiver);

  }

  private void save(JSONObject j) {

    verifyStoragePermissions(this);

//    File save = new File(getExternalMediaDirs()[1]+"/v.json");

    File save = new File(Environment.getExternalStorageDirectory() + "/v.json");
    if (save.exists()) {
      save.delete();
    }
    try {
      save.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      BufferedSink sink = Okio.buffer(Okio.sink(save));
      sink.writeUtf8(j.toString());
//      sink.writeString("测试信息", Charset.forName("UTF-8"));
      sink.close();
    } catch (IOException e) {
      e.printStackTrace();
    }


//    BufferedWriter bw = null;
//    FileWriter fw=null;
//
//    try
//    { fw = new FileWriter(save);
//      bw = new BufferedWriter(fw);
//      bw.write(j.toString());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }finally {
//      if(bw!=null){
//        try {
//          bw.close();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//      }
//      if(fw!=null){
//        try {
//          fw.close();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//      }
//    }

  }

  // Storage Permissions
  private static final int REQUEST_EXTERNAL_STORAGE = 1;
  private static String[] PERMISSIONS_STORAGE = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  };

  public static void verifyStoragePermissions(Activity activity) {
    // Check if we have write permission
    int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    if (permission != PackageManager.PERMISSION_GRANTED) {
      // We don't have permission so prompt the user
      ActivityCompat.requestPermissions(
          activity,
          PERMISSIONS_STORAGE,
          REQUEST_EXTERNAL_STORAGE
      );
    }
  }


  private String formFileTree() {
    File root = new File("/storage/sdcard1/v");
    if (isExternalStorageReadable() && root != null) {

      JSONObject j = rolling(root);
      save(j);
      try {
        return j.toString(2);
      } catch (JSONException e) {
        e.printStackTrace();
      }

    }
    return "";
  }


  private JSONObject rolling(File f) {

    if (f == null) {
      return null;
    }

    if (f.isFile()) {
      JSONObject jsonObject = new JSONObject();
      try {
        jsonObject.put("F:", f.getAbsolutePath());
      } catch (JSONException e) {
        e.printStackTrace();
      }
      return jsonObject;
    } else {
      JSONObject jsonObject = new JSONObject();
      JSONArray jsonArray = new JSONArray();
      try {
        jsonObject.put(f.getName(), jsonArray);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      if (f.listFiles() != null) {
        for (File fs : f.listFiles()) {
          jsonArray.put(rolling(fs));
        }
      }
      return jsonObject;
    }


  }


  public File getRootStorageDir() {
    // Get the directory for the user's public pictures directory.
    try {
      return Environment.getExternalStorageDirectory();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /* Checks if external storage is available for read and write */
  public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  /* Checks if external storage is available to at least read */
  public boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      return true;
    }
    return false;
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.END)) {
      drawer.closeDrawer(GravityCompat.END);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}

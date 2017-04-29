package com.ytinrete.music.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.ytinrete.contract.YtApp;
import com.ytinrete.music.view.YtMusicActivity;
import com.ytinrete.tools.AppLog;
import com.ytinrete.tools.PollingDevice;

import java.io.IOException;
import java.util.ArrayList;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 *
 */

public class YtMusicService extends Service {

  private static YtMusicService ytMusicService;

  public static final int START = 1001;
  public static final int STOP = 1002;
  public static final int PAUSE = 1003;
  public static final int JUMP = 1004;
  public static final int SEEK = 1005;


  private static final int PLAYER_STATUS_PLAYING = 2001;
  private static final int PLAYER_STATUS_PREPARING = 2002;
  private static final int PLAYER_STATUS_STOPPED = 2003;
  private static final int PLAYER_STATUS_PAUSED = 2004;

  private static int status = PLAYER_STATUS_STOPPED;

  private static String url;



  private static IjkMediaPlayer player;

  private static PollingDevice pollingDevice;

  private static ArrayList<IMusicServiceCallback> callbacks = new ArrayList<>();


  public interface IMusicServiceCallback {
    void onError();

    void onComplete();

    void onDuration(long current, long total);
  }

  public void subscribeCallback(IMusicServiceCallback callback) {
    callbacks.add(callback);
  }

  public void unSubscribeCallback(IMusicServiceCallback callback) {
    callbacks.remove(callback);
  }

  public static int getStatus() {
    return status;
  }

  public static void sendMsg(Message msg) {
    if (ytMusicService == null) {
      YtApp.getInstance().getAppContext().startService(
          new Intent(YtApp.getInstance().getAppContext(), YtMusicService.class));
      handler.sendMessageDelayed(msg, 3000);
    } else {
      handler.sendMessage(msg);
    }

  }

  private static void initPlayer() {
    player.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
      @Override
      public void onPrepared(IMediaPlayer iMediaPlayer) {
        status = PLAYER_STATUS_PLAYING;
        player.start();
      }
    });
    player.setOnInfoListener(onInfoListener);
    player.setOnCompletionListener(onCompletionListener);
    player.setOnErrorListener(onErrorListener);
    player.setOnSeekCompleteListener(seekCompleteListener);
  }

  private static IMediaPlayer.OnSeekCompleteListener seekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {
    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {

    }
  };

  private static IMediaPlayer.OnInfoListener onInfoListener = new IMediaPlayer.OnInfoListener() {
    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {

      switch (i) {
        case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
          break;
        case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
          break;
        case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
          break;
        case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
          break;
        case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
          break;
        case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
          break;
        case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
          break;
        case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
          break;
        case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
          break;
        case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
          break;
        case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
          break;
        case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
          break;
      }

      return true;
    }
  };

  private static IMediaPlayer.OnCompletionListener onCompletionListener = new IMediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
      AppLog.d("onCompletion");
      if (callbacks != null) {
        for (IMusicServiceCallback c : callbacks) {
          c.onComplete();
        }
      }
      stop();
    }
  };

  private static IMediaPlayer.OnErrorListener onErrorListener = new IMediaPlayer.OnErrorListener() {
    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
      AppLog.d("Error: " + i + "," + i1);
      if (callbacks != null) {
        for (IMusicServiceCallback c : callbacks) {
          c.onError();
        }
      }
      stop();
      return true;
    }
  };

  private static Runnable seekUpdate = new Runnable() {
    @Override
    public void run() {

      if (player != null && callbacks != null) {
        for (IMusicServiceCallback c : callbacks) {
          c.onDuration(player.getCurrentPosition(), player.getDuration());
        }
        AppLog.d(player.getCurrentPosition() + " " + player.getDuration());
      }
    }
  };

  private static void start() {

    player = new IjkMediaPlayer();
    IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_VERBOSE);
    initPlayer();

    status = PLAYER_STATUS_PREPARING;
    try {
      player.setDataSource(url);
    } catch (IOException e) {
      e.printStackTrace();
    }
    player.prepareAsync();
    if (pollingDevice != null) {
      pollingDevice.startPolling(seekUpdate, 1000);
    }
  }

  private static void stop() {
    status = PLAYER_STATUS_STOPPED;
    if (player != null) {
      player.stop();
      player.release();
      player = null;
    }
    if (pollingDevice != null) {
      pollingDevice.endPolling(seekUpdate);
    }
  }


  private static Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if (ytMusicService != null) {
        switch (msg.what) {
          case START:
            switch (status) {
              case PLAYER_STATUS_PLAYING:
              case PLAYER_STATUS_PREPARING:
                return;
              case PLAYER_STATUS_STOPPED:
                if (msg.getData() != null) {
                  url = msg.getData().getString("url");
                  if (url != null) {
                    start();
                  }
                }
                break;
              case PLAYER_STATUS_PAUSED:
                if (player != null) {
                  status = PLAYER_STATUS_PLAYING;
                  player.start();
                }
                break;
              default:
                break;
            }
            break;

          case STOP:
            switch (status) {
              case PLAYER_STATUS_PLAYING:
              case PLAYER_STATUS_PREPARING:
                post(new Runnable() {
                  @Override
                  public void run() {
                    stop();
                  }
                });
                break;
              case PLAYER_STATUS_STOPPED:
                break;
              case PLAYER_STATUS_PAUSED:
                stop();
              default:
                break;
            }
            break;
          case PAUSE:
            switch (status) {
              case PLAYER_STATUS_PLAYING:
                if (player != null) {
                  status = PLAYER_STATUS_PAUSED;
                  player.pause();
                }
                break;
              case PLAYER_STATUS_PREPARING:
                post(new Runnable() {
                  @Override
                  public void run() {
                    if (player != null) {
                      status = PLAYER_STATUS_PAUSED;
                      player.pause();
                    }
                  }
                });
                break;
              case PLAYER_STATUS_STOPPED:
                break;
              case PLAYER_STATUS_PAUSED:
                break;
              default:
                break;
            }
            break;
          case JUMP:

            switch (status) {
              case PLAYER_STATUS_PLAYING:
              case PLAYER_STATUS_PAUSED:
              case PLAYER_STATUS_PREPARING:
                if (msg.getData() != null) {
                  url = msg.getData().getString("url");
                }
                post(new Runnable() {
                  @Override
                  public void run() {
                    stop();
                    if (url != null) {
                      start();
                    }
                  }
                });
                break;
              case PLAYER_STATUS_STOPPED:
                if (msg.getData() != null) {
                  url = msg.getData().getString("url");
                  if (url != null) {
                    start();
                  }
                }
                break;
              default:
                break;
            }
            break;

          case SEEK:

            switch (status) {
              case PLAYER_STATUS_PLAYING:
                if (msg.getData() != null) {
                  if (player != null) {
                    int pos = msg.getData().getInt("seek");
                    if (pos != 0 && pos < player.getDuration()) {
                      player.seekTo(pos);
                    }
                  }
                }
                break;
              case PLAYER_STATUS_PAUSED:
              case PLAYER_STATUS_PREPARING:
              case PLAYER_STATUS_STOPPED:
                break;
              default:
                break;
            }
            break;
          default:
            break;

        }

      }
    }
  };

  @Override
  public void onCreate() {
    super.onCreate();
    ytMusicService = this;
    pollingDevice = new PollingDevice(handler);

    TelephonyManager tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    tmgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    IntentFilter f = new IntentFilter();
    f.addAction(Intent.ACTION_SCREEN_OFF);

    YtApp.getInstance().getAppContext().registerReceiver(broadcastReceiver, f);

  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {

      String action = intent.getAction();
      if (action.equals(Intent.ACTION_SCREEN_OFF)) {
        if (player != null && status == PLAYER_STATUS_PLAYING) {
          Context ctx = YtApp.getInstance().getAppContext();
          Intent in = new Intent(ctx, YtMusicActivity.class);
          in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          ctx.startActivity(in);
        }
      }
    }
  };


  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  private boolean mResumeAfterCall = false;
  private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

      if (player == null) {
        return;
      }

      if (state == TelephonyManager.CALL_STATE_RINGING) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int ringVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        if (ringVolume > 0) {
          mResumeAfterCall = (status == PLAYER_STATUS_PLAYING || mResumeAfterCall);
          player.pause();
        }
      } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
        // pause the music while a conversation is in progress
        mResumeAfterCall = (status == PLAYER_STATUS_PLAYING || mResumeAfterCall);
        player.pause();
      } else if (state == TelephonyManager.CALL_STATE_IDLE) {
        // start playing again
        if (mResumeAfterCall) {
          // resume playback only if music was playing
          // when the call was answered
          player.start();
          mResumeAfterCall = false;
        }
      }
    }
  };

  @Override
  public void onDestroy() {
    super.onDestroy();
    ytMusicService = null;
    player = null;
    pollingDevice = null;

    TelephonyManager tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    tmgr.listen(mPhoneStateListener, 0);

    YtApp.getInstance().getAppContext().unregisterReceiver(broadcastReceiver);

  }
}

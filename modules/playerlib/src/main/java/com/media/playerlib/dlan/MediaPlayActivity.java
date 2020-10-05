package com.media.playerlib.dlan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.media.playerlib.R;
import com.qingfeng.clinglibrary.Intents;
import com.qingfeng.clinglibrary.control.ClingPlayControl;
import com.qingfeng.clinglibrary.control.callback.ControlCallback;
import com.qingfeng.clinglibrary.control.callback.ControlReceiveCallback;
import com.qingfeng.clinglibrary.entity.ClingDeviceList;
import com.qingfeng.clinglibrary.entity.ClingVolumeResponse;
import com.qingfeng.clinglibrary.entity.DLANPlayState;
import com.qingfeng.clinglibrary.entity.IResponse;
import com.qingfeng.clinglibrary.service.manager.ClingManager;

import org.fourthline.cling.support.model.PositionInfo;

import java.util.Timer;
import java.util.TimerTask;


public class MediaPlayActivity extends AppCompatActivity {
    private static final String TAG = "mainactiviy";
    /**
     * 连接设备状态: 播放状态
     */
    public static final int PLAY_ACTION = 0xa1;
    /**
     * 连接设备状态: 暂停状态
     */
    public static final int PAUSE_ACTION = 0xa2;
    /**
     * 连接设备状态: 停止状态
     */
    public static final int STOP_ACTION = 0xa3;
    /**
     * 连接设备状态: 转菊花状态
     */
    public static final int TRANSITIONING_ACTION = 0xa4;
    /**
     * 获取进度
     */
    public static final int EXTRA_POSITION = 0xa5;
    /**
     * 投放失败
     */
    public static final int ERROR_ACTION = 0xa6;
    /**
     * tv端播放完成
     */
    public static final int ACTION_PLAY_COMPLETE = 0xa7;

    public static final int ACTION_POSITION_CALLBACK = 0xa8;

    private TextView tvVideoName;

    private Context mContext;
    private Handler mHandler = new InnerHandler();
    private Timer timer = null;


    private boolean isPlaying = false;
    private ClingPlayControl mClingPlayControl = new ClingPlayControl();//投屏控制器
    private AppCompatSeekBar seekBar;
    private ImageView playBt;
    private TextView currTime;
    private TextView countTime;
    private long hostLength;
    private ImageView plusVolume;
    private ImageView reduceVolume;
    private int currentVolume;
    private TextView playStatus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initStatusBar();
        setContentView(R.layout.media_play_layout);
        initView();
        initListener();
        registerReceivers();
        String playUrl = getIntent().getStringExtra(DLandataInter.Key.PLAYURL);
        String playTitle = getIntent().getStringExtra(DLandataInter.Key.PLAY_TITLE);
        initData(playUrl, playTitle);
    }

    private void initStatusBar() {
        getWindow().setStatusBarColor(Color.BLACK);
        //去除状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
    }

    private void initView() {
        tvVideoName = findViewById(R.id.text_content_title);
        playBt = findViewById(R.id.img_play);
        seekBar = findViewById(R.id.seek_bar_progress);
        currTime = findViewById(R.id.text_play_time);
        countTime = findViewById(R.id.text_play_max_time);
        playStatus = findViewById(R.id.play_status);

        plusVolume = findViewById(R.id.plus_volume);
        reduceVolume = findViewById(R.id.reduce_volume);

        getVolume();
        plusVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentVolume >= 96) {
                    return;
                }
                currentVolume += 4;
                mClingPlayControl.setVolume(currentVolume, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        getVolume();
                    }

                    @Override
                    public void fail(IResponse response) {
                        getVolume();
                    }
                });
            }
        });
        reduceVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentVolume <= 4) {
                    return;
                }
                currentVolume -= 4;
                mClingPlayControl.setVolume(currentVolume, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        getVolume();
                    }

                    @Override
                    public void fail(IResponse response) {

                    }
                });
            }
        });

    }

    private void getVolume() {
        mClingPlayControl.getVolume(new ControlReceiveCallback() {
            @Override
            public void receive(IResponse response) {
                Object responseResponse = response.getResponse();
                if (responseResponse instanceof ClingVolumeResponse) {
                    ClingVolumeResponse resp = (ClingVolumeResponse) response;
                    currentVolume = resp.getResponse();
                }
            }

            @Override
            public void success(IResponse response) {

            }

            @Override
            public void fail(IResponse response) {

            }
        });
    }

    private void initData(String playUrl, String playTitle) {

        tvVideoName.setText(playTitle);
        playStatus.setText("正在缓冲...");
        playNew(playUrl);

    }

    private void initListener() {

        playBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pause();
                    playBt.setSelected(false);
                    playStatus.setText("暂停播放...");
                } else {
                    continuePlay();
                    playBt.setSelected(true);
                    playStatus.setText("正在播放");
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int currentProgress = seekBar.getProgress(); // 转为毫秒

                int progress = (int) (hostLength * 1000 * (currentProgress * 0.01f));
                mClingPlayControl.seek(progress, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        Log.e(TAG, "seek success");
                    }

                    @Override
                    public void fail(IResponse response) {
                        Log.e(TAG, "seek fail");
                    }
                });
            }
        });
//

    }


    private void playNew(String url) {
        mClingPlayControl.playNew(url, new ControlCallback() {

            @Override
            public void success(IResponse response) {
                isPlaying = true;
                playBt.setSelected(true);
                ClingManager.getInstance().registerAVTransport(mContext);
                ClingManager.getInstance().registerRenderingControl(mContext);
                endGetProgress();
                startGetProgress();
                playStatus.setText("正在播放");
            }

            @Override
            public void fail(IResponse response) {
                mHandler.sendEmptyMessage(ERROR_ACTION);
            }
        });
    }

    private void continuePlay() {
        mClingPlayControl.play(new ControlCallback() {
            @Override
            public void success(IResponse response) {
                isPlaying = true;
//                tvVideoStatus.setText("正在投屏中");
                Log.e(TAG, "play success");

            }

            @Override
            public void fail(IResponse response) {
                Log.e(TAG, "play fail");
            }
        });

    }


    /**
     * 停止
     */
    private void stop() {
        mClingPlayControl.stop(new ControlCallback() {
            @Override
            public void success(IResponse response) {
            }

            @Override
            public void fail(IResponse response) {
            }
        });
    }

    /**
     * 暂停
     */
    private void pause() {
        mClingPlayControl.pause(new ControlCallback() {
            @Override
            public void success(IResponse response) {
                isPlaying = false;
//                tvVideoStatus.setText("暂停投屏中");
            }

            @Override
            public void fail(IResponse response) {
                Log.e(TAG, "pause fail");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
//        mPlayer.release();
        mHandler.removeCallbacksAndMessages(null);
        endGetProgress();
        unregisterReceiver(TransportStateBroadcastReceiver);

        ClingManager.getInstance().destroy();
        ClingDeviceList.getInstance().destroy();
    }

    private void registerReceivers() {
        //Register play status broadcast
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intents.ACTION_PLAYING);
        filter.addAction(Intents.ACTION_PAUSED_PLAYBACK);
        filter.addAction(Intents.ACTION_STOPPED);
        filter.addAction(Intents.ACTION_TRANSITIONING);
        filter.addAction(Intents.ACTION_POSITION_CALLBACK);
        filter.addAction(Intents.ACTION_PLAY_COMPLETE);
        registerReceiver(TransportStateBroadcastReceiver, filter);
    }


    private final class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PLAY_ACTION:
                    Log.i(TAG, "Execute PLAY_ACTION");
                    Toast.makeText(mContext, "正在投放", Toast.LENGTH_SHORT).show();

                    startGetProgress();
                    mClingPlayControl.setCurrentState(DLANPlayState.PLAY);
                    break;
                case PAUSE_ACTION:
                    Log.i(TAG, "Execute PAUSE_ACTION");

                    mClingPlayControl.setCurrentState(DLANPlayState.PAUSE);
                    break;
                case STOP_ACTION:
                    Log.i(TAG, "Execute STOP_ACTION");
                    mClingPlayControl.setCurrentState(DLANPlayState.STOP);
//                    foot.ivPlay.setImageResource(R.drawable.icon_video_pause);
                    break;
                case TRANSITIONING_ACTION:
                    Log.i(TAG, "Execute TRANSITIONING_ACTION");
                    Toast.makeText(mContext, "正在连接", Toast.LENGTH_SHORT).show();
                    break;

                case ACTION_POSITION_CALLBACK:
//                    foot.setCurProgress(msg.arg1);
                    break;
                case ACTION_PLAY_COMPLETE:
                    Log.i(TAG, "Execute GET_POSITION_INFO_ACTION");
//                    ToastUtils.showLong("播放完成");
                    break;

                case ERROR_ACTION:
                    Log.e(TAG, "Execute ERROR_ACTION");
                    Toast.makeText(mContext, "投放失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void startGetProgress() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                if (mClingPlayControl != null)
                    mClingPlayControl.getPositionInfo(new ControlReceiveCallback() {
                        @Override
                        public void receive(IResponse response) {
                            Object responseResponse = response.getResponse();
                            if (responseResponse instanceof PositionInfo) {
                                final PositionInfo positionInfo = (PositionInfo) responseResponse;
                                hostLength = positionInfo.getTrackDurationSeconds();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        countTime.setText(positionInfo.getTrackDuration() + "");
                                        currTime.setText(positionInfo.getRelTime() + "");
                                        seekBar.setProgress(positionInfo.getElapsedPercent());
                                    }
                                });

                            }
                        }

                        @Override
                        public void success(IResponse response) {

                        }

                        @Override
                        public void fail(IResponse response) {

                        }
                    });

            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1, 1000);
    }

    public static int timeToSec(String time) {
        String[] timeArray = time.split(":");
        int hour = Integer.parseInt(timeArray[0]) * 3600;
        int min = Integer.parseInt(timeArray[1]) * 60;
        int sec = Integer.parseInt(timeArray[2]);
        return (hour + min + sec) * 1000;
    }


    private void endGetProgress() {
        if (timer != null)
            timer.cancel();
        timer = null;
    }

    /**
     * 接收状态改变信息
     */
    BroadcastReceiver TransportStateBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "Receive playback intent:" + action);
            if (Intents.ACTION_PLAYING.equals(action)) {
                mHandler.sendEmptyMessage(PLAY_ACTION);

            } else if (Intents.ACTION_PAUSED_PLAYBACK.equals(action)) {
                mHandler.sendEmptyMessage(PAUSE_ACTION);

            } else if (Intents.ACTION_STOPPED.equals(action)) {
                mHandler.sendEmptyMessage(STOP_ACTION);

            } else if (Intents.ACTION_TRANSITIONING.equals(action)) {
                mHandler.sendEmptyMessage(TRANSITIONING_ACTION);
            } else if (Intents.ACTION_POSITION_CALLBACK.equals(action)) {
                Message msg = Message.obtain();
                msg.what = ACTION_POSITION_CALLBACK;
                msg.arg1 = intent.getIntExtra(Intents.EXTRA_POSITION, -1);
                mHandler.sendMessage(msg);
            } else if (Intents.ACTION_PLAY_COMPLETE.equals(action)) {
                mHandler.sendEmptyMessage(ACTION_PLAY_COMPLETE);
            }
        }
    };
}
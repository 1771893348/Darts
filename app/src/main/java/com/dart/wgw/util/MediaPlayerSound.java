package com.dart.wgw.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;


/**
 * Author:Admin
 * Time:2019/4/25 10:42
 * 描述：
 */
public class MediaPlayerSound implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    public MediaPlayer mediaPlayer; // 媒体播放器
    private String[] soundArray;
    private ArrayList<AssetFileDescriptor> mAssetFileDescriptor;
    private int soundIndex = 0;
    private AssetManager mAssets;
    private SoftReference<Context> reference;
    public static final int SCORE_LEFT_SOUND = 1010;
    public static final int SCORE_BUST_SOUND = 1011;
    public static final int SCORE_GAMEOVER_SOUND = 1012;
    public static final int SCORE_VERYGOOD_SOUND = 1013;
    public static final int SCORE_WIN_SOUND = 1014;
    public static final int GAME_BJ_MUSIC = 1015;
    private MediaSoundCallBack mMediaSoundCallBack;
    public static int sexSound = 1;//1:女生 0：男生
    private String SexPath = "broadcast/score_woman_";
    private Handler mHandler;


    // 初始化播放器
    public MediaPlayerSound(Context context) {
        super();
        try {
            reference = new SoftReference<>(context);
            mHandler = new MyHandler();
            mAssets = reference.get().getAssets();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (null == mediaPlayer) return;
        mediaPlayer.start();
    }

    /**
     * @param url url地址
     */
    public void playUrl(String url) {
        if (null == mediaPlayer) return;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * url地址
     */
    @SuppressLint("NewApi")
    public void playUrl(AssetFileDescriptor afd) {
        if (null == mediaPlayer) return;
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength()); // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
            //            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void playUrl(String... url) {
            soundIndex = 0;
            soundArray = url;
            playUrl(soundArray[soundIndex]);
        }

        public void playAssertsFile(int... score) {
            if (sexSound < 0) {
                if (null != mMediaSoundCallBack) mMediaSoundCallBack.onSoundAllComplete();
                return;
            }
            soundIndex = 0;
            mAssetFileDescriptor = new ArrayList<>();
            for (int sr : score) {
                AssetFileDescriptor afd = null;
                try {
                    String path;
                    if (sexSound == 1) {
                        path = "broadcast/score_woman_" + sr + ".OGG";
                        if (sr == SCORE_LEFT_SOUND) {
                            path = "broadcast/score_woman_left.OGG";
                        }
                    } else {
                        path = "broadcast/score_man_" + sr + ".OGG";
                        if (sr == SCORE_LEFT_SOUND) {
                            path = "broadcast/score_man_left.OGG";
                        }
                    }
                    afd = mAssets.openFd(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                if (null != afd) {
                    mAssetFileDescriptor.add(afd);
                }

            }
            if (mAssetFileDescriptor.size() > soundIndex) {
                playUrl(mAssetFileDescriptor.get(soundIndex));
            } else {
                if (null != mMediaSoundCallBack) mMediaSoundCallBack.onSoundAllComplete();
            }
        }

    public void playAssertsFile(MediaSoundCallBack callBack, int... score) {
        mMediaSoundCallBack = callBack;
        if (sexSound < 0) {
            if (null != mMediaSoundCallBack) mMediaSoundCallBack.onSoundAllComplete();
            return;
        }
        soundIndex = 0;
        mAssetFileDescriptor = new ArrayList<>();

        for (int sr : score) {
            AssetFileDescriptor afd;
            try {
                String path = getAssertsPath(sr);

                afd = mAssets.openFd(path);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (null != afd) {
                mAssetFileDescriptor.add(afd);
            }
        }
        if (mAssetFileDescriptor.size() > soundIndex) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playUrl(mAssetFileDescriptor.get(soundIndex));
                }
            }, 500);

        } else {
            if (null != mMediaSoundCallBack) mMediaSoundCallBack.onSoundAllComplete();
        }
    }

    private String getAssertsPath(int sr) {
        String path = "";
        if (sexSound == 1) {
            SexPath = "broadcast/score_woman_";
        } else {
            SexPath = "broadcast/score_man_";
        }
        path = SexPath + sr + ".OGG";
        if (sr == SCORE_LEFT_SOUND) {
            path = SexPath + "left.OGG";
        } else if (sr == SCORE_BUST_SOUND) {
            path = SexPath + "bust.OGG";
        } else if (sr == SCORE_GAMEOVER_SOUND) {
            path = SexPath + "gameover.OGG";
        } else if (sr == SCORE_VERYGOOD_SOUND) {
            path = SexPath + "verygood.OGG";
        } else if (sr == SCORE_WIN_SOUND) {
            path = SexPath + "win.OGG";
        }
        return path;
    }

    // 暂停
    public void pause() {
        mediaPlayer.pause();
    }

    // 停止
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Log.e("mediaPlayer", "onPrepared");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("mediaPlayer", "onCompletion");
        soundIndex++;
        if (null != soundArray) {
            if (soundIndex < soundArray.length) {
                playUrl(soundArray[soundIndex]);
            } else {
                if (null != mMediaSoundCallBack) mMediaSoundCallBack.onSoundAllComplete();
            }

        }

        if (null != mAssetFileDescriptor) {
            if (soundIndex < mAssetFileDescriptor.size()) {
                playUrl(mAssetFileDescriptor.get(soundIndex));
            } else {
                if (null != mMediaSoundCallBack) mMediaSoundCallBack.onSoundAllComplete();
            }

        }

    }

    /**
     * 缓冲更新
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("wgw_onError", "=====" + what + "-----" + extra);
        if (null != mMediaSoundCallBack) mMediaSoundCallBack.onSoundAllComplete();
        return false;
    }

    public interface MediaSoundCallBack {
        void onSoundAllComplete();
    }

    public class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
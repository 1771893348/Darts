package com.dart.wgw.module.dartsround

import android.app.Activity
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import com.dart.wgw.R
import com.dart.wgw.util.MediaPlayerSound
import com.dart.wgw.view.DartTarget
import java.io.IOException
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Author:Admin
 * Time:2019/5/30 10:12
 * 描述：
 */

class DartsRoundGame: Activity() {
    private var dart_target: DartTarget? = null
    private var mRandom: Random? = null
    private val scoreArea = arrayOf(
        "20",
        "1",
        "18",
        "4",
        "13",
        "6",
        "10",
        "15",
        "2",
        "17",
        "3",
        "19",
        "7",
        "16",
        "8",
        "11",
        "14",
        "9",
        "12",
        "5",
        "25",
        "50",
        "0"
    )
    private val scoreMultiple = arrayOf("a", "b", "c", "d")
    private var mThread: MyThread? = null
    private var mMediaPlayerSound: MediaPlayerSound? = null
    lateinit var mediaPlayer: MediaPlayer // 媒体播放器
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dartsround)
        mRandom = Random()
        dart_target = findViewById<View>(R.id.dart_target) as DartTarget
        mThread = MyThread()
        mMediaPlayerSound = MediaPlayerSound(this)
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)// 设置媒体流类型
        dart_target!!.setOnClickListener(View.OnClickListener {
            //                dart_target.setHighlight("25","",0);
            if (!mThread!!.threadState) {
                mThread!!.threadState = true
                mThread!!.start()
                var afd: AssetFileDescriptor? = null
                try {
                    afd = assets.openFd("broadcast/bj_music.MP3")
                    mediaPlayer.reset()
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.setDataSource(afd!!.fileDescriptor, afd.startOffset, afd.length)
                    mediaPlayer.prepare()
                    mediaPlayer.isLooping = true
                    mediaPlayer.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            //                else {
            //                    mThread.stopLight();
            //                }
            //                dart_target.setHighlight("0","a");
        })
    }

    private inner class MyThread : Thread() {
        private val mQuit = AtomicBoolean(false)
        internal var indexCount = 0
        internal var mScore = "0"
        internal var mTimes = "0"
        var threadState: Boolean
            get() = mQuit.get()
            set(state) = mQuit.set(state)

        fun stopLight() {
            mQuit.set(false)
        }

        override fun run() {
            super.run()
            while (mQuit.get()) {
                val index = mRandom!!.nextInt(scoreArea.size)
                val indexMul = mRandom!!.nextInt(scoreMultiple.size)
                mScore = scoreArea[index]
                mTimes = scoreMultiple[indexMul]
                dart_target!!.setHighlight(mScore, mTimes, 0)
                indexCount++
                if (indexCount > 38) {
                    stopLight()
                }
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
            mediaPlayer.stop()
            var sore = Integer.parseInt(mScore)
            indexCount = 0
            if (sore % 25 != 0) {
                when (mTimes) {
                    "b" -> sore = sore * 3
                    "d" -> sore = sore * 2
                }
            }

            mMediaPlayerSound!!.playAssertsFile(object : MediaPlayerSound.MediaSoundCallBack {
                override fun onSoundAllComplete() {

                }
            }, sore)
        }
    };
}
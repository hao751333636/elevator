package com.sinodom.elevator.single;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.sinodom.elevator.R;

public class SoundPoolManager {
    // SoundPool对象
    public static SoundPool mSoundPlayer = new SoundPool(1,
            AudioManager.STREAM_SYSTEM, 5);
    public static SoundPoolManager soundPlayUtils;
    // 上下文
    static Context mContext;

    public static SoundPool getSoundPool() {
        return mSoundPlayer;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static SoundPoolManager init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPoolManager();
        }

        // 初始化声音
        mContext = context;

        mSoundPlayer.load(mContext, R.raw.sound0, 1);// 1
        mSoundPlayer.load(mContext, R.raw.sound1, 1);// 2
        mSoundPlayer.load(mContext, R.raw.sound2, 1);// 3
        mSoundPlayer.load(mContext, R.raw.sound3, 1);// 4
        mSoundPlayer.load(mContext, R.raw.avchat_ring, 1);// 5

        return soundPlayUtils;
    }

    /**
     * 播放声音
     *
     * @param soundID
     */
    public static void play(int soundID) {
        mSoundPlayer.play(soundID, 1, 1, 0, 0, 1);
    }
}

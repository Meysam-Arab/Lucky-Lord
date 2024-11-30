package ir.fardan7eghlim.luckylord.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.SessionModel;

/**
 * Created by Meysam on 07/09/2017.
 */

public class SoundFXModel {

    public static Integer  CORRECT= 0;
    public static Integer  WRONG= 1;
    public static Integer  CLICKED= 2;
    public static Integer  NOTIFY= 3;

    private SoundPool mSoundPool;
    private int soundIds[];

    public SoundFXModel(Context cntx)
    {
        initialize(cntx);
    }

    public void initialize(Context cntx)
    {
        soundIds = new int[4];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .build();
        }
        else {
            mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }

//        initializeSoundIds(cntx);
//
        soundIds[CORRECT] = mSoundPool.load(cntx, R.raw.correct_answer, 1);
        soundIds[WRONG] = mSoundPool.load(cntx, R.raw.wrong_answer, 1);
        soundIds[CLICKED] = mSoundPool.load(cntx, R.raw.button_click, 1);
        soundIds[NOTIFY] = mSoundPool.load(cntx, R.raw.dialog_sound, 1);
    }


    public void play(int sound, Context cntx)
    {
        if(!new SessionModel(cntx).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
        {
            if(soundIds == null || mSoundPool == null)
                initialize(cntx);
            int streamID = mSoundPool.play(soundIds[sound], 1, 1, 1, 0, 1);
            mSoundPool.setLoop(streamID,0);
        }

    }

    public void releaseSoundPool()
    {
        if(mSoundPool != null)
        {
            mSoundPool.release();
            mSoundPool = null;
        }

        soundIds = null;
    }

}

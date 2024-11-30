package ir.fardan7eghlim.luckylord.utils;

/**
 * Created by Meysam on 26/07/2017.
 */

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.models.SessionModel;

public class SoundModel {

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    private Context cntx;
    private static MediaPlayer mPlayer;

    public static String IS_PLAYING = "playing";
    public static String STOPPED = "stopped";

    private static boolean specific_sound_playing = false;
//    private static Thread thread;
//    private static Thread thread;

    public SoundModel(Context contex)
    {
        cntx = contex;
    }

    public void playRandomMusic()
    {
        if(!new SessionModel(cntx).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED)) {

//            thread = new Thread()
//            {
//                @Override
//                public void run() {
//                    if(!Thread.currentThread().interrupted())
//                    {
//                        //Do something
////                        mPlayer = MediaPlayer.create(cntx, getRandomFile());
////                        mPlayer.setLooping(true);
////                        mPlayer.start();
//
//                        mPlayer = MediaPlayer.create(cntx.getApplicationContext(), getRandomFile());
//
//                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
//                        {
//                            @Override
//                            public void onCompletion(MediaPlayer mp)
//                            {
//                                // Code to start the next audio in the sequence
//                                if(!new SessionModel(cntx.getApplicationContext()).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
//                                {
//                                    mPlayer.release();
//                                    mPlayer = MediaPlayer.create(cntx.getApplicationContext(), getRandomFile());
//
//                                    mPlayer.setOnCompletionListener(this);
////                            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////                                @Override
////                                public void onPrepared(MediaPlayer mp) {
////                                    mPlayer.start();
////                                }
////                            });
//                                    mPlayer.start();
//
//                                }
//
//                            }
//                        });
//                        mPlayer.start();
//                    }
//                    else
//                    {
//                        if(mPlayer != null && mPlayer.isPlaying())
//                        {
//                            mPlayer.stop();
//                            mPlayer.release();
//                        }
//
//                    }
//
//                }
//
//                public void cancel() { interrupt(); }
//
//            };
//
//            if(mPlayer == null || !mPlayer.isPlaying())
//            {
//
//                thread.start();
//
//            }
//            if(mPlayer != null && mPlayer.isPlaying())
//            {
//                thread.interrupt();
//                mPlayer.stop();
//                mPlayer.release();
//                thread.start();
//
//            }





//        thread.start();

//                mPlayer = MediaPlayer.create(cntx.getApplicationContext(), getRandomFile());
//                mPlayer.setLooping(true);
//                mPlayer.start();
        }

    }

    public static void stopMusic()
    {
        if(mPlayer != null)
        {
            if(mPlayer.isPlaying())
            {
//                thread.stop();
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }
        }
    }

    private int getRandomFile()
    {
        ArrayList<Integer> files = new ArrayList<Integer>();
        files.add(R.raw.build_1);
        files.add(R.raw.build_2);
        files.add(R.raw.buy_1);
        files.add(R.raw.buy_2);
        files.add(R.raw.sj_1);
        files.add(R.raw.sj_2);

        Random r = new Random();
        int i1 = r.nextInt(files.size());

//        int i1 = 3;
        return files.get(i1);

    }

    public void playCountinuseRandomMusic()
    {
        if(!new SessionModel(cntx).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
        {
            if( mPlayer == null || !mPlayer.isPlaying() || specific_sound_playing)
            {
                specific_sound_playing = false;
                mPlayer = MediaPlayer.create(cntx.getApplicationContext(), getRandomFile());

                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        // Code to start the next audio in the sequence
                        if(!new SessionModel(cntx.getApplicationContext()).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
                        {
                            mPlayer.release();
                            mPlayer = MediaPlayer.create(cntx.getApplicationContext(), getRandomFile());

                            mPlayer.setOnCompletionListener(this);
//                            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                @Override
//                                public void onPrepared(MediaPlayer mp) {
//                                    mPlayer.start();
//                                }
//                            });
                            mPlayer.start();

                        }

                    }
                });
//                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mPlayer.start();
//                    }
//                });
//                mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                    @Override
//                    public boolean onError(MediaPlayer mp, int what, int extra) {
//                        return false;
//                    }
//                });

//            mPlayer.setLooping(true);

                mPlayer.start();
            }
        }



    }

    public static boolean isPlaying()
    {
        if(mPlayer == null)
            return false;
        if(mPlayer.isPlaying())
            return true;
        return false;
    }

    public static void playSpecificSound(int source, final Context mcntx, boolean continueAfterStop, final Integer afterStopSound, final Float afterStopVolume, Float volume)
    {
        if(mPlayer != null)
        {

            if(mPlayer.isPlaying()){
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
//                thread.interrupt();

            }

        }

        if(!new SessionModel(mcntx).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
        {
            if(continueAfterStop)
            {
                specific_sound_playing = true;
                mPlayer = MediaPlayer.create(mcntx.getApplicationContext(), source);
                mPlayer.setLooping(true);
                if(volume != null)
                    mPlayer.setVolume(volume,volume);
                mPlayer.start();

            }
            else
            {
                specific_sound_playing = true;

                mPlayer = MediaPlayer.create(mcntx.getApplicationContext(), source);
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        // Code to start the next audio in the sequence
                        if(!new SessionModel(mcntx.getApplicationContext()).getStringItem(SessionModel.KEY_MUSIC_PLAY).equals(SoundModel.STOPPED))
                        {
                            specific_sound_playing = false;
                            if(mPlayer != null)
                            {
                                mPlayer.stop();
                                mPlayer.release();
                                mPlayer = null;
                            }

//                            new SoundModel(mcntx).playCountinuseRandomMusic();
                            if(afterStopSound != null)
                            {
//                                if(BaseActivity.bgSound != null)
//                                    BaseActivity.bgSound.playCountinuseRandomMusic();
                                playSpecificSound(afterStopSound,mcntx,true,null,null,afterStopVolume);

                            }
//                            else
//                                playSpecificSound(afterStopSound,mcntx,true,null,null,afterStopVolume);


                        }

                    }
                });
                mPlayer.setLooping(false);
                if(volume != null)
                    mPlayer.setVolume(volume,volume);
                mPlayer.start();
            }

        }
    }

    public static void stopSpecificSound()
    {
        specific_sound_playing = false;
        if(mPlayer != null)
        {
            mPlayer.setLooping(false);
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }

    }



}

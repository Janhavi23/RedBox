package com.example.red_box;

import android.app.Service;
import android.content.Intent;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;


public class Fingerprint  {

    /*VoiceProvider tts = new VoiceProvider("0179b86737564589983ee198a829d646");


    {
        VoiceParameters params = new VoiceParameters("this is a java programme to convert speech to text", Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = new byte[0];
        try {
            voice = tts.speech(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("voice.mp3");
            fos.write(voice,0,voice.length);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/





}
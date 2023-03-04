package com.example.aut2_03;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class TerceraActivity extends AppCompatActivity {

    private SideNavBar sideNavBar;

    private MediaRecorder recorder;
    private String fileName;
    private Button recordButton;
    private boolean isRecording = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 201;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercera);

        // Menu lateral de la app
        sideNavBar = new SideNavBar();
        sideNavBar.createSideNavBar(this);

        // Código de la grabadora

        // Comprobamos los permisos para grabar audio y acceder al almacenamiento
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
        }

        // Configuramos el botón de grabación
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio_recorder.3gp";
        recordButton = findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording(recorder);
                } else {
                    startRecording();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION:
            case REQUEST_READ_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "No se pueden grabar audio ni acceder al almacenamiento sin permisos", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
            recordButton.setText("Detener grabación");
        } catch (IOException e) {
            Log.e("AudioRecorder", "Error al grabar el audio");
        }
    }

    private void stopRecording(MediaRecorder recorder) {
        if (recorder != null) {
            try {
                recorder.stop();
            } catch (RuntimeException ex) {
                // En caso de error al parar la grabación
                Log.e("AudioRecorder", "Error al parar la grabación");
            } finally {
                recorder.release();
                recorder = null;
            }
        }
        stopButton.setEnabled(false);
        playButton.setEnabled(true);
    }
}

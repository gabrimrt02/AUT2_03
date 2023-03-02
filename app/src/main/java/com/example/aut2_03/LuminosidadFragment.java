package com.example.aut2_03;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_LIGHT;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LuminosidadFragment extends Fragment {

    // Ints de los rangos de los valores de lx
    private final int MINIMO = 100;
    private final int MAXIMO = 2000;

    // Textos
    private String OSCURO;
    private String NORMAL;
    private String BRILLANTE;

    private float valormax;

    // Atributos del sensor
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;

    // Labels
    private TextView cantidadLuxTextview;
    private TextView luminosidadTextView;

    public LuminosidadFragment() {
        // Required empty public constructor
    }

    public static LuminosidadFragment newInstance(String param1, String param2) {
        LuminosidadFragment fragment = new LuminosidadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_luminosidad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recuperación de los campos de texto
        cantidadLuxTextview = (TextView) getView().findViewById(R.id.lum_cantidad_label);
        luminosidadTextView = (TextView) getView().findViewById(R.id.lum_luminosidad_label);

        // Recuperacion de los Strings
        OSCURO = getString(R.string.lum_oscuro);
        NORMAL = getString(R.string.lum_normal);
        BRILLANTE = getString(R.string.lum_brillante);

        // Código para recuperar los valores del sensor

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        }
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(getContext(), "El dispositivo no tiene sensor de luz!", Toast.LENGTH_SHORT).show();
        }
        valormax = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                double value = sensorEvent.values[0];

                if(value < MINIMO){
                    luminosidadTextView.setText("Luminosidad: " + String.valueOf(value) + " lx\nDARK");
                }else if(value >= MINIMO && value < MAXIMO){
                    luminosidadTextView.setText("Luminosidad: " + String.valueOf(value) + " lx\nMEDIUM");
                }else if(value >= MAXIMO){
                    luminosidadTextView.setText("Luminosidad: " + String.valueOf(value) + " lx\nBRIGHT");
                }

                int newValue = (int) (255f * value / valormax);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }

    private void changeLabel(float valor, String texto) {
        cantidadLuxTextview.setText("" + valor + " lux");
        luminosidadTextView.setText(texto);
    }

}
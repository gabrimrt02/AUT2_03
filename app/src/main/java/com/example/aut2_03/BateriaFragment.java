package com.example.aut2_03;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// TODO Implementar fragment
public class BateriaFragment extends Fragment {

    // Textos estado carga
    String AC;
    String USB;
    String INALAMBRICA;
    String CARGA;
    String NO_CARGA;

    public BateriaFragment() {
        // Required empty public constructor
    }

    public static BateriaFragment newInstance(String param1, String param2) {
        BateriaFragment fragment = new BateriaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bateria, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AC = getString(R.string.carga_ac);
        USB = getString(R.string.carga_usb);
        INALAMBRICA = getString(R.string.carga_inalambrica);
        CARGA = getString(R.string.cargando);
        NO_CARGA = getString(R.string.no_carga);

        // Recuperación de los campos de texto donde se introducen los datos
        TextView estadoTextView = (TextView) view.findViewById(R.id.bat_estado_label);
        TextView capacidadTextView = (TextView) view.findViewById(R.id.bat_capacidad_label);
        TextView porcentajeTextView = (TextView) view.findViewById(R.id.bat_porcentaje_label);

        // Declaración del objeto BatteryManager para obtener los datos de la batería
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getContext().registerReceiver(null, ifilter);

        BatteryManager batteryManager = (BatteryManager) getContext().
                getSystemService(Context.BATTERY_SERVICE);

        // Asinación del estado de la batería
        estadoTextView.setText(getEstadoBatería(batteryStatus));

        // Asignación de la capaciadad total de la batería
        capacidadTextView.setText(getCapacidadMaxima(batteryManager));

        // Asignación del porcentaje de la batería
        porcentajeTextView.setText(getPorcentajeCarga(batteryStatus));
    }

    private String getEstadoBatería(Intent batteryStatus) {
        String textoEstadoCarga;

        // ¿La batería está cargando?
        int estado = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean cargando = estado == BatteryManager.BATTERY_STATUS_CHARGING ||
                estado == BatteryManager.BATTERY_STATUS_FULL;

        // ¿Cómo está cargando la batería?
        int conexionCarga = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean cargaUSB = conexionCarga == BatteryManager.BATTERY_PLUGGED_USB;
        boolean cargaAC = conexionCarga == BatteryManager.BATTERY_PLUGGED_AC;
        boolean cargaInalambrica = conexionCarga == BatteryManager.BATTERY_PLUGGED_WIRELESS;

        // Asignamos el texto del modo de carga
        if(!cargando)
            textoEstadoCarga = NO_CARGA;
        else {
            textoEstadoCarga = CARGA;
            if(cargaUSB)
                textoEstadoCarga += USB;
            else if(cargaAC)
                textoEstadoCarga += AC;
            else if(cargaInalambrica)
                textoEstadoCarga += INALAMBRICA;
        }

        return textoEstadoCarga;
    }

    private String getCapacidadMaxima(BatteryManager batteryManager) {
        // Recuperamos la capacidad máxima de la bateria en mAh
        int contadorCarga = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        int capacidad = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        int capacidadTotal = (contadorCarga/capacidad) * 100;

        return capacidadTotal + " mAh";
    }

    private String getPorcentajeCarga(Intent batteryStatus) {
        // Determinamos el porcentaje de batería restante
        int nivel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int escala = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE,-1);

        float porcentajeBateria = nivel * 100 / (float) escala;

        return porcentajeBateria + "%";
    }
}
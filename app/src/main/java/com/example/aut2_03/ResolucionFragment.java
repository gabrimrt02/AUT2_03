package com.example.aut2_03;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

// TODO Implementar el fragment
public class ResolucionFragment extends Fragment {

    // Atributos privados
    private int alturaPantalla;
    private int anchoPantalla;

    // Labels donde se escribirán los datos
    private TextView altoTextView;
    private TextView anchoTextView;
    private TextView verticalTextView;
    private TextView apaisadoTextView;

    public ResolucionFragment() {
        // Required empty public constructor
    }

    public static ResolucionFragment newInstance(String param1, String param2) {
        ResolucionFragment fragment = new ResolucionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resolucion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recuperación de los datos de la pantalla
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        alturaPantalla = displayMetrics.heightPixels;
        anchoPantalla = displayMetrics.widthPixels;

        // Recuperación de los TextView donde se colocarán los datos
        altoTextView = (TextView) view.findViewById(R.id.res_alto_label);
        anchoTextView = (TextView) view.findViewById(R.id.res_ancho_label);
        verticalTextView = (TextView) view.findViewById(R.id.res_vertical_label);
        apaisadoTextView = (TextView) view.findViewById(R.id.res_apaisado_label);

        // Asignación de los datos en la interfaz gráfica
        altoTextView.setText(String.format("%d px", alturaPantalla));
        anchoTextView.setText(String.format("%d px", anchoPantalla));
        verticalTextView.setText(String.format("%d px X %d px", anchoPantalla, alturaPantalla));
        apaisadoTextView.setText(String.format("%d px X %d px", alturaPantalla, anchoPantalla));
    }
}
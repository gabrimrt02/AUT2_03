package com.example.aut2_03;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocalizacionFragment extends Fragment {

    // TextViews de la interfaz
    private TextView latitudTextView;
    private TextView longitudTextView;
    private TextView altitudTextView;
    private TextView rumboTextView;

    // Atributos necesarios para poder localizar el dispositivo
    LocationManager locationManager;
    Location location;

    public LocalizacionFragment() {
        // Required empty public constructor
    }

    public static LocalizacionFragment newInstance(String param1, String param2) {
        LocalizacionFragment fragment = new LocalizacionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_localizacion, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recuperamos los textview
        latitudTextView = (TextView) getView().findViewById(R.id.loc_latitud_label);
        longitudTextView = (TextView) getView().findViewById(R.id.loc_longitud_label);
        altitudTextView = (TextView) getView().findViewById(R.id.loc_altitud_label);
        rumboTextView = (TextView) getView().findViewById(R.id.loc_rumbo_label);

        // Recuperamos el LocationManager
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // Verificar si se tienen permisos para acceder a la ubicación
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permisos si no se tienen
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Recuperamos la última ubicación conocida del dispositivo
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(location != null) {
            actualizarUI(location);
        }

        // Listener que actualizará las etiquetas siempre que se cambie la ubicación
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                actualizarUI(location);
            }
        };

        // Actualizar la ubicación cada 10 segundos o cuando el dispositivo se mueva más de 10 metros
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, locationListener);
    }

    // Función que actualiza los campos de texto de la interfaz
    private void actualizarUI(Location location) {

        latitudTextView.setText("" + location.getLatitude());
        longitudTextView.setText("" + location.getLongitude());
        altitudTextView.setText("" + location.getAltitude());
        rumboTextView.setText("" + location.getBearing());

    }
}
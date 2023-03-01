package com.example.aut2_03;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SideNavBar extends AppCompatActivity {

    // Atributos del Navigator Drawer
    private DrawerLayout drawerLayout;
    private static MenuItem ultimoElegido;
    private Activity activity;

    public void createSideNavBar(Activity activity) {
        this.activity = activity;

        // Codigo del Navigator Drawer
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        NavigationView navigator = (NavigationView) activity.findViewById(R.id.navigator_view);

        // Escuchador para cada vez que se elige uno de los elementos del navigator
        navigator.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_primera_activity:
                        if (ultimoElegido != null && ultimoElegido.getItemId() ==
                                R.id.menu_primera_activity)
                            Toast.makeText(activity, R.string.already_act,
                                    Toast.LENGTH_LONG).show();
                        else
                            activity.startActivity(new Intent(activity, PrimeraActivity.class));
                        break;
                    case R.id.menu_segunda_activity:
                        if (ultimoElegido != null && ultimoElegido.getItemId() ==
                                R.id.menu_segunda_activity)
                            Toast.makeText(activity, R.string.already_act,
                                    Toast.LENGTH_LONG).show();
                        else
                            activity.startActivity(new Intent(activity, SegundaActivity.class));
                        break;
                    case R.id.menu_tercera_acivity:
                        if (ultimoElegido != null && ultimoElegido.getItemId() ==
                                R.id.menu_tercera_acivity)
                            Toast.makeText(activity, R.string.already_act,
                                    Toast.LENGTH_LONG).show();
                        else
                            activity.startActivity(new Intent(activity, TerceraActivity.class));
                        break;
                    case R.id.menu_cuarta_activity:
                        if (ultimoElegido != null && ultimoElegido.getItemId() ==
                                R.id.menu_cuarta_activity)
                            Toast.makeText(activity, R.string.already_act,
                                    Toast.LENGTH_LONG).show();
                        else
                            activity.startActivity(new Intent(activity, CuartaActivity.class));
                        break;
                }

                // Cambiamos el estado del ultimo elegido y marcamos el que se acaba de pulsar
                if (ultimoElegido != null)
                    ultimoElegido.setChecked(false);
                item.setChecked(true);
                ultimoElegido = item;
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}




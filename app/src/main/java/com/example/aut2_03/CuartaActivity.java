package com.example.aut2_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class CuartaActivity extends AppCompatActivity {

    private SideNavBar sideNavBar;

    private TabLayout tabs;
    private Fragment resolucionFragment;
    private Fragment bateriaFragment;
    private Fragment luminosidadFragment;
    private Fragment localizacionFragment;

    // Atributos primer fragment
    private DisplayMetrics dm;
    static int alturaPantalla;
    static int anchoPantalla;
    private TextView altoTextView;
    private TextView anchoTextView;
    private TextView verticalTextView;
    private TextView apaisadoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuarta);

        sideNavBar = new SideNavBar();
        sideNavBar.createSideNavBar(this);

        // Recuperamos los fragments para cargarlos en los Tabs
        resolucionFragment = new ResolucionFragment();
        bateriaFragment = new BateriaFragment();
        luminosidadFragment = new LuminosidadFragment();
        localizacionFragment = new LocalizacionFragment();

        // Creamos las nuevas pestañas y les asignamos un icono
        tabs = (TabLayout) findViewById(R.id.tabs_cuarta);
        tabs.addTab(tabs.newTab().setText(R.string.tab_resolucion));
        tabs.addTab(tabs.newTab().setText(R.string.tab_bateria));
        tabs.addTab(tabs.newTab().setText(R.string.tab_luminosidad));
        tabs.addTab(tabs.newTab().setIcon(android.R.drawable.ic_menu_mylocation));

        // Cargamos el fragment de la primera pestaña
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_cuarta_act, resolucionFragment).commit();

        // Que debe de hacer el programa al pulsar cada una de las pestañas
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tabs.getSelectedTabPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenedor_cuarta_act, resolucionFragment).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenedor_cuarta_act, bateriaFragment).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenedor_cuarta_act, luminosidadFragment).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenedor_cuarta_act, localizacionFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Vacío porque no hace nada
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(CuartaActivity.this,
                                R.string.already_tab, Toast.LENGTH_LONG).show();
            }
        });
    }

}
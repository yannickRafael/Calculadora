package yannick.pdm.calculadora.interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import yannick.pdm.calculadora.R;
import yannick.pdm.calculadora.databinding.ActivityMainBinding;
import yannick.pdm.calculadora.interfaces.fragments.CalculadoraBasicaFragment;
import yannick.pdm.calculadora.interfaces.fragments.ConversorMoedasFragment;
import yannick.pdm.calculadora.interfaces.fragments.ConversorUnidadesFragment;
import yannick.pdm.calculadora.interfaces.fragments.GraficosFragment;
import yannick.pdm.calculadora.interfaces.fragments.InfoFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setNavigationItemSelectedListener(MainActivity.this);
        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                binding.drawer,
                binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalculadoraBasicaFragment()).commit();
            binding.navView.setCheckedItem(R.id.nav_calculadora_basica);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (R.id.nav_calculadora_basica==item.getItemId()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalculadoraBasicaFragment()).commit();
            binding.navView.setCheckedItem(R.id.nav_calculadora_basica);
        } else if (R.id.nav_conversor_moedas==item.getItemId()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConversorMoedasFragment()).commit();
            binding.navView.setCheckedItem(R.id.nav_conversor_moedas);
        } else if (R.id.nav_conversor_unidades==item.getItemId()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConversorUnidadesFragment()).commit();
            binding.navView.setCheckedItem(R.id.nav_conversor_unidades);
        } else if (R.id.nav_graficos==item.getItemId()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GraficosFragment()).commit();
            binding.navView.setCheckedItem(R.id.nav_graficos);
        } else if (R.id.nav_info==item.getItemId()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();
            binding.navView.setCheckedItem(R.id.nav_info);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalculadoraBasicaFragment()).commit();
        }

        binding.drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
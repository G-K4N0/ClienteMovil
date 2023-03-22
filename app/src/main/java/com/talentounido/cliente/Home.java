package com.talentounido.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fragmentos.FragmentInicio;
import fragmentos.FragmentLaboratorios;
import fragmentos.FragmentMas;
import fragmentos.FragmentReportes;

public class Home extends AppCompatActivity {

    FragmentInicio fragmentInicio = new FragmentInicio();
    FragmentLaboratorios fragmentLaboratorios = new FragmentLaboratorios();
    FragmentReportes fragmentReportes = new FragmentReportes();
    FragmentMas fragmentMas = new FragmentMas();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnItemSelectedListener (mOnNavigationListener);
        loadFragment(fragmentInicio);
    }
    private final NavigationBarView.OnItemSelectedListener  mOnNavigationListener = item -> {
        switch (item.getItemId())
        {
            case R.id.fragmentInicio:
                loadFragment(fragmentInicio);
                return true;
            case R.id.fragmentLaboratorios:
                loadFragment(fragmentLaboratorios);
                return true;
            case R.id.fragmentReportes:
                loadFragment(fragmentReportes);
                return true;
            case R.id.fragmentMas:
                loadFragment(fragmentMas);
                return true;
        }
        return false;
    };
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer,fragment);
        transaction.commit();
    }
}
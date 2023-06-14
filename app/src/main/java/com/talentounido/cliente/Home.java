package com.talentounido.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.talentounido.cliente.fragmentos.FragmentAviso;
import com.talentounido.cliente.fragmentos.FragmentInicio;
import com.talentounido.cliente.fragmentos.FragmentLaboratorios;
import com.talentounido.cliente.fragmentos.FragmentMas;
import com.talentounido.cliente.fragmentos.FragmentReportes;

public class Home extends AppCompatActivity implements  FragmentLaboratorios.OnFragmentInteractionListener {

    FragmentInicio fragmentInicio = new FragmentInicio();
    FragmentLaboratorios fragmentLaboratorios = new FragmentLaboratorios();
    FragmentReportes fragmentReportes = new FragmentReportes();
    FragmentAviso fragmentAviso = new FragmentAviso();
    FragmentMas fragmentMas = new FragmentMas();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.naranja)));
        navigation.setOnItemSelectedListener (mOnNavigationListener);
        loadFragment(fragmentInicio);
    }
    @SuppressLint("NonConstantResourceId")
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
            case R.id.fragmentAviso:
                loadFragment(fragmentAviso);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
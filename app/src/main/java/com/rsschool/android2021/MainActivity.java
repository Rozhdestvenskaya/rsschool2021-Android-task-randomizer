package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentNavigator {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        // TODO: invoke function which apply changes of the transaction
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        // TODO: implement it
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment, SecondFragment.TAG);
        transaction.commit();
    }

    @Override
    public void moveForward(Bundle bundle) {
        int min = bundle.getInt(FirstFragment.MIN_VALUE_KEY);
        int max = bundle.getInt(FirstFragment.MAX_VALUE_KEY);
        openSecondFragment(min, max);
    }

    @Override
    public void moveBack(Bundle bundle) {
        int previousNumber = bundle.getInt(SecondFragment.PREVIOUS_RESULT_KEY);
        openFirstFragment(previousNumber);
    }

    @Override
    public void onBackPressed() {
        SecondFragment secondFragment = (SecondFragment) getSupportFragmentManager().findFragmentByTag(SecondFragment.TAG);
        if(secondFragment != null && secondFragment.isVisible()) secondFragment.moveBack();
        else super.onBackPressed();
    }
}

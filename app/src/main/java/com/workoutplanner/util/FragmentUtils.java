package com.workoutplanner.util;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentUtils {
    public static void openAsReplace(AppCompatActivity activity, int id, Fragment fragment) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void openAsDialog(AppCompatActivity activity, DialogFragment dlg) {
        FragmentManager fm = activity.getSupportFragmentManager();
        dlg.show(fm,  "Fragment_tag");
    }
}

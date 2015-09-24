package com.freeman.example.apitest.actionbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.freeman.example.apitest.R;
import com.freeman.example.apitest.appcompatdialog.DialogAppCompatTest;

/**
 * Created by freeman on 9/22/15.
 */
public class ActivityActionBarTest extends AppCompatActivity {

    private ActionBar actionBar = null;

    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_test_layout);

        actionBar = getSupportActionBar();

//        mHandler.postDelayed(hideRunnable, 2000);
    }

    private final Runnable showRunnable = new Runnable() {
        @Override
        public void run() {
            if (actionBar != null) {
                actionBar.show();
                mHandler.postDelayed(hideRunnable, 2000);
            }
        }
    };

    private final Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            if (actionBar != null) {
                actionBar.hide();
                mHandler.postDelayed(showRunnable, 2000);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_action_bar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                showToast("onMenuItemActionExpand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                showToast("onMenuItemActionCollapse");
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                showToast("Search");
                return true;
            case R.id.action_compose:
                showToast("Compose");
                showAppCompatDialog();
//                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showToast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void showAppCompatDialog () {
        /*DialogAppCompatTest dialog = new DialogAppCompatTest(this);
        dialog.setTitle("AppCompatDialog");
        dialog.show();*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("AppCompatAlertDialog")
                .setMessage("CompatMessage")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null);
        builder.create().show();

    }

    private void showDialog () {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
                .setTitle("AppCompatAlertDialog")
                .setMessage("CompatMessage")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null);
        builder.create().show();
    }
}

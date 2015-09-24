package com.freeman.example.apitest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.freeman.example.apitest.R;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Created by freeman on 15/9/10.
 */
public class ActivityDialogTest extends Activity {

    Random random;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator_test_layout);

        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHandler.sendEmptyMessageDelayed(MSG_SHOW1, 3000);
                myHandler.sendEmptyMessageDelayed(MSG_SHOW2, 1000);
            }
        });

        this.addContentView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        random = new Random();

        testSplit();

    }

    private final MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<ActivityDialogTest> activityRef = null;
        public MyHandler (ActivityDialogTest dialogTest) {
            activityRef = new WeakReference<ActivityDialogTest>(dialogTest);
        }

        @Override
        public void handleMessage(Message msg) {
            if (activityRef.get() != null) {
                activityRef.get().handleMessage(msg);
            }
        }
    }


    private static final int MSG_SHOW1 = 1;
    private static final int MSG_SHOW2 = 2;

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_SHOW1:
                showDialog1();
                break;
            case MSG_SHOW2:
                showDialog2();
                break;
        }
    }



    private void showDialog1 () {
        final Dialog dialog1 = new Dialog (this);
        dialog1.setTitle("Dialog1");
        Window window = dialog1.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        window.setAttributes(layoutParams);

        dialog1.show();
    }

    private void showDialog2 () {
        final Dialog dialog1 = new Dialog (this);
        dialog1.setTitle("Dialog2");

        Window window = dialog1.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
        window.setAttributes(layoutParams);

        dialog1.show();
    }

    private void testSplit () {
        String originalString = "Today\\'s 10 people draw, sent 5 iPhone6s";
        int index1 = originalString.indexOf("s");
        int index2 = originalString.indexOf("people");
        int index3 = originalString.indexOf("sent");
        System.out.println(String.format("index1=%d, index2=%d, index3=%d", index1, index2, index3));
    }


}
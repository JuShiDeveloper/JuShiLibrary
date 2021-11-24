package com.jushi.library.crowdout;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.jushi.library.R;

/**
 * 被踢下线弹窗
 */
public class CrowdOutDialogActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.crowd_out_dialog_title))
                .setMessage(getString(R.string.crowd_out_diolg_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.i_know), (dialog, which) -> logout()).show();
    }

    private void logout() {
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}

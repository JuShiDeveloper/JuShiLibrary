package com.jushi.library.crash;

import android.content.Intent;
import android.widget.TextView;

import com.jushi.library.BuildConfig;
import com.jushi.library.R;
import com.jushi.library.base.BaseApplication;
import com.jushi.library.base.BaseFragmentActivity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * 崩溃日志显示界面
 *
 */
public class ExceptionInfoActivity extends BaseFragmentActivity {
    private TextView exceptionView;

    public static void showException(Throwable throwable) {
        if (BaseApplication.getInstance() != null && BuildConfig.DEBUG) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(byteArrayOutputStream));
            String msg = new String(byteArrayOutputStream.toByteArray());
            Intent intent = new Intent(BaseApplication.getInstance(), ExceptionInfoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg", msg);
            BaseApplication.getInstance().startActivity(intent);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show_exception;
    }

    @Override
    protected void initView() {
        exceptionView = findViewById(R.id.show_exception_view);
    }

    @Override
    protected void getIntentData(Intent intent) {
        handlerIntent(getIntent(), false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handlerIntent(intent, true);
    }

    private void handlerIntent(Intent intent, boolean isNew) {
        String msg = intent.getStringExtra("msg");
        if (msg != null) {
            if (isNew){
                exceptionView.append("\n\n\n\n\n\n");
            }
            exceptionView.append(msg);
        }
    }
}

package com.jushi.library.customView.progressDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jushi.library.R;


@SuppressLint("HandlerLeak")
public class CustomProgressDialog extends Dialog {

    public static final int STYLE_ONE = 1;
    public static final int STYLE_TWO = 2;

    private String timeOutMessage;
    private OnTimeOutListener listener;
    private boolean isCancelable = true;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (isShowing()) {
                if (listener != null) {
                    listener.timeout();
                }
                dismiss();
                if (timeOutMessage != null && !"".equals(timeOutMessage)) {
                    Toast.makeText(getContext(), timeOutMessage, Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
    });

    public CustomProgressDialog(Context context) {
        this(context, R.style.CustomProgressDialog);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public static CustomProgressDialog createDialog(Context context) {
        return new CustomProgressDialog(context);
    }

    private void init() {
        setContentView(R.layout.custom_progressdialogui);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public void setCancelable(boolean a_cancelable) {
        this.isCancelable = a_cancelable;
        setCanceledOnTouchOutside(isCancelable);
    }

    public void setOnTimeOutListener(OnTimeOutListener a_listener) {
        listener = a_listener;
    }

    public void setStyle(int style) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        switch (style) {
            case STYLE_ONE:
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.bg_progressdialog);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                relativeLayout.setBackgroundDrawable(drawable);
                break;
            case STYLE_TWO:
                relativeLayout.setBackgroundDrawable(null);
                break;
            default:
                break;
        }
    }

    public void show(long timeout, final String timeoutMessage, OnTimeOutListener listener) {
        this.timeOutMessage = timeoutMessage;
        handler.sendEmptyMessageDelayed(0, timeout);
        setOnTimeOutListener(listener);
        setCancelable(false);
        super.show();
    }

    public void show(long timeout, final int timeoutMessage, OnTimeOutListener listener) {
        this.show(timeout, getContext().getString(timeoutMessage), listener);
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
            if (handler != null) {
                handler.removeMessages(0);
            }
        } catch (Exception exception) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShowing() && !isCancelable) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnTimeOutListener {
        void timeout();
    }

}

package com.jushi.library.customView.messageInput;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.library.R;

import java.util.List;

/**
 * 聊天消息输入框
 * 功能：
 * 1、文字输入；
 * 2、更多功能模块
 */
public class MessageInputView extends RelativeLayout implements View.OnClickListener {

    private ImageView ivChangeMode;
    private EditText etInputText;
    private TextView tvInputAudio;
    private TextView tvSendBtn;
    private ImageView ivMoreBtn;
    private RecyclerView rvMoreFunction;

    private boolean inputModeText = true;

    private OnMessageInputListener onMessageInputListener;

    public MessageInputView(Context context) {
        this(context, null);
    }

    public MessageInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        initView();
        setListener();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_message_input_layout, this);
        ivChangeMode = findViewById(R.id.iv_change_input_mode);
        etInputText = findViewById(R.id.et_msg_input);
        tvInputAudio = findViewById(R.id.audio_recorder_btn);
        tvSendBtn = findViewById(R.id.tv_send_btn);
        ivMoreBtn = findViewById(R.id.iv_msg_more);
        ivMoreBtn.setVisibility(GONE);
        rvMoreFunction = findViewById(R.id.rv_more_function_list);
    }

    private void setListener() {
        ivChangeMode.setOnClickListener(this);
        etInputText.setOnClickListener(this);
        tvInputAudio.setOnClickListener(this);
        tvSendBtn.setOnClickListener(this);
        ivMoreBtn.setOnClickListener(this);
    }

    /**
     * 设置更多功能
     *
     * @param imageResIds
     * @param functionTitles
     */
    public void setMoreFunction(List<Integer> imageResIds, List<String> functionTitles) {
        if (imageResIds.size() != functionTitles.size()) {
            throw new RuntimeException("资源文件与标题数量不匹配！");
        }
        ivMoreBtn.setVisibility(imageResIds.size() > 0 ? VISIBLE : GONE);
        rvMoreFunction.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvMoreFunction.setAdapter(new MoreFunctionAdapter(imageResIds, functionTitles));
        rvMoreFunction.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.set(10, 10, 10, 10);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_change_input_mode) {
            inputModeText = !inputModeText;
            ivChangeMode.setImageResource(inputModeText ? R.mipmap.ic_to_word : R.mipmap.ic_to_audio);
            etInputText.setVisibility(inputModeText ? VISIBLE : GONE);
            tvInputAudio.setVisibility(!inputModeText ? VISIBLE : GONE);
        } else if (v.getId() == R.id.et_msg_input) {
            rvMoreFunction.setVisibility(GONE);
            ivMoreBtn.setImageResource(R.mipmap.ic_msg_more);
        } else if (v.getId() == R.id.audio_recorder_btn) {
            if (onMessageInputListener == null) return;
            onMessageInputListener.onRecordAudio();
        } else if (v.getId() == R.id.tv_send_btn) {
            if (onMessageInputListener == null) return;
            onMessageInputListener.onSend(etInputText.getText().toString());
        } else if (v.getId() == R.id.iv_msg_more) {
            rvMoreFunction.setVisibility(rvMoreFunction.getVisibility() == GONE ? VISIBLE : GONE);
            ivMoreBtn.setImageResource(rvMoreFunction.getVisibility() == GONE ? R.mipmap.ic_msg_more : R.mipmap.ic_msg_close);
            if (onMessageInputListener == null) return;
            onMessageInputListener.onMoreBtnClick();
        }
    }

    public void setOnMessageInputListener(OnMessageInputListener listener) {
        this.onMessageInputListener = listener;
    }

    public interface OnMessageInputListener {
        void onSend(String msg);

        void onMoreBtnClick();

        void onRecordAudio();

        void onMoreFunctionClick(String functionTitle);
    }

    private class MoreFunctionAdapter extends RecyclerView.Adapter<MoreFunctionAdapter.VH> {

        private List<Integer> imageResIds;
        private List<String> functionTitles;

        public MoreFunctionAdapter(List<Integer> imageResIds, List<String> functionTitles) {
            this.imageResIds = imageResIds;
            this.functionTitles = functionTitles;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new VH(LayoutInflater.from(getContext()).inflate(R.layout.view_message_input_view_more_function_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int i) {
            vh.imageView.setImageResource(imageResIds.get(i));
            vh.textView.setText(functionTitles.get(i));
            vh.llItemView.setOnClickListener(v -> {
                if (onMessageInputListener == null) return;
                onMessageInputListener.onMoreFunctionClick(functionTitles.get(i));
            });
        }

        @Override
        public int getItemCount() {
            return imageResIds.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView textView;
            private LinearLayout llItemView;

            public VH(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_more_function_img);
                textView = itemView.findViewById(R.id.tv_more_function_title);
                llItemView = itemView.findViewById(R.id.ll_more_function_view);
            }
        }
    }
}

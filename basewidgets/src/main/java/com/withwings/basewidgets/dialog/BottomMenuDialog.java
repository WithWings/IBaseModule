package com.withwings.basewidgets.dialog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.withwings.basewidgets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿 IOS 底部弹出菜单
 * 创建：WithWings 时间 2018/1/8
 * Email:wangtong1175@sina.com
 */
public class BottomMenuDialog extends BottomSheetDialog implements View.OnClickListener {

    private RecyclerView mRecyShowMenu;

    private Button mBtnCancel;

    private Context mContext;

    private String mTitle;

    private List<String> mData = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;
    private LinearLayout mLlTitle;
    private TextView mTvTitle;

    public BottomMenuDialog(@NonNull Context context, List<String> data, OnItemClickListener onItemClickListener) {
        super(context);

        mContext = context;
        mData.addAll(data);
        mOnItemClickListener = onItemClickListener;

        init();
    }

    public BottomMenuDialog(@NonNull Context context, String title, List<String> data, OnItemClickListener onItemClickListener) {
        super(context);

        mContext = context;
        mTitle = title;
        mData.addAll(data);
        mOnItemClickListener = onItemClickListener;

        init();
    }

    private void init() {
        setContentView(R.layout.dialog_bottom_menu);

        initView();

        initListener();
    }

    private void initView() {
        View viewById = findViewById(R.id.design_bottom_sheet);
        if (viewById != null) {
            viewById.setBackgroundResource(android.R.color.transparent);
        }
        mLlTitle = findViewById(R.id.ll_title);
        mTvTitle = findViewById(R.id.tv_title);
        mRecyShowMenu = findViewById(R.id.recy_show_menu);
        mBtnCancel = findViewById(R.id.btn_cancel);

        if (TextUtils.isEmpty(mTitle)) {
            mLlTitle.setVisibility(View.GONE);
        } else {
            if (mData.size() == 0) {
                View viewTitleLine = findViewById(R.id.view_title_line);
                viewTitleLine.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mLlTitle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.radius_all));
                } else {
                    mLlTitle.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.radius_all));
                }
            }
            mLlTitle.setVisibility(View.VISIBLE);
        }

        mRecyShowMenu.setAdapter(new BottomMenuAdapter());
        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        // 设置为垂直线性，默认垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyShowMenu.setLayoutManager(linearLayoutManager);
    }

    private void initListener() {
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_cancel) {
            dismiss();
        }
    }

    public void setBtnCancel(String cancel) {
        if (TextUtils.isEmpty(cancel)) {
            mBtnCancel.setVisibility(View.GONE);
        } else {
            mBtnCancel.setVisibility(View.VISIBLE);
            mBtnCancel.setText(cancel);
        }
    }

    class BottomMenuAdapter extends RecyclerView.Adapter<BottomMenuAdapter.BottomMenuViewHolder> {

        private static final int RADIUS_ALL = 0;
        private static final int RADIUS_TOP = 1;
        private static final int RADIUS_BOTTOM = 2;
        private static final int RADIUS_NULL = 3;

        @Override
        public int getItemViewType(int position) {
            if (mData.size() == 1) {
                if (TextUtils.isEmpty(mTitle)) {
                    return RADIUS_ALL;
                } else {
                    return RADIUS_BOTTOM;
                }
            } else if (position == 0 && TextUtils.isEmpty(mTitle)) {
                return RADIUS_TOP;
            } else if (position == mData.size() - 1) {
                return RADIUS_BOTTOM;
            } else {
                return RADIUS_NULL;
            }
        }

        @Override
        public BottomMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BottomMenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bottom_menu, parent, false), viewType);
        }

        @Override
        public void onBindViewHolder(final BottomMenuViewHolder holder, int position) {
            holder.mBtnMenu.setText(mData.get(position));
            if (position == mData.size() - 1) {
                holder.mViewLine.setVisibility(View.GONE);
            } else {
                holder.mViewLine.setVisibility(View.VISIBLE);
            }
            holder.mBtnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mData.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class BottomMenuViewHolder extends RecyclerView.ViewHolder {

            private View mViewLine;
            private Button mBtnMenu;

            public BottomMenuViewHolder(View itemView, int viewType) {
                super(itemView);
                mViewLine = itemView.findViewById(R.id.view_line);
                mBtnMenu = itemView.findViewById(R.id.btn_menu);
                int resource = 0;
                switch (viewType) {
                    case RADIUS_ALL:
                        resource = R.drawable.radius_all;
                        break;
                    case RADIUS_TOP:
                        resource = R.drawable.radius_top;
                        break;
                    case RADIUS_BOTTOM:
                        resource = R.drawable.radius_bottom;
                        break;
                    case RADIUS_NULL:
                        resource = R.drawable.radius_null;
                        break;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mBtnMenu.setBackground(ContextCompat.getDrawable(mContext, resource));
                } else {
                    mBtnMenu.setBackgroundDrawable(ContextCompat.getDrawable(mContext, resource));
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String menu, int position);
    }
}

package com.skeeter.demo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skeeter.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author michael created on 2016/12/2.
 */
public class TestRecyclerView extends FragmentActivity {

    @BindView(value = android.R.id.list)
    protected CustomRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);

        mRecyclerView.setHasFixedSize(true);

    }


    public static class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(value = R.id.cell_recycler_container)
        public LinearLayout mContainerLayout;
        @BindView(value = R.id.cell_recycler_textview)
        public TextView mTextView;
        @BindView(value = R.id.cell_recycler_imgview)
        public ImageView mImageView;

        public CustomRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class CustomRecyclerAdapter extends RecyclerView.Adapter {
        public static class ViewType {
            public static final int TYPE_NORMAL = 0;
            public static final int TYPE_HEADER = 1;
            public static final int TYPE_FOOTER = 2;
        }


        private List<String> mDataList = new ArrayList<>();
        private List<Object> mHeaderList = new ArrayList<>();
        private List<Object> mFooterList = new ArrayList<>();

        private OnItemClickListener mOnItemClickListener;

        public CustomRecyclerAdapter(List<String> dataList) {
            setDataList(dataList);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ViewType.TYPE_NORMAL) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cell_recycler_view, parent, false);

                CustomRecyclerViewHolder viewHolder = new CustomRecyclerViewHolder(view);
                return viewHolder;
            }

            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Object data = getItem(position);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClickListener(v, getItem(position));
                    }
                });
            }



        }

        @Override
        public int getItemCount() {
            return getHeaderSize() + getDataSize() + getFooterSize();
        }

        @Override
        public int getItemViewType(int position) {
            if (position < getHeaderSize()) {
                return ViewType.TYPE_HEADER;
            } else if (position >= getHeaderSize() + getDataSize()) {
                return ViewType.TYPE_FOOTER;
            } else {
                return ViewType.TYPE_NORMAL;
            }
        }

        public Object getItem(int position) {
            if (position < getHeaderSize()) {
                return getHeaderList().get(position);
            } else if (position >= getHeaderSize() + getDataSize()) {
                return getFooterList().get(position - getHeaderSize() - getDataSize());
            } else {
                return getDataList().get(position - getHeaderSize());
            }
        }

        public int getDataSize() {
            return mDataList.size();
        }

        public int getHeaderSize() {
            return mHeaderList.size();
        }

        public int getFooterSize() {
            return mFooterList.size();
        }

        public List<String> getDataList() {
            return mDataList;
        }

        public void setDataList(List<String> dataList) {
            if (dataList != null) {
                mDataList = dataList;
                notifyDataSetChanged();
            }
        }

        public List<Object> getFooterList() {
            return mFooterList;
        }

        public void setFooterList(List<Object> footerList) {
            if (footerList != null) {
                mFooterList = footerList;
                notifyDataSetChanged();
            }
        }

        public List<Object> getHeaderList() {
            return mHeaderList;
        }

        public void setHeaderList(List<Object> headerList) {
            if (headerList != null) {
                mHeaderList = headerList;
                notifyDataSetChanged();
            }
        }
    }

    public static interface OnItemClickListener {
        void onItemClickListener(View currentView, Object currentData);
    }
}

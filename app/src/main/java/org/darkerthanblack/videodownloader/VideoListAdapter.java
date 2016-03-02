package org.darkerthanblack.videodownloader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.darkerthanblack.videodownloader.entity.Video;

import java.util.List;

/**
 * Created by Jay on 16/3/2.
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> implements View.OnClickListener{
    public List<Video> datas = null;
    public VideoListAdapter(List<Video> datas) {
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.fileName.setText(datas.get(position).getName());
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(Video)v.getTag());
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fileName;
        public TextView fileState;
        public TextView remainTime;
        public ViewHolder(View view){
            super(view);
            fileName = (TextView) view.findViewById(R.id.video_name);
            fileState = (TextView) view.findViewById(R.id.file_state);
            remainTime = (TextView) view.findViewById(R.id.remain_time);
        }
    }

    public void addItem(Video content, int position) {
        datas.add(position, content);
        notifyItemInserted(position);
    }

    public void removeItem(Video model) {
        int position = datas.indexOf(model);
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(int position)
    {
        datas.remove(position);
        notifyItemRemoved(position);
    }
    public void addItem(Video v)
    {
        datas.add(v);
        notifyItemInserted(datas.size());
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Video data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
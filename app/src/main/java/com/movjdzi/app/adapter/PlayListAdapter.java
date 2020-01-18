package com.movjdzi.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media.playerlib.widget.GlobalDATA;
import com.movjdzi.app.R;
import com.movjdzi.app.adapter.event.OnSeriClickListener;
import com.movjdzi.app.model.VideoVo;

import java.util.ArrayList;


/**
 * @author huangyong
 * createTime 2019/6/23
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayItemHolder> {

    private ArrayList<VideoVo> urls;
    private OnSeriClickListener clickListener;
    private int playIndex = 0;
    private int groupPlay;

    public PlayListAdapter(ArrayList<VideoVo> urls, OnSeriClickListener clickListener, int groupPlay) {
        this.urls = urls;
        this.clickListener = clickListener;
        this.groupPlay = groupPlay;
    }

    @NonNull
    @Override
    public PlayItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.play_squre_item, viewGroup, false);
        return new PlayItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayItemHolder playItemHolder, final int i) {
        if (playItemHolder.index == null) {
            return;
        }
        playItemHolder.index.setText((i + 1) + "");
        playItemHolder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.switchPlay(urls.get(i).getPlayUrl(), i, groupPlay);
                playIndex = i;
                GlobalDATA.PLAY_INDEX = i;
                notifyDataSetChanged();
            }
        });
        playItemHolder.index.setSelected(i == GlobalDATA.PLAY_INDEX ? true : false);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void setIndex(int currentIndex) {
        playIndex = currentIndex;
    }
}

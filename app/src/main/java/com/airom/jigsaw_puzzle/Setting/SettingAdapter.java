package com.airom.jigsaw_puzzle.Setting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airom.jigsaw_puzzle.R;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder>{

    private List<Setting> mSettingList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View settingView;
        ImageView settingImage;
        TextView settingName;
        public ViewHolder(View view){
            super(view);
            settingView = view;
            settingImage = (ImageView) view.findViewById(R.id.setting_image);
            settingName = (TextView) view.findViewById(R.id.setting_name);

        }
    }

    public SettingAdapter(List<Setting> settingList){
        mSettingList = settingList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.settingView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Setting setting = mSettingList.get(position);
                Toast.makeText(v.getContext(),"you clicked view" + setting.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.settingImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Setting setting = mSettingList.get(position);
                Toast.makeText(v.getContext(),"you clicked image" + setting.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Setting setting = mSettingList.get(position);
        holder.settingImage.setImageResource(setting.getImageId());
        holder.settingName.setText(setting.getName());
    }


    public int getItemCount() {
        return mSettingList.size();
    }


}

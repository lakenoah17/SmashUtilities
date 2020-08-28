package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.honorsmobileapps.noahshields.smashutilities.R;
import java.util.ArrayList;


public class StageItemAdapter extends RecyclerView.Adapter<StageItemAdapter.StageItemViewHolder> {
    private ArrayList<BasicStageInfo> stages;
    public static class StageItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView stageImage;
        private TextView stageName;
        private Context context;
        private CardView background;
        private BasicStageInfo basicStageInfo;
        private int imageResourceID;
        private String stageNameString;
        private ImageView strikeImage;
        public StageItemViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            background = itemView.findViewById(R.id.CardView_StageItem);
            stageImage = itemView.findViewById(R.id.ImageView_StageImage);
            stageName = itemView.findViewById(R.id.TextView_StageName);
            strikeImage = itemView.findViewById(R.id.ImageView_StageIsStriked);
            itemView.setOnClickListener(this);

        }
        public void bindStageItem(BasicStageInfo basicStageInfo){
            this.basicStageInfo = basicStageInfo;
            this.imageResourceID = basicStageInfo.getImageResourceID();
            this.stageNameString = basicStageInfo.getStageName();

            stageImage.setImageResource(imageResourceID);
            stageName.setText(stageNameString);

            if (basicStageInfo.getSelected()){
                background.setBackgroundTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.selected)));
                background.setCardElevation(12f);
                if (context instanceof StageStrikingActivity)
                    strikeImage.setVisibility(View.VISIBLE);
            }
            else{
                background.setBackgroundTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.unselected)));
                background.setCardElevation(2f);
            }
        }
        @Override
        public void onClick(View v) {
            if (basicStageInfo.getSelected()){
                background.setBackgroundTintList(ColorStateList.valueOf(v.getResources().getColor(R.color.unselected)));
                if (v.getContext() instanceof StageStrikingActivity)
                    strikeImage.setVisibility(View.GONE);
                background.setCardElevation(12f);
            }
            else{
                background.setBackgroundTintList(ColorStateList.valueOf(v.getResources().getColor(R.color.selected)));
                if (v.getContext() instanceof StageStrikingActivity)
                    strikeImage.setVisibility(View.VISIBLE);
                background.setCardElevation(2f);
            }
            basicStageInfo.setSelected(!basicStageInfo.getSelected());
        }
    }
    public StageItemAdapter(ArrayList<BasicStageInfo> stages){
        this.stages = stages;
    }
    @Override
    public int getItemCount() {
        return stages.size();
    }
    @Override
    public StageItemAdapter.StageItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.stage_item, viewGroup, false);
        return new StageItemAdapter.StageItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(StageItemAdapter.StageItemViewHolder holder, int position) {
        BasicStageInfo stageInfo = stages.get(position);
        holder.bindStageItem(stageInfo);
    }
    public void setStages(ArrayList<BasicStageInfo> stages){
        this.stages = stages;
    }
}

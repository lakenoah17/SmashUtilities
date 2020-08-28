package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.honorsmobileapps.noahshields.smashutilities.R;
import java.util.ArrayList;


public class StageListAdapter extends RecyclerView.Adapter<StageListAdapter.StageListViewHolder> {
        private ArrayList<StageListInfo> stageLists;
        public static class StageListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView name;
            private StageListInfo stageListInfo;

            public StageListViewHolder(View itemView){
                super(itemView);
                name = itemView.findViewById(R.id.TextView_StageListName);
                itemView.setOnClickListener(this);
            }
            public void bindStageItem(StageListInfo stageListInfo){
                this.stageListInfo = stageListInfo;
                name.setText(stageListInfo.getName());
            }
            @Override
            public void onClick(View v) {
                Intent startNext = new Intent(v.getContext(), StageStrikingActivity.class);
                startNext.putExtra("SharedPrefKey",stageListInfo.getKey());
                startNext.putExtra("StageListName",stageListInfo.getName());
                v.getContext().startActivity(startNext);
            }
        }
        public StageListAdapter(ArrayList<StageListInfo> stageLists){
            this.stageLists = stageLists;
        }
        @Override
        public int getItemCount() {
            return stageLists.size();
        }
        @Override
        public StageListAdapter.StageListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View view = layoutInflater.inflate(R.layout.stage_list_item, viewGroup, false);
            return new StageListAdapter.StageListViewHolder(view);
        }
        @Override
        public void onBindViewHolder(StageListAdapter.StageListViewHolder holder, int position) {
            StageListInfo stageListInfo = stageLists.get(position);
            holder.bindStageItem(stageListInfo);
        }
        public void setStages(ArrayList<StageListInfo> stageListInfos){
            this.stageLists = stageListInfos;
        }
}

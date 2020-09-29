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

//Works as the adapter for the RecyclerViews in the
//CreateStageListActivity and the StageStrikingActivity
public class StageItemAdapter extends RecyclerView.Adapter<StageItemAdapter.StageItemViewHolder> {
    private ArrayList<BasicStageInfo> stages;

    //The ItemViewHolder for stage items
    public static class StageItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView stageImage;
        private TextView stageName;
        private Context context;
        private CardView background;
        private BasicStageInfo basicStageInfo;
        private int imageResourceID;
        private String stageNameString;
        private ImageView strikeImage;

        //Constructs the ViewHolder
        //Params:
        //  itemView - the item of the RecyclerView that this
        //          ViewHolder will contain
        public StageItemViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            background = itemView.findViewById(R.id.CardView_StageItem);
            stageImage = itemView.findViewById(R.id.ImageView_StageImage);
            stageName = itemView.findViewById(R.id.TextView_StageName);
            strikeImage = itemView.findViewById(R.id.ImageView_StageIsStriked);

            itemView.setOnClickListener(this);

        }

        //Binds the appropriate information to the itemView
        //Params:
        //  basicStageInfo - data class containing all of the
        //           necessary info to populate the itemView
        public void bindStageItem(BasicStageInfo basicStageInfo){
            this.basicStageInfo = basicStageInfo;
            this.imageResourceID = basicStageInfo.getImageResourceID();
            this.stageNameString = basicStageInfo.getStageName();

            stageImage.setImageResource(imageResourceID);
            stageName.setText(stageNameString);

            //When the activity is restarted by a phone orientation
            //change or something else this makes sure the itemView
            //is properly updated to it's previous state
            if (basicStageInfo.getSelected()){
                background.setBackgroundTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.selected)));
                background.setCardElevation(12f);

                //The StageStrikingActivity has an extra image laid
                //over every item that's selected.
                //The image is a big red X
                if (context instanceof StageStrikingActivity)
                    strikeImage.setVisibility(View.VISIBLE);
            }
            else{
                background.setBackgroundTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.unselected)));
                background.setCardElevation(2f);
            }
        }

        //When an item is clicked it changes its appearance
        @Override
        public void onClick(View v) {
            //Checks if the item is already selected or not and unselects it
            if (basicStageInfo.getSelected()){
                //Changes background color
                background.setBackgroundTintList(ColorStateList.valueOf(v.getResources().getColor(R.color.unselected)));

                //Changes the visibility of the image
                // special to StageStrikingActivity
                if (v.getContext() instanceof StageStrikingActivity)
                    strikeImage.setVisibility(View.GONE);

                background.setCardElevation(12f);
            }
            else{
                //Changes background color
                background.setBackgroundTintList(ColorStateList.valueOf(v.getResources().getColor(R.color.selected)));

                //Changes the visibility of the image
                // special to StageStrikingActivity
                if (v.getContext() instanceof StageStrikingActivity)
                    strikeImage.setVisibility(View.VISIBLE);
                background.setCardElevation(2f);
            }

            //Changes the selected value to its opposite.
            basicStageInfo.setSelected(!basicStageInfo.getSelected());
        }
    }

    //Constructs the adapter
    //Params:
    //  stages - an ArrayList containing all of the stages
    //              to be used in the RecyclerView
    public StageItemAdapter(ArrayList<BasicStageInfo> stages){
        this.stages = stages;
    }

    //Gets the size of the RecyclerView
    //Params: N/A
    //Returns: the size of the RecyclerView
    @Override
    public int getItemCount() {
        return stages.size();
    }

    //Inflates the view to use for an item in the RecyclerView
    //Params:
    //  viewGroup - used to get the context of the RecyclerViews
    //              Activity and as the group this view is a part
    //              of
    //  i - the index of the item in the RecyclerView
    //Returns: a new ViewHolder for the adapter
    @Override
    public StageItemAdapter.StageItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Gets inflater
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        //Inflates a new item
        View view = layoutInflater.inflate(R.layout.stage_item, viewGroup, false);
        //Returns the new ViewHolder
        return new StageItemAdapter.StageItemViewHolder(view);
    }

    //Starts the binding process for the ViewHolder
    //Params:
    //  holder - the ViewHolder to bind
    //  position - the position of the ViewHolder in the RecyclerView
    @Override
    public void onBindViewHolder(StageItemAdapter.StageItemViewHolder holder, int position) {
        BasicStageInfo stageInfo = stages.get(position);
        holder.bindStageItem(stageInfo);
    }

    //This method allows the user to change the list the user
    //uses for the RecyclerView
    //Currently Unused
    //Params:
    //  stages - the ArrayList to replace the current one with
    public void setStages(ArrayList<BasicStageInfo> stages){
        this.stages = stages;
    }
}

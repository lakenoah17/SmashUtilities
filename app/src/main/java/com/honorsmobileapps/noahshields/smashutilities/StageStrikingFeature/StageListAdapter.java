package com.honorsmobileapps.noahshields.smashutilities.StageStrikingFeature;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.honorsmobileapps.noahshields.smashutilities.R;
import java.util.ArrayList;

//Works as the adapter for the RecyclerView in the
//ChooseStageListActivity
public class StageListAdapter extends RecyclerView.Adapter<StageListAdapter.StageListViewHolder> {
    private ArrayList<StageListInfo> stageLists;

    //ViewHolder class for the items of the RecyclerView
    public static class StageListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private StageListInfo stageListInfo;

        //Constructs the ViewHolder
        //Params:
        //  itemView - the item contained by this ViewHolder
        public StageListViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.TextView_StageListName);
            itemView.setOnClickListener(this);
        }

        //Binds the appropriate values of the itemView
        //Params:
        //  stageListInfo - class with all of the data
        //                  necessary to populate the itemView
        public void bindStageItem(StageListInfo stageListInfo){
            this.stageListInfo = stageListInfo;
            name.setText(stageListInfo.getName());
        }

        //When this item is clicked it starts the StageStrikingActivity
        //Params:
        //  v - the view that was clicked
        @Override
        public void onClick(View v) {
            //Creates Intent to start next Activity and adds the necessary
            //data for the next activity as extras.
            Intent startNext = new Intent(v.getContext(), StageStrikingActivity.class);
            startNext.putExtra("SharedPrefKey", stageListInfo.getKey());
            startNext.putExtra("StageListName", stageListInfo.getName());

            v.getContext().startActivity(startNext);
        }
    }

    //Constructs the StageListAdapter
    //Params:
    //  stageLists - list containing all of the
    //              StageListInfos needed for the RecyclerView
    public StageListAdapter(ArrayList<StageListInfo> stageLists){
        this.stageLists = stageLists;
    }

    //Gets the size of the RecyclerView
    //Params: N/A
    //Returns: the size of the RecyclerView
    @Override
    public int getItemCount() {
            return stageLists.size();
        }

    //Inflates the view to use for an item in the RecyclerView
    //Params:
    //  viewGroup - used to get the context of the RecyclerViews
    //              Activity and as the group this view is a part
    //              of
    //  i - the index of the item in the RecyclerView
    //Returns: a new ViewHolder for the adapter
    @Override
    public StageListAdapter.StageListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Gets the LayoutInflater to inflate the view
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        //Inflates the view
        View view = layoutInflater.inflate(R.layout.stage_list_item, viewGroup, false);
        //Returns the new ViewHolder
        return new StageListAdapter.StageListViewHolder(view);
    }

    //Starts the binding process for the ViewHolder
    //Params:
    //  holder - the ViewHolder to bind
    //  position - the position of the ViewHolder in the RecyclerView
    @Override
    public void onBindViewHolder(StageListAdapter.StageListViewHolder holder, int position) {
        StageListInfo stageListInfo = stageLists.get(position);
        holder.bindStageItem(stageListInfo);
    }

    //This method allows the user to change the list the user
    //uses for the RecyclerView
    //Currently Unused
    //Params:
    //  stageListInfos - the ArrayList to replace the current one with
    public void setStages(ArrayList<StageListInfo> stageListInfos){
        this.stageLists = stageListInfos;
    }
}

package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//This adapter is used to create the RecyclerView for the
//CharacterSelectionActivity
public class CharacterItemAdapter extends RecyclerView.Adapter<CharacterItemAdapter.CharacterItemViewHolder> {
    //The ArrayList that stores all of the character info
    private ArrayList<BasicCharacterInfo> characters;

    //Sets up an object that works to creates the items for the RecyclerView
    public static class CharacterItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Fields
        private ImageView characterImage;
        private TextView characterName;
        private Context context;
        private CardView background;
        private BasicCharacterInfo basicCharacterInfo;

        //Initializes the view
        //Params:
        //  itemView - The overall item view
        public CharacterItemViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();

            //Finding the fields within itemView
            characterImage = itemView.findViewById(R.id.CharacterImage);
            characterName = itemView.findViewById(R.id.CharacterName);
            background = itemView.findViewById(R.id.RelativeLayout_CharcterItem);

            itemView.setOnClickListener(this);
        }

        //Sets all of the values for the item views components
        //Params:
        //  basicCharacterInfo - object that contains all of the necessary
        //                       info to set up the item view's components
        public void bindCharacterItem(BasicCharacterInfo basicCharacterInfo){
            this.basicCharacterInfo = basicCharacterInfo;

            //Sets the character name text
            characterName.setText(basicCharacterInfo.getName());
            //Sets the item view's background color
            background.setCardBackgroundColor(Color.parseColor(basicCharacterInfo.getColor()));
            //Sets the item view's image to the one retrieved from KuroganeHamnmer
            Picasso.get().load(basicCharacterInfo.getPicURL()).placeholder(R.drawable.ic_launcher_background).into(characterImage);
        }

        //Changes the activity if this item is clicked
        @Override
        public void onClick(View v) {
            //Sets up the Intent to start the next activity
            Intent startNew = CharacterDataActivity.newIntent(context,
                    getAdapterPosition(),
                    basicCharacterInfo.getName(),
                    basicCharacterInfo.getPicURL());

            //Starts the activity
            context.startActivity(startNew);
        }
    }

    //Initialises the adapter
    //Params:
    //  characters - all of the basic character info retrieved from KuroganeHammer
    public CharacterItemAdapter(ArrayList<BasicCharacterInfo> characters){
        this.characters = characters;
    }

    //Returns the size of the RecyclerView
    @Override
    public int getItemCount() {
        return characters.size();
    }

    //Creates the holder for the item views
    //  viewGroup - holds all of the item views in the RecyclerView
    //  i - the index of the view holder created in the RecyclerView
    //Returns: returns the CharacterItemViewHolder
    @Override
    @NonNull
    public CharacterItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.character_item, viewGroup, false);
        return new CharacterItemViewHolder(view);
    }

    //Binds all of the characters to the item view in the RecyclerView
    //Params:
    //  holder - the current holder that data needs to be bound in
    //  position - the position in the RecyclerView
    @Override
    public void onBindViewHolder(CharacterItemViewHolder holder, int position) {
        BasicCharacterInfo characterInfo = characters.get(position);
        holder.bindCharacterItem(characterInfo);
    }

    //Sets the characters to use for the RecyclerView
    //Params:
    //  characters - list of the characters to use for the RecyclerView
    public void setCharacters(ArrayList<BasicCharacterInfo> characters){
        this.characters = characters;
    }
}

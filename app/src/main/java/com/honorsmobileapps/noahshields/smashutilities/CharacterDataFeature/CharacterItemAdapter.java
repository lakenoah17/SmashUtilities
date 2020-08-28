package com.honorsmobileapps.noahshields.smashutilities.CharacterDataFeature;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.honorsmobileapps.noahshields.smashutilities.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharacterItemAdapter extends RecyclerView.Adapter<CharacterItemAdapter.CharacterItemViewHolder> {
    private ArrayList<BasicCharacterInfo> characters;
    public static class CharacterItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView characterImage;
        private TextView characterName;
        private Context context;
        private CardView background;
        private BasicCharacterInfo basicCharacterInfo;
        public CharacterItemViewHolder(View itemView){
            super(itemView);
            context = itemView.getContext();
            characterImage = itemView.findViewById(R.id.CharacterImage);
            characterName = itemView.findViewById(R.id.CharacterName);
            background = itemView.findViewById(R.id.RelativeLayout_CharcterItem);
            itemView.setOnClickListener(this);
        }
        public void bindCharacterItem(BasicCharacterInfo basicCharacterInfo){
            this.basicCharacterInfo = basicCharacterInfo;
            characterName.setText(basicCharacterInfo.getName());
            background.setCardBackgroundColor(Color.parseColor(basicCharacterInfo.getColor()));
            Log.d("Img url", "bindCharacterItem: " + basicCharacterInfo.getPicURL());
            Picasso.get().load(basicCharacterInfo.getPicURL()).placeholder(R.drawable.ic_launcher_background).into(characterImage);
        }
        @Override
        public void onClick(View v) {
            Intent startNew = CharacterDataActivity.newIntent(context,getAdapterPosition(),basicCharacterInfo.getName(),basicCharacterInfo.getPicURL());
            context.startActivity(startNew);
        }
    }
    public CharacterItemAdapter(ArrayList<BasicCharacterInfo> characters){
        this.characters = characters;
    }
    @Override
    public int getItemCount() {
        return characters.size();
    }
    @Override
    public CharacterItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.character_item, viewGroup, false);
        return new CharacterItemViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CharacterItemViewHolder holder, int position) {
        BasicCharacterInfo characterInfo = characters.get(position);
        holder.bindCharacterItem(characterInfo);
    }
    public void setCharacters(ArrayList<BasicCharacterInfo> characters){
        this.characters = characters;
    }
}

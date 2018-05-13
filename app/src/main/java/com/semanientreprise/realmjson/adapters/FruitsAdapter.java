package com.semanientreprise.realmjson.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semanientreprise.realmjson.R;
import com.semanientreprise.realmjson.model.Fruits;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class FruitsAdapter extends RealmRecyclerViewAdapter<Fruits, FruitsAdapter.FruitsViewHolder> {

    public FruitsAdapter(RealmResults<Fruits> fruits){
        super(fruits, true);
    }

    @Override
    public FruitsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_fruit_row,parent,false);

        return new FruitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FruitsViewHolder holder, int position) {
        final Fruits fruits = getItem(position);

        holder.fruitName.setText(fruits.getName());
        holder.fruitPrice.setText(fruits.getPrice());
        Picasso.get().load(fruits.getImage())
                .resize(150,150)
                .centerCrop()
                .into(holder.fruitImage);
    }

    class FruitsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.fruit_name)
        TextView fruitName;
        @BindView(R.id.fruit_price)
        TextView fruitPrice;
        @BindView(R.id.fruit_img)
        ImageView fruitImage;

        FruitsViewHolder (View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}

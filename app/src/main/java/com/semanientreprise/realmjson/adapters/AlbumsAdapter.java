package com.semanientreprise.realmjson.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semanientreprise.realmjson.R;
import com.semanientreprise.realmjson.model.Albums;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class AlbumsAdapter extends RealmRecyclerViewAdapter<Albums, AlbumsAdapter.AlbumsViewHolder> {

    public AlbumsAdapter(RealmResults<Albums> albums){
        super(albums, true);
    }

    @Override
    public AlbumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_album_row,parent,false);

        return new AlbumsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumsViewHolder holder, int position) {
        final Albums albums = getItem(position);

        holder.AlbumName.setText(albums.getName());
        holder.albumYear.setText(albums.getYear());
        Picasso.get().load(albums.getImage())
                .resize(150,150)
                .centerCrop()
                .into(holder.albumImage);
    }

    class AlbumsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.album_name)
        TextView AlbumName;
        @BindView(R.id.album_year)
        TextView albumYear;
        @BindView(R.id.album_img)
        ImageView albumImage;

        AlbumsViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}

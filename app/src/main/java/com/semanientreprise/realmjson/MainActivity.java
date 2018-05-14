package com.semanientreprise.realmjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.semanientreprise.realmjson.adapters.AlbumsAdapter;
import com.semanientreprise.realmjson.model.Albums;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fruitsRecView)
    RecyclerView fruitsRecView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        fillUpDatabase();

        createRealmObjectFromString();

        createRealmObjectFromHashMap();

        realm.beginTransaction();

        final RealmResults<Albums> results = realm.where(Albums.class).findAll();

        realm.commitTransaction();

        fruitsRecView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        fruitsRecView.setHasFixedSize(true);

        fruitsRecView.setAdapter(new AlbumsAdapter(results));
    }

    private void createRealmObjectFromHashMap() {
        Map<String, String> album = new HashMap<String, String>();

        album.put("Name", "Love Letter");
        album.put("Image", "https://images-na.ssl-images-amazon.com/images/I/71sbfQ0L9bL._SY355_.jpg");
        album.put("Year", "2010");

        final JSONObject json = new JSONObject(album);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(Albums.class, json);
            }
        });
    }

    private void createRealmObjectFromString() {
        final String json = "{ Name: \"Pink Friday\", Image: \"https://upload.wikimedia.org/wikipedia/en/thumb/f/f1/Pink_Friday_album_cover.jpg/220px-Pink_Friday_album_cover.jpg\" ,Year: 2010}";

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(Albums.class, json);
            }
        });
    }

    private void fillUpDatabase() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream inputStream = getResources().openRawResource(R.raw.albums);
                try {
                    realm.createAllFromJson(Albums.class,inputStream);
                } catch (IOException e) {
                    if (realm.isInTransaction())
                        realm.cancelTransaction();
                }
            }
        });
    }
}

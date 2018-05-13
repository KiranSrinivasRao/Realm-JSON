package com.semanientreprise.realmjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.semanientreprise.realmjson.adapters.FruitsAdapter;
import com.semanientreprise.realmjson.model.Fruits;

import java.io.IOException;
import java.io.InputStream;

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
        realm = Realm.getDefaultInstance();

        fillUpDatabase();

        realm.beginTransaction();

        final RealmResults<Fruits> results = realm.where(Fruits.class).findAll();

        realm.commitTransaction();

        fruitsRecView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        fruitsRecView.setHasFixedSize(true);

        fruitsRecView.setAdapter(new FruitsAdapter(results));
    }

    private void fillUpDatabase() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream inputStream = getResources().openRawResource(R.raw.fruits);
                try {
                    realm.createAllFromJson(Fruits.class,inputStream);
                } catch (IOException e) {
                    if (realm.isInTransaction())
                        realm.cancelTransaction();
                }
            }
        });
    }
}

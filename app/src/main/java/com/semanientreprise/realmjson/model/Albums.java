package com.semanientreprise.realmjson.model;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Albums extends RealmObject {
    String Name;
    String Image;
    String Year;
}

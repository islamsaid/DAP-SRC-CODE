package com.asset.dailapp.springcloudconfigserver.constants;

public enum Queries {

    GET_ALL_SYSTEM_SETTING(88),
    UPDATE_SYSTEM_SETTING(91)

    ;
    public final int id;
    Queries(int id) {
    this.id= id;
    }
}

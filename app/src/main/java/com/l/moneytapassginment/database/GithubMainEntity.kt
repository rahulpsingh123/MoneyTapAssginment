package com.l.moneytapassginment.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GithubMainEntity : RealmObject() {
    @PrimaryKey
    var total_count = 0
    var incomplete_results = false
    var items: RealmList<GithubItemEntity>? = null
}
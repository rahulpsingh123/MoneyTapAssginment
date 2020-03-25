package com.l.moneytapassginment.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LicenseEntity : RealmObject() {
    @PrimaryKey
    var key: String? = null
    var name: String? = null
    var spdx_id: String? = null
    var url: String? = null
}
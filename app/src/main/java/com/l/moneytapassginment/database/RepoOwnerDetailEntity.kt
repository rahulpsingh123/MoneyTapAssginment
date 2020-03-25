package com.l.moneytapassginment.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RepoOwnerDetailEntity : RealmObject() {
    @PrimaryKey
    var login: String? = null
    var id = 0
    var node_id: String? = null
    var avatar_url: String? = null
    var gravatar_id: String? = null
    var url: String? = null
    var html_url: String? = null
    var followers_url: String? = null
    var following_url: String? = null
    var gists_url: String? = null
    var starred_url: String? = null
    var subscriptions_url: String? = null
    var organizations_url: String? = null
    var repos_url: String? = null
    var events_url: String? = null
    var received_events_url: String? = null
    var type: String? = null
    var site_admin = false

}
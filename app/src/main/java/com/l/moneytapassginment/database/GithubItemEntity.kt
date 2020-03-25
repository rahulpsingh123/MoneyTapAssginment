package com.l.moneytapassginment.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class GithubItemEntity : RealmObject() {
    @PrimaryKey
    var id = 0
    var node_id: String? = null
    var name: String? = null
    var full_name: String? = null
    var owner: RepoOwnerDetailEntity? = null
    var html_url: String? = null
    var description: String? = null
    var isFork = false
    var url: String? = null
    var forks_url: String? = null
    var keys_url: String? = null
    var collaborators_url: String? = null
    var teams_url: String? = null
    var hooks_url: String? = null
    var issue_events_url: String? = null
    var events_url: String? = null
    var assignees_url: String? = null
    var branches_url: String? = null
    var tags_url: String? = null
    var blobs_url: String? = null
    var git_tags_url: String? = null
    var git_refs_url: String? = null
    var trees_url: String? = null
    var statuses_url: String? = null
    var languages_url: String? = null
    var stargazers_url: String? = null
    var contributors_url: String? = null
    var subscribers_url: String? = null
    var subscription_url: String? = null
    var commits_url: String? = null
    var git_commits_url: String? = null
    var comments_url: String? = null
    var issue_comment_url: String? = null
    var contents_url: String? = null
    var compare_url: String? = null
    var merges_url: String? = null
    var archive_url: String? = null
    var downloads_url: String? = null
    var issues_url: String? = null
    var pulls_url: String? = null
    var milestones_url: String? = null
    var labels_url: String? = null
    var releases_url: String? = null
    var deployments_url: String? = null
    var git_url: String? = null
    var ssh_url: String? = null
    var clone_url: String? = null
    var svn_url: String? = null
    var homepage: String? = null
    var mirror_url: String? = null
    var language: String? = null
    var default_branch: String? = null
    var created_at: Date? = null
    var updated_at: Date? = null
    var pushed_at: Date? = null
    var size = 0
    var stargazers_count = 0
    var watchers_count = 0
    var forks_count = 0
    var open_issues_count = 0
    var forks = 0
    var open_issues = 0
    var watchers = 0
    var score = 0.0
    var has_issues = false
    var has_projects = false
    var has_downloads = false
    var has_wiki = false
    var has_pages = false
    var archived = false
    var license: LicenseEntity? = null

}
package com.l.moneytapassginment.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.l.moneytapassginment.R
import com.l.moneytapassginment.viewModel.MoneyTapViewModel


class MainActivity : BaseActivity() {
    var model: MoneyTapViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProviders.of(this).get(MoneyTapViewModel::class.java)
        if (savedInstanceState == null) {
            addFragment(SearchRepoFragment(), addToBackStack = false, transition = TRANSITION.NONE)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}



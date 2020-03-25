package com.l.moneytapassginment.responseModel

import io.reactivex.disposables.CompositeDisposable

open class BaseRepo {
    private val subscriptions = CompositeDisposable()
    open fun clear(){
        subscriptions.dispose()
    }
}
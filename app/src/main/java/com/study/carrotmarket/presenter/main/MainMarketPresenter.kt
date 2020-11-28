package com.study.carrotmarket.presenter.main

import android.util.Log
import com.study.carrotmarket.constant.MainMarketContract
import com.study.carrotmarket.model.CarrotMarketDataRepository
import io.reactivex.disposables.CompositeDisposable

class MainMarketPresenter(private var view : MainMarketContract.View) : MainMarketContract.Presenter {
    private val TAG = MainMarketPresenter::class.java.simpleName

    override fun getSimpleUsedItem(): Boolean {
        val composite = CompositeDisposable()
        composite.add(
            CarrotMarketDataRepository.getSimpleUsedItem().subscribe ({
                view.setUsedItemList(it)
            }, {
                Log.e(TAG, it.localizedMessage)
            })

        )
        return true
    }

    override fun getDetailUsedItem(id : Int): Boolean {
        val composite = CompositeDisposable()
        composite.add(
            CarrotMarketDataRepository.getDetailUsedItem(id).subscribe( {
                view.setDetailUsedItem(it[0])
            }, {
              Log.e(TAG, it.localizedMessage)
            })
        )
        return true
    }
}
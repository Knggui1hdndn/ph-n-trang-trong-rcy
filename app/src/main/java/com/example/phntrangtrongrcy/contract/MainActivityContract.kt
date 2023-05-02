package com.example.phntrangtrongrcy.contract

 import com.example.phntrangtrongrcy.model.entity.QuoteList
 import com.example.phntrangtrongrcy.model.entity.Result

interface MainActivityContract {
    interface View {
        fun onSuccess(listData: List<Result>)
        fun onError(message: String)
    }

    interface Presenter {
        fun getData(page: Int, callback: (List<Result>) -> Unit)
    }
}
package com.althaafridha.kisahnabiapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.althaafridha.kisahnabiapp.data.KisahResponse
import com.althaafridha.kisahnabiapp.data.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel: ViewModel() {

	var isLoading = MutableLiveData<Boolean>()
	var isError = MutableLiveData<Throwable>()
	var kisahResponse = MutableLiveData<List<KisahResponse>>()

	fun getData(responseHandler: (List<KisahResponse>) -> Unit, errorHandler: (Throwable) -> Unit) {
		ApiClient.getApiService().getKisahNabi()
			// membuat background thread / proses asynchronous
			.subscribeOn(Schedulers.io())
			// menentukan dimana thread akan dibuat
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
				responseHandler(it)
			}, {
				errorHandler(it)
			})
	}

	fun getKisahNabi() {
		isLoading.value = true
		getData({
			isLoading.value = false
			kisahResponse.value = it
		},{
			isLoading.value = false
			isError.value = it
		})
	}
}
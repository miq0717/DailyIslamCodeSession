package miq0717.dailyislamcodesession.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import miq0717.dailyislamcodesession.data.model.HadithBooksResponse
import miq0717.dailyislamcodesession.data.repository.HadithBooksRepository
import miq0717.dailyislamcodesession.util.NetworkHelper
import miq0717.dailyislamcodesession.util.Resource

class HadithBooksViewModel @ViewModelInject constructor(
    private val hadithBooksRepository: HadithBooksRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _hadithBooks = MutableLiveData<Resource<HadithBooksResponse>>()
    val hadithBooks: LiveData<Resource<HadithBooksResponse>>
        get() = _hadithBooks

    fun getHadithBooks() {
        viewModelScope.launch {
            _hadithBooks.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                hadithBooksRepository.getHadithBooks(
                    limit = 15,
                    page = 1
                ).let {
                    if (it.isSuccessful) {
                        _hadithBooks.postValue(Resource.success(it.body()))
                    } else _hadithBooks.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _hadithBooks.postValue(Resource.error("Please connect to internet", null))
        }
    }
}
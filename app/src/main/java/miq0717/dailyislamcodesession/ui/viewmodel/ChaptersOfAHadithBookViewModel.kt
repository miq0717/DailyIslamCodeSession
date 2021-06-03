package miq0717.dailyislamcodesession.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import miq0717.dailyislamcodesession.data.model.ChaptersOfAHadithBookResponse
import miq0717.dailyislamcodesession.data.repository.ChaptersOfAHadithBookRepository
import miq0717.dailyislamcodesession.util.NetworkHelper
import miq0717.dailyislamcodesession.util.Resource

class ChaptersOfAHadithBookViewModel @ViewModelInject constructor(
    private val chaptersOfAHadithBookRepository: ChaptersOfAHadithBookRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _chaptersOfAHadithBook = MutableLiveData<Resource<ChaptersOfAHadithBookResponse>>()
    val chaptersOfAHadithBook: LiveData<Resource<ChaptersOfAHadithBookResponse>>
        get() = _chaptersOfAHadithBook

    fun getChaptersOfAHadithBook(
        collectionName: String,
        limit: Int,
        page: Int
    ) {
        viewModelScope.launch {
            _chaptersOfAHadithBook.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                chaptersOfAHadithBookRepository.getChaptersOfAHadithBook(
                    collectionName = collectionName,
                    limit = limit,
                    page = page
                ).let {
                    if (it.isSuccessful) {
                        _chaptersOfAHadithBook.postValue(Resource.success(it.body()))
                    } else _chaptersOfAHadithBook.postValue(
                        Resource.error(
                            it.errorBody().toString(), null
                        )
                    )
                }
            } else _chaptersOfAHadithBook.postValue(
                Resource.error(
                    "Please connect to internet and try again",
                    null
                )
            )
        }
    }
}
package miq0717.dailyislamcodesession.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import miq0717.dailyislamcodesession.data.model.HadithsOfAChapterResponse
import miq0717.dailyislamcodesession.data.repository.HadithsOfAChapterRepository
import miq0717.dailyislamcodesession.util.NetworkHelper
import miq0717.dailyislamcodesession.util.Resource

class HadithsOfAChapterViewModel @ViewModelInject constructor(
    private val hadithsOfAChapterRepository: HadithsOfAChapterRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _hadithsOfAChapter = MutableLiveData<Resource<HadithsOfAChapterResponse>>()
    val hadithsOfAChapter: LiveData<Resource<HadithsOfAChapterResponse>>
        get() = _hadithsOfAChapter

    fun getHadithsOfAChapter(
        collectionName: String,
        bookNumber: Int,
        limit: Int,
        page: Int
    ) {
        viewModelScope.launch {
            _hadithsOfAChapter.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                hadithsOfAChapterRepository.getHadithsOfAChapter(
                    collectionName = collectionName,
                    bookNumber = bookNumber,
                    limit = limit,
                    page = page
                ).let {
                    if (it.isSuccessful) {
                        _hadithsOfAChapter.postValue(Resource.success(it.body()))
                    } else _hadithsOfAChapter.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else _hadithsOfAChapter.postValue(Resource.error("Please connect to internet and try again", null))
        }
    }
}
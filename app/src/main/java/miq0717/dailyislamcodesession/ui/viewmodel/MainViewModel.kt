package miq0717.dailyislamcodesession.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import miq0717.dailyislamcodesession.data.model.ChaptersOfAHadithBookResponse
import miq0717.dailyislamcodesession.data.model.HadithBooksResponse
import miq0717.dailyislamcodesession.data.model.HadithInfoDatum
import miq0717.dailyislamcodesession.data.model.HadithsOfAChapterResponse
import miq0717.dailyislamcodesession.data.repository.MainRepository
import miq0717.dailyislamcodesession.util.NetworkHelper
import miq0717.dailyislamcodesession.util.Resource

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _hadithBooks = MutableLiveData<Resource<HadithBooksResponse>>()
    val hadithBooks: LiveData<Resource<HadithBooksResponse>>
        get() = _hadithBooks

    private val _chaptersOfAHadithBook = MutableLiveData<Resource<ChaptersOfAHadithBookResponse>>()
    val chaptersOfAHadithBook: LiveData<Resource<ChaptersOfAHadithBookResponse>>
        get() = _chaptersOfAHadithBook

    private val _hadithsOfAChapter = MutableLiveData<Resource<HadithsOfAChapterResponse>>()
    val hadithsOfAChapter: LiveData<Resource<HadithsOfAChapterResponse>>
        get() = _hadithsOfAChapter

    private val _detailsOfAHadith = MutableLiveData<Resource<HadithInfoDatum>>()
    val detailsOfAHadith: LiveData<Resource<HadithInfoDatum>>
        get() = _detailsOfAHadith

    fun getHadithBooks() {
        viewModelScope.launch {
            _hadithBooks.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getHadithBooks(
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

    fun getChaptersOfAHadithBook(
        collectionName: String,
        limit: Int,
        page: Int
    ) {
        viewModelScope.launch {
            _chaptersOfAHadithBook.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getChaptersOfAHadithBook(
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
                    "Please connect to internet",
                    null
                )
            )
        }
    }

    fun getChaptersOfAHadithBook(
        collectionName: String,
        bookNumber: Int,
        limit: Int,
        page: Int
    ) {
        viewModelScope.launch {
            _hadithsOfAChapter.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getHadithsOfAChapter(
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
            } else _hadithsOfAChapter.postValue(Resource.error("Please connect to internet", null))
        }
    }

    fun getDetailsOfAHadith(
        collectionName: String,
        hadithNumber: Int
    ) {
        viewModelScope.launch {
            _detailsOfAHadith.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getDetailsOfAHadith(
                    collectionName = collectionName,
                    hadithNumber = hadithNumber
                ).let {
                    if (it.isSuccessful) {
                        _detailsOfAHadith.postValue(Resource.success(it.body()))
                    } else _detailsOfAHadith.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else _detailsOfAHadith.postValue(Resource.error("Please connect to internet", null))
        }
    }
}
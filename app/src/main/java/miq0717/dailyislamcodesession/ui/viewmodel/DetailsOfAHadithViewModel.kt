package miq0717.dailyislamcodesession.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import miq0717.dailyislamcodesession.data.model.HadithInfoDatum
import miq0717.dailyislamcodesession.data.repository.DetailsOfAHadithRepository
import miq0717.dailyislamcodesession.util.NetworkHelper
import miq0717.dailyislamcodesession.util.Resource

class DetailsOfAHadithViewModel @ViewModelInject constructor(
    private val detailsOfAHadithRepository: DetailsOfAHadithRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _detailsOfAHadith = MutableLiveData<Resource<HadithInfoDatum>>()
    val detailsOfAHadith: LiveData<Resource<HadithInfoDatum>>
        get() = _detailsOfAHadith

    fun getDetailsOfAHadith(
        collectionName: String,
        hadithNumber: Int
    ) {
        viewModelScope.launch {
            _detailsOfAHadith.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                detailsOfAHadithRepository.getDetailsOfAHadith(
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
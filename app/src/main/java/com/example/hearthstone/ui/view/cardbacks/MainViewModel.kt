package com.example.hearthstone.ui.view.cardbacks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.model.CardBacks
import com.example.hearthstone.model.data.CardsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: CardsApi
): ViewModel(), CoroutineScope {



    private val _state = MutableLiveData<CardsState>()
    val state: LiveData<CardsState?> = _state

    data class CardsState(
        val cards: List<CardBacks?>? = null,
        val isLoading: Boolean = false
    )

    init {
        getCardsBack()
    }

    private val viewModelJob = SupervisorJob()
    override val coroutineContext: CoroutineContext = IO + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancelChildren()
    }

     fun getCardsBack() {

        viewModelScope.launch {
            try {
                _state.postValue(CardsState(isLoading = true))
                _state.postValue(CardsState(cards = api.getCardsBack(), isLoading = false))


            } catch (e: Exception) {
                Log.e("MainViewModel", "getCards", e)
                _state.postValue(CardsState(isLoading = false))
            }
        }
    }


}
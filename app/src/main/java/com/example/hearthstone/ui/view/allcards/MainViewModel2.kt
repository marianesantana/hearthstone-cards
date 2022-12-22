package com.example.hearthstone.ui.view.allcards

import android.util.Log
import androidx.lifecycle.*
import com.example.hearthstone.model.data.CardsApi
import com.example.hearthstone.model.SingleCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel2 @Inject constructor(
    private val api: CardsApi,

    ): ViewModel(), CoroutineScope {

    private val _state = MutableLiveData<SingleCardState>()
    val state: LiveData<SingleCardState> = _state

    data class SingleCardState(
        val card: SingleCard? = null,
        val isLoading: Boolean? = false,
    )

    private val viewModelJob = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancelChildren()
    }

    fun getCard(cardId: Int){
        viewModelScope.launch {
            try {
                    _state.postValue(SingleCardState(isLoading = true))
                     _state.postValue(
                        SingleCardState(
                            card = api.getCard(cardId),
                            isLoading = false
                        )
                    )
                    val response = api.getCard(cardId)
                    Log.d(response.toString(), "MainViewModelResponse")

            } catch (e: Exception) {
                Log.e("MainViewModel2", "getAllCards", e)
                _state.postValue(SingleCardState(isLoading = false))
            }
            finally {
                print("finish it")
            }
        }
    }
}
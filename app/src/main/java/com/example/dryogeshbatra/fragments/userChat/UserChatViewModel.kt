package com.example.dryogeshbatra.fragments.userChat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dryogeshbatra.API.Auth
import com.example.dryogeshbatra.API.RetrofitHelper
import com.example.dryogeshbatra.firestore.FirestoreClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserChatViewModel : ViewModel() {

    val networkToken = MutableLiveData<String>()
    val userId = MutableLiveData<String>()





    fun FetchRestrauntDetails(userId: String){
        viewModelScope.launch(Dispatchers.IO){
            val retro = RetrofitHelper.getInstance()
            retro.getToken(
                "$userId",
                "publisher",
                "uid",
                0
            ).enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                    networkToken.value = response.body()?.rtcToken
                    //Log.i("tokennn", "${response.body()?.rtcToken}")
                    //Log.i("Successs", response.body()?.rtcToken.toString())

                }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
                    Log.i("Failll", t.message.toString())
                }
            })
        }
    }

 /*   private val _roomName = MutableLiveData<String>()
    val roomName: LiveData<String> = _roomName

    private val _onJoinEvent = MutableSharedFlow<String>()
    val onJoinEvent = _onJoinEvent.asSharedFlow()

    fun onRoomEnter(name: String){
        _roomName.value = name
    }

    fun onJoinRoom(){
        if (roomName.value?.isEmpty() == true){
            Log.i("RoomTrans", "The room can't be empty")
            return
        }
        viewModelScope.launch {
            roomName.value?.let { _onJoinEvent.emit(it) }
        }
    }*/
}
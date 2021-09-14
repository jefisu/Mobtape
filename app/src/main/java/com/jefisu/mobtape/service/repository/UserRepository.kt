package com.jefisu.mobtape.service.repository

import android.content.Context
import com.google.gson.Gson
import com.jefisu.mobtape.R
import com.jefisu.mobtape.service.constants.MobConstants.Companion.HTTP
import com.jefisu.mobtape.service.listener.APIListener
import com.jefisu.mobtape.service.dto.UserDto
import com.jefisu.mobtape.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(val context: Context) {

    private val mRemote = RetrofitClient.api

    fun login(email: String, password: String, listener: APIListener) {
        val call: Call<UserDto> = mRemote.login(email, password)
        call.enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.code() != HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()?.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSucess(it) }
                }
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }

    fun create(name: String, email: String, password: String, listener: APIListener) {
        val call: Call<UserDto> = mRemote.create(name, email, password)
        call.enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                if (response.code() != HTTP.SUCCESS) {
                    val validation =
                        Gson().fromJson(response.errorBody()?.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSucess(it) }
                }
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }
}
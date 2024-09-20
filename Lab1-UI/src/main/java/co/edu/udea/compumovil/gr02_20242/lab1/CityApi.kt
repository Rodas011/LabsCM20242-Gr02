package co.edu.udea.compumovil.gr02_20242.lab1

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface CityApi {
    @POST("countries/cities")
    fun getCities(@Body request: CityRequest): Call<CityResponse>
}

fun sendRequest(
    country: String,
    onResponse: (CityResponse) -> Unit,
    onFailure: (Throwable) -> Unit
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://countriesnow.space/api/v0.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(CityApi::class.java)
    val call = api.getCities(CityRequest(country))

    call.enqueue(object : Callback<CityResponse> {
        override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
            if (response.isSuccessful) {
                val cityResponse = response.body()!!
                Log.d("sendRequest", "Success! Cities: ${cityResponse.data}")
                onResponse(cityResponse)
            } else {
                onFailure(Throwable("Request failed with code: ${response.code()}"))
            }
        }

        override fun onFailure(call: Call<CityResponse>, t: Throwable) {
            Log.e("sendRequest", "Failed: ${t.message}")
            onFailure(t)
        }
    })
}
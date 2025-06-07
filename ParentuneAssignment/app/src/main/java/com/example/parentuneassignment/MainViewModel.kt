package com.example.parentuneassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainViewModel : ViewModel() {

    private val _plans = MutableLiveData<List<Plan>>()
    val plans: LiveData<List<Plan>> = _plans

    init {
        fetchPlansFromApi()
    }

    private fun fetchPlansFromApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getPlans()
                val uiPlans = response.data.map { planApi ->
                    Plan(
                        name = planApi.plan_name,
                        price = if (planApi.cost_per_day > 0) "Rs ${planApi.cost_per_day}/Day" else "Free",
                        recommended = planApi.tagged_as,
                        successRate = planApi.claims,
                        headerImage = planApi.banner,
                        features = planApi.description.data_monthly,
                        buttonText = planApi.cta,
                        badgeColor = when (planApi.plan_name.uppercase()) {
                            "PLUS" -> "#2E7D7D"
                            "PRO" -> "#FF9800"
                            "BASIC" -> "#757575"
                            else -> "#999999"
                        }
                    )
                }
                _plans.value = uiPlans
            } catch (e: Exception) {
                e.printStackTrace()
                // Optionally handle error state here
            }
        }
    }
}

interface ApiService {
    @GET("api/subscription/subscribe/v2/plans")
    suspend fun getPlans(): PlansResponse
}
object RetrofitClient {
    private const val BASE_URL = "https://qa7.parentune.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
package com.example.parentuneassignment

data class PlansResponse(
    val statusCode: Int,
    val message: String,
    val data: List<PlanApiModel>
)

data class PlanApiModel(
    val plan_id: Int,
    val banner: String,
    val price: Double?,
    val claims: String,
    val description: Description,
    val tagged_as: String,
    val plan_name: String,
    val button_background: String,
    val cost_per_day: Double,
    val cost_per_day_new: Double,
    val cta: String
)

data class Description(
    val data_monthly: List<Feature>,
    val data_annual: List<Feature>
)

data class Feature(
    val text: String,
    val isLocked: String
)


data class Plan(
    val name: String = "",
    val price: String = "",
    val recommended: String = "",
    val successRate: String = "",
    val headerImage: String = "",
    val features: List<Feature> = emptyList(),
    val buttonText: String = "",
    val badgeColor: String = ""
)

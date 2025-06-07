package com.example.parentuneassignment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parentuneassignment.databinding.ItemCardsBinding
import com.example.parentuneassignment.databinding.ItemFeatureBinding
import com.squareup.picasso.Picasso

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {

    private var plans: List<Plan> = emptyList()

    fun setPlans(plans: List<Plan>) {
        this.plans = plans
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemCardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val plan = plans[position]
        holder.bind(plan)
    }

    override fun getItemCount(): Int = plans.size

    inner class PlanViewHolder(private val binding: ItemCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan) {
            with(binding) {
                Picasso.get().load(plan.headerImage).into(ivPlanHeader)

                tvPlanName.text = plan.name
                tvPlanPrice.text = plan.price
                tvSuccessRate.text = plan.successRate
                tvRecommended.text = plan.recommended
                when (position) {
                    0 -> {
                        view1.setBackgroundResource(R.drawable.circle_indicator_active)
                        view2.setBackgroundResource(R.drawable.circle_indicator_inactive)
                        view3.setBackgroundResource(R.drawable.circle_indicator_inactive)
                    }
                    1 -> {
                        view1.setBackgroundResource(R.drawable.circle_indicator_inactive)
                        view2.setBackgroundResource(R.drawable.circle_indicator_active)
                        view3.setBackgroundResource(R.drawable.circle_indicator_inactive)
                    }
                    2 -> {
                        view1.setBackgroundResource(R.drawable.circle_indicator_inactive)
                        view2.setBackgroundResource(R.drawable.circle_indicator_inactive)
                        view3.setBackgroundResource(R.drawable.circle_indicator_active)
                    }
                    else -> {
                        view1.setBackgroundResource(R.drawable.circle_indicator_inactive)
                        view2.setBackgroundResource(R.drawable.circle_indicator_inactive)
                        view3.setBackgroundResource(R.drawable.circle_indicator_inactive)
                    }
                }
                try {
                    llBadge.background?.setTint(Color.parseColor(plan.badgeColor))
                } catch (e: Exception) {
                    llBadge.background?.setTint(Color.BLACK)
                }

                llFeatures.removeAllViews()

                plan.features.forEach { feature ->
                    val featureBinding = ItemFeatureBinding.inflate(
                        LayoutInflater.from(itemView.context),
                        llFeatures,
                        false
                    )
                   if(feature.isLocked == "True"){
                    featureBinding.ivIcon.setBackgroundResource(R.drawable.lock)
                   }
                    else{
                       featureBinding.ivIcon.setBackgroundResource(R.drawable.check)
                   }
                    featureBinding.tvFeature.text = feature.text
                    llFeatures.addView(featureBinding.root)
                }
            }
        }
    }

}
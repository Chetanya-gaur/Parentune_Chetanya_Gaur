package com.example.parentuneassignment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parentuneassignment.databinding.ItemCardsBinding
import com.example.parentuneassignment.databinding.ItemFeatureBinding
import com.squareup.picasso.Picasso

class PlanAdapter(
    private val onIndicatorClick: (Int) -> Unit
) : RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {

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
        holder.bind(plan, position)
    }

    override fun getItemCount(): Int = plans.size

    inner class PlanViewHolder(private val binding: ItemCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan, position: Int) {
            with(binding) {
                Picasso.get().load(plan.headerImage).into(ivPlanHeader)

                tvPlanName.text = plan.name
                tvPlanPrice.text = plan.price
                tvSuccessRate.text = plan.successRate
                tvRecommended.text = plan.recommended

                view1.setBackgroundResource(
                    if (position == 0) R.drawable.circle_indicator_active else R.drawable.circle_indicator_inactive
                )
                view2.setBackgroundResource(
                    if (position == 1) R.drawable.circle_indicator_active else R.drawable.circle_indicator_inactive
                )
                view3.setBackgroundResource(
                    if (position == 2) R.drawable.circle_indicator_active else R.drawable.circle_indicator_inactive
                )

                // Click listeners for indicators
                view1.setOnClickListener { onIndicatorClick(0) }
                view2.setOnClickListener { onIndicatorClick(1) }
                view3.setOnClickListener { onIndicatorClick(2) }

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
                    featureBinding.ivIcon.setBackgroundResource(
                        if (feature.isLocked == "True") R.drawable.lock else R.drawable.check
                    )
                    featureBinding.tvFeature.text = feature.text
                    llFeatures.addView(featureBinding.root)
                }
            }
        }
    }
}

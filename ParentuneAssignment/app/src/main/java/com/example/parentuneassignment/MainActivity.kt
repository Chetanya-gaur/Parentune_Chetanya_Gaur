package com.example.parentuneassignment

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.parentuneassignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var planAdapter: PlanAdapter
    private lateinit var viewModel: MainViewModel
    private var currentPlans: List<Plan> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       supportActionBar?.title = "Choose Your Plan"
       supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupViewModel()
        setupRecyclerView()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        planAdapter = PlanAdapter()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvPlans.layoutManager = layoutManager
        binding.rvPlans.adapter = planAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvPlans)
        binding.rvPlans.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Find the snapped view
                    val snappedView = snapHelper.findSnapView(layoutManager)
                    if (snappedView != null) {
                        val position = layoutManager.getPosition(snappedView)
                        updateButtonText(position)
                    }
                }
            }
        })
    }
    private fun updateButtonText(position: Int) {
        if (position in currentPlans.indices) {
            val plan = currentPlans[position]
            binding.btnPlan.text = plan.buttonText
        }
    }
    private fun observeData() {
        viewModel.plans.observe(this) { plans ->
             currentPlans = plans
            planAdapter.setPlans(plans)
            updateButtonText(0)
        }
    }
}

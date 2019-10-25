package com.jcaique.presentation.dialects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcaique.domain.models.Region
import com.jcaique.presentation.R
import com.jcaique.presentation.utils.DividerItemDecoration
import com.jcaique.presentation.utils.dataflow.UserInteraction
import com.jcaique.presentation.utils.dataflow.ViewState
import com.jcaique.presentation.utils.selfInject
import kotlinx.android.synthetic.main.activity_dialects.*
import kotlinx.android.synthetic.main.activity_regions.emptyStateView
import kotlinx.android.synthetic.main.activity_regions.errorStateView
import kotlinx.android.synthetic.main.activity_regions.loadingStateView
import kotlinx.android.synthetic.main.error_state_layout.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class DialectsActivity : AppCompatActivity(), KodeinAware {
    
    companion object {
        const val EXTRA_REGION = "region"
        
        fun newInstance(activity: Activity, region: Region) = activity.run {
            startActivity(
                Intent(this, DialectsActivity::class.java)
                    .putExtra(EXTRA_REGION, region)
            )
        }
    }

    override val kodein = selfInject()
    private val viewModel by kodein.instance<DialectsViewModel>()
    
    private val region by lazy { intent.getSerializableExtra(EXTRA_REGION) as Region }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialects)
        init()
        setupToolbar()
    }

    private fun init() {
        viewModel.handle(UserInteraction.OpenedScreen)
        
        lifecycleScope.launch {
            viewModel.bind().collect { handle(it) }
        }
        
        dialectsRv.run {
            layoutManager = LinearLayoutManager(this@DialectsActivity)
            addItemDecoration(
                DividerItemDecoration(this@DialectsActivity)
                    .also { 
                        ContextCompat
                            .getDrawable(this@DialectsActivity, R.drawable.divider)
                            ?.let(it::setDrawable) 
                    }
            )
        }
    }
    
    private fun setupToolbar() {
        dialectsToolBar.apply {
            title = region.name.capitalize()
            setNavigationOnClickListener { finish() }
        }
    }

    private fun handle(state: ViewState<DialectsPresentation>) {
        controlVisibilities(state)

        when (state) {
            is ViewState.Success -> setupContent(state.value)
            is ViewState.Failed -> setupRetry()
        }
    }

    private fun setupRetry() {
        errorStateView.let {
            tryAgainBtn.setOnClickListener {
                viewModel.handle(UserInteraction.RequestedFreshContent)
            }
        }
    }

    private fun setupContent(value: DialectsPresentation) {
        dialectsRv.adapter = DialectAdapter(value)
    }

    private fun controlVisibilities(state: ViewState<DialectsPresentation>) {
        loadingStateView.isVisible = state is ViewState.Loading
        emptyStateView.isVisible = state is ViewState.Success && state.value.dialects.isEmpty()
        errorStateView.isVisible = state is ViewState.Failed
        dialectsRv.isVisible = state is ViewState.Success && state.value.dialects.isNotEmpty()
    }
}

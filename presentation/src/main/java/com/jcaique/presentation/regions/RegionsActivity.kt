package com.jcaique.presentation.regions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcaique.domain.models.Region
import com.jcaique.presentation.R
import com.jcaique.utils.DividerItemDecoration
import com.jcaique.utils.dataflow.UserInteraction.OpenedScreen
import com.jcaique.utils.dataflow.UserInteraction.RequestedFreshContent
import com.jcaique.utils.dataflow.ViewState
import com.jcaique.utils.selfInject
import com.jcaique.presentation.contributing.ContributingNavigation
import com.jcaique.presentation.dialects.DialectsActivity
import com.jcaique.utils.dataflow.ViewState.Failed
import com.jcaique.utils.dataflow.ViewState.Loading
import com.jcaique.utils.dataflow.ViewState.Success
import kotlinx.android.synthetic.main.activity_regions.*
import kotlinx.android.synthetic.main.error_state_layout.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import kotlinx.coroutines.flow.collect
import org.kodein.di.generic.instance

class RegionsActivity : AppCompatActivity(), KodeinAware {

    override val kodein = selfInject()
    private val viewModel by kodein.instance<RegionsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regions)
        init()
        setupBottomBar()
    }

    private fun init() {
        viewModel.handle(OpenedScreen)

        lifecycleScope.launch {
            viewModel.bind().collect { handle(it) }
        }

        regionsRv.run {
            layoutManager = LinearLayoutManager(this@RegionsActivity)
            addItemDecoration(DividerItemDecoration(this@RegionsActivity))
        }
    }

    private fun setupBottomBar() {
        setSupportActionBar(bar)
        val bottom = ContributingNavigation()

        bar.setNavigationOnClickListener {
            bottom.show(supportFragmentManager, bottom.tag)
        }
    }

    private fun handle(state: ViewState<RegionsPresentation>) {
        controlVisibilities(state)

        when (state) {
            is Success -> setupContent(state.value)
            is Failed -> setupRetry()
        }
    }

    private fun setupRetry() {
        errorStateView.let {
            tryAgainBtn.setOnClickListener {
                viewModel.handle(RequestedFreshContent)
            }
        }
    }

    private fun setupContent(value: RegionsPresentation) {
        regionsRv.adapter =
            RegionAdapter(value, ::navigateToDialects)
    }

    private fun controlVisibilities(state: ViewState<RegionsPresentation>) {
        loadingStateView.isVisible = state is Loading
        emptyStateView.isVisible = state is Success && state.value.regions.isEmpty()
        errorStateView.isVisible = state is Failed
        regionsRv.isVisible = state is Success && state.value.regions.isNotEmpty()
    }

    private fun navigateToDialects(region: Region) =
        DialectsActivity.newInstance(this, region)
}

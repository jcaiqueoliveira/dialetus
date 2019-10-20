package com.jcaique.presentation.regions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcaique.presentation.R
import com.jcaique.presentation.utils.DividerItemDecoration
import com.jcaique.presentation.utils.dataflow.UserInteraction.OpenedScreen
import com.jcaique.presentation.utils.dataflow.UserInteraction.RequestedFreshContent
import com.jcaique.presentation.utils.dataflow.ViewState
import com.jcaique.presentation.utils.dataflow.ViewState.Failed
import com.jcaique.presentation.utils.dataflow.ViewState.Loading
import com.jcaique.presentation.utils.dataflow.ViewState.Success
import com.jcaique.presentation.utils.selfInject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_state_layout.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class RegionsActivity : AppCompatActivity(), KodeinAware {

    override val kodein = selfInject()
    private val viewModel by kodein.instance<RegionsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
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
        regionsRv.adapter = RegionAdapter(value)
    }

    private fun controlVisibilities(state: ViewState<RegionsPresentation>) {
        loadingStateView.isVisible = (state is Loading)
        emptyStateView.isVisible = (state is Success && state.value.regions.isEmpty())
        errorStateView.isVisible = (state is Failed)
        regionsRv.isVisible = (state is Success && state.value.regions.isNotEmpty())
    }
}

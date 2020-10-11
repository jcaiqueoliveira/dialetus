package com.jcaique.dialetus.presentation.regions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cafe.adriel.dalek.DalekEvent
import cafe.adriel.dalek.Failure
import cafe.adriel.dalek.Finish
import cafe.adriel.dalek.Start
import cafe.adriel.dalek.Success
import cafe.adriel.dalek.collectIn
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.presentation.R
import com.jcaique.dialetus.presentation.contributing.ContributingNavigation
import com.jcaique.dialetus.presentation.dialects.DialectsActivity
import com.jcaique.dialetus.utils.extensions.selfInject
import com.jcaique.dialetus.utils.ui.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_regions.*
import kotlinx.android.synthetic.main.error_state_layout.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.instance

class RegionsActivity : AppCompatActivity(), DIAware {

    override val di by selfInject()
    private val viewModel by di.instance<RegionsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regions)
        setupViews()
        showRegions()
    }

    private fun setupViews() {
        setSupportActionBar(bar)
        val bottom = ContributingNavigation()

        bar.setNavigationOnClickListener {
            bottom.show(supportFragmentManager, bottom.tag)
        }

        regionsRv.run {
            layoutManager = LinearLayoutManager(this@RegionsActivity)
            addItemDecoration(DividerItemDecoration(this@RegionsActivity))
        }
    }

    private fun showRegions() {
        viewModel
            .showRegions()
            .collectIn(lifecycleScope, ::handleResult)
    }

    private suspend fun handleResult(event: DalekEvent<RegionsPresentation>) {
        controlVisibilities(event)

        when (event) {
            is Success -> setupContent(event.value)
            is Failure -> setupRetry()
        }
    }

    private fun setupContent(value: RegionsPresentation) {
        regionsRv.adapter = RegionAdapter(value, ::navigateToDialects)
    }

    private fun setupRetry() {
        tryAgainBtn.setOnClickListener {
            showRegions()
        }
    }

    private fun controlVisibilities(event: DalekEvent<RegionsPresentation>) {
        if (event is Finish) return

        loadingStateView.isVisible = event is Start
        emptyStateView.isVisible = event is Success && event.value.regions.isEmpty()
        errorStateView.isVisible = event is Failure
        regionsRv.isVisible = event is Success && event.value.regions.isNotEmpty()
    }

    private fun navigateToDialects(region: Region) =
        DialectsActivity.newInstance(this, region)
}

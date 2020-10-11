package com.jcaique.dialetus.presentation.dialects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cafe.adriel.dalek.DalekEvent
import cafe.adriel.dalek.Failure
import cafe.adriel.dalek.Finish
import cafe.adriel.dalek.Start
import cafe.adriel.dalek.Success
import cafe.adriel.dalek.collectIn
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.presentation.R
import com.jcaique.dialetus.presentation.contributing.ContributingConst
import com.jcaique.dialetus.utils.extensions.selfInject
import com.jcaique.dialetus.utils.extensions.share
import com.jcaique.dialetus.utils.ui.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_dialects.*
import kotlinx.android.synthetic.main.activity_regions.emptyStateView
import kotlinx.android.synthetic.main.activity_regions.errorStateView
import kotlinx.android.synthetic.main.activity_regions.loadingStateView
import kotlinx.android.synthetic.main.error_state_layout.*
import org.kodein.di.DIAware
import org.kodein.di.instance

class DialectsActivity : AppCompatActivity(), DIAware {
    
    companion object {
        const val EXTRA_REGION = "region"

        fun newInstance(activity: Activity, region: Region) = activity.run {
            startActivity(
                Intent(this, DialectsActivity::class.java)
                    .putExtra(EXTRA_REGION, region)
            )
        }
    }

    override val di = selfInject()

    private val viewModel by di.instance<DialectsViewModel>()
    
    private val region by lazy { intent.getSerializableExtra(EXTRA_REGION) as Region }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialects)
        setupViews()
        showDialects()
    }

    private fun setupViews() {
        dialectsToolBar.apply {
            title = region.name.capitalize()
            setNavigationOnClickListener { finish() }
        }

        dialectsList.run {
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

        dialectsFilter.addTextChangedListener { filterDialects() }
    }

    private fun showDialects() {
        viewModel
            .getDialects(region)
            .collectIn(lifecycleScope, ::handleResult)
    }

    private fun filterDialects() {
        viewModel
            .filterDialects(query = dialectsFilter.text.toString())
            .collectIn(lifecycleScope, ::handleResult)
    }

    private suspend fun handleResult(event: DalekEvent<DialectsPresentation>) {
        controlVisibilities(event)

        when (event) {
            is Success -> setupContent(event.value)
            is Failure -> setupRetry()
        }
    }

    private fun setupContent(presentation: DialectsPresentation) {
        dialectsList.adapter = DialectAdapter(presentation, ::shareDialect)
    }

    private fun setupRetry() {
        tryAgainBtn.setOnClickListener {
            showDialects()
        }
    }

    private fun shareDialect(dialect: Dialect) = dialect.run {
        """
        |${this.dialect}
        |
        |${getString(R.string.meaning)}:
        |${meanings.joinToString(separator = "\n") { "- $it" }}
        |
        |${getString(R.string.examples)}:
        |${examples.joinToString(separator = "\n") { "- $it" }}
        |
        |
        |${getString(R.string.more_dialects_on, ContributingConst.WEB)}
        """
            .trimMargin()
            .share(this@DialectsActivity)
    }

    private fun controlVisibilities(event: DalekEvent<DialectsPresentation>) {
        if (event is Finish) return

        loadingStateView.isVisible = event is Start
        emptyStateView.isVisible = event is Success && event.value.dialects.isEmpty()
        errorStateView.isVisible = event is Failure
        dialectsList.isVisible = event is Success && event.value.dialects.isNotEmpty()
    }
}

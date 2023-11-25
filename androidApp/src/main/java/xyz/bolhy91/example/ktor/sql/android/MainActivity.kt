package xyz.bolhy91.example.ktor.sql.android

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import xyz.bolhy91.example.ktor.sql.SpaceXSDK
import xyz.bolhy91.example.ktor.sql.cache.DatabaseDriverFactory

class MainActivity : AppCompatActivity() {

    private val mainScope = MainScope()

    private lateinit var launchesRecyclerView: RecyclerView
    private lateinit var progressBarView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val launchesRvAdapter = LaunchesRvAdapter(listOf())

    private val sdk = SpaceXSDK(DatabaseDriverFactory(this))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "SpaceX Launches"
        setContentView(R.layout.activity_main)
        launchesRecyclerView = findViewById(R.id.launchesListRv)
        progressBarView = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeContainer)

        launchesRecyclerView.adapter = launchesRvAdapter
        launchesRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displayLaunches(true)
        }
        displayLaunches(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun displayLaunches(needReload: Boolean) {
        progressBarView.visibility = View.VISIBLE
        mainScope.launch {
            kotlin.runCatching {
                sdk.getAllLaunches(needReload)
            }.onSuccess {
                launchesRvAdapter.launches = it
                launchesRvAdapter.notifyDataSetChanged()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.visibility = View.GONE
        }
    }
}
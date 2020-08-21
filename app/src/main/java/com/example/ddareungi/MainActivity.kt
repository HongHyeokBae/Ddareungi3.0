package com.example.ddareungi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.ddareungi.bookmark.BookmarkFragment
import com.example.ddareungi.map.MapFragment
import com.example.ddareungi.map.MapViewModel
import com.example.ddareungi.timer.TimerFragment
import com.example.ddareungi.utils.setupActionBar
import com.example.ddareungi.viewmodel.BikeStationViewModel
import com.example.ddareungi.viewmodel.MainViewModel
import com.example.ddareungi.viewmodel.TimerViewModel
import com.example.ddareungi.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val bookmarkFragment: BookmarkFragment = BookmarkFragment.newInstance()
    private val timerFragment by lazy { TimerFragment.newInstance() }
    var mapFragment: MapFragment? = null

    private val fm: FragmentManager = supportFragmentManager

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var bikeStationViewModel: BikeStationViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var timerViewModel: TimerViewModel

    val mainViewModel by lazy { ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(R.id.toolbar) {
            setDisplayShowTitleEnabled(false)
        }

        setUpBottomNav()

        // 정류소 viewModel 객체 생성
        bikeStationViewModel = ViewModelProviders
            .of(this, BikeStationViewModel.Companion.Factory(application))
            .get(BikeStationViewModel::class.java)

        mapViewModel = ViewModelProviders
            .of(this, MapViewModel.Companion.Factory(application))
            .get(MapViewModel::class.java)

        weatherViewModel = ViewModelProviders
            .of(this, WeatherViewModel.Companion.Factory(application))
            .get(WeatherViewModel::class.java)

        timerViewModel = ViewModelProviders
            .of(this, TimerViewModel.Companion.Factory(application))
            .get(TimerViewModel::class.java)


        // 첫 화면으로 [BookmarkFragment] 설정
        if(fm.findFragmentById(R.id.frag_container) == null) {
            fm.beginTransaction().add(R.id.frag_container, bookmarkFragment).commit()
        }

    }

    private fun setUpBottomNav() {
        bottom_nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.bookmark -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frag_container, bookmarkFragment).commit()
                }
                R.id.map -> {
                    setMapFragInstance(null)
                    fm.beginTransaction().replace(R.id.frag_container, mapFragment!!).commit()
                }
                R.id.timer -> {
                    fm.beginTransaction().replace(R.id.frag_container, timerFragment).commit()
                }
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // 현재 Fragment Id 저장
        fm.putFragment(outState, CURRENT_FRAGMENT_TAG, fm.findFragmentById(R.id.frag_container)!!)
    }

    override fun onBackPressed() {
        val activeFrag = supportFragmentManager.findFragmentById(R.id.frag_container)
        if(activeFrag is MapFragment) {
            if(!activeFrag.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    fun setMapFragInstance(stationId: String?) {
        if(mapFragment == null) {
            mapFragment = MapFragment.newInstance(stationId)
        } else {
            val args = Bundle()
            args.putString(MapFragment.CLICKED_IN_BOOKMARK_FRAG_TAG, stationId)
            mapFragment!!.arguments = args
        }
    }

    companion object {
        const val CURRENT_FRAGMENT_TAG = "current_fragment"
    }
}

package com.example.instagramm.activity


import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.instagramm.R
import com.example.instagramm.adapter.ViewPagerAdapter
import com.example.instagramm.databinding.ActivityMainBinding
import com.example.instagramm.fragment.*

/*
MainActivity contains view pager with 5 fragments
and pages can be controlled by BottomNavigationView
 */

class MainActivity : BaseActivity(), UploadFragment.UploadListener, HomeFragment.HomeListener {

    val TAG = MainActivity::class.java.simpleName
    var index = 0
    lateinit var binding: ActivityMainBinding
    lateinit var homeFragment: HomeFragment
    lateinit var uploadFragment: UploadFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun scrollToUpload() {
        index = 2
        scrollByIndex(index)
    }

    override fun scrollToHome() {
        index = 0
        scrollByIndex(index)
    }

    private fun scrollByIndex(index: Int) {
        binding.apply {
            viewPager.currentItem = index
            bottomNavigationView.menu.getItem(index).isChecked = true
        }
    }

    private fun initView() {
        binding.apply {
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigationHome -> {
                        viewPager.currentItem = 0
                    }
                    R.id.navigationSearch -> {
                        viewPager.currentItem = 1
                    }
                    R.id.navigationUpload -> {
                        viewPager.currentItem = 2
                    }
                    R.id.navigationFavorite -> {
                        viewPager.currentItem = 3
                    }
                    R.id.navigationProfile -> {
                        viewPager.currentItem = 4
                    }
                }
                true
            }

            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    index = position
                    bottomNavigationView.menu.getItem(index).isChecked = true
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
        }

        //Home and Upload fragments are global for communication
        homeFragment = HomeFragment()
        uploadFragment = UploadFragment()
        setupViewPager(binding.viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(homeFragment)
        adapter.addFragment(SearchFragment())
        adapter.addFragment(uploadFragment)
        adapter.addFragment(FavoriteFragment())
        adapter.addFragment(ProfileFragment())
        viewPager.adapter = adapter
    }
}
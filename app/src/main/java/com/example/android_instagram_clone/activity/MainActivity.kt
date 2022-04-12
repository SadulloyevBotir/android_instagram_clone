package com.example.android_instagram_clone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewParent
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager.widget.ViewPager
import com.example.android_instagram_clone.R
import com.example.android_instagram_clone.adapter.ViewPagerAdapter
import com.example.android_instagram_clone.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * It contains view pager with 5 fragment in MainActivity
 * and pages can be controlled by BottomNavigationView
 */
class MainActivity : AppCompatActivity(), UploadFragment.UploadListener, HomeFragment.HomeListener {
    var TAG = MainActivity::class.java.toString()
    var index = 0
    lateinit var homeFragment: HomeFragment
    lateinit var uploadFragment: UploadFragment
    lateinit var viewPager: ViewPager
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
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
        viewPager.setCurrentItem(index)
        bottomNavigationView.getMenu().getItem(index).setChecked(true)
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> viewPager.setCurrentItem(0)
                R.id.navigation_search -> viewPager.setCurrentItem(1)
                R.id.navigation_upload -> viewPager.setCurrentItem(2)
                R.id.navigation_favorite -> viewPager.setCurrentItem(3)
                R.id.navigation_profile -> viewPager.setCurrentItem(4)
            }
            true
        }
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                index = position
                bottomNavigationView.getMenu().getItem(index).setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        //Home and Home Fragment are global for communication purpose
        homeFragment = HomeFragment()
        uploadFragment = UploadFragment()
        setupViewPager(viewPager)
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
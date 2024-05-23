package com.example.githubuser.ui

import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter (activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
//    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {fragment = FollowersFragment()
                fragment.arguments = Bundle().apply {
                    putInt(FollowersFragment.ARG_POSITION, position + 1)
                    putString(FollowersFragment.ARG_USERNAME, username)
                }
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putInt(FollowersFragment.ARG_POSITION, position + 1)
                    putString(FollowersFragment.ARG_USERNAME, username)
                }
            }
        }
        return fragment as Fragment
//        val fragment = FollowersFragment()
//        fragment.arguments = Bundle().apply {
//            putInt(FollowersFragment.ARG_POSITION, position + 1)
//            putString(FollowersFragment.ARG_USERNAME, username)
//        }
//        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}
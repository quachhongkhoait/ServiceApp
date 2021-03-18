package com.example.serviceapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AdapterPager(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    private val arrayListFragment = ArrayList<Fragment>()
    private val arrayListTitle = ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return arrayListFragment[position]
    }

    override fun getCount(): Int {
        return arrayListFragment.size
    }

    fun addData(fm: Fragment, title: String) {
        arrayListFragment.add(fm)
        arrayListTitle.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return arrayListTitle[position]
    }
}
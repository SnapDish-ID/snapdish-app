package com.github.wisemann64.snapdishapp.ui.items

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager(private val context: Context): LinearLayoutManager(context) {

    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun canScrollVertically(): Boolean {
        return false
    }

}
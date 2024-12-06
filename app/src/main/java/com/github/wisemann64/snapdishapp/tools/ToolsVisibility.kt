package com.github.wisemann64.snapdishapp.tools

import android.view.View

class ToolsVisibility {
    companion object {
        val GONE = View.GONE
        val VISIBLE = View.VISIBLE

        fun visibility(value: Boolean): Int {
            return if (value) {
                VISIBLE
            } else {
                GONE
            }

        }
    }
}
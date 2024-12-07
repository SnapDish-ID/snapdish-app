package com.github.wisemann64.snapdishapp.ui.items

import com.github.wisemann64.snapdishapp.data.DataRecipe

interface OnClickListener {
    fun onClick(position: Int, data: DataRecipe)
}
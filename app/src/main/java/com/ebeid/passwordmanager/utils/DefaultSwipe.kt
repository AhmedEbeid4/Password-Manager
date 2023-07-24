package com.ebeid.passwordmanager.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ebeid.passwordmanager.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class DefaultSwipe(
    private val context: Context,
    private val onSwipeRight: (index: Int) -> Unit,
    private val onSwipeLeft: (index: Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val index = viewHolder.adapterPosition
        when (direction) {
            ItemTouchHelper.LEFT -> {
                onSwipeLeft(index)
            }

            ItemTouchHelper.RIGHT -> {
                onSwipeRight(index)
            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addSwipeLeftLabel("Delete")
            .setSwipeLeftLabelColor(Color.WHITE)
            .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.red))
            .setSwipeLeftActionIconTint(Color.WHITE)
            .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
            .addSwipeRightLabel("Update")
            .setSwipeRightLabelColor(Color.WHITE)
            .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.blue))
            .setSwipeRightActionIconTint(Color.WHITE)
            .addSwipeRightActionIcon(R.drawable.baseline_edit_24)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
package com.jcaique.presentation.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

/**
 * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
 * [LinearLayoutManager].
 *
 * @param context Current context, it will be used to access resources.
 * @param orientation Divider orientation. Should be [.HORIZONTAL] or [.VERTICAL].
 */
internal class DividerItemDecoration(context: Context, orientation: Int = LinearLayout.VERTICAL) :
    RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null

    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
     */
    private var mOrientation: Int = 0

    private val mBounds = Rect()

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(
                TAG,
                "@android:attr/listDivider was not set in the theme used for this " +
                    "DividerItemDecoration. " +
                    "Please set that attribute all call setDrawable()"
            )
        }
        a.recycle()
        setOrientation(orientation)
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * [RecyclerView.LayoutManager] changes orientation.
     *
     * @param orientation [.HORIZONTAL] or [.VERTICAL]
     */
    private fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL && orientation != VERTICAL)) {
            "Invalid orientation. It should be either HORIZONTAL or VERTICAL"
        }
        mOrientation = orientation
    }

    /**
     * Sets the [Drawable] for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    fun setDrawable(drawable: Drawable) {
        mDivider = drawable
    }

    override fun getItemOffsets(
      outRect: Rect,
      view: View,
      parent: RecyclerView,
      state: RecyclerView.State
    ) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        when (mOrientation == VERTICAL) {
            true -> outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
            false -> outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || mDivider == null) {
            return
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = 0
            right = parent.width
        }

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            drawVerticalChild(canvas, child, parent, left, right)
        }

        canvas.restore()
    }

    private fun drawVerticalChild(
      canvas: Canvas,
      view: View,
      parent: RecyclerView,
      left: Int,
      right: Int
    ) {
        parent.getDecoratedBoundsWithMargins(view, mBounds)

        val bottom = mBounds.bottom + view.translationY.roundToInt()
        val top = bottom - mDivider!!.intrinsicHeight

        mDivider!!.setBounds(left, top, right, bottom)
        mDivider!!.draw(canvas)
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        } else {
            top = 0
            bottom = parent.height
        }

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            drawHorizontalChild(canvas, child, parent, top, bottom)
        }

        canvas.restore()
    }

    private fun drawHorizontalChild(
      canvas: Canvas,
      view: View,
      parent: RecyclerView,
      top: Int,
      bottom: Int
    ) {
        parent.layoutManager?.getDecoratedBoundsWithMargins(view, mBounds)

        val right = mBounds.right + view.translationX.roundToInt()
        val left = right - mDivider!!.intrinsicWidth

        mDivider!!.setBounds(left, top, right, bottom)
        mDivider!!.draw(canvas)
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL

        private const val TAG = "DividerItem"
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}

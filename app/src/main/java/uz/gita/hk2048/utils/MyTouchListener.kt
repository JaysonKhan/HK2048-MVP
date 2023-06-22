package uz.gita.hk2048.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.gita.hk2048.model.FindSide
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {
    private val myGesure = GestureDetector(context, MyGesureListener())
    private var myMovementSideListener: ((FindSide) -> Unit)? = null

    inner class MyGesureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (abs(e2.x - e1.x) > 100 || abs(e2.y - e1.y) > 100) {
                if (abs(e2.x - e1.x) < abs(e2.y - e1.y)) {  // vertical
                    if (e2.y > e1.y) {// down
                        myMovementSideListener?.invoke(FindSide.DOWN)
                    } else {  // up
                        myMovementSideListener?.invoke(FindSide.UP)
                    }
                } else { // horizontal
                    if (e2.x > e1.x) {// right
                        myMovementSideListener?.invoke(FindSide.RIGHT)
                    } else {  // left
                        myMovementSideListener?.invoke(FindSide.LEFT)
                    }
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        myGesure.onTouchEvent(event)
        return true
    }
    fun setMyMovementSideListener(block: (FindSide) -> Unit) {
        myMovementSideListener = block
    }
}
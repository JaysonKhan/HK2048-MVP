package uz.gita.hk2048

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import uz.gita.hk2048.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2500 // 2.5 sec
    private lateinit var process: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        process = findViewById(R.id.progressBar)

        val animation = ObjectAnimator.ofInt(process, "progress", 0, 100)
        animation.duration = 2000
        animation.interpolator = DecelerateInterpolator()
        animation.start()

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}
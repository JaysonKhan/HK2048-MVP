package uz.gita.hk2048.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import uz.gita.hk2048.R
import uz.gita.hk2048.model.FindSide
import uz.gita.hk2048.repository.Repository
import uz.gita.hk2048.ui.info.InfoActivity
import uz.gita.hk2048.utils.BackUtils
import uz.gita.hk2048.utils.MyTouchListener
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(){
    private val items: MutableList<TextView> = ArrayList(16)
    private lateinit var mainView: LinearLayoutCompat
    private val repository = Repository.getInstance()
    private val util = BackUtils()
    private lateinit var reload: ImageView
    private lateinit var infoIMg: ImageView
    private lateinit var animatorSet:AnimatorSet
    private lateinit var myTouchListener: MyTouchListener
    private lateinit var score: TextView
    private lateinit var rek: TextView



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        loadViews()
        describeMatrixToView()

        myTouchListener = MyTouchListener(this)
        myTouchListener.setMyMovementSideListener {
            when (it){
                FindSide.UP-> {
                    repository.moveToUp()
                    describeMatrixToView()
                }
                FindSide.DOWN ->{
                    repository.moveToDown()
                    describeMatrixToView()
                }
                FindSide.LEFT ->{
                    repository.moveToLeft()
                    describeMatrixToView()
                }
                FindSide.RIGHT ->{
                    repository.moveToRight()
                    describeMatrixToView()
                }
            }
        }
        mainView.setOnTouchListener(myTouchListener)
    }

    private fun describeMatrixToView() {
        var df:DecimalFormat = DecimalFormat("0000")
        if (repository.REKORD>9999){
            df = DecimalFormat("00000")
        }
        score.text = df.format(repository.SCORE)


        val _matrix = repository.matrix
        for (i in _matrix.indices) {
            for (j in _matrix[i].indices) {
                items[i * 4 + j].apply {
                    text = if (_matrix[i][j] == 0) ""
                    else _matrix[i][j].toString()

                    setBackgroundResource(util.colorByAmount(_matrix[i][j]))
                }
            }
        }
        if(!repository.isSwipable()){
            mainView.setOnTouchListener(null)
            animatorSet.start()
        }
        rek.text = df.format(repository.REKORD)
    }

    fun loadViews() {
        mainView = findViewById(R.id.mainView)
        score = findViewById(R.id.score)
        rek = findViewById(R.id.record)

        for (i in 0 until mainView.childCount) {
            val linear = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until linear.childCount) {
                items.add(linear.getChildAt(j) as TextView)
            }
        }
        reload = findViewById(R.id.buttonReload)
        infoIMg = findViewById(R.id.img_info)
        reload.setOnClickListener {
            mainView.setOnTouchListener(myTouchListener)
            repository.resetMatrix()
            repository.resetScore()
            describeMatrixToView()
        }
        infoIMg.setOnClickListener {
            startActivity(Intent(this@MainActivity, InfoActivity::class.java))
        }


        val gameOverText = findViewById<TextView>(R.id.gameOverText)
        val fadeOut = ObjectAnimator.ofFloat(gameOverText, View.ALPHA, 1f, 0f)
        fadeOut.duration = 2000
        val fadeIn = ObjectAnimator.ofFloat(gameOverText, View.ALPHA, 0f, 1f)
        fadeIn.duration = 5000
        fadeIn.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                gameOverText.visibility = View.VISIBLE
                for (i in 0 until items.size){
                    items[i].setBackgroundResource(util.colorByAmount(1))
                }
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                fadeOut.start()
                repository.resetScore()
                repository.resetMatrix()
                describeMatrixToView()
                mainView.setOnTouchListener(myTouchListener)
            }
        })
        animatorSet = AnimatorSet()
        animatorSet.play(fadeIn).before(fadeOut)
        animatorSet.startDelay = 100
        gameOverText.text = "Game Over"
    }

    override fun onStop() {
        repository.saveToPref()
        super.onStop()
    }
}
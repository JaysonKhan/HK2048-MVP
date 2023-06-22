package uz.gita.hk2048.ui.info

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import uz.gita.hk2048.R

class InfoActivity : AppCompatActivity() {
   lateinit var home: AppCompatButton
    lateinit var telegram: View
    lateinit var instagram:View
    lateinit var pinterest:View
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        home = findViewById(R.id.buttonHomeInAbout)

        telegram = findViewById(R.id.telegram)
        instagram = findViewById(R.id.instagram)
        pinterest = findViewById(R.id.pinterest)
        home.setOnClickListener { view: View? -> finish() }

        telegram.setOnClickListener { view: View? ->
            val uri = Uri.parse("https://t.me/J_khan347")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        instagram.setOnClickListener { view: View? ->
            val uri = Uri.parse("https://www.instagram.com/j_khan347/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        pinterest.setOnClickListener { view: View? ->
            val uri = Uri.parse("https://www.pinterest.com/betta347/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
package uz.gita.hk2048

import android.app.Application
import uz.gita.hk2048.model.Settings

class App:Application() {
    override fun onCreate() {
        Settings.init(this)
        super.onCreate()
    }
}
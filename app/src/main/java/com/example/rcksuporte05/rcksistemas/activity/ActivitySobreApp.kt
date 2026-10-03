package com.example.rcksuporte05.rcksistemas.activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import com.example.rcksuporte05.rcksistemas.R

class ActivitySobreApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre_app)
        var pInfo: PackageInfo? = null
        var txv_versao_app : TextView = findViewById(R.id.txv_versao__app) as TextView
        try {
            pInfo = packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            val sdkMin  = "21"
            txv_versao_app.text = "Versão do APP/API: $version\nLinguagem utilizada(Java/Kotlin)" +
                    "\nVersão ANDROID compativel com o app >= ANDROID 5.1 Lolipop(SDK 21)"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
}
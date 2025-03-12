package com.example.naiarasamos_rogersantana_act_piano

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private val soundMap = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        soundPool = SoundPool.Builder()
            .setMaxStreams(25)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()

        // Cargar sonidos en el mapa
        val keys = mapOf(
            R.id.key_c2 to R.raw.c2, R.id.key_d2 to R.raw.d2, R.id.key_e2 to R.raw.e2,
            R.id.key_f2 to R.raw.f2, R.id.key_g2 to R.raw.g2, R.id.key_a2 to R.raw.a2, R.id.key_b2 to R.raw.b2,

            R.id.key_c3 to R.raw.c3, R.id.key_d3 to R.raw.d3, R.id.key_e3 to R.raw.e3,
            R.id.key_f3 to R.raw.f3, R.id.key_g3 to R.raw.g3, R.id.key_a3 to R.raw.a3, R.id.key_b3 to R.raw.b3,
            R.id.key_c4 to R.raw.c4,

            R.id.key_a_2 to R.raw.a_2, R.id.key_a_3 to R.raw.a_3, R.id.key_c_2 to R.id.key_c_2,
            R.id.key_c_3 to R.raw.c_3, R.id.key_d_2 to R.raw.d_2, R.id.key_d_3 to R.raw.d_3,
            R.id.key_f_2 to R.raw.f_2, R.id.key_f_3 to R.raw.f_3, R.id.key_g_2 to R.id.key_g_2, R.id.key_g_3 to R.raw.g_3
        )

        // Se añade cada sonido al SoundPool
        keys.forEach {
            (key, sound) -> soundMap[key] = soundPool.load(this, sound, 1)
        }

        // Se asigna el listener a cada botón
        keys.keys.forEach { setKeyListener(it) }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setKeyListener(keyId: Int) {
        val key = findViewById<ImageButton>(keyId)

        key.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                soundMap[keyId]?.let { soundPool.play(it, 1f, 1f, 0, 0, 1f) }

                //Para ver si funciona
                val toast = Toast.makeText(this, keyId.toString(), Toast.LENGTH_SHORT) // in Activity
                toast.show()

                v.performClick()
            }
            true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}

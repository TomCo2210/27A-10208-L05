package dev.tomco.a27a_10208_l05.utilities

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import dev.tomco.a27a_10208_l05.model.Song

class SignalManager private constructor(context: Context) {
    private val context: Context = context.applicationContext

    enum class ToastLength(val length: Int) {
        SHORT(Toast.LENGTH_SHORT),
        LONG(Toast.LENGTH_LONG)
    }

    companion object {
        @Volatile
        private var instance: SignalManager? = null

        fun init(context: Context): SignalManager {
            return instance ?: synchronized(this) {
                instance ?: SignalManager(context).also { instance = it }
            }
        }

        fun getInstance(): SignalManager {
            return instance ?: throw IllegalStateException(
                "SignalManager must be initialized by calling init(context) before use."
            )
        }
    }

    fun toast(text: String, duration: ToastLength) {
        Toast
            .makeText(
                context,
                text,
                duration.length
            )
            .show()
    }

    fun vibrate() {
        val vibrator: Vibrator =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(
                    Context.VIBRATOR_MANAGER_SERVICE
                ) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(
                    Context.VIBRATOR_SERVICE
                ) as Vibrator
            }

        val SOSPattern = longArrayOf(
            0,
            200,
            100,
            200,
            100,
            200,
            300,
            500,
            100,
            500,
            100,
            500,
            300,
            200,
            100,
            200,
            100,
            200
        )

        val waveFormVibrationEffect =
            VibrationEffect
            .createWaveform(
                SOSPattern,
                -1
            )

        val oneShotVibrationEffect =
            VibrationEffect
                .createOneShot(
                    500,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )

        vibrator.vibrate(waveFormVibrationEffect)
//        vibrator.vibrate(oneShotVibrationEffect)
    }
}
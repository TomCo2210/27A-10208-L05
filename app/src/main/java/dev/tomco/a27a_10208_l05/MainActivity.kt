package dev.tomco.a27a_10208_l05

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import dev.tomco.a27a_10208_l05.model.Playlist
import dev.tomco.a27a_10208_l05.utilities.Constants
import dev.tomco.a27a_10208_l05.utilities.DataManager
import dev.tomco.a27a_10208_l05.utilities.DataStoreManager
import dev.tomco.a27a_10208_l05.utilities.FileManager
import dev.tomco.a27a_10208_l05.utilities.ImageLoader
import dev.tomco.a27a_10208_l05.utilities.SharedPreferencesManagerV3
import dev.tomco.a27a_10208_l05.utilities.SignalManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var main_IMG_background: AppCompatImageView

    private lateinit var main_LBL_json: MaterialTextView

    private lateinit var main_BTN_toast_and_vibrate: MaterialButton

    private lateinit var main_BTN_load_image: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()

        //sharedPreferencesExample()
        //filesExample()
        dataStoreExample()


    }

    private fun dataStoreExample() {
        lifecycleScope.launch {
            val dataStore = DataStoreManager.getInstance()

            var playlistAsJson: String = dataStore
                .getString(Constants.DataStore.PLAYLIST_KEY, "")
                .first()

            if (playlistAsJson.isEmpty()){
                playlistAsJson = Gson().toJson(DataManager.generatePlaylist())
                dataStore.putString(Constants.DataStore.PLAYLIST_KEY, playlistAsJson)
                Log.d("Playlist As Json:", playlistAsJson)
            }

            val regeneratedPlaylist: Playlist? = Gson().fromJson(
                playlistAsJson,
                Playlist::class.java
            )
            Log.d("Playlist As Object:", regeneratedPlaylist?.toString()?:"No Playlist")
        }
    }

    private fun filesExample() {
        val playlistAsJson = Gson().toJson(DataManager.generatePlaylist())
        FileManager.writeText(
            this,
            Constants.Files.PLAYLIST_KEY,
            playlistAsJson
        )

        val fromFile: String? = FileManager.readText(
            this,
            Constants.Files.PLAYLIST_KEY
        )
        Log.d("Playlist From File:", fromFile ?: "File Not Found!")
        Log.d("File Path:", filesDir.absolutePath)

    }

    private fun sharedPreferencesExample() {
        val gson = Gson()

        var playlistAsJson = SharedPreferencesManagerV3
            .getInstance()
            .getString(
                Constants.SP_KEYS.PLAYLIST_KEY,
                "")
        Log.d("Playlist As Json:", playlistAsJson)

        if (playlistAsJson.isEmpty()) {
            val playlist: Playlist = DataManager.generatePlaylist()
            Log.d("Playlist Object:", playlist.toString())

            playlistAsJson = gson.toJson(playlist)
            Log.d("Playlist As Json:", playlistAsJson)
        }
        //V1:
//        SharedPreferencesManagerV1
//            .putString(
//                this,
//                Constants.SP_KEYS.PLAYLIST_KEY,
//                playlistAsJson
//            )

        //V2:
//        playlistAsJson = SharedPreferencesManagerV2(this)
//            .getString(
//                Constants.SP_KEYS.PLAYLIST_KEY,
//                playlistAsJson
//            )
//        Log.d("Playlist As Json:", playlistAsJson)
    }

    private fun findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background)
        main_LBL_json = findViewById(R.id.main_LBL_json)
        main_BTN_toast_and_vibrate = findViewById(R.id.main_BTN_toast_and_vibrate)
        main_BTN_load_image = findViewById(R.id.main_BTN_load_image)
    }

    private fun initViews() {
        main_BTN_toast_and_vibrate.setOnClickListener {
            SignalManager
                .getInstance()
                .toast(
                    "Hello World! 🌍",
                    SignalManager.ToastLength.SHORT
                )
            SignalManager
                .getInstance()
                .vibrate()
        }

        main_BTN_load_image.setOnClickListener {
            ImageLoader.getInstance()
                .loadImage(
                    "https://www.philandgarth.com/wp-content/uploads/2024/04/Viennas-best-known-market-is-Christmas-World-on-Rathausplatz-on-City-Hall-square-1920x1280.jpg",
                    main_IMG_background
                )
        }
    }
}
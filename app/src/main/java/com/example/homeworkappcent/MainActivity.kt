package com.example.homeworkappcent

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.homeworkappcent.ui.database.AppRoomDatabase
import com.example.homeworkappcent.ui.database.GameItem
import com.example.homeworkappcent.ui.utils.animateHidden
import com.example.homeworkappcent.ui.utils.animateShown
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_favorite))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //If the database is not loaded, we are not removing the "Loading data" layout.
        val sharedPreferences = getSharedPreferences(SETTINGS, 0)
        if (!sharedPreferences.getBoolean(FIRST_TIME, true)) {
            linearLayout_progress.animateHidden()
            nav_host_fragment.animateShown()
        }

    }

    override fun onResume() {
        super.onResume()
        loadDatabase()
    }

    private fun loadDatabase() {
        val gameDao = AppRoomDatabase.getDatabase(this).gameDao()
        val client = OkHttpClient()

        // request for Game List
        var request = Request.Builder()
            .url("https://rapidapi.p.rapidapi.com/games")
            .get()
            .addHeader("x-rapidapi-host", "rawg-video-games-database.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "99026dadc9msh07a34ecbf02ddc5p1570e0jsn6a90d56b1daf")
            .build()

        Thread {
            try {
                client.newCall(request).execute().use { responseGameList ->
                    if (!responseGameList.isSuccessful) throw IOException("Unexpected code $responseGameList")

                    val jsonGameListObject = JSONObject(responseGameList.body!!.string())
                    val resultsArray = jsonGameListObject.getJSONArray("results")

                    for (i in 0 until resultsArray.length()) {
                        val jsonGameObject = JSONObject(resultsArray[i].toString())
                        val id = jsonGameObject.getString("id")

                        // if nothing is new, then skip.
                        if (gameDao.getGameItemById(id) != null) {
                            continue
                        }

                        // request for Game Details
                        request = Request.Builder()
                            .url("https://rapidapi.p.rapidapi.com/games/$id")
                            .get()
                            .addHeader(
                                "x-rapidapi-host",
                                "rawg-video-games-database.p.rapidapi.com"
                            )
                            .addHeader(
                                "x-rapidapi-key",
                                "99026dadc9msh07a34ecbf02ddc5p1570e0jsn6a90d56b1daf"
                            )
                            .build()

                        client.newCall(request).execute().use { responseGameDetails ->
                            if (!responseGameList.isSuccessful) throw IOException("Unexpected code $responseGameList")

                            val jsonGameDetailsObject =
                                JSONObject(responseGameDetails.body!!.string())

                            val name = jsonGameObject.getString("name")
                            val rating = jsonGameObject.getDouble("rating").toString()
                            val metacritic = jsonGameObject.getInt("metacritic").toString()
                            val image = jsonGameObject.getString("background_image")
                            val released = jsonGameObject.getString("released")
                            val description = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Html.fromHtml(
                                    jsonGameDetailsObject.getString("description"),
                                    Html.FROM_HTML_MODE_LEGACY
                                ).toString()
                            } else {
                                Html.fromHtml(jsonGameDetailsObject.getString("description"))
                                    .toString()
                            }

                            gameDao.addGameItem(
                                GameItem(
                                    id,
                                    name,
                                    rating,
                                    metacritic,
                                    false,
                                    image,
                                    released,
                                    description
                                )
                            )
                        }
                    }
                    val sharedPreferences = getSharedPreferences(SETTINGS, 0)
                    if (sharedPreferences.getBoolean(FIRST_TIME, true)) {
                        // data is loaded, now we can change the UI.
                        runOnUiThread {
                            with(sharedPreferences.edit()) {
                                putBoolean(FIRST_TIME, false)
                                apply()
                            }

                            recreate()
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }.start()
    }

    companion object {
        private const val FIRST_TIME = "firstTime"
        private const val SETTINGS = "settings"
    }
}
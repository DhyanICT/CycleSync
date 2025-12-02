package com.code.chatboat.activity

import android.content.Intent
import android.os.AsyncTask
import com.code.chatboat.BaseActivity
import com.code.chatboat.databinding.ActivityMainBinding
import com.code.chatboat.room.AppDatabase
import com.code.chatboat.utils.CustomExitDialog
import com.code.chatboat.utils.SharedPreferencesManager

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData() {
        val sharedPrefs = SharedPreferencesManager.getInstance(this)

        // Calendar: check if there is any menstrual data and navigate accordingly
        binding.btnCal.setOnClickListener {
            CheckDataExist().execute()
        }

        // Open chatbot using saved language preference (default: "eng")
//        binding.btnBot.setOnClickListener {
//            val typeLanguage = sharedPrefs.getString("language", "eng")
//            startActivity(
//                Intent(this@MainActivity, ChatActivity::class.java)
//                    .putExtra("type", "Levenshtein")
//                    .putExtra("typeLanguage", typeLanguage)
//            )
//        }
        binding.btnBot.setOnClickListener {
            var typeLanguage = sharedPrefs.getString("language", "eng")

            // If someone still has old "guj" saved, force them to English now
            if (typeLanguage == "guj") {
                typeLanguage = "eng"
                sharedPrefs.putString("language", "eng")
            }

            startActivity(
                Intent(this@MainActivity, ChatActivity::class.java)
                    .putExtra("type", "Levenshtein")
                    .putExtra("typeLanguage", typeLanguage)
            )
        }

        // Settings screen
        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }

        // Tracker screen
        binding.btnTracker.setOnClickListener {
            startActivity(Intent(this@MainActivity, TrackerActivity::class.java))
        }
    }

    // Background check for existing menstrual data
    inner class CheckDataExist : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void?): Boolean {
            val items = AppDatabase.getDatabase(this@MainActivity)
                .getDao()
                .getAllMenstrualPeriodData()
            return items.isNotEmpty()
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)
            val next = if (result) {
                MenstrualActivity::class.java
            } else {
                MenstrualEditActivity::class.java
            }
            startActivity(Intent(this@MainActivity, next))
        }
    }

    override fun onBackPressed() {
        val customDialog = CustomExitDialog(this@MainActivity, false)
        customDialog.setTitleText("Are you sure want to exit?")
        customDialog.setMessageText("Yes", "No")
        customDialog.show()
    }
}

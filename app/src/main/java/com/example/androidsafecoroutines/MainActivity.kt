package com.example.androidsafecoroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsafecoroutines.databinding.ActivityMainBinding
import com.example.androidsafecoroutines.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MainFragment.newInstance())
            .commit()
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }
}
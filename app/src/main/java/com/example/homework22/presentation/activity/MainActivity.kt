package com.example.homework22.presentation.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.homework22.R
import com.example.homework22.databinding.ActivityMainBinding
import com.google.android.datatransport.runtime.logging.Logging
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private var request =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavGraph()
        handleNavigation()
        readToken()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            request.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }

    private fun readToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logging.d("Fetching FCM registration token failed", task.exception.toString())
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

        })
    }

    private fun handleNavigation() = binding.apply {
        bottomNavigation.setOnItemSelectedListener { items ->
            when (items.itemId) {
                R.id.homeIcon -> navController.navigate(R.id.homeFragment)
                R.id.chatIcon -> navController.navigate(R.id.messagesFragment)
                R.id.bellIcon -> navController.navigate(R.id.notificationsFragment)
                R.id.heartIcon -> navController.navigate(R.id.favouritesFragment)
            }
            true
        }
    }

    private fun setUpNavGraph() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navController)
    }
}
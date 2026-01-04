package com.maternitytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maternitytracker.data.database.AppDatabase
import com.maternitytracker.data.repository.ShoppingRepository
import com.maternitytracker.ui.screens.HomeScreen
import com.maternitytracker.ui.screens.LabelManagementScreen
import com.maternitytracker.ui.theme.EnhancedMaternityTrackerTheme
import com.maternitytracker.viewmodel.ShoppingViewModel
import com.maternitytracker.viewmodel.ShoppingViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val repository = ShoppingRepository(database.shoppingItemDao(), database.labelDao())
        
        setContent {
            EnhancedMaternityTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: ShoppingViewModel = viewModel(
                        factory = ShoppingViewModelFactory(repository)
                    )
                    
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                viewModel = viewModel,
                                onNavigateToLabelManagement = {
                                    navController.navigate("label_management")
                                }
                            )
                        }
                        composable("label_management") {
                            LabelManagementScreen(
                                viewModel = viewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
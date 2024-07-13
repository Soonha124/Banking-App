package com.example.bankingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankingapp.database.DBHandler
import com.example.bankingapp.screens.allCustomers
import com.example.bankingapp.screens.customerDetails
import com.example.bankingapp.screens.homeScreen
import com.example.bankingapp.screens.profile
import com.example.bankingapp.screens.transferMoney

@Composable
fun navigation(dbHandler: DBHandler) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.homeScreen
    ) {
        composable(Screens.homeScreen) {
            homeScreen(navController = navController)
        }
        composable(Screens.allCustomers) {
            allCustomers(navController, dbHandler)
        }

        composable(Screens.profile) {
            profile(navController = navController)
        }

        composable("${Screens.TransferMoney}/{senderId}") { backStackEntry ->
            val senderId = backStackEntry.arguments?.getString("senderId")?.toInt() ?: 0
            transferMoney(navController = navController, senderId)
        }
        composable("${Screens.customerDetails}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            customerDetails(navController = navController, userId)
        }
    }
}


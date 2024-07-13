package com.example.bankingapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankingapp.Components.ElevatedButtonComponent
import com.example.bankingapp.Components.textComponent
import com.example.bankingapp.R
import com.example.bankingapp.database.DatabaseProvider
import com.example.bankingapp.navigation.Screens

@Composable
fun homeScreen(navController: NavController) {

    Scaffold(

    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            textComponent(
                text = "Welcome to\n" +
                        " Banking App",
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally))
            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = "",
                Modifier
                    .fillMaxWidth()
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ElevatedButtonComponent(
                name = "View All Customers",
                onClick = {
                    navController.navigate(Screens.allCustomers)
                },
                modifier = Modifier
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun homeScreenView()
{
    homeScreen(navController = rememberNavController())
}
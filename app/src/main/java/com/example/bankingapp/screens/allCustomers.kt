package com.example.bankingapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankingapp.Components.Customers
import com.example.bankingapp.Components.ElevatedButtonComponent
import com.example.bankingapp.Components.textComponent
import com.example.bankingapp.R
import com.example.bankingapp.database.DBHandler
import com.example.bankingapp.database.DatabaseProvider
import com.example.bankingapp.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun allCustomers(navController: NavController, dbHandler: DBHandler) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate(Screens.homeScreen)
                        })
                )
            })
        },
        content = { innerPadding ->
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .padding(innerPadding))
            {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                {
                    Image(
                        painter = painterResource(id = R.drawable.customers),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
//                            .size(300.dp)
                    )
                }
                textComponent(
                    text = "All Customers",
                    modifier = Modifier
                )
                    Customers(dbHandler, onClick = { userId ->
                        navController.navigate("${Screens.customerDetails}/$userId")
                    },
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxSize()
                            .padding(top = 10.dp))


                ElevatedButtonComponent(
                    name = "Transfer Money",
                    onClick = {
                        val userId = 0
                        navController.navigate("${Screens.TransferMoney}/$userId")
                    },
                    modifier = Modifier

                )
            }

        })
}

@Preview
@Composable
fun Pre() {
    val navController = rememberNavController()
//    allCustomers(navController, )
}
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bankingapp.Components.ElevatedButtonComponent
import com.example.bankingapp.Components.customerImage
import com.example.bankingapp.Components.textComponent
import com.example.bankingapp.R
import com.example.bankingapp.database.DatabaseProvider
import com.example.bankingapp.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customerDetails(navController: NavController, userId: Int) {
    val dbHandler = DatabaseProvider.getInstance(LocalContext.current)

    val user = dbHandler.getUserById(userId)
    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.details),
                    contentDescription = "details",
                    modifier = Modifier
                        .fillMaxSize()
                        .size(100.dp)
                )
            }


            textComponent(
                text = "Customer Details",
                modifier = Modifier
                    .padding(10.dp)
            )

            user?.let {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 50.dp)
                        .weight(2f),
                )
                {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        customerImage(
                            icon = Icons.Default.Person,
                            imageName = "",
                            color = Color(0xFFCE0303),
                            modifier = Modifier
                        )

                        Text(text = "Name: ${user.name}")
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)

                    ) {
                        customerImage(
                            icon = Icons.Default.Email,
                            imageName = "",
                            color = Color(0xFFCE0303),
                            modifier = Modifier
                        )

                        Text(text = "Email: ${user.email}")

                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.bank_icon),
                            contentDescription = "amount",
                            colorFilter = ColorFilter.tint(Color(0xFFCE0303)),
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Text(text = "Amount: ${user.balance}")

                    }

                }

            } ?: run {
                Text(
                    text = "User not Found",
                    color = Color(0xFFF31010)
                )
            }


            ElevatedButtonComponent(
                name = "Go Back",
                onClick = {
                    navController.navigate(Screens.allCustomers)
//                    navController.navigate(Screens.TransferMoney)
//                    navController.navigate("${Screens.TransferMoney}/${user?.id}")
                },
                modifier = Modifier
            )


        }
    }
}


@Preview(showBackground = true)
@Composable
fun detailPreview() {
    customerDetails(navController = rememberNavController(), 1)
}
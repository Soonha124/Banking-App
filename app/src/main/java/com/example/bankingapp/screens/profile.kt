package com.example.bankingapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bankingapp.navigation.Screens
import com.example.bankingapp.navigation.bottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profile(navController: NavController){
    val items = listOf(
        bottomNavigation(
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
        ),
        bottomNavigation(
            label = "Customers",
            selectedIcon = Icons.Filled.Person,
            unSelectedIcon = Icons.Outlined.Person,
        ),
        bottomNavigation(
            label = "Profile",
            selectedIcon = Icons.Filled.Info,
            unSelectedIcon = Icons.Outlined.Info,
        )
    )
    var selected by rememberSaveable {
        mutableStateOf(2)
    }
    Scaffold (
        topBar = {
            TopAppBar(title = {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable(onClick = {
                            navController.navigate(Screens.homeScreen)
                        }))
            })
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        label = {
                            Text(text = item.label)
                        },
                        selected = index == selected,
                        onClick = {
                            selected = index
                            navController.navigate(Screens.profile)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selected) {
                                    item.selectedIcon
                                } else item.unSelectedIcon,
                                contentDescription = ""
                            )
                        },)
                }
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /*TODO*/ },
//                    icon = {
//                        Icon(Icons.Default.Home,
//                            contentDescription = "")
//                    },
//                    label = { Text(text = "Home")}
//                )
            }
        },
        content = {
                paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
            {
                Text(
                    text = "All Customers"
                )
            }
        })
}
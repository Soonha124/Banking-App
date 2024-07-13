package com.example.bankingapp.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bankingapp.Components.ElevatedButtonComponent
import com.example.bankingapp.Components.textComponent
import com.example.bankingapp.R
import com.example.bankingapp.database.DatabaseProvider
import com.example.bankingapp.database.Transfer
import com.example.bankingapp.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun transferMoney(navController: NavController, senderId: Int) {
    val dbHandler = DatabaseProvider.getInstance(LocalContext.current)

    var amount by remember { mutableStateOf("") }
    val users = dbHandler.getAllUsers().filter { it.id != senderId }
    val context = LocalContext.current

    var showSenderDialog by remember { mutableStateOf(false) }
    var showReceiverDialog by remember { mutableStateOf(false) }
    var selectedSenderId by remember { mutableStateOf<Int?>(null) }
    var selectedReceiverId by remember { mutableStateOf<Int?>(null) }

    Scaffold(topBar = {
        TopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    Modifier.clickable(onClick = {
                        navController.navigate(Screens.allCustomers)
                    })
                )

            }
        })
    }) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            textComponent(
                text = "Transfer Money",
                modifier = Modifier
            )

            OutlinedTextField(
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.money),
                    contentDescription = "",
                        modifier = Modifier.size(24.dp))},
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier)
            {
                Card(
                    onClick = { showSenderDialog = true },
                    shape = RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp),
                    modifier = Modifier
                        .height(200.dp)
                        .weight(1f),
                    colors = CardDefaults.elevatedCardColors(
                        Color(0xFF00B107)
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 30.dp,
                        focusedElevation = 10.dp
                    ),
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Icon(Icons.Outlined.Person,
                                contentDescription = "Sender",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(5.dp))
                            Text(
                                text = "Select Sender",
                                color = Color(0xFFFFFFFF),
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Italic,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }
                )

                Card(
                    onClick = { showReceiverDialog = true },
                    shape = RoundedCornerShape(topEnd = 16.dp, bottomStart = 16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 30.dp,
                        focusedElevation = 10.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        Color(0xFFFFFFFF)
                    ),
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(Icons.Outlined.Person,
                                contentDescription = "Receiver",
                                tint = Color(0xFF00B107),
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                            Text(
                                text = "Select Receiver",
                                color = Color(0xFF00B107),
                                fontSize = 20.sp,
                                fontStyle = FontStyle.Italic,
                                fontFamily = FontFamily.Serif,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Sender: ${users.find { it.id == selectedSenderId }?.name ?: ""}",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Recipient: ${users.find { it.id == selectedReceiverId }?.name ?: ""}",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }



            if (showSenderDialog) {
                AlertDialog(
                    iconContentColor = Color(0xFF00C408),
                    containerColor = Color(0xFFFFFFFF),
                    icon = { Icon(painter = painterResource(id = R.drawable.bank_icon),
                        contentDescription = "")},
                    onDismissRequest = { showSenderDialog = false },
                    title = { Text("Select Sender",
                        color = Color(0xFF00C408)) },
                    text = {
                        LazyColumn {
                            items(users) { user ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedSenderId = user.id
                                            showSenderDialog = false
                                        }
                                        .padding(16.dp)
                                ) {
                                    Text(user.name)
                                    Text(text = user.balance.toString(),
                                        color  = Color(0xFF00C408))
                                }
                            }
                        }
                    },
                    confirmButton = {
                        ElevatedButtonComponent(name = "Close",
                            onClick = {showSenderDialog = false })
                    }
                )
            }

            if (showReceiverDialog) {
                AlertDialog(
                    iconContentColor = Color(0xFF00C408),
                    containerColor = Color(0xFFFFFFFF),
                    icon = { Icon(painter = painterResource(id = R.drawable.bank_icon),
                        contentDescription = "")},
                    onDismissRequest = { showReceiverDialog = false },
                    title = { Text("Select Recipient",
                        color = Color(0xFF00C408)
                    ) },
                    text = {
                        LazyColumn {
                            items(users) { user ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedReceiverId = user.id
                                            showReceiverDialog = false
                                        }
                                        .padding(16.dp)
                                ) {
                                    Text(user.name)
                                    Text(text = user.balance.toString(),
                                        color  = Color(0xFF00C408))
                                }
                            }
                        }
                    },
                    confirmButton = {
                        ElevatedButtonComponent(name = "Close",
                            onClick = {showReceiverDialog = false })
                    }
                )
            }

            ElevatedButtonComponent(
                name = "Transfer",
                onClick = {
                    val transferAmount = amount.toDoubleOrNull()
                    Log.d("TransferInfo", "Sender ID: $selectedSenderId, Recipient ID: $selectedReceiverId, Amount: $transferAmount")

                    if (selectedSenderId != null && selectedReceiverId != null && transferAmount != null && transferAmount > 0) {
                        val sender = users.find { it.id == selectedSenderId }
                        if ((sender?.balance ?: 0.0) >= transferAmount) {
                            val transfer = Transfer(selectedSenderId!!, selectedReceiverId!!, transferAmount)
                            CoroutineScope(Dispatchers.IO).launch {
                                val success = dbHandler.recordTransfer(transfer)
                                withContext(Dispatchers.Main) {
                                    if (success) {
                                        Toast.makeText(context, "Money Transferred", Toast.LENGTH_LONG).show()
                                        navController.navigate(Screens.allCustomers)
                                    } else {
                                        Toast.makeText(context, "Transfer Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(context, "Sender has insufficient balance", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Invalid Amount or Recipients", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

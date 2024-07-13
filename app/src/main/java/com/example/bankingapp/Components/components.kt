package com.example.bankingapp.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.database.DBHandler
@Composable
fun textComponent(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontStyle = FontStyle.Italic,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        color = Color(0xFF38A83C),
        modifier = modifier
    )
}

@Composable
fun ElevatedButtonComponent(
    name: String, onClick: () -> Unit, modifier: Modifier = Modifier,
) {

    ElevatedButton(modifier = modifier,
        onClick = onClick,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 12.dp
        ),
        colors = ButtonDefaults.buttonColors(
            Color(0xFF38A83C)
        )
    ) {
        Text(
            text = name,
        )
    }

}

@Composable
fun customerImage(icon: ImageVector,imageName: String,
                  color: Color, modifier: Modifier ){
    Icon(icon,
        contentDescription = imageName,
        tint = color,
        modifier = modifier)
}

@Composable
fun Customers(dbHandler: DBHandler, onClick: (Int) -> Unit, modifier: Modifier) {
    val users = dbHandler.getAllUsers()

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {items(users.size) {index->
        val user = users[index]

        Divider(
            modifier = Modifier,
            thickness = 2.dp,
            color = Color(0xFF16C701)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable(onClick = { onClick(user.id) }))
        {
            customerImage(icon = Icons.Default.Person,
                imageName = "" ,
                color = Color(0xFFF73BC8),
                modifier = Modifier
                    .size(34.dp))

        Text(text = user.name,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(5.dp)
               )
        }
    }

    }

}



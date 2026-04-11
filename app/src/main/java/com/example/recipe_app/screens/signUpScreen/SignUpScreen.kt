
package com.example.recipe_app

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe_app.ui.theme.Recipe_AppTheme

data class Country(val name: String, val code: String, val flag: String)

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun SignUpScreen(modifier: Modifier= Modifier) {

    val context = LocalContext.current

    var firstName  by remember { mutableStateOf("") }
    var lastName  by remember { mutableStateOf("") }
    var email  by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }

    val mainOrange = Color(0xFFFF7A00)


    var selectedDay by remember { mutableStateOf("Day") }
    var selectedMonth by remember { mutableStateOf("Month") }
    var selectedYear by remember { mutableStateOf("Year") }

    var expandedDay by remember { mutableStateOf(false) }
    var expandedMonth by remember { mutableStateOf(false) }
    var expandedYear by remember { mutableStateOf(false) }

    val days = (1..31).map { it.toString() }
    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    val years = (1970..2025).map { it.toString() }

    var expanded by remember { mutableStateOf(false) }

    val countries = listOf(
        Country("Egypt", "+20", "🇪🇬"),
        Country("USA", "+1", "🇺🇸"),
        Country("UK", "+44", "🇬🇧"),
        Country("Saudi Arabia", "+966", "🇸🇦")
    )

    var selectedCountry by remember {
        mutableStateOf(countries[0])
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = mainOrange,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(28.dp)
                    .clickable {
                    }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Sign Up",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            Column(modifier = Modifier.weight(1f)) {
                Text("First Name")
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = firstName,
                    onValueChange = {
                        if (it.matches(Regex("^[a-zA-Z ]*$"))) {
                            firstName = it
                        }
                    },                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("Last Name")
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = lastName,
                    onValueChange = {
                        if (it.matches(Regex("^[a-zA-Z ]*$"))) {
                            lastName = it
                        }
                    },                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            Text("Email")
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            Text("Birthday")

            Row(modifier = Modifier.fillMaxWidth()) {

                // Day
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(onClick = { expandedDay = true }) {
                        Text(selectedDay)
                    }

                    DropdownMenu(
                        expanded = expandedDay,
                        onDismissRequest = { expandedDay = false }
                    ) {
                        days.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedDay = it
                                    expandedDay = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(onClick = { expandedMonth = true }) {
                        Text(selectedMonth)
                    }

                    DropdownMenu(
                        expanded = expandedMonth,
                        onDismissRequest = { expandedMonth = false }
                    ) {
                        months.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedMonth = it
                                    expandedMonth = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(onClick = { expandedYear = true }) {
                        Text(selectedYear)
                    }

                    DropdownMenu(
                        expanded = expandedYear,
                        onDismissRequest = { expandedYear = false }
                    ) {
                        years.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedYear = it
                                    expandedYear = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            Text("Phone Number")

            Row(modifier = Modifier.fillMaxWidth()) {

                Box {
                    OutlinedButton(onClick = { expanded = true }) {
                        Text("${selectedCountry.flag} ${selectedCountry.code}")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Text(country.flag)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("${country.name} (${country.code})")
                                    }
                                },
                                onClick = {
                                    selectedCountry = country
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))


                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            if (it.matches(Regex("^[0-9]*$"))) {
                                phone = it
                            }
                        },
                        modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            Text("Password")
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (
                    firstName.isEmpty() ||
                    lastName.isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty()
                ) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()

                } else if (!isValidEmail(email)) {
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()

                } else if (
                    selectedDay == "Day" ||
                    selectedMonth == "Month" ||
                    selectedYear == "Year"
                ) {
                    Toast.makeText(context, "Select valid birthday", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = mainOrange),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Or login with", color = Color.Gray)

        Spacer(modifier = Modifier.height(15.dp))



        Row {
            Text("Already have an account? ")

            Text(
                text = "Log In",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                }
            )
        }
    }
}
@Preview
@Composable
fun SignUpScreenPreview() {
    Recipe_AppTheme {
Scaffold() {
    InnerPadding->
    SignUpScreen(Modifier.padding(InnerPadding))
}

    }
}
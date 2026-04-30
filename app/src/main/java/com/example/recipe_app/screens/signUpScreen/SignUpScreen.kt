package com.example.recipe_app.screens.signUpScreen

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe_app.ui.theme.Recipe_AppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest

data class Country(val name: String, val code: String, val flag: String)

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

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
    val years = (1970..2026).map { it.toString() }

    var expandedCountry by remember { mutableStateOf(false) }

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
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = mainOrange,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(28.dp)
                    .clickable { onLoginClick() }
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
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
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
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Email")
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Birthday")
            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expandedDay = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedDay)
                    }

                    DropdownMenu(
                        expanded = expandedDay,
                        onDismissRequest = { expandedDay = false }
                    ) {
                        days.forEach { day ->
                            DropdownMenuItem(
                                text = { Text(day) },
                                onClick = {
                                    selectedDay = day
                                    expandedDay = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expandedMonth = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedMonth)
                    }

                    DropdownMenu(
                        expanded = expandedMonth,
                        onDismissRequest = { expandedMonth = false }
                    ) {
                        months.forEach { month ->
                            DropdownMenuItem(
                                text = { Text(month) },
                                onClick = {
                                    selectedMonth = month
                                    expandedMonth = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { expandedYear = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(selectedYear)
                    }

                    DropdownMenu(
                        expanded = expandedYear,
                        onDismissRequest = { expandedYear = false }
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(year) },
                                onClick = {
                                    selectedYear = year
                                    expandedYear = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Phone Number")
            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                Box {
                    OutlinedButton(onClick = { expandedCountry = true }) {
                        Text("${selectedCountry.flag} ${selectedCountry.code}")
                    }

                    DropdownMenu(
                        expanded = expandedCountry,
                        onDismissRequest = { expandedCountry = false }
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
                                    expandedCountry = false
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
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Password")
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                when {
                    firstName.isBlank() ||
                            lastName.isBlank() ||
                            email.isBlank() ||
                            phone.isBlank() ||
                            password.isBlank() -> {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }

                    !isValidEmail(email.trim()) -> {
                        Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
                    }

                    password.length < 6 -> {
                        Toast.makeText(
                            context,
                            "Password must be at least 6 characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    selectedDay == "Day" ||
                            selectedMonth == "Month" ||
                            selectedYear == "Year" -> {
                        Toast.makeText(context, "Select valid birthday", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        isLoading = true

                        auth.createUserWithEmailAndPassword(email.trim(), password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser

                                    val profileUpdates = UserProfileChangeRequest.Builder()
                                        .setDisplayName("$firstName $lastName")
                                        .build()

                                    user?.updateProfile(profileUpdates)

                                    user?.sendEmailVerification()
                                        ?.addOnCompleteListener { verifyTask ->
                                            isLoading = false

                                            auth.signOut()

                                            if (verifyTask.isSuccessful) {
                                                Log.d(
                                                    "SignUpDebug",
                                                    "User created and verification sent: uid=${user.uid}, email=${user.email}"
                                                )

                                                Toast.makeText(
                                                    context,
                                                    "Account created. Please verify your email first.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } else {
                                                Log.e(
                                                    "SignUpDebug",
                                                    "Verification email failed",
                                                    verifyTask.exception
                                                )

                                                Toast.makeText(
                                                    context,
                                                    "Account created, but verification email was not sent.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }

                                            onLoginClick()
                                        }
                                } else {
                                    isLoading = false

                                    val e = task.exception
                                    Log.e("SignUpDebug", "createUser failed", e)

                                    val message = when (e) {
                                        is FirebaseAuthUserCollisionException ->
                                            "This email is already registered"

                                        is FirebaseAuthWeakPasswordException ->
                                            "Weak password"

                                        is FirebaseAuthInvalidCredentialsException ->
                                            "Invalid email format"

                                        else ->
                                            e?.localizedMessage ?: "Registration failed"
                                    }

                                    Toast.makeText(
                                        context,
                                        message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = mainOrange),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isLoading) "Loading..." else "Register",
                color = Color.White
            )
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
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    Recipe_AppTheme {
        Scaffold { innerPadding ->
            SignUpScreen(
                modifier = Modifier.padding(innerPadding),
                onLoginClick = {}
            )
        }
    }
}
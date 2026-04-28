package com.example.recipe_app.screens.signInScreen


import androidx.compose.material.icons.filled.Visibility
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe_app.R


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInScreen() {
    val context = LocalContext.current
    Column( modifier = Modifier.fillMaxSize() ) {

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxSize().padding(28.dp))
        {

            Row( modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
            {

                Spacer(modifier = Modifier.weight(1f))

                Text(

                    text = "Sign In",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp

import com.example.recipe_app.ui.theme.Recipe_AppTheme
import com.google.firebase.auth.FirebaseAuth

fun isValidLoginEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUp: () -> Unit = {},
    onLogin: () -> Unit = {},
    onContinueAsGuest: () -> Unit = {}
) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var userEmailAddress by rememberSaveable { mutableStateOf("") }
    var userPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser
        currentUser?.reload()?.addOnCompleteListener {
            val updatedUser = auth.currentUser

            if (updatedUser != null && updatedUser.isEmailVerified) {
                onLogin()
            } else {
                auth.signOut()
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Sign In",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))


            Image(
                painter = painterResource(id = R.drawable.download),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome Back",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to access your saved recipes",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))
            var isError by remember { mutableStateOf(false) }
            var userEmailAddress by rememberSaveable {mutableStateOf("") }
            OutlinedTextField(
                value = userEmailAddress,
                onValueChange = {userEmailAddress=it},
                placeholder = { Text("Enter your email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Icon")
                },
                isError = isError,
                supportingText = {
                    if (isError) {

            OutlinedTextField(
                value = userEmailAddress,
                onValueChange = {
                    userEmailAddress = it
                    emailError = false
                },
                placeholder = { Text("Enter your email") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Email Icon")
                },
                isError = emailError,
                supportingText = {
                    if (emailError) {
                        Text("Enter a valid email")
                    }
                },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.fillMaxWidth()
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            var userPassword by rememberSaveable { mutableStateOf("") }
            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                placeholder = { Text("Enter your password") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                    Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                },
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = null
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.fillMaxWidth()
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { Toast.makeText(context,"going to Home screen", Toast.LENGTH_SHORT).show()},
                onClick = {
                    when {
                        userEmailAddress.isBlank() || userPassword.isBlank() -> {
                            Toast.makeText(
                                context,
                                "Please enter email and password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        !isValidLoginEmail(userEmailAddress.trim()) -> {
                            emailError = true
                            Toast.makeText(
                                context,
                                "Invalid email address",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        userPassword.length < 6 -> {
                            Toast.makeText(
                                context,
                                "Password must be at least 6 characters",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            isLoading = true

                            auth.signInWithEmailAndPassword(
                                userEmailAddress.trim(),
                                userPassword
                            ).addOnCompleteListener { task ->

                                if (task.isSuccessful) {
                                    val user = auth.currentUser

                                    user?.reload()?.addOnCompleteListener { reloadTask ->
                                        isLoading = false

                                        if (reloadTask.isSuccessful) {
                                            val updatedUser = auth.currentUser

                                            if (updatedUser != null && updatedUser.isEmailVerified) {
                                                Toast.makeText(
                                                    context,
                                                    "Login successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                onLogin()
                                            } else {
                                                auth.signOut()

                                                Toast.makeText(
                                                    context,
                                                    "Please verify your email first",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        } else {
                                            auth.signOut()

                                            Toast.makeText(
                                                context,
                                                "Failed to refresh verification status",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                } else {
                                    isLoading = false

                                    Toast.makeText(
                                        context,
                                        task.exception?.localizedMessage ?: "Authentication failed",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF47B25)
                )
            ) {
                Text("Sign In", color = Color.White,fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Text(
                    text = if (isLoading) "Loading..." else "Sign In",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("OR CONTINUE WITH", color = Color.Gray,fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(
                text = "OR CONTINUE WITH",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Icon(  painter = painterResource(id = R.drawable.search),
                    contentDescription = null,  modifier = Modifier.size(40.dp).clickable{ Toast.makeText(context,"Sign in with Google",
                        Toast.LENGTH_SHORT).show()},
                    tint = Color.Unspecified)
                Icon(  painter = painterResource(id = R.drawable.facebook),modifier = Modifier.size(40.dp).clickable{ Toast.makeText(context,"Sign in with Facebook",
                    Toast.LENGTH_SHORT).show()},tint = Color.Unspecified
                    ,
                    contentDescription = null)
                Icon(  painter = painterResource(id = R.drawable.twitter),modifier = Modifier.size(40.dp).clickable{ Toast.makeText(context,"Sign in with X",
                    Toast.LENGTH_SHORT).show()},tint = Color.Unspecified
                    ,
                    contentDescription = null)


                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            Toast.makeText(
                                context,
                                "Google sign in not added yet",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    tint = Color.Unspecified
                )

                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            Toast.makeText(
                                context,
                                "Facebook sign in not added yet",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    tint = Color.Unspecified
                )

                Icon(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            Toast.makeText(
                                context,
                                "X sign in not added yet",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Continue as Guest",
                fontSize=16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF47B25),  modifier = Modifier.align(Alignment.CenterHorizontally)
                    .clickable{Toast.makeText(context, "Continue as guest", Toast.LENGTH_SHORT).show()}
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF47B25),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onContinueAsGuest()
                    }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account? ", color = Color.Gray)
                Text("Sign Up", color = Color(0xFFF47B25), modifier = Modifier.clickable{ Toast.makeText(context,"Sign Up",
                    Toast.LENGTH_SHORT).show()}, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

        }
    }
}

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don't have an account? ", color = Color.Gray)

                Text(
                    text = "Sign Up",
                    color = Color(0xFFF47B25),
                    modifier = Modifier.clickable {
                        onSignUp()
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    Recipe_AppTheme {
        SignInScreen()
    }
}

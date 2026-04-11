package com.example.recipe_app.screens.signInScreen


import androidx.compose.material.icons.filled.Visibility
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
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUp : () -> Unit,
    onLogin : () -> Unit
    ) {
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
                        Text("Enter a valid email")
                    }
                },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.fillMaxWidth()
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
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onLogin ,
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
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("OR CONTINUE WITH", color = Color.Gray,fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally))

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


            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Continue as Guest",
                fontSize=16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF47B25),  modifier = Modifier.align(Alignment.CenterHorizontally)
                    .clickable{Toast.makeText(context, "Continue as guest", Toast.LENGTH_SHORT).show()}
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account? ", color = Color.Gray)
                Text("Sign Up", color = Color(0xFFF47B25), modifier = Modifier.clickable{
                    onSignUp()
                    Toast.makeText(context,"Sign Up",
                    Toast.LENGTH_SHORT).show()}, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

        }
    }
}


package com.example.recipe_app.screens.profileScreen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import coil.compose.AsyncImage
import com.example.recipe_app.service.AccountService
import com.example.recipe_app.service.UserProfile
import com.example.recipe_app.ui.theme.OrangeVariant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.avatarDataStore by preferencesDataStore(name = "avatar_prefs")




private val avatarColors = listOf(
    Color(0xFFE6D7CC),
    Color(0xFFFFD6D6),
    Color(0xFFD6EFFF),
    Color(0xFFE3D7FF),
    Color(0xFFD9F5E5),
    Color(0xFFFFE7C2)
)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    accountService: AccountService,
    favoriteCount: String,
    plannedMeal: String = "Next: Grilled Salmon Tonight",
    onFavoriteMealsClick: () -> Unit = {},
    onPlannedMealsClick: () -> Unit = {},
    onLogOutComplete: () -> Unit
) {
    val userProfile by accountService.userProfile.collectAsState(
        initial = UserProfile("Loading...", "", null)
    )

    val context = LocalContext.current
    val avatarPreferences = remember { AvatarPreferences(context) }
    val selectedAvatarIndex by avatarPreferences.selectedAvatar.collectAsState(initial = 0)
    val coroutineScope = rememberCoroutineScope()

    var showAvatarDialog by rememberSaveable { mutableStateOf(false) }
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var profileImageUrl by rememberSaveable { mutableStateOf<String?>(userProfile.photoUrl) }

    LaunchedEffect(userProfile.photoUrl) {
        profileImageUrl = userProfile.photoUrl
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                val result = accountService.uploadProfileImage(it)
                if (result != null) {
                    profileImageUrl = result
                }
            }
        }
    }

    if (showAvatarDialog) {
        AlertDialog(
            onDismissRequest = { showAvatarDialog = false },
            title = {
                Text(
                    text = "Choose your avatar",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(avatarColors) { index, color ->
                        AvatarOption(
                            color = color,
                            selected = selectedAvatarIndex == index
                        ) {
                            coroutineScope.launch {
                                avatarPreferences.saveSelectedAvatar(index)
                            }
                            showAvatarDialog = false
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAvatarDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Logout",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(text = "Are you sure you want to log out?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        accountService.signOut()
                        onLogOutComplete()
                    }
                ) {
                    Text(
                        text = "Yes",
                        color = Color(0xFFE76F51)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text(text = "No")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFCFAF7))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .padding(bottom = 90.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    if (profileImageUrl != null) {
                        AsyncImage(
                            model = profileImageUrl,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        SelectedAvatar(
                            color = avatarColors[selectedAvatarIndex],
                            modifier = Modifier.clickable { showAvatarDialog = true }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(OrangeVariant)
                            .clickable { launcher.launch("image/*") }
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CameraAlt,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Change avatar",
                    color = OrangeVariant,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { showAvatarDialog = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userProfile.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = userProfile.joinDate,
            color = Color(0xFF9A948E),
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        SectionTitle("MY KITCHEN")

        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuCard(
            icon = Icons.Outlined.FavoriteBorder,
            iconTint = Color(0xFFF4A261),
            title = "Favorite Meals",
            subtitle = favoriteCount,
            onClick = onFavoriteMealsClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuCard(
            icon = Icons.Outlined.CalendarMonth,
            iconTint = Color(0xFFF4A261),
            title = "Planned Meals",
            subtitle = plannedMeal,
            onClick = onPlannedMealsClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color(0xFFE8E1DA)
        )

        SectionTitle("ACCOUNT")

        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuRow(
            icon = Icons.Outlined.PersonOutline,
            iconTint = Color(0xFF7D8CA3),
            title = "Edit Profile",
            onClick = { showAvatarDialog = true }
        )

        Spacer(modifier = Modifier.height(6.dp))

        ProfileMenuRow(
            icon = Icons.Outlined.Logout,
            iconTint = Color(0xFFE76F51),
            title = if (accountService.isGuest()) "Login" else "Logout",
            titleColor = if (!accountService.isGuest()) Color(0xFFE76F51) else Color(0xFF222222),
            showArrow = false,
            onClick = {
                if (!accountService.isGuest()) {
                    showLogoutDialog = true
                } else {
                    accountService.signOut()
                    onLogOutComplete()
                }
            }
        )
    }
}

@Composable
fun SelectedAvatar(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(110.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.PersonOutline,
            contentDescription = null,
            tint = Color(0xFF7A6F68),
            modifier = Modifier.size(56.dp)
        )
    }
}

@Composable
fun AvatarOption(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(78.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) OrangeVariant else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.PersonOutline,
            contentDescription = null,
            tint = Color(0xFF6E625A),
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xFF9A948E),
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun ProfileMenuCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        tonalElevation = 1.dp,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconTint.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF222222)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = Color(0xFF9A948E)
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFB8B2AC)
            )
        }
    }
}

@Composable
fun ProfileMenuRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    titleColor: Color = Color(0xFF222222),
    showArrow: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = titleColor,
            modifier = Modifier.weight(1f)
        )

        if (showArrow) {
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFB8B2AC)
            )
        }
    }
}
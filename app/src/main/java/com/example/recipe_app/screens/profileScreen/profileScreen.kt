package com.example.recipe_app.screens.profileScreen

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.recipe_app.R
import com.example.recipe_app.screens.profileScreen.viewmodel.ThemeViewModel
import com.example.recipe_app.service.AccountService
import com.example.recipe_app.service.UserProfile
import com.example.recipe_app.ui.theme.OrangeVariant
import kotlinx.coroutines.launch

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
    themeViewModel: ThemeViewModel,
    favoriteCount: String,
    onFavoriteMealsClick: () -> Unit = {},
    onLogOutComplete: () -> Unit
) {
    val userProfile by accountService.userProfile.collectAsState(
        initial = UserProfile("Loading...", "", null)
    )

    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val avatarPreferences = remember { AvatarPreferences(context) }
    val selectedAvatarIndex by avatarPreferences.selectedAvatar.collectAsState(initial = 0)
    val coroutineScope = rememberCoroutineScope()

    var showAvatarDialog by rememberSaveable { mutableStateOf(false) }
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var showLanguageDialog by rememberSaveable { mutableStateOf(false) }
    var profileImageUrl by rememberSaveable { mutableStateOf<String?>(userProfile.photoUrl) }

    LaunchedEffect(userProfile.photoUrl) {
        profileImageUrl = userProfile.photoUrl
    }

    // Language Dialog (Omitted for brevity, kept same as your previous code)
    if (showLanguageDialog) {
        LanguageSelectionDialog(onDismiss = { showLanguageDialog = false })
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
        AvatarSelectionDialog(
            selectedAvatarIndex = selectedAvatarIndex,
            onAvatarSelected = { index ->
                coroutineScope.launch { avatarPreferences.saveSelectedAvatar(index) }
                showAvatarDialog = false
            },
            onDismiss = { showAvatarDialog = false }
        )
    }

    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                accountService.signOut()
                onLogOutComplete()
            },
            onDismiss = { showLogoutDialog = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .padding(bottom = 90.dp)
    ) {
        // Profile Header Section
        ProfileHeader(
            profileImageUrl = profileImageUrl,
            selectedAvatarIndex = selectedAvatarIndex,
            userName = userProfile.name,
            joinDate = userProfile.joinDate,
            onAvatarClick = { showAvatarDialog = true },
            onCameraClick = { launcher.launch("image/*") }
        )

        Spacer(modifier = Modifier.height(28.dp))

        SectionTitle(stringResource(R.string.my_kitchen))
        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuCard(
            icon = Icons.Outlined.FavoriteBorder,
            iconTint = Color(0xFFF4A261),
            title = stringResource(R.string.favorite_meals),
            subtitle = favoriteCount,
            onClick = onFavoriteMealsClick
        )
        


        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(stringResource(R.string.account))
        Spacer(modifier = Modifier.height(12.dp))

        // Dark Mode Toggle
        ProfileToggleRow(
            icon = Icons.Outlined.DarkMode,
            iconTint = Color(0xFF9C27B0),
            title = "Dark Mode",
            isChecked = isDarkMode,
            onCheckedChange = { themeViewModel.toggleTheme(it) }
        )
        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuRow(
            icon = Icons.Outlined.Language,
            iconTint = Color(0xFF4A90E2),
            title = stringResource(R.string.language),
            onClick = { showLanguageDialog = true }
        )
        ProfileMenuRow(
            icon = if (accountService.isGuest()) Icons.Outlined.Login else Icons.Outlined.Logout,
            iconTint = Color(0xFFE76F51),
            title = if (accountService.isGuest()) stringResource(R.string.login) else stringResource(R.string.logout),
            titleColor = if (!accountService.isGuest()) Color(0xFFE76F51) else MaterialTheme.colorScheme.onSurface,
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
fun ProfileToggleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = OrangeVariant
            )
        )
    }
}

@Composable
fun ProfileHeader(
    profileImageUrl: String?,
    selectedAvatarIndex: Int,
    userName: String,
    joinDate: String,
    onAvatarClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(contentAlignment = Alignment.BottomEnd) {
            if (profileImageUrl != null) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile",
                    modifier = Modifier.size(110.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                SelectedAvatar(
                    color = avatarColors[selectedAvatarIndex],
                    modifier = Modifier.clickable { onAvatarClick() }
                )
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(OrangeVariant)
                    .clickable { onCameraClick() }
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
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
            text = stringResource(R.string.change_avatar),
            color = OrangeVariant,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onAvatarClick() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = userName,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = joinDate,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.secondary,
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
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconTint.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = subtitle, fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
            }
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
fun ProfileMenuRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    titleColor: Color = Color.Unspecified,
    showArrow: Boolean = true,
    onClick: () -> Unit
) {
    val finalTitleColor = if (titleColor == Color.Unspecified) MaterialTheme.colorScheme.onSurface else titleColor
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, color = finalTitleColor, modifier = Modifier.weight(1f))
        if (showArrow) {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
        }
    }
}

// Dialog helper components
@Composable
fun LanguageSelectionDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.language), fontWeight = FontWeight.Bold) },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                TextButton(onClick = {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                    onDismiss()
                }) { Text("English", fontSize = 18.sp) }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                    onDismiss()
                }) { Text("العربية", fontSize = 18.sp) }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text(text = stringResource(R.string.cancel)) } }
    )
}

@Composable
fun AvatarSelectionDialog(selectedAvatarIndex: Int, onAvatarSelected: (Int) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.choose_your_avatar), fontWeight = FontWeight.Bold) },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth().height(220.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(avatarColors) { index, color ->
                    AvatarOption(color = color, selected = selectedAvatarIndex == index) { onAvatarSelected(index) }
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text(text = stringResource(R.string.cancel)) } }
    )
}

@Composable
fun LogoutConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.logout), fontWeight = FontWeight.Bold) },
        text = { Text(text = stringResource(R.string.are_you_sure_you_want_to_log_out)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.yes), color = Color(0xFFE76F51))
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(text = stringResource(R.string.no)) } }
    )
}

@Composable
fun SelectedAvatar(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(110.dp).clip(CircleShape).background(color), contentAlignment = Alignment.Center) {
        Icon(imageVector = Icons.Outlined.PersonOutline, contentDescription = null, tint = Color(0xFF7A6F68), modifier = Modifier.size(56.dp))
    }
}

@Composable
fun AvatarOption(color: Color, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(78.dp).clip(CircleShape).background(color)
            .border(width = if (selected) 3.dp else 1.dp, color = if (selected) OrangeVariant else Color.Transparent, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = Icons.Outlined.PersonOutline, contentDescription = null, tint = Color(0xFF6E625A), modifier = Modifier.size(36.dp))
    }
}

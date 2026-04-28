package com.example.recipe_app.screens.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe_app.ui.theme.Recipe_AppTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userName: String = "Lamiaa Ahmed",
    joinedDate: String = "Joined April 2024",
    favoriteCount: String = "10 items saved",
    plannedMeal: String = "Next: Grilled Salmon Tonight",
    onFavoriteMealsClick: () -> Unit = {},
    onPlannedMealsClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFCFAF7))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6D7CC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = "Profile",
                    tint = Color(0xFF7A6F68),
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userName,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = joinedDate,
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

        HorizontalDivider(color = Color(0xFFE8E1DA))

        Spacer(modifier = Modifier.height(18.dp))

        SectionTitle("ACCOUNT")

        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuRow(
            icon = Icons.Outlined.PersonOutline,
            iconTint = Color(0xFF7D8CA3),
            title = "Edit Profile",
            onClick = onEditProfileClick
        )

        Spacer(modifier = Modifier.height(6.dp))

        ProfileMenuRow(
            icon = Icons.AutoMirrored.Outlined.Logout,
            iconTint = Color(0xFFE76F51),
            title = "Logout",
            titleColor = Color(0xFFE76F51),
            showArrow = false,
            onClick = onLogoutClick
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xFF9A948E),
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun ProfileMenuCard(
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

            Column(modifier = Modifier.weight(1f)) {
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
private fun ProfileMenuRow(
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    Recipe_AppTheme {
        MaterialTheme {
            ProfileScreen()
        }
    }
}


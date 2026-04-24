package com.example.recipe_app.screens.detailsScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeScreen(videoUrl: String,modifier: Modifier = Modifier) {
    val videoId = remember(videoUrl) {
        if (videoUrl.contains("v=")) {
            videoUrl.split("v=")[1].split("&")[0]
        } else {
            videoUrl.split("/").last()
        }
    }

    key(videoId) {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp)),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    })
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun YoutubeScreenPreview(){
//    YoutubeScreen("Na-6-lzs2wU")
}
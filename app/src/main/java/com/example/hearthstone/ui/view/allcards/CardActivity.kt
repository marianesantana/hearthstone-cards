package com.example.hearthstone.ui.view.allcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.hearthstone.ui.theme.HearthstoneTheme
import com.example.hearthstone.ui.view.cardbacks.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardActivity : ComponentActivity() {

    @Composable
    fun ScrollableSample(cardId: Int) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {

            val viewModel: MainViewModel = hiltViewModel()

            val cardName = viewModel.state.observeAsState().value?.cards?.get(cardId)?.name
            val cardImage = viewModel.state.observeAsState().value?.cards?.get(cardId)?.img
            val cardDescription = viewModel.state.observeAsState().value?.cards?.get(cardId)?.description

            val isLoading = viewModel.state.observeAsState().value?.isLoading
            val strokeWidth = 5.dp

            if(isLoading==true) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .drawBehind {
                        drawCircle(
                            Color.Blue,
                            radius = size.width / 2 - strokeWidth.toPx() / 2,
                            style = Stroke(strokeWidth.toPx())

                        )
                    },
                    color = Color.Gray,
                    strokeWidth = strokeWidth,
                )}
            else {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = cardName.toString(),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(14.dp))


                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = cardImage).apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                }).build()
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),

                        contentDescription = cardName
                    )

                    Spacer(modifier = Modifier.padding(14.dp))


                    Text(text = cardDescription.toString())


                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HearthstoneTheme {
                Surface(
                    modifier = Modifier.background(Color(253, 240, 217)) ){

                }
                initComponent()

            }

        }
    }

    @Composable
    private fun initComponent() {
        val cardId = intent.getIntExtra(CARD_ID_EXTRA, 0)
        ScrollableSample(cardId = cardId)

    }

    companion object {
        const val CARD_ID_EXTRA = "cardBackId"
    }
}


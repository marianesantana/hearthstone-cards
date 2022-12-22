package com.example.hearthstone.ui.view.cardbacks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hearthstone.ui.theme.HearthstoneTheme
import com.example.hearthstone.ui.view.allcards.CardActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HearthstoneTheme {

                Surface {
                        ScrollableSample()
                }

            }

        }
}

    @Composable
    fun ScrollableSample() {
        val viewModel: MainViewModel = hiltViewModel()
        val cards = viewModel.state.observeAsState().value?.cards
        val isLoading = viewModel.state.observeAsState().value?.isLoading
        val strokeWidth = 5.dp


        Column(
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState(), true
                )
                .padding(32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {
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
                        strokeWidth = strokeWidth
                    )}
            else {
                cards?.let {
                    for(card in cards){
                        Text(
                            text = card?.name.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,

                            modifier = Modifier.clickable {
                                if (card != null) {
                                    onClick(card.cardBackId)
                                }
                            }

                        )
                        Text(
                            text = card?.description.toString()

                        )

                        Spacer(modifier = Modifier.padding(12.dp))

                    }

                }

            }

        }
    }


    fun onClick(cardId: Int) {
        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra("cardBackId", cardId)
        startActivity(intent)
    }
}




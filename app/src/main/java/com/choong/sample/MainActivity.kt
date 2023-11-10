package com.choong.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.choong.extern.api.url.ExternUrlParser
import com.choong.sample.ui.theme.AndroidToolsTheme

class MainActivity : ComponentActivity() {
    val urlParser = ExternUrlParser()

    val testUrl1 = "https://play.google.com/store/apps/details?id=com.strava";
    val testUrl2 = "https://m.onestore.co.kr/mobilepoc/apps/appsDetail.omp?prodId=0000768118"
    val testUrl3 = "https://m.onestore.net/en-sg/apps/appsDetail?prodId=0000765594"
    val testUrl4 = "https://ones.to/0000765594"
    val testUrl5 = "https://onesto.re/0000765594"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidToolsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (modifier = Modifier.fillMaxWidth()) {
                        Greeting(testUrl1 + "\n" +urlParser.parsingUrl(testUrl1).toString())
                        Greeting(testUrl2 + "\n" +urlParser.parsingUrl(testUrl2).toString())
                        Greeting(testUrl3 + "\n" +urlParser.parsingUrl(testUrl3).toString())
                        Greeting(testUrl4 + "\n" +urlParser.parsingUrl(testUrl4).toString())
                        Greeting(testUrl5 + "\n" +urlParser.parsingUrl(testUrl5).toString())
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "$name!",
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidToolsTheme {
        Greeting("Android")
    }
}
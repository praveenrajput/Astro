package com.praveen.astro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.praveen.astro.api.AstrosApi
import com.praveen.astro.data.network.ktorHttpClient
import com.praveen.astro.models.Astros
import com.praveen.astro.models.People
import com.praveen.astro.ui.theme.AstroTheme
import com.praveen.astro.viewModels.AstrosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val astrosViewModel: AstrosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AstroTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AstrosList(astrosViewModel)
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            val astrosApi = AstrosApi(client = ktorHttpClient)
            val response = astrosApi.getAstrosKtor()
            withContext(Dispatchers.Main) {
                astrosViewModel.onListCahange(response)
            }
        }
    }
}

@Composable
fun AstrosList(astrosViewModel: AstrosViewModel = viewModel()) {
    val astrosList: Astros by astrosViewModel.astrosLiveData.observeAsState(Astros())
    LazyColumn {
        items(astrosList.people) {
            AstrosDetail(people = it)
        }
    }
}

@Composable
fun AstrosDetail(people: People) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = people.name,
            textAlign = TextAlign.Center
        )
        Text(
            text = people.craft,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AstroTheme {
        AstrosDetail(people = People("name", "ii"))
    }
}

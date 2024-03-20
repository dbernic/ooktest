package com.dbernic.ooktest.ui.postcards

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.dbernic.ooktest.R
import com.dbernic.ooktest.data.model.Postcard
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostcardScreen(
    viewModel: PostcardsViewModel = hiltViewModel()

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(32.dp,0.dp),
                    text = stringResource(id = R.string.list_title)
                )
            },
        )

        PostcardsList(viewModel)
    }

}


@Composable
fun PostcardsList(viewModel: PostcardsViewModel) {
    val postcardsListItems = viewModel.postcards.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        content = {
            items(postcardsListItems.itemCount){index ->  
                val item = postcardsListItems[index]
                item?.let {
                    AsyncImage(
                        modifier = Modifier.height(125.dp),
                        model = "https://cdn.otkritkiok.ru/posts/thumbs/${it.image}",
                        contentDescription = "Postcard",
                        clipToBounds = true
                    )
                }
            }
        }
    )

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),
//
//    ) {
//        items(postcardsListItems.itemCount) { index ->
//            postcardsListItems[index].let {card->
//                card?.let {
//                    AsyncImage(
//                        model = "https: //cdn.otkritkiok.ru/posts/thumbs/${it.image}",
//                        contentDescription = "Postcard")
//                }
//
//            }
//
//        }
//    }

//
    postcardsListItems.apply {
        when {
            loadState.append is LoadState.Error -> {
                Toast.makeText(LocalContext.current, "Network error", Toast.LENGTH_LONG).show()
            }
            loadState.refresh is LoadState.Loading -> {
                //TODO
            }
            loadState.append is LoadState.Loading -> {
                //TODO
            }
        }
    }

}
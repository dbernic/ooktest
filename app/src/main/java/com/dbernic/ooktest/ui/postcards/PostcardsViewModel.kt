package com.dbernic.ooktest.ui.postcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dbernic.ooktest.data.datasourse.PostcardsDataSource
import com.dbernic.ooktest.data.model.Postcard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class PostcardsViewModel @Inject constructor(
    private val pagingSource: PostcardsDataSource,
): ViewModel() {

    val postcards: Flow<PagingData<Postcard>> = Pager(
        config = PagingConfig(30),
        pagingSourceFactory = { pagingSource })
    .flow.cachedIn(viewModelScope)
}
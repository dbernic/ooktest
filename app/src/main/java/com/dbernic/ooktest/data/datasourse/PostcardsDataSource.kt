package com.dbernic.ooktest.data.datasourse

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dbernic.ooktest.data.model.Data
import com.dbernic.ooktest.data.model.Postcard
import com.dbernic.ooktest.data.repository.RestRepository
import com.google.gson.Gson
import javax.inject.Inject

class PostcardsDataSource @Inject constructor(
    private val restRepository: RestRepository,
    private val gson: Gson,
): PagingSource<Int, Postcard>() {
    override fun getRefreshKey(state: PagingState<Int, Postcard>): Int? =  state.anchorPosition


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Postcard> {
        val nextPage = params.key ?: 1
        val result = restRepository.getPostcards(nextPage)
        return try{
            result?.let {
                if (it.status == "success") {
                    val postcardsData = gson.fromJson(it.data, Data::class.java)
                    val rawList = postcardsData.postcards
                    val postcardsList = rawList.map { card->
                        Postcard(card.image.replace(".gif", ".jpg"))
                    }
                    LoadResult.Page(
                        data = postcardsList,
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = if (rawList.isEmpty()) null else nextPage + 1
                    )
                } else {
                    LoadResult.Error(Exception(it.message))
                }
            } ?:   LoadResult.Error(Exception("Something went wrong"))

        } catch (e: Exception) {
            LoadResult.Error(Exception("No Response"))
        }



    }
}
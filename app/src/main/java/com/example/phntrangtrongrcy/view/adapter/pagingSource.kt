import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.phntrangtrongrcy.model.api.ApiInterface
import  com.example.phntrangtrongrcy.model.entity.Result
import java.io.IOException
import java.lang.Exception
import kotlin.math.log




class pagingSource(val API: ApiInterface ) : PagingSource<Int, Result>() {
     @OptIn(ExperimentalPagingApi::class)
 fun aoj(){
     super.invalidate()
 }
    @SuppressLint("SuspiciousIndentation")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        return try {
            val position = params.key ?: 1
            val response = API.getDataAsync(position)
            return LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalPages) null else position + 1
            )
        } catch (e: Exception) {
            if(e is IOException){


            }

            Log.d("fffffff", "gggggg" + e.message.toString())
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
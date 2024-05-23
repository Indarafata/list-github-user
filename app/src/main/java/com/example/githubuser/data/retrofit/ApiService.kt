package com.example.githubuser.data.retrofit
import com.example.githubuser.data.response.FollowResponseItem
import com.example.githubuser.data.response.GitHubResponse
import com.example.githubuser.data.response.UserDetailResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGitHubUsers(@Query("q") query: String): Call<GitHubResponse>


    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>
}
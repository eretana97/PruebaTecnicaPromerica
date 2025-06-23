package com.eretana.entrevista.api;

import com.eretana.entrevista.models.Commnet;
import com.eretana.entrevista.models.Post;
import com.eretana.entrevista.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {


    @GET("posts")
    Call<List<Post>> getAllPost();

    @GET("comments")
    Call<List<Commnet>> getAllComments();

    @GET("users")
    Call<List<User>> getAllUsers();


}

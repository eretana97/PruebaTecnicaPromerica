package com.eretana.entrevista;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eretana.entrevista.adapter.PostAdapter;
import com.eretana.entrevista.api.ApiClient;
import com.eretana.entrevista.api.ApiService;
import com.eretana.entrevista.models.Commnet;
import com.eretana.entrevista.models.Post;
import com.eretana.entrevista.models.User;
import com.eretana.entrevista.models.XPost;
import com.eretana.entrevista.utils.TimeFuntions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PostAdapter adapter;
    private Switch switch_mode;
    private RecyclerView rcv;
    private static enum MODE {ULTIMOS, POPULAR};
    private static MODE CURRENT_MODE = MODE.ULTIMOS;
    private ApiService apiservice;
    private List<XPost> xposts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getData();
    }

    private void init(){

        switch_mode = findViewById(R.id.switch_mode);
        rcv = findViewById(R.id.rcv);
        apiservice = ApiClient.getRetrofit().create(ApiService.class);
        xposts = new ArrayList<>();
        adapter = new PostAdapter(this,xposts);


        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter);


        switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(CURRENT_MODE == MODE.ULTIMOS){
                    CURRENT_MODE = MODE.POPULAR;
                }else{
                    CURRENT_MODE = MODE.ULTIMOS;
                }

                sortList();
            }
        });
    }

    private void getData(){
        getPost();
    }

    private void getPost(){

        apiservice.getAllPost().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful() && response.body() != null){
                    for(Post p : response.body()){
                        XPost x = new XPost();
                        x.setId(p.getId());
                        x.setBody(p.getBody());
                        x.setTitle(p.getTitle());
                        x.setMinutes(TimeFuntions.getRandMinutes());
                        x.setUserId(p.getUserId());
                        xposts.add(x);
                    }



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getUsers();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable throwable) {

            }
        });



    }




    private void getUsers(){
        apiservice.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null){

                    for(User u : response.body()){
                        for (XPost x : xposts){
                            if(u.getId() == x.getUserId()){
                                x.setUsername(u.getUsername());
                            }
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getComments();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable throwable) {

            }
        });
    }

    private void getComments(){
        apiservice.getAllComments().enqueue(new Callback<List<Commnet>>() {
            @Override
            public void onResponse(Call<List<Commnet>> call, Response<List<Commnet>> response) {
                if(response.isSuccessful() && response.body() != null){
                    for(XPost x : xposts){
                        for(Commnet c : response.body()){
                            if(c.getPostId() == x.getId()){
                                x.getComments().add(c);
                            }
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sortList();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Commnet>> call, Throwable throwable) {

            }
        });
    }


    private void sortList(){
        List<XPost> temp = new ArrayList<>(xposts);

        Log.e("SORT","SORTMODE:"+CURRENT_MODE);
        if (CURRENT_MODE == MODE.ULTIMOS) {
            temp.sort((a, b) -> Integer.compare(b.getId(), a.getId()));
        } else if (CURRENT_MODE == MODE.POPULAR) {
            temp.sort((a, b) -> Integer.compare(
                    b.getComments() != null ? b.getComments().size() : 0,
                    a.getComments() != null ? a.getComments().size() : 0
            ));
        }


        xposts = new ArrayList<>();
        xposts.addAll(temp);
        xposts = xposts.subList(0,24);


        adapter = new PostAdapter(this,xposts);
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }





}
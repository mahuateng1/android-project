package club.litiansai.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import club.litiansai.retrofit.api.ApiService;
import club.litiansai.retrofit.entity.Result;
import club.litiansai.retrofit.entity.UserEntity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建一个拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Log.i("TAG", message))
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        // 创建一个客户端
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("/") // 主机地址
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        // 获取代理对象
        ApiService apiService = this.retrofit.create(ApiService.class);


        // 同步的请求
        new Thread(() -> {
            try {
                // 执行方法 retrofit中的Call对OkHttp中的Call进行来了一层封装
                Response<String> response = apiService.login1("这是一个测试").execute();

                runOnUiThread(() -> {
                    Toast.makeText(this, response.body(), Toast.LENGTH_SHORT).show();
                });
                Log.i(TAG, response.body());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // 异步的请求
        apiService.login1("这又是一个请求").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

        apiService.test04(new UserEntity()).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                // 将请求的结果封装到 一个对象Result中
                Log.i(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }


}



































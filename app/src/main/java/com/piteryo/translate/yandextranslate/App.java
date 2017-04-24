package com.piteryo.translate.yandextranslate;

import android.app.Application;
import android.util.Log;

import com.piteryo.translate.yandextranslate.api.DictionaryApi;
import com.piteryo.translate.yandextranslate.api.TranslateApi;

import java.io.IOException;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piter on 20.04.2017.
 */

public class App extends Application {

    private static TranslateApi translateApi;
    private static DictionaryApi dictionaryApi;
    public static Map<String, String> ShortFullNameMap;
    private Retrofit retrofit;
    public final static String TRANSLATE_API_KEY = "trnsl.1.1.20170314T185810Z.c4a0a3f17d8e183a.31bf233d32d397909da80721e4f441f69619c060";
    public final static String DICTIONARY_API_KEY = "dict.1.1.20170423T220122Z.50cb7a42afd4cad6.95e26c1f9921eb9ef0c77e760970daf1e1cbddf1";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration cfg = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(cfg);
        /*HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!*/

        buildRetrofit(TRANSLATE_API_KEY, "https://translate.yandex.net/");
        buildRetrofit(DICTIONARY_API_KEY, "https://dictionary.yandex.net");

    }
    private void buildRetrofit(final String apiKey, String url)
    {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new LogJsonInterceptor());
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("key", apiKey)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

         if (apiKey == TRANSLATE_API_KEY) {

             translateApi = retrofit.create(TranslateApi.class);
         }
         else {
             dictionaryApi = retrofit.create(DictionaryApi.class);
         }
    }

    public static TranslateApi getTranslateApi() {
        return translateApi;
    }
    public static DictionaryApi getDictionaryApi() {
        return dictionaryApi;
    }
}

class LogJsonInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);
        String rawJson = response.body().string();

        Log.d(BuildConfig.APPLICATION_ID, String.format("raw JSON response is: %s", rawJson));

// Re-create the response before returning it because body can be read only once
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), rawJson)).build();
    }
}
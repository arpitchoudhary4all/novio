package amigo.atom.team.amigo.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MOHIT MALHOTRA on 04-02-2018.
 */

public interface DocBot {

    @GET("/get")
    Call<String> loadChanges(@Query("msg") String status);
}

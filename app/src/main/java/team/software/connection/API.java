package team.software.connection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface API {
    @POST("api/v1/client/")
    Call<PostDataRegisterClient> registerClient(@Body PostDataRegisterClient postDataRegisterClient);
}

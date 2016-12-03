package team.software.connection;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface API {
    @POST("api/v1/client/")
    Call<PostDataRegisterClient> registerClient(@Body PostDataRegisterClient postDataRegisterClient);

    @POST("api/v1/service/")
    Call<PostDataRegisterProvider> registerProvider(@Body PostDataRegisterProvider postDataRegisterProvider);

    @POST("api/v1/user/forgot_password")
    Call<Useraccount> forgotPassword(@Body Useraccount useraccount);

    @GET("api/v1/getvehiclecategory/")
    Call<GetDataVehicleCategory> vehicleCategory();

    @GET("api/v1/getmodel/")
    Call<GetDataVehicleModel> vehicleModel();
}

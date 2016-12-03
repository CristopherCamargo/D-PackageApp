package team.software.connection;

import java.util.List;
import java.util.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface API {
    @POST("api/v1/client/")
    Call<PostDataRegisterClient> registerClient(@Body PostDataRegisterClient postDataRegisterClient);

    @Multipart
    @POST("api/v1/service/")
    Call<ResponseBody> registerProvider(
            @Part("useraccount.first_name") RequestBody useraccount_first_name,
            @Part("useraccount.last_name") RequestBody useraccount_last_name,
            @Part("useraccount.email") RequestBody useraccount_email,
            @Part("useraccount.password") RequestBody useraccount_password,
            @Part("phone")  RequestBody phone,
            @Part MultipartBody.Part photo,
            @Part("birthdate") RequestBody birthDate,
            @Part("address") RequestBody address,
            @Part("identity_card") RequestBody identity_card,
            @Part MultipartBody.Part drive_license,
            @Part("vehicle.license_plate") RequestBody vehicle_license_plate,
            @Part("vehicle.model")  RequestBody vehicle_model,
            @Part("vehicle.category") RequestBody vehicle_category,
            @Part("vehicle.color") RequestBody vehicle_color,
            @Part MultipartBody.Part photo1);

    @POST("api/v1/user/forgot_password")
    Call<Useraccount> forgotPassword(@Body Useraccount useraccount);

    @GET("api/v1/getvehiclecategory/")
    Call<GetDataVehicleCategory> vehicleCategory();

    @GET("api/v1/getmodel/")
    Call<GetDataVehicleModel> vehicleModel();
}

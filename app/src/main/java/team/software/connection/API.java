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
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


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

    @Multipart
    @POST("api/v1/shipment/")
    Call<ResponseBody> requestPackage(
            @Header("Authorization") String authorization,
            @Part("shipmenttype") RequestBody shipmenttype,
            @Part("packagetype") RequestBody packagetype,
            @Part MultipartBody.Part photo1,
            @Part MultipartBody.Part photo2,
            @Part MultipartBody.Part photo3,
            @Part("tags") RequestBody tags,
            @Part("receiver") RequestBody receiver,
            @Part("origin") RequestBody origin,
            @Part("destination") RequestBody destination,
            @Part("insured") RequestBody insured
    );

    @POST("api/v1/user/forgot_password/")
    Call<Useraccount> forgotPassword(@Body Useraccount useraccount);

    @GET("api/v1/getvehiclecategory/")
    Call<GetDataVehicleCategory> vehicleCategory();

    @GET("api/v1/getmodel/")
    Call<GetDataVehicleModel> vehicleModel();

    @POST("api/v1/user/{pk}/change_password/")
    Call<ResponseBody> changePassword(@Header("Authorization") String authorization, @Body PostDataChangePassword postDataChangePassword, @Path("pk") int pk);

    @GET("api/v1/client/{pk}/")
    Call<PostDataRegisterClient> getProfileClient(@Header("Authorization") String authorization, @Path("pk") int pk);

    @POST("api/v1/logout/")
    Call<ResponseBody> logout(@Header("Authorization") String authorization, @Body logout log);

    @POST("api/v1/card/")
    Call<ResponseBody> SaveToken(@Header("Authorization") String authorization,@Body StripeToken token);

    @GET("api/v1/card/")
    Call<GetDataCards> getCards(@Header("Authorization") String authorization);

    @GET("api/v1/getshipmenttype/")
    Call<GetDataShipmentType> getShipmentType(@Header("Authorization") String authorization);

    @GET("api/v1/getpackagetype/")
    Call<GetDataPackageType> getPackageType(@Header("Authorization") String authorization);

    @Multipart
    @POST("api/v1/shipment/confirm_shipment/")
    Call<ResponseBody> saveRequestPackage(
            @Header("Authorization") String authorization,
            @Part("id") RequestBody id,
            @Part("confirm") RequestBody confirm
    );
}

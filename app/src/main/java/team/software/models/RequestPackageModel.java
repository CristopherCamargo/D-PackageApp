package team.software.models;

import team.software.d_packageapp.RequestPackage;

/**
 * Created by Cristopher on 11/30/16.
 */

public class RequestPackageModel {
    private String indentificador;
    private String type;
    private String tag;
    private String destine;
    private String provider_service;
    private String status;

    public RequestPackageModel(String r_identificador,String r_type,String r_tag,String r_destine,String r_provider_service,String r_status){
        this.setIndentificador(r_identificador);
        this.setDestine(r_destine);
        this.setProvider_service(r_provider_service);
        this.setType(r_type);
        this.setTag(r_tag);
        this.setStatus(r_status);
    }

    public String getDestine() {
        return destine;
    }

    public String getIndentificador() {
        return indentificador;
    }

    public String getProvider_service() {
        return provider_service;
    }

    public String getStatus() {
        return status;
    }

    public String getTag() {
        return tag;
    }

    public String getType() {
        return type;
    }

    public void setDestine(String destine) {
        this.destine = destine;
    }

    public void setIndentificador(String indentificador) {
        this.indentificador = indentificador;
    }

    public void setProvider_service(String provider_service) {
        this.provider_service = provider_service;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setType(String type) {
        this.type = type;
    }
}

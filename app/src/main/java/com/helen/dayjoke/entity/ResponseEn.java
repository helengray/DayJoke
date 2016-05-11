package com.helen.dayjoke.entity;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Helen on 2016/4/27.
 *
 */
public class ResponseEn extends BaseEn{
    @SerializedName("showapi_res_code")
    private int code;
    @SerializedName("showapi_res_error")
    private String errorMsg;
    @SerializedName("showapi_res_body")
    private ResponseBodyEn data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ResponseBodyEn getData() {
        return data;
    }

    public void setData(ResponseBodyEn data) {
        this.data = data;
    }
}

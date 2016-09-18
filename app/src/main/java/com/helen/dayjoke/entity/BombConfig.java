package com.helen.dayjoke.entity;

/**
 * Created by Helen on 2016/7/29.
 *
 */
public class BombConfig extends BaseEn{
    private Boolean isOpenWelfare;

    public Boolean isOpenWelfare() {
        if(isOpenWelfare == null){
            return Boolean.FALSE;
        }
        return isOpenWelfare;
    }

    public void setOpenWelfare(Boolean openWelfare) {
        isOpenWelfare = openWelfare;
    }
}

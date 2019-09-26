package com.imooc.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {
    UP(0,"上架"),
    DOWN(1,"下架");
    private int code;
    private String message;
    ProductStatus(int code,String message){
        this.code = code;
        this.message = message;
    }
}

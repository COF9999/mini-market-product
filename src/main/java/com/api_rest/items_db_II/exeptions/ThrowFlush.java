package com.api_rest.items_db_II.exeptions;

import com.api_rest.items_db_II.funtionalinterfaces.LambdaSupplierExeption;

import java.util.function.Supplier;

public class ThrowFlush {

    public static <T extends RuntimeException> T launchExeption(LambdaSupplierExeption lambdaSupplierExeption,T e){
        return lambdaSupplierExeption.handleExeption(e);
    }


    public static <X extends RuntimeException> void launchExeception(Supplier<? extends X> lambdaSupplierExeption){
        throw lambdaSupplierExeption.get();
    }
}

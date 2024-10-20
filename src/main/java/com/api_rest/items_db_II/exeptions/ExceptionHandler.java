package com.api_rest.items_db_II.exeptions;

import com.api_rest.items_db_II.funtionalinterfaces.LambdaSupplierExeption;

public class ExceptionHandler implements LambdaSupplierExeption {
    @Override
    public <T extends RuntimeException> T handleExeption(T exeption) {
        return exeption;
    }
}

package com.api_rest.items_db_II.funtionalinterfaces;

@FunctionalInterface
public interface LambdaSupplierExeption {

    <T extends RuntimeException> T handleExeption(T exeption);
}

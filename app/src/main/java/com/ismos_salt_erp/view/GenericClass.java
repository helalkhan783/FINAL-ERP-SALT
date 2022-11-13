package com.ismos_salt_erp.view;

 import java.util.List;

public class GenericClass<T extends List>{
    private  T t;

   public GenericClass(T t) {
        this.t = t;
        t.clear();
    }

}

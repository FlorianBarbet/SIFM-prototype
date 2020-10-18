package com.shoppo.domain;

import org.assertj.core.groups.Tuple;

import java.lang.reflect.InvocationTargetException;

public class InstanceFactory {

    public static <T extends Instance>
    T getInstance(
            Class<T> shouldInstance
    ) {
        T obj = null;
        try {
            obj = shouldInstance.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

}

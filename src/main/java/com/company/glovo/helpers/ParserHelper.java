package com.company.glovo.helpers;

import java.util.function.Function;


public class ParserHelper {

    public static <T> Object parseValue(T initialVal, Function<T, Object> parser) {
        return null == initialVal ? null : parser.apply(initialVal);
    }

}

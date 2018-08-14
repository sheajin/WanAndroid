package com.xy.wanandroid.model.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by jingbin on 2017/2/14.
 */

public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);

        return (Converter<ResponseBody, Object>) value -> {
            if (value.contentLength() == 0)
                return null;
            return delegate.convert(value);
        };
    }
}
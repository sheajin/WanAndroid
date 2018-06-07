package app.model.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class LogGsonConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = new Gson().getAdapter(TypeToken.get(type));
        return new LogGsonResponseBodyConverter<>(adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = new Gson().getAdapter(TypeToken.get(type));
        return new LogGsonRequestBodyConverter<>(adapter);
    }

    private class LogGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private TypeAdapter<T> adapter;

        LogGsonResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
            this.adapter = this.adapter.nullSafe();
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String content = value.string();
//            JsonReader jsonReader = new Gson().newJsonReader(value.charStream());
//            T t = adapter.read(jsonReader);
            if (content.contains("</div>")) {
                return adapter.fromJson(content.substring(content.lastIndexOf("</div>") + 6, content.length()));
            }
            return adapter.fromJson(content);
        }
    }

    private class LogGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");
        private TypeAdapter<T> adapter;

        LogGsonRequestBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = new Gson().newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteArray());
        }
    }
}
package io.hasura.sdk.responseConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import io.hasura.sdk.Converter;
import io.hasura.sdk.HasuraErrorCode;
import io.hasura.sdk.Util;
import io.hasura.sdk.exception.HasuraException;
import io.hasura.sdk.exception.HasuraJsonException;
import io.hasura.sdk.model.response.HasuraErrorResponse;
import okhttp3.Response;

/**
 * Created by jaison on 10/06/17.
 */

public class HasuraListResponseConverter<K> implements Converter<List<K>, HasuraException> {

    private Class<K> clazz;
    private Gson gson = new GsonBuilder().create();

    public HasuraListResponseConverter(Class<K> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<K> fromResponse(Response response) throws HasuraException {
        int code = response.code();

        try {
            if (code == 200) {
                return Util.parseJsonArray(gson,response, clazz);
            } else {
                HasuraErrorResponse err = Util.parseJson(gson, response, HasuraErrorResponse.class);
                HasuraErrorCode errCode = HasuraErrorCode.getFromCode(err.getCode());
                throw new HasuraException(errCode, err.getMessage());
            }
        } catch (HasuraJsonException e) {
            throw new HasuraException(HasuraErrorCode.INTERNAL_ERROR, e);
        }
    }


    @Override
    public HasuraException fromIOException(IOException e) {
        return new HasuraException(HasuraErrorCode.CONNECTION_ERROR, e);
    }

    @Override
    public HasuraException castException(Exception e) {
        return (HasuraException) e;
    }
}

package com.kbalazsworks.common.builders;

import com.kbalazsworks.common.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder<T>
{
    T data;
    int errorCode = 0;
    HttpStatus statusCode = HttpStatus.OK;
    HttpHeaders headers = new HttpHeaders();

    public ResponseEntity<ResponseData<T>> build() throws ApiException
    {
        boolean success = errorCode == 0;

        if (errorCode > 0 && statusCode == HttpStatus.OK)
        {
            throw new ApiException("Status code setup is needed for error response");
        }

        ResponseData<T> responseData = new ResponseData<>(data, success, errorCode, "");

        return new ResponseEntity<>(responseData, headers, statusCode);
    }

    public ResponseEntityBuilder<T> setData(T data) {
        this.data = data;

        return this;
    }
}

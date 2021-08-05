package com.boomi.azure.connector;

import com.boomi.connector.api.ObjectData;
import com.boomi.connector.api.ObjectIdData;
import com.boomi.connector.api.OperationResponse;
import com.boomi.connector.api.PayloadUtil;
import com.boomi.connector.api.ResponseUtil;
import com.boomi.connector.api.UpdateRequest;
import com.boomi.connector.util.BaseUpdateOperation;
import com.boomi.util.IOUtil;


import java.util.concurrent.*;
import java.io.*;
import okhttp3.*;
import org.json.*;
import com.boomi.connector.api.GetRequest;
import com.boomi.connector.api.OperationResponse;
import com.boomi.connector.util.BaseGetOperation;

public class AzureGetOperation extends BaseGetOperation {

    protected AzureGetOperation(AzureConnectorConnection<?> conn) {
        super(conn);
    }

	@Override
    public AzureConnectorConnection<?> getConnection() {
        return (AzureConnectorConnection<?>) super.getConnection();
    }
	
	@Override
	protected void executeGet(GetRequest getRequest, OperationResponse operationResponse) {
		
		String authToken = "";
		ObjectIdData requestData = getRequest.getObjectId();
		String objectID = requestData.getObjectId();
		
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
	            .readTimeout(30000, TimeUnit.MILLISECONDS)
	            .build();
	        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
	        RequestBody body = RequestBody.create(mediaType, "client_id=" + getConnection().getClientID() + "&scope=https://storage.azure.com/.default&client_secret=" + getConnection().getClientSecret() + "&grant_type=client_credentials");
	        Request request = new Request.Builder()
	            .url("https://login.microsoftonline.com/" + getConnection().getTenantID() + "/oauth2/v2.0/token")
	            .method("POST", body)
	            .addHeader("Content-Type", "application/x-www-form-urlencoded")
	            .build();
	        Response response = client.newCall(request).execute();
	        
	        JSONObject token = new JSONObject(response.body().string());
	        authToken = token.getString("access_token");
	        System.out.println(authToken);
		} catch (Exception e) {
			ResponseUtil.addExceptionFailure(operationResponse, requestData, e);
		} finally {
        }
		
		try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .build();
            Request request = new Request.Builder()
                .url(getConnection().getConnectionURL() + "/" + getConnection().getFileSystemName() + "/" + getConnection().getDirectoryPath() + "/" + getConnection().getFileName())
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
            Response response = client.newCall(request).execute();
            
            int statusCode = response.code();
            ResponseBody responseBody = response.body();
            String content = responseBody.string();

            if (content.length() > 0) {
            	ResponseUtil.addResultWithHttpStatus(operationResponse, requestData, statusCode, response.message(), PayloadUtil.toPayload(content));
            } else {
            	ResponseUtil.addEmptySuccess(operationResponse, requestData, String.valueOf(statusCode));
            }
        } catch (Exception e) {
        	ResponseUtil.addExceptionFailure(operationResponse, requestData, e);
        } finally {
        }
	}
}
package com.boomi.azure.connector;

import com.boomi.connector.api.ObjectData;
import com.boomi.connector.api.OperationResponse;
import com.boomi.connector.api.ResponseUtil;
import com.boomi.connector.api.UpdateRequest;
import com.boomi.connector.util.BaseUpdateOperation;
import com.boomi.util.IOUtil;


import java.util.concurrent.*;
import java.io.*;
import okhttp3.*;
import org.json.*;

public class AzureExecuteOperation extends BaseUpdateOperation {
	
	protected AzureExecuteOperation(AzureConnectorConnection<?> conn) {
		super(conn);
	}

	@Override
    public AzureConnectorConnection<?> getConnection() {
        return (AzureConnectorConnection<?>) super.getConnection();
    }
	
	@Override
	protected void executeUpdate(UpdateRequest updateRequest, OperationResponse operationResponse) {
		String authToken = "";
		String resource = "";
		String putURL = "";
		
		if (!getConnection().getDirectoryPath().isEmpty() && !getConnection().getFileName().isEmpty()) {
			resource = "file";
			putURL = getConnection().getConnectionURL() + "/" + getConnection().getFileSystemName() + "/" + getConnection().getDirectoryPath() + "/" + getConnection().getFileName() + "?resource=" + resource;
		}
		else if (!getConnection().getDirectoryPath().isEmpty() && getConnection().getFileName().isEmpty()) {
			resource = "directory";
			putURL = getConnection().getConnectionURL() + "/" + getConnection().getFileSystemName() + "/" + getConnection().getDirectoryPath() + "?resource=" + resource;
		}
		
		
		
		for (ObjectData data : updateRequest) {
			
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
	        	ResponseUtil.addExceptionFailure(operationResponse, data, e);
			} finally {
	        }
			
			try {				
	            OkHttpClient client = new OkHttpClient().newBuilder()
	                .readTimeout(30000, TimeUnit.MILLISECONDS)
	                .build();
	            MediaType mediaType = MediaType.parse("text/plain");
	            RequestBody body = RequestBody.create(mediaType, "");
	            Request request = new Request.Builder()
	                .url(putURL)
	                .method("PUT", body)
	                .addHeader("Authorization", "Bearer " + authToken)
	                .addHeader("Content-Length", "0")
	                .addHeader("x-ms-version", "2018-11-09")
	                .build();
	            Response response = client.newCall(request).execute();

	            int statusCode = response.code();

	            //Successful call, but no data is returned from this service.
	            if (statusCode >= 200 && statusCode < 300) {
	                ResponseUtil.addEmptySuccess(operationResponse, data, String.valueOf(statusCode));
	            } else {
	                // Unsuccessful call, return status code of why
	                ResponseUtil.addEmptyFailure(operationResponse, data, String.valueOf(statusCode));
	            }
	        } catch (Exception e) {
	        	ResponseUtil.addExceptionFailure(operationResponse, data, e);
	        } finally {
	        }
		} 
		
	}
}
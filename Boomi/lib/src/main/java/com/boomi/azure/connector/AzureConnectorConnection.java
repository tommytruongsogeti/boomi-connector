package com.boomi.azure.connector;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.util.BaseConnection;

// facilitate pulling values from the connection profile in the UI 
public class AzureConnectorConnection extends BaseConnection {

    private static final String BASE_URL_FIELD = "url";
    private static final String CONNECTION_URL_FIELD = "connectionURL";
    private static final String ACCOUNT_NAME_FIELD = "accountName";
    private static final String CLIENT_ID_FIELD = "clientID";
    private static final String CLIENT_SECRET_FIELD = "clientSecret";
    private static final String TENANT_ID_FIELD = "tenantID";
    private static final String FILE_SYSTEM_NAME_FIELD = "fileSystemName";
    private static final String DIRECTORY_PATH_FIELD = "dirPath";
    private static final String ENDPOINT_FIELD = "endpoint";
    private static final String RESPONSE_TIMEOUT_FIELD = "responseTimeout";

    public String getBaseURL() {
        return getContext().getConnectionProperties().getProperty(BASE_URL_FIELD);
    }

	public String getConnectionURL() {
        return getContext().getConnectionProperties().getProperty(CONNECTION_URL_FIELD);
    }

	public String getAccountName() {
        return getContext().getConnectionProperties().getProperty(ACCOUNT_NAME_FIELD);
    }

	public String getClientID() {
        return getContext().getConnectionProperties().getProperty(CLIENT_ID_FIELD);
    }

	public String getClientSecret() {
        return getContext().getConnectionProperties().getProperty(CLIENT_SECRET_FIELD);
    }

	public String getTenantID() {
        return getContext().getConnectionProperties().getProperty(TENANT_ID_FIELD);
    }

	public String getFileSystemName() {
        return getContext().getConnectionProperties().getProperty(FILE_SYSTEM_NAME_FIELD);
    }

	public String getDirectoryPath() {
        return getContext().getConnectionProperties().getProperty(DIRECTORY_PATH_FIELD);
    }

	public String getEndpoint() {
        return getContext().getConnectionProperties().getProperty(ENDPOINT_FIELD);
    }

	public String getResponseTimeout() {
        return getContext().getConnectionProperties().getProperty(RESPONSE_TIMEOUT_FIELD);
    }

    public AzureConnectorConnection(BrowseContext context) {
        super(context);
    }
    
}
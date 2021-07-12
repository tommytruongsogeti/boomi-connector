package com.boomi.azure.connector;

import com.boomi.connector.api.GetRequest;
import com.boomi.connector.api.OperationResponse;
import com.boomi.connector.util.BaseGetOperation;

public class AzureGetOperation extends BaseGetOperation {

    protected AzureGetOperation(AzureConnectorConnection conn) {
        super(conn);
    }

	@Override
	protected void executeGet(GetRequest request, OperationResponse response) {
		// TODO Auto-generated method stub
	}

	@Override
    public AzureConnectorConnection getConnection() {
        return (AzureConnectorConnection) super.getConnection();
    }
}
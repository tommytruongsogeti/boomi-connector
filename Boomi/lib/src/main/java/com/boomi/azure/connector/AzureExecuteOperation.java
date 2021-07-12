package com.boomi.azure.connector;

import com.boomi.connector.api.OperationResponse;
import com.boomi.connector.api.UpdateRequest;
import com.boomi.connector.util.BaseUpdateOperation;

public class AzureExecuteOperation extends BaseUpdateOperation {

	protected AzureExecuteOperation(AzureConnectorConnection conn) {
		super(conn);
	}

	@Override
	protected void executeUpdate(UpdateRequest request, OperationResponse response) {
		// TODO Auto-generated method stub
	}

	@Override
    public AzureConnectorConnection getConnection() {
        return (AzureConnectorConnection) super.getConnection();
    }
}
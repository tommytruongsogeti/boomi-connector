package com.boomi.azure.connector;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.Browser;
import com.boomi.connector.api.Operation;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.util.BaseConnector;
import com.boomi.azure.connector.AzureExecuteOperation;
import com.boomi.azure.connector.AzureGetOperation;

public class AzureConnector extends BaseConnector {

    @Override
    public Browser createBrowser(BrowseContext context) {
        return new AzureConnectorBrowser(context);
    }    

    @Override
    protected Operation createGetOperation(OperationContext context) {
        return new AzureGetOperation(new AzureConnectorConnection<OperationContext> (context));
    }

    @Override
    protected Operation createExecuteOperation(OperationContext context) {
        return new AzureExecuteOperation(new AzureConnectorConnection<OperationContext> (context));
    }
}
package com.boomi.azure.connector;

import java.util.Collection;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectDefinitions;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.util.BaseBrowser;

public class AzureConnectorBrowser extends BaseBrowser {

    protected AzureConnectorBrowser(BrowseContext context) {
        super(context);
    }

	@Override
	public ObjectDefinitions getObjectDefinitions(String objectTypeId,
			Collection<ObjectDefinitionRole> roles) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectTypes getObjectTypes() {
		// TODO Auto-generated method stub
		return null;
	}
}
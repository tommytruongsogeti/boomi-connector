package com.boomi.azure.connector;

import java.io.*;
import java.util.Collection;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.ConnectorException;
import com.boomi.connector.api.ContentType;
import com.boomi.connector.api.ObjectDefinition;
import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectDefinitions;
import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.api.ui.BrowseField;
import com.boomi.connector.util.BaseBrowser;
import com.boomi.util.ClassUtil;
import com.boomi.util.IOUtil;
import com.boomi.util.StreamUtil;
import java.nio.charset.Charset;

public class AzureConnectorBrowser extends BaseBrowser {
	
	private static final String ID = "ID";
	private static final String ID_SCHEMA = "/id-schema.json";
    private static final String UTF8 = "UTF-8";

    protected AzureConnectorBrowser(BrowseContext context) {
        super(context);
    }
    
    
	@Override
	public ObjectTypes getObjectTypes() {
        ObjectTypes types = new ObjectTypes();
        ObjectType type = new ObjectType();
        type.setId(ID);
        type.setLabel(ID);
        types.getTypes().add(type);
        return types;
    }
	
	@Override
	public ObjectDefinitions getObjectDefinitions(String objectTypeId,
			Collection<ObjectDefinitionRole> roles) {
		ObjectDefinitions definitions = new ObjectDefinitions();
        switch (getContext().getOperationType()) {

            case GET:
                //Output has incoming data, no outgoing data
                definitions.getDefinitions().add(
                        new ObjectDefinition()
                                .withInputType(ContentType.NONE)
                                .withOutputType(ContentType.JSON)
                                .withJsonSchema(getJsonSchema())
                                .withElementName(""));
                    
                break;
            // output and input
            case EXECUTE:
                
                ObjectDefinition inputDef = new ObjectDefinition()
                        .withInputType( ContentType.JSON )
                        .withOutputType( ContentType.NONE)
                        .withJsonSchema(getJsonSchema())
                        .withElementName("");
                definitions.getDefinitions().add(inputDef);

                definitions.getDefinitions().add(new ObjectDefinition()
                        .withInputType(ContentType.NONE)
                        .withOutputType(ContentType.NONE));
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return definitions;
	}

	private static String getJsonSchema() {
        String schema;
        InputStream is = ClassUtil.getResourceAsStream(ID_SCHEMA);
        try {
            schema = StreamUtil.toString(is, Charset.forName(UTF8));
        } catch (IOException ex) {
            throw new ConnectorException("Error reading schema", ex);
        } finally {
            IOUtil.closeQuietly(is);
        }
        return schema;
    }
}
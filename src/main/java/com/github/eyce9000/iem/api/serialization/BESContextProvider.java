package com.github.eyce9000.iem.api.serialization;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Provider
public class BESContextProvider implements ContextResolver<JAXBContext> {
    private JAXBContext context = null;
 
    public BESContextProvider(){
    	
    }
    
    public JAXBContext getContext(Class<?> type) {
		try {
	    	if(context==null)
	    		context = JAXBContext.newInstance("com.bigfix.schemas.besapi:com.bigfix.schemas.bes:com.ibm.tem.webreports.relevance:com.ibm.tem.api.relevance.results");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        return context;
    }
}
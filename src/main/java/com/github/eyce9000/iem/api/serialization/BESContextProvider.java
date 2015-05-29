package com.github.eyce9000.iem.api.serialization;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;

import com.bigfix.schemas.bes.BES;
import com.bigfix.schemas.besapi.BESAPI;
import com.github.eyce9000.iem.api.relevance.results.QueryResult;
import com.github.eyce9000.iem.webreports.relevance.Envelope;

@Provider
public class BESContextProvider implements ContextResolver<JAXBContext> {
    private JAXBContext context = null;
 
    public BESContextProvider(){
    	
    }
    
    @Override
	public JAXBContext getContext(Class<?> type) {
		try {
			String jaxbContextPath = StringUtils.join(new String[]{
					BES.class.getPackage().getName(),
					BESAPI.class.getPackage().getName(),
					Envelope.class.getPackage().getName()
//					QueryResult.class.getPackage().getName()
			},":");
			
	    	if(context==null)
	    		context = JAXBContext.newInstance(jaxbContextPath);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        return context;
    }
}
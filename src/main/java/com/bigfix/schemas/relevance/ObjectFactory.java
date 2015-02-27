
package com.bigfix.schemas.relevance;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bigfix.schemas.relevance package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ResponseHeaderElement_QNAME = new QName("http://schemas.bigfix.com/Relevance", "ResponseHeaderElement");
    private final static QName _RequestHeaderElement_QNAME = new QName("http://schemas.bigfix.com/Relevance", "RequestHeaderElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bigfix.schemas.relevance
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStructuredRelevanceResultResponse }
     * 
     */
    public GetStructuredRelevanceResultResponse createGetStructuredRelevanceResultResponse() {
        return new GetStructuredRelevanceResultResponse();
    }

    /**
     * Create an instance of {@link StructuredRelevanceResult }
     * 
     */
    public StructuredRelevanceResult createStructuredRelevanceResult() {
        return new StructuredRelevanceResult();
    }

    /**
     * Create an instance of {@link GetRelevanceResult }
     * 
     */
    public GetRelevanceResult createGetRelevanceResult() {
        return new GetRelevanceResult();
    }

    /**
     * Create an instance of {@link RequestHeader }
     * 
     */
    public RequestHeader createRequestHeader() {
        return new RequestHeader();
    }

    /**
     * Create an instance of {@link GetRelevanceResultResponse }
     * 
     */
    public GetRelevanceResultResponse createGetRelevanceResultResponse() {
        return new GetRelevanceResultResponse();
    }

    /**
     * Create an instance of {@link StoreSharedVariable }
     * 
     */
    public StoreSharedVariable createStoreSharedVariable() {
        return new StoreSharedVariable();
    }

    /**
     * Create an instance of {@link DashboardVariableIdentifier }
     * 
     */
    public DashboardVariableIdentifier createDashboardVariableIdentifier() {
        return new DashboardVariableIdentifier();
    }

    /**
     * Create an instance of {@link StoreSharedVariableResponse }
     * 
     */
    public StoreSharedVariableResponse createStoreSharedVariableResponse() {
        return new StoreSharedVariableResponse();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link UserAccount }
     * 
     */
    public UserAccount createUserAccount() {
        return new UserAccount();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link DeleteSharedVariable }
     * 
     */
    public DeleteSharedVariable createDeleteSharedVariable() {
        return new DeleteSharedVariable();
    }

    /**
     * Create an instance of {@link DeleteSharedVariableResponse }
     * 
     */
    public DeleteSharedVariableResponse createDeleteSharedVariableResponse() {
        return new DeleteSharedVariableResponse();
    }

    /**
     * Create an instance of {@link ResponseHeader }
     * 
     */
    public ResponseHeader createResponseHeader() {
        return new ResponseHeader();
    }

    /**
     * Create an instance of {@link GetStructuredRelevanceResult }
     * 
     */
    public GetStructuredRelevanceResult createGetStructuredRelevanceResult() {
        return new GetStructuredRelevanceResult();
    }

    /**
     * Create an instance of {@link AuthenticateHeader }
     * 
     */
    public AuthenticateHeader createAuthenticateHeader() {
        return new AuthenticateHeader();
    }

    /**
     * Create an instance of {@link LoginHeader }
     * 
     */
    public LoginHeader createLoginHeader() {
        return new LoginHeader();
    }

    /**
     * Create an instance of {@link ResultList }
     * 
     */
    public ResultList createResultList() {
        return new ResultList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.bigfix.com/Relevance", name = "ResponseHeaderElement")
    public JAXBElement<ResponseHeader> createResponseHeaderElement(ResponseHeader value) {
        return new JAXBElement<ResponseHeader>(_ResponseHeaderElement_QNAME, ResponseHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.bigfix.com/Relevance", name = "RequestHeaderElement")
    public JAXBElement<RequestHeader> createRequestHeaderElement(RequestHeader value) {
        return new JAXBElement<RequestHeader>(_RequestHeaderElement_QNAME, RequestHeader.class, null, value);
    }

}

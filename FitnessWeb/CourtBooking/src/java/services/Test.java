/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author bjmaclean
 */
@WebService(serviceName = "Test")
public class Test {

    /**
     * 
     * 
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        String test =  "<message><greeting>Hello</greeting><name>" + txt + "</name><message>";
        
        //Convert the string to xml before passing back
        
        
         
        return test;
        
        
        
        
    }
}

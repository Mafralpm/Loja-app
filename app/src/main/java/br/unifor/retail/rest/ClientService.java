package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.UserLogin;


/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = "https://bluelab.herokuapp.com/user", converters = {FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class,MappingJackson2HttpMessageConverter.class})
public interface ClientService extends RestClientHeaders{

    @Post("/sign_up")
    void cadastraCliente(@Body RecordLogin recordLogin);

    @Post("/sign_in")
    void login(@Body UserLogin userLogin);

}

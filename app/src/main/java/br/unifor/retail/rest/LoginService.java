package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;

import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.UserLogin;


/**
 * Created by labm4 on 16/11/16.
 */

@Rest(rootUrl = "http://bluelab.herokuapp.com/user", converters = MappingJackson2HttpMessageConverter.class)
public interface LoginService {

    @Post("/sign_in")
    void login(@Body UserLogin userLogin);
}

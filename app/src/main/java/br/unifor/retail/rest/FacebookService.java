package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;

/**
 * Created by vania on 21/11/16.
 */

@Rest(rootUrl = "http://bluelab.heroku.com", converters = MappingJackson2HttpMessageConverter.class)
public interface FacebookService {

    @Post("/user_facebook/sign_in")
    RecordLogin pegaFacebook(@Body User user);
}

package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;

import static br.unifor.retail.statics.StaticsRest.FACEBOOK_SING_IN;
import static br.unifor.retail.statics.StaticsRest.FACEBOOK_SING_UP;
import static br.unifor.retail.statics.StaticsRest.ROOT_URL;

/**
 * Created by vania on 21/11/16.
 */

@Rest(rootUrl = ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface FacebookService {

    @Post(FACEBOOK_SING_IN)
    User pegaFacebook(@Body RecordLogin recordLogin);

    @Post(FACEBOOK_SING_UP)
    User criaFacebook(@Body RecordLogin recordLogin);
}

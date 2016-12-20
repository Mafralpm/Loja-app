package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;
import br.unifor.retail.statics.StaticsRest;

/**
 * Created by vania on 21/11/16.
 */

@Rest(rootUrl = StaticsRest.ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface FacebookService {

    @Post(StaticsRest.FACEBOOK_SING_IN)
    User pegaFacebook(@Body RecordLogin recordLogin);

    @Post(StaticsRest.FACEBOOK_SING_UP)
    User criaFacebook(@Body RecordLogin recordLogin);
}

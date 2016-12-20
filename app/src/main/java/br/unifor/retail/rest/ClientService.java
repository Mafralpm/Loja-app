package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;

import static br.unifor.retail.statics.StaticsRest.ROOT_URL;
import static br.unifor.retail.statics.StaticsRest.USER_SING_IN;
import static br.unifor.retail.statics.StaticsRest.USER_SING_UP;


/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface ClientService extends RestClientHeaders{

    @Post(USER_SING_UP)
    void cadastraCliente(@Body RecordLogin recordLogin);

    @Post(USER_SING_IN)
    User login(@Body RecordLogin recordLogin);
}

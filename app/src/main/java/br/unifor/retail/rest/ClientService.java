package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;
import br.unifor.retail.statics.StaticsRest;


/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = StaticsRest.ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface ClientService extends RestClientHeaders{

    @Post(StaticsRest.USER_SING_UP)
    void cadastraCliente(@Body RecordLogin recordLogin);

    @Post(StaticsRest.USER_SING_IN)
    User login(@Body RecordLogin recordLogin);
}

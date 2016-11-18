package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;


/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = "https://bluelab.herokuapp.com/user", converters = MappingJackson2HttpMessageConverter.class)
public interface ClientService extends RestClientHeaders{

    @Post("/sign_up")
    void cadastraCliente(@Body RecordLogin recordLogin);

    @Post("/sign_in")
    User login(@Body RecordLogin recordLogin);

    @Get("/clientes_user/{idCliente}.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "C5TqmVb2GdaQJsPgy3mR")})
    void buscaCliente(@Path Long idCliente);


}

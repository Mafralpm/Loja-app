package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.RecordLogin;
import br.unifor.retail.model.User;


/**
 * Created by mafra on 13/12/16.
 */

@Rest(rootUrl = "https://bluelab.herokuapp.com", converters = MappingJackson2HttpMessageConverter.class)
public interface InfoClienteService extends RestClientHeaders {
    @Get("/clientes/{idCliente}.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    User searchClient(@Path Long idCliente);

    @Put("/clientes/{idCliente}.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    User updatInfoCliente(@Body User user, @Path Long idCliente);

}
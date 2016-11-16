package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresHeader;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;

import java.util.List;

import br.unifor.retail.model.response.ResponseClient;


/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = "https://bluelab.herokuapp.com/user", converters = {FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class,MappingJackson2HttpMessageConverter.class})
public interface ClientService extends RestClientHeaders{

    @Post("/sign_up")
    @Accept(MediaType.APPLICATION_JSON_VALUE)
    @RequiresHeader({HttpHeaders.ALLOW, MediaType.MULTIPART_FORM_DATA_VALUE})
    void cadastraCliente(@Body MultiValueMap multiValueMap);

    @Get("/sign_in.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "C5TqmVb2GdaQJsPgy3mR")})
    @Accept(MediaType.APPLICATION_JSON_VALUE)
    List<ResponseClient> getClients();

}

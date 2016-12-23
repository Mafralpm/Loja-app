package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.User;

import static br.unifor.retail.statics.StaticsAdmin.EMAIL;
import static br.unifor.retail.statics.StaticsAdmin.EMAIL_KEY;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN_KEY;
import static br.unifor.retail.statics.StaticsRest.BUSCA_CLIENTE;
import static br.unifor.retail.statics.StaticsRest.ROOT_URL;


/**
 * Created by mafra on 13/12/16.
 */

@Rest(rootUrl = ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)

public interface InfoClienteService {

    @Get(BUSCA_CLIENTE)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    User searchClient(@Path Long idUser);

    @Put(BUSCA_CLIENTE)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    User updatInfoCliente(@Body User user, @Path Long idUser);

}
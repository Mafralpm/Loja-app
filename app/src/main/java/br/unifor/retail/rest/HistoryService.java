package br.unifor.retail.rest;

/**
 * Created by vania on 27/10/16.
 */
//

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collection;

import br.unifor.retail.model.History;
import br.unifor.retail.model.Product;
import br.unifor.retail.statics.StaticsAdmin;


@Rest(rootUrl = "https://bluelab.herokuapp.com/", converters = MappingJackson2HttpMessageConverter.class)
public interface HistoryService {

    @Post("/historicos")
    @Headers({
            @Header(name = StaticsAdmin.EMAIL_KEY, value = StaticsAdmin.EMAIL),
            @Header(name = StaticsAdmin.TOKEN_KEY, value = StaticsAdmin.TOKEN)})
    void cria (@Body History history);

    @Get("/cliente_historicos/{cliente_id}.json")
    @Accept(MediaType.APPLICATION_JSON_VALUE)
    @Headers({
            @Header(name = StaticsAdmin.EMAIL_KEY, value = StaticsAdmin.EMAIL),
            @Header(name = StaticsAdmin.TOKEN_KEY, value = StaticsAdmin.TOKEN)})
    Collection<Product> searchProducts(@Path Long cliente_id);
}

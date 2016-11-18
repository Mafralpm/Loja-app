package br.unifor.retail.rest;

/**
 * Created by vania on 27/10/16.
 */
//

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.History;


@Rest(rootUrl = "http://bluelab.heroku.com", converters = MappingJackson2HttpMessageConverter.class)
public interface HistoryService {

    @Post("/historicos")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "6r2p9zNbeoTeoTcU6msP")})
    void enviar (@Body History history);




}

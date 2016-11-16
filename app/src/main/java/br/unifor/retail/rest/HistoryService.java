package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.rest.response.ResponseHistory;

/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = "http://bluelab.heroku.com/historico", converters = MappingJackson2HttpMessageConverter.class)
public interface HistoryService {

    @Get("/{idHistory}.json")
    //@RequiresHeader({("X-Admin-Email"),("X-Admin-Token")})
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "C5TqmVb2GdaQJsPgy3mR")})
    ResponseHistory searchHistory (@Path int idHistory);
}

package br.unifor.retail.rest;


import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.model.Product;

/**
 * Created by vania on 24/10/16.
 */

@Rest(rootUrl = "http://bluelab.herokuapp.com/produtos", converters = MappingJackson2HttpMessageConverter.class)
public interface ProductService{

    @Get("/{idProduct}.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "6r2p9zNbeoTeoTcU6msP")})
    Product searchProduct (@Path Long idProduct);


}

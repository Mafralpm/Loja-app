package br.unifor.retail.rest;


import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.rest.response.ResponseProduct;

/**
 * Created by vania on 24/10/16.
 */

@Rest(rootUrl = "http://bluelab.heroku.com/produtos", converters = MappingJackson2HttpMessageConverter.class)
//@Rest(rootUrl = "http://10.50.158.91:3000/produtos", converters = {FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class})

public interface ProductService{

    @Get("/{idProduct}.json")
    //@RequiresHeader({("X-Admin-Email"),("X-Admin-Token")})
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "-oWy-CtyS6socyY5tZgE")})
    ResponseProduct searchProduct (@Path Long idProduct);
}

package br.unifor.retail.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.rest.response.ResponseProduct;

/**
 * Created by vania on 24/10/16.
 */

@Rest(rootUrl = "http://bluelab.heroku.com/produtos", converters = MappingJackson2HttpMessageConverter.class)
//@Rest(rootUrl = "http://10.50.158.91:3000/produtos", converters = {FormHttpMessageConverter.class, ByteArrayHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class})

public interface ProductService{

    @Get("/{idProduct}.json")
    ResponseProduct searchProduct (Long idProduct);
}

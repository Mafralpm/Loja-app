package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import br.unifor.retail.rest.response.ResponseReview;

/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = "http://bluelab.heroku.com/reviews_produto", converters = MappingJackson2HttpMessageConverter.class)
public interface ReviewService {

    @Get("/{produto_id}.json")
    @Accept(MediaType.APPLICATION_JSON)

    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "C5TqmVb2GdaQJsPgy3mR")})
    ResponseReview searchProductReview(@Path int produto_id);

}



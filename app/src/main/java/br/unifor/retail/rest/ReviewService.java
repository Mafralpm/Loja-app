package br.unifor.retail.rest;

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

import br.unifor.retail.model.Review;

import static br.unifor.retail.statics.StaticsAdmin.EMAIL;
import static br.unifor.retail.statics.StaticsAdmin.EMAIL_KEY;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN_KEY;
import static br.unifor.retail.statics.StaticsRest.BUSCA_REVIEWS_PRODUTO;
import static br.unifor.retail.statics.StaticsRest.REVIEWS;
import static br.unifor.retail.statics.StaticsRest.ROOT_URL;


/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface ReviewService {

    @Get(BUSCA_REVIEWS_PRODUTO)
    @Accept(MediaType.APPLICATION_JSON_VALUE)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    Collection<Review> searchProductReview(@Path Long produto_id);

    @Post(REVIEWS)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    void criaReview(@Body Review review);

}



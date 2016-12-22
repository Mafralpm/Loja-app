package br.unifor.retail.rest;

/**
 * Created by vania on 27/10/16.
 */
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collection;

import br.unifor.retail.model.History;
import br.unifor.retail.model.Product;

import static br.unifor.retail.statics.StaticsAdmin.EMAIL;
import static br.unifor.retail.statics.StaticsAdmin.EMAIL_KEY;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN_KEY;
import static br.unifor.retail.statics.StaticsRest.BUSCAR_PRODUTOS_NO_CLIENTE_HISTORICO_;
import static br.unifor.retail.statics.StaticsRest.HISTORICOS;
import static br.unifor.retail.statics.StaticsRest.ROOT_URL;


@Rest(rootUrl = ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface HistoryService {

    @Post(HISTORICOS)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    void cria (@Body History history);

    @Get(BUSCAR_PRODUTOS_NO_CLIENTE_HISTORICO_)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    Collection<Product> searchProducts(@Path Long idUser);
}

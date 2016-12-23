package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.RequiresAuthentication;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collection;

import br.unifor.retail.model.Pedido;
import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.model.Product;

import static br.unifor.retail.statics.StaticsAdmin.EMAIL;
import static br.unifor.retail.statics.StaticsAdmin.EMAIL_KEY;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN_KEY;
import static br.unifor.retail.statics.StaticsRest.BUSCA_PEDIDO;
import static br.unifor.retail.statics.StaticsRest.BUSCA_PEDIDO_PRODUTO_REVIEW;
import static br.unifor.retail.statics.StaticsRest.FINALIZA_PEDIDO;
import static br.unifor.retail.statics.StaticsRest.PEDIDOS;
import static br.unifor.retail.statics.StaticsRest.PEDIDOS_HAS_PRODUTO;
import static br.unifor.retail.statics.StaticsRest.PEDIDO_DELETA_PRODUTO;
import static br.unifor.retail.statics.StaticsRest.ROOT_URL;

/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface PedidoService {

    @Post(PEDIDOS)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    Pedido criaPedido(@Body Pedido pedido);

    @Post(PEDIDOS_HAS_PRODUTO)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    void criaPedidoHasProduto(@Body PedidoHasProduto pedidoHasProduto);

    @Get(BUSCA_PEDIDO_PRODUTO_REVIEW)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    Collection<Product> searchProductReview(@Path Long pedido_id);

    @Put(FINALIZA_PEDIDO)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    Pedido finalizaPedido(@Path Long pedido_id, @Body Pedido pedido);

    @Get(BUSCA_PEDIDO)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    Pedido buscaPedido(@Path Long pedido_id);


    @Delete(PEDIDO_DELETA_PRODUTO)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    void deletarPedido (@Path Long pedido_id, @Path Long produto_id);

}

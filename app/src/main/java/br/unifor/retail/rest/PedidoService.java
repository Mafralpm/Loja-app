package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collection;

import br.unifor.retail.model.Pedido;
import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.model.Product;

/**
 * Created by vania on 27/10/16.
 */

@Rest(rootUrl = "https://bluelab.herokuapp.com", converters = MappingJackson2HttpMessageConverter.class)
public interface PedidoService {

    @Post("/pedidos.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    Pedido criaPedido(@Body Pedido pedido);

    @Post("/pedidos_has_produtos")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    void criaPedidoHasProduto(@Body PedidoHasProduto pedidoHasProduto);

    @Get(("/pedido_produtos/{pedido_id}.json"))
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    Collection<Product> searchProductReview(@Path Long pedido_id);

    @Put("/pedidos/{pedido_id}.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    Pedido finalizaPedido(@Path Long pedido_id, @Body Pedido pedido);

    @Get("/pedidos/{pedido_id}.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    Pedido buscaPedido(@Path Long pedido_id);

    @Delete("/pedidos/{produto_id}.json")
    @Headers({
            @Header(name = "X-Admin-Email", value = "admin@admin.com"),
            @Header(name = "X-Admin-Token", value = "j8UepQwzQwSbmnJVJ88C")})
    Pedido deletarPedido (@Path Long produto_id);

}

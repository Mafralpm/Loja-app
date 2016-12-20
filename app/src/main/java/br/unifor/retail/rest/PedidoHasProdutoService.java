package br.unifor.retail.rest;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Headers;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collection;

import br.unifor.retail.model.PedidoHasProduto;
import br.unifor.retail.statics.StaticsAdmin;

/**
 * Created by mafra on 05/12/16.
 */

@Rest(rootUrl = "https://bluelab.herokuapp.com", converters = MappingJackson2HttpMessageConverter.class)
public interface PedidoHasProdutoService {

    @Get(("/pedidos_finalizados/{pedido_id}.json"))
    @Headers({
            @Header(name = StaticsAdmin.EMAIL_KEY, value = StaticsAdmin.EMAIL),
            @Header(name = StaticsAdmin.TOKEN_KEY, value = StaticsAdmin.TOKEN)})
    Collection<PedidoHasProduto> searchPedidoHasProduct(@Path Long pedido_id);

}

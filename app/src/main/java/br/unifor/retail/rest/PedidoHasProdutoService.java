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

import static br.unifor.retail.statics.StaticsAdmin.EMAIL;
import static br.unifor.retail.statics.StaticsAdmin.EMAIL_KEY;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN;
import static br.unifor.retail.statics.StaticsAdmin.TOKEN_KEY;
import static br.unifor.retail.statics.StaticsRest.BUSCA_PEDIDOS_FINALIZADOS;
import static br.unifor.retail.statics.StaticsRest.ROOT_URL;

/**
 * Created by mafra on 05/12/16.
 */

@Rest(rootUrl = ROOT_URL, converters = MappingJackson2HttpMessageConverter.class)
public interface PedidoHasProdutoService {

    @Get(BUSCA_PEDIDOS_FINALIZADOS)
    @Headers({
            @Header(name = EMAIL_KEY, value = EMAIL),
            @Header(name = TOKEN_KEY, value = TOKEN)})
    Collection<PedidoHasProduto> searchPedidoHasProduct(@Path Long pedido_id);

}

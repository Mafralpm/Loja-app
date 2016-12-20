package br.unifor.retail.statics;

/**
 * Created by vania on 19/12/16.
 */

public class StaticsRest {

    public static final String ROOT_URL = "https://bluelab.herokuapp.com";

    public static final String USER = "/user";

    public static final String USER_SING_UP = USER + "/sign_up";

    public static final String USER_SING_IN = USER + "/sign_in";

    public static final String PRODUTOS = "/produtos";

    public static final String BUSCA_PRODUTOS = PRODUTOS + "/{idProduct}.json";

    public static final String USER_FACEBOOK = "/user_facebook";

    public static final String FACEBOOK_SING_UP = USER_FACEBOOK + "/sign_up";

    public static final String FACEBOOK_SING_IN = USER_FACEBOOK + "/sign_in";

    public static final String HISTORICOS = "/historicos";

    public static final String CLIENTE_HISTORICO = "/cliente_historicos";

    public static final String BUSCAR_PRODUTOS_NO_CLIENTE_HISTORICO_ = CLIENTE_HISTORICO + "/{cliente_id}.json";

    public static final String CLIENTE = "/clientes";

    public static final String BUSCA_CLIENTE = CLIENTE + "/{idCliente}.json";

    public static final String PEDIDOS_FINALIZADOS = "/pedidos_finalizados";

    public static final String BUSCA_PEDIDOS_FINALIZADOS = PEDIDOS_FINALIZADOS + "/{pedido_id}.json";

    public static final String REVIEWS_PRODUTO = "/reviews_produto";

    public static final String BUSCA_REVIEWS_PRODUTO = REVIEWS_PRODUTO + "/{produto_id}.json";

    public static final String REVIEWS = REVIEWS_PRODUTO + "/reviews";

    public static final String PEDIDOS = "/pedidos.json";

    public static final String PEDIDOS_HAS_PRODUTO = "/pedidos_has_produtos";

    public static final String PEDIDO_PRODUTO = "/pedido_produtos";

    public static final String BUSCA_PEDIDO_PRODUTO_REVIEW = PEDIDO_PRODUTO + "/{pedido_id}.json";

    public static final String PEDIDO = "/pedidos";

    public static final String BUSCA_PEDIDO = PEDIDO + "/{pedido_id}.json";

    public static final String FINALIZA_PEDIDO = PEDIDO + "/{pedido_id}.json";

    public static final String DELETA_PEDIDO = PEDIDO + "/{produto_id}.json";

}


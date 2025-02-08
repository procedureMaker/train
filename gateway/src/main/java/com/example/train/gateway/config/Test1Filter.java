package com.example.train.gateway.config;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class Test1Filter implements GlobalFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOG.info("Test1filter");
//        让这个过滤器顺利的往后
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

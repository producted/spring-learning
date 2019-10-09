package com.zhangpk.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 *
 * 配置TokenStoreConfig用于存储Token
 *
 * Created By zhangpk On 2019/6/20
 **/
@Configuration
public class TokenStoreConfig {

    /**
     * redis 链接工厂
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "merryyou.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * jwt TOKEN配置信息
     */
    @Configuration
    @ConditionalOnProperty(prefix = "merryyou.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenCofnig{

        /**
         * 使用jwtTokenStore存储token
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 用于生成jwt
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey("merryyou");//生成签名的key
            return accessTokenConverter;
        }

        /**
         * 用于扩展JWT
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new MerryyouJwtTokenEnhancer();
        }

        /**
         * AuthenticationSuccessHandler
         * @return
         */
        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler(){
            return new SimpleUrlAuthenticationSuccessHandler();
        }

    }
}


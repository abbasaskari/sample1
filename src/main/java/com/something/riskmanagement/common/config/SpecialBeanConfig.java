package com.something.riskmanagement.common.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Configuration
public class SpecialBeanConfig {
    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("p@$5W0rD");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("Base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}

//    public static void main(String[] args) {
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword("p@$5W0rD");
//        config.setAlgorithm("PBEWithMD5AndDES");
//        config.setKeyObtentionIterations("1000");
//        config.setPoolSize("1");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        config.setStringOutputType("Base64");
//        encryptor.setConfig(config);
//
//        String enc = encryptor.encrypt("your text to encrypt");
//        System.out.println(enc);
//    }

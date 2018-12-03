package com.monkeyzi.oauth.im.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "monkeyzi.im")
@Getter
@Setter
@Component
public class ImServerConfig {

   private ImProperties monkeyIm=new ImProperties();


}

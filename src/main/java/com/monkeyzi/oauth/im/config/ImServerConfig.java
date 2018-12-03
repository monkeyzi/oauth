package com.monkeyzi.oauth.im.config;

import com.monkeyzi.oauth.im.properties.ImProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "monkeyzi.im")
@Getter
@Setter
@Component
public class ImServerConfig {

   private ImProperties im=new ImProperties();


}

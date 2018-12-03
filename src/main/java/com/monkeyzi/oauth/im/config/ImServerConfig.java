package com.monkeyzi.oauth.im.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "monkeyzi")
@Data
@Component
public class ImServerConfig {

   private ImProperties im=new ImProperties();


}

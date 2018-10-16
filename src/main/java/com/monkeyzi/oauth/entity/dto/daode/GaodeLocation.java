package com.monkeyzi.oauth.entity.dto.daode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GaodeLocation extends GaodeDto {

    private String province;

    private String city;

    private String adcode;

    private String rectangle;
}

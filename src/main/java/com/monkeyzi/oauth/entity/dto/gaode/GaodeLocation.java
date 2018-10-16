package com.monkeyzi.oauth.entity.dto.gaode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GaodeLocation<T> extends GaodeDto {

    private T province;

    private T city;

    private T adcode;

    private T rectangle;
}

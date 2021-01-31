package com.shopapp.products.beans;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductListVO implements Serializable {
    private static final long serialVersionUID = 6567879334741385352L;

    private List<Long> productIds;
}

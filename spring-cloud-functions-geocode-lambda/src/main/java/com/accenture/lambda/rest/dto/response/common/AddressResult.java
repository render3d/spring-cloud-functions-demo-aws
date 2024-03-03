package com.accenture.lambda.rest.dto.response.common;

import java.math.BigDecimal;

public record AddressResult(String addressString, String postcode, BigDecimal latitude, BigDecimal longitude) {}

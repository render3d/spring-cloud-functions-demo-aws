package com.accenture.lambda.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Location(BigDecimal x, BigDecimal y) {}

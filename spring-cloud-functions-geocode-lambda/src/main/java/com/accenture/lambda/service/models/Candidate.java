package com.accenture.lambda.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Candidate(String address, Location location, BigDecimal score, Attributes attributes, Extent extent) {}

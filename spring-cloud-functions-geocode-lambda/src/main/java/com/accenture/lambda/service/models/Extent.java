package com.accenture.lambda.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Extent(
        @JsonProperty("xmin") BigDecimal xMin,
        @JsonProperty("ymin") BigDecimal yMin,
        @JsonProperty("xmax") BigDecimal xMax,
        @JsonProperty("ymax") BigDecimal yMax) {}

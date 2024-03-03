package com.accenture.lambda.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SpatialReference(Integer wkid, Integer latestWkid) {}

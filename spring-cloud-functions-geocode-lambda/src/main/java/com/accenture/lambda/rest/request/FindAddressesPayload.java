package com.accenture.lambda.rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FindAddressesPayload(String address, Integer maxResults, Boolean fuzzy) {}

package com.accenture.lambda.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FindAddressCandidatesResponse(SpatialReference spatialReference, List<Candidate> candidates) {}

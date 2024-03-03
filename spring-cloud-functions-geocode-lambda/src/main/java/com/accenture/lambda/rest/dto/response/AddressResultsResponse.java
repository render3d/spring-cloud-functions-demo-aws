package com.accenture.lambda.rest.dto.response;

import com.accenture.lambda.rest.dto.response.common.AddressResult;
import java.util.List;
import lombok.Getter;

@Getter
public final class AddressResultsResponse {
    private final List<AddressResult> matchedAddresses;

    private final Integer totalResults;

    public AddressResultsResponse(final List<AddressResult> matchedAddresses) {
        this.matchedAddresses = matchedAddresses;
        this.totalResults = matchedAddresses.size();
    }
}

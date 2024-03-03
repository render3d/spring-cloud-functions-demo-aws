package com.accenture.lambda.service;

import com.accenture.lambda.rest.dto.response.AddressResultsResponse;
import com.accenture.lambda.rest.dto.response.common.AddressResult;
import com.accenture.lambda.service.models.FindAddressCandidatesResponse;
import java.util.List;
import java.util.Objects;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FindAddressService {
    public static final String LOC_NAME_WORLD = "World";

    public AddressResultsResponse findAddressesResponseMapping(
            FindAddressCandidatesResponse findAddressCandidatesResponse) {
        final List<AddressResult> matchedAddresses = findAddressCandidatesResponse.candidates().stream()
                .map(candidate -> {
                    if (!Objects.equals(candidate.attributes().locName(), LOC_NAME_WORLD)) {
                        throw new IllegalStateException(String.format(
                                "Unexpected value for Candidate.Attributes.Loc_name field in ArcGIS  /findAddressCandidates response: %s",
                                candidate.attributes().locName()));
                    }

                    return new AddressResult(
                            candidate.address(),
                            candidate.attributes().postal(),
                            candidate.location().x(),
                            candidate.location().y());
                })
                .toList();

        return new AddressResultsResponse(matchedAddresses);
    }
}

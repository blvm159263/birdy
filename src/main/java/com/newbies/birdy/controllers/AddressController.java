package com.newbies.birdy.controllers;

import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name ="Address API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/addresses")
public class AddressController  {

    private final AddressService addressService;

    @Operation(summary = "Get Address by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "200", description = "Return AddressDTO"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{address-id}")
    public ResponseEntity<?> getAddressById(@PathVariable(name = "address-id") Integer addressId){
        return ResponseEntity.ok(addressService.getAddressDTOById(addressId));
    }
}

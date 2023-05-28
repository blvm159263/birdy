package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.AddressDTO;
import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController  {

    private final AddressService addressService;

    @Operation(summary = "Create new Address for User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Can't create address! Bad Request!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "201", description = "Created successfully! Return address id!"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<?> createAddress(@PathVariable(name = "user-id") Integer userId, @RequestBody AddressDTO addressDTO){
        Integer aid = addressService.createAddress(userId, addressDTO);
        if(aid == 0){
            return ResponseEntity.badRequest().body("Can't create address!");
        }else{
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        }
    }
}

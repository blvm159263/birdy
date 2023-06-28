package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.AddressDTO;
import com.newbies.birdy.dto.UserDTO;
import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.AddressService;
import com.newbies.birdy.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Authorization")
@Tag(name = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController  {

    private final AddressService addressService;
    private final UserService userService;

    @Operation(summary = "Create new Address for User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Can't create address! Bad Request!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "201", description = "Created successfully! Return address id!"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PostMapping("/{user-id}/addresses")
    public ResponseEntity<?> createAddress(@PathVariable(name = "user-id") Integer userId, @RequestBody AddressDTO addressDTO){
        Integer aid = addressService.createAddress(userId, addressDTO);
        if(aid == 0){
            return ResponseEntity.badRequest().body("Can't create address!");
        }else{
            return new ResponseEntity<>(aid, HttpStatus.CREATED);
        }
    }



    @Operation(summary = "Get All User Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Address not found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "500", description = "Internal error"),
            @ApiResponse(responseCode = "200", description = "Return list AddressDTO")
    })
    @GetMapping("/{user-id}/addresses")
    public ResponseEntity<?> getAllUserAddress(@PathVariable(name = "user-id") Integer userId){
        List<AddressDTO> list = addressService.getAllUserAddress(userId, true);
        if(list.size() > 0){
            return ResponseEntity.ok(list);
        }else{
            return new ResponseEntity<>("Can't find any address!", HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "Get User Information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "500", description = "Internal error"),
            @ApiResponse(responseCode = "200", description = "Return UserDTO")
    })
    @GetMapping("/{user-id}")
    public ResponseEntity<?> getUserInformation(@PathVariable(name = "user-id") Integer userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(summary = "Get User by Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "500", description = "Internal error"),
            @ApiResponse(responseCode = "200", description = "Return UserDTO")
    })
    @GetMapping("/phone/{phone-number}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable(name = "phone-number") String phoneNumber){
        UserDTO user = userService.getUserByPhoneNumber(phoneNumber, true);
        if(user== null){
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get Defaul Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "500", description = "Internal error"),
            @ApiResponse(responseCode = "200", description = "Return AddressDTO")
    })
    @GetMapping("/{user-id}/address-default")
    public ResponseEntity<?> getDefaultAddress(@PathVariable(name = "user-id") Integer userId){
        AddressDTO addressDTO = userService.getUserDefaultAddress(userId);
        return ResponseEntity.ok(addressDTO);
    }
}

package com.aptech.hrmapi.controller;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.UserDTO;
import com.aptech.hrmapi.request.UserRequest;
import com.aptech.hrmapi.response.ResponseBody;
import com.aptech.hrmapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.domain}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<ResponseBody> searchUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, userService.getAllUser(request)));
    }

    @GetMapping("/get-user")
    public ResponseEntity<ResponseBody> getUser(@RequestParam("id") Long id) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, userService.getUser(id)));
    }

    @PostMapping("/create-user")
    public ResponseEntity<ResponseBody> createUser(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, userService.saveOrUpdate(dto, AddOrUpdateType.ADD)));
    }

    @PutMapping("/update-user")
    public ResponseEntity<ResponseBody> updateUser(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, userService.saveOrUpdate(dto, AddOrUpdateType.UPDATE)));
    }

    @PutMapping("/delete-user")
    public ResponseEntity<ResponseBody> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }
}

package com.aptech.hrmapi.controller;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.RoleDTO;
import com.aptech.hrmapi.request.RoleRequest;
import com.aptech.hrmapi.response.ResponseBody;
import com.aptech.hrmapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.domain}/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/search")
    public ResponseEntity<ResponseBody> searchAction(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, roleService.getAllRole(request)));
    }

    @GetMapping("/get-role")
    public ResponseEntity<ResponseBody> getRole(@RequestParam("id") Long id) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, roleService.getRole(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createAction(@Valid @RequestBody RoleDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, roleService.saveOrUpdate(dto, AddOrUpdateType.ADD)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> updateAction(@Valid @RequestBody RoleDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, roleService.saveOrUpdate(dto, AddOrUpdateType.UPDATE)));
    }

    @PostMapping("/assign-role")
    public ResponseEntity<ResponseBody> assignRole(@RequestParam("username") String username, @RequestParam("roleCode") String roleCode) {
        roleService.assignRoleToUser(username, roleCode);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }

    @PutMapping("/change-status")
    public ResponseEntity<ResponseBody> changeStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        roleService.changeStatus(id, status);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBody> deleteAction(@RequestParam("id") Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }
}

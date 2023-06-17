package com.aptech.hrmapi.controller;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.ActionDTO;
import com.aptech.hrmapi.request.ActionRequest;
import com.aptech.hrmapi.response.ResponseBody;
import com.aptech.hrmapi.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.domain}/action")
@RequiredArgsConstructor
public class ActionController {
    private final ActionService actionService;

    @GetMapping("/search")
    public ResponseEntity<ResponseBody> searchAction(@RequestBody ActionRequest request) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, actionService.getAllAction(request)));
    }

    @GetMapping("/get-action")
    public ResponseEntity<ResponseBody> getAction(@RequestParam("id") Long id) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, actionService.getAction(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createAction(@Valid @RequestBody ActionDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, actionService.saveOrUpdate(dto, AddOrUpdateType.ADD)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> updateAction(@Valid @RequestBody ActionDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, actionService.saveOrUpdate(dto, AddOrUpdateType.UPDATE)));
    }

    @PutMapping("/change-status")
    public ResponseEntity<ResponseBody> changeStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        actionService.changeStatus(id, status);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBody> deleteAction(@RequestParam("id") Long id) {
        actionService.deleteAction(id);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }
}

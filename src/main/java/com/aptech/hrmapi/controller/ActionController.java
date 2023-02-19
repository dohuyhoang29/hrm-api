package com.aptech.hrmapi.controller;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.ActionDto;
import com.aptech.hrmapi.exception.CommonException;
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
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, actionService.getAllAction(request)));
        } catch (CommonException ex) {
            return ResponseEntity.ok(new ResponseBody(ex.getResponse().getResponseCode(), ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseBody(Response.SYSTEM_ERROR));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createAction(@Valid @RequestBody ActionDto dto) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, actionService.saveOrUpdate(dto, AddOrUpdateType.ADD)));
        } catch (CommonException ex) {
            return ResponseEntity.ok(new ResponseBody(ex.getResponse().getResponseCode(), ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseBody(Response.SYSTEM_ERROR));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> updateAction(@Valid @RequestBody ActionDto dto) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, actionService.saveOrUpdate(dto, AddOrUpdateType.UPDATE)));
        } catch (CommonException exception) {
            return ResponseEntity.ok(new ResponseBody(exception.getResponse().getResponseCode(), exception.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseBody(Response.SYSTEM_ERROR));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBody> deleteAction(@RequestParam("id") Long id) {
        try {
            actionService.deleteAction(id);
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
        } catch (CommonException exception) {
            return ResponseEntity.ok(new ResponseBody(exception.getResponse().getResponseCode(), exception.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseBody(Response.SYSTEM_ERROR));
        }
    }
}

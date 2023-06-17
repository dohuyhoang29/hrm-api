package com.aptech.hrmapi.controller;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.dto.FeatureDTO;
import com.aptech.hrmapi.request.FeatureRequest;
import com.aptech.hrmapi.response.ResponseBody;
import com.aptech.hrmapi.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.domain}/feature")
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureService featureService;

    @GetMapping("/search")
    public ResponseEntity<ResponseBody> searchAction(@RequestBody FeatureRequest request) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, featureService.getAllFeature(request)));
    }

    @GetMapping("/get-feature")
    public ResponseEntity<ResponseBody> getFeature(@RequestParam("id") Long id) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, featureService.getFeature(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createAction(@Valid @RequestBody FeatureDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, featureService.saveOrUpdate(dto, AddOrUpdateType.ADD)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> updateAction(@Valid @RequestBody FeatureDTO dto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, featureService.saveOrUpdate(dto, AddOrUpdateType.UPDATE)));
    }

    @PutMapping("/change-status")
    public ResponseEntity<ResponseBody> changeStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        featureService.changeStatus(id, status);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBody> deleteAction(@RequestParam("id") Long id) {
        featureService.deleteFeature(id);
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS));
    }
}

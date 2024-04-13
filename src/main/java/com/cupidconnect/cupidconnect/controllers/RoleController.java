package com.cupidconnect.cupidconnect.controllers;


import com.cupidconnect.cupidconnect.services.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.prefix}/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    @GetMapping
    public ResponseEntity<?> getAllRoles(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

//    @PostMapping
//    public ResponseEntity<?> createRole(
//            @RequestBody Role role
//
//    ) {
//        roleService.createRole(role);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

}

package cn.org.faster.framework.admin.permission.controller;

import cn.org.faster.framework.admin.permission.entity.SysPermission;
import cn.org.faster.framework.admin.permission.model.SysPermissionAddReq;
import cn.org.faster.framework.admin.permission.model.SysPermissionUpdateReq;
import cn.org.faster.framework.admin.permission.service.SysPermissionService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangbowen
 */
@RestController
@RequestMapping("/sys/permissions")
@AllArgsConstructor
public class SysPermissionController {
    private SysPermissionService sysPermissionService;

    /**
     * @return 权限列表
     */
    @GetMapping("/tree")
    @RequiresPermissions("permissions:tree")
    public ResponseEntity tree() {
        return ResponseEntity.ok(sysPermissionService.treeList());
    }

    /**
     * @return 权限列表
     */
    @GetMapping
    @RequiresPermissions("permissions:list")
    public ResponseEntity list() {
        return ResponseEntity.ok(sysPermissionService.list());
    }

    /**
     * @param permissionId 权限id
     * @return 权限详情
     */
    @GetMapping("/{permissionId}")
    @RequiresPermissions("permissions:info")
    public ResponseEntity info(@PathVariable Long permissionId) {
        return ResponseEntity.ok(sysPermissionService.info(permissionId));
    }

    /**
     * 新增权限
     *
     * @param request 添加权限实体
     * @return ResponseEntity
     */
    @PostMapping
    @RequiresPermissions("permissions:add")
    public ResponseEntity add(@Validated @RequestBody SysPermissionAddReq request) {
        SysPermission insert = new SysPermission();
        BeanUtils.copyProperties(request, insert);
        return sysPermissionService.add(insert);
    }

    /**
     * @param request      更新权限实体
     * @param permissionId 权限id
     * @return ResponseEntity
     */
    @PutMapping("/{permissionId}")
    @RequiresPermissions("permissions:modify")
    public ResponseEntity update(@RequestBody SysPermissionUpdateReq request, @PathVariable Long permissionId) {
        SysPermission update = new SysPermission();
        BeanUtils.copyProperties(request, update);
        update.setId(permissionId);
        return sysPermissionService.update(update);
    }

    /**
     * 删除权限
     *
     * @param permissionId 权限id
     * @return ResponseEntity
     */
    @DeleteMapping("/{permissionId}")
    @RequiresPermissions("permissions:delete")
    public ResponseEntity delete(@PathVariable Long permissionId) {
        sysPermissionService.delete(permissionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

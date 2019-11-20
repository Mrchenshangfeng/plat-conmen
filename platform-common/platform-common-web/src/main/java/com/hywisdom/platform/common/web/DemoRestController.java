package com.hywisdom.platform.common.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈demo〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
//@RestController
//@RequestMapping(value = "/sys/dict")
@Api(description = "系统 - 字典")
public class DemoRestController {

//    @Autowired
//    private SysDictService sysDictService;
//
//    @ApiOperation(value = "字典详情")
//    @GetMapping("/{id}")
//    public ResponseMessage<SysDictEO> getById(@PathVariable("id") String id) {
//        return Result.success(sysDictService.get(id));
//    }
//
//    @ApiOperation(value = "字典查询")
//    @GetMapping("")
//    public ResponseMessage<PageInfo<SysDictEO>> list(Integer pageNo, Integer pageSize, String type, String description) {
//        PageInfo pageInfo = new PageInfo(pageNo, pageSize);
//        return Result.success(sysDictService.page(pageInfo, type, description));
//    }
//
//    @ApiOperation(value = "新增字典")
//    @PostMapping("")
//    public ResponseMessage<SysDictEO> save(@RequestBody SysDictEO sysMenuEO) {
//        sysMenuEO.setId(UUID.randomUUID10());
//        sysMenuEO.setDelFlag(DeleteFlagEnum.NORMAL.getValue());
//        return Result.success(sysDictService.save(sysMenuEO));
//    }

//    @ApiOperation(value = "修改字典")
//    @PutMapping("")
//    public ResponseMessage<SysDictEO> update(@RequestBody SysDictEO sysMenuEO) {
//        return Result.success(sysDictService.update(sysMenuEO));
//    }
//
//    @ApiOperation(value = "删除字典")
//    @DeleteMapping("/{id}")
//    public ResponseMessage deleteById(@PathVariable("id") String id) {
//        sysDictService.delete(id);
//        return Result.success();
//    }
//
//    @ApiOperation(value = "获取所有字典类型")
//    @GetMapping("/type")
//    public ResponseMessage<List<String>> listDictType() {
//        return Result.success(sysDictService.listDictType());
//    }
}

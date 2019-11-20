package com.hywisdom.platform.common.web;

import com.hywisdom.platform.common.model.entity.BaseEntity;
import com.hywisdom.platform.common.model.http.ResponseMessage;
import com.hywisdom.platform.common.model.http.Result;
import com.hywisdom.platform.common.service.service.BaseService;
import com.hywisdom.platform.common.utils.CodeUtils;
import com.hywisdom.platform.common.utils.GenericeClassUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈CRUDController〉
 *
 * @author wangxz
 * @create 2018/11/14
 * @since 1.0.0
 */
@Slf4j
@Api
@RestController
public abstract class BaseCrudController<T extends BaseEntity,S extends BaseService<T>> extends BaseController {

    @SuppressWarnings("unchecked")
    protected Class<T> entityClass = (Class<T>) GenericeClassUtils.getSuperClassGenricType(this.getClass(), 0);

    @Autowired
    protected S service;

    @ApiOperation(value = "根据Id查询实体", notes = "selectById")
    @ApiImplicitParam(name = "id", value = "实体id", required = true)
    @GetMapping(value = "/{id}")
    public ResponseMessage<T> getById(@PathVariable(value = "id") final String id) {
        T entity;
        if (StringUtils.isNoneBlank(id)) {
            entity = service.getById(id);
        } else {
            return Result.message(String.valueOf(HttpStatus.NO_CONTENT.value()), "没有结果", null);
        }
        return Result.success(entity);
    }

    @ApiOperation(value = "新增实体", notes = "add")
    @ApiImplicitParam(name = "entity", value = "实体Json", required = true, dataType = "application/json")
    @PostMapping(value = "")
    public ResponseMessage<T> add(@Valid @RequestBody final T entity) {
        return service.save(entity) ? Result.success(entity) : Result.error();
    }

    @ApiOperation(value = "更新", notes = "update")
    @ApiImplicitParam(name = "entity", value = "实体Json", required = true, dataType = "application/json")
    @PutMapping(value = "/{id}")
    public ResponseMessage<T> update(@PathVariable(value = "id") final String id, @RequestBody final T entity) {
        //TODO 检验是否存在该ID
        return service.updateById(entity) ? Result.success(entity) : Result.error();
    }


    @ApiOperation(value = "根据Id删除实体", notes = "delete")
    @ApiImplicitParam(name = "id", value = "实体id", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}")
    public ResponseMessage delete(@PathVariable(value = "id") final String id) {

        return service.removeById(id) ? Result.success() : Result.error();
    }

    /**
     * 查询所有实体
     */
    @ApiOperation(value = "查询所有实体", notes = "findAllEntitys")
    @SuppressWarnings({"rawtypes", "unchecked"})
    @GetMapping(value = "/list")
    @ResponseBody
    public ResponseMessage<List<T>> findAll() {
        List<T> entityList = service.list();
        if (entityList != null) {
            log.info("[CRUDController.getAll] result entityList.size : {} ", entityList.size());
        }
        return Result.success(service.list());
    }
}

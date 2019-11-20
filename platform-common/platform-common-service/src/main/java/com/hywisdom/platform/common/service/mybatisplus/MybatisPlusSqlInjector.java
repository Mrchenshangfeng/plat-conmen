package com.hywisdom.platform.common.service.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 〈功能简述〉<br>
 * 〈sql注入器〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class MybatisPlusSqlInjector extends AbstractSqlInjector {

    @Override
    public boolean isInjectSqlRunner() {
        return false;
    }

    @Override
    public List<AbstractMethod> getMethodList() {
        return Stream.of(
                new Insert(),
                new Delete(),
                new DeleteByMap(),
                new DeleteById(),
                new DeleteBatchByIds(),
                new Update(),
                new UpdateById(),
                new SelectById(),
                new SelectBatchByIds(),
                new SelectByMap(),
                new SelectOne(),
                new SelectCount(),
                new SelectMaps(),
                new SelectMapsPage(),
                new SelectObjs(),
                new SelectList(),
                new SelectPage()
        ).collect(Collectors.toList());
    }

}

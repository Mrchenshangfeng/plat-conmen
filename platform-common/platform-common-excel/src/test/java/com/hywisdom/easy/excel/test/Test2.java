package com.hywisdom.easy.excel.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hywisdom.easy.excel.demo.Elplan;

public class Test2<T> {
	// 新建的集合，读取excel时没有id的直接放在这里，不进入到map中
	private Set<Elplan<T>> addList = new HashSet<Elplan<T>>();
	// 删除的集合
	private Set<Elplan<T>> delList = new HashSet<Elplan<T>>();
	// 更新的集合
	private Set<Elplan<T>> updateList = new HashSet<Elplan<T>>();
	// 该项目Excel中读取的集合,没有id的直接保存在addList，该map只存有id的对象
	private Map<String, Elplan<T>> excelMap = new HashMap<String, Elplan<T>>();
	// 该项目数据库中读取的集合
	private Map<String, Elplan<T>> dataMap = new HashMap<String, Elplan<T>>();
	// 临时Map
	private Map<String, Elplan<T>> tempMap = new HashMap<String, Elplan<T>>();

	public void doBuild() {
		tempMap = new HashMap<String, Elplan<T>>(dataMap);
		// 获取删除集合
		tempMap.keySet().removeAll(excelMap.keySet());
		// 分析删除集合,已完成的Elplan重构wbs并添加到update集合中
		this.reloadDelMap(tempMap);
		// 其余所有带有id的excel数据进行compare比较，判断是否要更新
		this.reloadUpdateMap(excelMap);
	}

	private void reloadDelMap(Map<String, Elplan<T>> map) {
		Iterator<Entry<String, Elplan<T>>> iter = tempMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Elplan<T>> entry = iter.next();
			Elplan<T> elplan = entry.getValue();
			if ("已完成".equals(elplan.getStatus())) {
				this.updateList.add(reloadWBS(elplan));
			} else {
				this.delList.add(elplan);
			}
		}
	}

	private void reloadUpdateMap(Map<String, Elplan<T>> map) {
		Iterator<Entry<String, Elplan<T>>> iter = tempMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Elplan<T>> entry = iter.next();
			Elplan<T> excelBean = entry.getValue();
			Elplan<T> dataBean = dataMap.get(entry.getKey());
			if (dataBean == null || excelBean.compareTo((T) dataBean) != 0) {
				this.updateList.add(entry.getValue());
			}
		}
	}

	private Elplan<T> reloadWBS(Elplan<T> elplan) {
		return elplan;
	}
}

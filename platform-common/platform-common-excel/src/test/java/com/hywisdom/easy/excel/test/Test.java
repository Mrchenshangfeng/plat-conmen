package com.hywisdom.easy.excel.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hywisdom.easy.excel.demo.Elplan;

public class Test<T> {
	// 该项目Excel中读取的集合,没有id的直接保存在addList，该map只存有id的对象
	public Map<String, Elplan<T>> excelMap = new HashMap<String, Elplan<T>>();
	// 该项目数据库中读取的集合
	public Map<String, Elplan<T>> dataMap = new HashMap<String, Elplan<T>>();
	// 临时Map
	public Map<String, Elplan<T>> tempMap = new HashMap<String, Elplan<T>>();
	// 新建的集合，读取excel时没有id的直接放在这里，不进入到map中
	public Set<Elplan<T>> addList = new HashSet<Elplan<T>>();
	// 删除的集合
	public Set<Elplan<T>> delList = new HashSet<Elplan<T>>();
	// 更新的集合
	public Set<Elplan<T>> updateList = new HashSet<Elplan<T>>();

	public void doBuild(){
		tempMap = new HashMap<String, Elplan<T>>(dataMap);
		//获取删除集合
		tempMap.keySet().removeAll(excelMap.keySet());
		//分析删除集合,已完成的Elplan重构wbs并添加到update集合中
		this.reloadDelMap(tempMap);
		//获取新增的更新集合，excel中有，但数据库map中没有，则一定需要更新parentID
		tempMap = new HashMap<String, Elplan<T>>(excelMap); 
		tempMap.keySet().removeAll(dataMap.keySet());
		this.updateList.addAll(tempMap.values());
		//需要判断的更新集合
		tempMap = new HashMap<String, Elplan<T>>(excelMap); 
		tempMap.keySet().retainAll(dataMap.keySet());
		this.reloadUpdateMap(tempMap);
	}
	
	private void reloadDelMap(Map<String, Elplan<T>> map){
		Iterator<Entry<String, Elplan<T>>> iter = tempMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, Elplan<T>> entry = iter.next();
			Elplan<T> elplan = entry.getValue();
			if("已完成".equals(elplan.getStatus())){
				this.updateList.add(reloadWBS(elplan));
			}else{
				this.delList.add(elplan);
			}
		}
	}
	
	private void reloadUpdateMap(Map<String, Elplan<T>> map){
		Iterator<Entry<String, Elplan<T>>> iter = tempMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, Elplan<T>> entry = iter.next();
			if(entry.getValue().compareTo((T) dataMap.get(entry.getKey()))!=0){
				this.updateList.add(entry.getValue());
			}
		}
	}

	private Elplan<T> reloadWBS(Elplan<T> elplan) {
		return elplan;
	}
}

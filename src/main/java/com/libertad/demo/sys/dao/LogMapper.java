package com.libertad.demo.sys.dao;

import java.util.List;

import com.libertad.demo.sys.entity.LogEntity;


/**
 * 日志
 * @author Bai
 *
 */
public interface LogMapper {
	//id查询
	LogEntity getlogById(Long id);
	//动态获取列表
	List<LogEntity> getListLogByPageInfo(LogEntity log);
	//新增一条
	int saveLog(LogEntity log);
	//更新一条
	int updateLog(LogEntity log);
	//删除一条
	int removeLog(Long id);
	//删除多条
	int batchRemoveLog(Long[] ids);
	//多条新增
	void saveLogList(List<LogEntity> list);
}

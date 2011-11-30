package org.varks.society.local.dao;

import java.util.List;

import org.varks.society.local.entities.Record;


public interface RecordDAO {
	/**这个方式通过Record的id来获取Record的对象
	 * 
	 * @param id Record的id
	 * @return Record对象
	 */
	public Record findById(Object id);
	
	/**
	 * 插入Record实体
	 * @param Record的对象
	 * @return 如果成功返回插入的实体的id,如果失败,返回null
	 */
	public  Long insert(Record record);
	
	/**
	 * 更新操作
	 * @param Record
	 * @return 返回boolean 若成功,返回
	 *true
	 */
	public boolean update(Record record);
	
	/**
	 * 删除操作
	 * @param Record
	 * @return boolean,若成功返回true.
	 */
	public boolean delete(Record record);
	
	/**列出全部的Record对像,以供筛选.
	 * 
	 * @return 返回Record list.
	 */
	public List<Record> findAll();
	
	/**
	 * 分页显示列出的全部Record对象
	 * @param pageNumber 页码
	 * @param pageCapacity 页容量
	 * @return 返回Record list.
	 */
	public List<Record> findAll(int pageNumber,int pageCapacity);
	
	/**
	 * 通过键值删除实体
	 * @param o user键值
	 * @return 若成功,返回true.
	 */
	public boolean deleteById(Object o);
	
	/**通过用户ID查找记录, 这是分页版本
	 * 
	 * @param id
	 * @param pageNum
	 * @param pageCapacity
	 * @return
	 */
	public List<Record> findByUserId(Long id, int pageNum, int pageCapacity);
	
	/**通过用户ID查找所有记录
	 * 
	 * @param id
	 * @return
	 */
	public List<Record> findByUserId(Long id);

}

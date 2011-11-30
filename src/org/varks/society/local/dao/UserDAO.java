package org.varks.society.local.dao;

import java.util.List;

import org.varks.society.local.entities.Login;
import org.varks.society.local.entities.User;

public interface UserDAO {
	
	/**这个方式通过 User的id来获取Record的对象
	 * 
	 * @param id  User的id
	 * @return  User对象
	 */
	public User findById(Object id);
	
	/**
	 * 插入 User实体
	 * @param  User的对象
	 * @return 如果成功返回插入的实体的id,如果失败,返回null
	 */
	public Long insert(User user);
	
	/**
	 * 更新操作
	 * @param  User
	 * @return 返回boolean 若成功,返回
	 *true
	 */
	public boolean update(User user);
	
	/**
	 * 删除操作
	 * @param  User
	 * @return boolean,若成功返回true.
	 */
	public boolean delete(User user);

	/**列出全部的 User对像,以供筛选.
	 * 
	 * @return 返回 User list.
	 */
	public List<User> findAll();
	
	/**
	 * 分页显示列出的全部 User对象
	 * @param pageNumber 页码
	 * @param pageCapacity 页容量
	 * @return 返回Record list.
	 */
	public List<User>findAll(int pageNumber,int pageCapacity);
	
	public User findByLogin(Login login);
	
	/**
	 * 通过键值删除实体
	 * @param o user键值
	 * @return 若成功,返回true.
	 */
	public boolean deleteById(Object o);
	
	/**通过UserName发现User实体
	 * 
	 */
	public User findByUserName(String userName);
	
}

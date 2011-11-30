package org.varks.society.local.dao;

import java.util.List;

import org.varks.society.local.entities.PhotoAlbum;
import org.varks.society.local.entities.User;

public interface PhotoAlbumDAO {
	/**这个方式通过photoAlbum 的id来获取photoAlbum的对象
	 * 
	 * @param id PhotoAlbum的id
	 * @return PhotoAlbum对象
	 */
	public PhotoAlbum findById(Object id);
	
	/**
	 * 插入相册实体
	 * @param photoAlbum的对象
	 * @return 如果成功返回插入的实体的id,如果失败,返回null
	 */
	public Long insert(PhotoAlbum photoAlbum);
	
	/**
	 * 更新操作
	 * @param photoAlbum
	 * @return 返回boolean 若成功,返回
	 *true
	 */
	public boolean update(PhotoAlbum photoAlbum);
	
	/**
	 * 删除操作
	 * @param photoAlbum
	 * @return boolean,若成功返回true.
	 */
	public boolean delete(PhotoAlbum photoAlbum);
	
	/**列出全部的photoAlbum对像,以供筛选.
	 * 
	 * @return 返回photoAlbum list.
	 */
	public List<PhotoAlbum> findAll();
	
	/**
	 * 分页显示列出的全部相册对象
	 * @param pageNumber 页码
	 * @param pageCapacity 页容量
	 * @return 返回photoAlbum list.
	 */
	public List<PhotoAlbum> findAll(int pageNumber,int pageCapacity);
	
	/**
	 * 通过键值删除实体
	 * @param o user键值
	 * @return 若成功,返回true.
	 */
	public boolean deleteById(Object o);
	
	/**
	 * 通过user查找photoAlbum
	 * @param user对象
	 * @return返回相册对象 list
	 */
	public List<PhotoAlbum> findByUser(User user);
	
	/**
	 * 
	 * @param user 对象
	 * @param pageNumber页码
	 * @param pageCapacity每页容量
	 * @return 返回相册的list
	 */
	public List<PhotoAlbum> findByUser(User user,int pageNumber,int pageCapacity);
	
	
}

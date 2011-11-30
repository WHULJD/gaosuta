package org.varks.society.local.dao;

import java.util.List;

import org.varks.society.local.entities.Photo;



public interface PhotoDAO {
	/**
	 * 通过id查找Photo对象
	 * @param photo id
	 * @return 返回photo对象,若失败,返回null
	 */
	public Photo findById(Object o);
	
	/**
	 * 插入photo 对象
	 * @param photo 对象
	 * @return 若成功,返回插入对象的id,若失败,返回null.
	 */
	public  Long insert(Photo photo);
	
	/**
	 * 更新图片
	 * @param photo 相册对象
	 * @return 若成功返回true.
	 */
	public boolean update(Photo photo);
	
	/**
	 * 删除图片实体
	 * @param photo 对象
	 * @return 若成功删除返回true.
	 */
	public boolean delete(Photo photo);
	
	/**列出全部的photo对像,以供筛选.
	 * 
	 * @return 返回photo list.
	 */
	public List<Photo> findAll();
	
	/**
	 * 分页显示列出的全部photo对象
	 * @param pageNumber 页码
	 * @param pageCapacity 页容量
	 * @return 返回photo list.
	 */
	public List<Photo> findAll(int pageNumber,int pageCapacity);
	
	/**
	 * 通过键值删除实体
	 * @param o user键值
	 * @return 若成功,返回true.
	 */
	public boolean deleteById(Object o);
	
	/**通过相册ID获取相册列表
	 * 
	 * @param id
	 * @param pageNum
	 * @param pageCapacity
	 * @return
	 */
	public List<Photo> findByPhotoAlbumId(Long id, int pageNum, int pageCapacity);

}

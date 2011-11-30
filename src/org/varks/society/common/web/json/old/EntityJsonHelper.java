package org.varks.society.common.web.json.old;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.varks.society.common.reflect.ReflectUtils;

/**实体类转化为Json工具的相关辅助. 可以在转化为json字串过程中将有关@OneToMany的属性置空
 * 
 * @author lenovo
 *
 */
public class EntityJsonHelper {
	public void shieldEntityMapping(Object entity) {
		List<Field> fieldList = ReflectUtils.getAllFields(entity.getClass());
		for (Field field : fieldList)
			handleField(field, entity);
	}

	private void handleField(Field field, Object entity) {
		if (isMappingField(field)) {
			field.setAccessible(true);
			fieldSetNull(field, entity);
		}
	}

	private boolean isMappingField(Field field) {
		if (field.getAnnotation(OneToOne.class) != null)
			return true;
		else if (field.getAnnotation(OneToMany.class) != null)
			return true;
		else if (field.getAnnotation(ManyToOne.class) != null)
			return true;
		else if (field.getAnnotation(ManyToMany.class) != null)
			return true;
		else
			return false;
	}

	private void fieldSetNull(Field field, Object entity) {
		try {
			field.set(entity, null);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}

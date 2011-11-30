package org.varks.society.local.web.helper;

import org.varks.society.common.web.json.old.EntityJsonHelper;

public class JsonMappingShieldHelper {
	private static EntityJsonHelper helper = new EntityJsonHelper();
	
	public static void mappedJson(Object entity) {
		helper.shieldEntityMapping(entity);
	}
}

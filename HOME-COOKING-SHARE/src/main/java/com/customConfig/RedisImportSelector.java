package com.customConfig;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class RedisImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(
				importingClassMetadata.getAnnotationAttributes(EnableRedisInitModule.class.getName(), false));

		boolean init = attributes.getBoolean("init");
		if (init) {
			return new String[] { RedisInitialConfig.class.getName() };
		} else {
			return new String[] {};
		}
	}

}

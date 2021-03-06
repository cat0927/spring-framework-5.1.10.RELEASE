/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

/**
 * {@link Condition} that matches based on the value of a {@link Profile @Profile}
 * annotation.
 *
 * @author Chris Beams
 * @author Phillip Webb
 * @author Juergen Hoeller
 * @since 4.0
 */
class ProfileCondition implements Condition {

	/**
	 *  `@Profile` 原理。
	 * @return
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

		// 获取 “Profile” 所有的属性方法值。
		MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(Profile.class.getName());
		if (attrs != null) {
			for (Object value : attrs.get("value")) {

				/**
				 *  1、“ Profiles.of() ” 最终调用 {@link org.springframework.core.env.ProfilesParser.ParsedProfiles#parse(String...)}
				 *  	对象参数进行解析。最终，调用 “ ParsedProfiles ” 构造函数进行初始化。
				 *
				 *  Profile 匹配 {@link org.springframework.core.env.AbstractEnvironment#acceptsProfiles(Profiles)}
				 */
				if (context.getEnvironment().acceptsProfiles(Profiles.of((String[]) value))) {

					// 如果和环境参数匹配的话, 则返回true
					return true;
				}
			}
			return false;
		}
		return true;
	}

}

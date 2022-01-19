/*
 * Copyright 2002-2020 the original author or authors.
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

/**
 * A {@link Condition} that offers more fine-grained control when used with
 * {@code @Configuration}. Allows certain conditions to adapt when they match
 * based on the configuration phase. For example, a condition that checks if a bean
 * has already been registered might choose to only be evaluated during the
 * {@link ConfigurationPhase#REGISTER_BEAN REGISTER_BEAN} {@link ConfigurationPhase}.
 *
 * @author Phillip Webb
 * @since 4.0
 * @see Configuration
 */
public interface ConfigurationCondition extends Condition {

	/**
	 * Return the {@link ConfigurationPhase} in which the condition should be evaluated.
	 */
	ConfigurationPhase getConfigurationPhase();


	/**
	 * The various configuration phases where the condition could be evaluated.
	 * 条件判断在什么时候执行？
	 *
	 * 条件判断的执行分为两个阶段，如下：
	 * 1、配置类解析阶段(ConfigurationPhase.PARSE_CONFIGURATION)：在这个阶段会得到一批配置类的信息和一些需要注册的Bean。
	 * 2、Bean注册阶段(ConfigurationPhase.REGISTER_BEAN)：将配置类解析阶段得到的配置类和需要注册的Bean注入到容器中。
	 *
	 * 默认都是配置解析阶段，其实也就够用了，但是在Spring Boot中使用了ConfigurationCondition，这个接口可以自定义执行阶段，
	 * 比如@ConditionalOnMissingBean都是在Bean注册阶段执行，因为需要从容器中判断Bean。
	 *
	 * 这个两个阶段有什么不同呢？：
	 * 1、配置类解析阶段只是将需要加载配置类和一些Bean（被@Conditional注解过滤掉之后）收集起来，
	 * 2、而Bean注册阶段是将的收集来的Bean和配置类注入到容器中，
	 * 3、如果在配置类解析阶段执行Condition接口的matches()接口去判断某些Bean是否存在IOC容器中，这个显然是不行的，因为这些Bean还未注册到容器中。
	 */
	enum ConfigurationPhase {

		/**
		 * The {@link Condition} should be evaluated as a {@code @Configuration}
		 * class is being parsed.
		 * <p>If the condition does not match at this point, the {@code @Configuration}
		 * class will not be added.
		 */
		PARSE_CONFIGURATION,

		/**
		 * The {@link Condition} should be evaluated when adding a regular
		 * (non {@code @Configuration}) bean. The condition will not prevent
		 * {@code @Configuration} classes from being added.
		 * <p>At the time that the condition is evaluated, all {@code @Configuration}
		 * classes will have been parsed.
		 */
		REGISTER_BEAN
	}

}

/**
 * @(#) ObjectMapperTest.class $version 2017. 02. 02
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.type;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ObjectMapperTest 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class ObjectMapperTest {
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) throws IOException {
		String str = "{\"code\":\"OK\", \"value1\": 1, \"value2\": 59.12}";

		Map<String, Object> map = objectMapper.readValue(str, new TypeReference<Map<String, Object>>() {});
		System.out.println(map);
		System.out.println(map.get("code").getClass());
		System.out.println(map.get("value1").getClass());
		System.out.println(map.get("value2").getClass());
		System.out.println(objectMapper.writeValueAsString(map));

		String str2 = "[{\"code\":\"OK\", \"value1\": 1, \"value2\": 59.12}, {\"code\":\"OK\", \"value1\": 1, \"value2\": 59.12}]";
		List<Map<String, Object>> list = objectMapper.readValue(str2, new TypeReference<List<Map<String, Object>>>() {});
		System.out.println(list);
		System.out.println(list.get(0).get("code").getClass());
		System.out.println(list.get(0).get("value1").getClass());
		System.out.println(list.get(0).get("value2").getClass());
		System.out.println(objectMapper.writeValueAsString(list));
	}
}
package kymr.github.io.functional;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.ParameterizedTypeReference;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Naver on 2017-01-06.
 */
public class RetryUtilsTest {

	@Test
	public void testRunnable() throws Exception {
		RetryUtils.retry(() -> System.out.println("hi"), new IOException(), "error occurs");
	}

	@Test(expectedExceptions = Exception.class)
	public void testRunnableWithException() throws Exception {
		RetryUtils.retry(() -> {
			throw new Exception();
		}, new IOException(), "error occurs");
	}

	@Test
	public void testCallable() throws Exception {
		String result = RetryUtils.retry(() -> "hi", new IOException(), "error occurs");
		System.out.println(result);

		List<String> results = RetryUtils.retry(() -> Arrays.asList("hi1", "hi2", "hi3"), new IOException(), "error occurs");
		results.forEach(str -> System.out.println("List : " + str));
	}

	@Test(expectedExceptions = Exception.class)
	public void testCallableException() throws Exception {
		String result = RetryUtils.retry(() -> {
			throw new Exception();
		}, new IOException(), "error occurs");
	}
}
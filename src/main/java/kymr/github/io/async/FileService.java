/**
 * @(#) FileService.class $version 2017. 01. 05
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * FileService 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
@Slf4j
@Service
public class FileService {
	public CompletableFuture<String> download(String url) {
		String destPath = newPath();

		return CompletableFuture.supplyAsync(() -> {
			try (ReadableByteChannel sourceChannel = Channels.newChannel(new URL(url).openStream());
				 FileOutputStream fos = new FileOutputStream(destPath);
			) {
				log.info("download {} -> {}", url, destPath);
				fos.getChannel().transferFrom(sourceChannel, 0, Long.MAX_VALUE);
			} catch (Exception exception) {
				log.error("error occurs when downloading the file {}", url);
			}

			return destPath;
		});
	}

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(10);

		return threadPoolTaskExecutor;
	}

	private String newPath() {
		return "C:/example.test";
	}
}
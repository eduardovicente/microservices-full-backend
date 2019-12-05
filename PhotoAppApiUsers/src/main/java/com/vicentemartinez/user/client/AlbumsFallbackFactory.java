package com.vicentemartinez.user.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vicentemartinez.user.ui.model.AlbumResponseModel;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@Component
public class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {

	@Override
	public AlbumsServiceClient create(Throwable cause) {
		return new AlbumsServiceClientFallback(cause);
	}

}

class AlbumsServiceClientFallback implements AlbumsServiceClient {

	private final Throwable cause;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public AlbumsServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when getAlbums was called with userId: " + id + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Error took place: " + cause.getLocalizedMessage());
		}
		return new ArrayList();
	}

}
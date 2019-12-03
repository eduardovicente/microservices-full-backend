package com.vicentemartinez.user.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vicentemartinez.user.ui.model.AlbumResponseModel;

@Component
public class AlbumsFallback implements AlbumsServiceClient {

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		return new ArrayList<>();
	}
	
}

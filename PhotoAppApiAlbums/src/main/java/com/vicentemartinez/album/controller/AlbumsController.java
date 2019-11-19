/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicentemartinez.album.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicentemartinez.album.model.AlbumEntity;
import com.vicentemartinez.album.service.AlbumsService;
import com.vicentemartinez.album.ui.model.AlbumResponseModel;
import com.vicentemartinez.album.util.ModelMapperUtil;

@RestController
@RequestMapping("/users/{id}/albums")
public class AlbumsController {
    
    @Autowired
    AlbumsService albumsService;
    @Autowired
    ModelMapperUtil modelMapper;
    
    @GetMapping( 
            produces = { 
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
            })
    public List<AlbumResponseModel> userAlbums(@PathVariable String id) {

        List<AlbumResponseModel> returnValue = new ArrayList<>();
        
        List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);
        
        if(albumsEntities == null || albumsEntities.isEmpty())
        {
            return returnValue;
        } 
        returnValue = modelMapper.mapListToObjects(albumsEntities, AlbumResponseModel.class);
        return returnValue;
    }
}

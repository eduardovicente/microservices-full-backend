package com.vicentemartinez.user.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperUtilImpl implements ModelMapperUtil {
	@Autowired
	ModelMapper modelMapper;

	@Override
	public <T, S>S mapToObject(T objectToMap, Class<S> objectClass) {
		return modelMapper.map(objectToMap, objectClass);
	}

	@Override
	public <T, S>List<S> mapListToObjects(List<T> objectToMap, Class<S> objectClass) {
		return objectToMap.stream().map(e -> modelMapper.map(e, objectClass))
				.collect(Collectors.toList());
	}
}
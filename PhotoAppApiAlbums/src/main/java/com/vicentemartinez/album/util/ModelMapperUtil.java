package com.vicentemartinez.album.util;

import java.util.List;

public interface ModelMapperUtil {

	<T, S> S mapToObject(T objectToMap, Class<S> objectClass);

	<T, S> List<S> mapListToObjects(List<T> objectToMap, Class<S> objectClass);

}

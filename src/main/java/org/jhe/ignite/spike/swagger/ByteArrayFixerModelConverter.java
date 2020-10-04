package org.jhe.ignite.spike.swagger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.SimpleType;

import io.swagger.converter.ModelConverter;
import io.swagger.converter.ModelConverterContext;
import io.swagger.jackson.AbstractModelConverter;
import io.swagger.models.properties.ByteArrayProperty;
import io.swagger.models.properties.Property;
import io.swagger.util.Json;

/**
 * This class is not used at runtime, only when generating swagger.json files.
 * This is used to work around a problem where byte[] -&gt; swagger -&gt; feign
 * -&gt; List&lt;byte[]&gt; *
 * https://github.com/kongchen/swagger-maven-plugin/issues/422 *
 * https://stackoverflow.com/questions/54010889/when-using-swagger-codegen-getting-listbyte-instead-of-simply-byte
 * 
 * @author M999JVE
 *
 */
public class ByteArrayFixerModelConverter extends AbstractModelConverter implements ModelConverter {
	public ByteArrayFixerModelConverter() {
		super(Json.mapper());
	}

	@Override
	public Property resolveProperty(Type type, ModelConverterContext context, Annotation[] annotations,
			Iterator<ModelConverter> chain) {
		if (isByteArray(type)) {
			// bypass the chain! It would convert the ByteArrayProperty to an Array of
			// ByteArrayProperty (bug in ModelModifier I think)
			return new ByteArrayProperty();
		}
		Property property = null;
		if (chain.hasNext()) {
			property = (chain.next()).resolveProperty(type, context, annotations, chain);
		}

		return property;
	}

	private boolean isByteArray(Type type) {
		boolean ret = type instanceof Class && type == byte[].class;
		if (!ret && type instanceof ArrayType) {
			ArrayType at = (ArrayType) type;
			JavaType contentType = at.getContentType();
			if (contentType instanceof SimpleType) {
				SimpleType st = (SimpleType) contentType;
				ret = st.getRawClass() == byte.class;
			}
		}
		return ret;
	}
}

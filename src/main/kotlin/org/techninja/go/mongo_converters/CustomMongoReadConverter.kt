package org.techninja.go.mongo_converters

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bson.Document
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.GenericConverter
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Component
import org.techninja.go.domain.Game

@Component
class CustomMongoReadConverter : GenericConverter {
    private val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


    override fun getConvertibleTypes(): MutableSet<GenericConverter.ConvertiblePair> {
        return mutableSetOf(Game::class.java).map {
            GenericConverter.ConvertiblePair(Document::class.java, it)
        }.toMutableSet()
    }

    override fun convert(source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): Any? {
        source as Document
        val targetClass = Class.forName(targetType.type.name)
        targetClass.getAllDeclaredField().forEach {field ->
            field.getAnnotation(Id::class.java)?.let {
                source["_id"]?.let {
                    source[field.name] = it
                }
            }
        }

        return objectMapper.convertValue(source, targetClass)
    }
}
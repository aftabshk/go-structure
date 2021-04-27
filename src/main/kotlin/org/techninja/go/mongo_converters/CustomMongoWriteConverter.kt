package org.techninja.go.mongo_converters

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bson.Document
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.GenericConverter
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Component
import org.techninja.go.domain.Game
import java.lang.reflect.Field

@Component
class CustomMongoWriteConverter: GenericConverter {
    private val objectMapper = jacksonObjectMapper()

    override fun getConvertibleTypes(): MutableSet<GenericConverter.ConvertiblePair> {
        return mutableSetOf(Game::class.java).map {
            GenericConverter.ConvertiblePair(it, Document::class.java)
        }.toMutableSet()
    }

    override fun convert(source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): Any? {
        val document = objectMapper.convertValue(source, Document::class.java)
        val sourceClass = Class.forName(sourceType.type.name)
        sourceClass.getAllDeclaredField().forEach {field ->
            field.getAnnotation(Id::class.java)?.let {
                field.trySetAccessible()
                document.remove(field.name)
                field.get(source)?.let {
                    document["_id"] = it
                }
            }
        }

        return document
    }
}

fun Class<*>.getAllDeclaredField(): Set<Field> {
    val declaredFieldOfThisClass = this.declaredFields
    return if (this.superclass.equals(Object::class.java)) {
        declaredFieldOfThisClass.toSet()
    } else {
        declaredFieldOfThisClass.toSet() + this.superclass.getAllDeclaredField()
    }
}
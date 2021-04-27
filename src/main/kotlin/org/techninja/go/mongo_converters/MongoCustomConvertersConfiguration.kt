package org.techninja.go.mongo_converters

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoCustomConvertersConfiguration(
    private val customMongoWriteConverter: CustomMongoWriteConverter,
    private val customMongoReadConverter: CustomMongoReadConverter
) {

    @Bean
    fun mongoCustomConverters(): MongoCustomConversions {
        return MongoCustomConversions(mutableListOf(customMongoWriteConverter, customMongoReadConverter))
    }
}
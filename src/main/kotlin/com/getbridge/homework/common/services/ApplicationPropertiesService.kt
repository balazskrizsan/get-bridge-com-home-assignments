package com.getbridge.homework.common.services

import org.springframework.stereotype.Service

@Service
class ApplicationPropertiesService {
    val serverEnv: String by lazy { System.getenv("SERVER_ENV") }
    val serverPort: String by lazy { System.getenv("SERVER_PORT") }
    val serverSslEnabled: String by lazy { System.getenv("SERVER_SSL_ENABLED") }
    val serverSslKeyStore: String by lazy { System.getenv("SERVER_SSL_KEY_STORE") }
    val serverSslKeyStorePassword: String by lazy { System.getenv("SERVER_SSL_KEY_STORE_PASSWORD") }
    val springDatasourceHikariMaximumPoolSize: String by lazy { System.getenv("SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE") }
    val springDatasourceHikariMinimumIdle: String by lazy { System.getenv("SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE") }
    val springDatasourcePassword: String by lazy { System.getenv("SPRING_DATASOURCE_PASSWORD") }
    val springDatasourceUrl: String by lazy { System.getenv("SPRING_DATASOURCE_URL") }
}

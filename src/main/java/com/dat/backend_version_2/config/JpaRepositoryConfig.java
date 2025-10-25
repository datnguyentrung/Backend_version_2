package com.dat.backend_version_2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Cấu hình cho JPA Repositories
 * Chỉ quét các repository trong package repository và loại trừ Redis repositories
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.dat.backend_version_2.repository",
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
        type = org.springframework.context.annotation.FilterType.REGEX,
        pattern = ".*Redis.*"
    )
)
public class JpaRepositoryConfig {
}

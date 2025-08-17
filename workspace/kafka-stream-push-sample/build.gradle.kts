tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {

//    implementation group: 'org.springframework.cloud', name: 'spring-cloud-stream-binder-kafka', version: '3.2.1'

    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:4.1.2")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.apache.kafka:kafka-streams")

    // db 세팅
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2")
}

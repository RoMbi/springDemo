package com.accenture.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

//    @Test
//    void returnsPage() {
//        // database is initialized with script "data.sql"
//        assertThat(
//                productRepository
//                        .findAllPage(PageRequest.of(0, 10))
//                        .getContent()
//                        .size())
//                .isEqualTo(10);
//    }
}

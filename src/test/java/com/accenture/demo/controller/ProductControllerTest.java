package com.accenture.demo.controller;

import com.accenture.demo.DemoApplication;
import com.accenture.demo.dao.ProductDao;
import com.accenture.demo.entity.Product;
import com.accenture.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = DemoApplication.class)
class ProductControllerTest {
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductDao mockProductDao;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    void testGetProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Name");
        product.setDescription("Desc");
        product.setPrice(12.99F);
        when(mockProductDao.get(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/product/1"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.description", is("Desc")))
                .andExpect(jsonPath("$.price", is(12.99)));

        verify(mockProductDao, times(1)).get(1L);
    }

    @Test
    void testCreateExistProductNameException() throws Exception {
        Product product = new Product();
        product.setId(2L);
        product.setName("Product Name");
        when(mockProductDao.findByName("Product Name")).thenReturn(product);

        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        mockMvc.perform(
                put("/product/create")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
        )
                .andExpect(status().isConflict())
                .andExpect(status().reason(containsString("Product already exists")));
    }
}
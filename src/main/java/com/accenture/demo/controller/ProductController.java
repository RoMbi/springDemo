package com.accenture.demo.controller;

import com.accenture.demo.controller.exception.ProductNotFoundException;
import com.accenture.demo.dao.ProductDao;
import com.accenture.demo.dto.ProductDto;
import com.accenture.demo.dto.ProductListDto;
import com.accenture.demo.entity.Product;
import com.accenture.demo.repository.ProductRepository;
import com.accenture.demo.service.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductListDto productListDto;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ProductListDto getAllProducts() {
        productListDto.setProducts(ProductMapper.map(productDao.getAll()));
        return productListDto;
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String getMessage(@RequestParam("id") Long id) {
        Product product = productDao.get(id).get();
        NumberFormat nf = NumberFormat.getInstance(new Locale("us", "US"));
        nf.setMinimumFractionDigits(2);

        return "<h1>Buy now <b>" + product.getName() +
                "</b> for only " + product.getPrice() + nf.getCurrency() + "!</h1>";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ProductDto getProduct(@PathVariable("id") Long id) {
        return ProductMapper.map(productDao.get(id).get());
    }

    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public Product save(@RequestBody Product product) {
        Product existingProduct = productDao.findByName(product.getName());
        if (existingProduct != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Product already exists", new EntityExistsException());
        }

        return productDao.save(product).get();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ProductDto productDto) {
        Product product = ProductMapper.convertToEntity(productDto);

        return productDao.update(product) ? "updated" : "can't update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productDao.delete(id) ? "deleted" : "something went wrong";
    }

    @GetMapping(path = "/list")
    Page<Product> loadProductsPage(
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {
        return productRepository.findAllPage(pageable);
    }

    @RequestMapping(value = "/price/range", method = RequestMethod.GET)
    Page<Product> loadPriceRangePage(
            @RequestParam("min") BigDecimal min,
            @RequestParam("max") BigDecimal max,
            @PageableDefault(page = 0, size = 5)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {
        return productRepository.findByPriceRange(pageable, min, max);
    }
}

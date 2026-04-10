package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.ProductReqDTO;
import com.example.distribution_sales_techfira.dto.ProductResDTO;
import com.example.distribution_sales_techfira.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController<ProductReqDTO,ProductResDTO,Integer> {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        super(productService);
        this.productService=productService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String,String>> uploadExcel(@RequestParam("file")MultipartFile file){
        Map<String,String> response=new HashMap<>();
        if(file.isEmpty()){
            response.put("message","please upload valid excel file");
            return ResponseEntity.badRequest().body(response);
        }
        try{
            productService.importProductFromExcel(file);
            response.put("message","Excel uploaded successfully");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("message","Error while uploading file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("get-all")
    public ResponseEntity<BaseResponse<CustomPageResponse<ProductResDTO>>>
    getAllProducts(@RequestParam(defaultValue="1") int page,
                   @RequestParam(defaultValue="10") int size,
                   @RequestParam(defaultValue = "createdAt") String sortField,
                   @RequestParam(defaultValue = "desc") String sortDir){
        int zeroBasedPage = Math.max(page - 1, 0);
        CustomPageResponse<ProductResDTO> result=productService.findAllPages(page,size,sortField,sortDir);
        result.setPage(page);

        return ResponseEntity.ok(new BaseResponse<>("success",HttpStatus.OK.value(), result));
    }
    @GetMapping("/productCode/{code}")
    public ResponseEntity<BaseResponse<ProductResDTO>> getByProductCode(@PathVariable Integer code) {
        ProductResDTO result=productService.getProductByCode(code);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }

    @GetMapping("/search")
    public  ResponseEntity<BaseResponse<CustomPageResponse<ProductResDTO>>> serchProduct(
            @RequestParam String keyword,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="5") int size
    ){
        CustomPageResponse<ProductResDTO> result=productService.serachProduct(keyword,page,size);
        return ResponseEntity.ok(new BaseResponse<>("success", HttpStatus.OK.value(), result));
    }
    @Override
    protected String getEntityName() {
        return "Product";
    }
}

package com.example.distribution_sales_techfira.controller;

import com.example.distribution_sales_techfira.config.BaseResponse;
import com.example.distribution_sales_techfira.dto.*;
import com.example.distribution_sales_techfira.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vendor")
public class VendorController extends BaseController<VendorReqDTO,VendorResDTO,Integer>{

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        super(vendorService);
        this.vendorService = vendorService;
    }
 

    @GetMapping
    public ResponseEntity<BaseResponse<CustomPageResponse<VendorResDTO>>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size ){
        int zeroBasedPage = Math.max(page -1,0);
        CustomPageResponse<VendorResDTO> result  = vendorService.findAllPaged(zeroBasedPage, size);

        return ResponseEntity.ok(new BaseResponse<>("success",HttpStatus.OK.value(),result));
    }

    @DeleteMapping("/delete-{id}")
    public ResponseEntity<BaseResponse> deleteById(@PathVariable Integer id){
        return ResponseEntity.ok(new BaseResponse("success",HttpStatus.OK.value(),vendorService.deleteVendor(id)));
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<Map<String, String>> uploadVendorExcel(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "Please upload a valid Excel file.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            vendorService.importVendorsFromExcel(file);
            response.put("message", "Vendors uploaded successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error while uploading file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/search")
    public ResponseEntity<BaseResponse<List<VendorResDTO>>> search(@RequestParam @RequestBody String text){
        List<VendorResDTO> result = vendorService.search(text);
        return ResponseEntity.ok(new BaseResponse<>("success",HttpStatus.OK.value(),result));
    }

    @GetMapping("/without-pagination")
    public ResponseEntity<BaseResponse<List<VendorResDTO>>> findAllWithoutPagination(){
        List<VendorResDTO> allWithoutPagination = vendorService.findAllWithoutPagination();
        return ResponseEntity.ok(new BaseResponse<>("success",HttpStatus.OK.value(),allWithoutPagination));
    }
    @Override
    protected String getEntityName() {
        return "vendor";
    }
}

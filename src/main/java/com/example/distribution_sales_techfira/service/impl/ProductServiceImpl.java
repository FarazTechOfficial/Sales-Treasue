package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.dto.CustomPageResponse;
import com.example.distribution_sales_techfira.dto.ProductReqDTO;
import com.example.distribution_sales_techfira.dto.ProductResDTO;
import com.example.distribution_sales_techfira.entity.Product;
import com.example.distribution_sales_techfira.exception.CustomException;
import com.example.distribution_sales_techfira.mapper.ProductMapper;
import com.example.distribution_sales_techfira.repository.ProductRepository;
import com.example.distribution_sales_techfira.service.ProductService;
import com.example.distribution_sales_techfira.util.AuditUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.distribution_sales_techfira.util.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final AuditUtil auditUtil;
    private final UserServiceImp userServiceImp;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, AuditUtil auditUtil, UserServiceImp userServiceImp) {
        this.repository=repository;
        this.mapper=mapper;
        this.auditUtil=auditUtil;
        this.userServiceImp=userServiceImp;
    }

    @Override
    public void importProductFromExcel(MultipartFile file) throws IOException {
        List<Product> productList=new ArrayList<>();
        Integer id=userServiceImp.getLoggedInUserId();
        try(Workbook workbook=new XSSFWorkbook(file.getInputStream())){
            Sheet sheet=workbook.getSheetAt(0);
            for(int i=1; i<=sheet.getLastRowNum(); i++){
                Row row=sheet.getRow(i);
                if (row == null || row.getCell(0) == null)continue;
                Product p =new Product();
                //Product Code
                Cell cell=row.getCell(0);
                int code;
                if (cell.getCellType() == CellType.NUMERIC) {
                    code = (int) cell.getNumericCellValue();
                } else {
                    code = (int) Double.parseDouble(getCellValue(cell));
                }
                p.setProductCode(code);
                //Product Description
                p.setProductDescription(getCellValue(row.getCell(1)));
                //Uint of Measure
                Cell uom=row.getCell(2);
                if(uom != null && uom.getCellType() == CellType.NUMERIC){
                    p.setUnitOfMeasurement((float)uom.getNumericCellValue());
                }else{
                    p.setUnitOfMeasurement(Float.parseFloat(getCellValue(uom)));
                }
                //Distributor Price
                Cell dp=row.getCell(3);
                if(dp != null && dp.getCellType() == CellType.NUMERIC){
                    p.setDistributorPrice((float)dp.getNumericCellValue());
                }else{
                    p.setDistributorPrice(Float.parseFloat(getCellValue(dp)));
                }
                //Trade Price
                Cell tp=row.getCell(4);
                if(tp != null && tp.getCellType() == CellType.NUMERIC){
                    p.setTradePrice((float) tp.getNumericCellValue());
                }else{
                    p.setTradePrice(Float.parseFloat(getCellValue(tp)));
                }
                //Maximum Retail Price
                Cell mrp=row.getCell(5);
                if(mrp != null && mrp.getCellType() == CellType.NUMERIC){
                    p.setMaximumRetailPrice((float) mrp.getNumericCellValue());
                }else{
                    p.setMaximumRetailPrice(Float.parseFloat(getCellValue(mrp)));
                }
                //Quantity in Pieces
                Cell quantityP=row.getCell(6);
                if(quantityP != null && quantityP.getCellType() == CellType.NUMERIC){
                    p.setQuantityOpInPieces((float) quantityP.getNumericCellValue());
                }else{
                    p.setQuantityOpInPieces(Float.parseFloat(getCellValue(quantityP)));
                }
                //Quantity in Carton
                Cell quantityC=row.getCell(7);
                if(quantityC != null && quantityC.getCellType() == CellType.NUMERIC){
                    p.setQuantityOpInCarton((float) quantityC.getNumericCellValue());
                }else{
                    p.setQuantityOpInCarton(Float.parseFloat(getCellValue(quantityC)));
                }

                p.setStatus(2);
                p.setCreatedBy(id);

                Optional<Product> existing=repository.findByProductCode(code);
                if(existing.isPresent()){
                    Product ex=existing.get();
                    ex.setCreatedBy(id);
                    ex.setProductDescription(p.getProductDescription());
                    ex.setUnitOfMeasurement(p.getUnitOfMeasurement());
                    ex.setDistributorPrice(p.getDistributorPrice());
                    ex.setTradePrice(p.getTradePrice());
                    ex.setMaximumRetailPrice(p.getMaximumRetailPrice());
                    ex.setQuantityOpInPieces(p.getQuantityOpInPieces());
                    ex.setQuantityOpInCarton(p.getQuantityOpInCarton());
                    //ex.setStatus(2);
                    repository.save(ex);
                }else{
                    productList.add(p);
                }
            }//end loop
        }//end try
        System.out.println("Uploading " + productList.size() + " products");
        repository.saveAll(productList);
    }//end upload product

    @Override
    public ProductResDTO save(ProductReqDTO requestDTO) {
        auditUtil.createAudit(requestDTO);
        if(repository.findByProductCode(requestDTO.getProductCode()).isPresent()){
            throw  new CustomException(PRODUCT_DUPLICATE);
        }
        Product product = mapper.toEntity(requestDTO);
        Product save=repository.save(product);
        return mapper.toDTO(save);
    }
    @Override
    public ProductResDTO update(Integer id, ProductReqDTO requestDTO) {
        auditUtil.updateAudit(requestDTO);
        Product product = repository.findById(id)
                .orElseThrow(() -> new CustomException("Product Id does not exists!"));

        if (requestDTO.getProductCode() !=null){
            product.setProductCode(requestDTO.getProductCode());
        }
        if (requestDTO.getProductDescription() !=null){
            product.setProductDescription(requestDTO.getProductDescription());
        }
        if (requestDTO.getUnitOfMeasurement() !=null){
            product.setUnitOfMeasurement(requestDTO.getUnitOfMeasurement());
        }
        if (requestDTO.getDistributorPrice() !=null){
            product.setDistributorPrice(requestDTO.getDistributorPrice());
        }
        if(requestDTO.getTradePrice() != null){
            product.setTradePrice(requestDTO.getTradePrice());
        }
        if(requestDTO.getMaximumRetailPrice() != null){
            product.setMaximumRetailPrice(requestDTO.getMaximumRetailPrice());
        }
        if(requestDTO.getQuantityOpInPieces() != null){
            product.setQuantityOpInPieces(requestDTO.getQuantityOpInPieces());
        }
        if(requestDTO.getQuantityOpInCarton() != null){
            product.setQuantityOpInCarton(requestDTO.getQuantityOpInCarton());
        }
        if (requestDTO.getUpdatedBy() != null) {
            product.setUpdatedBy(requestDTO.getUpdatedBy());
        }


        product.setStatus(2);
        Product saved = repository.save(product);
        return mapper.toDTO(saved);
    }

    private String getCellValue(Cell cell){
        if(cell==null)return "";
        return switch (cell.getCellType()){
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

public CustomPageResponse<ProductResDTO>
findAllPages(int page, int size,String sortField, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("asc") ?
            Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Product> result = repository.findByStatusNot(3, pageable);
    List<ProductResDTO> content = mapper.toDTOList(result.getContent());

    return new CustomPageResponse<>(
            content,
            result.getSort().isUnsorted(),
            result.getSort().isSorted(),
            result.getTotalElements(),
            size,
            page + 1
    );
}

    @Override
    public ProductResDTO findByID(Integer id) {
        Product product = repository.findById(id).orElseThrow(() -> new CustomException("Product id does not exists!"));
        return mapper.toDTO(product);
    }

    @Override
    public ProductResDTO getProductByCode(Integer code) {
        return repository.findByProductCode(code)
                .map(mapper::toDTO)
                .orElseThrow(() -> new CustomException("ProductCode does not exists"));
    }


    @Override   
    public void softDeleteById(Integer id) {
        Product product = repository.findById(id).orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND_BY_CODE));
        product.setStatus(3);
        repository.save(product);

    }
    @Override
    public void updateStatus(Integer id, Integer status) {
        Product product = repository.findById(id).orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND_BY_CODE));
        product.setStatus(status);
        repository.save(product);
    }
    @Override
    public CustomPageResponse<ProductResDTO> serachProduct(String keyword, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Product> resultPage=repository
                .searchProducts(keyword,pageable);
        List<ProductResDTO> dtoList=resultPage.getContent().stream()
                .map(mapper::toDTO)
                .toList();
        return new CustomPageResponse<>(
                dtoList,
                resultPage.getSort().isUnsorted(),
                resultPage.getSort().isSorted(),
                resultPage.getTotalElements(),
                resultPage.getSize(),
                resultPage.getTotalPages()
        );
    }
}
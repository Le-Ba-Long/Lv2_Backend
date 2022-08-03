package com.globits.da.rest;

import com.globits.da.common.ErrorMessage;
import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.ResponseRequest;
import com.globits.da.service.ProvinceService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/provinces")
public class RestProvinceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestProvinceController.class);
    @Autowired
    ProvinceService provinceService;
    @Autowired
    ModelMapper modelMapper;

    //    @GetMapping("/list")
//    public ResponseEntity<?> getAll() {
//        List<ProvinceDto> listProvince = provinceService.getAll();
//        if (listProvince.isEmpty()) {
//            return ResponseEntity
//                    .status(ErrorMessage.NOT_SUCCESS.getCode())
//                    .body(new ResponseRequest<>(
//                            ErrorMessage.LIST_IS_EMPTY.getCode(),
//                            ErrorMessage.LIST_IS_EMPTY.getMessage(),
//                            listProvince));
//
//        }
//        return ResponseEntity
//                .status(ErrorMessage.SUCCESS.getCode())
//                .body(new ResponseRequest<>(
//                        ErrorMessage.SUCCESS.getCode(),
//                        ErrorMessage.SUCCESS.getMessage(),
//                        listProvince));
//    }
    @GetMapping("/list")
    public ResponseRequest<List<ProvinceDto>> getAll() {
        List<ProvinceDto> listProvince = provinceService.getAll();
        if (listProvince.isEmpty()) {
            return new ResponseRequest<>(
                    ErrorMessage.LIST_IS_EMPTY.getCode(),
                    ErrorMessage.LIST_IS_EMPTY.getMessage(),
                    listProvince);
        }
        return new ResponseRequest<>(
                ErrorMessage.SUCCESS.getCode(),
                ErrorMessage.SUCCESS.getMessage(),
                listProvince);
    }

//    @PostMapping("/insert")
//    public ResponseEntity<?> insert(@RequestBody ProvinceDto provinceDto) {
//        String resultErrorMessage = provinceService.insert(provinceDto).getMessageError();
//        int resultErrorCode = provinceService.insert(provinceDto).getStatusCode();
//        if (resultErrorMessage.equals(ErrorMessage.SUCCESS.getMessage())) {
//            return ResponseEntity
//                    .status(ErrorMessage.SUCCESS.getCode())
//                    .body(new ResponseRequest<ProvinceDto>(
//                            ErrorMessage.SUCCESS.getCode(),
//                            ErrorMessage.SUCCESS.getMessage(),
//                            provinceDto));
//        }
//            return ResponseEntity
//                    .status(ErrorMessage.NOT_SUCCESS.getCode())
//                    .body(new ResponseRequest<ProvinceDto>(
//                            resultErrorCode,
//                            resultErrorMessage,
//                            provinceDto));
//
//    }

    @PostMapping("/insert")
    public ResponseRequest<ProvinceDto> insert(@RequestBody ProvinceDto provinceDto) {
        String resultErrorMessage = provinceService.insert(provinceDto).getMessageError();
        int resultErrorCode = provinceService.insert(provinceDto).getStatusCode();
        if (resultErrorMessage.equals(ErrorMessage.SUCCESS.getMessage())) {
            return new ResponseRequest<ProvinceDto>(
                    ErrorMessage.SUCCESS.getCode(),
                    ErrorMessage.SUCCESS.getMessage(),
                    provinceDto);
        }
        return new ResponseRequest<ProvinceDto>(
                resultErrorCode,
                resultErrorMessage,
                provinceDto);
    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody ProvinceDto provinceDto) {
//        String resultErrorMessage = provinceService.update(id, provinceDto).getMessageError();
//        int resultErrorCode = provinceService.update(id, provinceDto).getStatusCode();
//        if (resultErrorMessage.equals(ErrorMessage.SUCCESS.getMessage())) {
//            return ResponseEntity
//                    .status(ErrorMessage.SUCCESS.getCode())
//                    .body(new ResponseRequest<ProvinceDto>(
//                            ErrorMessage.SUCCESS.getCode(),
//                            ErrorMessage.SUCCESS.getMessage(),
//                            provinceDto));
//        }
//        return ResponseEntity
//                .status(ErrorMessage.NOT_SUCCESS.getCode())
//                .body(new ResponseRequest<ProvinceDto>(
//                        resultErrorCode,
//                        resultErrorMessage,
//                        provinceDto));
//    }

    @PutMapping("/update/{id}")
    public ResponseRequest<ProvinceDto> update(@PathVariable(name = "id") UUID id, @RequestBody ProvinceDto provinceDto) {
        String resultErrorMessage = provinceService.update(id, provinceDto).getMessageError();
        int resultErrorCode = provinceService.update(id, provinceDto).getStatusCode();
        if (resultErrorMessage.equals(ErrorMessage.SUCCESS.getMessage())) {
            return new ResponseRequest<ProvinceDto>(
                    ErrorMessage.SUCCESS.getCode(),
                    ErrorMessage.SUCCESS.getMessage(),
                    provinceDto);
        }
        return new ResponseRequest<ProvinceDto>(
                resultErrorCode,
                resultErrorMessage,
                provinceDto);
    }

    //    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
//        if (provinceService.deleteById(id)) {
//            Province province = provinceService.findById(id);
//            LOGGER.info("Delete Success");
//            return ResponseEntity
//                    .status(ErrorMessage.SUCCESS.getCode())
//                    .body(new ResponseRequest<>(
//                            ErrorMessage.SUCCESS.getCode(),
//                            ErrorMessage.SUCCESS.getMessage(), modelMapper.map(province, ProvinceDto.class)));
//        } else {
//            LOGGER.info("Delete Not Success");
//            return ResponseEntity
//                    .status(ErrorMessage.NOT_SUCCESS.getCode())
//                    .body(new ResponseRequest<>(ErrorMessage.ID_NOT_EXIST.getCode(),
//                            ErrorMessage.ID_NOT_EXIST.getMessage(),
//                            null));
//        }
//    }
    @DeleteMapping("/delete/{id}")
    public ResponseRequest<ProvinceDto> delete(@PathVariable("id") UUID id) {
        Province province = provinceService.findById(id);
        if (provinceService.deleteById(id)) {
            LOGGER.info("Delete Success");
            return new ResponseRequest<>(
                    ErrorMessage.SUCCESS.getCode(),
                    ErrorMessage.SUCCESS.getMessage(), modelMapper.map(province, ProvinceDto.class));
        } else {
            LOGGER.info("Delete Not Success");
            return new ResponseRequest<>(ErrorMessage.ID_NOT_EXIST.getCode(),
                    ErrorMessage.ID_NOT_EXIST.getMessage(),
                    null);
        }
    }
}

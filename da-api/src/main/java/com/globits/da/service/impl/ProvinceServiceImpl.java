package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.common.ErrorMessage;
import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.ResponseRequest;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.ProvinceService;
import com.globits.da.validate.ValidateProvince;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl extends GenericServiceImpl<Province, UUID> implements ProvinceService {
    @Autowired
    ProvinceRepository repository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ProvinceDto> getAll() {
        return repository.findAll()
                .stream()
                .map(province -> {
                    return modelMapper.map(province, ProvinceDto.class);
                }).collect(Collectors.toList());
    }

    @Override
    public ProvinceDto save(ProvinceDto provinceDto) {
        // provinceDto.setId(UUID.randomUUID());
        provinceDto.getId();
        return modelMapper
                .map(repository.save(modelMapper.map(provinceDto, Province.class)), ProvinceDto.class);
    }

    @Override
    public Boolean deleteById(UUID uuid) {
        if (repository.existsProvinceById(uuid)) {
            repository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public ResponseRequest<ProvinceDto> update(UUID id, ProvinceDto provinceDto) {
        if (repository.existsProvinceById(id)) {
            String resultErrorMessage = checkValidCodeAndName(provinceDto).getMessage();
            String updateSuccess = ErrorMessage.SUCCESS.getMessage();
            if (updateSuccess.equals(resultErrorMessage)) {
                return new ResponseRequest<>(
                        ErrorMessage.SUCCESS.getCode(),
                        ErrorMessage.SUCCESS.getMessage(),
                        modelMapper.map(repository.findById(id)
                                .map(province -> {
                                    province.setCode(provinceDto.getCode());
                                    province.setName(provinceDto.getName());
                                    return province;
                                }), ProvinceDto.class));
            } else {
                return new ResponseRequest<>(
                        ValidateProvince.resultStatusCode(checkValidCodeAndName(provinceDto)),
                        resultErrorMessage,
                        provinceDto);
            }
        }
        return new ResponseRequest<>(
                ErrorMessage.ID_NOT_EXIST.getCode(),
                ErrorMessage.ID_NOT_EXIST.getMessage(),
                provinceDto);
    }

    @Override
    public ResponseRequest<ProvinceDto> insert(ProvinceDto provinceDto) {
        String resultErrorMessage = checkValidProvince(provinceDto).getMessage();
        String insertSuccess = ErrorMessage.SUCCESS.getMessage();
        if (insertSuccess.equals(resultErrorMessage)) {
            provinceDto.setId(UUID.randomUUID());
            return new ResponseRequest<>(
                    ErrorMessage.SUCCESS.getCode(),
                    ErrorMessage.SUCCESS.getMessage(),
                    modelMapper.map(repository.save(modelMapper.map(provinceDto, Province.class)), ProvinceDto.class));
        }
        return new ResponseRequest<>(
                ValidateProvince.resultStatusCode(checkValidProvince(provinceDto)),
                resultErrorMessage,
                provinceDto);

    }

    @Override
    public Page<ProvinceDto> getPage(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Page<ProvinceDto> search(ProvinceSearchDto dto) {
        return null;
    }

    @Override
    public ProvinceDto getByID(UUID uuid) {
        return modelMapper.map(repository.findById(uuid), ProvinceDto.class);
    }

    @Override
    public Boolean checkCodeHasExist(String code) {
        return repository.findByCode(code) == null;

    }

    @Override
    public String resultErrorMessageId(UUID id) {
        if (repository.existsProvinceById(id))
            return ErrorMessage.ID_IS_EXIST.getMessage();
        return null;
    }

    //kiểm tra xem tỉnh có hợp lệ không ,không hợp lệ trả ra errormessage tương ứng không có trả về  ErrorMessage.SUCCESS
    public ErrorMessage checkValidProvince(ProvinceDto provinceDto) {
        if (ValidateProvince.checkCodeIsNull(provinceDto.getCode())) {
            return ErrorMessage.CODE_IS_NULL;
        } else if (repository.existsProvinceByCode(provinceDto.getCode())) {
            return ErrorMessage.CODE_IS_EXIST;
        } else if (ValidateProvince.checkNameIsNull(provinceDto.getName())) {
            return ErrorMessage.NAME_IS_NULL;
        } else if (repository.existsProvinceByName(provinceDto.getName())) {
            return ErrorMessage.NAME_IS_EXIST;
        }
        return ErrorMessage.SUCCESS;
    }

    public ErrorMessage checkValidCodeAndName(ProvinceDto provinceDto) {
        if (ValidateProvince.checkCodeIsNull(provinceDto.getCode())) {
            return ErrorMessage.CODE_IS_NULL;
        } else if (ValidateProvince.checkNameIsNull(provinceDto.getName())) {
            return ErrorMessage.NAME_IS_NULL;
        }
        return ErrorMessage.SUCCESS;
    }
}

package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.common.ErrorMessage;
import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.ResponseRequest;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.DistrictService;
import com.globits.da.validate.ValidateProvince;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl extends GenericServiceImpl<District, UUID> implements DistrictService {
    @Autowired
    DistrictRepository repository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<DistrictDto> getAll() {
        return repository.findAll()
                .stream()
                .map(district -> {
                    return modelMapper.map(district, DistrictDto.class);
                }).collect(Collectors.toList());
    }

    @Override
    public DistrictDto save(DistrictDto districtDto) {
        districtDto.getId();
        return modelMapper
                .map(repository.save(modelMapper.map(districtDto, District.class)), DistrictDto.class);
    }

    @Override
    public Boolean deleteById(UUID uuid) {
        if (repository.existsDistrictById(uuid)) {
            repository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public ResponseRequest<DistrictDto> update(UUID id, DistrictDto districtDto) {
        if (repository.existsDistrictById(id)) {
            String resultErrorMessage = checkValidCodeAndName(districtDto).getMessage();
            String updateSuccess = ErrorMessage.SUCCESS.getMessage();
            if (updateSuccess.equals(resultErrorMessage)) {
                return new ResponseRequest<>(
                        ErrorMessage.SUCCESS.getCode(),
                        ErrorMessage.SUCCESS.getMessage(),
                        modelMapper.map(repository.findById(id)
                                .map(district -> {
                                    district.setCode(districtDto.getCode());
                                    district.setName(districtDto.getName());
                                    return district;
                                }), DistrictDto.class));
            } else {
                return new ResponseRequest<>(
                        ValidateProvince.resultStatusCode(checkValidCodeAndName(districtDto)),
                        resultErrorMessage,
                        districtDto);
            }

        }
        return new ResponseRequest<>(
                ErrorMessage.ID_NOT_EXIST.getCode(),
                ErrorMessage.ID_NOT_EXIST.getMessage(),
                districtDto);
    }

    @Override
    public ResponseRequest<DistrictDto> insert(DistrictDto districtDto) {
        String resultErrorMessage = checkValidDistrict(districtDto).getMessage();
        String insertSuccess = ErrorMessage.SUCCESS.getMessage();
        if (insertSuccess.equals(resultErrorMessage)) {
            districtDto.setId(UUID.randomUUID());
            return new ResponseRequest<>(
                    ErrorMessage.SUCCESS.getCode(),
                    ErrorMessage.SUCCESS.getMessage(),
                    modelMapper.map(repository.save(modelMapper.map(districtDto, District.class)), DistrictDto.class));
        }
        return new ResponseRequest<>(
                ValidateProvince.resultStatusCode(checkValidDistrict(districtDto)),
                resultErrorMessage,
                districtDto);
    }

    @Override
    public Page<DistrictDto> getPage(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Page<DistrictDto> search(ProvinceSearchDto dto) {
        return null;
    }

    @Override
    public DistrictDto getByID(UUID uuid) {
        return modelMapper.map(repository.findById(uuid), DistrictDto.class);
    }

    @Override
    public Boolean checkCodeHasExist(String code) {
        return repository.findByCode(code) == null;
    }

    @Override
    public String resultErrorMessageId(UUID id) {
        if (repository.existsDistrictById(id))
            return ErrorMessage.ID_IS_EXIST.getMessage();
        return null;
    }

    //kiểm tra xem tỉnh có hợp lệ không ,không hợp lệ trả ra errormessage tương ứng không có trả về  ErrorMessage.SUCCESS
    public ErrorMessage checkValidDistrict(DistrictDto districtDto) {

        if (ValidateProvince.checkCodeIsNull(districtDto.getCode())) {
            return ErrorMessage.CODE_IS_NULL;
        } else if (repository.existsDistrictByCode(districtDto.getCode())) {
            return ErrorMessage.CODE_IS_EXIST;
        } else if (ValidateProvince.checkNameIsNull(districtDto.getName())) {
            return ErrorMessage.NAME_IS_NULL;
        } else if (repository.existsDistrictByName(districtDto.getName())) {
            return ErrorMessage.NAME_IS_EXIST;
        }

        return ErrorMessage.SUCCESS;
    }

    public ErrorMessage checkValidCodeAndName(DistrictDto districtDto) {

        if (ValidateProvince.checkCodeIsNull(districtDto.getCode())) {
            return ErrorMessage.CODE_IS_NULL;
        } else if (ValidateProvince.checkNameIsNull(districtDto.getName())) {
            return ErrorMessage.NAME_IS_NULL;
        }
        return ErrorMessage.SUCCESS;
    }
}

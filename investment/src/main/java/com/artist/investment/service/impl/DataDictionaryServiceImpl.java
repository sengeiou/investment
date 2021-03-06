package com.artist.investment.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.artist.investment.dao.DataDictionaryMapper;
import com.artist.investment.domain.dto.DataDictionaryDto;
import com.artist.investment.domain.dto.DataDictionaryValueDto;
import com.dili.uap.sdk.domain.DataDictionary;
import com.dili.uap.sdk.domain.DataDictionaryValue;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artist.investment.dao.DataDictionaryValueMapper;
import com.artist.investment.service.DataDictionaryService;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.dto.DTOUtils;

/**
 *
 * 10:41:18.
 */
@Service
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary, Long> implements DataDictionaryService {

	@Autowired
	private DataDictionaryValueMapper valueMapper;

	public DataDictionaryMapper getActualDao() {
		return (DataDictionaryMapper) getDao();
	}

	@Override
	public DataDictionaryDto findByCode(String code) {
		DataDictionary record = DTOUtils.newDTO(DataDictionary.class);
		record.setCode(code);
		DataDictionary model = this.getActualDao().selectOne(record);
		if (model == null) {
			return null;
		}
		DataDictionaryValue valueRecord = DTOUtils.newDTO(DataDictionaryValue.class);
		valueRecord.setDdCode(model.getCode());
		List<DataDictionaryValue> values = this.valueMapper.select(valueRecord);
		DataDictionaryDto dto = DTOUtils.cast(model, DataDictionaryDto.class);
		if (CollectionUtils.isNotEmpty(values)) {
			List<DataDictionaryValueDto> dtos = new ArrayList<>(values.size());
			for (DataDictionaryValue value : values) {
				DataDictionaryValueDto valueDto = DTOUtils.cast(value, DataDictionaryValueDto.class);
				dtos.add(valueDto);
			}
			dto.setValues(dtos);
		}
		return dto;
	}

	@Override
	public int insert(DataDictionary t) {
		return super.insert(t);
	}

	@Override
	public int insertSelective(DataDictionary t) {
		return super.insertSelective(t);
	}

}
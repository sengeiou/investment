package com.artist.sysadmin.service.impl;

import com.artist.sysadmin.dao.UserDataAuthMapper;
import com.artist.sysadmin.domain.UserDataAuth;
import com.artist.sysadmin.service.UserDataAuthService;
import com.dili.ss.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成 This file was generated on 2017-07-04 15:24:51.
 */
@Service
public class UserDataAuthServiceImpl extends BaseServiceImpl<UserDataAuth, Long> implements UserDataAuthService {

//	private final static Logger LOG = LoggerFactory.getLogger(UserDataAuthServiceImpl.class);

	public UserDataAuthMapper getActualDao() {
		return (UserDataAuthMapper) getDao();
	}


	@Override
	public int delete(UserDataAuth userDataAuth) {
		return getActualDao().delete(userDataAuth);
	}
}
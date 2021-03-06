package com.artist.sysadmin.service.impl;

import com.artist.sysadmin.dao.ResourceMapper;
import com.artist.sysadmin.dao.RoleMenuMapper;
import com.artist.sysadmin.domain.Resource;
import com.artist.sysadmin.exception.MenuException;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.artist.sysadmin.dao.MenuMapper;
import com.artist.sysadmin.domain.Menu;
import com.artist.sysadmin.domain.RoleMenu;
import com.artist.sysadmin.domain.dto.MenuCondition;
import com.artist.sysadmin.domain.dto.MenuListDto;
import com.artist.sysadmin.domain.dto.UpdateMenuDto;
import com.artist.sysadmin.manager.UserManager;
import com.artist.sysadmin.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 由MyBatis Generator工具自动生成 This file was generated on 2017-07-04 15:24:51.
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements MenuService {

	private static final int THREAD_POOL_SIZE = 10;

	@Autowired
	private MenuMapper menuDao;
	@Autowired
	private ResourceMapper resourceDao;
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private UserManager userManager;

	public MenuMapper getActualDao() {
		return (MenuMapper) getDao();
	}

	@Override
	public List<Menu> findUserMenu(String userId) throws MenuException {
		if (StringUtils.isBlank(userId)) {
			throw new MenuException("请先登录");
		}
		List<Menu> menus = this.menuDao.findByUserId(Long.valueOf(userId));
		Iterator<Menu> it = menus.iterator();
		while (it.hasNext()) {
			Menu menu = it.next();
			if (menu.getType().equals(2)) {
				it.remove();
			}
		}
		return menus;
	}

	@Override
	public List<MenuListDto> listContainAndParseResource() {
		List<Menu> menus = this.menuDao.selectAll();
		List<MenuListDto> targetList = new ArrayList<>(menus.size());
		for (Menu menu : menus) {
			MenuListDto vo = new MenuListDto();
			vo.setId(menu.getId());
			vo.setName(menu.getName());
			vo.setParentId(menu.getParentId());
			vo.addAttribute("type", menu.getType());
			targetList.add(vo);
		}
		return targetList;
	}

	@Override
	public BaseOutput<Object> update(UpdateMenuDto dto) {
		Menu menu = this.menuDao.selectByPrimaryKey(dto.getId());
		if (menu == null) {
			return BaseOutput.failure("菜单不存在");
		}
		String url = menu.getMenuUrl();
		menu.setName(dto.getName());
		menu.setMenuUrl(dto.getMenuUrl());
		menu.setType(dto.getType());
		menu.setDescription(dto.getDescription());
		menu.setOrderNumber(dto.getOrderNumber());
		menu.setModified(new Date());
		int result = this.menuDao.updateByPrimaryKey(menu);
		if (result <= 0) {
			return BaseOutput.failure("更新菜单失败");
		}
		if (!dto.getMenuUrl().equals(url)) {
			this.userManager.reloadUserUrlsByMenu(dto.getId());
		}
		return BaseOutput.success("修改成功");
	}

	@Override
	public BaseOutput<Object> deleteCheckIsBinding(Long menuId) {
		Menu menu = this.menuDao.selectByPrimaryKey(menuId);
		if (menu == null) {
			return BaseOutput.failure("菜单不存在");
		}
		Menu menuQuery = new Menu();
		menuQuery.setParentId(menu.getId());
		int count = this.menuDao.selectCount(menuQuery);
		if (count > 0) {
			return BaseOutput.failure("该菜单下包含子菜单，不能删除");
		}
		Resource resourceQuery = new Resource();
		resourceQuery.setMenuId(menuId);
		count = this.resourceDao.selectCount(resourceQuery);
		if (count > 0) {
			return BaseOutput.failure("该菜单下包含资源权限，不能删除");
		}
		RoleMenu record = new RoleMenu();
		record.setMenuId(menuId);
		count = this.roleMenuMapper.selectCount(record);
		if (count > 0) {
			return BaseOutput.failure("该菜单下绑定有角色，不能删除");
		}
		count = this.menuDao.deleteByPrimaryKey(menuId);
		if (count <= 0) {
			return BaseOutput.failure("删除失败");
		}
		this.userManager.reloadUserUrlsByMenu(menuId);
		return BaseOutput.success("删除成功");
	}

	@Override
	public List<Menu> getParentMenus(String id) {
		String parentIds = getActualDao().getParentMenus(id);
		if(StringUtils.isBlank(parentIds)){
			return null;
		}
		String[] parentIdArr = parentIds.split(",");
		MenuCondition menuCondition = new MenuCondition();
		//递归查出来的父id需要反转
		List ids = Arrays.asList(parentIdArr);
		Collections.reverse(ids);
		menuCondition.setIds(ids);
		//然而in查询无法按in的顺序获得结果，还是要根据ids的顺序重排
		List<Menu> menus = listByExample(menuCondition);
		List<Menu> sortedMenus = new ArrayList<>(menus.size());
		for(int i=0; i<ids.size(); i++){
			for(Menu menu : menus){
				if(ids.get(i).equals(menu.getId().toString())){
					sortedMenus.add(menu);
					break;
				}
			}
		}
		return sortedMenus;
	}

	@Override
	public List<Menu> getParentMenusByUrl(String url){
		Menu menu = new Menu();
		menu.setMenuUrl(url);
		List<Menu> menus = getActualDao().select(menu);
		if(menus == null || menus.isEmpty()){
			return null;
		}
		return getParentMenus(menus.get(0).getId().toString());
	}

}
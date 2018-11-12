package com.monkeyzi.oauth.service.impl;

import com.google.common.collect.Lists;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.Department;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.entity.domain.UserDepartment;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.dept.DeptDto;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;
import com.monkeyzi.oauth.enums.CommStatuEnum;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.service.DepartMentService;
import com.monkeyzi.oauth.service.UserDeptService;
import com.monkeyzi.oauth.utils.PublicUtil;
import com.monkeyzi.oauth.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartMentService {

    @Autowired
    private UserDeptService userDeptService;
    @Override
    public int addDept(DeptDto deptDto, LoginAuthDto loginAuthUser) {
        //部门编码不能相同
        Department department=new Department();
        department.setDeptCode(deptDto.getDeptCode());
        Department dept=selectOne(department);
        if (PublicUtil.isNotEmpty(dept)){
            throw new BusinessException(ErrorCodeEnum.DS202);
        }
        ModelMapper modelMapper=new ModelMapper();
        Department deptMent=modelMapper.map(deptDto,Department.class);
        deptMent.setUpdateInfo(loginAuthUser);
        deptMent.setId(generateId());
        int result=saveSelective(deptMent);
        return result;
    }

    @Override
    public int editDept(DeptDto deptDto, LoginAuthDto loginAuthUser) {
        if (Objects.equals(deptDto.getId(),deptDto.getParentId())){
            throw new BusinessException(ErrorCodeEnum.DS204);
        }
        //部门编码不能重复
        Department department=new Department();
        department.setDeptCode(deptDto.getDeptCode());
        Department dept=selectOne(department);
        if (PublicUtil.isNotEmpty(dept)){
            throw new BusinessException(ErrorCodeEnum.DS202);
        }
        ModelMapper modelMapper=new ModelMapper();
        Department deptMent=modelMapper.map(deptDto,Department.class);
        deptMent.setUpdateInfo(loginAuthUser);
        int result=updateByPrimaryKey(deptMent);
        return result;
    }

    @Override
    public void deleteDept(List<String> ids) {
       ids.forEach(a->{
           UserDepartment us=new UserDepartment();
           us.setDeptId(a);
           List<UserDepartment> usList=userDeptService.select(us);
           if (PublicUtil.isNotEmpty(usList)){
               throw new BusinessException(ErrorCodeEnum.DS205);
           }
       });
       ids.forEach(a->{
           deleteByPrimaryKey(a);
       });
    }

    @Override
    public List<TreeDto> queryDept() {
        Department dt=new Department();
        dt.setStatus(CommStatuEnum.DEPT_STATUS_RUN.code);
        List<Department> departmentList=select(dt);
        List<TreeDto> treeDtoList=Lists.newArrayList();
        departmentList.forEach(a-> {
            TreeDto dto=new TreeDto();
            dto.setId(a.getId());
            dto.setParentId(a.getParentId());
            dto.setCreateBy(a.getCreateBy());
            dto.setName(a.getDeptName());
            treeDtoList.add(dto);
        });
        return TreeUtils.getTree(treeDtoList,GlobalConstant.Sys.SYS_DEPT_ID);
    }



    @Override
    public Department queryDeptDetail(String id) {
        Department department=selectByKey(id);
        return department;
    }
}

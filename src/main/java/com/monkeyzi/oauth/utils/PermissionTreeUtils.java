package com.monkeyzi.oauth.utils;

import com.google.common.collect.Lists;
import com.monkeyzi.oauth.entity.dto.permission.PermissionDto;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 菜单权限树形工具类
 */
public class PermissionTreeUtils {

    /**
     * 获取递归树
     * @param list
     * @param parentId
     * @return
     */
    public static List<PermissionDto>  getTree(List<PermissionDto> list,String parentId){

        List<PermissionDto> permissionDtoList= Lists.newArrayList();
        for (PermissionDto dto:list){
            if (StringUtils.isBlank(dto.getParentId())){
                continue;
            }
            if (Objects.equals(dto.getParentId(),parentId)){
                //递归子节点
                recursionFn(list,dto);
                permissionDtoList.add(dto);
            }
        }
        return permissionDtoList;
    }

    /**
     * 递归列表
     * @param list
     * @param dto
     */
    public static  void recursionFn(List<PermissionDto> list,PermissionDto dto){
        List<PermissionDto> childList=getChildList(list,dto);
        dto.setChildren(childList);
        if (PublicUtil.isNotEmpty(childList)){
            dto.setHasChild(true);
        }
        for (PermissionDto child:childList){
            if (hasChild(list,child)){
                for (PermissionDto ch:childList){
                    recursionFn(list,ch);
                }
                dto.setHasChild(true);
            }
        }
    }

    /**
     * 获取子节点列表
     * @param list
     * @param dto
     * @return
     */
    public static List<PermissionDto> getChildList(List<PermissionDto> list,PermissionDto dto){
         List<PermissionDto> permissionDtoList=Lists.newArrayList();
         for (PermissionDto permissionDto:list){
             if (PublicUtil.isEmpty(permissionDto.getParentId())){
                 continue;
             }
             if (Objects.equals(permissionDto.getParentId(),dto.getId())){
                 permissionDtoList.add(permissionDto);
             }
         }
         return permissionDtoList;
    }


    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<PermissionDto> list,PermissionDto dto) {
        return !getChildList(list, dto).isEmpty();
    }

}

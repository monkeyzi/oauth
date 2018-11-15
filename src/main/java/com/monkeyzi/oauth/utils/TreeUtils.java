package com.monkeyzi.oauth.utils;

import com.google.common.collect.Lists;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 树形工具类
 */
public class TreeUtils {
    /**
     * 获取树
     * @param list
     * @param parentId
     * @return
     */
    public static List<TreeDto> getTree(List<TreeDto> list, String parentId){

        List<TreeDto> dtoList= Lists.newArrayList();
        for (TreeDto dto:list){
            if (StringUtils.isBlank(dto.getParentId())){
                continue;
            }
            if (Objects.equals(dto.getParentId(),parentId)){
                //递归子节点
                recursionFn(list,dto);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }


    public static  void recursionFn(List<TreeDto> list,TreeDto dto){
        List<TreeDto> childList=getChildList(list,dto);
        dto.setChildren(childList);
        if (PublicUtil.isNotEmpty(childList)){
            dto.setHasChild(true);
        }
        for (TreeDto child:childList){
            if (hasChild(list,child)){
                for (TreeDto ch:childList){
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
    public static List<TreeDto> getChildList(List<TreeDto> list,TreeDto dto){
        List<TreeDto> dtoList=Lists.newArrayList();
        for (TreeDto ch:list){
            if (PublicUtil.isEmpty(ch.getParentId())){
                continue;
            }
            if (Objects.equals(ch.getParentId(),dto.getId())){
                dtoList.add(ch);
            }
        }
        return dtoList;
    }


    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<TreeDto> list,TreeDto dto) {
        return !getChildList(list, dto).isEmpty();
    }
}

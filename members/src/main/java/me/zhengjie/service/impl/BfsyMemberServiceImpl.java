/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.service.impl;

import me.zhengjie.domain.BfsyMember;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.BfsyMemberRepository;
import me.zhengjie.service.BfsyMemberService;
import me.zhengjie.service.dto.BfsyMemberDto;
import me.zhengjie.service.dto.BfsyMemberQueryCriteria;
import me.zhengjie.service.mapstruct.BfsyMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author weiyien
* @date 2021-03-13
**/
@Service
@RequiredArgsConstructor
public class BfsyMemberServiceImpl implements BfsyMemberService {

    private final BfsyMemberRepository bfsyMemberRepository;
    private final BfsyMemberMapper bfsyMemberMapper;

    @Override
    public Map<String,Object> queryAll(BfsyMemberQueryCriteria criteria, Pageable pageable){
        Page<BfsyMember> page = bfsyMemberRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bfsyMemberMapper::toDto));
    }

    @Override
    public List<BfsyMemberDto> queryAll(BfsyMemberQueryCriteria criteria){
        return bfsyMemberMapper.toDto(bfsyMemberRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BfsyMemberDto findById(Long userId) {
        BfsyMember bfsyMember = bfsyMemberRepository.findById(userId).orElseGet(BfsyMember::new);
        ValidationUtil.isNull(bfsyMember.getUserId(),"BfsyMember","userId",userId);
        return bfsyMemberMapper.toDto(bfsyMember);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BfsyMemberDto create(BfsyMember resources) {
        if(bfsyMemberRepository.findByUsername(resources.getUsername()) != null){
            throw new EntityExistException(BfsyMember.class,"username",resources.getUsername());
        }
        if(bfsyMemberRepository.findByEmail(resources.getEmail()) != null){
            throw new EntityExistException(BfsyMember.class,"email",resources.getEmail());
        }
        if(bfsyMemberRepository.findByPhone(resources.getPhone()) != null){
            throw new EntityExistException(BfsyMember.class,"phone",resources.getPhone());
        }
        return bfsyMemberMapper.toDto(bfsyMemberRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BfsyMember resources) {
        BfsyMember bfsyMember = bfsyMemberRepository.findById(resources.getUserId()).orElseGet(BfsyMember::new);
        ValidationUtil.isNull( bfsyMember.getUserId(),"BfsyMember","id",resources.getUserId());
        BfsyMember bfsyMember1 = null;
        bfsyMember1 = bfsyMemberRepository.findByUsername(resources.getUsername());
        if(bfsyMember1 != null && !bfsyMember1.getUserId().equals(bfsyMember.getUserId())){
            throw new EntityExistException(BfsyMember.class,"username",resources.getUsername());
        }
        bfsyMember1 = bfsyMemberRepository.findByEmail(resources.getEmail());
        if(bfsyMember1 != null && !bfsyMember1.getUserId().equals(bfsyMember.getUserId())){
            throw new EntityExistException(BfsyMember.class,"email",resources.getEmail());
        }
        bfsyMember.copy(resources);
        bfsyMemberRepository.save(bfsyMember);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long userId : ids) {
            bfsyMemberRepository.deleteById(userId);
        }
    }

    @Override
    public void download(List<BfsyMemberDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BfsyMemberDto bfsyMember : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户名", bfsyMember.getUsername());
            map.put("性别", bfsyMember.getGender());
            map.put("手机号码", bfsyMember.getPhone());
            map.put("邮箱", bfsyMember.getEmail());
            map.put("密码", bfsyMember.getPassword());
            map.put("状态：1启用、0禁用", bfsyMember.getEnabled());
            map.put("创建者", bfsyMember.getCreateBy());
            map.put("更新者", bfsyMember.getUpdateBy());
            map.put("创建日期", bfsyMember.getCreateTime());
            map.put("更新时间", bfsyMember.getUpdateTime());
            map.put("生日", bfsyMember.getDate());
            map.put("积分", bfsyMember.getScore());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
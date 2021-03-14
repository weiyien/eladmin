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
package me.zhengjie.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.domain.BfsyMember;
import me.zhengjie.service.BfsyMemberService;
import me.zhengjie.service.dto.BfsyMemberQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author weiyien
* @date 2021-03-13
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "members管理")
@RequestMapping("api/bfsyMember")
public class BfsyMemberController {

    private final BfsyMemberService bfsyMemberService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bfsyMember:list')")
    public void download(HttpServletResponse response, BfsyMemberQueryCriteria criteria) throws IOException {
        bfsyMemberService.download(bfsyMemberService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询会员")
    @ApiOperation("查询会员")
    @PreAuthorize("@el.check('bfsyMember:list')")
    public ResponseEntity<Object> query(BfsyMemberQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bfsyMemberService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员")
    @ApiOperation("新增会员")
    @PreAuthorize("@el.check('bfsyMember:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BfsyMember resources){
        return new ResponseEntity<>(bfsyMemberService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员")
    @ApiOperation("修改会员")
    @PreAuthorize("@el.check('bfsyMember:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BfsyMember resources){
        bfsyMemberService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员")
    @ApiOperation("删除会员")
    @PreAuthorize("@el.check('bfsyMember:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        bfsyMemberService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
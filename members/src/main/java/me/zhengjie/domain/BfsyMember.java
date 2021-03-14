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
package me.zhengjie.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author weiyien
* @date 2021-03-13
**/
@Entity
@Data
@Getter
@Setter
@Table(name="bfsy_member")
public class BfsyMember extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @ApiModelProperty(value = "ID")
    private Long userId;

    @Column(name = "username",unique = true)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "gender",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "性别")
    private String gender;

    @Column(name = "phone",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @Column(name = "email",unique = true)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "enabled")
    @ApiModelProperty(value = "状态：1启用、0禁用")
    private Boolean enabled;

    @Column(name = "date")
    @ApiModelProperty(value = "生日")
    private Timestamp date;

    @Column(name = "score")
    @ApiModelProperty(value = "积分")
    private Long score;

    public void copy(BfsyMember source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
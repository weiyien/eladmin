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
package me.zhengjie.service.dto;

import lombok.Data;
import me.zhengjie.base.BaseDTO;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author weiyien
* @date 2021-03-13
**/
@Data
public class BfsyMemberDto extends BaseDTO implements Serializable {

    /** ID */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 性别 */
    private String gender;

    /** 手机号码 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 密码 */
    private String password;

    /** 状态：1启用、0禁用 */
    private Boolean enabled;

    /** 生日 */
    private Timestamp date;

    /** 积分 */
    private Long score;
}
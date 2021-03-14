<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">用户名</label>
        <el-input v-model="query.username" clearable placeholder="用户名" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">性别</label>
        <el-input v-model="query.gender" clearable placeholder="性别" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">手机号码</label>
        <el-input v-model="query.phone" clearable placeholder="手机号码" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">邮箱</label>
        <el-input v-model="query.email" clearable placeholder="邮箱" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">状态：1启用、0禁用</label>
        <el-input v-model="query.enabled" clearable placeholder="状态：1启用、0禁用" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">生日</label>
        <el-input v-model="query.date" clearable placeholder="生日" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="ID">
            <el-input v-model="form.userId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="form.username" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="性别" prop="gender">
                未设置字典，请手动设置 Radio
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="form.phone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.email" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="状态：1启用、0禁用">
                未设置字典，请手动设置 Radio
          </el-form-item>
          <el-form-item label="创建者">
            <el-input v-model="form.createBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新者">
            <el-input v-model="form.updateBy" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建日期">
            <el-input v-model="form.createTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间">
            <el-input v-model="form.updateTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker v-model="form.date" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="积分">
            <el-input v-model="form.score" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="userId" label="ID" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="phone" label="手机号码" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="password" label="密码" />
        <el-table-column prop="enabled" label="状态：1启用、0禁用" />
        <el-table-column prop="createBy" label="创建者" />
        <el-table-column prop="updateBy" label="更新者" />
        <el-table-column prop="createTime" label="创建日期" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column prop="date" label="生日" />
        <el-table-column prop="score" label="积分" />
        <el-table-column v-if="checkPer(['admin','bfsyMember:edit','bfsyMember:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudBfsyMember from '@/api/bfsyMember'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { userId: null, username: null, gender: null, phone: null, email: null, password: null, enabled: null, createBy: null, updateBy: null, createTime: null, updateTime: null, date: null, score: null }
export default {
  name: 'BfsyMember',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'members', url: 'api/bfsyMember', idField: 'userId', sort: 'userId,desc', crudMethod: { ...crudBfsyMember }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'bfsyMember:add'],
        edit: ['admin', 'bfsyMember:edit'],
        del: ['admin', 'bfsyMember:del']
      },
      rules: {
        gender: [
          { required: true, message: '性别不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'username', display_name: '用户名' },
        { key: 'gender', display_name: '性别' },
        { key: 'phone', display_name: '手机号码' },
        { key: 'email', display_name: '邮箱' },
        { key: 'enabled', display_name: '状态：1启用、0禁用' },
        { key: 'date', display_name: '生日' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>

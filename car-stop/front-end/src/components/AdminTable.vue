<template>
  <div id="right-table">

    <!--新增员工对话框-->
    <el-button plain @click="addAdminDialog = true" color="#626aef">
      新增员工信息
    </el-button>
    <el-dialog v-model="addAdminDialog" title="新增员工信息" width="500">
      <el-form :model="adminForm">
        <el-form-item label="账号" :label-width="formLabelWidth">
          <el-input v-model="adminForm.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="adminForm.password" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addAdminDialog = false">取消</el-button>
          <el-button color="#626aef"  @click="addAdminReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>


<!--修改管理员信息对话框-->
    <el-button plain @click="updateAdminDialog = true" color="#626aef">
      修改员工信息
    </el-button>
    <el-dialog v-model="updateAdminDialog" title="修改员工信息" width="500">
      <el-form :model="updateAdminForm">
        <el-form-item label="ID" :label-width="formLabelWidth">
          <el-input v-model="updateAdminForm.id" autocomplete="off" />
        </el-form-item>
        <el-form-item label="账号" :label-width="formLabelWidth">
          <el-input v-model="updateAdminForm.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="updateAdminForm.password" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="updateAdminDialog = false">取消</el-button>
          <el-button color="#626aef" @click="updateAdminReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!--表格-->
    <el-table :data="adminList" style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="账号" prop="username" />
      <el-table-column label="密码" prop="password" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
              @click="delAdminReq(scope.row)"
              color="#626aef"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import ax from "../http/axios_utils.js";
import {ElMessage} from "element-plus";

//新增用户弹出框设置
const addAdminDialog = ref(false)
const formLabelWidth = '140px'

//新增用户弹出框表单
const adminForm = reactive({
  username: '',
  password: '',
})

//修改用户弹出框设置
const updateAdminDialog = ref(false)
//修改用户表单
const updateAdminForm = reactive({
  id: '',
  username: '',
  password: '',
})

const adminList = ref()

//查询所有管理员
const queryAndLoadAdminList = () => {
  ax.get("/admin/list")
      .then(function (success) { //success 是自定义响应的参数名
        adminList.value = success.data.data
      })
}

//新增用户请求
const addAdminReq = () => {
  addAdminDialog.value = false
  ax.post('/admin/reg', adminForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '员工新增成功！',
        type: 'success',
      })
    }
    queryAndLoadAdminList()
  })
}

//删除用户请求
const delAdminReq = (admin) => {
  ax.get(`/admin/del/${admin.id}`).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '员工删除成功！',
        type: 'success',
      })
    }
    queryAndLoadAdminList()
  })
}

//修改用户请求
const updateAdminReq = () => {
  updateAdminDialog.value = false
  ax.post('/admin/update', updateAdminForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '员工信息修改成功！',
        type: 'success',
      })
    }
    queryAndLoadAdminList()
  })
}

onMounted(() => {
  //查询所有管理员
  queryAndLoadAdminList()
})
</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
<template>
  <div id="right-table">

    <!--对话框-->
    <el-button plain @click="dialogFormVisible = true">
      新增管理员信息
    </el-button>
    <el-dialog v-model="dialogFormVisible" title="新增管理员信息" width="500">
      <el-form :model="adminForm">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="adminForm.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="adminForm.password" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="addAdminReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!--表格-->
    <el-table :data="adminList" style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="密码" prop="password" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
              @click="delAdminReq(scope.row)"
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
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'

//新增用户弹出框表单
const adminForm = reactive({
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
  dialogFormVisible.value = false
  ax.post('/admin/reg', adminForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '管理员新增成功！',
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
        message: '管理员删除成功！',
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
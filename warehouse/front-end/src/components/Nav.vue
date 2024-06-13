<template>
  <div id="nav">
    <!--登录对话框-->
    <el-dialog v-model="addAdminDialog" title="登录" width="500">
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
          <el-button @click="addAdminDialog = false">取消</el-button>
          <el-button type="primary" @click="loginReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo menu"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
    >
      <el-menu-item index="0">
        <img
            style="width: 100px"
            src="../image/logo.png"
            alt="Element logo"
        />
      </el-menu-item>
      <div class="flex-grow" />

      <el-menu-item index="1" v-if="adminStore.name == null" @click="addAdminDialog = true">登录</el-menu-item>
      <el-menu-item index="1" v-if="adminStore.name != null">欢迎回来~ {{ adminStore.name }}</el-menu-item>
      <el-menu-item index="2" v-if="adminStore.name != null">退出登录</el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>

import ax from "../http/axios_utils.js";
import {ElMessage} from "element-plus";
import {adminStore} from "../stores/admin.js";

//登录弹出框设置
const addAdminDialog = ref(false)
const formLabelWidth = '140px'

//登录弹出框表单
const adminForm = reactive({
  username: '',
  password: '',
})

const loginReq = () => {
  addAdminDialog.value = true
  ax.post('/admin/login', adminForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '登录成功！',
        type: 'success',
      })
     adminStore.name = success.data.data.name
    }
  })
}

</script>

<style lang="less" scoped>
.flex-grow {
  flex-grow: 1;
}

#nav{
  width: 100%;
  .menu {
    height: 80px;
  }
}


</style>
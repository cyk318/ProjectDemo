<template>
  <div id="right-table">
    <el-table :data="adminData" style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="密码" prop="password" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
              @click=""
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

const adminData = ref()
onMounted(() => {
  //查询所有管理员
  ax.get("/admin/list")
      .then(function (success) { //success 是自定义响应的参数名
        adminData.value = success.data.data
      })
})

</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
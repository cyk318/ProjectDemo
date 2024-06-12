<template>
  <div id="right-table">
    <el-table v-loading="loading" :data="warehouseTable" style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="地址" prop="address" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
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

//加载中...
const loading = ref()
onBeforeMount(() => {
  loading.value = true
})

//获取所有仓库信息
const warehouseTable = ref()
onMounted(() => {
  ax.get('/warehouse/list').then((success) => {
    warehouseTable.value = success.data.data
    loading.value = false
  })
})

</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
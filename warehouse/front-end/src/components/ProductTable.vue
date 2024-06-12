<template>
  <div id="right-table">
    <el-table :data="productData" style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="库存 ID" prop="warehouseId" />
      <el-table-column label="产品名称" prop="name" />
      <el-table-column label="产品描述" prop="description" />
      <el-table-column label="价格" prop="price" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
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

//获取所有产品信息
const productData = ref()
onMounted(() => {
  ax.get('/product/list').then((success) => {
    productData.value = success.data.data
  })
})

</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
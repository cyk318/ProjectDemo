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
const productData = [
  {
    id: '111',
    warehouseId: 'aaa',
    name: '蔚来',
    description: '蔚来汽车，你值得拥有',
    price: 100.1
  },
  {
    id: '222',
    warehouseId: 'bbb',
    name: 'BMW',
    description: '宝马！！！',
    price: 199.1
  }
]

</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
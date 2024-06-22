<template>
  <div id="product-table">
    <h1 class="title">商城列表</h1>
    <el-table
        ref="multipleTableRef"
        :data="tableData"
        style="width: 800px"
        @selection-change="handleChooseChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="Date" width="120">
        <template #default="scope">{{ scope.row.date }}</template>
      </el-table-column>
      <el-table-column property="name" label="Name" width="120" />
      <el-table-column property="address" label="Address" />
    </el-table>
  </div>
</template>

<script setup>
//选中的商品
const chooses = ref()
const handleChooseChange = (choose) => {
  chooses.value = choose
}

//所有商品
const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-08',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-06',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-07',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]

</script>

<style lang="less" scoped>
#product-table {
  width: 800px;
  margin: 0 auto;
}

.title {
  width: 300px;
  height: 100px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
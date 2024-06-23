<template>
  <div>
    <h1 class="title">订单列表</h1>

    <!--列表-->
    <el-table
        ref="multipleTableRef"
        :data="orderList"
        style="width: 800px; margin: 0 auto"
        @selection-change="handleChooseChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column property="storeVo.id" label="商家ID" />
      <el-table-column property="storeVo.name" label="商家名称" />
      <el-table-column property="storeVo.avatar" label="商家头像" />
      <el-table-column property="infoVo.id" label="商品ID" />
      <el-table-column property="infoVo.title" label="商品标题" />
      <el-table-column property="infoVo.description" label="商品描述" />
      <el-table-column property="infoVo.price" label="商品价格" />
      <el-table-column property="infoVo.count" label="库存数量" />
      <el-table-column label="发布日期" width="120">
        <template #default="scope">{{ scope.row.infoVo.ctime }}</template>
      </el-table-column>
    </el-table>

    <div class="act">
      <h3>合计: {{totalPrice}}</h3>
      <el-button class="act-btn" type="primary">提交订单</el-button>
    </div>
  </div>
</template>

<script setup>
const chooses = ref()
const handleChooseChange = (choose) => {
  chooses.value = choose
}
//所有商品
const orderList = ref()
//总价格
const totalPrice = ref(0)

</script>

<style lang="less" scoped>

.title {
  width: 300px;
  height: 60px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.act {
  width: 300px;
  height: 50px;
  margin: 0 auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
</style>
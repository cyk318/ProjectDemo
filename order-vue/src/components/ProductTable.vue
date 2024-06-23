<template>
  <div id="product-table">
    <h1 class="title">商城列表</h1>
    <!--列表-->
    <el-table
        ref="multipleTableRef"
        :data="productList"
        style="width: 800px"
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

    <!--分页-->
    <div class="foo-page">
      <el-pagination
          :page-size="limit"
          :pager-count="pagerCount"
          layout="prev, pager, next"
          :total="total"
          @current-change=""
      />
    </div>
  </div>
</template>

<script setup>
//选中的商品
import {getProductPage} from "../request/product_table_req.js";

const chooses = ref()
const handleChooseChange = (choose) => {
  chooses.value = choose
}
//所有商品
const productList = ref()
//分页相关
const start = ref(1)
const limit = ref(10) //每页显示 10 条
const pagerCount = ref(5) //超过多少页隐藏
const total = ref() //总数

onMounted(() => {
  getProductPage(start.value, limit.value).then((data) => {
    total.value = data.total
    productList.value = data.result
  })
})

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

.foo-page {
  width: 200px;
  height: 80px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
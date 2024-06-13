<template>
  <div id="right-table">

    <!--新增产品对话框-->
    <el-button plain @click="addProductDialog = true">
      新增产品信息
    </el-button>
    <el-dialog v-model="addProductDialog" title="新增产品信息" width="500">
      <el-form :model="addProductForm">
        <el-form-item label="产品ID" :label-width="formLabelWidth">
          <el-input v-model="addProductForm.id" autocomplete="off" />
        </el-form-item>
        <el-form-item label="仓库ID(必须要正确填写)" :label-width="formLabelWidth">
          <el-input v-model="addProductForm.warehouseId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="产品名称" :label-width="formLabelWidth">
          <el-input v-model="addProductForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="addProductForm.description" autocomplete="off" />
        </el-form-item>
        <el-form-item label="价格" :label-width="formLabelWidth">
          <el-input v-model="addProductForm.price" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addProductDialog = false">取消</el-button>
          <el-button type="primary" @click="addProductReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>


    <!--修改产品信息对话框-->
    <el-button plain @click="updateProductDialog = true">
      修改产品信息
    </el-button>
    <el-dialog v-model="updateProductDialog" title="修改产品信息" width="500">
      <el-form :model="updateProductForm">
        <el-form-item label="产品ID" :label-width="formLabelWidth">
          <el-input v-model="updateProductForm.id" autocomplete="off" />
        </el-form-item>
        <el-form-item label="仓库ID(必须正确填写)" :label-width="formLabelWidth">
          <el-input v-model="updateProductForm.warehouseId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="产品名称" :label-width="formLabelWidth">
          <el-input v-model="updateProductForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="产品描述" :label-width="formLabelWidth">
          <el-input v-model="updateProductForm.description" autocomplete="off" />
        </el-form-item>
        <el-form-item label="产品价格" :label-width="formLabelWidth">
          <el-input v-model="updateProductForm.price" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="updateProductDialog = false">取消</el-button>
          <el-button type="primary" @click="updateProductReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

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
              @click="delProductReq(scope.row)"
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

const formLabelWidth = '140px'
//获取所有产品信息
const productData = ref()

//新增产品弹出框设置
const addProductDialog = ref(false)
//新增产品弹出框表单
const addProductForm = reactive({
  id: '',
  warehouseId: '',
  name: '',
  description: '',
  price: '',
})

//修改产品弹出框设置
const updateProductDialog = ref(false)
//修改产品表单
const updateProductForm = reactive({
  id: '',
  warehouseId: '',
  name: '',
  description: '',
  price: '',
})

//查询产品列表
const queryProductList = () => {
  ax.get('/product/list').then((success) => {
    productData.value = success.data.data
  })
}

//添加产品信息
const addProductReq = () => {
  addProductDialog.value = false
  ax.post('/product/add', addProductForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '产品信息新增成功！',
        type: 'success',
      })
    }
    queryProductList()
  })
}

//修改产品信息
const updateProductReq = () => {
  updateProductDialog.value = false
  ax.post("/product/update", updateProductForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '产品信息修改成功！',
        type: 'success',
      })
    }
    queryProductList()
  })
}

//删除产品信息
const delProductReq = (product) => {
  ax.get(`/product/del/${product.id}`).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '产品信息删除成功！',
        type: 'success',
      })
    }
    queryProductList()
  })
}
onMounted(() => {
  queryProductList()
})

</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
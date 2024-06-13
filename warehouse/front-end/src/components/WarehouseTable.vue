<template>
  <div id="right-table">

    <!--新增仓库对话框-->
    <el-button plain @click="addWarehouseDialog = true">
      新增仓库信息
    </el-button>
    <el-dialog v-model="addWarehouseDialog" title="新增仓库信息" width="500">
      <el-form :model="addWarehouseForm">
        <el-form-item label="ID" :label-width="formLabelWidth">
          <el-input v-model="addWarehouseForm.id" autocomplete="off" />
        </el-form-item>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="addWarehouseForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="addWarehouseForm.address" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addWarehouseDialog = false">取消</el-button>
          <el-button type="primary" @click="addWarehouseReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>


    <!--修改仓库信息对话框-->
    <el-button plain @click="updateWarehouseDialog = true">
      修改仓库信息
    </el-button>
    <el-dialog v-model="updateWarehouseDialog" title="修改仓库信息" width="500">
      <el-form :model="updateWarehouseForm">
        <el-form-item label="ID" :label-width="formLabelWidth">
          <el-input v-model="updateWarehouseForm.id" autocomplete="off" />
        </el-form-item>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="updateWarehouseForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="住址" :label-width="formLabelWidth">
          <el-input v-model="updateWarehouseForm.address" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="updateWarehouseDialog = false">取消</el-button>
          <el-button type="primary" @click="updateWarehouseReq" >
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

    <el-table :data="warehouseTable" style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="地址" prop="address" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="small"
              type="danger"
              @click="delWarehouseReq(scope.row)"
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
//仓库列表
const warehouseTable = ref()

//新增仓库弹出框设置
const addWarehouseDialog = ref(false)
//新增仓库弹出框表单
const addWarehouseForm = reactive({
  id: '',
  name: '',
  address: '',
})

//修改仓库弹出框设置
const updateWarehouseDialog = ref(false)
//修改仓库表单
const updateWarehouseForm = reactive({
  id: '',
  name: '',
  address: '',
})

//获取所有仓库信息
const queryWarehouseList = () => {
  ax.get('/warehouse/list').then((success) => {
    warehouseTable.value = success.data.data
  })
}

//添加仓库信息
const addWarehouseReq = () => {
  addWarehouseDialog.value = false
  ax.post('/warehouse/add', addWarehouseForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '仓库信息新增成功！',
        type: 'success',
      })
    }
    queryWarehouseList()
  })
}

//修改仓库信息
const updateWarehouseReq = () => {
  updateWarehouseDialog.value = false
  ax.post("/warehouse/update", updateWarehouseForm).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '仓库信息修改成功！',
        type: 'success',
      })
    }
    queryWarehouseList()
  })
}

//删除仓库信息
const delWarehouseReq = (warehouse) => {
  ax.get(`/warehouse/del/${warehouse.id}`).then((success) => {
    if (success.data.code === 0) {
      ElMessage({
        message: '仓库信息删除成功！',
        type: 'success',
      })
    }
    queryWarehouseList()
  })
}


onMounted(() => {
  queryWarehouseList()
})
</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
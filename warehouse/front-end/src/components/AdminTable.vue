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
const adminData = [
  {
    id: 'aaa',
    username: '杜鹏站',
    password: '1111',
  },
  {
    id: 'bbb',
    username: '东郊能',
    password: '2222',
  },
]

</script>

<style lang="less" scoped>
  #right-table {
    width: 100%;
  }
</style>
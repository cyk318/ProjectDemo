<template>
  <div id="index-left-list">
    <button class="box">
      <img class="icon" src="../infra/images/icon-find.png" alt="">
      <span class="desc">发现</span>
    </button>
    <button class="box">
      <img class="icon" src="../infra/images/icon-pub.png" alt="">
      <span class="desc">发布</span>
    </button>
    <button class="box">
      <img class="icon" src="../infra/images/icon-notify.png" alt="">
      <span class="desc">通知</span>
    </button>
    <button class="box">
      <img class="icon" src="../infra/images/icon-more.png" alt="">
      <span class="desc">更多</span>
    </button>
  </div>
</template>

<script setup>

</script>

<style lang="less" scoped>
#index-left-list {
  width: 250px;
  height: 100px;
  margin-left: 12px;
  .box {
    display: flex;
    align-items: center;
    width: 100%;
    height: 50px;
    margin-top: 15px;
    padding-left: 20px;
    .icon {
      width: 25px;
      margin-right: 10px;
    }
    .desc {
      font-weight: 900;
      font-size: 16px;
    }
  }
}
</style>
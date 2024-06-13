import { createApp } from 'vue'
import App from './App.vue'
import router from "./api/router.js";
import 'element-plus/es/components/message/style/css'
import pinia from './stores'  //引入

const app = createApp(App)
app.use(router)  //一定要在挂在前 use
app.use(pinia)
app.mount('#app')

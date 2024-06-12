import { createApp } from 'vue'
import App from './App.vue'
import router from "./api/router.js";
import 'element-plus/es/components/message/style/css'

const app = createApp(App)
app.use(router)  //一定要在挂在前 use
app.mount('#app')

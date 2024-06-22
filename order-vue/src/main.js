import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./common/router.js";

const app = createApp(App)
app.use(router)  //一定要在挂在前 use
app.mount('#app')

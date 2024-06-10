import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
    routes: [
        {
            name: '主页',
            component: () => import('../pages/Index.vue')
        },
    ],
    history: createWebHistory()
})

export default router
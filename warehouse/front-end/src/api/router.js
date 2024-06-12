import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
    routes: [
        //一开始就展示管理员信息
        {
            path: '/',
            redirect: '/admin'
        },
        {
            name: "管理员信息",
            path: '/admin',
            component: () => import("../components/AdminTable.vue")
        },
    ],
    history: createWebHistory()
})

export default router
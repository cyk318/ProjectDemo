import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";

const router = createRouter({
    routes: [
        //一开始就展示管理员信息
        {
            path: '/',
            redirect: '/admin'
        },
        {
            name: "admin",
            path: '/admin',
            component: () => import("../components/AdminTable.vue")
        },
        {
            name: "product",
            path: '/product',
            component: () => import("../components/ProductTable.vue")
        },
        {
            name: "warehouse",
            path: '/warehouse',
            component: () => import("../components/WarehouseTable.vue")
        },
        {
            name: "product_group",
            path: '/product_group',
            component: () => import("../components/ProductGroupTable.vue")
        },
    ],
    history: createWebHashHistory()
})

export default router
import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";

const router = createRouter({
    routes: [
        { path: '/', redirect: '/product_table' },
        { name: 'ProductTable',    path: '/product_table',    component: () => import('../components/ProductTable.vue') },
        { name: 'OrderTable',        path: '/order_table',        component: () => import('../components/OrderTable.vue') }
    ],
    history: createWebHashHistory()
})

export default router
import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";

const routes = [
    {
        path: '/',
        component: () => import("@/views/Index.vue"),
        meta: {
            show: false,
            isFrame: true,
        }
    },
    {
        path: '/index',
        name: 'index',
        component: () => import("@/views/Index.vue"),
        meta: {
            title: "首页",
            icon: "HomeFilled",
            show: true,
            isFrame: true,
        }
    },
    {
        path: '/login',
        name: 'login',
        component: () => import("@/views/Login.vue"),
        meta: {
            show: false,
            isFrame: false,
        }
    },
    {
        path: '/register',
        name: 'register',
        component: () => import("@/views/Register.vue"),
        meta: {
            show: false,
            isFrame: false,
        }
    },
    {
        path: '/order',
        name: 'order',
        component: () => import("@/views/order/Index.vue"),
        meta: {
            title: "订单管理",
            icon: "Document",
            show: true,
            isFrame: true,
        },
        children: [
            {
                path: '/orderlist',
                name: 'orderlist',
                component: () => import("@/views/order/OrderList.vue"),
                meta: {
                    title: "订单管理",
                    icon: "Document",
                    show: true,
                    isFrame: true,
                },
            },
        ]
    },
    {
        path: '/product',
        name: 'product',
        component: () => import("@/views/product/Index.vue"),
        meta: {
            title: "商品管理",
            icon: "Menu",
            show: true,
            isFrame: true,
        },
        children: [
            {
                path: '/category',
                name: 'category',
                component: () => import("@/views/product/Category.vue"),
                meta: {
                    title: "品类管理",
                    icon: "Menu",
                    show: true,
                    isFrame: true,
                },
            },
            {
                path: '/productaddedit',
                name: 'productaddedit',
                component: () => import("@/views/product/ProductAddEdit.vue"),
                meta: {
                    title: "商品编辑",
                    icon: "Plus",
                    show: false,
                    isFrame: true,
                },
            },
            {
                path: '/productlist',
                name: 'productlist',
                component: () => import("@/views/product/ProductList.vue"),
                meta: {
                    title: "商品管理",
                    icon: "Menu",
                    show: true,
                    isFrame: true,
                },
            },
        ]
    },
    {
        path: '/vendor',
        name: 'vendor',
        component: () => import("@/views/vendor/Index.vue"),
        meta: {
            title: "供应商管理",
            icon: "Menu",
            show: true,
            isFrame: true,
        },
        children: [
            {
                path: '/vendor/list',
                name: 'vendor/list',
                component: () => import("@/views/vendor/List.vue"),
                meta: {
                    title: "供应商管理",
                    icon: "Menu",
                    show: true,
                    isFrame: true,
                },
            },
        ]
    },
    {
        path: '/about',
        name: 'about',
        component: () => import("@/views/About.vue"),
        meta: {
            title: "关于我们",
            icon: "Phone",
            show: true,
            isFrame: true,
        }
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import("@/views/404.vue"),
        meta: {
            show: false,
            isFrame: false,
        }
    },
]

const router = createRouter({
    history: createWebHistory('/'),
    routes
})

export default router
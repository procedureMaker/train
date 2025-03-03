import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        component: () => import('../views/main.vue'),
        meta: {
            loginRequire: true
        },
        children: [{
            path: 'welcome',
            component: () => import('../views/main/welcome.vue')
        }, {
            path: 'about',
            component: () => import('../views/main/about.vue')
        }]
    },
    //默认重定向到welcome路由
    {
        path: '',
        redirect: '/welcome'
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
